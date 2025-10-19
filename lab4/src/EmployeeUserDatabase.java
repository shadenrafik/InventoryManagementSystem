import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class EmployeeUserDatabase extends Database {
    private static ArrayList <EmployeeUser> records ;
    private String filename;

    public EmployeeUserDatabase(String filename) {
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
                EmployeeUser user = createRecordFrom(line);
                records.add(user);
            }
            scan.close();
        }
        catch(Exception e){
            System.out.println("Error opening the file ( "+ e.getMessage()+" ).");
        }
    }

    public EmployeeUser createRecordFrom (String line){
        String[]parts= line.split(",");
        return new EmployeeUser(parts[0], parts[1], parts[2], parts[3], parts[4]);
    }

    public void saveToFile(){
        try{
        PrintWriter fptrW =new PrintWriter(new FileWriter("lab4/"+filename));
        for(int i = 0 ; i<records.size();i++){
            EmployeeUser user = records.get(i);
            fptrW.println(user.lineRepresentation());
        }
        fptrW.close();
        }
        catch(Exception e){
            System.out.println("Error Writing to the file( "+ e.getMessage()+" ).");
        }
    }
    

}
