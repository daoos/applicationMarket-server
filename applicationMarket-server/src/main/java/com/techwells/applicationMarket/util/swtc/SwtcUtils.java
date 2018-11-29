package com.techwells.applicationMarket.util.swtc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;

import com.google.gson.Gson;
import com.techwells.applicationMarket.util.HttpUtils;



/**
 * 井通钱包的工具类
 * @author 陈加兵
 *
 */
public class SwtcUtils {
	public final static String HTTP="https://api.jingtum.com/";   //调用的IP
	
	/**
	 * 获取余额
	 * @param address  钱包的地址
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> getBalance(String address) throws Exception{
		String url=HTTP+"v2/accounts/"+address+"/balances";   //查询的余额的api
		return HttpUtils.doGet(url);
	}
	
	
	/**
	 * 支付,相当于转账
	 * @param payNeedData 支付信息的封装类
	 * @param fromAddress  支付方的钱包地址
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException
	 * 
	 */
	public static Map<String, Object> pay(PayNeedData payNeedData,String fromAddress) throws ClientProtocolException, IOException{
		String jsonParams=new Gson().toJson(payNeedData);  //转换成json字符串的形式
		System.err.println(jsonParams);   //输出json格式的字符串
		String url=HTTP+"v2/accounts/"+fromAddress+"/payments";  //支付的url
		return HttpUtils.doPostReturnMap(url,jsonParams);
	}
	
	
	/**
	 * 查询交易记录
	 * @param hash  交易的hash值
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> getTransaction(String hash) throws Exception{
		String url=HTTP+"v2/transactions/"+hash;  //查询的地址
		return HttpUtils.doGet(url);
	}
	
	
	
	/**
	 * 将指数形式的字符串转换成数值
	 * @param value  
	 * @return
	 */
    public static String formatFloatNumber(Double value) {
        if(value != null){
            if(value.doubleValue() != 0.00){
                java.text.DecimalFormat df = new java.text.DecimalFormat("########");
                return df.format(value.doubleValue());
            }else{
                return "0.00";
            }
        }
        return "";
    }
    
    
    @Test
    public void test6() throws Exception{
    	//1.0939962 E7
    	//1.0940847 E7
    	//1.0941451E7
//    	System.out.println(getTransaction("9119737A7E326E4C11CDAF12C3709712AEDA7618A76E4F1D9336329C728BC941"));
    	System.out.println(getBalance("jNtuYEfWLVB2PfSjhvSur5Asq1yTsXkBge"));
    	
    }
	
	
	
	
	@Test
	public void test4() throws Exception{
		Map<String, Object> map=getTransaction("B21596ABC1332F1CB97C1748CF1A64CC4994FFFA632AF3E42F45CF15AA639D28");
		System.out.println(map);
		Map<String, Object> map2=(Map<String, Object>) map.get("transaction");
//		String date=formatFloatNumber((Double)map2.get("date"));
		System.out.println(formatFloatNumber((Double)map2.get("ledger")));
		System.out.println();
	}
	
	
	
	@Test
	public void testPay() throws ClientProtocolException, IOException{
		PayNeedData data=new PayNeedData();
		data.setClient_id(System.currentTimeMillis()+"");
		data.setSecret("spxkiWNim47uP3jecBjcf2wwHVCDe");
//		data.setSource_address("jNtuYEfWLVB2PfSjhvSur5Asq1yTsXkBge");
		
		Amount amount=new Amount();
		amount.setValue("1000");
		
		PayObject payObject=new PayObject();
		payObject.setDestination("jp2uU9VMjeYSNBp3ggJg2Zxw59twBrvAF2");
		payObject.setSource("jNtuYEfWLVB2PfSjhvSur5Asq1yTsXkBge");
		payObject.setAmount(amount);
		
		data.setPayment(payObject);
		
		String fromAddress="jNtuYEfWLVB2PfSjhvSur5Asq1yTsXkBge";
		
		Map<String, Object> map=pay(data, fromAddress);
		System.out.println((Boolean)map.get("success")==true);
		System.out.println(map.get("result").equals("tesSUCCESS"));
		System.out.println(map.get("fee") instanceof Double);
		System.out.println(map.get("date"));
		
	}
	
	
	
	@Test
	public void test1() throws ClientProtocolException, IOException{
		String name="name:'陈加兵'";
		System.out.println(HttpUtils.doPostReturnMap("http://developer.jingtum.com/api2_doc.html#id1", ""));
		
	}
	
	
	public static void main(String[] args) throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		String address="jNtuYEfWLVB2PfSjhvSur5Asq1yTsXkBge";
		List<Map<String, Object>> p=new ArrayList<Map<String,Object>>();
		map=getBalance(address);
		p=(List<Map<String, Object>>) map.get("balances");
		
		System.out.println(p.get(0).get("value"));
		System.out.println((Boolean)map.get("success")==true);
	}
	
	
	@Test
	public void test2() throws Exception{
		String url="https://api.jingtum.com/v2/accounts/jNtuYEfWLVB2PfSjhvSur5Asq1yTsXkBge/payments";
		System.out.println(HttpUtils.doGet(url));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
