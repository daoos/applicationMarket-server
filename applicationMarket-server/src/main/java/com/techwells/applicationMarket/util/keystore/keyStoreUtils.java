package com.techwells.applicationMarket.util.keystore;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import org.aspectj.apache.bcel.generic.InstructionConstants.Clinit;
import org.junit.Test;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Hash;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.methods.response.NewAccountIdentifier;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;


/**
 * keystore文件的工具类
 * @author 陈加兵
 */
public class keyStoreUtils {
	
	/**t
	 * 创建一个钱包，生成keystore文件
	 * @param path 钱包的路径（不包括文件名称）
	 * @param password 钱包的密码
	 * @throws Exception
	 */
	private static void creatAccount(String path,String password) throws Exception {
	    String walletFileName="";//文件名
	    //钱包文件保持路径，请替换位自己的某文件夹路径
	    walletFileName = WalletUtils.generateNewWalletFile(password, new File(path), false);
	    //WalletUtils.generateFullNewWalletFile("password1",new File(walleFilePath1));
	    //WalletUtils.generateLightNewWalletFile("password2",new File(walleFilePath2));
	}
	
	
	/**
	 * 获取钱包地址
	 * @param path  钱包的路径（包括文件的名称）
	 * @param password   钱包的密码
	 * @return
	 * @throws Exception
	 */
	public static String getAddress(String path,String password) throws Exception{
		Credentials credentials = WalletUtils.loadCredentials(password, path);
		String address = credentials.getAddress();
		ECKeyPair ecKeyPair=credentials.getEcKeyPair();
		System.out.println("0x"+ecKeyPair.getPrivateKey().toString(16));
		return address;
	}
	
	/**
	 * 获取钱包的私钥
	 * @param path
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static String getPrivateKey(String path,String password) throws Exception{
		Credentials credentials = WalletUtils.loadCredentials(password, path);
		BigInteger privateKey = credentials.getEcKeyPair().getPrivateKey();
		return privateKey.toString();
	}
	
	
	/**
	 * 加载钱包
	 * @throws Exception
	 */
	@Test
	public void loadWallet() throws Exception {
	    String walleFilePath="C:\\images\\UTC--2018-10-23T04-00-05.917000000Z--3dfdaaecc881d878b0bd436145e3fb548054b6b0.json";
	    String passWord="12345678";
	    Credentials credentials = WalletUtils.loadCredentials(passWord, walleFilePath);
	    
	    //连接方式1：使用infura 提供的客户端
	    Web3j web3j = Web3j.build(new HttpService("https://rinkeby.infura.io/zmd7VgRt9go0x6qlJ2Mk"));// TODO: 2018/4/10 token更改为自己的
	     
	    //测试是否连接成功
	    String web3ClientVersion = web3j.web3ClientVersion().send().getWeb3ClientVersion();
	    
	    if (web3j == null) return;
	    String address = "0x4a1a050e9e657c19e9a2678df202fc72a12f6afb";//等待查询余额的地址
	    //第二个参数：区块的参数，建议选最新区块
	    EthGetBalance balance = web3j.ethGetBalance(address, DefaultBlockParameter.valueOf("latest")).send();
	    //格式转化 wei-ether
	    String blanceETH = Convert.fromWei(balance.getBalance().toString(), Convert.Unit.ETHER).toPlainString().concat(" ether");
	    System.out.println(blanceETH);
	    
	}
	
	
	@Test
	public void getAddress() throws Exception{
		String path="C:\\images\\UTC--2018-11-16T10-33-32.48000000Z--dfc04431e03652a7f5abbf074aa63e7db51baed2.json";
		String password="12345678";
		System.out.println(getPrivateKey(path, "12345678"));
	}
	
	
	@Test
	public void createWallet() throws Exception{
		String path="C:\\images";
		File file=new File(path);
		String password="12345678";
//		this.creatAccount(path, password);
		ECKeyPair ecKeyPair=new ECKeyPair(new BigInteger("1655665"), new BigInteger("1665156151"));
		WalletUtils.generateWalletFile("12345678", ecKeyPair, file, false);
	}
	
