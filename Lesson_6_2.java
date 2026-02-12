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

        phoneBook.add("Сусликов", "+7-911-123-45-67");
        phoneBook.add("Сусликов", "+7-911-234-56-78");
        phoneBook.add("Сусликов", "+7-911-345-67-89");

        phoneBook.add("Афоничкин", "+7-922-111-22-33");
        phoneBook.add("Афоничкин", "+7-922-444-55-66");

        phoneBook.add("Василькова", "+7-933-777-88-99");
        phoneBook.add("Василькова", "+7-933-999-00-11");

        phoneBook.add("Юдина", "+7-944-555-66-77");

        phoneBook.add("Васильев", "+7-955-111-22-33");
        phoneBook.add("Васильев", "+7-955-444-55-66");
        phoneBook.add("Васильев", "+7-955-777-88-99");

        //Дубликат
        phoneBook.add("Сусликов", "+7-911-123-45-67");
        System.out.println("Попытка добавить дубликат для Сусликова");

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