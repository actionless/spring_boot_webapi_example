package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Date;


@Entity
public class Customer {
    private static int idCounter = 0;

	@Id
    private int id = idCounter++;

    private String name;
    private String surname;
    private Date createdAt = Date.from(java.time.Instant.now());

	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getSurname() {
		return surname;
	}
}
