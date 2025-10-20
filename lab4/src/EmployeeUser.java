public class EmployeeUser implements Record {
    private String employeeId ;
    private String name;
    private String email;
    private String address;
    private String phoneNumber;
    
    public EmployeeUser(String employeeId, String name, String email, String address, String phoneNumber){
        this.employeeId = employeeId;
        this.name = name;
        this.email=email;
        this.address=address;
        this.phoneNumber=phoneNumber;

    }

    // private boolean checkEmployeeID (String ID){                    //make sure it is unique
    //     ArrayList <EmployeeUser> checker = EmployeeUserDatabase.returnAllRecords();
    //     for ( EmployeeUser user : checker){
    //         if(user.getSearchKey().equals(ID)){
    //             return false;
    //         }
    //     }
    //     return true;
    // }

    @Override
    public String lineRepresentation(){
        return employeeId+","+name+","+email+","+address+","+phoneNumber;
    }
    @Override
    public String getSearchKey(){
        return employeeId;
    }

}
