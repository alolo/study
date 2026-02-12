import java.util.*;

public class Lesson_6_2 {
    static class SimplePhoneDirectory {
        private Map<String, List<String>> directory = new HashMap<>();

        public void add(String surname, String phoneNumber) {
            if (directory.containsKey(surname)) {
                List<String> phones = directory.get(surname);
                if (!phones.contains(phoneNumber)) {
                    phones.add(phoneNumber);
                }
            } else {
                List<String> phones = new ArrayList<>();
                phones.add(phoneNumber);
                directory.put(surname, phones);
            }
        }

        public List<String> get(String surname) {
            return directory.getOrDefault(surname, new ArrayList<>());
        }

        public void printAll() {
            for (Map.Entry<String, List<String>> entry : directory.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        }
    }

    public static void main(String[] args) {
        SimplePhoneDirectory phoneBook = new SimplePhoneDirectory();

        phoneBook.add("Сусликов", "+7(916)-382-74-91");
        phoneBook.add("Сусликов", "+7(495)-637-28-54");
        phoneBook.add("Сусликов", "+7(965)-241-86-37");

        phoneBook.add("Афоничкин", "+7(926)-573-49-12");
        phoneBook.add("Афоничкин", "+7(812)-349-67-85");

        phoneBook.add("Василькова", "+7(903)-514-26-73");
        phoneBook.add("Василькова", "+7(911)-728-43-96");

        phoneBook.add("Юдина", "+7(499)-856-31-49");

        phoneBook.add("Васильев", "+7(985)-267-94-18");
        phoneBook.add("Васильев", "+7(343)-576-82-31");
        phoneBook.add("Васильев", "+7(962)-418-73-65");

        //Дубликат
        phoneBook.add("Сусликов", "+7(916)-382-74-91");

        System.out.println("\nСправочник:");
        phoneBook.printAll();

        System.out.println("\nПоиск:");
        System.out.println("Сусликов: " + phoneBook.get("Сусликов"));
        System.out.println("Юдина: " + phoneBook.get("Юдина"));
        System.out.println("Афоничкин: " + phoneBook.get("Афоничкин"));

        System.out.println("\nПоиск несуществующих  фамилий:");
        System.out.println("Серпухова: " + phoneBook.get("Серпухова"));

        System.out.println("\nВсего фамилий: " + phoneBook.directory.size());

        int totalPhones = 0;
        for (List<String> phones : phoneBook.directory.values()) {
            totalPhones += phones.size();
        }
        System.out.println("Всего номеров: " + totalPhones);

    }
}