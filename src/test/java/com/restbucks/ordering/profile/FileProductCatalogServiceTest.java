package com.restbucks.ordering.profile;

import com.restbucks.ordering.file.FileProductCatalogService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.core.io.PathResource;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class FileProductCatalogServiceTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private FileProductCatalogService subject = new FileProductCatalogService();
    private String path = new PathResource("/Users/twer/Workspace/restbucks/src/test/data/file/product-catalog.xml").getPath();

    @Before
    public void setUp() throws Exception {
        subject.setPath(path);
    }

    @Test
    public void whenEvaluate_itShouldGetPriceUsingJsonPath() {
        double price = subject.evaluate("latte", "medium");

        assertThat(price, is(2.0));
    }

    @Test
    public void whenEvaluate_itShouldThrowNoSuchProduct_givenUnknownProductItem() {
        thrown.expectMessage("Cannot find product with name[latte] and size[i don't know]");

        subject.evaluate("latte", "i don't know");
    }

}