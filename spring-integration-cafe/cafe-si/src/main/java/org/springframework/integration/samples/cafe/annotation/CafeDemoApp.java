/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.integration.samples.cafe.annotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.samples.cafe.Cafe;
import org.springframework.integration.samples.cafe.DrinkType;
import org.springframework.integration.samples.cafe.Order;
import org.springframework.integration.samples.cafe.SerialSillyCafe;

public class CafeDemoApp {

	public static void main(String[] args) {


//		TODO TRY OUT CafeDemoPlainJava first

//		AbstractApplicationContext context =
			//new ClassPathXmlApplicationContext("/META-INF/spring/integration/cafeDemo-annotation.xml", CafeDemoApp.class);
//			new AnnotationConfigApplicationContext(CafeDemoConfig.class);
//		Cafe cafe = (Cafe) context.getBean("cafe");
		Cafe cafe = new SerialSillyCafe();
		for (int i = 1; i <= 10; i++) {
			Order order = new Order(i);
			order.addItem(DrinkType.LATTE, 2, false);
			order.addItem(DrinkType.MOCHA, 3, true);
			cafe.placeOrder(order);
		}
		//WE'll have to wait some more for all Deliveries to appear in console
		//context.close();
	}
}
