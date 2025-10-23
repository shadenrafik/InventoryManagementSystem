

import java.time.LocalDate;

public class InventorySystemExtendedTest {

    public static void main(String[] args) {
        double totalMark = 0;

        // Employee Management Test (3 Marks)
        System.out.println("Testing Employee Management...");
        totalMark += testEmployeeManagement();

        // Product Management Test (2 Marks)
        System.out.println("Testing Product Management...");
        totalMark += testProductManagement();

        // Purchase Management Test (4 Marks)
        System.out.println("Testing Purchase Management...");
        totalMark += testPurchaseManagement();

        // Return and Payment Management Test (9 Marks)
        System.out.println("Testing Return and Payment Management...");
        totalMark += testReturnAndPaymentManagement();

        // Display final score
        System.out.println("Total Score: " + totalMark + "/18");
        System.out.println("7 Marks for comparing files");
    }

    public static double testEmployeeManagement() {
        // Do not Forget to Clear File
        double mark = 0;
        try {
            AdminRole admin = new AdminRole();

            // Adding Employee Test
            admin.addEmployee("E001", "Ahmed", "ahmed@retail.com", "Alexandria", "0123456789");
            if (admin.getListOfEmployees().length == 1) {
                System.out.println("Case 1 passed : Add Employee");
                mark += 0.5;
            } else {
                System.out.println("Case 1 Failed: could not Add Employee");
            }

            // Duplicate Employee Test
            admin.addEmployee("E001", "Ahmed", "ahmed@retail.com", "Alexandria", "0123456789");
            if (admin.getListOfEmployees().length == 1) {
                System.out.println("Case 2 passed : Employee Duplicate should not be added");
                mark += 1;
            } else {
                System.out.println("Case 2 failed : Employee Duplicate added which is wrong");
            }

            // Adding another Employee Test
            admin.addEmployee("E002", "Mohamed", "mohamed@retail.com", "Cairo", "0123456780");
            if (admin.getListOfEmployees().length == 2) {
                System.out.println("Case 3 passed : Add second Employee");
                mark += 0.5;
            } else {
                System.out.println("Case 3 failed : could not Add second Employee");
            }

            // Removing Employee Test
            admin.removeEmployee("E001");
            if (admin.getListOfEmployees().length == 1) {
                System.out.println("Case 4 passed : Remove First Employee");
                mark += 1;
            } else {
                System.out.println("Case 4 failed : could not Remove First Employee");
            }
            admin.logout();

        } catch (Exception e) {
            System.out.println("Employee Management Test Failed: " + e.getMessage());
        }
        return mark;
    }

    public static double testProductManagement() {
        double mark = 0;
        try {
            EmployeeRole employee = new EmployeeRole();

            // Adding Product Test
            employee.addProduct("P001", "Laptop", "Apple", "TechSupplier", 10);
            if (employee.getListOfProducts().length == 1) {
                System.out.println("Case 5 passed : Add Product");
                mark += 0.5;
            } else {
                System.out.println("Case 5 failed : Could not Add Product");
            }

            // Duplicate Product Test
            employee.addProduct("P001", "Laptop", "Apple", "TechSupplier", 10);
            if (employee.getListOfProducts().length == 1) {
                System.out.println("Case 6 passed : Product Duplicate should not be added");
                mark += 0.5;
            } else {
                System.out.println("Case 6 failed : Product Duplicate added which is wrong");
            }

            // Verify Product Data Test
            if (employee.getListOfProducts()[0].getSearchKey().equals("P001")) {
                System.out.println("case 7 passed : get list of products of employee ");
                mark += 1;
            } else {
                System.out.println("case 7 failed : product is not added to list of employee");
            }
            employee.logout();

        } catch (Exception e) {
            System.out.println("Product Management Test Failed: " + e.getMessage());
        }
        return mark;
    }

