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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
//@TestPropertySource(locations = "alt.yaml")
public class TestSpringCuBeanuriMockuite {
    @Autowired
    LogicaCuDependenteExterne target;
    @MockBean
    ExternalSystemClient mockClient;

    @Test
    public void zaTest() {
        Mockito.when(mockClient.call()).thenReturn("a");
        Assert.assertEquals("A",target.m());
    }
}
