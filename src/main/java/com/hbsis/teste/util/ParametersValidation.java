package com.hbsis.teste.util;

import java.net.MalformedURLException;

public class ParametersValidation {

	public static void validation(Object... objects) throws MalformedURLException {
		for(Object obj : objects) {
			if(obj == null)				
				throw new MalformedURLException("Há dados obrigatórios da requisição sem valor, verifique.");
		}
	}
	
}
