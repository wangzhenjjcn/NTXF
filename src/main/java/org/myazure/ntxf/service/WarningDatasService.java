package org.myazure.ntxf.service;

import java.util.List;

import org.myazure.ntxf.domain.WarningDatas;

/**
 * @author Wang Zhen <A.Hleb.King wangzhenjjcn@gmail.com>
 * @since 2018年1月3日 下午1:31:44
 */

public interface WarningDatasService {
	public WarningDatas getLastWarningDatasByOwnerCode(String ownerCode);

	public WarningDatas findOneById(int id);

	public List<WarningDatas> findNotMistakeNotTest();

	public int getLastMsgId();

}
