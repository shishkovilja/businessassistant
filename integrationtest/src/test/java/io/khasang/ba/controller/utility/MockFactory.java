package io.khasang.ba.controller.utility;

import io.khasang.ba.entity.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Supplier;

import static io.khasang.ba.controller.utility.RestRequests.getCreatedEntitiesList;

/**
 * Factory class providing automation of creating and changing mock entities
 */
public final class MockFactory {

    /**
     * Amount of entities used to check relations
     */
    public static final int RELATED_ENTITIES_AMOUNT = 5;

    /**
     * Map which determines supplier for entity by entity class, therefore there is no necessity create mock entity directly,
     * because its' supplier will be detected automatically
     */
    public static final Map<Class<?>, Supplier<?>> mockSuppliersMap = Collections.unmodifiableMap(new HashMap<Class<?>, Supplier<?>>() {{
        put(Customer.class, MockFactory::getMockCustomer);
        put(CustomerRequestStage.class, MockFactory::getMockCustomerRequestStage);
        put(CustomerRequestStageName.class, MockFactory::getMockCustomerRequestStageName);
        put(Operator.class, MockFactory::getMockOperator);
    }});

    //Mock data for Customer
    private static final String TEST_CUSTOMER_LOGIN_PREFIX = "TEST_CUSTOMER_";
    private static final String TEST_CUSTOMER_EMAIL_SUFFIX = "@ba.khasang.io";
    private static final String TEST_CUSTOMER_FULL_NAME_PREFIX = "Ivan Petrov ";
    private static final String TEST_CUSTOMER_COUNTRY_PREFIX = "Russia ";
    private static final String TEST_CUSTOMER_CITY_PREFIX = "Saint Petersburg ";
    private static final String TEST_CUSTOMER_ABOUT_PREFIX = "Another one mock test customer";

    //Mock data for Operator
    private static final String TEST_OPERATOR_LOGIN_PREFIX = "TEST_OPERATOR_";
    private static final String TEST_OPERATOR_EMAIL_SUFFIX = "@operator.khasang.io";
    private static final String TEST_OPERATOR_FULL_NAME_PREFIX = "Barak Obama ";
    private static final String TEST_OPERATOR_COUNTRY_PREFIX = "USA ";
    private static final String TEST_OPERATOR_CITY_PREFIX = "New York ";
    private static final String TEST_OPERATOR_ABOUT_PREFIX = "Another one mock test operator ";

    //Mock data for CustomerRequestStage
    private static final String TEST_CUSTOMER_REQUEST_STAGE_DESCRIPTION = "Test description of the customer's request stage";

    //Mock data for CustomerRequestStageName
    private static final String TEST_CUSTOMER_REQUEST_STAGE_NAME_NAME_PREFIX = "TEST_STAGE_NAME_PREFIX_";
    private static final String TEST_CUSTOMER_REQUEST_STAGE_NAME_DESCRIPTION_PREFIX = "Customer's request stage name: ";

    /**
     * Create mock {@link Customer} instance
     *
     * @return mock customer instance
     */
    public static Customer getMockCustomer() {
        Random random = new Random();
        Customer customer = new Customer();
        CustomerInformation customerInformation = new CustomerInformation();

        customer.setLogin(TEST_CUSTOMER_LOGIN_PREFIX + UUID.randomUUID().toString());
        customer.setPassword(UUID.randomUUID().toString());
        customer.setEmail(UUID.randomUUID().toString() + TEST_CUSTOMER_EMAIL_SUFFIX);
        customer.setCustomerInformation(customerInformation);

        customerInformation.setFullName(TEST_CUSTOMER_FULL_NAME_PREFIX + UUID.randomUUID().toString());
        customerInformation.setBirthDate(LocalDate.ofYearDay(
                1930 + random.nextInt(71),
                1 + random.nextInt(365)));
        customerInformation.setCountry(TEST_CUSTOMER_COUNTRY_PREFIX + UUID.randomUUID().toString());
        customerInformation.setCity(TEST_CUSTOMER_CITY_PREFIX + UUID.randomUUID().toString());
        customerInformation.setAbout(TEST_CUSTOMER_ABOUT_PREFIX + UUID.randomUUID().toString());

        return customer;
    }

