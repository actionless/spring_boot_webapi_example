package com.example.demo.entity;

import java.util.Date;


public class Customer {
    private static int idCounter = 0;

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
