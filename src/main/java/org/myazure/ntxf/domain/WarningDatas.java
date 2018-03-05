package org.myazure.ntxf.domain;

import java.util.Calendar;

import javax.persistence.*;

@Entity
@Table(name = "sd_sense_warning")
public class WarningDatas {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private int id;

	@Column(name = "ownercode", nullable = false, length = 50, columnDefinition = "nvarchar")
	private String ownerCode;

	@Transient
	OwnerDatas ownerDatas;

	@Column(name = "Time", nullable = false)
	private Calendar time;

	@Column(name = "Alarmtype", nullable = false)
	private int alarmType;

	@Column(name = "Sourcedesc", columnDefinition = "nvarchar")
	private String sourceDesc;

	@Column(name = "Devicedesc", columnDefinition = "nvarchar")
	private String deviceDesc;

	@Column(name = "Wheredesc", columnDefinition = "nvarchar")
	private String whereDesc;

	@Column(name = "Ismistake", nullable = false)
	private int isMistake;

	@Column(name = "Istest", nullable = false)
	private int isTest;

	@Column(name = "Reserve", nullable = true, columnDefinition = "nvarchar")
	private String reserve;

	@Column(name = "Partcode", nullable = true, columnDefinition = "nvarchar")
	private String partCode;

	public WarningDatas() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOwnerCode() {
		return ownerCode;
	}

	public void setOwnerCode(String ownerCode) {
		this.ownerCode = ownerCode;
	}

	public int getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(int alarmType) {
		this.alarmType = alarmType;
	}

	public Calendar getTime() {
		return time;
	}

	public void setTime(Calendar time) {
		this.time = time;
	}

	public String getSourceDesc() {
		return sourceDesc;
	}

	public void setSourceDesc(String sourceDesc) {
		this.sourceDesc = sourceDesc;
	}

	public String getDeviceDesc() {
		return deviceDesc;
	}

	public void setDeviceDesc(String deviceDesc) {
		this.deviceDesc = deviceDesc;
	}

	public String getWhereDesc() {
		return whereDesc;
	}

	public void setWhereDesc(String whereDesc) {
		this.whereDesc = whereDesc;
	}

	public int getIsMistake() {
		return isMistake;
	}

	public void setIsMistake(int isMistake) {
		this.isMistake = isMistake;
	}

	public int getIsTest() {
		return isTest;
	}

	public void setIsTest(int isTest) {
		this.isTest = isTest;
	}

	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

	public String getPartCode() {
		return partCode;
	}

	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}

	public OwnerDatas getOwnerDatas() {
		return ownerDatas;
	}

	public void setOwnerDatas(OwnerDatas ownerDatas) {
		this.ownerDatas = ownerDatas;
	}
}
