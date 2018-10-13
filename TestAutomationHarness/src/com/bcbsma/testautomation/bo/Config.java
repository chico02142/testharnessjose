package com.bcbsma.testautomation.bo;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Config implements ServletContextListener {
    private static final String ATTRIBUTE_NAME = "config";
    private Properties config = new Properties();

    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            config.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("resources/res/conf.properties"));
            config.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("reseources/res/serviceProperties.properties"));
        } catch (IOException e) {
            System.out.println("Loading config failed\n"+e.getMessage());
        }
        event.getServletContext().setAttribute(ATTRIBUTE_NAME, this);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        // NOOP.
    }

    public static Config getInstance(ServletContext context) {
        return (Config) context.getAttribute(ATTRIBUTE_NAME);
    }

    public String getProperty(String key) {
        return config.getProperty(key);
    }
}
