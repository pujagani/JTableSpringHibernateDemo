package controller;

import org.hibernate.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

import javax.servlet.*;
import java.util.*;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired(required = true)
    private ServletContext httpContext;
// Here half work is done by getting by getting the username and password for the entered username.
    // Check if the username and password obtained match the entered username and password is done by Spring.
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        try {
            SessionFactory factory = BaseController.getFactory(httpContext);
            Session session = factory.openSession();

            String sql = "Select id, user_name, password From user Where user_name = '" + username + "'";
            Object[] row = (Object[]) session.createNativeQuery(sql).getSingleResult();
            String password = (String) row[2];
            session.close();

            List<GrantedAuthority> authorities = new ArrayList<>();
            return new User(username, password, authorities);
        } catch (Exception ex) {
            throw ex;
        }
    }
}