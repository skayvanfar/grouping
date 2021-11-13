package ir.sk.processing;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

class CsvParserTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void readFile() throws Exception {
        // loads CSV file from the resource folder.
        URL resource = CsvParser.class.getClassLoader().getResource("sample.csv");
        File file = Paths.get(resource.toURI()).toFile();

        CsvParser obj = new CsvParser();
        List<List<String>> result = obj.readFile(file, 1);

        int listIndex = 0;
        for (List<String> arrays : result) {
            System.out.println("\nString[" + listIndex++ + "] : " + arrays);

            int index = 0;
            for (String array : arrays) {
                System.out.println(index++ + " : " + array);
            }

        }
    }
}