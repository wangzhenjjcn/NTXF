package org.myazure.ntxf.service;

import java.util.List;

import org.myazure.ntxf.configuration.AppConfiguration;
import org.myazure.ntxf.domain.AlertData;
import org.myazure.ntxf.domain.WarningDatas;
import org.myazure.ntxf.repository.AlertDataRepository;
import org.myazure.ntxf.repository.WarningDatasRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import weixin.popular.api.MessageAPI;
import weixin.popular.bean.BaseResult;
import weixin.popular.bean.message.message.TextMessage;
import weixin.popular.support.TokenManager;

@Service
@Transactional
public class AlertSenderServiceImpl implements AlertDataService {
	private static final Logger LOG = LoggerFactory
			.getLogger(AlertSenderServiceImpl.class);
	@Autowired
	private final AlertDataRepository alertDataRepository;


	@Autowired
	public AlertSenderServiceImpl(AlertDataRepository alertDataRepository) {
		this.alertDataRepository = alertDataRepository;
	}

	@Override
	public int getLastMsgId() {
		AlertData alertData = alertDataRepository
				.findTop1ByIsTestOrderByIdDesc(0);
		return alertData == null ? 0 : alertData.getId();
	}
	@Override
	public List<AlertData> findNotMistakeNotTest() {
		return alertDataRepository.findByIsTestOrderByTimeDesc(0);
	}

	@Override
	public AlertData findOneById(int id) {
		return alertDataRepository.findOne(id);
	}

	@Override
	public AlertData getLastAlertDataByOwnerCode(String ownerCode) {
		return   alertDataRepository
				.findTop1ByOwnerCodeOrderByTimeDesc(ownerCode);
	}


}
