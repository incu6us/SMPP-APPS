package ua.com.life.smpp.db.domain;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.IndexColumn;

@Entity
@Table(name="campaign")
public class Campaign {

	@Id
	@GeneratedValue
	@Column(name="campaign_id")
	private Long campaignId;
	
	@Column(name="name",length=80)
	private String name;
	
	@Column(name="source_addr", length=20)
	private String sourceAddr;
	
	@OneToMany(mappedBy="campaign")
	private Set<MsisdnList> msisdnList;
	
	
	public Campaign() {
	}

	public Campaign(String name, String sourceAddr) {
		this.name = name;
		this.sourceAddr = sourceAddr;
	}

	public Campaign(String name, String sourceAddr, Set<MsisdnList> msisdList){
		this.name = name;
		this.sourceAddr = sourceAddr;
		this.msisdnList = msisdList;
	}
	

	public Long getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(Long campaignId) {
		this.campaignId = campaignId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSourceAddr() {
		return sourceAddr;
	}

	public void setSourceAddr(String sourceAddr) {
		this.sourceAddr = sourceAddr;
	}

	public Set<MsisdnList> getMsisdnList() {
		return msisdnList;
	}

	public void setMsisdnList(Set<MsisdnList> msisdnList) {
		this.msisdnList = msisdnList;
	}
	
	
}
