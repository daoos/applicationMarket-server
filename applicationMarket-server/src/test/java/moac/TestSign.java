package moac;

import java.math.BigInteger;
import java.net.URL;

import org.junit.Test;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;

public class TestSign {
	
	@Test
	public void test1() throws Throwable{
		BigInteger GAS_PRICE = BigInteger.valueOf(22_000_000_000L);
        BigInteger GAS_LIMIT = BigInteger.valueOf(4_300_000);
		//转账人账户地址
        String ownAddress = "0x4a1a050e9e657c19e9a2678df202fc72a12f6afb";
        //被转人账户地址
        String toAddress = "0x842979507ca3dbb94392fb076f2555c7ff483d78";
        
        String privateKey="bbeeec3ce5279b197efc2a6ae2598df0bb461bff6ee43e702e5c8d1342fdee9a";
        
        Credentials credentials=Credentials.create(privateKey);
        
        RawTransaction rawTransaction=RawTransaction.createMcTransaction(BigInteger.valueOf(0), GAS_PRICE, GAS_LIMIT, toAddress, BigInteger.valueOf(1));
        
        byte[] b=TransactionEncoder.signMessage(rawTransaction, credentials);
        
        System.out.println(Numeric.toHexString(b));
        
        JsonRpcHttpClient client=new JsonRpcHttpClient(new URL("http://47.92.101.153:8545"));
        String[] params=new String[1];    //封装请求参数
		params[0]=Numeric.toHexString(b);
		
        String hash=client.invoke("mc_sendRawTransaction", params,String.class);
        System.out.println(hash);
        
        
        
        
        
	}
}
