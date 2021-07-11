package project2;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.RFC4180Parser;
import com.opencsv.RFC4180ParserBuilder;
import com.opencsv.exceptions.CsvValidationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.collections.ObservableList;

public class CSV {

    // Using third party library, read data from file - MySongs.csv supplied in project folder
    public static LinkedList readFromCSV(File fileName) throws FileNotFoundException {

        LinkedList linkedList = new LinkedList();
        RFC4180Parser rfc4180Parser = new RFC4180ParserBuilder().build();
        CSVReaderBuilder csvReaderBuilder = new CSVReaderBuilder(new FileReader(fileName)).withCSVParser(rfc4180Parser).withSkipLines(1);
        try ( CSVReader csvReader = csvReaderBuilder.build()) {
            String[] songColumn;
            Song nextSong;
            while ((songColumn = csvReader.readNext()) != null) {
                nextSong = new Song();
                nextSong.setSongName(songColumn[0]);
                nextSong.setFilePath(songColumn[1]);
                linkedList.addNode(nextSong);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        linkedList.mergeSort();
        return linkedList;
    }

    // Using third party library, write data to new file or update saved MySongs.csv
    public static void writeToCSV(ObservableList<Song> results) {

        CSVWriter writer;
        File file = new File("MySongs.csv");
        try {
            writer = new CSVWriter(new FileWriter(file));
            String[] columns = {"Song Name", "File Path"};
            writer.writeNext(columns);
            for (Song result : results) {
                String[] nextResult = {result.getSongName(), result.getFilePath()};
                writer.writeNext(nextResult);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
