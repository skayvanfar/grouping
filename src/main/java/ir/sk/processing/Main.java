package ir.sk.processing;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public final static String FILE_FLAG = "-f";
    public final static String DATE_FLAG = "-d";

    private static class Option {
        String flag, opt;

        Option(String flag, String opt) {
            this.flag = flag;
            this.opt = opt;
        }
    }

    public static void main(String[] args) throws Exception {

        List<Option> options = getOptions(args);

        File file = getFile(getOpt(options, FILE_FLAG)); // TODO: 11/14/2021 many files

        CsvParser obj = new CsvParser();
        List<List<String>> result = obj.readFile(file, 1);

        Search search = new Search();

        // O(Log n)
        int[] result2 = search.searchRange(result, getOpt(options, DATE_FLAG));

        Map<String, Integer> elementCountMap = getCounts(result, result2);
        Map<String, Integer> sortedElementCountMap = new LinkedHashMap<>();

        elementCountMap.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .forEach(entry -> {
                    sortedElementCountMap.put(entry.getKey(), entry.getValue());
                });

        Set<String> maxNames = getByMaxCount(sortedElementCountMap);

        maxNames.stream().forEach(s -> write(System.out, s));

    }

    private static void write(PrintStream writer, String s) {
        writer.println(s);
    }

    private static String getOpt(List<Option> options,String flag) {
        return options.stream().filter(option -> option.flag.equals(flag)).findFirst().get().opt;
    }

    private static List<Option> getOptions(String[] args) {
        List<Option> optsList = new ArrayList<>();

        for (int i = 0; i < args.length; i++) {
            switch (args[i].charAt(0)) {
                case '-':
                    if (args[i].length() < 2)
                        throw new IllegalArgumentException("Not a valid argument: " + args[i]);
                    else {
                        if (args.length - 1 == i)
                            throw new IllegalArgumentException("Expected arg after: " + args[i]);
                        // -opt
                        optsList.add(new Option(args[i], args[i + 1]));
                        i++;
                    }
                    break;
            }
        }
        return optsList;
    }

    private static File getFile(String fileName) throws URISyntaxException {
        URL resource = CsvParser.class.getClassLoader().getResource(fileName);
        return Paths.get(resource.toURI()).toFile();
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
