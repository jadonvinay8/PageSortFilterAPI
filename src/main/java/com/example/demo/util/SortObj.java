package com.example.demo.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SortObj {

    private final String field;
    private final boolean isAscending;

    public String toString() {
        return field + ": " + (isAscending ? "ASC" : "DESC");
    }
}
