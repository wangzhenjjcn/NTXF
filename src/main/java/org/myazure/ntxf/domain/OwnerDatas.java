package org.myazure.ntxf.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Wang Zhen <A.Hleb.King wangzhenjjcn@gmail.com>
 * @since 2018年1月7日 下午7:27:34
 */

@Entity
@Table(name = "sd_ownerdetail")
public class OwnerDatas {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private int id;

	@Column(name = "ownercode", nullable = false, length = 50, columnDefinition = "nvarchar")
	private String ownerCode;

	@Column(name = "Ownername", columnDefinition = "nvarchar")
	private String ownerName;

	@Column(name = "Address", columnDefinition = "nvarchar")
	private String Address;

	@Column(name = "adminunit", columnDefinition = "nvarchar")
	private String adminunit;

	// [OwnerCode] nvarchar(50) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	// [OwnerName] nvarchar(50) COLLATE Chinese_PRC_CI_AS NULL ,
	// ID
	// OwnerCode[OwnerCode] nvarchar(50) COLLATE Chinese_PRC_CI_AS NOT NULL ,
	// OwnerName[OwnerName] nvarchar(50) COLLATE Chinese_PRC_CI_AS NULL ,
	// Address[Address] nvarchar(50) COLLATE Chinese_PRC_CI_AS NULL ,
	// Phone
	// adminunit [adminunit] nvarchar(70) COLLATE Chinese_PRC_CI_AS NULL ,
	// IsLinked[IsLinked] bit NULL ,

