package com.spring.bean;

import com.spring.bean.Wheel;

public class Car {
	private String name;
	private String length;
	private String width;
	private String height;
	private Wheel wheel;
	
	public Car(){
		
	}
	
	public String setname(){
		return name;
	}
	public void getname(String name){
		this.name = name;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public Wheel getWheel() {
		return wheel;
	}
	public void setWheel(Wheel wheel) {
		this.wheel = wheel;
	}
	
	
	@Override
	public String toString() {
		return "Car [name=" + name + ", length=" + length + ", width=" + width + ", height=" + height + ", wheel="
				+ wheel + "]";
	}
	
	
	
}
