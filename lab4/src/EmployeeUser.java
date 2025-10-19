import java.util.*;

public class EmployeeUser implements Record {
    private String employeeId ;
    private String name;
    private String email;
    private String address;
    private String phoneNumber;
    
    EmployeeUser(String employeeId, String name, String email, String address, String phoneNumber){
        if(checkEmployeeID(employeeId)){
            this.employeeId = employeeId;
        }
        else{
            this.employeeId = null;
            System.out.println("Invalid ID number (can not two Employees have the same ID)");
        }
        this.name = name;
        if(checkEmail(email)){
            this.email=email;
        }
        else{
            this.email=null;
            System.out.println("Invalid email(for "+employeeId+")");
        }
        this.address=address;
        if(checkPhoneNUmber(phoneNumber)){
            this.phoneNumber=phoneNumber;
        }
        else{
            this.phoneNumber=null;
            System.out.println("Invalid phone number(for "+employeeId+")");
        }


    }

    private boolean checkEmployeeID (String ID){                    //make sure it is unique
        ArrayList <EmployeeUser> checker = EmployeeUserDatabase.returnAllRecords();
        for ( EmployeeUser user : checker){
            if(user.getSearchKey().equals(ID)){
                return false;
            }
        }
        return true;
    }

    private boolean checkEmail (String email){
        return email.contains("@")&&email.contains(".");
    }

    private boolean checkPhoneNUmber(String phone){         //may add validation for orange , we ,ect
        if(phone.length()!=11) return false;
        for(int i =0;i<11;i++){
            if (phone.charAt(i)<'0'|| phone.charAt(i)>'9') return false;
        }
        return true;
    }
    @Override
    public String lineRepresentation(){
        return employeeId+","+name+","+email+","+address+","+phoneNumber;
    }
    @Override
    public String getSearchKey(){
        return employeeId;
    }

}
