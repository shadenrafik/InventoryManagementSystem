package main;

import java.util.*;


public abstract class Database<T> {
    protected ArrayList<T> records = new ArrayList<>();
    protected String filename;

    public Database(String filename) {
        this.filename = filename;
    }

    public abstract void readFromFile();
    public abstract T createRecordFrom(String line);
    public abstract void saveToFile();

    public ArrayList<T> returnAllRecords() {
        return records;
    }

    public void insertRecord(T record) {
        records.add(record);
    }

    public void deleteRecord(T record) {
        records.remove(record);
    }

    public boolean contains(T record) {
        return records.contains(record);
    }
}
