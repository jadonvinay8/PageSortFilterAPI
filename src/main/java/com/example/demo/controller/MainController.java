package com.example.demo.controller;

import com.example.demo.domain.TicketModel;
import com.example.demo.service.TicketService;
import com.example.demo.util.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final TicketService ticketService;

    @GetMapping("/tickets")
    public Page<TicketModel> getTickets(@RequestParam("pageSize") int pageSize, @RequestParam("pageNum") int pageNum,
                                        @RequestParam(value = "sortBy", required = false) Optional<List<String>> sort,
                                        @RequestParam(value = "domain", required = false) Optional<List<String>> domains,
                                        @RequestParam(value = "status", required = false) Optional<List<String>> statuses
                                   ) {
        return ticketService.findAll(pageNum, pageSize, sort, domains, statuses);
    }

}
