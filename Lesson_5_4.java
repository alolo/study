public class Lesson_5_4 {

    public static void main(String[] args) {

        int[] numbers = {5, 10, 15, 20, 25};
        System.out.println("Допустимые индексы: от 0 до " + (numbers.length - 1));

        try {
            int validIndex = 2;
            System.out.println("\nДоступ к индексу " + validIndex + ": " + numbers[validIndex]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Исключение: " + e.getMessage());
        }

        int negativeIndex = -1;
        try {
            negativeIndex = -1;
            System.out.println("\n Доступ к индексу " + negativeIndex);
            System.out.println("Результат: " + numbers[negativeIndex]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Поймано исключение ArrayIndexOutOfBoundsException!");
            System.out.println("Сообщение: " + e.getMessage());
            System.out.println("Индекс: " + negativeIndex);
        }

        try {
            int tooLargeIndex = numbers.length; // 5
            System.out.println("\n Доступ к индексу " + tooLargeIndex);
            int value = numbers[tooLargeIndex];
            System.out.println("Результат: " + value);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Поймано исключение ArrayIndexOutOfBoundsException!");
            System.out.println("Сообщение: " + e.getMessage());
            System.out.println("Ошибка: максимальный допустимый индекс = " + (numbers.length - 1));
        }

        try {
            int wayTooLargeIndex = 10;
            System.out.println("\n Доступ к индексу " + wayTooLargeIndex);
            System.out.println("Результат: " + numbers[wayTooLargeIndex]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Поймано исключение ArrayIndexOutOfBoundsException!");
            System.out.println("Сообщение: " + e.getMessage());
        }

        accessArrayElement(new int[]{8,1,2,7,6},5);
        System.out.println("\nКонец");
    }

    public static void accessArrayElement(int[] array, int index) {
        System.out.println("\nГенерация исключений");
        try {
            System.out.println("Доступ к индексу[" + index + "]:");
            int value = array[index];
            System.out.println("Результат: " + value);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Исключение перехвачено: Индекс " + index + " выходит за границы массива");
        }
        if (index < 0 || index >= array.length) {
            throw new ArrayIndexOutOfBoundsException(
                    "\nИндекс " + index + " выходит за границы массива длиной " + array.length
            );
        }
        System.out.println("array[" + index + "] = " + array[index]);
    }
}
