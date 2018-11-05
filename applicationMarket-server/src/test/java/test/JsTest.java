package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class JsTest {

	@Test
	public void test1() {
		System.out.println(System.currentTimeMillis()/1000+"");

	}

	@Test
	public void test2(){
		ScriptEngineManager sem = new ScriptEngineManager();
		ScriptEngine se = sem.getEngineByName("javascript");
	    try
	    {
		     String script = "function say(name){ return 'hello,'+name;}";
		     se.eval(script);
		     Invocable inv2 = (Invocable) se;
		     String res=(String)inv2.invokeFunction("say","陈加兵");
		     System.out.println(res);
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	}
	
	
	@Test
	public void test3() throws ScriptException, NoSuchMethodException, InterruptedException, IOException {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("nashorn");
		
//		FileReader reader1=new FileReader(new File("C:\\images\\chain.js"));
		FileReader reader2=new FileReader(new File("C:\\images\\a.js"));
//		BufferedReader reader=new BufferedReader(reader2);
//		StringBuffer buffer=new StringBuffer();
//		String tempLine=null;
//		while((tempLine=reader.readLine())!=null){
//			buffer.append(tempLine).append("\n");
//		}
		
//		System.out.println(buffer.toString());
//		System.out.println(engine.eval(reader1));
//		System.out.println(engine.eval(reader2));
//		engine.eval(reader1);
		
		engine.eval(reader2);
		
		
		
		
		
		
		
		
//		Invocable invocable=(Invocable)engine;
////		System.out.println(invocable.invokeFunction("get", "陈加兵"));
//		
//		JavaScriptInterface executeMethod = invocable.getInterface(JavaScriptInterface.class);
//		System.out.println(executeMethod.execute("str1", "str2"));
//		System.out.println(engine.eval(reader1));
//		Thread.sleep(3000);
		
		
		
//		Invocable invocable=(Invocable)engine;
		
		
		
//		System.out.println(invocable.invokeFunction("o", new String[]{"1","2"}));
//		System.out.println(engine.eval(reader2));
		
//		Compilable compilable=(Compilable)engine;
//		BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(new File("C:\\images\\chain.js"))));
//		CompiledScript script=compilable.compile(reader1);
//		script.eval();
		
		
		
//		script.eval();
//		Invocable inv2 = (Invocable) script;
//		System.out.println(inv2.invokeFunction("get", "陈加兵"));
		
		

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
		
}
