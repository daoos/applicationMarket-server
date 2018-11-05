package com.techwells.applicationMarket.util.swtc;

import java.io.Serializable;

/**
 * 井通支付的支付对象
 * @author 陈加兵
 */
public class PayObject implements Serializable{
	private String source;  //发起账号
	private String destination;  //目标账号
	private Amount amount;  //支付金额
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public Amount getAmount() {
		return amount;
	}
	public void setAmount(Amount amount) {
		this.amount = amount;
	}
	
}