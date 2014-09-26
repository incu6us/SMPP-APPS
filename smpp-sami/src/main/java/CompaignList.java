

import java.util.GregorianCalendar;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="compaign", uniqueConstraints = {@UniqueConstraint(columnNames = "compaign_id")})
public class CompaignList {

	@Id
	@GeneratedValue
	@Column(name="compaign_id", unique=true, nullable=false)
	private Long id;

	@Column(name="name", length=100)
	private String name;
	
	@Column(length=255)
	private String description;
	
	@Column(name="commit_date")
	private GregorianCalendar commitDate;
	
	@Column(name="start_date")
	private GregorianCalendar startDate;
	
	@Column(length=10)
	private String state;
	
	@Column(length=50)
	private String owner;
	
	@Column
	private int speed;
	
	@Column(name="validity_period", length=20)
	private String validityPeriod;
	
	@Column(name="sms_text", length=512)
	private String smsText;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "compaign")
	private Set<MsisdnList> msisdnList;
	
	
	
	public CompaignList() {
	}


	public CompaignList(Long id, String name, String description,
			GregorianCalendar commitDate, GregorianCalendar startDate,
			String state, String owner, int speed, String validityPeriod,
			String smsText) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.commitDate = commitDate;
		this.startDate = startDate;
		this.state = state;
		this.owner = owner;
		this.speed = speed;
		this.validityPeriod = validityPeriod;
		this.smsText = smsText;
	}


	public CompaignList(Long id, Set<MsisdnList> msisdnList, String name,
			String description, GregorianCalendar commitDate,
			GregorianCalendar startDate, String state, String owner, int speed,
			String validityPeriod, String smsText) {
		super();
		this.id = id;
		this.msisdnList = msisdnList;
		this.name = name;
		this.description = description;
		this.commitDate = commitDate;
		this.startDate = startDate;
		this.state = state;
		this.owner = owner;
		this.speed = speed;
		this.validityPeriod = validityPeriod;
		this.smsText = smsText;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public GregorianCalendar getCommitDate() {
		return commitDate;
	}


	public void setCommitDate(GregorianCalendar commitDate) {
		this.commitDate = commitDate;
	}


	public GregorianCalendar getStartDate() {
		return startDate;
	}


	public void setStartDate(GregorianCalendar startDate) {
		this.startDate = startDate;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getOwner() {
		return owner;
	}


	public void setOwner(String owner) {
		this.owner = owner;
	}


	public int getSpeed() {
		return speed;
	}


	public void setSpeed(int speed) {
		this.speed = speed;
	}


	public String getValidityPeriod() {
		return validityPeriod;
	}


	public void setValidityPeriod(String validityPeriod) {
		this.validityPeriod = validityPeriod;
	}


	public String getSmsText() {
		return smsText;
	}


	public void setSmsText(String smsText) {
		this.smsText = smsText;
	}
	
	
}
