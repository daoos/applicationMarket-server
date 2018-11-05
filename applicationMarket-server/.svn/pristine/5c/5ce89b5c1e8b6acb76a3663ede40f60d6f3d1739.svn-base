package test;

import java.util.Date;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.techwells.applicationMarket.dao.NoticeMapper;
import com.techwells.applicationMarket.dao.UserMapper;
import com.techwells.applicationMarket.domain.Notice;
import com.techwells.applicationMarket.domain.User;
import com.techwells.applicationMarket.domain.rs.NoticeAdmin;

public class NoticeTest {
	@Test
	public void test1(){
		ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("spring-mybatis.xml");
		NoticeMapper noticeMapper=context.getBean("noticeMapper",NoticeMapper.class);
		Notice notice=new Notice();
		notice.setPublishId(1);
		notice.setContent("第一条公告");
		notice.setHtmlContent("<h1>第一条公告</h1>");
		notice.setCreateDate(new Date());
		noticeMapper.insertSelective(notice);
	}
	
	
	@Test
	public void test2(){
		Integer a=10;
//		System.out.println(.equals(a));
	}
}
