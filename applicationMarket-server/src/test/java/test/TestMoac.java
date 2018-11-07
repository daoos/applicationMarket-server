package test;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;

import org.aspectj.weaver.ast.Var;
import org.junit.Test;

import com.google.gson.Gson;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;

public class TestMoac {

	public static void signTransaction(String txjson, String privateKey) {
		TxJson tx = new Gson().fromJson(txjson, TxJson.class);

		if (tx.chainId < 1) {
			return;
		}

		// if (!tx.gasPrice&&!tx.gasLimit) {
		// return;
		// }

		if (tx.nonce < 0 || tx.gasLimit < 0 || tx.gasPrice < 0
				|| tx.chainId < 0) {
			return;
		}

		// Sharding Flag only accept the
		// If input has not sharding flag, set it to 0 as global TX.
		if (tx.shardingFlag == null) {
			// console.log("Set default sharding to 0");
			tx.shardingFlag = 0;
		}

		try {
			// Make sure all the number fields are in HEX format

			TxJson transaction = tx;
			transaction.to = tx.to;
			transaction.data = tx.data;
			transaction.value = tx.value;
			transaction.chainId = tx.chainId;
			transaction.shardingFlag = tx.shardingFlag;
			transaction.systemContract = "0x";
			// transaction.via = tx.via || '0x'; //Sharding subchain address

			// Encode the TX for signature
			// type txdata struct {
			// AccountNonce uint64 `json:"nonce" gencodec:"required"`
			// SystemContract uint64 `json:"syscnt" gencodec:"required"`
			// Price *big.Int `json:"gasPrice" gencodec:"required"`
			// GasLimit *big.Int `json:"gas" gencodec:"required"`
			// // nil means contract creation
			// Amount *big.Int `json:"value" gencodec:"required"`
			// Payload []byte `json:"input
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) throws Throwable {
		JsonRpcHttpClient client=new JsonRpcHttpClient(new URL("http://47.92.101.153:8545"));
		String[] params=new String[2];    //封装请求参数
		params[0]="0x4a1a050e9e657c19e9a2678df202fc72a12f6afb";  //钱包的地址  
		params[1]="latest";   //获取最新的余额信息
		String b=client.invoke("mc_getTransactionCount", params,String.class);  //返回十六进制的余额
		System.out.println(b);
//		String[] p=new String[1];
//		String net=client.invoke("net_version",p,String.class);
//		System.out.println(client.invoke("mc_getTransactionCount", params,String.class));
//		String rawTx = "{"
//				+ "\"from\": \"0x4a1a050e9e657c19e9a2678df202fc72a12f6afb\","
//				+ "\"nonce\":"+0x0+","
//				+ "\"gasPrice\":"+0x9c40+","
//				+ "\"gasLimit\":"+0x9c40+","
//				+ "\"to\": \"0x4a1a050e9e657c19e9a2678df202fc72a12f6afb\","
//				+ "\"value\":"+0x1+","
//				+ "\"data\": \"0x00\","
//				+ "\"chainId\":"+net+"}";
//		System.out.println(rawTx);
//		signTransaction(rawTx, "733f3188ed94196b4f4c40beed0790debfe005f179728a252f2231a4a05d24e3");
	}
	
	
	
	@Test
	public void test1(){
		int a=0x1;
		System.out.println(a);
		System.out.println(Integer.parseInt("0x18".substring(2),16));
	}
}
