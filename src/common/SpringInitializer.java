package common;

import org.springframework.web.servlet.support.*;

/*
    Initialize Spring framework (Return SpringConfiguration class as RootConfigClasses).
 */
public class SpringInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{SpringConfiguration.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/controller/*"};
    }
}