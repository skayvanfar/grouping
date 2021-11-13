package ir.sk.processing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        // loads CSV file from the resource folder.
        URL resource = CsvParser.class.getClassLoader().getResource("sample.csv");
        File file = Paths.get(resource.toURI()).toFile();

        CsvParser obj = new CsvParser();
        List<List<String>> result = obj.readFile(file, 1);

        Search search = new Search();

      //  int[] result2 = search.searchRange(result, "2018-12-09");

        int listIndex = 0;
        for (List<String> arrays : result) {
            System.out.println("\nString[" + listIndex++ + "] : " + arrays);

            int index = 0;
            for (String array : arrays) {
                System.out.println(index++ + " : " + array);
            }
        }

      //  System.out.println(result.stream().filter(strings -> strings[1].startsWith("2018")));
    }

}
