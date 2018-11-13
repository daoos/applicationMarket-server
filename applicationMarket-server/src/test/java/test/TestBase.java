package test;

import org.junit.Test;

import com.techwells.applicationMarket.util.Base64Util;

public class TestBase {
	
	@Test
	public void test1() throws Exception{
		String key="565f69d314285b28acf85bc6804bcfcd1d65cecb3b54184e98bf33ab7f47b230";
		System.out.println(Base64Util.Decoder(Base64Util.encoder(key)).equals(key));
	}
	
	
}	
