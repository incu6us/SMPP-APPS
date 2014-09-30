

import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	private Long Id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "compaign_id", nullable=false)
	private CompaignList compaignList;
	
	@Column
	private String msisdn;
	
	@Column(name = "sending_date")
	private GregorianCalendar sendingDate;

	@Column(name = "delivery_date_smsc")
	private GregorianCalendar deliveryDate;
	
	@Column(length = 11)
	private int status;
	
	@Column(name = "message_id", length = 50)
	private String messageId;
	
	@Column(name = "message_state", length = 7)
	private String messageState;

	
	public MsisdnList() {
	}

	public MsisdnList(Long id, String msisdn,
			GregorianCalendar sendingDate, GregorianCalendar deliveryDate,
			int status, String messageId, String messageState) {
		super();
		Id = id;
		this.msisdn = msisdn;
		this.sendingDate = sendingDate;
		this.deliveryDate = deliveryDate;
		this.status = status;
		this.messageId = messageId;
		this.messageState = messageState;
	}
	
	public MsisdnList(Long id, CompaignList compaignList, String msisdn,
			GregorianCalendar sendingDate, GregorianCalendar deliveryDate,
			int status, String messageId, String messageState) {
		super();
		Id = id;
		this.compaignList = compaignList;
		this.msisdn = msisdn;
		this.sendingDate = sendingDate;
		this.deliveryDate = deliveryDate;
		this.status = status;
		this.messageId = messageId;
		this.messageState = messageState;
	}

	
	
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public CompaignList getCompaignId() {
		return compaignList;
	}

	public void setCompaignId(CompaignList compaignList) {
		this.compaignList = compaignList;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public GregorianCalendar getSendingDate() {
		return sendingDate;
	}

	public void setSendingDate(GregorianCalendar sendingDate) {
		this.sendingDate = sendingDate;
	}

	public GregorianCalendar getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(GregorianCalendar deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getMessageState() {
		return messageState;
	}

	public void setMessageState(String messageState) {
		this.messageState = messageState;
	}
	
	
	
	
}
