package org.myazure.ntxf.repository;

import java.util.List;

import org.myazure.ntxf.domain.AlertData;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface AlertDataRepository extends PagingAndSortingRepository<AlertData, Integer> {

	public AlertData findOneByOwnerCodeOrderByTime(String ownerCode);
	public List<AlertData> findByOwnerCode(String ownerCode);
	public AlertData findTop1ByOwnerCodeOrderByTimeDesc(String ownerCode);
	public AlertData findTop1ByOwnerCodeOrderByTimeAsc(String ownerCode);
	public List<AlertData> findByIsTestOrderByTimeDesc(int i);
	public AlertData findTop1ByIsTestOrderByIdDesc(int i);
	
}
