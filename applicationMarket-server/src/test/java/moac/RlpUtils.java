package moac;

public class RlpUtils {
	
	public static String rlp_encode(Object input) throws Exception{
		if (input instanceof String) { //如果是字符串
			if (((String) input).length()==1&&((String)input).charAt(0)<128) {
				return (String) input;
			}else {
				return encode_length(((String) input).length(), 128)+input;
			}
		}else if (input.getClass().isArray()) {  //如果是数组
			String output="";
			for (Object object  : (Object[])input) {
				output+=rlp_encode(object);
			}
			return encode_length(output.length(),192) + output;
		}else {
			return null;
		}
	}
	
	
	public static String encode_length(int L,int offest) throws Exception{
		if (L<56) {
			char a=(char)(L+offest);
			return a+"";
		}else if (L<256*8) {
			String  BL = to_binary(L);
			return (char)(BL.length() + offest + 55) + BL;
		}else {
			throw new Exception("异常");
		}
	}
	
	public static String to_binary(int x){
		if (x==0) {
			return "";
		}else {
			return to_binary((x/256))+(char)(x%256);
		}
	}
	
	
	
}
