package com.ece.parkisonditributed.back;

import feign.Feign;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Component
public class ParkinsonBackApplication {

	private static String targetUrl;
	@Value("${app.targetUrl}")
	public void settargetUrl(String targetUrl) { ParkinsonBackApplication.targetUrl = targetUrl; }


	public static void main(String[] args) {
		SpringApplication.run(ParkinsonBackApplication.class, args);
	}

}
