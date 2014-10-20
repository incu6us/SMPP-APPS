package ua.com.life.smpp.sami.message;

public class ComparedMessageObject {

	private Long campaignId;
	private String campaignName;
	private String campaignSourceAddres;
	private String campaignText;
	private Integer totalMessages;
	private Integer inActionMessages; 	// with status=1
	private Integer successMessages; 	// with status=
	private Integer unsuccessMessages; // with status=-1
	
	
	
	public ComparedMessageObject(){
	}
	
	public ComparedMessageObject(Long campaignId, String campaignName, String campaignSourceAddres, String campaignText) {
		this.campaignId = campaignId;
		this.campaignName = campaignName;
		this.campaignSourceAddres = campaignSourceAddres;
		this.campaignText = campaignText;
	}

	public ComparedMessageObject(Long campaignId, String campaignName, String campaignSourceAddres, String campaignText, Integer totalMessages, Integer inActionMessages,
			Integer successMessages, Integer unsuccessMessages) {
		this.campaignId = campaignId;
		this.campaignName = campaignName;
		this.campaignSourceAddres = campaignSourceAddres;
		this.campaignText = campaignText;
		this.totalMessages = totalMessages;
		this.inActionMessages = inActionMessages;
		this.successMessages = successMessages;
		this.unsuccessMessages = unsuccessMessages;
	}
	
	

	public Long getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(Long campaignId) {
		this.campaignId = campaignId;
	}

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	public String getCampaignSourceAddres() {
		return campaignSourceAddres;
	}

	public void setCampaignSourceAddres(String campaignSourceAddres) {
		this.campaignSourceAddres = campaignSourceAddres;
	}

	public String getCampaignText() {
		return campaignText;
	}

	public void setCampaignText(String campaignText) {
		this.campaignText = campaignText;
	}

	public Integer getTotalMessages() {
		return totalMessages;
	}

	public void setTotalMessages(Integer totalMessages) {
		this.totalMessages = totalMessages;
	}

	public Integer getInActionMessages() {
		return inActionMessages;
	}

	public void setInActionMessages(Integer inActionMessages) {
		this.inActionMessages = inActionMessages;
	}

	public Integer getSuccessMessages() {
		return successMessages;
	}

	public void setSuccessMessages(Integer successMessages) {
		this.successMessages = successMessages;
	}

	public Integer getUnsuccessMessages() {
		return unsuccessMessages;
	}

	public void setUnsuccessMessages(Integer unsuccessMessages) {
		this.unsuccessMessages = unsuccessMessages;
	}
	
}