	@Test
	public void load() throws Exception{
		this.loadWallet();
	}

	
	@Test
	public void test4() throws Throwable{
		//设置需要的矿工费
        BigInteger GAS_PRICE = BigInteger.valueOf(22_000_000_000L);
        BigInteger GAS_LIMIT = BigInteger.valueOf(4_300_000);

        //调用的是kovan测试环境，这里使用的是infer这个客户端
        Web3j web3j = Web3j.build(new HttpService("http://47.92.101.153:8545"));
        //转账人账户地址
        String ownAddress = "0x4a1a050e9e657c19e9a2678df202fc72a12f6afb";
        //被转人账户地址
        String toAddress = "0x842979507ca3dbb94392fb076f2555c7ff483d78";
        //转账人私钥
        Credentials credentials = Credentials.create("bbeeec3ce5279b197efc2a6ae2598df0bb461bff6ee43e702e5c8d1342fdee9a");

        //getNonce（这里的Nonce我也不是很明白，大概是交易的笔数吧）
        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
                ownAddress, DefaultBlockParameterName.LATEST).sendAsync().get();
        BigInteger nonce = ethGetTransactionCount.getTransactionCount();
        
        System.err.println(nonce);
        
        
//      JsonRpcHttpClient client=new JsonRpcHttpClient(new URL("http://47.92.101.153:8545"));
//		String[] params=new String[2];    //封装请求参数
//		params[0]="0x0bd3c0b16dc0d210cc8b42345d089ddafac9c55c";  //钱包的地址  
//		params[1]="latest";   //获取最新的余额信息
//		String b=client.invoke("mc_getTransactionCount", params,String.class);  //返回十六进制的余额
        
        //创建交易，这里是转0.5个以太币
        BigInteger value = Convert.toWei("0.1", Convert.Unit.ETHER).toBigInteger();
        
        RawTransaction rawTransaction = RawTransaction.createEtherTransaction(
                BigInteger.valueOf(0), GAS_PRICE, GAS_LIMIT, toAddress, BigInteger.valueOf(1));

        //签名Transaction，这里要对交易做签名
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        
//        
//      JsonRpcHttpClient client=new JsonRpcHttpClient(new URL("http://47.92.101.153:8545"));
//		Object[] params=new Object[2];    //封装请求参数
//		params[0]="0x4a1a050e9e657c19e9a2678df202fc72a12f6afb";  //钱包的地址  
//		params[1]=Numeric.toHexString(signedMessage);   //获取最新的余额信息
//		String b=client.invoke("mc_sign", params,String.class);  //返回十六进制的余额
        
//        String hexValue = ;
        System.out.println(Numeric.toHexString(Hash.sha256(signedMessage)));
//        
//        System.out.println(hexValue.length());
        
        
        
//        JsonRpcHttpClient client=new JsonRpcHttpClient(new URL("http://47.92.101.153:8545"));
//		String[] params=new String[1];    //封装请求参数
//		params[0]="0xf86e12808504a817c800829c4094dc74dfdde12cf553d39dcf22d7d51258f4f76fe3865af3107a400000808081eaa0f9a2008d6773a4aa72f0c4474f8202337163f017aa3667b5fba7456e80e23936a022f7e74ba0c0b041fc2b04c0dbf5cd6c2deebe5e3aeb6326e7699470b59e8cb2";  //钱包的地址  
////		params[1]="lat  est";   //获取最新的余额信息
//		
//		
//		
//        String hash=client.invoke("mc_sendRawTransaction", params,String.class);
//        System.out.println(hash);
        

        //发送交易
//        EthSendTransaction ethSendTransaction =
//                web3j.ethSendRawTransaction(hexValue).sendAsync().get();
//        String transactionHash = ethSendTransaction.getTransactionHash();
//
//        //获得到transactionHash后就可以到以太坊的网站上查询这笔交易的状态了
//        System.out.println(transactionHash);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
