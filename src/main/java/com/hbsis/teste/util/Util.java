package com.hbsis.teste.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Util {

	public static StringBuffer abstractResponse(InputStream inputStream) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			return response;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean isNumber(String s) {
		try {
			Double.parseDouble(s.trim());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
