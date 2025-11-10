//package lk.Project.SmartBiz.config;
//
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public FilterRegistrationBean<JwtFilter> jwtFilter(JwtFilter jwtFilter) {
//        FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(jwtFilter);
//        registrationBean.addUrlPatterns("/api/v1/business/*"); // Protect only business APIs
//        return registrationBean;
//    }
//}
