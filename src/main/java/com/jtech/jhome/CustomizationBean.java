package com.jtech.jhome;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class CustomizationBean implements
        WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    @Value("${jhome.media.path}")
    private String mediaPath;

    @Override
    public void customize(ConfigurableServletWebServerFactory container) {
       // container.setPort(8080);
       // container.setContextPath("/jh");
        container.setDocumentRoot(new File(mediaPath));
    }
}
