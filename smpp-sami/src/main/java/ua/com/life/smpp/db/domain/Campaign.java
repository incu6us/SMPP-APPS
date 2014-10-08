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
	@Column(name="camapign_id")
	private Long camaignId;
	
	@Column(name="name",length=80, unique=true)
	private String name;
	
	@OneToMany(mappedBy="campaign")
	private Set<MsisdnList> msisdnList;
	
	
	public Campaign() {
	}

	public Campaign(String name) {
		this.name = name;
	}

	public Campaign(String name, Set<MsisdnList> msisdList){
		this.name = name;
		this.msisdnList = msisdList;
	}
	

	public Long getCamaignId() {
		return camaignId;
	}

	public void setCamaignId(Long camaignId) {
		this.camaignId = camaignId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<MsisdnList> getMsisdnList() {
		return msisdnList;
	}

	public void setMsisdnList(Set<MsisdnList> msisdnList) {
		this.msisdnList = msisdnList;
	}
	
	
}
