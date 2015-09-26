package com.restbucks.ordering.rest;

import com.google.common.io.Resources;
import org.springframework.core.io.DefaultResourceLoader;

import java.io.IOException;

import static com.google.common.base.Charsets.UTF_8;

public class TestUtils {
    public static String readFileAsString(String path) throws IOException {
        return Resources.toString(new DefaultResourceLoader().getResource(path).getURL(), UTF_8);
    }
}