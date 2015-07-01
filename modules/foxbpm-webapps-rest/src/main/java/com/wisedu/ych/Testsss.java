package com.wisedu.ych;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Testsss {

	public static void main(String[] args) {
		RestTemplate tem = new RestTemplate();
		ResponseEntity<String> aa = tem.getForEntity("http://127.0.0.1:8080/foxbpm-webapps-rest/service/runtime/tasks", String.class);
		String b = aa.getBody();
		System.out.println(b);
	}
}
