package it.cabsb.security;

import it.cabsb.model.Role;
import it.cabsb.model.User;
import it.cabsb.persistence.service.IUserService;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

@Component
public class SpringAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	private IUserService userService;
	
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        
        User user = userService.loadUserByUsername(username);
        
        if (user == null) {
            throw new BadCredentialsException("login.error.username.not.found");
        }

        if (!Hashing.sha256().hashString(password, Charsets.UTF_8).toString().equals(user.getPassword())) {
            throw new BadCredentialsException("login.error.password.error");
        }
        
        if(!user.isAccountNonExpired()) {
        	throw new AccountExpiredException("login.error.account.expired");
        }
        
        if(!user.isAccountNonLocked()) {
        	throw new LockedException("login.error.account.locked");
        }
        
        if(!user.isCredentialsNonExpired()) {
        	throw new CredentialsExpiredException("login.error.credentials.expired");
        }
        
        if(!user.isEnabled()) {
        	throw new DisabledException("login.error.account.disabled");
        }

        Set<Role> authorities = user.getAuthorities();

        return new UsernamePasswordAuthenticationToken(username, password, authorities);
	}

	public boolean supports(Class<?> authentication) {
		return true;
	}
}
