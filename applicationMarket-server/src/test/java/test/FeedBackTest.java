package test;

import java.util.List;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.techwells.applicationMarket.dao.FeedBackMapper;
import com.techwells.applicationMarket.dao.UserMapper;
import com.techwells.applicationMarket.domain.User;
import com.techwells.applicationMarket.domain.rs.FeedBackImageUserProvinceVos;
import com.techwells.applicationMarket.util.PagingTool;

public class FeedBackTest {
	@Test
	public void test1(){
		ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("spring-mybatis.xml");
		FeedBackMapper feedBackMapper=context.getBean("feedBackMapper",FeedBackMapper.class);
		PagingTool pagingTool=new PagingTool();
		List<FeedBackImageUserProvinceVos> feedBackImageUserProvinceVos=feedBackMapper.selectFeedBackImageUserProvinceVos(pagingTool);
		System.out.println(feedBackImageUserProvinceVos.size());
	}
}
