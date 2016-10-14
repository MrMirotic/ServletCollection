import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;
import java.io.IOException;
 


import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

 
public class ServletProcessor1 {
 
	public void process(Request request, Response response) throws IOException {
		String uri = request.getUri();
		String servletUrl = uri.substring(uri.lastIndexOf("/"));
		String servletName = new XMLUtil().getServletName(servletUrl);
		if(servletName.equals("404")){
			String errorMessage = "HTTP/1.1 404 File Not Found\r\n"
					+ "Content-Type: text/html\r\n" + "Content-Length: 26\r\n"
					+ "\r\n" + "<h1>Servlet Not Found</h1>";
			response.output.write(errorMessage.getBytes());
			System.out.println("Servlet Not Found");
			return;
		}
		System.out.println(servletName);
		//	类加载器，用于从指定JAR文件或目录加载类
		URLClassLoader loader = null;
		try {
			URLStreamHandler streamHandler = null;
			//	创建类加载器
			loader = new URLClassLoader(new URL[]{new URL(null, "file:", streamHandler)});
		} 
		catch (IOException e) {
			System.out.println(e.toString());
		}
		
		Class<?> myClass = null;
		try {
			//加载对应的servlet类
			myClass = loader.loadClass(servletName);
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
		}
		
		Servlet servlet = null;
		
		try {
			//生产servlet实例
			servlet = (Servlet) myClass.newInstance();
			//执行ervlet的service方法
			servlet.service((ServletRequest) request,(ServletResponse) response);
			response.output.flush();
			response.getWriter().flush();;
		} catch (Exception e) {
			System.out.println(e.toString());
		} catch (Throwable e) {
			System.out.println(e.toString());
		}
		
	}
}
