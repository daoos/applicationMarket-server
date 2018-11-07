package com.techwells.applicationMarket.util.moac;

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
	
	private final static String MC_BLOCAKNUMBER="mc_blockNumber";  //获取区块的高度
	
	private final static String MC_SENDTRANSCATION="mc_sendTransaction";   //转账
	
	
	//返回区块的高度 mc_blockNumber
	
	//mc_getBlockTransactionCountByHash  mc_getBlockByHash
	
	//转账     mc_sendTransaction
	
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
	 * 获取区块的高度
	 * @param address
	 * @return
	 * @throws Throwable
	 */
	public static String getBlock(String address) throws Throwable{
		JsonRpcHttpClient client=new JsonRpcHttpClient(new URL(SERVER_ADDRESS));
		String[] params=new String[2];    //封装请求参数
//		params[0]=address;  //钱包的地址  
//		params[1]="latest";   //获取最新的余额信息
		String b=client.invoke(MC_BLOCAKNUMBER, params,String.class);  //返回十六进制的余额
		
		return String.valueOf(Long.parseLong(b.substring(2),16));
	}
	
	
	private static String sendTransaction(String from,String to,String value,String data) throws Throwable{
		Params params=new Params();
		params.setFrom(from);
		params.setTo(to);
//		params.setValue("0x"+Long.toHexString(Long.parseLong(value)));
		params.setValue("0x1");
		params.setData(data);
//		String json=new Gson().toJson(params);
//		System.err.println(json);
		Object[] p={params};
		JsonRpcHttpClient client=new JsonRpcHttpClient(new URL(SERVER_ADDRESS));
		String b=client.invoke(MC_SENDTRANSCATION, p,String.class);  //返回十六进制的余额
		return b;
	}
	
	
	@Test
	public void test4() throws Throwable{
		System.out.println(getBalance("0x4a1a050e9e657c19e9a2678df202fc72a12f6afb"));
	}
	
	
	
	
	@Test
	public void test3() throws Throwable{
		JsonRpcHttpClient client=new JsonRpcHttpClient(new URL(SERVER_ADDRESS));
		String[] params=new String[2];    //封装请求参数
		params[0]="0x4a1a050e9e657c19e9a2678df202fc72a12f6afb";  //钱包的地址  
		params[1]="0xdeadbeaf";   //获取最新的余额信息
		String b=client.invoke("mc_sign", params,String.class);  //返回十六进制的余额
	}
	
	
	@Test
	public void test2() throws Throwable{
//		JsonRpcHttpClient client=new JsonRpcHttpClient(new URL(SERVER_ADDRESS));
//		String[] params=new String[2];    //封装请求参数
//		params[0]="0x4a1a050e9e657c19e9a2678df202fc72a12f6afb";  //钱包的地址  
//		params[1]="latest";   //获取最新的余额信息
//		String b=client.invoke(MC_GETBALANCE, params,String.class);  //返回十六进制的余额
		
//		String address="0x4a1a050e9e657c19e9a2678df202fc72a12f6afb";
		String address="0x3dfdaaecc881d878b0bd436145e3fb548054b6b0";
//		System.out.println(getBalance(address));
		System.out.println(getBalance(address));  //1112014
		
	}
	
	
	
	@Test
	public void test1() throws Throwable{
		//0x842979507ca3dbb94392fb076f2555c7ff483d78
		//0x4a1a050e9e657c19e9a2678df202fc72a12f6afb
//		System.out.println(getBalance("0x4a1a050e9e657c19e9a2678df202fc72a12f6afb"));
//		System.out.println("".substring(2));
//		System.out.println(Long.toString(0xd098));
//		System.out.println(Long.toString(0x12f8b3a319c000,16));
//		System.out.println(Long.toHexString(5340000000000000L));
//		System.out.println(Long.parseLong("12f8b3a319c000", 16));
		
		System.out.println(getBlock("0x4a1a050e9e657c19e9a2678df202fc72a12f6afb"));
		
		
		
		
		
		
		
	}
	
}
