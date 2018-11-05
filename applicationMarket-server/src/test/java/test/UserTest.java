package test;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.techwells.applicationMarket.dao.UserMapper;
import com.techwells.applicationMarket.domain.User;

public class UserTest {
	@Test
	public void test1(){
		ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("spring-mybatis.xml");
		UserMapper userMapper=context.getBean("userMapper",UserMapper.class);
//		User user=userMapper.selectUser(1);
//		System.out.println(user);
	}
}
