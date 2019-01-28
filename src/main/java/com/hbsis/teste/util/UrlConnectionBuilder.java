package com.hbsis.teste.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class UrlConnectionBuilder {
	private StringBuilder host;
	private StringBuilder variables;
	private final StringBuilder api_key;

	public UrlConnectionBuilder(Builder builder) {
		variables = builder.variables;
		host = new StringBuilder("http://api.openweathermap.org/data/2.5/forecast?");
		api_key = new StringBuilder("eb8b1a9405e659b2ffc78f0a520b1a46");
	}
	
	public HttpURLConnection build() throws MalformedURLException, ProtocolException, IOException {
			URL url = new URL(host.append(variables).append("&appid=").append(api_key).toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestMethod("GET");
			conn.setDoOutput(true);
			return conn;
	}

	public static class Builder implements INameAndCountry, IId,IZip, ILatLong{
		private StringBuilder variables;

		@Override
		public UrlConnectionBuilder byId(String id) {
			variables = new StringBuilder("id=").append(id);
			return new UrlConnectionBuilder(this);
		}

		@Override
		public UrlConnectionBuilder byNameAndCountry(String name, String country) {
			variables = new StringBuilder("q=").append(name).append(",").append(country);
			return new UrlConnectionBuilder(this);
		}

		@Override
		public UrlConnectionBuilder byZipAndCountry(String zip, String country) {
			variables = new StringBuilder("zip=").append(zip).append(",").append(country);
			return new UrlConnectionBuilder(this);
		}
		
		@Override
		public UrlConnectionBuilder byLatAndLon(String lat, String lon) {
			variables = new StringBuilder("lat=").append(lat.trim()).append("&lon=").append(lon.trim());
			return new UrlConnectionBuilder(this);
		}
	}
}

interface INameAndCountry {
	public UrlConnectionBuilder byNameAndCountry(String name, String country);
}

interface IId {
	public UrlConnectionBuilder byId(String id);
}

interface IZip {
	public UrlConnectionBuilder byZipAndCountry(String zip, String country);
}

interface ILatLong{
	public UrlConnectionBuilder byLatAndLon(String lat, String lon);
}
