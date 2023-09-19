package com.visa.prj;

import com.visa.prj.entity.Employee;
import com.visa.prj.service.AppService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringDemoApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(SpringDemoApplication.class, args);

		Employee e = new Employee(22, "Roger"); // not a bean
		ctx.getBean(AppService.class).insert(e);

//		String[] names = ctx.getBeanDefinitionNames();
//		for(String name : names) {
//			System.out.println(name);
//		}
	}

}
