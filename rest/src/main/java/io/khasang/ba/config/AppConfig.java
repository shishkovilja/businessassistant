package io.khasang.ba.config;

import io.khasang.ba.dao.*;
import io.khasang.ba.dao.impl.*;
import io.khasang.ba.entity.*;
import io.khasang.ba.service.CreateTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

@Configuration
@PropertySource(value = "classpath:util.properties")
@PropertySource(value = "classpath:auth.properties")
@PropertySource(value = "classpath:log4j.properties")
public class AppConfig {

    @Autowired
    private Environment environment;

    @Bean
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.postgresql.driver"));
        dataSource.setUrl(environment.getRequiredProperty("jdbc.postgresql.url"));
        dataSource.setUsername(environment.getRequiredProperty("jdbc.postgresql.user"));
        dataSource.setPassword(environment.getRequiredProperty("jdbc.postgresql.password"));
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource());
        return jdbcTemplate;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        JdbcDaoImpl jdbcDao = new JdbcDaoImpl();
        jdbcDao.setDataSource(dataSource());
        jdbcDao.setUsersByUsernameQuery(environment.getRequiredProperty("usersByQuery"));
        jdbcDao.setAuthoritiesByUsernameQuery(environment.getRequiredProperty("rolesByQuery"));
        return jdbcDao;
    }

    @Bean
    public CreateTable createTable() {
        return new CreateTable(jdbcTemplate());
    }

    @Bean
    public CatDao catDao() {
        return new CatDaoImpl(Cat.class);
    }

    @Bean
    public RoleDao roleDao() {
        return new RoleDaoImpl(Role.class);
    }

    @Bean
    public DocumentItemDao documentItemDao() {
        return new DocumentItemDaoImpl(DocumentItem.class);
    }

    @Bean
    public OnlineQueueDao onlineQueueDao() {
        return new OnlineQueueDaoImpl(OnlineQueue.class);
    }

    @Bean
    public UserDao userDao() {
        return new UserDaoImpl(User.class);
    }

    @Bean
    public DocumentDao documentDao() {
        return new DocumentDaoImpl(Document.class);
    }

    @Bean
    public TicketDao ticketDao() {
        return new TicketDaoImpl(Ticket.class);
    }

    @Bean
    public PointOfInterestDao pointOfInterestDao() {
        return new PointOfInterestDaoImpl(PointOfInterest.class);
    }

    @Bean
    public EmployeeDao employeeDao() {
        return new EmployeeDaoImpl(Employee.class);
    }

    @Bean
    public FeedbackDao feedbackDao() {
        return new FeedbackDaoImpl(Feedback.class);
    }

    @Bean
    public NewsDao newsDao() {
        return new NewsDaoImpl(News.class);
    }

    @Bean
    public HistoryDao historyDao() {
        return new HistoryDaoImpl(History.class);
    }

    @Bean
    public RequestDao requestDao() {
        return new RequestDaoImpl(Request.class);
    }

    @Bean
    public CourseDao courseDao() {
        return new CourseDaoImpl(Course.class);
    }

    @Bean
    public ClientDao clientDao() {
        return new ClientDaoImpl(Client.class);
    }
}
