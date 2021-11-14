package ir.sk.processing;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CsvParser {

    private static final char DEFAULT_SEPARATOR = ',';

    public List<List<String>> readFile(File csvFile) throws Exception {
        return readFile(csvFile, 0);
    }

    public List<List<String>> readFile(File csvFile, int skipLine) throws Exception {
        return read(new FileReader(csvFile), skipLine);
    }

    public List<List<String>> read(Reader reader, int skipLine) throws IOException {
        List<List<String>> result = new ArrayList<>();
        int indexLine = 1;

        try (BufferedReader br = new BufferedReader(reader)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (indexLine++ <= skipLine) {
                    continue;
                }

                List<String> csvLineInArray = parseLine(line);
                result.add(csvLineInArray);
            }
        }

        return result;
    }

    public List<String> parseLine(String line) {
        return parse(line, DEFAULT_SEPARATOR);
    }

    private List<String> parse(String line, char separator) {
        List<String> result = new ArrayList<>();
        StringBuilder field = new StringBuilder();

        for (char c : line.toCharArray()) {
            if (c == separator) {  // if find separator and not in quotes, add field to the list
                result.add(field.toString());
                field.setLength(0);             // empty the field and ready for the next
            } else {
                field.append(c);                // else append the char into a field
            }
        }

        result.add(field.substring(0, 10));           // this is the last field
        return result;

    }

}
