package com.example.demo;

import java.util.Random;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extensions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.service.interf.UserService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AllTests {
	@Autowired
	UserService users;
	
	@Test
	@Rollback(value=true)
	@Transactional
	void test()
	{
		users.submitOrder("lzq2", "gpu1660", new Random().nextInt(30));
	}
}
