package com.dala.taboo;

import lombok.ToString;

/**
 * Created by lazar on 1/25/2022.
 * Project name: taboo-vaadin
 **/

@ToString
public class Person {
    private String username;

    public Person(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
