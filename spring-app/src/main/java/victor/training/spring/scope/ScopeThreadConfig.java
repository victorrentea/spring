/*
 * Copyright 2002-2019 the original author or authors.
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

package victor.training.spring.scope;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.beans.factory.config.Scope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.NamedThreadLocal;
import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class ScopeThreadConfig {

	@Bean
	public static CustomScopeConfigurer defineThreadScope() {
		CustomScopeConfigurer configurer = new CustomScopeConfigurer();
		// WARNING: Can leaks memory if data remains on thread. Prefer 'request' scope or read here: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/support/SimpleThreadScope.html
		// TODO finally { ClearableThreadScope.clearAllThreadData(); }
		configurer.addScope("thread", new ClearableThreadScope());
		return configurer;
	}
	/**
	 * Silly but correct implementation of Thread scope that supports clearing of all data at the end.
	 * @see org.springframework.context.support.SimpleThreadScope
	 */
	public static class ClearableThreadScope implements Scope {
		private static final Logger log = LoggerFactory.getLogger(ClearableThreadScope.class);

		private static final ThreadLocal<Map<String, Object>> threadScope =
						new NamedThreadLocal<Map<String, Object>>("ClearableThreadScope") {
							@Override
							protected Map<String, Object> initialValue() {
								return new HashMap<>();
							}
						};

		public static void clearAllThreadData() {
			threadScope.remove();
		}

		@Override
		public Object get(String name, ObjectFactory<?> objectFactory) {
			Map<String, Object> scope = threadScope.get();
			Object scopedObject = scope.get(name);
			if (scopedObject == null) {
				scopedObject = objectFactory.getObject();
				scope.put(name, scopedObject);
			}
			return scopedObject;
		}

		@Override
		@Nullable
		public Object remove(String name) {
			Map<String, Object> scope = threadScope.get();
			return scope.remove(name);
		}

		@Override
		public void registerDestructionCallback(String name, Runnable callback) {
			callback.run();
			threadScope.get().remove(name);
		}

		@Override
		@Nullable
		public Object resolveContextualObject(String key) {
			return null;
		}

		@Override
		public String getConversationId() {
			return Thread.currentThread().getName();
		}

	}

}