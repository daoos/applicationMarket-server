package com.techwells.applicationMarket.util.moac;

import org.junit.Test;

/**
 * 转账的实体类
 * @author 陈加兵
 */
public class Params {
	private String from;    //发送方的地址
	private String to;     //接受方的地址
	private String gas="0x9c40";
	private String gasPrice="0x4a817c800";
	private String value;    //转账的金额
	private String data;   //附加数据
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getGas() {
		return gas;
	}
	public void setGas(String gas) {
		this.gas = gas;
	}
	public String getGasPrice() {
		return gasPrice;
	}
	public void setGasPrice(String gasPrice) {
		this.gasPrice = gasPrice;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	
	
}
