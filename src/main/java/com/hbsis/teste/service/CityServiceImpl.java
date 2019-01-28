package com.hbsis.teste.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.hbsis.teste.domain.model.City;
import com.hbsis.teste.domain.payload.Forecast;
import com.hbsis.teste.domain.payload.request.city.CityRequest;
import com.hbsis.teste.domain.payload.response.city.ForecastResponse;
import com.hbsis.teste.domain.repository.CityRepository;
import com.hbsis.teste.exception.BadRequest;
import com.hbsis.teste.util.ParametersValidation;
import com.hbsis.teste.util.UrlConnectionBuilder;
import com.hbsis.teste.util.Util;

@Controller
@RequestMapping("/city")
public class CityServiceImpl implements CityService {

	@Autowired
	private CityRepository cityRepository;
	
	@RequestMapping(value = "save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@CrossOrigin(origins = "http://localhost:3000")
	public City save(@RequestBody String request) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		CityRequest cityRequest;
		try {
			cityRequest = mapper.readValue(request,CityRequest.class);
			cityRepository.save(cityRequest.city);
			Optional<City> c = cityRepository.findById(cityRequest.city.id);
			return c.get();
		} catch (IOException e) {
			return (City) BadRequest.ResponseBadRequest(e, new City());
		}
	}
	
	@RequestMapping("delete")
	@ResponseBody
	@CrossOrigin(origins = "http://localhost:3000")
	public boolean delete(long id) {
		try{
			cityRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@RequestMapping("listOne")
	@ResponseBody
	@CrossOrigin(origins = "http://localhost:3000")
	public Optional<City> listOne(long id) {
		return cityRepository.findById(id);
	}
	
	@RequestMapping(value = "listAll", method = RequestMethod.GET)
	@ResponseBody
	@CrossOrigin(origins = "http://localhost:3000")
	public List<City> listAll() {		
		return cityRepository.findAll();
	}

	@RequestMapping(value = "findCity", method = RequestMethod.GET)
	@ResponseBody
	@CrossOrigin(origins = "http://localhost:3000")
	public Forecast findCity(String text) {
		try {
			ParametersValidation.validation(text);
			HttpURLConnection conn;
			String[] parameters = text.split(",");
			
			if(parameters.length == 1) {
				//checks whether the value is a number. If yes, a search will be made for the id. if not, it will return error message.					
				if(!Util.isNumber(parameters[0]))
					throw new MalformedURLException("Separe o nome da cidade e a sigla do país por virgula.");
				else
				conn = new UrlConnectionBuilder.Builder().byId(text.trim()).build();
			}
			//checks if the user entered a incorrect value 
			else if(parameters.length > 2)
				throw new MalformedURLException("Não foi possível efetuar a busca. Separe os valores por somente uma virugula.");
			
			//checks if the length of country informed is bigger than two
			else if(!Util.isNumber(parameters[1]) && parameters[1].trim().length() > 2)
				throw new MalformedURLException("O pais deve ter apenas dois caracteres.");

			//checks if the value informed is the a latitude and longitude.
			else if(Util.isNumber(parameters[0]) && Util.isNumber(parameters[1]))
				conn = new UrlConnectionBuilder.Builder().byLatAndLon(parameters[0], parameters[1]).build();
			
			//checks if the value is the a zip code and country
			else if(Util.isNumber(parameters[0]) && !Util.isNumber(parameters[1]))
				conn = new UrlConnectionBuilder.Builder().byZipAndCountry(parameters[0], parameters[1]).build();
			
			//checks if the value is the name and country
			else if(!Util.isNumber(parameters[0]) && !Util.isNumber(parameters[1]))
				conn = new UrlConnectionBuilder.Builder().byNameAndCountry(parameters[0], parameters[1]).build();
			else {
				throw new MalformedURLException("Não foi possivel localizar a cidade");
			}
			//Here could be done a check if the city is already registered in the base. 
			//If so, it adds true to the forecast's isCityOnDatabase,
			//and the front would no longer let the user send save request
			return abstractForecast(conn);
		} catch (IOException e) {
			return (Forecast) BadRequest.ResponseBadRequest(e, new Forecast());
		}
	}

	@RequestMapping(value = "findWeatherByIdCity", method = RequestMethod.GET)
	@ResponseBody
	@CrossOrigin(origins = "http://localhost:3000")
	public ForecastResponse findWeatherByIdCity(long id) {
		try {
			ParametersValidation.validation(id);
			HttpURLConnection conn;
			if(id == 0)
				throw new MalformedURLException("ID da cidade inválido");
			else
				conn = new UrlConnectionBuilder.Builder().byId(id+"").build();
			ForecastResponse response = new ForecastResponse(abstractForecast(conn));
			return response;
		} catch (MalformedURLException e) {
			return (ForecastResponse) BadRequest.ResponseBadRequest(e, new ForecastResponse());
		} catch (IOException e) {
			return (ForecastResponse) BadRequest.ResponseBadRequest(e, new ForecastResponse());
		}
	}
	
	private Forecast abstractForecast(HttpURLConnection conn) {
		StringBuffer response;	
		try {
			InputStream is = conn.getInputStream();
			response = Util.abstractResponse(is);
			Forecast forecast = new Gson().fromJson(response.toString(), Forecast.class);
			return forecast;
		} catch (IOException e) {
			return (Forecast) BadRequest.ResponseBadRequest(e, new Forecast());
		}
	}
}
