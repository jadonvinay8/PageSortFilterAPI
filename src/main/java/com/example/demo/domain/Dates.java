package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dates {

    private String creationStartDate;
    private String creationEndDate;
    private String closureStartDate;
    private String closureEndDate;

}
