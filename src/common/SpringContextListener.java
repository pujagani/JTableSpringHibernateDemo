package common;

import org.hibernate.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
/*
Annotation @WebListener makes this class ServletContextListener (contextInitialized and contextDestroyed methods gets called).
ServletContext is the common area of the application. We are storing SessionFactory in ServletContext.
When the context is destroyed then destroy the factory if it exists.
*/

@WebListener
public class SpringContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        ServletContext httpContext = event.getServletContext();
        SessionFactory factory = (SessionFactory) httpContext.getAttribute("SessionFactory");
        if (factory != null) {
            factory.close();
            httpContext.removeAttribute("SessionFactory");
        }
    }
}
