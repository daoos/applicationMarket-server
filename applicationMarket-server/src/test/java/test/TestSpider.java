package test;

import java.util.List;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;


//0xe953d6bbccab1977ae7dff6a4094f3dbbd774fb60c6b214a294c17a8c5c56156

public class TestSpider {
	public static void main(String[] args) throws Exception {
        // 创建webclient
        WebClient webClient = new WebClient();
        // JS 支持
        webClient.getOptions().setJavaScriptEnabled(true);   
        // 取消 CSS 支持
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        // 获取指定网页实体
        HtmlPage page = (HtmlPage) webClient.getPage("http://www.emoonbow.com/applicationMarketAccount/account.html");
//        System.err.println(page.asText());
        // 获取搜索输入框
//        HtmlInput input_from = (HtmlInput) page.getHtmlElementById("from_address");
//        // 往输入框 “填值”
//        input_from.setValueAttribute("0x4a1a050e9e657c19e9a2678df202fc72a12f6afb");
//        
//     // 获取搜索输入框
//        HtmlInput input_to = (HtmlInput) page.getHtmlElementById("to_address");
//        // 往输入框 “填值”
//        input_to.setValueAttribute("0x842979507ca3dbb94392fb076f2555c7ff483d78");
//        
//     // 获取搜索输入框
//        HtmlInput input_sendKey = (HtmlInput) page.getHtmlElementById("send_key");
//        // 往输入框 “填值”
//        input_sendKey.setValueAttribute("bbeeec3ce5279b197efc2a6ae2598df0bb461bff6ee43e702e5c8d1342fdee9a");
//        
//     // 获取搜索输入框
//        HtmlInput input_value = (HtmlInput) page.getHtmlElementById("amount");
//        // 往输入框 “填值”
//        input_value.setValueAttribute("0.1");
//        
//        // 获取搜索按钮
//        HtmlInput btn = (HtmlInput) page.getHtmlElementById("search_btn");
//        // “点击” 搜索
//        HtmlPage page2 = btn.click();
////         选择元素
////        List<HtmlElement> spanList=page2.getHtmlElementById("hash").getTextContent();
////        
//        String hash=page2.getHtmlElementById("hash").getTextContent();
//        System.err.println(hash);
        	
//        for(int i=0;i<spanList.size();i++) {
//            // 输出新页面的文本
//            System.out.println(i+1+"、"+spanList.get(i).asText());
//        }
    }
	
	
	@Test
	public void test1(){
		try {
            WebClient client=new WebClient(BrowserVersion.FIREFOX_52);
            // JS 支持
            client.getOptions().setJavaScriptEnabled(true);   
            // 取消 CSS 支持
            client.getOptions().setCssEnabled(false);
            client.getOptions().setThrowExceptionOnScriptError(false);
            HtmlPage page=client.getPage("http://www.emoonbow.com/applicationMarketAccount/account.html");
            System.out.println(page.asText());
//            HtmlForm form = page.getFormByName("f");
//            HtmlTextInput text= form.getInputByName("wd");
//            HtmlSubmitInput submit = form.getInputByValue("百度一下");
//            text.setValueAttribute("爱撒谎的男孩");
//            HtmlPage page2=submit.click();
//            System.out.println(page2.asXml());
        } catch (Exception e) {
//            e.printStackTrace();
        } 
	}
	
	
	//
	@Test
	public void test3(){
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}	
