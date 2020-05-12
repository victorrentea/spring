package com.example.demo.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BusinessLogicServiceTest {
    @Mock
    private AltaClasa alta;
    @InjectMocks
    private BusinessLogicService target;

    @Test
    public void faceUpper() {
        Mockito.when(alta.m()).thenReturn("tataie");
        String actual = target.m();
        Assert.assertEquals("TATAIE", actual);
    }
}
