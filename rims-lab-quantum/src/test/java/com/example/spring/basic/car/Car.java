package com.example.spring.basic.car;

import org.springframework.stereotype.Component;

import com.example.spring.basic.ICar;

@Component(value = "myCar1")
public class Car implements ICar {

}
