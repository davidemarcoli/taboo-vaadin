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
    static ArrayList<InputStream> files = new ArrayList<>();
    private static final Gson gson = new Gson();
    private static final Random rand = new Random();

    /**
     * Add an InputStream to the files ArrayList.
     * @param is The InputStream to add.
     */
    public static void addStream(InputStream is) {
        files.add(is);
    }

    /**
     * This Method gets all Files from a Zip File.
     * @Deprecated This method is deprecated and Not usable anymore.
     * @param file The Zip File to get the files from.
     * @throws IOException If the Zip File is not found.
     */

    @Deprecated
    public static void addZip(File file) throws IOException {
        ZipFile zipFile = new ZipFile(file);

        Enumeration<? extends ZipEntry> entries = zipFile.entries();

        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            if (entry.isDirectory()) {
            }
            InputStream stream = zipFile.getInputStream(entry);
        }
    }

    /**
     * This method gets all categories in the dataset.
     * @return An array of Strings with the category names.
     */

    @SneakyThrows
    public static String[] getAllCategories() {
        File dir = ResourceUtils.getFile("classpath:data/de");

        String[] fileNames = getFiles(Objects.requireNonNull(dir.listFiles()));
        System.out.println(Arrays.toString(fileNames));
        return fileNames;
    }

    /**
     * This method gets all languages that are present in the dataset.
     * @return An array of Strings with the language names.
     */

    @SneakyThrows
    public static String[] getAllLanguages() {
        File dir = ResourceUtils.getFile("classpath:data");
        String[] folderNames = getFolders(Objects.requireNonNull(dir.listFiles()));
        System.out.println(Arrays.toString(folderNames));
        return folderNames;
    }

    /**
     * This method gets all files in the File Objects.
     * @param files The Folders to get the files from.
     * @return An array of Strings with the file names.
     */

    public static String[] getFiles(File[] files) {
        ArrayList<String> filesNames = new ArrayList<>();

        for (File file : files) {
            if (file.isDirectory()) {
                getFiles(Objects.requireNonNull(file.listFiles())); // Calls same method again.
            } else {
                String[] pathSplit = file.getAbsolutePath().split(Pattern.quote("\\"));
                String fileName = pathSplit[pathSplit.length - 1].split("\\.")[0];
                filesNames.add(fileName.substring(0, 1).toUpperCase() + fileName.substring(1));
            }
        }

        return filesNames.toArray(new String[0]);
    }

    /**
     * This method gets all Folders in the File Objects.
     * @param files The files to get the folders from.
     * @return An array of Strings with the folder names.
     */

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

    /**
     * This method loads the data from the files and stores it in a map.
     * @param category The category to load.
     * @param language The language to load.
     */

    @SneakyThrows
    public static void insertWords(String category, String language) {
        File file = ResourceUtils.getFile("classpath:data" + File.separator + language.toLowerCase() + File.separator + category.toLowerCase() + ".json");

        byte[] encoded = Files.readAllBytes(file.toPath());
        String contents = new String(encoded, Charset.defaultCharset());

        Map<String, ArrayList<String>> result = new ObjectMapper().readValue(contents, HashMap.class);
        log.info("Result HM: " + result);
        data.putAll(result);
    }

    /**
     * This method is for Dev purposes only.
     * It prints all the words in the data folder.
     */

    public static void printAll() {
        for (InputStream is : files) {
            String contents = null;
            try {
                contents = IOUtils.toString(is, StandardCharsets.UTF_8);
                Map<String, ArrayList<String>> result2 = new ObjectMapper().readValue(contents, HashMap.class);
                log.info("Result HM2: " + result2);

                data.putAll(result2);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method returns a random word from the list of words.
     * @return a random word from the list of words.
     */

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
