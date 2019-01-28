package com.hbsis.teste.exception;

import com.hbsis.teste.domain.payload.Response;

public class BadRequest {

	public static Response ResponseBadRequest(Exception e, Response classResponse) {

		EExceptions exception = EExceptions.valueOf(e.getClass().getSimpleName());
		switch (exception) {
		case FileNotFoundException:
			classResponse.setParamns(202, "Cidade não localizada");
			return classResponse;
		case ProtocolException:
			classResponse.setParamns(404, "Houve um problema em nosso servidor. Tente novamente mais tarde.");
			return classResponse;
		case MalformedURLException:
			classResponse.setParamns(500,!e.getMessage().isEmpty() ? e.getMessage() : "Houve um problema em nosso servidor. Entre em contato com o suporte técnico.");
			return classResponse;
		case IOException:
			classResponse.setParamns(500,!e.getMessage().isEmpty() ? e.getMessage() : "Houve um problema em nosso servidor. Entre em contato com o suporte técnico.");
			return classResponse;
		default:
			classResponse.setParamns(500, classResponse.message == null? e.getMessage() : classResponse.message);
			return classResponse;
		}

	}

}
