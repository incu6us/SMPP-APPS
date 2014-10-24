package ua.com.life.smpp.sami.message;

import java.util.List;

public class MessagesStatusFromDeliveryReport {

	private Integer status;
	private Integer count;
	
	
	public MessagesStatusFromDeliveryReport() {
	}
	
	public MessagesStatusFromDeliveryReport(Integer status, Integer count) {
		this.status = status;
		this.count = count;
	}

	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	
}
