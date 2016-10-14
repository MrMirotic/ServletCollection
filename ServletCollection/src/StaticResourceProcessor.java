import java.io.IOException;
 
public class StaticResourceProcessor {
 
	public void process(Request request, Response response) {
		try {
			response.setRequest(request);
			response.sendStaticResource();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
