package egovframework.com.cmm;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

public class JSONResponseUtil {
	
	public static ResponseEntity getJSONResponse(Object obj){
		
		new JSONSerializer();
		String json = JSONSerializer.toJSON(obj).toString();
		HttpHeaders responseHeader = new HttpHeaders();
		responseHeader.add("Content-Type", "application/json;charset=UTF-8;");
		return new ResponseEntity(json, responseHeader, HttpStatus.OK);
	}
	public static String getJSONString(Object obj){
		new JSONSerializer();
		return JSONSerializer.toJSON(obj).toString();
	}
	public static JSON getJSON(Object obj){
		new JSONSerializer();
		return JSONSerializer.toJSON(obj);
	}

}
