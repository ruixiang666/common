package cn.orgid.common.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class BeansFactory implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {

		BeansFactory.applicationContext = applicationContext;

	}

	public static ApplicationContext getApplicationContext() {

		return applicationContext;

	}

	public static <T> T get(Class<T> clazz) {

		return applicationContext.getBean(clazz);
		
	}

}
