
import java.util.*;

public class AdminRole {
    private EmployeeUserDatabase database ;

    public AdminRole(){
        database = new EmployeeUserDatabase("Employees.txt");
        database.readFromFile();
    }

    public void addEmployee(String employeeId, String name, String email, String address, String phoneNumber){
        EmployeeUser newEmployee = new EmployeeUser(employeeId, name, email, address, phoneNumber);
        database.insertRecord(newEmployee);
        database.saveToFile();
        System.out.println("Employee added successfully.");
    }

    public EmployeeUser[] getListOfEmployees(){
        ArrayList<EmployeeUser> records = new ArrayList<>(database.returnAllRecords());
        EmployeeUser[] employeesArray = new EmployeeUser[records.size()];
        for(int i =0 ; i<records.size();i++){
            employeesArray[i]= records.get(i);
        }
        return employeesArray;
    }

    public void removeEmployee(String key){
        if(database.contains(key)){
            database.deleteRecord(key);
            database.saveToFile();
            System.out.println("Employee removed successfully.");
        }else{
            System.out.println("Employee ID not found.");
        }
    }

    public void logout(){
        database.saveToFile();
        System.out.println("All data saved. logging out");
    }
}