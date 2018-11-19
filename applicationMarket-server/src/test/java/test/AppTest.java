package test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Spring;

import org.apache.bcel.generic.NEW;
import org.apache.ibatis.javassist.expr.NewArray;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.web3j.crypto.ECKeyPair;

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
import com.techwells.applicationMarket.service.QuartzService;
import com.techwells.applicationMarket.service.RecommendTypeService;
import com.techwells.applicationMarket.util.HanyuPinyinUtils;
import com.techwells.applicationMarket.util.PagingTool;
import com.techwells.applicationMarket.util.swtc.SwtcUtils;

public class AppTest {
	@Test
	public void test1() throws Exception{
		Map<String, Object> transaction = SwtcUtils.getTransaction("584AF0CB4D97B0EFCF4FED7CF4ACCC4480B865BD72D525D2B06D8B0D230E8E05");
		System.out.println((Boolean)transaction.get("success")==true);
		Map<String, Object> map2=(Map<String, Object>) transaction.get("transaction");
//		System.out.println(transaction.get("transaction"));
		//这里由于程序执行太快，没有不能及时返回区块的高度
		String block=SwtcUtils.formatFloatNumber((Double)map2.get("ledger"));   //获取区块的高度
		System.out.println(block);
		
	}
	
	@Test
	public void test2(){
		System.out.println(new BigInteger("bbeeec3ce5279b197efc2a6ae2598df0bb461bff6ee43e702e5c8d1342fdee9a", 16));
	}
	
	@Test
	public void test3() throws IOException{
		ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("spring-mybatis.xml");
		QuartzService quartzService=context.getBean("quartzServiceImpl",QuartzService.class);
		quartzService.getMoacTrasactionDetail();
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