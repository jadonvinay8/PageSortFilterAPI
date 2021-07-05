package com.example.demo.repo;

import com.example.demo.domain.Ticket;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepo extends JpaRepository<Ticket, String> {

    Slice<Ticket> findByUserId(String assignee, Pageable pageable);

}
