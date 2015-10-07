package com.restbucks.ordering.persistence.jpa;

import com.github.hippoom.springtestdbunit.dataset.GivenWhenThenFlatXmlDataSetLoader;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.restbucks.ordering.Application;
import com.restbucks.ordering.domain.Order;
import com.restbucks.ordering.domain.OrderRepository;
import com.restbucks.ordering.domain.Payment;
import com.restbucks.ordering.domain.PaymentRepository;
import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit.FlywayTestExecutionListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import static com.github.springtestdbunit.assertion.DatabaseAssertionMode.NON_STRICT_UNORDERED;
import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        DbUnitTestExecutionListener.class, FlywayTestExecutionListener.class})
@FlywayTest(invokeCleanDB = false)
@DbUnitConfiguration(dataSetLoader = GivenWhenThenFlatXmlDataSetLoader.class)
@ActiveProfiles("test")
public class JpaPaymentRepositoryShould {
    @Autowired
    private PaymentRepository subject;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @DatabaseSetup("given:classpath:persistence/payment_save.xml")
    @ExpectedDatabase(
            value = "then:classpath:persistence/payment_save.xml",
            assertionMode = NON_STRICT_UNORDERED
    )
    @Test
    public void save() throws Exception {
        new TransactionTemplate(transactionManager).execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {

                final Payment given = subject.findById("1");
                final Payment toBeSaved = cloneFrom("2", given);

                subject.store(toBeSaved);
            }
        });
    }

    private Payment cloneFrom(String id, Payment prototype) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setFieldAccessLevel(PRIVATE)
                .setFieldMatchingEnabled(true);
        mapper.addMappings(new PropertyMap<Payment, Payment>() {
            @Override
            protected void configure() {
                map(id, destination.getId());
            }
        });
        return mapper.map(prototype, Payment.class);
    }
}