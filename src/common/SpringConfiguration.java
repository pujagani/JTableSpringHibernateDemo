package common;

import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.*;
/*
    Configure spring.
    Add SpringRequestInterceptor.
*/

@Import({SpringSecurityConfig.class})
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"controller"})
public class SpringConfiguration extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        SpringRequestInterceptor interceptor = new SpringRequestInterceptor();
        registry.addInterceptor(interceptor);
    }
}
