
        // declaring variables
          // LocalDate is a Java object for storing dates (year, month, day) (easier in date calculations and formatting)

public class CustomerProduct {
    private String customerSSN;
    private String productID;
    private LocalDate purchaseDate;
    private boolean paid;

            // constructor aalshan n initialize elobjects
    public CustomerProduct(String customerSSN, String productID, LocalDate purchaseDate){
        this.customerSSN=customerSSN;
        this.productID=productID;
        this.purchaseDate=purchaseDate;
        this.paid=false;
    }

         // methods: (7)
               // nreturn ssn of customer
               // nreturn id of product
               // nreturn date of purchase
               // nktb elinfo f line wahed w comma seperated
               // nreturn true if paid and false otherwise
               // nchange payment status
               // to find the info of the product we're searching for 

    public String getCustomerSSN(){
        return customerSSN;
    }

    public String getProductID(){
        return productID;
    }

    public LocalDate getPurchaseDate(){
        return purchaseDate;
    }

            // used DateTimeFormatter (this is a class in java that can convert dates into specific text form)
    public String lineRepresentation(){
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return customerSSN + "," + productID + "," + purchaseDate.format(formatter) + "," + paid;
    }

    public boolean isPaid(){
        return paid;
    }

    public void setPaid(boolean paid){
        this.paid=paid;
    }

    public String getSearchKey(){
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return customerSSN + "," + productID + "," + purchaseDate.format(formatter);
    }







}
