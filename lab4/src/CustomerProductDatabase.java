import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class CustomerProductDatabase extends Database<CustomerProduct>{


    public CustomerProductDatabase(String filename) {
        super(filename);
    }

    @Override
    public void readFromFile(){
        records.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                records.add(createRecordFrom(line));
            }
        }
        catch (FileNotFoundException e){
            System.out.println("File not found!");
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

      @Override
        public CustomerProduct createRecordFrom(String line){
          String[] parts=line.split(",");
          DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd-MM-yyyy");
          LocalDate date=LocalDate.parse(parts[2],formatter);
          boolean paid=Boolean.parseBoolean(parts[3]);
          CustomerProduct cp=new CustomerProduct(parts[0],parts[1],date);
          cp.setPaid(paid);
          return cp;
        }

      @Override
              public void saveToFile() throws IOException {
          try (BufferedWriter bw=new BufferedWriter(new FileWriter(filename))){
              for (CustomerProduct b:records){
                  bw.write(b.lineRepresentation());
                  bw.newLine();
              }
          }
        }


    }





