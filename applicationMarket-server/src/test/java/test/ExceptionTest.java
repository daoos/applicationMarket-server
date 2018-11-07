package test;

import org.apache.log4j.Logger;
import org.junit.Test;

public class ExceptionTest {
	private Logger logger=Logger.getLogger(ExceptionTest.class);
	
	@Test
	public void test1(){
		try {
			int i=1/0;
		} catch (Exception e) {
			//使用这种格式记录异常信息输出到日志中的内容和e.printStackTrace()是一样，能够很快的定位到指定的地方
			logger.error("异常错误信息",e);   
		}
	}
	
	
}
