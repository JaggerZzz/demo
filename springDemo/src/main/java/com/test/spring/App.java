package com.test.spring;

import org.junit.Test;

import com.spring.bean.Car;
import com.spring.bean.Wheel;
import com.spring.ioc.springIoc;

/**
 * Hello world!
 *
 */
public class App 
{
    @Test
    public void getBean() throws Exception{
    	String location = springIoc.class.getClassLoader().getResource("spring-test.xml").getFile();
    	springIoc bf = new springIoc(location);
    	Wheel wheel = (Wheel) bf.getBean("wheel");
    	System.out.println(wheel);
    	Car car = (Car) bf.getBean("car");
    	System.out.println(car);
    }
}
