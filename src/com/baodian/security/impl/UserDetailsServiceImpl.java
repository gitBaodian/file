package com.baodian.security.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.baodian.service.user.UserManager;

public class UserDetailsServiceImpl implements UserDetailsService {

	private UserManager userManager;
	private UserCache userCache;

	public UserManager getUserManager() {
		return userManager;
	}
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	public UserCache getUserCache() {
		return userCache;
	}
	public void setUserCache(UserCache userCache) {
		this.userCache = userCache;
	}

	@Override
	public UserDetails loadUserByUsername(String account)
			throws UsernameNotFoundException {
		account = account.toLowerCase();
		String password = this.userManager.findU_passByAccount(account);
		if(password == null) {
			throw new UsernameNotFoundException(account);
		}
		return new User(account, password);
	}
}
