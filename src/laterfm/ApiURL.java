package laterfm;

import java.net.URL;

/**
 * Simple class to allow URL building with addParam()
 * A class to avoid long Strings in the code
 */
public class ApiURL {
	private URL url;
	
	ApiURL(String method) {
		try {
			this.url = new URL(ApiHandler.API_URL + "?method=" + method + "&api_key=" + ApiHandler.API_KEY + "&format=json");
		} catch(Exception e) {
			
		}
	}
	
	public void addParam(String name, String value) {
		try {
			this.url = new URL(this.url.toString() + "&" + name + "=" + value);
		} catch(Exception e) {
			
		}
	}
	
	public void addParam(String name, int value) {
		addParam(name, String.valueOf(value));
	}
	
	public URL getURL() {
		return this.url;
	}
}
