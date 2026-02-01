import java.time.LocalDate;

public class Lesson_3 {
    public static void main(String[] args) {
        Product productsArray[] = new Product[5];
        productsArray[0] = new Product("Samsung s25 Ultra", LocalDate.of(2025, 12, 3), "Samsung", "Korea", 120000, true);
        productsArray[1] = new Product("Apple Iphone 16 pro max 1tb", LocalDate.of(2025, 8, 13), "Apple", "China", 160000, false);
        productsArray[2] = new Product("Realme GT7", LocalDate.of(2025, 10, 5), "OPPO", "China", 80000, false);
        productsArray[3] = new Product("SSD NVME Kingston 1tb", LocalDate.of(2023, 12, 3), "Kingston", "China", 20000, true);
        productsArray[4] = new Product("ОЗУ XPG Adata", LocalDate.of(2024, 3, 22), "XPG", "China", 65000, true);
        for (Product product : productsArray) {
            product.productInfo();
            System.out.println("----------------------");
        }
        Park park = new Park("HappyLand");
        Park.Attraction a1 = park.new Attraction("Американские горки", "10:00 - 20:00", 500);
        Park.Attraction a2 = park.new Attraction("Колесо обозрения", "09:00 - 22:00", 300);

        a1.parkInfo();
        a2.parkInfo();
    }

    public static class Product {
        private String name;
        private LocalDate productionDate;
        private String manufacturer;
        private String countryOfOrigin;
        private double price;
        private boolean reserved;

        public Product(String name, LocalDate productionDate, String manufacturer, String countryOfOrigin, double price, boolean reserved) {
            this.name = name;
            this.productionDate = productionDate;
            this.manufacturer = manufacturer;
            this.countryOfOrigin = countryOfOrigin;
            this.price = price;
            this.reserved = reserved;
        }

        public void productInfo() {
            System.out.println("Название: " + name);
            System.out.println("Дата производства: " + productionDate);
            System.out.println("Производитель: " + manufacturer);
            System.out.println("Страна происхождения: " + countryOfOrigin);
            System.out.println("Цена: " + price + " Руб.");
            System.out.println("Забронирован: " + (reserved ? "Да" : "Нет"));
        }
    }

    public static class Park {
        private String parkName;

        public Park(String parkName) {
            this.parkName = parkName;
        }

        public class Attraction {
            private String attractionName;
            private String workingHour;
            private double price;

            public Attraction(String attractionName, String workingHour, double price) {
                this.attractionName = attractionName;
                this.workingHour = workingHour;
                this.price = price;
            }

            public void parkInfo() {
                System.out.println("Парк: " + parkName);
                System.out.println("Аттракцион: " + attractionName);
                System.out.println("Часы работы: " + workingHour);
                System.out.println("Стоимость: " + price + " Руб.");
                System.out.println("- - - - - - - - - - - - -");
            }
        }
    }
}