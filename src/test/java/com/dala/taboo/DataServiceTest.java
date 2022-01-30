package com.dala.taboo;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DataServiceTest {

    /**
     * This test is to check if a category is present in the dataset
     */

    @Test
    void getAllCategories() {
        // assert that a category exists
        assertThat(DataService.getAllCategories().length > 0);
    }

    /**
     * This test is to check if a language is present in the dataset
     */

    @Test
    void getAllLanguages() {
        // assert that a language exists
        assertThat(DataService.getAllLanguages().length > 0);
    }

    /**
     * This test is to insert Words with a category and language and check if the words are inserted correctly
     */

    @Test
    @Order(1)
    void insertWords() {
        // insert words with the category cars and the language en
        DataService.insertWords("cars", "en");
        // assert that the words are inserted
        assertThat(DataService.data.size() > 0);
    }

    /**
     * This test is to check if the words are inserted correctly
     */

    @Test
    @Order(2)
    void getRandomWord() {
        // try to get a random word and see if it is not null
        assertNotNull(DataService.getRandomWord());
    }
}