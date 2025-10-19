import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static String validateNonEmpty(String label) {
        String input;
        do {
            System.out.print(label);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) System.out.println("Field cannot be empty.");
        } while (input.isEmpty());
        return input;
    }

    public static String validateName(String label) {
        String input;
        do {
            input = validateNonEmpty(label);
            if (!input.matches("^[a-zA-Z\\s]+$")) {
                System.out.println("Name must contain only letters and spaces.");
                input = "";
            }
        } while (input.isEmpty());
        return input;
    }

    public static String validateEmail(String label) {
        String input;
        do {
            input = validateNonEmpty(label);
            if (!input.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                System.out.println("Invalid email format.");
                input = "";
            }
        } while (input.isEmpty());
        return input;
    }

    public static String validatePhone(String label) {
        String input;
        do {
            input = validateNonEmpty(label);
            if (!input.matches("^01[0-9]{9}$")) {
                System.out.println("Phone number must be 11 digits and start with 01.");
                input = "";
            }
        } while (input.isEmpty());
        return input;
    }

    public static int validateInteger(String label) {
        while (true) {
            try {
                System.out.print(label);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    public static float validateFloat(String label) {
        while (true) {
            try {
                System.out.print(label);
                return Float.parseFloat(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    public static LocalDate validateData(String label) {
        while (true) {
            System.out.print(label);
            String input = scanner.nextLine().trim();
            try {
                return LocalDate.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Use dd-MM-yyyy.");
            }
        }
    }

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n=== Inventory Management System ===");
            System.out.println("1. Admin");
            System.out.println("2. Employee");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            int choice = validateInteger("");
            switch(choice) {
                case 1 ->{
                    AdminRole admin = new AdminRole();
                    while (true){
                        System.out.println("\n=== Inventory Management System ===");
                        System.out.println("1. Admin - Add Employee");
                        System.out.println("2. Admin - Remove Employee");
                        System.out.println("3. Admin - List Employees");
                        System.out.println("4. Admin - Logout");
                        System.out.println("0. Exit");
                        System.out.print("Choose an option: ");

                        int choice1 = validateInteger("");

                        switch (choice1) {
                            case 1 -> {
                                String id = validateNonEmpty("Employee ID: ");
                                String name = validateName("Name: ");
                                String email = validateEmail("Email: ");
                                String address = validateNonEmpty("Address: ");
                                String phone = validatePhone("Phone: ");
                                admin.addEmployee(id, name, email, address, phone);
                            }
                            case 2 -> {
                                String id = validateNonEmpty("Employee ID to remove: ");
                                admin.removeEmployee(id);
                            }
                            case 3 -> {
                                EmployeeUser[] employees = admin.getListOfEmployees();
                                for (EmployeeUser e : employees) {
                                    System.out.println(e.lineRepresentation());
                                }
                            }
                            case 4 -> {
                                admin.logout();
                                System.out.println("Logged out. Returning to main menu");

                            }

                            case 0 -> {
                                admin.logout();

                                System.out.println("System exited. Data saved.");
                                return;
                            }
                            default -> System.out.println("Invalid option.");
                        }
                        if (choice1 == 4) break;
                    }}
                case 2 ->{
                    EmployeeRole employee = new EmployeeRole();
                    while (true){
                        System.out.println("1. Employee - Add Product");
                        System.out.println("2. Employee - List Products");
                        System.out.println("3. Employee - Purchase Product");
                        System.out.println("4. Employee - Return Product");
                        System.out.println("5. Employee - Apply Payment");
                        System.out.println("6. Employee - List Purchases");
                        System.out.println(("7. Employee - Logout"));
                        System.out.println("0. Exit");
                        System.out.print("Choose an option: ");
                        int choice2 = validateInteger("");

                        switch (choice2) {
                            case 1 -> {
                                String productID = validateNonEmpty("Product ID: ");
                                String productName = validateName("Product Name: ");
                                String manuName = validateName("Manufacturer: ");
                                String suppName = validateName("Supplier: ");
                                int quantity = validateInteger("Quantity: ");
                                float price = validateFloat("Price: ");
                                employee.addProduct(productID,productName,  manuName,  suppName,  quantity,  price);
                            }
                            case 2 -> {
                                Product[] products = employee.getListOfProducts();
                                for (Product p : products) {
                                    System.out.println(p.lineRepresentation());
                                }
                            }
                            case 3 -> {
                                String ssn = validateNonEmpty("Customer SSN: ");
                                String prodID = validateNonEmpty("Product ID: ");
                                LocalDate date = validateData("Purchase Date (dd-MM-yyyy): ");
                                boolean success = employee.purchaseProduct(ssn, prodID, date);
                                System.out.println(success ? "Purchase successful." : "Purchase failed.");
                            }
                            case 4 -> {
                                String ssn = validateNonEmpty("Customer SSN: ");
                                String prodID = validateNonEmpty("Product ID: ");
                                LocalDate purchaseDate = validateData("Purchase Date (dd-MM-yyyy): ");
                                LocalDate returnDate = validateData("Return Date (dd-MM-yyyy): ");
                                double refund = employee.returnProduct(ssn, prodID, purchaseDate, returnDate);
                                System.out.println(refund == -1 ? "Return failed." : "Return successful. Refund: " + refund);
                            }
                            case 5 -> {
                                String ssn = validateNonEmpty("Customer SSN: ");
                                LocalDate date = validateData("Purchase Date (dd-MM-yyyy): ");
                                boolean paid = employee.applyPayment(ssn, date);
                                System.out.println(paid ? "Payment applied." : "Payment failed.");
                            }
                            case 6 -> {
                                CustomerProduct[] purchases = employee.getListOfPurchasingOperations();
                                for (CustomerProduct cp : purchases) {
                                    System.out.println(cp.lineRepresentation());}}
                            case 7 -> {
                                employee.logout();
                                System.out.println("Logged out. Returning to main menu");
                            }
                            case 0 -> {
                                employee.logout();
                                System.out.println("System exited. Data saved.");
                                return;
                            }
                            default -> System.out.println("Invalid option. Try again.");
                        }

                        if (choice2 == 7) break;
                    }
                }

                case 0 -> {
                    System.out.println("System exited. Data saved.");
                    return;
                }

                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }


}