package it.cabsb.sample;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Localization {
	
	private static Logger _log = LoggerFactory.getLogger(Localization.class);
	
	public static void main(String[] args) {
		Locale locale = Locale.GERMAN;      
		for (String country : Locale.getISOCountries()) {
			_log.info(new Locale("", country).getDisplayCountry(locale));
		}
	}

}
