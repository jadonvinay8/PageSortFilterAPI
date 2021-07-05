package com.example.demo.util;

import com.example.demo.domain.Ticket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.List;

@Data
@With
@NoArgsConstructor
@AllArgsConstructor
public class Page<E extends Ticket> {

    private List<E> content;
    private Integer totalItems;
    private Integer totalPages;
    private boolean isFirstPage;
    private boolean isLastPage;

}
