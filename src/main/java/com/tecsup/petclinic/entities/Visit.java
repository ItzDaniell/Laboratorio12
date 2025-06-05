package com.tecsup.petclinic.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Entity(name = "visits")
@Data
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "pet_id")
    private int petId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "visit_date")
    private LocalDate date;

    private String description;

    public Visit() {
    }

    public Visit(int id, int petId, LocalDate date, String description) {
        this.id = id;
        this.petId = petId;
        this.date = date;
        this.description = description;
    }

    public Visit(int petId, LocalDate date, String description) {
        this.petId = petId;
        this.date = date;
        this.description = description;
    }
}