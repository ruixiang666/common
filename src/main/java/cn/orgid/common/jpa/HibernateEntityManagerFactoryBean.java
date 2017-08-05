package cn.orgid.common.jpa;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

public class HibernateEntityManagerFactoryBean extends LocalContainerEntityManagerFactoryBean{

	

	@Override
	public void setJpaProperties(Properties jpaProperties) {
		
		//禁用属性设置
		return;
		
	}
	
	public void setJpaPropertyMap(Map<String, ?> jpaProperties) {
		
		//禁用属性设置
		return;
		
	}
	
	

}
