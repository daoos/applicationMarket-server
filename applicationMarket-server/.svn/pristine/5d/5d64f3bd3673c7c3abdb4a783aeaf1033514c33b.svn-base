package test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Spring;

import org.apache.ibatis.javassist.expr.NewArray;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.google.gson.Gson;
import com.techwells.applicationMarket.dao.AppMapper;
import com.techwells.applicationMarket.dao.BannerMapper;
import com.techwells.applicationMarket.dao.WalletDetailMapper;
import com.techwells.applicationMarket.domain.App;
import com.techwells.applicationMarket.domain.Banner;
import com.techwells.applicationMarket.domain.RecommendType;
import com.techwells.applicationMarket.domain.UserApp;
import com.techwells.applicationMarket.domain.WalletDetail;
import com.techwells.applicationMarket.domain.rs.AppAndVersionVos;
import com.techwells.applicationMarket.domain.rs.AppVersionImageVos;
import com.techwells.applicationMarket.domain.rs.WalletDetailVos;
import com.techwells.applicationMarket.service.RecommendTypeService;
import com.techwells.applicationMarket.util.HanyuPinyinUtils;
import com.techwells.applicationMarket.util.PagingTool;

public class AppTest {
	@Test
	public void test1(){
		ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("spring-mybatis.xml");
		BannerMapper bannerMapper=context.getBean("bannerMapper",BannerMapper.class);
		Banner banner=bannerMapper.countBannerByPageAndLocation(1, 1, 1);
		System.out.println(banner);
	}
	
	
	@SuppressWarnings("restriction")
	@Test
	public void test2(){
		BASE64Encoder encoder = new BASE64Encoder();
		String a=encoder.encode("spxkiWNim47uP3jecBjcf2wwHVCDe".getBytes());// 返回Base64编码过的字节数组字符
		System.out.println(a);
	}
	
	@SuppressWarnings("restriction")
	@Test
	public void test3() throws IOException{
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] b=decoder.decodeBuffer("c3B4a2lXTmltNDd1UDNqZWNCamNmMnd3SFZDRGU=");
		System.out.println(new String(b));
	}
	

	
	
	
}


class A{
	private BigDecimal price;

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
}