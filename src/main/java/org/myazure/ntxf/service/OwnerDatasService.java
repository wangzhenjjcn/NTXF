package org.myazure.ntxf.service;

import org.myazure.ntxf.domain.OwnerDatas;

/**
 * @author Wang Zhen <A.Hleb.King wangzhenjjcn@gmail.com>
 * @since 2018年1月7日 下午7:50:59
 */

public interface OwnerDatasService {
	public String getCompanyNameByOwnerCode(String ownerCode);

	public String getCompanyNameByAdminunit(String adminunit);

	public String getOwnerCodeByAdminunit(String adminunit);

	public String getAdminunitByOwnerCode(String ownerCode);

	public OwnerDatas findOneByOwnerCode(String ownerCode);
}
