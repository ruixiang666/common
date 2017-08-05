package cn.orgid.common.jpa;

import java.util.Map;

import org.hibernate.cfg.Environment;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

public class HibernateJpaVendor extends HibernateJpaVendorAdapter {

	@Override
	public Map<String, Object> getJpaPropertyMap() {
		
		Map<String, Object> map = super.getJpaPropertyMap();
		if(map.get(Environment.HBM2DDL_AUTO)!=null){
			if(map.get(Environment.HBM2DDL_AUTO).equals("update")||
					map.get(Environment.HBM2DDL_AUTO).equals("validate")){
				return map;
			}else{
				throw new RuntimeException("不安全的数据库设置:"+map.get(Environment.HBM2DDL_AUTO));
			}
		}
		return map;
	
	}
	
	

	

}
