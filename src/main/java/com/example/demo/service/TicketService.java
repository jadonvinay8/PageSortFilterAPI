package com.example.demo.service;

import com.example.demo.domain.Dates;
import com.example.demo.domain.TicketModel;
import com.example.demo.repo.TicketJdbcRepo;
import com.example.demo.util.Page;
import com.example.demo.util.QueryBuilder;
import com.example.demo.util.SortObj;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketJdbcRepo ticketJdbcRepo;

    public Page<TicketModel> findAll(int pageNum, int pageSize, Optional<List<String>> sort, Optional<List<String>> domains,
                                     Optional<List<String>> statuses, Dates dates) {
        String q = new QueryBuilder()
          .withUserId("123")
          .withFiltering(domains, statuses, dates)
          .withSorting(getSortObjs(sort))
          .withPaging(pageNum, pageSize)
          .build();
        System.out.println("builder query: " + q);
        List<TicketModel> tickets = ticketJdbcRepo.findAllTickets(q);
        if (tickets.isEmpty()) {
            return fetchEmptyPage(tickets);
        } else {
            return fetchPageWithContent(pageNum, pageSize, tickets);
        }
    }

    private List<SortObj> getSortObjs(Optional<List<String>> sort) {
        List<SortObj> sorts = null;
        if (sort.isPresent()) {
            sorts = sort.get().stream().map(s -> {
                String[] vals = s.split(":");
                return new SortObj(vals[0], vals[1].equals("ASC"));
            }).collect(Collectors.toList());
        }
        return sorts;
    }


    private Page<TicketModel> fetchPageWithContent(int pageNum, int pageSize, List<TicketModel> allTickets) {
        int totalRows = allTickets.get(0).getTotalRows();
        int totalPages = (int) Math.ceil((double) totalRows / pageSize);
        Page<TicketModel> ticketModelPage = new Page<>();
        return ticketModelPage.withContent(allTickets)
          .withTotalItems(totalRows)
          .withTotalPages(totalPages)
          .withFirstPage(pageNum == 1)
          .withLastPage(pageNum == totalPages);
    }

    private Page<TicketModel> fetchEmptyPage(List<TicketModel> allTickets) {
        Page<TicketModel> page = new Page<>();
        return page.withContent(allTickets)
          .withTotalPages(0)
          .withTotalItems(0)
          .withLastPage(true)
          .withFirstPage(true);
    }

}
