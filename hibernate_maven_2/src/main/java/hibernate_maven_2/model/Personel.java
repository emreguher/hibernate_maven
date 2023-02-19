package hibernate_maven_2.model;

import java.io.Serializable;
import java.math.*;

// Java Persistence API
import javax.persistence.*;

// java bean
@Entity
public class Personel implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(length=30, nullable=false)
	private String ad;
	@Column(length=30, nullable=false)
	private String soyad;
	@Column(precision=10, scale=2)
	private BigDecimal maas;
	
	public Personel() {
		// TODO Auto-generated constructor stub
	}
	
	public Personel(String ad, String soyad, BigDecimal maas) {
		this.ad = ad;
		this.soyad = soyad;
		this.maas = maas;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAd() {
		return ad;
	}

	public void setAd(String ad) {
		this.ad = ad;
	}

	public String getSoyad() {
		return soyad;
	}

	public void setSoyad(String soyad) {
		this.soyad = soyad;
	}

	public BigDecimal getMaas() {
		return maas;
	}

	public void setMaas(BigDecimal maas) {
		this.maas = maas;
	}
	
	

}
