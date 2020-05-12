package com.example.aspects;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ProxyApp.class)
public class ExpensiveOpsSpringTest {
    @Autowired
    ExpensiveOps target;
    @Test
    public void testCache() {
        target.isPrime(10_000_169);
        long t0 = System.currentTimeMillis();
        target.isPrime(10_000_169);
        long t1 = System.currentTimeMillis();
        Assert.assertTrue(t1-t0 < 30);
    }
}
