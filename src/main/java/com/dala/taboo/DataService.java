package com.dala.taboo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
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

//        log.info(words.get(rand.nextInt(words.size())).toString());

//        ArrayList<String> keysAsArray = new ArrayList<>(data.keySet());

//        Set<String> keySet = data.keySet();

        String[] keysAsArray = data.keySet().toArray(new String[0]);
//        String[] keysAsArray = keySet.toArray(new String[0]);

        log.info(keysAsArray);

//        String key = keysAsArray.get(rand.nextInt(keysAsArray.size()));
        String key = keysAsArray[rand.nextInt(keysAsArray.length)];
//        log.info("Key: " + key);
//        log.info(data.get(key));
//        log.info(data.get(key).getClass().getSimpleName());

//        System.out.println(data.get(key));

        ArrayList<String> dataSet = data.get(key);

//        ArrayList<String> newWord = new ArrayList<>();
//        newWord.add(key);
//        Collections.addAll(newWord, dataSet);
//
//        return newWord;

        return new TabooWord(key, dataSet.toArray(new String[0]));
    }
}
