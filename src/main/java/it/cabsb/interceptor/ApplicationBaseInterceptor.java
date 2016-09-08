package it.cabsb.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

@Component
public class ApplicationBaseInterceptor extends HandlerInterceptorAdapter {

	private String[] langs;
	private Boolean javascriptDebug;
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		if(modelAndView!=null && modelAndView.getViewName()!=null) {
			boolean isRedirect = modelAndView.getViewName().startsWith(UrlBasedViewResolver.REDIRECT_URL_PREFIX);
			if(!isRedirect) {
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			    String username = auth.getName();
			    modelAndView.getModelMap().addAttribute("username", username);
			    modelAndView.getModelMap().addAttribute("langs", langs);
			    modelAndView.getModelMap().addAttribute("javascriptDebug", javascriptDebug);
			}
		}
		super.postHandle(request, response, handler, modelAndView);
	}

	public void setLangs(String[] langs) {
		this.langs = langs;
	}

	public void setJavascriptDebug(String javascriptDebug) {
		this.javascriptDebug = Boolean.parseBoolean(javascriptDebug);
	}
	
}
