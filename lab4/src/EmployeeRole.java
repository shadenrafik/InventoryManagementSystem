

import java.time.LocalDate;
import java.util.ArrayList;

public class EmployeeRole {
    private ProductDatabase productsDatabase;
    private CustomerProductDatabase customerProductDatabase;

    public EmployeeRole(){

    }

    public void addProduct(String productID, String productName, String manufacturerName, String supplierName, int quantity){
        Product newProduct = new Product(productID,productName,manufacturerName,supplierName,quantity);
        if(productDatabase.contains(productID)){
            System.out.println("product ID already exixt");
        }
        else{
            productsDatabase.insertRecord(newProduct);
            productsDatabase.saveToFile();
            System.out.println("Product added successfully");
        }

    }
    
    public Product[] getListOfProducts(){
        ArrayList <product> records = productsDatabase.returnAllRecords();
        Products[] productsArray = new product[records.size()];
        for(int i =0 ; i<records.size();i++){
            productsArray[i]= records.get(i);
        }
        return productsArray;

    }

    public CustomerProduct[] getListOfPurchasingOperations(){



    }

    public boolean purchaseProduct(String customerSSN, String productID, LocalDate purchaseDate){

        
    }

    public double returnProduct(String customerSSN, String productID,LocalDate purchaseDate ,LocalDate returnDate){


    }

    public boolean applyPayment(String customerSSN, LocalDate purchaseDate){


    }

    public void logout(){
        productsDatabase.saveToFile();
        customerProductDatabase.saveToFile();
        System.out.println("All Data was saved in productsDatabase and customerProductDatabase");
    }
}