    public static double testPurchaseManagement() {
        double mark = 0;
        try {
            EmployeeRole employee = new EmployeeRole();

            // Adding Product with quantity 1
            employee.addProduct("P002", "Mouse", "Logitech", "TechSupplier", 1);
            if (employee.getListOfProducts().length == 2) {
                System.out.println("case 8 passed : Add product");
                mark += 0.5;
            } else {
                System.out.println("case 8 failed : could not Add product");
            }

            // Prevent Duplicate Product Test
            employee.addProduct("P002", "Mouse", "Logitech", "TechSupplier", 1);
            if (employee.getListOfProducts().length == 2) {
                System.out.println("Case 9 passed : Product Duplicate should not be added");
                mark += 0.5;
            } else {
                System.out.println("Case 9 failed : Product Duplicate is added which is wrong");
            }

            // Purchase Product Test - check quantity decreases
            boolean purchased = employee.purchaseProduct("7845345678", "P002", LocalDate.now());
            // After purchase, quantity should be 0
            if (purchased && employee.getListOfProducts()[1].getQuantity() == 0 &&
                    employee.getListOfPurchasingOperations().length == 1) {
                System.out.println("Case 10 passed : purchase product and check that quantity is 0 after purchasing");
                mark += 1.5;
            } else {
                System.out.println("Case 10 failed : could not purchase product or quantity not updated correctly");
            }

            // Test Purchase when Out of Stock
            boolean outOfStockPurchase = employee.purchaseProduct("7845345679", "P002", LocalDate.now());
            if (!outOfStockPurchase) {
                System.out.println("Case 11 passed : could not purchase product when out of stock");
                mark += 1.5;
            } else {
                System.out.println("Case 11 failed : purchased product when out of stock which is wrong");
            }
            employee.logout();

        } catch (Exception e) {
            System.out.println("Purchase Management Test Failed: " + e.getMessage());
        }
        return mark;
    }

    public static double testReturnAndPaymentManagement() {
        double mark = 0;
        try {
            EmployeeRole employee = new EmployeeRole();

            // Purchase Product for Non-Existent Product Test
            boolean purchased = employee.purchaseProduct("1234567890", "P999", LocalDate.now());
            if (!purchased) {
                System.out.println("Case 12 passed : could not purchase non-existing product");
                mark += 1;
            } else {
                System.out.println("Case 12 failed : purchased non-existing product which is wrong");
            }

            // Add a new product for testing
            employee.addProduct("P003", "Keyboard", "Logitech", "TechSupplier", 5);

            // Purchase Product Successfully
            purchased = employee.purchaseProduct("1234567890", "P003", LocalDate.now());
            if (purchased) {
                System.out.println("Case 13 passed : successfully purchased existing product");
                mark += 1;
            } else {
                System.out.println("Case 13 failed : could not purchase existing product with available quantity");
            }

            // Return Product within 14 days
            LocalDate purchaseDate = LocalDate.now();
            LocalDate returnDate = LocalDate.now().plusDays(5);
            double refund = employee.returnProduct("1234567890", "P003", purchaseDate, returnDate);
            if (refund != -1 && employee.getListOfProducts()[2].getQuantity() == 5) {
                System.out.println("Case 14 passed : returned product within 14 days and quantity restored");
                mark += 2;
            } else {
                System.out.println("Case 14 failed : could not return product within 14 days or quantity not restored");
            }

            // Purchase again for return test after 14 days
            employee.purchaseProduct("1234567891", "P003", LocalDate.now());

            // Try to Return Product after 14 days
            LocalDate oldPurchaseDate = LocalDate.now().minusDays(20);
            LocalDate lateReturnDate = LocalDate.now();
            employee.purchaseProduct("1234567892", "P003", oldPurchaseDate);
            double invalidRefund = employee.returnProduct("1234567892", "P003", oldPurchaseDate, lateReturnDate);
            if (invalidRefund == -1) {
                System.out.println("Case 15 passed : could not return product after 14 days");
                mark += 1;
            } else {
                System.out.println("Case 15 failed : returned product after 14 days which is wrong");
            }

            // Test Apply Payment
            employee.purchaseProduct("1234567893", "P003", LocalDate.now());
            boolean paymentApplied = employee.applyPayment("1234567893", LocalDate.now());
            if (paymentApplied) {
                System.out.println("Case 16 passed : payment applied successfully");
                mark += 1;
            } else {
                System.out.println("Case 16 failed : could not apply payment");
            }

            // Test returnDate earlier than purchaseDate
            LocalDate futurePurchase = LocalDate.now();
            LocalDate pastReturn = LocalDate.now().minusDays(5);
            employee.purchaseProduct("1234567894", "P003", futurePurchase);
            double invalidDateRefund = employee.returnProduct("1234567894", "P003", futurePurchase, pastReturn);
            if (invalidDateRefund == -1) {
                System.out.println("Case 17 passed : could not return with returnDate earlier than purchaseDate");
                mark += 1;
            } else {
                System.out.println("Case 17 failed : allowed return with invalid date which is wrong");
            }

            // Test return for non-existing purchase record
            double nonExistentRefund = employee.returnProduct("9999999999", "P003", LocalDate.now(), LocalDate.now());
            if (nonExistentRefund == -1) {
                System.out.println("Case 18 passed : could not return non-existing purchase record");
                mark += 2;
            } else {
                System.out.println("Case 18 failed : returned non-existing purchase which is wrong");
            }

            employee.logout();

        } catch (Exception e) {
            System.out.println("Return and Payment Management Test Failed: " + e.getMessage());
        }
        return mark;
    }
}
