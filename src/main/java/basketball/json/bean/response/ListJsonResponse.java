/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basketball.json.bean.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ListJsonResponse <T> {

	private String Result;
	private List<T> Records;
	private long StartIndex;
	private long TotalRecordCount;
	private String Message;

	
	public ListJsonResponse() {
	}

	public ListJsonResponse(String Result) {
		this.Result = Result;
	}

	public ListJsonResponse(String Result, List<T> Records) {
		this.Result = Result;
		this.Records = Records;
	}

	public ListJsonResponse(String Result, List<T> Records, long StartIndex, long TotalRecordCount) {
		this.Result = Result;
		this.Records = Records;
		this.StartIndex = StartIndex;
		this.TotalRecordCount = TotalRecordCount;
	}
	
	public ListJsonResponse(String Result, String Message) {
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

	@JsonProperty("Records")
	public List<T> getRecords() {
		return Records;
	}

	public void setRecords(List<T> Records) {
		this.Records = Records;
	}

	@JsonProperty("StartIndex")
	public long getStartIndex() {
		return StartIndex;
	}

	public void setStartIndex(int StartIndex) {
		this.StartIndex = StartIndex;
	}
	
	@JsonProperty("TotalRecordCount")
	public long getTotalRecordCount() {
		return TotalRecordCount;
	}

	public void setTotalRecordCount(int TotalRecordCount) {
		this.TotalRecordCount = TotalRecordCount;
	}
	
	@JsonProperty("Message")
	public String getMessage() {
		return Message;
	}

	public void setMessage(String Message) {
		this.Message = Message;
	}

}
