package ua.com.life.smpp.db.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="smpp_settings")
public class SmppSettings {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="name", length=80, unique=true)
	private String name;
	
	@Column(name="system_id", length=40, unique=true)
	private String systemId;
	
	@Column(name="password", length=40)
	private String password;
	
	@Column(name="host", length=20)
	private String host; 
	
	@Column(name="port", length=7)
	private int port;

	@Column(name="active", length=1)
	private int active;
	
	

	public SmppSettings() {
	}
	
	public SmppSettings(String name, String systemId, String password,
			String host, int port, int active) {
		this.name = name;
		this.systemId = systemId;
		this.password = password;
		this.host = host;
		this.port = port;
		this.active = active;
	}
	
	public SmppSettings(Long id, String name, String systemId, String password,
			String host, int port, int active) {
		this.id = id;
		this.name = name;
		this.systemId = systemId;
		this.password = password;
		this.host = host;
		this.port = port;
		this.active = active;
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

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}
	
}
