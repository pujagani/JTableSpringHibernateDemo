package controller;

import model.*;
import org.hibernate.*;
import org.hibernate.boot.*;
import org.hibernate.boot.registry.*;
import org.springframework.beans.propertyeditors.*;
import org.springframework.validation.*;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class BaseController {
    public static final String DATE_FORMAT = "dd/MM/yyyy";

    public Session session;
    public HttpServletRequest request;
    public HttpServletResponse response;
    public HttpSession httpSession;
    public ServletContext httpContext;

    /*
        SpringRequestInterceptor calls preHandle and postHandle.
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Store request, response, httpSession and httpContext. Will be useful later.
        this.request = request;
        this.response = response;
        httpSession = request.getSession(true);
        httpContext = request.getServletContext();
        session = null;
        return true;
    }

    // Closing the Hibernate session after processing the request.
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        try {
            session.close();
            session = null;
            request = null;
            response = null;
            httpSession = null;
            httpContext = null;

        } catch (Exception ignore) {

        }
    }

    // Creating session factory  for Hibernate.
    public SessionFactory getFactory() {
        return getFactory(httpContext);
    }

    public static SessionFactory getFactory(ServletContext httpContext) {
        // Since session factory is heavy to create. If it is already created then use it, else create a new one.
        SessionFactory factory = (SessionFactory) httpContext.getAttribute("SessionFactory");
        if (factory != null) return factory;

        StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
        registryBuilder.configure();
        StandardServiceRegistry registry = registryBuilder.build();
        MetadataSources metadataSources = new MetadataSources(registry);
        // Adding classes. Creates tables.
        metadataSources.addAnnotatedClass(City.class);
        metadataSources.addAnnotatedClass(Course.class);
        metadataSources.addAnnotatedClass(Student.class);
        metadataSources.addAnnotatedClass(StudentPhone.class);
        metadataSources.addAnnotatedClass(StudentResult.class);
        metadataSources.addAnnotatedClass(User.class);

        Metadata metadata = metadataSources.buildMetadata();
        factory = metadata.buildSessionFactory();
        httpContext.setAttribute("SessionFactory", factory);
        return factory;
    }

    //Creating factory session.
    public void createSession() {
        if (session != null) return;
        SessionFactory factory = getFactory();
        session = factory.openSession();
    }

    //Inserting record in database using Hibernate.
    public JTableResult insert(Object obj) {
        JTableResult rslt = new JTableResult();
        try {
            //Hibernate session is created for database access.
            createSession();
            session.getTransaction().begin();
            session.save(obj);
            session.getTransaction().commit();
            rslt.Result = "OK";
            rslt.Record = obj;
            return rslt;
        } catch (Throwable ex) {
            rslt.Result = "Error";
            rslt.Message = exceptionToString(ex);
            return rslt;
        }
    }

    //Updating record in database using Hibernate.
    public JTableResult update(Object obj) {
        JTableResult rslt = new JTableResult();
        try {
            createSession();
            session.getTransaction().begin();
            session.update(obj);
            session.getTransaction().commit();
            rslt.Result = "OK";
            rslt.Record = obj;
            return rslt;
        } catch (Throwable ex) {
            rslt.Result = "Error";
            rslt.Message = exceptionToString(ex);
            return rslt;
        }
    }

    //Deleting record in database using Hibernate.
    public JTableResult delete(Object obj) {
        JTableResult rslt = new JTableResult();
        try {
            createSession();
            session.getTransaction().begin();
            session.delete(obj);
            session.getTransaction().commit();
            rslt.Result = "OK";
            return rslt;
        } catch (Throwable ex) {
            rslt.Result = "Error";
            rslt.Message = exceptionToString(ex);
            return rslt;
        }
    }

    //Error handling.
    public JTableResult toError(BindingResult bindingResult) {
        JTableResult rslt = new JTableResult();
        ObjectError objectError = bindingResult.getAllErrors().get(0);
        rslt.Result = "Error";
        String message = objectError.getDefaultMessage();
        rslt.Message = message;
        if (objectError instanceof FieldError) {
            FieldError fieldError = (FieldError) objectError;
            String fieldName = fieldError.getField();
            rslt.Message = fieldName + " " + message;
            String[] codes = fieldError.getCodes();
            if (codes != null && codes.length > 2) {
                if ("typeMismatch.java.util.Date".equals(codes[2])) {
                    rslt.Message = fieldName + " is Invalid date format";
                }
            }
        }
        return rslt;
    }

    // Printing entire stack trace to debug.
    public static String exceptionToString(Throwable ex) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ex.printStackTrace(new PrintStream(stream));
        return new String(stream.toByteArray());
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    /*
    @ResponseBody
    @RequestMapping(value = "Save")
    public JTableResult Save(WebRequest request) {
        Student obj = new Student();
        WebRequestDataBinder binder = new WebRequestDataBinder(obj);
        binder.bind(request);
        binder.validate();
        BindingResult bindingResult = binder.getBindingResult();
        if (bindingResult.hasErrors()) return toError(bindingResult);
        int action = Integer.parseInt(request.getParameter("action"));
        if (action == 1) return insert(obj);
        else return update(obj);
    }
 */
}
