package com.example.demo.repo;

import com.example.demo.domain.TicketModel;
import com.example.demo.domain.TicketRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TicketJdbcRepo {

    private final JdbcTemplate jdbcTemplate;

    public List<TicketModel> findAllTickets(String query) {
        return jdbcTemplate.query(query, new TicketRowMapper());
    }

}
