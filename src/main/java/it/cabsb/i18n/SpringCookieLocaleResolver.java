package it.cabsb.i18n;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.util.WebUtils;

@Component
public class SpringCookieLocaleResolver extends CookieLocaleResolver {

	private List<Locale> applicationLocales;
	
	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		// Check request for pre-parsed or preset locale.
		Locale locale = (Locale) request.getAttribute(LOCALE_REQUEST_ATTRIBUTE_NAME);
		if (locale != null) {
			if(applicationLocales.contains(locale)) {
				return locale;
			} else {
				return getDefaultLocale();
			}
		}

		// Retrieve and parse cookie value.
		Cookie cookie = WebUtils.getCookie(request, getCookieName());
		if (cookie != null) {
			locale = StringUtils.parseLocaleString(cookie.getValue());
			if (logger.isDebugEnabled()) {
				logger.debug("Parsed cookie value [" + cookie.getValue() + "] into locale '" + locale + "'");
			}
			if (locale != null) {
				request.setAttribute(LOCALE_REQUEST_ATTRIBUTE_NAME, locale);
				if(applicationLocales.contains(locale)) {
					return locale;
				} else {
					return getDefaultLocale();
				}
			}
		}

		return determineDefaultLocale(request);
	}

	public void setApplicationLocales(List<Locale> applicationLocales) {
		this.applicationLocales = applicationLocales;
	}
	
}
