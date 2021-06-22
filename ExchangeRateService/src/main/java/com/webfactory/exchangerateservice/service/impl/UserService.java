package com.webfactory.exchangerateservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.webfactory.exchangerateservice.ExchangeRateServiceApplication;
import com.webfactory.exchangerateservice.model.User;

@Service
public class UserService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = ExchangeRateServiceApplication.users.stream().filter(e -> e.getUsername().equals(username))
				.findFirst().get();
		if (user == null) {
			throw new UsernameNotFoundException("User " + username + " not found");
		}
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		user.getRoles().forEach(s -> {
			authorities.add(new SimpleGrantedAuthority(s.getName()));
		});
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
	}

}
