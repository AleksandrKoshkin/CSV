import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "data.csv";
        List<Employee> list = parseCSV(columnMapping, fileName);
        String json = listToJson(list);
        writeString(json);
    }

    private static void writeString(String json) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("data.json"))) {
            writer.write(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String listToJson(List list) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        String json = gson.toJson(list);
        return json;
    }

    private static List<Employee> parseCSV(String[] columnMapping, String fileName) {
        try {
            CSVReader reader = new CSVReader(new FileReader(fileName));
            ColumnPositionMappingStrategy columnPositionMappingStrategy = new ColumnPositionMappingStrategy();
            columnPositionMappingStrategy.setType(Employee.class);
            columnPositionMappingStrategy.setColumnMapping(columnMapping);
            CsvToBean csvToBean = new CsvToBeanBuilder(reader).withMappingStrategy(columnPositionMappingStrategy).build();
            return csvToBean.parse();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
