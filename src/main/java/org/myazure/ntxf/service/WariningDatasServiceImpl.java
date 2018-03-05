package org.myazure.ntxf.service;

import java.util.List;

import org.myazure.ntxf.domain.WarningDatas;
import org.myazure.ntxf.repository.WarningDatasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Wang Zhen <A.Hleb.King wangzhenjjcn@gmail.com>
 * @since 2018年1月3日 下午1:33:35
 */
@Service
public class WariningDatasServiceImpl implements WarningDatasService {
	@Autowired
	private final WarningDatasRepository warningDatasRepository;

	@Autowired
	public WariningDatasServiceImpl(
			WarningDatasRepository warningDatasRepository) {
		this.warningDatasRepository = warningDatasRepository;
	}

	@Override
	public WarningDatas getLastWarningDatasByOwnerCode(String ownerCode) {
		return warningDatasRepository
				.findTop1ByOwnerCodeOrderByTimeDesc(ownerCode);
	}

	@Override
	public int getLastMsgId() {
		WarningDatas warningDatas = warningDatasRepository
				.findTop1ByIsTestOrderByIdDesc(0);
		return warningDatas == null ? 0 : warningDatas.getId();
	}

	@Override
	public WarningDatas findOneById(int id) {
		return warningDatasRepository.findOne(id);
	}

	@Override
	public List<WarningDatas> findNotMistakeNotTest() {
		return warningDatasRepository.findByIsMistakeAndIsTestOrderByTimeDesc(
				0, 0);
	}

}
