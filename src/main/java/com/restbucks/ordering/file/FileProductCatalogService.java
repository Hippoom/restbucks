package com.restbucks.ordering.file;

import com.restbucks.ordering.domain.NoSuchProductException;
import com.restbucks.ordering.domain.ProductCatalogService;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.File;

import static java.lang.String.format;

@Configuration
@ConfigurationProperties(prefix = "productCatalog.file")
public class FileProductCatalogService implements ProductCatalogService {

    private String path;

    @Override
    public double evaluate(String itemName, String size) {
        try {
            File catalogFile = new File(path);
            SAXReader saxReader = new SAXReader();
            Document products = saxReader.read(catalogFile);
            Element root = products.getRootElement();
            String xpath = format("/products/product[@name='%s' and @size='%s']", itemName, size);
            Element product = (Element) root.selectSingleNode(xpath);

            return Double.parseDouble(product.attributeValue("price"));
        } catch (Exception e) {
            throw new NoSuchProductException(itemName, size, e);
        }
    }

    public void setPath(String path) {
        this.path = path;
    }
}
