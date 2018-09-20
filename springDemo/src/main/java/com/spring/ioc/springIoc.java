package com.spring.ioc;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class springIoc {
	private Map<String, Object> beanMap = new HashMap<>();
	
	public springIoc(String location) throws Exception{
		loadBean(location);
	}
	
	public Object getBean(String name){
		Object bean = beanMap.get(name);
		
		if(bean == null){
			throw new IllegalArgumentException("there is no bean with name " + name);
		}
		
		return bean;
	}
	
	private void loadBean(String location) throws Exception {
		// TODO Auto-generated method stub
		//加载XMl配置文件
		InputStream input = new FileInputStream(location);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(input);
		Element root = doc.getDocumentElement();
		NodeList nodes = root.getChildNodes();
		
		//遍历<bean>标签
		for(int i = 0;i < nodes.getLength();i++){
			Node node = nodes.item(i);
			if(node instanceof Element){
				Element ele = (Element) node;
				String id = ele.getAttribute("id");
				String className = ele.getAttribute("class");
				//加载beanClass
				Class beanClass = null;
				try{
					beanClass = Class.forName(className);	
				}catch(ClassNotFoundException e){
					e.printStackTrace();
					return;
				}
				//创建bean
				Object bean = beanClass.newInstance();
				
				//遍历 <property> 标签
				NodeList propertyNodes = ele.getElementsByTagName("property");
				for(int j = 0;j < propertyNodes.getLength();j++){
					Node propertyNode = propertyNodes.item(j);
					if(propertyNode instanceof Element){
						Element propertyElement = (Element) propertyNode;
						String name = propertyElement.getAttribute("name");
						String value = propertyElement.getAttribute("value");
						
						//利用反射将bean相关字段访问权限设为可访问
						Field declaredField = bean.getClass().getDeclaredField(name);
						declaredField.setAccessible(true);
						
						if(value != null && value.length() > 0){
							//将属性值填充到相关字段
							declaredField.set(bean,value);
						}else{
							String ref = propertyElement.getAttribute("ref");
							if(ref == null || ref.length() == 0){
								throw new IllegalArgumentException("ref config error");
							}
							//将引用填充到相关字段
							declaredField.set(bean, getBean(ref));
						}
						//将bean注册到bean容器中
						registerBean(id,bean);
					}
				}
			}
		}
		
	}

	private void registerBean(String id, Object bean) {
		beanMap.put(id, bean);
		
	}
}
