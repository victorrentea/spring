package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Execution(ExecutionMode.CONCURRENT)
class Test1 {
	private static final Logger log = LoggerFactory.getLogger(Test1.class);

	@Test
	public void first() throws Exception{
		log.info("start");
		Thread.sleep(500);
		log.info("end");
	}
	@Test
	public void second() throws Exception{
		log.info("start");
		Thread.sleep(500);
		log.info("end");
	}

}
