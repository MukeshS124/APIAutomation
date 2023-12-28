package resources;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utils {
	public static RequestSpecification req;
	public RequestSpecification requestSpecification() throws IOException {
		if(req==null) {
		PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));					//to export the logs into a file
		req = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl")).addQueryParam("key", "qaclick123")
				.addFilter(RequestLoggingFilter.logRequestTo(log))								//To export the request Logs
				.addFilter(ResponseLoggingFilter.logResponseTo(log))							//To export the response Logs
				.setContentType(ContentType.JSON).build();
		return req;
		}
		return req;
	}
	public String getGlobalValue(String key) throws IOException {
		Properties prop = new Properties();											//Introduce the property file
		FileInputStream fis = new FileInputStream("C:\\Users\\mukes\\eclipse-workspace\\GoogleAPIFW\\src\\test\\java\\resources\\global.properties");
																					//To read a property file to input datas
		prop.load(fis);
		return prop.getProperty(key);
	}
	public String getJsonPath(Response response, String key) {
		String responseString = response.asString();
		JsonPath js = new JsonPath(responseString);
		return js.get(key).toString();
		
	}
}
