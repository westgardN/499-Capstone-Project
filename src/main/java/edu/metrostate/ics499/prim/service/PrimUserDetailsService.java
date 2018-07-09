package edu.metrostate.ics499.prim.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.metrostate.ics499.prim.model.User;
import edu.metrostate.ics499.prim.model.Role;

/**
 * The PrimUserDetailsService implements Spring's UserDetailsService interface for providing user services
 * to the authorization layer.
 */
@Service("primUserDetailsService")
public class PrimUserDetailsService implements UserDetailsService {

    static final Logger logger = LoggerFactory.getLogger(PrimUserDetailsService.class);

    @Autowired
    private UserService userService;

    /**
     * Called by Spring to load a user for authentication. Returns an instance of Spring's UserDetails class.
     *
     * @param ssoId the unigue single sign-on id that identifies the user we are looking for.
     *
     * @return an instance of Spring's UserDetails class.
     * @throws UsernameNotFoundException thrown if the provided ssoId does not identify a valid user.
     */
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String ssoId)
            throws UsernameNotFoundException {
        User user = userService.findBySsoId(ssoId);
        logger.info("User : {}", user);
        if (user == null) {
            logger.info("User not found");
            throw new UsernameNotFoundException("Username not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getSsoId(), user.getPassword(),
                true, true, true, true, getGrantedAuthorities(user));
    }


    private List<GrantedAuthority> getGrantedAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        for (Role role : user.getRoles()) {
            logger.info("Role : {}", role);
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getType()));
        }
        logger.info("authorities : {}", authorities);
        return authorities;
    }
}