	public OwnerDatas() {
		// CREATE TABLE [dbo].[sd_ownerdetail] (
		// [ID] int NOT NULL IDENTITY(1,1) ,
		// [OwnerCode] nvarchar(50) COLLATE Chinese_PRC_CI_AS NOT NULL ,
		// [OwnerName] nvarchar(50) COLLATE Chinese_PRC_CI_AS NULL ,
		// [CorporationID] int NULL ,
		// [Address] nvarchar(50) COLLATE Chinese_PRC_CI_AS NULL ,
		// [ProvinceID] nvarchar(250) COLLATE Chinese_PRC_CI_AS NULL ,
		// [Phone] nvarchar(50) COLLATE Chinese_PRC_CI_AS NULL ,
		// [ContactorID] int NULL ,
		// [Brief] text COLLATE Chinese_PRC_CI_AS NULL ,
		// [Logo] image NULL ,
		// [orgnization] nvarchar(20) COLLATE Chinese_PRC_CI_AS NULL ,
		// [enterprisetype] int NULL ,
		// [dutyname] nvarchar(20) COLLATE Chinese_PRC_CI_AS NULL ,
		// [dutyidcard] nvarchar(18) COLLATE Chinese_PRC_CI_AS NULL ,
		// [dutyphone] nvarchar(20) COLLATE Chinese_PRC_CI_AS NULL ,
		// [managername] nvarchar(30) COLLATE Chinese_PRC_CI_AS NULL ,
		// [manageridcard] nvarchar(18) COLLATE Chinese_PRC_CI_AS NULL ,
		// [managerphone] nvarchar(20) COLLATE Chinese_PRC_CI_AS NULL ,
		// [legalname] nvarchar(30) COLLATE Chinese_PRC_CI_AS NULL ,
		// [legalidcard] nvarchar(20) COLLATE Chinese_PRC_CI_AS NULL ,
		// [legalphone] nvarchar(20) COLLATE Chinese_PRC_CI_AS NULL ,
		// [employees] int NULL ,
		// [publishdate] datetime NULL ,
		// [superunit] nvarchar(70) COLLATE Chinese_PRC_CI_AS NULL ,
		// [adminunit] nvarchar(70) COLLATE Chinese_PRC_CI_AS NULL ,
		// [owership] int NULL ,
		// [assets] float(53) NULL ,
		// [floorarea] float(53) NULL ,
		// [totalarea] float(53) NULL ,
		// [totalNodes] int NULL ,
		// [SafeLevel] int NULL ,
		// [ShortMessage] nvarchar(255) COLLATE Chinese_PRC_CI_AS NULL ,
		// [ShortMessageItems] nvarchar(30) COLLATE Chinese_PRC_CI_AS NULL ,
		// [VedioConfig] nvarchar(250) COLLATE Chinese_PRC_CI_AS NULL ,
		// [CheckRate] float(53) NULL ,
		// [GoodRate] float(53) NULL ,
		// [RxEmail] nvarchar(250) COLLATE Chinese_PRC_CI_AS NULL ,
		// [SafeMonitorLevel] int NULL ,
		// [OwnerCategory] int NULL ,
		// [UseCategory] int NULL ,
		// [TechRepresent] int NULL ,
		// [ClientRepresent] int NULL ,
		// [IsLinked] bit NULL ,
		// [reserve] nvarchar(255) COLLATE Chinese_PRC_CI_AS NULL ,
		// [Lastupdate] datetime NULL ,
		// [InstallInfoSheet] nvarchar(50) COLLATE Chinese_PRC_CI_AS NULL ,
		// [InNetProtocol] nvarchar(50) COLLATE Chinese_PRC_CI_AS NULL ,
		// [InterfaceInfoDesc] nvarchar(1024) COLLATE Chinese_PRC_CI_AS NULL ,
		// [ControlInfoDesc] nvarchar(1024) COLLATE Chinese_PRC_CI_AS NULL ,
		// [MonitorInfoDesc] nvarchar(1024) COLLATE Chinese_PRC_CI_AS NULL ,
		// [IsKPI] int NULL ,
		// [RegionEx] int NULL ,
		// CONSTRAINT [PK_sd_ownerdetail] PRIMARY KEY ([ID])
		// )
		// ON [PRIMARY]
		// TEXTIMAGE_ON [PRIMARY]
		// GO
		//
		// DBCC CHECKIDENT(N'[dbo].[sd_ownerdetail]', RESEED, 24)
		// GO
		//
		// IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description',
		// 'SCHEMA', N'dbo',
		// 'TABLE', N'sd_ownerdetail',
		// 'COLUMN', N'InstallInfoSheet')) > 0)
		// EXEC sp_updateextendedproperty @name = N'MS_Description', @value =
		// N'安装信息表 有无'
		// , @level0type = 'SCHEMA', @level0name = N'dbo'
		// , @level1type = 'TABLE', @level1name = N'sd_ownerdetail'
		// , @level2type = 'COLUMN', @level2name = N'InstallInfoSheet'
		// ELSE
		// EXEC sp_addextendedproperty @name = N'MS_Description', @value =
		// N'安装信息表 有无'
		// , @level0type = 'SCHEMA', @level0name = N'dbo'
		// , @level1type = 'TABLE', @level1name = N'sd_ownerdetail'
		// , @level2type = 'COLUMN', @level2name = N'InstallInfoSheet'
		// GO
		//
		// IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description',
		// 'SCHEMA', N'dbo',
		// 'TABLE', N'sd_ownerdetail',
		// 'COLUMN', N'InNetProtocol')) > 0)
		// EXEC sp_updateextendedproperty @name = N'MS_Description', @value =
		// N'入网协议 有无'
		// , @level0type = 'SCHEMA', @level0name = N'dbo'
		// , @level1type = 'TABLE', @level1name = N'sd_ownerdetail'
		// , @level2type = 'COLUMN', @level2name = N'InNetProtocol'
		// ELSE
		// EXEC sp_addextendedproperty @name = N'MS_Description', @value =
		// N'入网协议 有无'
		// , @level0type = 'SCHEMA', @level0name = N'dbo'
		// , @level1type = 'TABLE', @level1name = N'sd_ownerdetail'
		// , @level2type = 'COLUMN', @level2name = N'InNetProtocol'
		// GO
		//
		// IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description',
		// 'SCHEMA', N'dbo',
		// 'TABLE', N'sd_ownerdetail',
		// 'COLUMN', N'InterfaceInfoDesc')) > 0)
		// EXEC sp_updateextendedproperty @name = N'MS_Description', @value =
		// N'接口描述信息'
		// , @level0type = 'SCHEMA', @level0name = N'dbo'
		// , @level1type = 'TABLE', @level1name = N'sd_ownerdetail'
		// , @level2type = 'COLUMN', @level2name = N'InterfaceInfoDesc'
		// ELSE
		// EXEC sp_addextendedproperty @name = N'MS_Description', @value =
		// N'接口描述信息'
		// , @level0type = 'SCHEMA', @level0name = N'dbo'
		// , @level1type = 'TABLE', @level1name = N'sd_ownerdetail'
		// , @level2type = 'COLUMN', @level2name = N'InterfaceInfoDesc'
		// GO
		//
		// IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description',
		// 'SCHEMA', N'dbo',
		// 'TABLE', N'sd_ownerdetail',
		// 'COLUMN', N'ControlInfoDesc')) > 0)
		// EXEC sp_updateextendedproperty @name = N'MS_Description', @value =
		// N'控制器信息描述'
		// , @level0type = 'SCHEMA', @level0name = N'dbo'
		// , @level1type = 'TABLE', @level1name = N'sd_ownerdetail'
		// , @level2type = 'COLUMN', @level2name = N'ControlInfoDesc'
		// ELSE
		// EXEC sp_addextendedproperty @name = N'MS_Description', @value =
		// N'控制器信息描述'
		// , @level0type = 'SCHEMA', @level0name = N'dbo'
		// , @level1type = 'TABLE', @level1name = N'sd_ownerdetail'
		// , @level2type = 'COLUMN', @level2name = N'ControlInfoDesc'
		// GO
		//
		// IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description',
		// 'SCHEMA', N'dbo',
		// 'TABLE', N'sd_ownerdetail',
		// 'COLUMN', N'MonitorInfoDesc')) > 0)
		// EXEC sp_updateextendedproperty @name = N'MS_Description', @value =
		// N'监控范围描述'
		// , @level0type = 'SCHEMA', @level0name = N'dbo'
		// , @level1type = 'TABLE', @level1name = N'sd_ownerdetail'
		// , @level2type = 'COLUMN', @level2name = N'MonitorInfoDesc'
		// ELSE
		// EXEC sp_addextendedproperty @name = N'MS_Description', @value =
		// N'监控范围描述'
		// , @level0type = 'SCHEMA', @level0name = N'dbo'
		// , @level1type = 'TABLE', @level1name = N'sd_ownerdetail'
		// , @level2type = 'COLUMN', @level2name = N'MonitorInfoDesc'
		// GO
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

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getAdminunit() {
		return adminunit;
	}

	public void setAdminunit(String adminunit) {
		this.adminunit = adminunit;
	}

}
