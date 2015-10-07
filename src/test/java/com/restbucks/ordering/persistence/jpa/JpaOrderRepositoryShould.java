package com.restbucks.ordering.persistence.jpa;

import com.github.hippoom.springtestdbunit.dataset.GivenWhenThenFlatXmlDataSetLoader;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.restbucks.ordering.Application;
import com.restbucks.ordering.domain.Order;
import com.restbucks.ordering.domain.OrderRepository;
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
public class JpaOrderRepositoryShould {
    @Autowired
    private OrderRepository subject;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @DatabaseSetup("given:classpath:persistence/order_save.xml")
    @ExpectedDatabase(
            value = "then:classpath:persistence/order_save.xml",
            assertionMode = NON_STRICT_UNORDERED
    )
    @Test
    public void save() throws Exception {
        new TransactionTemplate(transactionManager).execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {

                final Order given = subject.findByTrackingId("1");
                final Order toBeSaved = cloneFrom("2", given);

                subject.store(toBeSaved);
            }
        });
    }

    private Order cloneFrom(String trackingId, Order prototype) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setFieldAccessLevel(PRIVATE)
                .setFieldMatchingEnabled(true);
        mapper.addMappings(new PropertyMap<Order, Order>() {
            @Override
            protected void configure() {
                map(trackingId, destination.getTrackingId());
            }
        });
        return mapper.map(prototype, Order.class);
    }
}