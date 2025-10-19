import java.io.*;
import java.time.*;
import java.time.format.*;
import java.util.*;




public class CustomerProductDatabase extends Database<CustomerProduct>{


    public CustomerProductDatabase(String filename) {
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
                CustomerProduct user = createRecordFrom(line);
                records.add(user);
            }
            scan.close();
        }
        catch(Exception e){
            System.out.println("Error opening the file ( "+ e.getMessage()+" ).");
        }
    }

        public CustomerProduct createRecordFrom(String line){
          String[] parts=line.split(",");
          DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd-MM-yyyy");
          LocalDate date=LocalDate.parse(parts[2],formatter);
          boolean paid=Boolean.parseBoolean(parts[3]);
          CustomerProduct cp=new CustomerProduct(parts[0],parts[1],date);
          cp.setPaid(paid);
          return cp;
        }

    public void saveToFile(){
        try{
            PrintWriter fptrW =new PrintWriter(new FileWriter("lab4/"+filename));
            for(int i = 0 ; i<records.size();i++){
                CustomerProduct user = records.get(i);
                fptrW.println(user.lineRepresentation());
            }
            fptrW.close();
        }
        catch(Exception e){
            System.out.println("Error Writing to the file( "+ e.getMessage()+" ).");
        }
    }


    }





