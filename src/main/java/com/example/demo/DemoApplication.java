package com.example.demo;

import com.example.demo.domain.Ticket;
import com.example.demo.repo.TicketRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootApplication
@RequiredArgsConstructor
public class DemoApplication implements CommandLineRunner {

    private final TicketRepo ticketRepo;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) {
        List<String> types = Arrays.asList("CR", "AM", "IV");
        List<String> statuses = Arrays.asList("Created", "Assigned", "In Process", "Closed", "Escalated", "Re-opened");
        List<Ticket> tickets = IntStream.range(0, 1000)
          .mapToObj(i -> new Ticket(String.valueOf(i), Math.random() < 0.6 ? "123" : "245",
            types.get((int) (Math.random()*3)), "IN", statuses.get((int) (Math.random()*6)), "ids",
            Timestamp.from(Instant.now().minus(2, ChronoUnit.DAYS)),Timestamp.from(Instant.now()),
        !(Math.random() > 0.7)))
          .collect(Collectors.toList());
        ticketRepo.saveAll(tickets);
    }

}
