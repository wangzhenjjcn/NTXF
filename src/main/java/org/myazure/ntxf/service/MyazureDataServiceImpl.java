package org.myazure.ntxf.service;

import java.util.List;

import org.myazure.ntxf.configuration.AppConfiguration;
import org.myazure.ntxf.domain.MyazureData;
import org.myazure.ntxf.repository.MyazureDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;


@Service
public class MyazureDataServiceImpl implements MyazureDataService{
	@Autowired
	private StringRedisTemplate redisTemplate;
	@Autowired
	private AppConfiguration appConfiguration;

	@Autowired
	private final MyazureDataRepository myazureDataRepository;
	
	@Autowired
	public MyazureDataServiceImpl(AppConfiguration appConfiguration,MyazureDataRepository myazureDataRepository) {
		this.appConfiguration = appConfiguration;
		this.myazureDataRepository=myazureDataRepository;
	}

	@Override
	public String getValue(String key) {
		MyazureData  data=myazureDataRepository.findOneByMkey(key);
		if (data==null) {
			return null;
		}
		String resault =data.getMvalue();
		return resault;
	}

	@Override
	public MyazureData getMyazureData(String key) {
		return myazureDataRepository.findOneByMkey(key);
	}

	@Override
	public MyazureData save(MyazureData myazureData) {
		return myazureDataRepository.save(myazureData);
	}
	
	@Override
	public MyazureData save(String key,String value) {
		return myazureDataRepository.save(new MyazureData(key,value));
	}

	@Override
	public List<MyazureData> findMyazureDataByMkeyStartingWith(String keyPreString) {
		System.err.println(keyPreString);
		return myazureDataRepository.findByMkeyStartingWith(keyPreString);
	}

	@Override
	public List<MyazureData> deleteAllMyazureDatasByKeyStartWith(
			String keyPreString) {
		List<MyazureData>  dataTodelete=myazureDataRepository.findByMkeyStartingWith(keyPreString);
		for (MyazureData myazureData : dataTodelete) {
			myazureDataRepository.delete(myazureData.getMkey());
		}
		return dataTodelete;
	}

	@Override
	public String getIDValue(String iDkey, String key) {
		MyazureData  data=myazureDataRepository.findOneByMkey(iDkey.replace("ID", key));
		if (data==null) {
			return null;
		}
		String resault =data.getMvalue();
		return resault;
	}
	@Override
	public String getIDValue(String iDkey, Integer key) {
		MyazureData  data=myazureDataRepository.findOneByMkey(iDkey.replace("ID", key+""));
		if (data==null) {
			return null;
		}
		String resault =data.getMvalue();
		return resault;
	}
	@Override
	public String getKeyPreValue(String preKey, String key) {
		MyazureData  data=myazureDataRepository.findOneByMkey(preKey+ key);
		if (data==null) {
			return null;
		}
		String resault =data.getMvalue();
		return resault;
	}

	@Override
	public MyazureData save(String key, Integer value) {
		return myazureDataRepository.save(new MyazureData(key,value+""));
	}
}
