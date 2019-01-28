package com.hbsis.teste.domain.payload.response.city;

import java.util.ArrayList;
import java.util.List;

import com.hbsis.teste.domain.payload.Forecast;
import com.hbsis.teste.domain.payload.Response;

public class ForecastResponse extends Response{
	
	public List<Weather> listWeatherDay;
	public long id;
	public String name;
	public String country;
	
	public ForecastResponse() {
		
	}
	
	public ForecastResponse(Forecast forecast) {
		listWeatherDay = new ArrayList<>();
		id = forecast.city.id;
		name = forecast.city.name;
		country = forecast.city.country;
		forecast.list.forEach(list->{
			String date = list.dt_txt.substring(0, 10);
			list.date = date;
			String hour = list.dt_txt.substring(list.dt_txt.length()-8, list.dt_txt.length());
			Weather weather = new Weather(date, hour, list.main.temp_min, list.main.temp_max, list.weather.get(0).description, list.main.humidity);
			listWeatherDay.add(weather);
		});
		
	}
}

class Weather{
	public String date;
	public String hour;
    public double min;
    public double max;
    public String description;
    public double humidity;
    
	public Weather(String date, String hour, double min, double max, String description, double humidity) {
		super();
		this.date = date;
		this.hour = hour;
		this.min = Math.round(min - 273.15);
		this.max = Math.round(max - 273.15);
		this.description = description;
		this.humidity = humidity;
	}
    
    
    
}
