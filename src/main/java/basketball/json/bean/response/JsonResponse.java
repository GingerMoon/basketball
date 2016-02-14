/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basketball.json.bean.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonResponse <T> {

	private String Result;
	private T Records;
	private String Message;

	
	public JsonResponse() {
	}

	public JsonResponse(String Result) {
		this.Result = Result;
	}

	public JsonResponse(String Result, T Records) {
		this.Result = Result;
		this.Records = Records;
	}
	
	public JsonResponse(String Result, String Message) {
		this.Result = Result;
		this.Message = Message;
	}

	@JsonProperty("Result")
	public String getResult() {
		return Result;
	}

	public void setResult(String Result) {
		this.Result = Result;
	}

	@JsonProperty("Record")
	public T getRecords() {
		return Records;
	}

	public void setRecords(T Records) {
		this.Records = Records;
	}
	
	@JsonProperty("Message")
	public String getMessage() {
		return Message;
	}

	public void setMessage(String Message) {
		this.Message = Message;
	}

}
