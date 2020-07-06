package com.arademia.aqar;

import com.arademia.aqar.storage.StorageProperties;
import com.arademia.aqar.storage.StorageService;
import com.arademia.aqar.util.SupportingTools;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class AqarApplication {

	public static void main(String[] args) {

		SpringApplication.run(AqarApplication.class, args);
	}


	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}

}
