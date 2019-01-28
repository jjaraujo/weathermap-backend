package com.hbsis.teste.domain.model;

import org.springframework.data.mongodb.core.mapping.Document;

import com.hbsis.teste.domain.payload.Coord;
import com.hbsis.teste.domain.payload.Response;

@Document(collection = "city")
public class City extends Response{
	
	public City() {
	}
	public City(int cod, String message) {
		this.cod = cod;
		this.message = message;
	}
	
	public long id;
	public String name;
	public String country;
	public Coord coord;
	public long population;
}
