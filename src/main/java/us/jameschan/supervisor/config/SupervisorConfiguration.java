package us.jameschan.supervisor.config;

import io.micrometer.common.lang.NonNullApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import us.jameschan.supervisor.advice.SupervisorInterceptor;

import javax.sql.DataSource;

/**
 * Supervisor core configuration.
 */
@NonNullApi
@Configuration
@EnableWebMvc
@PropertySource({
    "classpath:.env.properties"
})
public class SupervisorConfiguration implements WebMvcConfigurer {
    @Value("${hibernate.driver}")
    private String hibernateDriver;

    @Value("${database.url}")
    private String databaseUrl;

    @Value("${database.username}")
    private String databaseUsername;

    @Value("${database.password}")
    private String databasePassword;

    @Value("${server.domain}")
    private String serverDomain;

    /**
     * JPA data source configuration.
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(hibernateDriver);
        dataSource.setUrl(databaseUrl + "?createDatabaseIfNotExist=true");
        dataSource.setUsername(databaseUsername);
        dataSource.setPassword(databasePassword);

        return dataSource;
    }

    @Bean
    public String serverDomain() {
        return serverDomain;
    }

    /**
     * Interceptors registration.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SupervisorInterceptor());
    }
}
