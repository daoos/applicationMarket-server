package moac;

public class Send {
	public static void main(String[] args) {
		RawTransaction transaction=new RawTransaction();
		transaction.from="";
		transaction.to="";
		transaction.data="0x";
		transaction.chainId="0x";
		transaction.gasLimit="";
		transaction.nonce="";
		transaction.gasLimit="";
	}
	
	public String fromNat(String bn){
		return bn=="0x0"?"0x":bn.length()%2==0?bn:"0x0"+bn.substring(2);
	}
}

