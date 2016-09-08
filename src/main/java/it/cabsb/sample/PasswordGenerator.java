package it.cabsb.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

public class PasswordGenerator {

	private static Logger _log = LoggerFactory.getLogger(PasswordGenerator.class);
	
	public static void main(String[] args) {
		String password = "admin";
		String passwordSha256 = Hashing.sha256().hashString(password, Charsets.UTF_8).toString();
		_log.info("Password: " + password);
		_log.info("SHA256 Password: " + passwordSha256);
	}

}
