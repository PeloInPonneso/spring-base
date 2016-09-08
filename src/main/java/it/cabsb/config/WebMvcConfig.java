package it.cabsb.config;


import it.cabsb.i18n.Iso;
import it.cabsb.i18n.Iso.Country;
import it.cabsb.i18n.SpringCookieLocaleResolver;
import it.cabsb.interceptor.ApplicationBaseInterceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;

@Order(1)
@Configuration
@EnableWebMvc
@PropertySource({ "classpath:app.properties" })
@ComponentScan(basePackages = { "it.cabsb.web" })
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	
	@Autowired
    private Environment env;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		registry.addInterceptor(localeChangeInterceptor);
		
		ApplicationBaseInterceptor applicationBaseInterceptor = new ApplicationBaseInterceptor();
		applicationBaseInterceptor.setLangs(env.getProperty("application.lang", "available.lang").split(","));
		applicationBaseInterceptor.setJavascriptDebug(env.getProperty("javascript.debug"));
		registry.addInterceptor(applicationBaseInterceptor);
	}
	
	@Bean
	public CookieLocaleResolver localeResolver() {
		final SpringCookieLocaleResolver localeResolver = new SpringCookieLocaleResolver();
		String[] langs = env.getProperty("application.lang", "available.lang").split(",");
		List<Locale> applicationLocales = new ArrayList<Locale>();
		for (String lang : langs) {
			applicationLocales.add(new Locale(lang));
		}
		localeResolver.setApplicationLocales(applicationLocales);
		localeResolver.setDefaultLocale(new Locale(env.getProperty("default.lang")));
		localeResolver.setCookieMaxAge(Integer.MAX_VALUE);
		return localeResolver;
	}
	
	@Bean
	public Iso iso() {
		final Iso iso = new Iso();
		String[] langs = env.getProperty("application.lang", "available.lang").split(",");
		for (String lang : langs) {
			Locale locale = new Locale(lang);
			List<Country> countries = new ArrayList<Country>();
			for (String isoCountry : Locale.getISOCountries()) {
				Locale country = new Locale("", isoCountry);
				countries.add(iso.new Country(country.getCountry(), country.getDisplayCountry(locale)));
			}
			iso.getCountries().put(lang, countries);
		}
		return iso;
	}
	
	@Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        final PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        return propertySourcesPlaceholderConfigurer;
    }
	
	@Bean
	public MessageSource messageSource() {
		final ReloadableResourceBundleMessageSource bundle = new ReloadableResourceBundleMessageSource();
		bundle.setBasenames(new String[] {"WEB-INF/classes/i18n/messages"});
		bundle.setDefaultEncoding("UTF-8");
		return bundle;
	}
	
	@Bean
	public LocalValidatorFactoryBean validator() {
		final LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.setValidationMessageSource(messageSource());
		return localValidatorFactoryBean;
	}
	
	
	@Bean
	public InternalResourceViewResolver viewResolver() {
		final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
	@Bean
	public TilesConfigurer tilesConfigurer() {
		final TilesConfigurer tilesConfigurer = new TilesConfigurer();
		tilesConfigurer.setDefinitions("/WEB-INF/classes/it/cabsb/tiles/definitions.xml");
		return tilesConfigurer;
	}
	
	@Bean
	public VelocityEngineFactoryBean velocityEngineFactoryBean() {
		final VelocityEngineFactoryBean velocityEngineFactoryBean = new VelocityEngineFactoryBean();
		
		Properties velocityProperties = new Properties();
		velocityProperties.put("resource.loader", "class");
		velocityProperties.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		velocityProperties.put("velocimacro.library", "org/springframework/web/servlet/view/velocity/spring.vm");
		
		velocityEngineFactoryBean.setVelocityProperties(velocityProperties);
		return velocityEngineFactoryBean;
	}
	
	@Bean
    public CommonsMultipartResolver filterMultipartResolver(){
		final CommonsMultipartResolver filterMultipartResolver = new CommonsMultipartResolver();
		filterMultipartResolver.setMaxUploadSize(10000000);
		return filterMultipartResolver;
    }
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
		registry.addResourceHandler("/static/**").addResourceLocations("/static/");
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
	}
	
	@Override
	public Validator getValidator() {
		return validator();
	}
	
}
