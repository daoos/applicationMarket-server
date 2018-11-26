package com.techwells.applicationMarket.util.moac;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Test;

import com.google.gson.Gson;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.googlecode.jsonrpc4j.spring.rest.JsonRpcRestClient;

/**
 * MOAC钱包的工具类
 * @author 陈加兵
 *
 */
public class MoacUtils {
	private final static String SERVER_ADDRESS="http://47.92.101.153:8545"; //服务器的地址
	
	private final static String MC_GETBALANCE="mc_getBalance";   //查询余额的方法
	
	private final static String MC_GETTRANSACTIONBYHASH="mc_getTransactionByHash";  //获取交易信息
	
	/**
	 * 获取钱包的余额
	 * @param address 钱包的地址
	 * @return
	 * @throws Throwable 
	 */
	public static String getBalance(String address) throws Throwable{
		JsonRpcHttpClient client=new JsonRpcHttpClient(new URL(SERVER_ADDRESS));
		String[] params=new String[2];    //封装请求参数
		params[0]=address;  //钱包的地址  
		params[1]="latest";   //获取最新的余额信息
		String b=client.invoke(MC_GETBALANCE, params,String.class);  //返回十六进制的余额
		return String.valueOf(Long.parseLong(b.substring(2),16)/1000000000000000000.0);
	}
	
	
	/**
	 * 根据hash获取交易的详细信息
	 * @throws MalformedURLException 
	 * @param hash  交易的hash值
	 * 							0xe953d6bbccab1977ae7dff6a4094f3dbbd774fb60c6b214a294c17a8c5c56156
	 */
	public static TransactionDetail getTransactionDetail(String hash) throws Throwable{
		JsonRpcHttpClient client=new JsonRpcHttpClient(new URL(SERVER_ADDRESS));
		Object[] params=new Object[1];    //封装请求参数
		params[0]=hash;
		TransactionDetail detail=client.invoke(MC_GETTRANSACTIONBYHASH, params,TransactionDetail.class);
		if (detail!=null) {
			detail.setBlockNumber(String.valueOf(Integer.parseInt(detail.getBlockNumber().substring(2),16)));
			detail.setValue(String.valueOf(new BigInteger(detail.getValue().substring(2), 16).longValue()/1000000000000000000.0));
			detail.setGasPrice(String.valueOf(Integer.parseInt(detail.getGasPrice().substring(2),16)));
			detail.setGas(String.valueOf(Integer.parseInt(detail.getGas().substring(2),16)));
		}
		return detail;
	}
	
	
	//16345785d8a0000
	@Test
	public void test1() throws Throwable{
		TransactionDetail detail=getTransactionDetail("0xaba7ed02fd4214dc4893c90a346bd315845662109bf0b99b1aef87c0bf1cc436");
		System.out.println(detail.getValue());
		System.out.println((Double)(Long.parseLong(detail.getGas())/1000000000000000000.0*Long.parseLong(detail.getGasPrice())));
		System.out.println(detail.getGasPrice());
	}
	
	
	@Test
	public void test2()throws Throwable{
		JsonRpcHttpClient client=new JsonRpcHttpClient(new URL(SERVER_ADDRESS));
		Object[] params=new Object[1];    //封装请求参数
//		params[0]=hash;
		String a=client.invoke("mc_gasPrice",params, String.class);
		System.out.println(String.valueOf(new BigInteger(a.substring(2),16).longValue()/1000000000000000000.0*100000000));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
