package moac;

import java.math.BigInteger;
/**
 */
public class RawTransaction {

    public String nonce;
    public String gasPrice;
    public String gasLimit;
    public String to;
    public String value;
    public String data;
    public String from;
    public String chainId;;
    
    
    

//    protected RawTransaction(BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to,
//                           BigInteger value, String data) {
//        this.nonce = nonce;
//        this.gasPrice = gasPrice;
//        this.gasLimit = gasLimit;
//        this.to = to;
//        this.value = value;
//
//        if (data != null) {
//            this.data = Numeric.cleanHexPrefix(data);
//        }
//    }
//
//    public static RawTransaction createContractTransaction(
//            BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, BigInteger value,
//            String init) {
//
//        return new RawTransaction(nonce, gasPrice, gasLimit, "", value, init);
//    }
//
//    public static RawTransaction createMcTransaction(
//            BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to,
//            BigInteger value) {
//
//        return new RawTransaction(nonce, gasPrice, gasLimit, to, value, "");
//
//    }
//
//    public static RawTransaction createTransaction(
//            BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to, String data) {
//        return createTransaction(nonce, gasPrice, gasLimit, to, BigInteger.ZERO, data);
//    }
//
//    public static RawTransaction createTransaction(
//            BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to,
//            BigInteger value, String data) {
//
//        return new RawTransaction(nonce, gasPrice, gasLimit, to, value, data);
//    }
//
//    public BigInteger getNonce() {
//        return nonce;
//    }
//
//    public BigInteger getGasPrice() {
//        return gasPrice;
//    }
//
//    public BigInteger getGasLimit() {
//        return gasLimit;
//    }
//
//    public String getTo() {
//        return to;
//    }
//
//    public BigInteger getValue() {
//        return value;
//    }
//
//    public String getData() {
//        return data;
//    }
}
