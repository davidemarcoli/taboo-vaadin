package com.dala.taboo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Log4j2
public class DataService {
    static Map<String, ArrayList<String>> data = new HashMap<>();
//    static ArrayList<TabooWord> words = new ArrayList<>();

    static ArrayList<InputStream> files = new ArrayList<>();

    private static final Gson gson = new Gson();
    private static final Random rand = new Random();

    public static void addStream(InputStream is) {
        files.add(is);
    }

    public static void addZip(File file) throws IOException {
        ZipFile zipFile = new ZipFile(file);

        Enumeration<? extends ZipEntry> entries = zipFile.entries();

        while(entries.hasMoreElements()){
            ZipEntry entry = entries.nextElement();

            if (entry.isDirectory()) {

            }

            InputStream stream = zipFile.getInputStream(entry);

        }
    }

    @SneakyThrows
    public static String[] getAllCategories() {
        InputStream in = DataService.class.getClassLoader().getResourceAsStream("classpath:data/de");
        File dir2 = ResourceUtils.getFile("classpath:data/de");
        File dir = new File("classpath:data/de");
        String[] fileNames = getFiles(Objects.requireNonNull(dir2.listFiles()));
        System.out.println(Arrays.toString(fileNames));
        return fileNames;
    }

    @SneakyThrows
    public static String[] getAllLanguages() {
        File dir = ResourceUtils.getFile("classpath:data");
        String[] folderNames = getFolders(Objects.requireNonNull(dir.listFiles()));
        System.out.println(Arrays.toString(folderNames));
        return folderNames;
    }

    public static String[] getFiles(File[] files) {
        ArrayList<String> filesNames = new ArrayList<>();

        for (File file : files) {
            if (file.isDirectory()) {
//                System.out.println("Directory: " + file.getAbsolutePath());
                getFiles(Objects.requireNonNull(file.listFiles())); // Calls same method again.
            } else {
//                System.out.println("File: " + file.getAbsolutePath());
//                String fileName = file.getAbsolutePath().replaceAll("\\", "\\\\");
                String[] pathSplit = file.getAbsolutePath().split(Pattern.quote("\\"));
                String fileName = pathSplit[pathSplit.length - 1].split("\\.")[0];
                filesNames.add(fileName.substring(0, 1).toUpperCase() + fileName.substring(1));
            }
        }

        return filesNames.toArray(new String[0]);
    }

    public static String[] getFolders(File[] files) {
        ArrayList<String> folderNames = new ArrayList<>();

        for (File file : files) {
            if (file.isDirectory()) {
                String[] pathSplit = file.getAbsolutePath().split(Pattern.quote("\\"));
                String fileName = pathSplit[pathSplit.length - 1];
                folderNames.add(fileName.substring(0, 1).toUpperCase() + fileName.substring(1));
            }
        }

        return folderNames.toArray(new String[0]);
    }

    @SneakyThrows
    public static void insertWords(String category, String language) {
        File file = ResourceUtils.getFile("classpath:data/" + language.toLowerCase() + "/" + category.toLowerCase() + ".json");

        byte[] encoded = Files.readAllBytes(file.toPath());
        String contents = new String(encoded, Charset.defaultCharset());

        Map<String, ArrayList<String>> result = new ObjectMapper().readValue(contents, HashMap.class);
        log.info("Result HM: " + result);
        data.putAll(result);
    }

    public static void printAll() {
        for (InputStream is : files) {
            String contents = null;
            try {
                contents = IOUtils.toString(is, StandardCharsets.UTF_8);
//                System.out.println(contents);

//                Map result = gson.fromJson(contents, Map.class);
//                Map<String, String[]> result2 = new ObjectMapper().readValue(contents, HashMap.class);
                Map<String, ArrayList<String>> result2 = new ObjectMapper().readValue(contents, HashMap.class);
//                Map<String, String[]> typedMap = new ObjectMapper().readValue(contents, new TypeReference<Map<String, String[]>>() {});
//                log.info("Result HM: " + result);
                log.info("Result HM2: " + result2);
//                log.info("Result HM3: " + typedMap);
//
//                ArrayList<String> keysAsArray = new ArrayList<>(result.keySet());
//                log.info("Keys: " + keysAsArray);
//
//                for (String key : keysAsArray) {
//                    words.add(new TabooWord(key, result.get(key)));
//                }


                data.putAll(result2);
//                System.out.println(data);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static TabooWord getRandomWord() {
        if (data.size() > 0) {
            String[] keysAsArray = data.keySet().toArray(new String[0]);
            String key = keysAsArray[rand.nextInt(keysAsArray.length)];
            ArrayList<String> dataSet = data.get(key);
            data.remove(key);
            return new TabooWord(key, dataSet.toArray(new String[0]));
        } else {
            return null;
        }
    }
}
