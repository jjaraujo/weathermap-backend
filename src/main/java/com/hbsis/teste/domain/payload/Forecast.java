package com.hbsis.teste.domain.payload;

import com.hbsis.teste.domain.model.City;

public class Forecast extends Response{
	public Forecast(int cod, String message) {
		super(cod, message);
	}
	
	public Forecast() {
	}
	
	public int cnt;
	public java.util.List<List> list;
	public City city;
}
