import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class EmployeeUserDatabase {
    private static ArrayList <EmployeeUser> records ;
    private String filename;

    public EmployeeUserDatabase(String filename){
        this.filename=filename;
        records = new ArrayList<>();
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

    public static ArrayList<EmployeeUser> returnAllRecords(){
        return records;
    }

    public boolean contains(String key ){
        for(int i =0 ; i<records.size();i++){
            if(records.get(i).getSearchKey().equals(key)){
                return true;
            }
        }
        return false;
    }

    public EmployeeUser getRecord(String key){
        // if(!contains(key)){
        //     System.out.println("");
        //     return null;
        // }
        // else{
        for(EmployeeUser user : records){
            if(user.getSearchKey().equals(key)) {
            return user;
            }
        }
        // System.out.println("the employeeID not found");
        return null;
    }

    public void insertRecord(EmployeeUser record){
        if(!contains(record.getSearchKey())){
            records.add(record);
        }else{
            System.err.println("Employee ID already exists.");
        }
    }

    public void deleteRecord(String key){
        EmployeeUser deletedUser=getRecord(key);
        if(deletedUser==null){
            System.out.println("the employeeID not found.");
            return;
        }
        int indexOfDeletedUser=records.indexOf(deletedUser);
        records.remove(indexOfDeletedUser);
        
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
