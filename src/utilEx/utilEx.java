package utilEx;

public class utilEx {

	
	
	public utilEx() {}
	
	public static String arrow(int depth) {
		// 이미지
		String rs = "<img src='./image/arrow.png' width='20px' height='20px'/>";
		// 여백
		String nbsp = "&nbsp;&nbsp;&nbsp;&nbsp;";
		
		String ts ="";
		
		for(int i =0; i < depth; i++) {
			
			ts += nbsp;
		}
		
		return depth==0?"":ts + rs;
		
	}
	
	
}
