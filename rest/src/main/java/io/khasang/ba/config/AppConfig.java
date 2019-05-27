package io.khasang.ba.config;

import io.khasang.ba.dao.*;
import io.khasang.ba.dao.impl.*;
import io.khasang.ba.entity.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:log4j.properties")
public class AppConfig {

    @Bean
    public CatDao catDao() {
        return new CatDaoImpl(Cat.class);
    }

    @Bean
    public OperatorRoleDao operatorRoleDao() {
        return new OperatorRoleDaoImpl(OperatorRole.class);
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
    public OperatorDao userDao() {
        return new OperatorDaoImpl(Operator.class);
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

    @Bean
    public CustomerDao customerDao() {
        return new CustomerDaoImpl(Customer.class);
    }

    @Bean
    public CustomerRequestTypeDao customerRequestTypeDao() {
        return new CustomerRequestTypeDaoImpl(CustomerRequestType.class);
    }

    @Bean
    public CategoryDao categoryDao() {
        return new CategoryDaoImpl(Category.class);
    }

    @Bean
    public CustomerRequestStageDao customerRequestStageDao() {
        return new CustomerRequestStageDaoImpl(CustomerRequestStage.class);
    }

    @Bean
    public CustomerRequestStageNameDao customerRequestStageNameDao() {
        return new CustomerRequestStageNameDaoImpl(CustomerRequestStageName.class);
    }
}
