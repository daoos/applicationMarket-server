package moac;

import java.util.List;

import org.apache.bcel.generic.NEW;
import org.junit.Test;

public class Send {
	public static void main(String[] args) {
		RawTransaction transaction=new RawTransaction();
		transaction.from="0x4a1a050e9e657c19e9a2678df202fc72a12f6afb";
		transaction.to="0x842979507ca3dbb94392fb076f2555c7ff483d78";
		transaction.data="0x";
		transaction.chainId="0x1";
		transaction.gasLimit="0x7d0";
		transaction.nonce="0x1";
		transaction.gasPrice="0x7d0";
		transaction.value="0x";
		
		RlpList rlpList=new RlpList(RlpString.create(fromNat(transaction.nonce)),
									RlpString.create(fromNat("0x")),
									RlpString.create(fromNat(transaction.gasPrice)),
									RlpString.create(fromNat(transaction.gasLimit)),
									RlpString.create(transaction.to.toLowerCase()),
									RlpString.create(fromNat(transaction.value)),
									RlpString.create(transaction.data),
									RlpString.create(fromNat("0x")),
									RlpString.create(fromNat("0x")),
									RlpString.create(fromNat(transaction.chainId)),
									RlpString.create("0x"),
									RlpString.create("0x"));
		byte[] b=RlpEncoder.encodeList(rlpList);
		byte[] a=Hash.sha256(b);
		String msgHash=new String(a);
		System.out.println(msgHash);
		RlpDecoder.decode(b);
	}
	
	public static String fromNat(String bn){
		return bn=="0x0"?"0x":bn.length()%2==0?bn:"0x0"+bn.substring(2);
	}
	
	public static void ecsign(String msgHash,String privateKey){
		
	}
	
	@Test
	public void test1() throws Exception{
		
		
	}
	
}

