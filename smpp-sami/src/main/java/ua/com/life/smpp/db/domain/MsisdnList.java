package ua.com.life.smpp.db.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="msisdn_list")
public class MsisdnList {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(length=15)
	private String msisdn;
	
	@Column(name="accept_date")
	private Date sendingDate;
	
	@Column(name="submit_date")
	private Date deliveryDateSMSC;
	
	@Column(name="done_date")
	private Date readingDate;
	
	@Column(name="validity_period", length=16)  
	private String validityPeriod = "000000020000000R";		// 2 hours
	
	@Column(length=5, nullable=false)
	private Integer status;
	
	@Column(name="message_id", length=40)
	private String messageId;
	
	@Column(name="smsc_err", length=5)
	private String smscErr;
	
	@Column(name="id_systemid")
	private Long idSystemId;
	
	@ManyToOne
	@JoinColumn(name="campaign_id")
	private Campaign campaign;
	

	public MsisdnList(){
	}

	public MsisdnList(String msisdn,  Integer status) {
		this.msisdn = msisdn;
		this.sendingDate = new Date(System.currentTimeMillis());
		this.status = status;
	}

	public MsisdnList(String msisdn, Campaign camaign) {
		this.msisdn = msisdn;
		this.sendingDate = new Date(System.currentTimeMillis());
		this.status = 0;
		this.campaign = camaign;
	}
	
	public MsisdnList(String msisdn, Campaign camaign, String validityPeriod, Long idSystemId) {
		this.msisdn = msisdn;
		this.sendingDate = new Date(System.currentTimeMillis());
		this.status = 0;
		this.validityPeriod = validityPeriod;
		this.idSystemId = idSystemId;
		this.campaign = camaign;
	}
	
	public MsisdnList(String msisdn,  Integer status, Campaign camaign) {
		this.msisdn = msisdn;
		this.sendingDate = new Date(System.currentTimeMillis());
		this.status = status;
		this.campaign = camaign;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public Date getSendingDate() {
		return sendingDate;
	}

	public void setSendingDate(Date sendingDate) {
		this.sendingDate = sendingDate;
	}

	public Date getDeliveryDateSMSC() {
		return deliveryDateSMSC;
	}

	public void setDeliveryDateSMSC(Date deliveryDateSMSC) {
		this.deliveryDateSMSC = deliveryDateSMSC;
	}

	public Date getReadingDate() {
		return readingDate;
	}

	public void setReadingDate(Date readingDate) {
		this.readingDate = readingDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getSmscErr() {
		return smscErr;
	}

	public void setSmscErr(String smscErr) {
		this.smscErr = smscErr;
	}

	public Campaign getCampaign() {
		return campaign;
	}

	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}
	
	public String getValidityPeriod() {
		return validityPeriod;
	}

	public void setValidityPeriod(String validityPeriod) {
		this.validityPeriod = validityPeriod;
	}

	public Long getIdSystemId() {
		return idSystemId;
	}

	public void setIdSystemId(Long idSystemId) {
		this.idSystemId = idSystemId;
	}

}
