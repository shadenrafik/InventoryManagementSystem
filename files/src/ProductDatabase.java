import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
public class ProductDatabase extends Database <Product> {



    public ProductDatabase(String filename) {
        super(filename);
    }

    public void readFromFile(){
        records.clear();
        try{
            File fptr = new File ("lab4/"+filename);
            Scanner scan = new Scanner(fptr);
            String line;
            while(scan.hasNextLine()){
                line = scan.nextLine();
                Product products  = createRecordFrom(line);

                records.add(products);
            }
            scan.close();
        }
        catch(Exception e){
            System.out.println("Error opening the file ( "+ e.getMessage()+" ).");
        }
    }
    public Product createRecordFrom(String line){
        String[] objects=line.split(",");
        int quantity = Integer.parseInt(objects[4]);
        float price = Float.parseFloat(objects[5]);
        return new Product(objects[0], objects[1], objects[2], objects[3], quantity ,price);
    }

    public void saveToFile(){
        try{
            PrintWriter fptrW =new PrintWriter(new FileWriter("lab4/"+filename));
            for (int i = 0 ; i<records.size();i++){
                Product products = records.get(i);
                fptrW.println(products.lineRepresentation());
            }
            fptrW.close();
        }
        catch(Exception e){
            System.out.println("Error Writing to the file( "+ e.getMessage()+" ).");
        }
    }


}
