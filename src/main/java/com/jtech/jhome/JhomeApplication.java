package com.jtech.jhome;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
@EnableScheduling
public class JhomeApplication extends SpringBootServletInitializer {

	private static final Logger log = LoggerFactory.getLogger(JhomeApplication.class);

	public static void main(String[] args) {
		//System.setProperty("server.servlet.context-path", "/jh");
		SpringApplication.run(JhomeApplication.class, args);
	}
}
