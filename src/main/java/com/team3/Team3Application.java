package com.team3;

import com.team3.global.service.DatabaseTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class Team3Application {

	@Autowired
	private DatabaseTestService databaseTestService;

	public static void main(String[] args) {
		SpringApplication.run(Team3Application.class, args);
	}

	@PostConstruct
	public void init() {
		// 애플리케이션이 시작된 후 데이터베이스 연결을 테스트합니다.
		databaseTestService.testConnection();
	}
}
