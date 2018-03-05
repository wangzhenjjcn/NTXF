package org.myazure.ntxf.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "A_Myazure_Storage")
public class MyazureData {
	@Id
	@Column(name = "mkey", nullable = false, length = 250, columnDefinition = "nvarchar")
	private String mkey;

	@Column(name = "mvalue", nullable = true, length = 4000, columnDefinition = "nvarchar")
	private String mvalue;

	public MyazureData() {

	}

	public MyazureData(String key, String value) {
		this.mkey = key;
		this.mvalue = value;
	}

	public String getMkey() {
		return mkey;
	}

	public void setMkey(String mkey) {
		this.mkey = mkey;
	}

	public String getMvalue() {
		return mvalue;
	}

	public void setMvalue(String mvalue) {
		this.mvalue = mvalue;
	}

	 

}
