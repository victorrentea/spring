package com.example.aspects;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
//@TestPropertySource(locations = "alt.yaml")
public class TestSpringCuBeanuriReimplemntate {
    @Autowired
    LogicaCuDependenteExterne target;

    @Test
    public void zaTest() {
        Assert.assertEquals("DUMMY IMPL",target.m());
    }
}


@Service
@Primary
class ExternalSystemClientDoarPtTeste extends ExternalSystemClient {
    public String call() {
        return "dummy impl";
    }

}