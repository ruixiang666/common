package cn.orgid.common.groovy;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import groovy.lang.GroovyClassLoader;

public class GroovyClassUtil {
	
	

	static Map<String, Class<?>> map = new ClassMap<String, Class<?>>();

	public static Class<?> getClass(String text,String mdkey) {

		//String key = MD5.md5(text);
		if (map.containsKey(mdkey)) {
			return map.get(mdkey);
		}
		GroovyClassLoader classLoader = null;
		try {
			classLoader = new GroovyClassLoader(Thread.currentThread()
					.getContextClassLoader());
			Class<?> clazz = classLoader.parseClass(text);
			map.put(mdkey, clazz);
			return clazz;
		}catch(Throwable e){
			e.printStackTrace();
			return null;
		}finally{
			if (classLoader != null) {
				try {
					classLoader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
	
	

	public static class ClassMap<V, K> extends LinkedHashMap<V, K>{
		

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private static final int MAX_ENTRIES = 1000;

		@Override
		protected boolean removeEldestEntry(Entry<V, K> eldest) {
			// TODO Auto-generated method stub
			 return size() > MAX_ENTRIES;
		}

		
	}
}
