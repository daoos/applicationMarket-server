package com.techwells.applicationMarket.util.keystore;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

import org.junit.Test;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
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


/**
 * keystore文件的工具类
 * @author 陈加兵
 */
public class keyStoreUtils {
	
	/**
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
		String path="C:\\images\\UTC--2018-10-23T04-00-05.917000000Z--3dfdaaecc881d878b0bd436145e3fb548054b6b0.json";
		String password="12345678";
		System.out.println(getAddress(path,password));
	}
	
	
	@Test
	public void createWallet() throws Exception{
		String path="C:\\images";
		String password="12345678";
		this.creatAccount(path, password);
	}
	
	@Test
	public void load() throws Exception{
		this.loadWallet();
	}

	
	@Test
	public void test4() throws Exception{
		//设置需要的矿工费
        BigInteger GAS_PRICE = BigInteger.valueOf(22_000_000_000L);
        BigInteger GAS_LIMIT = BigInteger.valueOf(4_300_000);

        //调用的是kovan测试环境，这里使用的是infura这个客户端
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

        //创建交易，这里是转0.5个以太币
        BigInteger value = Convert.toWei("0.1", Convert.Unit.ETHER).toBigInteger();
        RawTransaction rawTransaction = RawTransaction.createEtherTransaction(
                nonce, GAS_PRICE, GAS_LIMIT, toAddress, value);

        //签名Transaction，这里要对交易做签名
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hexValue = Numeric.toHexString(signedMessage);

        //发送交易
        EthSendTransaction ethSendTransaction =
                web3j.ethSendRawTransaction(hexValue).sendAsync().get();
        String transactionHash = ethSendTransaction.getTransactionHash();

        //获得到transactionHash后就可以到以太坊的网站上查询这笔交易的状态了
        System.out.println(transactionHash);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
