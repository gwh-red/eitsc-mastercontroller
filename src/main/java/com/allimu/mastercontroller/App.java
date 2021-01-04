package com.allimu.mastercontroller;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.allimu.mastercontroller.util.HessianClient;



/**
 * Hello world!
 */
public class App{
	
    public static void main( String[] args ){
    	// 加载spirng配置文件
        @SuppressWarnings({ "resource", "unused" })
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-netty.xml");
        
        // 项目启动时注入HessianProxyFactoryBean  (2020-10-17增加)
        @SuppressWarnings("resource")
		ApplicationContext application = new AnnotationConfigApplicationContext(HessianClient.class);
        application.getBean("hessianProxyFactoryBean");
    }
    
}