    /**
     * Change existing {@link Customer}. Firstly, new mock entity is made and then copying of necessary
     * fields from old entity (generally with constraints Id, Unique, NaturalId etc) is performed.
     *
     * @param oldCustomer old entity
     * @return changed entity
     */
    public static Customer getChangedMockCustomer(Customer oldCustomer) {
        Customer newCustomer = getMockCustomer();

        newCustomer.setId(oldCustomer.getId());
        newCustomer.setLogin(oldCustomer.getLogin());
        newCustomer.setRegistrationTimestamp(oldCustomer.getRegistrationTimestamp());

        return newCustomer;
    }

    /**
     * Create mock {@link Operator} instance
     *
     * @return mock operator instance
     */
    public static Operator getMockOperator() {
        Random random = new Random();
        Operator operator = new Operator();
        OperatorInformation operatorInformation = new OperatorInformation();

        operator.setLogin(TEST_OPERATOR_LOGIN_PREFIX + UUID.randomUUID().toString());
        operator.setPassword(UUID.randomUUID().toString());
        operator.setEmail(UUID.randomUUID().toString() + TEST_OPERATOR_EMAIL_SUFFIX);
        operator.setOperatorInformation(operatorInformation);

        operatorInformation.setFullName(TEST_OPERATOR_FULL_NAME_PREFIX + UUID.randomUUID().toString());
        operatorInformation.setBirthDate(LocalDate.ofYearDay(
                1930 + random.nextInt(71),
                1 + random.nextInt(365)));
        operatorInformation.setCountry(TEST_OPERATOR_COUNTRY_PREFIX + UUID.randomUUID().toString());
        operatorInformation.setCity(TEST_OPERATOR_CITY_PREFIX + UUID.randomUUID().toString());
        operatorInformation.setAbout(TEST_OPERATOR_ABOUT_PREFIX + UUID.randomUUID().toString());

        return operator;
    }

    /**
     * Change existing {@link Operator}. Firstly, new mock entity is made and then copying of necessary
     * fields from old entity (generally with constraints Id, Unique, NaturalId etc) is performed.
     *
     * @param oldOperator old entity
     * @return changed entity
     */
    public static Operator getChangedMockOperator(Operator oldOperator) {
        Operator newOperator = getMockOperator();

        newOperator.setId(oldOperator.getId());
        newOperator.setLogin(oldOperator.getLogin());
        newOperator.setRegistrationTimestamp(oldOperator.getRegistrationTimestamp());

        return newOperator;
    }

    /**
     * Create mock {@link CustomerRequestStage} instance
     *
     * @return mock {@link CustomerRequestStage} instance
     */
    public static CustomerRequestStage getMockCustomerRequestStage() {
        CustomerRequestStage customerRequestStage = new CustomerRequestStage();

        List<Operator> operatorList = getCreatedEntitiesList(
                Operator.class,
                RELATED_ENTITIES_AMOUNT,
                HttpStatus.CREATED);

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

    /**
     * Create mock {@link CustomerRequestStageName} instance
     *
     * @return mock {@link CustomerRequestStageName} instance
     */
    public static CustomerRequestStageName getMockCustomerRequestStageName() {
        CustomerRequestStageName customerRequestStageName = new CustomerRequestStageName();

        customerRequestStageName.setName(TEST_CUSTOMER_REQUEST_STAGE_NAME_NAME_PREFIX + UUID.randomUUID().toString());
        customerRequestStageName.setDescription(TEST_CUSTOMER_REQUEST_STAGE_NAME_DESCRIPTION_PREFIX +
                UUID.randomUUID().toString());

        return customerRequestStageName;
    }

    /**
     * Change existing {@link CustomerRequestStageName}. Firstly, new mock entity is made and then copying of necessary
     * fields from old entity (generally with constraints Id, Unique, NaturalId etc) is performed.
     *
     * @param oldCustomerRequestStageName old entity
     * @return changed entity
     */
    public static CustomerRequestStageName getChangedMockCustomerRequestStageName(CustomerRequestStageName oldCustomerRequestStageName) {
        CustomerRequestStageName newCustomerRequestStageName = getMockCustomerRequestStageName();

        newCustomerRequestStageName.setId(oldCustomerRequestStageName.getId());

        return newCustomerRequestStageName;
    }
}
