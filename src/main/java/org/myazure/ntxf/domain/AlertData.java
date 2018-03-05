package org.myazure.ntxf.domain;

import java.util.Calendar;

import javax.persistence.*;

@Entity
@Table(name = "sd_alarms")
public class AlertData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private int id;


	@Column(name = "ownercode", nullable = false, length = 50, columnDefinition = "nvarchar")
	private String ownerCode;

	@Transient
	OwnerDatas ownerDatas;

	@Column(name = "Deviceindex", nullable = false)
	private Integer deviceIndex;
	
	@Column(name = "Deviceno", nullable = true)
	private Integer deviceNo;
	
	@Column(name = "istest", nullable = true)
	private Integer isTest;
	
	@Column(name = "Time", nullable = false)
	private Calendar time;

	@Column(name = "Alarm_Status", columnDefinition = "nvarchar")
	private String alarmStatus;

	@Column(name = "Alarm_Sourcedesc", columnDefinition = "nvarchar")
	private String sourceDesc;

	@Column(name = "Alarm_Devicedesc", columnDefinition = "nvarchar")
	private String deviceDesc;

	@Column(name = "Alarm_Wheredesc", columnDefinition = "nvarchar")
	private String whereDesc;

	@Column(name = "Isdeal" )
	private boolean IsDeal;

	@Column(name = "Reserve", nullable = true, columnDefinition = "nvarchar")
	private String reserve;


	public AlertData() {
//		CREATE TABLE [dbo].[sd_alarms] (
//				[ID] int NOT NULL IDENTITY(1,1) ,
//				[OwnerCode] nvarchar(50) COLLATE Chinese_PRC_CI_AS NOT NULL ,
//				[DeviceIndex] int NOT NULL ,
//				[DeviceNo] int NULL ,
//				[isTest] int NULL ,
//				[Time] datetime NOT NULL ,
//				[Alarm_Status] nvarchar(100) COLLATE Chinese_PRC_CI_AS NULL ,
//				[Alarm_SourceDesc] nvarchar(255) COLLATE Chinese_PRC_CI_AS NULL ,
//				[Alarm_DeviceDesc] nvarchar(255) COLLATE Chinese_PRC_CI_AS NULL ,
//				[Alarm_WhereDesc] nvarchar(255) COLLATE Chinese_PRC_CI_AS NULL ,
//				[Reserve] nvarchar(255) COLLATE Chinese_PRC_CI_AS NULL ,
//				[Lastupdate] datetime NULL ,
//				[IsDeal] bit NULL ,
//				[SignalType] nvarchar(50) COLLATE Chinese_PRC_CI_AS NULL ,
//				CONSTRAINT [PK_sd_alarms] PRIMARY KEY ([ID])
//				)
//				ON [PRIMARY]
//				GO
//
//				DBCC CHECKIDENT(N'[dbo].[sd_alarms]', RESEED, 213193)
//				GO
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


	public OwnerDatas getOwnerDatas() {
		return ownerDatas;
	}


	public void setOwnerDatas(OwnerDatas ownerDatas) {
		this.ownerDatas = ownerDatas;
	}


	public Integer getDeviceIndex() {
		return deviceIndex;
	}


	public void setDeviceIndex(Integer deviceIndex) {
		this.deviceIndex = deviceIndex;
	}


	public Integer getDeviceNo() {
		return deviceNo;
	}


	public void setDeviceNo(Integer deviceNo) {
		this.deviceNo = deviceNo;
	}


	public Integer getIsTest() {
		return isTest;
	}


	public void setIsTest(Integer isTest) {
		this.isTest = isTest;
	}


	public Calendar getTime() {
		return time;
	}


	public void setTime(Calendar time) {
		this.time = time;
	}


	public String getAlarmStatus() {
		return alarmStatus;
	}


	public void setAlarmStatus(String alarmStatus) {
		this.alarmStatus = alarmStatus;
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


	public boolean isIsDeal() {
		return IsDeal;
	}


	public void setIsDeal(boolean isDeal) {
		IsDeal = isDeal;
	}


	public String getReserve() {
		return reserve;
	}


	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

}
