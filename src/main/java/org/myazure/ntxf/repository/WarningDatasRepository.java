package org.myazure.ntxf.repository;

import java.util.List;

import org.myazure.ntxf.domain.WarningDatas;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarningDatasRepository extends
		PagingAndSortingRepository<WarningDatas, Integer> {

	public WarningDatas findOneByOwnerCodeOrderByTime(String ownerCode);

	public List<WarningDatas> findByOwnerCode(String ownerCode);

	public WarningDatas findTop1ByOwnerCodeOrderByTimeDesc(String ownerCode);

	public WarningDatas findTop1ByOwnerCodeOrderByTimeAsc(String ownerCode);

	public WarningDatas findTop1ByIsTestOrderByIdDesc(int isTest);

	public List<WarningDatas> findByIsMistakeAndIsTestOrderByTimeDesc(
			Integer isMistake, Integer isTest);
}
