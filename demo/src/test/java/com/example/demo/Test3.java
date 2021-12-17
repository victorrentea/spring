package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "custom=aa")
@Execution(ExecutionMode.CONCURRENT)
class Test3 {
	private static final Logger log = LoggerFactory.getLogger(Test3.class);
//@MockBean
//Clasa c;

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
