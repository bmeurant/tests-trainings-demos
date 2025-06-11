package io.bmeurant.spring61.features.jdbcclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Spring configuration for JDBC and JdbcClient.
 * Defines DataSource, JdbcTemplate, and JdbcClient beans.
 * Enables transaction management.
 */
@Configuration
@EnableTransactionManagement // Enables Spring's annotation-driven transaction management (@Transactional)
public class DataSourceConfig {

    /**
     * Defines a H2 in-memory DataSource.
     *
     * @return A DataSource instance.
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1"); // In-memory database
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    /**
     * Defines a JdbcTemplate bean, which JdbcClient wraps.
     *
     * @param dataSource The DataSource to use.
     * @return A JdbcTemplate instance.
     */
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    /**
     * Defines a JdbcClient bean, wrapping the JdbcTemplate.
     *
     * @param jdbcTemplate The JdbcTemplate to use.
     * @return A JdbcClient instance.
     */
    @Bean
    public JdbcClient jdbcClient(JdbcTemplate jdbcTemplate) {
        return JdbcClient.create(jdbcTemplate);
    }

    /**
     * Configures a transaction manager for the DataSource.
     * Essential for @Transactional to work.
     *
     * @param dataSource The DataSource to manage transactions for.
     * @return A PlatformTransactionManager instance.
     */
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}