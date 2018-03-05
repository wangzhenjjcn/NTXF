package org.myazure.ntxf.service;

import org.myazure.ntxf.domain.OwnerDatas;
import org.myazure.ntxf.repository.OwnerDatasRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

/**
 * @author Wang Zhen <A.Hleb.King wangzhenjjcn@gmail.com>
 * @since 2018年1月7日 下午7:52:59
 */
@Service
public class OwnerDatasServiceImpl implements OwnerDatasService {
	@Autowired
	private final OwnerDatasRepository ownerDatasRepository;

	private static final Logger LOG = LoggerFactory
			.getLogger(OwnerDatasServiceImpl.class);

	@Autowired
	public OwnerDatasServiceImpl(OwnerDatasRepository ownerDatasRepository) {
		this.ownerDatasRepository = ownerDatasRepository;
	}

	@Override
	public String getCompanyNameByOwnerCode(String ownerCode) {
		return ownerDatasRepository.findTop1ByOwnerCode(ownerCode)
				.getOwnerName();
	}

	@Override
	public String getCompanyNameByAdminunit(String adminunit) {
		if (ownerDatasRepository
				.findTop1ByAdminunitOrderByOwnerCodeDesc(adminunit) == null) {
			return "";
		}
		return ownerDatasRepository.findTop1ByAdminunitOrderByOwnerCodeDesc(
				adminunit).getOwnerName();
	}

	@Override
	public String getOwnerCodeByAdminunit(String adminunit) {
		return ownerDatasRepository.findTop1ByAdminunitOrderByOwnerCodeDesc(
				adminunit).getOwnerCode();
	}

	@Override
	public String getAdminunitByOwnerCode(String ownerCode) {
		return ownerDatasRepository.findTop1ByOwnerCodeOrderByAdminunitDesc(
				ownerCode).getAdminunit();
	}

	@Override
	public OwnerDatas findOneByOwnerCode(String ownerCode) {
		return ownerDatasRepository
				.findTop1ByOwnerCodeOrderByAdminunitDesc(ownerCode);
	}

}
