package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ticket")
public class Ticket implements Serializable {

    @Id
    private String id;
    private String userId;
    private String type;
    private String domain;
    private String status;
    private String identifiers;
    private Timestamp creationTime;
    private Timestamp closureTime;
    private boolean isActionTaken;

}
