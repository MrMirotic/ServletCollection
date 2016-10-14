import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class XMLUtil {
	private Document document;
	Map<String, String> map = new HashMap<String, String>();
	XMLUtil(){
	    File file = new File("web.xml");
	    try {
	    	document = Jsoup.parse(file, "UTF-8");
	    } catch (IOException e) {
	      // TODO Auto-generated catch block
	    	e.printStackTrace();
	    }
		Elements servlets = document.select("servlet");
		Elements servletMappings = document.select("servlet-mapping");
		for(Element tmp:servletMappings){
			String text1 = tmp.select("url-pattern").text();
			String text2 = tmp.select("servlet-name").text();
			for(Element tmp2:servlets)
			{
				String text3 = tmp2.select("servlet-name").text();
				String text4 = tmp2.select("servlet-class").text();
				if(text2.equals(text3))
					map.put(text1,text4);
			}
		}
	}
	public String getServletName(String servletUrl){
		if(map.keySet().contains(servletUrl))
			return map.get(servletUrl);
		else
			return "404";
		
	}
}
