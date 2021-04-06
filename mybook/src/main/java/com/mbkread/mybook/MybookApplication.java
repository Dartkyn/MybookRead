package com.mbkread.mybook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.servlet.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.HttpPutFormContentFilter;

@SpringBootApplication
public class MybookApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybookApplication.class, args);
	}
	@SuppressWarnings("deprecation")
	@Bean
	public Filter httpPutFormContentFilter() {
		return new HttpPutFormContentFilter();
	}

}
