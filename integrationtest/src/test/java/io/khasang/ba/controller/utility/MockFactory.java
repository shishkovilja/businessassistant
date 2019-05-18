package io.khasang.ba.controller.utility;

import io.khasang.ba.entity.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import static io.khasang.ba.controller.utility.RestRequests.*;

/**
 * Factory class providing automation of creating and changing mock entities
 */
public final class MockFactory {

    /**
     * Amount of entities used to check relations
     */
    public static final int RELATED_ENTITIES_AMOUNT = 5;

    //Mock data for Customer
    private static final String TEST_CUSTOMER_LOGIN_PREFIX = "TEST_CUSTOMER_";
    private static final String TEST_CUSTOMER_RAW_PASSWORD = "123tEsT#";
    private static final String TEST_CUSTOMER_EMAIL_SUFFIX = "@ba.khasang.io";
    private static final String TEST_CUSTOMER_FULL_NAME = "Ivan Petrov";
    private static final LocalDate TEST_CUSTOMER_BIRTHDATE = LocalDate.of(1986, 8, 26);
    private static final String TEST_CUSTOMER_COUNTRY = "Russia";
    private static final String TEST_CUSTOMER_CITY = "Saint Petersburg";
    private static final String TEST_CUSTOMER_ABOUT = "Another one mock test customer";

    /**
     * Ready-to-use mock {@link Customer} supplier
     */
    public static final Supplier<Customer> MOCK_CUSTOMER_SUPPLIER = MockFactory::getMockCustomer;

    /**
     * Ready-to-use mock {@link CustomerRequestStage} supplier
     */
    public static final Supplier<CustomerRequestStage> MOCK_CUSTOMER_REQUEST_STAGE_SUPPLIER =
            MockFactory::getMockCustomerRequestStage;

    //Mock data for Operator
    private static final String TEST_OPERATOR_LOGIN_PREFIX = "TEST_OPERATOR_";
    private static final String TEST_OPERATOR_RAW_PASSWORD = "123tEsT#";
    private static final String TEST_OPERATOR_EMAIL_SUFFIX = "@ba.khasang.io";
    private static final String TEST_OPERATOR_FULL_NAME = "Ivan Petrov";
    private static final LocalDate TEST_OPERATOR_BIRTHDATE = LocalDate.of(1986, 8, 26);
    private static final String TEST_OPERATOR_COUNTRY = "Russia";
    private static final String TEST_OPERATOR_CITY = "Saint Petersburg";
    private static final String TEST_OPERATOR_ABOUT = "Another one mock test operator";

    //Mock data for CustomerRequestStage
    private static final String TEST_CUSTOMER_REQUEST_STAGE_DESCRIPTION = "Test description of the customer's request stage";

    /**
     * Create mock {@link Customer} instance
     *
     * @return mock customer instance
     */
    public static Customer getMockCustomer() {
        Customer customer = new Customer();
        CustomerInformation customerInformation = new CustomerInformation();

        customer.setLogin(TEST_CUSTOMER_LOGIN_PREFIX + UUID.randomUUID().toString());
        customer.setPassword(TEST_CUSTOMER_RAW_PASSWORD);
        customer.setEmail(UUID.randomUUID().toString() + TEST_CUSTOMER_EMAIL_SUFFIX);
        customer.setCustomerInformation(customerInformation);

        customerInformation.setFullName(TEST_CUSTOMER_FULL_NAME);
        customerInformation.setBirthDate(TEST_CUSTOMER_BIRTHDATE);
        customerInformation.setCountry(TEST_CUSTOMER_COUNTRY);
        customerInformation.setCity(TEST_CUSTOMER_CITY);
        customerInformation.setAbout(TEST_CUSTOMER_ABOUT);

        return customer;
    }

    /**
     * Create mock {@link Operator} instance
     *
     * @return mock operator instance
     */
    public static Operator getMockOperator() {
        Operator operator = new Operator();
        OperatorInformation operatorInformation = new OperatorInformation();

        operator.setLogin(TEST_OPERATOR_LOGIN_PREFIX + UUID.randomUUID().toString());
        operator.setEmail(UUID.randomUUID().toString() + TEST_OPERATOR_EMAIL_SUFFIX);
        operator.setPassword(TEST_OPERATOR_RAW_PASSWORD);
        operator.setOperatorInformation(operatorInformation);

        operatorInformation.setFullName(TEST_OPERATOR_FULL_NAME);
        operatorInformation.setBirthDate(TEST_OPERATOR_BIRTHDATE);
        operatorInformation.setCountry(TEST_OPERATOR_COUNTRY);
        operatorInformation.setCity(TEST_OPERATOR_CITY);
        operatorInformation.setAbout(TEST_OPERATOR_ABOUT);

        return operator;
    }

    /**
     * Create mock {@link CustomerRequestStage} instance
     *
     * @return mock {@link CustomerRequestStage} instance
     */
    public static CustomerRequestStage getMockCustomerRequestStage() {
        CustomerRequestStage customerRequestStage = new CustomerRequestStage();

        List<Operator> operatorList = getCreatedEntitiesList(
                MockFactory::getMockOperator,
                RELATED_ENTITIES_AMOUNT,
                OPERATOR_ROOT + ADD_PATH,
                HttpStatus.OK);

        customerRequestStage.setComment(TEST_CUSTOMER_REQUEST_STAGE_DESCRIPTION);
        customerRequestStage.setOperators(operatorList);

        return customerRequestStage;
    }

    /**
     * Change existing {@link CustomerRequestStage}. Firstly, new mock entity is made and then copying of necessary
     * fields from old entity (generally with constraints Id, Unique, NaturalId etc) is performed.
     *
     * @param oldCustomerRequestStage old entity
     * @return changed entity
     */
    public static CustomerRequestStage getChangedMockCustomerRequestStage(CustomerRequestStage oldCustomerRequestStage) {
        CustomerRequestStage newCustomerRequestStage = getMockCustomerRequestStage();

        newCustomerRequestStage.setId(oldCustomerRequestStage.getId());
        newCustomerRequestStage.setCreationTimestamp(oldCustomerRequestStage.getCreationTimestamp());

        return newCustomerRequestStage;
    }
}
