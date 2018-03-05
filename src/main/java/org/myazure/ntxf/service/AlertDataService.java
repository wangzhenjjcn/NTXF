package org.myazure.ntxf.service;

import java.util.List;

import org.myazure.ntxf.domain.AlertData;

public interface AlertDataService {

	public int getLastMsgId();
	public List<AlertData> findNotMistakeNotTest();
	public AlertData findOneById(int id);
	public AlertData getLastAlertDataByOwnerCode(String ownerCode);
	
	
	
}
