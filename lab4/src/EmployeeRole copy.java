package lab4;

import java.time.LocalDate;
import java.util.ArrayList;

public class EmployeeRole {
    private ProductDatabase productsDatabase;
    private CustomerProductDatabase customerProductDatabase;

    public EmployeeRole(){
        productsDatabase = new ProductDatabase("Products.txt");
        productsDatabase.readFromFile();
        customerProductDatabase = new CustomerProductDatabase("CustomerProducts.txt");
        customerProductDatabase
    }

    public void addProduct(String productID, String productName, String manufacturerName, String supplierName, int quantity, float price){
        Product newProduct = new Product (productID,productName,manufacturerName,supplierName,quantity,price);
        if(productsDatabase.contains(productID)){
            System.out.println("product ID already exixt");
        }
        else{
            productsDatabase.insertRecord(newProduct);
            productsDatabase.saveToFile();
            System.out.println("Product added successfully");
        }

    }
    
    public Product[] getListOfProducts(){
        ArrayList <Product> records = productsDatabase.returnAllRecords();
        Products [] productsArray = new product [records.size()];
        for(int i =0 ; i<records.size();i++){
            productsArray[i]= records.get(i);
        }
        return productsArray;

    }

    public CustomerProduct[] getListOfPurchasingOperations(){
        ArrayList <CustomerProduct> records = customerProductDatabase.returnAllRecords();
        CustomerProduct[] purchasingArray  = new CustomerProduct[records.size()];
        for(int i =0 ; i<records.size();i++){
            purchasingArray[i]= records.get(i);
        }
        
        return purchasingArray;

    }

    public boolean purchaseProduct(String customerSSN, String productID, LocalDate purchaseDate){
        Product product = productsDatabase.getRecord(productID);
        if(product==null){
            System.out.println("Product not found");
            return false;
        }
        if(product.getQuantity()== 0){
            System.out.println("Product is out of stock");
            return false;
        }
        product.setQuantity(product.getQuantity()-1);
        CustomerProduct newPurchase = new CustomerProduct (customerSSN, productID, purchaseDate);
        newPurchase.setPaid(false);
        customerProductDatabase.insertRecord(newPurchase);
        productsDatabase.saveToFile();
        customerProductDatabase.saveToFile();
        System.out.println("Purchase successful");
        return true;


    }

    public double returnProduct(String customerSSN, String productID,LocalDate purchaseDate ,LocalDate returnDate){
        if(returnDate.isBefore(purchaseDate)){
            System.out.println("Return date cannot be before purchase date");
            return -1;
        }
        if(!productsDatabase.contains(productID)){
            System.out.println("Product not found");
            return -1;
        }

        String purchaseKey = customerSSN + "," + productID + "," + purchaseDate.getDayOfMonth()+","+ purchaseDate.getMonthValue()+","+purchaseDate.getYear();
        
        if(!customerProductDatabase.contains(purchaseKey)){
            System.out.println("Purchase record not found");
            return -1;
        }

        int daysBetween = (int) java.time.temporal.ChronoUnit.DAYS.between(purchaseDate, returnDate);   //mohim
        
        if(daysBetween >14){
            System.out.println("more than 14 days have passed");
            return -1;
        }

        Product product = productsDatabase.getRecord(productID);
        product.setQuantity(product.getQuantity()+1);
        productsDatabase.saveToFile();
        customerProductDatabase.saveToFile();
        System.out.println("Product returned successfully");
        return product.getPrice() ;
          }

    public boolean applyPayment(String customerSSN, LocalDate purchaseDate){

        ArrayList<CustomerProduct> records = customerProductDatabase.returnAllRecords();

        for(int i =0 ; i<records.size();i++){
            if(records.get(i).getCustomerSSN().equals(customerSSN) && records.get(i).getPurchaseDate().equals(purchaseDate)){
                if(records.get(i).isPaid()){
                    System.out.println("already paid");
                    return false;
                }
                records.get(i).setPaid(true);
                customerProductDatabase.saveToFile();
                System.out.println("paid successfully");
                return true;
            }
        }
        System.out.println("purchase record not found");
        return false;
        

    }

    public void logout(){
        productsDatabase.saveToFile();
        customerProductDatabase.saveToFile();
        System.out.println("All Data was saved in productsDatabase and customerProductDatabase");
    }
}
