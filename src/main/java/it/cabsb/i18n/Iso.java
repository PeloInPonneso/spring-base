package it.cabsb.i18n;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class Iso implements Serializable {
	
	private static final long serialVersionUID = -7454934505766502860L;
	
	Map<String, List<Country>> countries = new HashMap<String, List<Country>>(0);

	public Map<String, List<Country>> getCountries() {
		return countries;
	}

	public void setCountries(Map<String, List<Country>> countries) {
		this.countries = countries;
	}
	
	public class Country {
		
		private String language;
		private String countryName;
		
		public Country(String language, String countryName) {
			this.language = language;
			this.countryName = countryName;
		}

		public String getLanguage() {
			return language;
		}

		public String getCountryName() {
			return countryName;
		}
		
	}
}
