package cn.orgid.common.threadlocal;

public class UserIdThreadLocal {
	
	
	
	private static ThreadLocal<Long> userId = new ThreadLocal<Long>();
	
	public static void set(Long id){
		
		userId.set(id);
	
	}
	
	public static Long get(){
		
		return userId.get();
		
	}
	 

}
