import java.util.*;

class Student {
    private String name;
    private String group;
    private int course;
    private List<Integer> grades;

    public Student(String name, String group, int course, List<Integer> grades) {
        this.name = name;
        this.group = group;
        this.course = course;
        this.grades = new ArrayList<>(grades);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public List<Integer> getGrades() {
        return new ArrayList<>(grades);
    }

    public void setGrades(List<Integer> grades) {
        this.grades = new ArrayList<>(grades);
    }

    // Средний бал
    public double getAverageGrade() {
        if (grades.isEmpty()) {
            return 0.0;
        }
        int sum = 0;
        for (int grade : grades) {
            sum += grade;
        }
        return (double) sum / grades.size();
    }

    // Перевод
    public boolean promote() {
        if (getAverageGrade() >= 3.0) {
            this.course++;
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return course == student.course &&
                Objects.equals(name, student.name) &&
                Objects.equals(group, student.group) &&
                Objects.equals(grades, student.grades);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, group, course, grades);
    }

    @Override
    public String toString() {
        return String.format("Студент: %s: \nГруппа: %s  Курс: %d  Средний балл: %.2f",
                name, group, course, getAverageGrade());
    }
}

class StudentManager {
    static class SimplePhoneDirectory {
        private Map<String, List<String>> directory = new HashMap<>();

        // Добавление записи
        public void add(String surname, String phoneNumber) {
            // Если фамилия уже есть, добавляем к существующему списку
            if (directory.containsKey(surname)) {
                List<String> phones = directory.get(surname);
                if (!phones.contains(phoneNumber)) { // избегаем дубликатов
                    phones.add(phoneNumber);
                }
            } else {
                // Создаём новый список и добавляем номер
                List<String> phones = new ArrayList<>();
                phones.add(phoneNumber);
                directory.put(surname, phones);
            }
        }

        // Получение списка номеров по фамилии
        public List<String> get(String surname) {
            // Возвращаем список номеров или пустой список, если фамилии нет
            return directory.getOrDefault(surname, new ArrayList<>());
        }

        // Для удобства: вывод всех записей
        public void printAll() {
            for (Map.Entry<String, List<String>> entry : directory.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        }
    }

    public static void main(String[] args) {
        Set<Student> students = new HashSet<>();

        students.add(new Student("Сусликов Сергей", "1", 2, Arrays.asList(4, 5, 4, 5, 4)));
        students.add(new Student("Василькова Алёна", "1", 2, Arrays.asList(3, 3, 4, 3, 3)));
        students.add(new Student("Васильев Пётр", "2", 2, Arrays.asList(2, 3, 2, 3, 2)));
        students.add(new Student("Козлов Дмитрий", "2", 3, Arrays.asList(5, 5, 4, 5, 5)));
        students.add(new Student("Серпухова Валентина", "3", 3, Arrays.asList(3, 4, 3, 4, 3)));
        students.add(new Student("Афоничкин Григорий", "3", 1, Arrays.asList(2, 2, 3, 2, 2)));

        System.out.println("Все студенты");
        printAllStudents(students);

        removePoorStudents(students);
        System.out.println("\nСтуденты после репрессий (отчислений)");
        printAllStudents(students);

        promoteStudents(students);
        System.out.println("\nПереведённые:");
        printAllStudents(students);

        System.out.println("\nПо курсам");
        printStudents(students, 2);
        printStudents(students, 3);
        printStudents(students, 4);
    }

    public static void removePoorStudents(Set<Student> students) {
        Iterator<Student> iterator = students.iterator();
        while (iterator.hasNext()) {
            Student student = iterator.next();
            if (student.getAverageGrade() < 3.0) {
                System.out.println("Отчислен: " + student.getName() +
                        " (средний балл: " + student.getAverageGrade() + ")");
                iterator.remove();
            }
        }
    }

    public static void promoteStudents(Set<Student> students) {
        for (Student student : students) {
            if (student.promote()) {
                System.out.println("Переведен на курс " + student.getCourse() +
                        ": " + student.getName() +
                        " (средний балл: " + student.getAverageGrade() + ")");
            }
        }
    }

    public static void printStudents(Set<Student> students, int course) {
        System.out.println("\nСтуденты на " + course + " курсе:");
        boolean found = false;
        for (Student student : students) {
            if (student.getCourse() == course) {
                System.out.println("  - " + student.getName());
                found = true;
            }
        }
        if (!found) {
            System.out.println("  (нет студентов на данном курсе)");
        }
    }

    public static void printAllStudents(Set<Student> students) {
        for (Student student : students) {
            System.out.println(student);
        }
    }
}
