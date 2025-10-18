
       // declaring variables
public class Product implements Record {
    private String productID;
    private String productName;
    private String manufacturerName;
    private String supplierName;
    private int quantity;
    private float price;

        // constructor aalshan n initialize elobjects
    public Product(String productID, String productName, String manufacturerName, String supplierName, int quantity, float price) {
    this.productID = productID;
    this.productName = productName;
    this.manufacturerName = manufacturerName;
    this.supplierName = supplierName;
    this.quantity = quantity;
    this.price = price;
    }

    // methods: (4)
           // to get quantity
           // change quantity if a product is added or taken
           // nktb elinfo f line wahed w comma seperated
           // nrag3 el id bta3 elproduct

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity){
        this.quantity=quantity;
    }

    @Override
    public String lineRepresentation(){
        return productID + "," + productName + "," + manufacturerName + "," + supplierName + "," + quantity + "," + price;
    }
    @Override
    public String getSearchKey(){
        return productID;
    }

}