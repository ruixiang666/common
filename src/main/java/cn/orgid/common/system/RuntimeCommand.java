package cn.orgid.common.system;

import java.io.IOException;

public class RuntimeCommand {
	
	public static void execute(String command){
		try {
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
