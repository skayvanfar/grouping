package ir.sk.processing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        // loads CSV file from the resource folder.
        URL resource = CsvParser.class.getClassLoader().getResource("sample.csv");
        File file = Paths.get(resource.toURI()).toFile();

        CsvParser obj = new CsvParser();
        List<List<String>> result = obj.readFile(file, 1);

        Search search = new Search();

        // O(Log n)
        int[] result2 = search.searchRange(result, "2018-12-09");

        Map<String, Integer> elementCountMap = getCounts(result, result2);
        Map<String, Integer> sortedElementCountMap = new LinkedHashMap<>();

        elementCountMap.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .forEach(entry -> {
                    sortedElementCountMap.put(entry.getKey(), entry.getValue());
                });

        Set<String> maxNames = getByMaxCount(sortedElementCountMap);

        maxNames.stream().forEach(s -> System.out.println(s));

    }

    private static Set<String> getByMaxCount(Map<String, Integer> sortedElementCountMap) {
        Set<String> maxNames = new HashSet<>();
        int maxCount = sortedElementCountMap.entrySet().stream().findFirst().get().getValue();
        for (Map.Entry<String, Integer> item : sortedElementCountMap.entrySet()) {
            if (item.getValue() == maxCount)
                maxNames.add(item.getKey());
            else
                break;
        }
        return maxNames;
    }

    private static Map<String, Integer> getCounts(List<List<String>> result, int[] result2) {
        Map<String, Integer> map = new LinkedHashMap<>();
        result.subList(result2[0], result2[1] + 1).stream().forEach(strings -> {
            String name = strings.get(0);
            map.put(name, map.getOrDefault(name, 0) + 1);
        });
        return map;
    }

}
