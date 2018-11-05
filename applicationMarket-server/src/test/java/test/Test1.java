package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.util.Enumeration;

import org.junit.Test;

public class Test1 {
	
	@Test
	public void test1(){
		System.out.println(Long.parseLong("123", 16));
		System.out.println(Long.toHexString(Long.parseLong("1")));
		System.out.println(Double.toHexString(0.1));
	}
	
}
