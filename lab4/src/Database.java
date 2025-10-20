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
    public abstract void saveToFile();

    //returns array of records
    public ArrayList<T> returnAllRecords() {
        return new ArrayList<>(records);
    }

    public void insertRecord(T record) {
        if (!contains(record.getSearchKey())) {
            records.add(record);
            System.out.println("Record Created.");
        } else {
            System.out.println("Record already exists.");}
    }


    public abstract T createRecordFrom (String line);



    public void deleteRecord(String key) {
        Iterator <T> i = records.iterator();
        while (i.hasNext()) {
            T record = i.next();
            if (Objects.equals(key, record.getSearchKey())) {
                i.remove();
                System.out.println("Record deleted.");
                return;
            }
        }
        System.out.println("Record not found.");}

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
        System.out.println("Record not found");
        return null;
    }
}
