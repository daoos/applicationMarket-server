package moac;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bouncycastle.crypto.digests.KeccakDigest;
import org.bouncycastle.jcajce.provider.digest.Keccak;
import org.web3j.crypto.RawTransaction;
import org.web3j.rlp.RlpEncoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpType;
import org.web3j.utils.Numeric;

public class Transaction {
	public String nonce;
	public String gasPrice = "";
	public String gasLimit = "";
	public String to;
	public String value;
	public String data;
	public String systemContract = "0x";
	public String via = "0x";
	public String chainId;

	public static void signMessage(Transaction tx, String privateKey) {
		Transaction transaction = new Transaction();
		transaction.nonce = "0x0";
		transaction.data = tx.data;
		transaction.value = Integer.toHexString(Integer.parseInt(tx.value));
		transaction.chainId = Integer.toHexString(Integer.parseInt(tx.chainId));
		transaction.systemContract = tx.systemContract;
		transaction.via = tx.via;

		List<RlpType> result = new ArrayList<>();
		result.add(RlpString.create(transaction.nonce));
		result.add(RlpString.create(transaction.systemContract));
		result.add(RlpString.create(transaction.gasPrice));
		result.add(RlpString.create(transaction.gasLimit));
		result.add(RlpString.create(transaction.to));
		result.add(RlpString.create(transaction.value));
		result.add(RlpString.create(transaction.data));
		result.add(RlpString.create(transaction.chainId));
		result.add(RlpString.create(transaction.via));
		result.add(RlpString.create("0x"));
		result.add(RlpString.create("0x"));
		result.add(RlpString.create("0x"));
		RlpList rlpList = new RlpList(result);
		byte[] rlpencode = RlpEncoder.encode(rlpList);

		// Keccak.Digest256.getInstance("").

	}

	public static RlpString create(String value) {
		byte[] bytes = value.getBytes();
		if (bytes[0] == 0) { // remove leading zero
			return new RlpString(Arrays.copyOfRange(bytes, 1, bytes.length));
		} else {
			return new RlpString(bytes);
		}
	}
}

class RlpString implements RlpType {
	public static final byte[] EMPTY = new byte[] {};

	public final byte[] value;

	public RlpString(byte[] value) {
		this.value = value;
	}

	public byte[] getBytes() {
		return value;
	}

	public BigInteger asPositiveBigInteger() {
		if (value.length == 0) {
			return BigInteger.ZERO;
		}
		return new BigInteger(1, value);
	}

	public String asString() {
		return Numeric.toHexString(value);
	}

	public static RlpString create(byte[] value) {
		return new RlpString(value);
	}

	public static RlpString create(byte value) {
		return new RlpString(new byte[] { value });
	}

	public static RlpString create(BigInteger value) {
		// RLP encoding only supports positive integer values
		if (value.signum() < 1) {
			return new RlpString(EMPTY);
		} else {
			byte[] bytes = value.toByteArray();
			if (bytes[0] == 0) { // remove leading zero
				return new RlpString(Arrays.copyOfRange(bytes, 1, bytes.length));
			} else {
				return new RlpString(bytes);
			}
		}
	}

	public static RlpString create(long value) {
		return create(BigInteger.valueOf(value));
	}

	public static RlpString create(String value) {
		return new RlpString(value.getBytes());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		RlpString rlpString = (RlpString) o;

		return Arrays.equals(value, rlpString.value);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(value);
	}
}
