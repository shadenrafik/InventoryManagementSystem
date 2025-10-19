import java.util.*;

public abstract class Database <T extends Record>{
    protected ArrayList<T> records;
    protected String filename;

    public Database(String filename){
        this.filename = filename;
        records = new ArrayList<>();
    }

//have different functions in different classes
// fa cant be written here (abstract only)
    public abstract void readFromFile();
    public abstract T createRecordFrom(String line);
    public abstract void saveToFile();

    //returns array of records
    public List<T> returnAllRecords() {
        return new ArrayList<>(records);
    }

    public void insertRecord(T record) {
        records.add(record);
        System.out.println("Record Created");
    }
//FFFFFFFFFFFFFFFFFFFFIIIIIIIIIIIIIIIXXXXXXXXXXXXXXXXX
    public void deleteRecord(String key) {
        for (T i : records) {
            if (Objects.equals(key, i.getSearchKey())){
                records.remove(i);}
            System.out.println("Record Deleted");}
            System.out.println("Record Not Found");
            return;
        }

    public boolean contains(String key) {
        for (T i : records) {
            if (Objects.equals(key, i.getSearchKey())) return true;
        }
        return false;
    }

    //returns 1 obj
    public T getRecord(String key){

        for(T i: records){
            if(Objects.equals(key,i.getSearchKey()))
                return i;
            }
        return null;
    }
}
