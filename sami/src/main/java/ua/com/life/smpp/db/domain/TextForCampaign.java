package ua.com.life.smpp.db.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="campaign_text")
public class TextForCampaign {

	@Id
	@GeneratedValue
	@Column(name="text_id")
	private Long id;
	
	@Column(name="text", length=254)
	private String text;
	
	@ManyToOne
	@JoinColumn(name="campaign_id")
	private Campaign campaign;

	public TextForCampaign(){
	}
	
	public TextForCampaign(String text) {
		this.text = text;
	}

	public TextForCampaign(String text, Campaign campaign) {
		this.text = text;
		this.campaign = campaign;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Campaign getCampaign() {
		return campaign;
	}

	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}
	
}
