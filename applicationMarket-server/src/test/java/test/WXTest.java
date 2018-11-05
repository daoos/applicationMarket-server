package test;

import static org.hamcrest.CoreMatchers.nullValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;

import com.google.gson.Gson;
import com.techwells.applicationMarket.domain.User;


public class WXTest {
	
	@Test
	public void test(){
		Gson gson=new Gson();
		User user=new User();
		user.setActivated(1);
		user.setUserName("chenjiabing");
//		gson
		System.out.println(gson.toJson(user));
	}
	
	@Test
	public void test1(){
		HttpClient client = new DefaultHttpClient();
//		HttpGet httpGet=new HttpGet();
		String url="http://www.baidu.com";
		System.out.println(URI.create(url).toString());
		HttpGet get=new HttpGet(URI.create(url));
		HttpPost post=new HttpPost(URI.create(url));
		try {
			HttpResponse response = client.execute(get);
			HttpEntity entity=response.getEntity();
			BufferedReader reader=new BufferedReader(new InputStreamReader(entity.getContent()));
			StringBuilder sb = new StringBuilder();
			for (String temp = reader.readLine(); temp != null; temp = reader.readLine()) {
	             sb.append(temp);
	         }
	         
			System.out.println(sb.toString());
	         
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void test2() throws ClientProtocolException, IOException{
		HttpClient client = new DefaultHttpClient();
		String url="http://www.emoonbow.com:8081/lifecrystal-server/user/getDefaultDecedent.do";
		System.out.println(URI.create(url).toString());
		HttpGet get=new HttpGet(URI.create(url));
		HttpResponse response=client.execute(get);
		if (response.getStatusLine().getStatusCode()==200) {
			HttpEntity entity=response.getEntity();
			BufferedReader reader=new BufferedReader(new InputStreamReader(entity.getContent(),"UTF-8"));
			StringBuilder sb = new StringBuilder();
			for (String temp = reader.readLine(); temp != null; temp = reader.readLine()) {
	             sb.append(temp);
	         }
			Gson gson=new Gson();
			System.out.println(gson.fromJson(sb.toString(), Map.class).get("errorCode"));
			System.out.println(sb.toString());
		}
	
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
