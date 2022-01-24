package com.dala.taboo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@AllArgsConstructor
@Data
@ToString
public class TabooWord {
    private String word;
    private String[] tabooWords;
}
