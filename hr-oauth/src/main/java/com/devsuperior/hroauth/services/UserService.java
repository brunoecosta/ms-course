package com.devsuperior.hroauth.services;

import com.devsuperior.hroauth.entities.User;
import com.devsuperior.hroauth.feingclients.UserFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserFeignClient userFeignClient;

    public User findByEmail(String email) {
        User user = userFeignClient.findUserByEmail(email).getBody();

        if(null == user) {
            logger.error("Email not found: " + email);
            throw new IllegalArgumentException("Email not found:" + email);
        }
        logger.info("Email found: " + email);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userFeignClient.findUserByEmail(userName).getBody();

        if(null == user) {
            logger.error("Email not found: " + userName);
            throw new UsernameNotFoundException("Email not found:" + userName);
        }
        logger.info("Email found: " + userName);
        return user;
    }
}
