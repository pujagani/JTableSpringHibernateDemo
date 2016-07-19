package controller;


import model.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.security.web.authentication.logout.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import java.io.*;

@Controller
@RequestMapping(value = "/Login")
public class LoginController extends BaseController {
    @Autowired
    @Qualifier("authenticationManager")
    AuthenticationManager authenticationManager;

    @RequestMapping(value = "")
    public String index() {
        return "/jsp/Login.jsp";
    }

    @ResponseBody
    @RequestMapping(value = "Login")
    public JTableResult Login(String user_name, String password, String theme) {
        JTableResult rslt = new JTableResult();
        try {
            // Username and password is given as token to authentication manager.
            // If authenticated then theme is set.
            // Else error message is displayed.
            // Here Spring uses the User object from UserDetailsService to check if credentials are correct.

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user_name, password);
            Authentication auth = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
            httpSession.setAttribute("Theme", theme);
            rslt.Result = "OK";
            return rslt;
        } catch (Exception ex) {
            rslt.Result = "Error";
            rslt.Message = "Invalid User ID or Password";
            return rslt;
        }
    }

    @ResponseBody
    @RequestMapping(value = "Logout")
    public void Logout() throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        response.sendRedirect("../Home");
    }
}

