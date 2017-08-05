package cn.orgid.common.util.str;

public class CharUtil {
	
    public static String cleanInvalidChar(String input) {  
        StringBuilder retval = new StringBuilder();  
        char ch;  
        
        for (int i = 0; i < input.length(); i++) {  
          ch = input.charAt(i);  
        
          
          if (ch % 0x10000 != 0xffff && // 0xffff - 0x10ffff range step 0x10000  
              ch % 0x10000 != 0xfffe && // 0xfffe - 0x10fffe range  
              (ch <= 0xfdd0 || ch >= 0xfdef) && // 0xfdd0 - 0xfdef  
              (ch > 0x1F || ch == 0x9 || ch == 0xa || ch == 0xd)) {  
        
            retval.append(ch);  
          }  
        }  
        
        return retval.toString();  
      }  
    
    public static String cleanAdjoinDuplicateChar(String val,char c1){
    	
    	if(val==null)
    		return null;
    	StringBuffer bf = new StringBuffer();
    	char[] cs = val.toCharArray();
    	for (char c : cs) {
			if(c!=c1||bf.length()==0||bf.charAt(bf.length()-1)!=c){
				bf.append(c);
			}
		}
    	return bf.toString();
    	
    }
    
    public static void main(String[] args) {
		//System.out.println(cleanAdjoinDuplicateChar("a///121112//wqwq.htm",'/'));
		
		StringBuffer sf = new StringBuffer("112");
		//System.out.println(sf.charAt(2));
	}

}
