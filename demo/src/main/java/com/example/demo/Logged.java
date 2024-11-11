package com.example.demo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME) // sa nu o stearga javac
public @interface Logged {
}
