package com.example.demo.domain;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketRowMapper implements RowMapper<TicketModel> {

    @Override
    public TicketModel mapRow(ResultSet resultSet, int i) throws SQLException {
        TicketModel ticketModel = new TicketModel();
        ticketModel.setDomain(resultSet.getString("domain"));
        ticketModel.setIdentifiers(resultSet.getString("identifiers"));
        ticketModel.setStatus(resultSet.getString("status"));
        ticketModel.setType(resultSet.getString("type"));
        ticketModel.setUserId(resultSet.getString("user_id"));
        ticketModel.setId(resultSet.getString("id"));
        ticketModel.setTotalRows(resultSet.getInt("total_rows"));
        return ticketModel;
    }
}
