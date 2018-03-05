package org.myazure.ntxf.repository;

import org.myazure.ntxf.domain.OwnerDatas;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Wang Zhen <A.Hleb.King wangzhenjjcn@gmail.com>
 * @since 2018年1月7日 下午7:48:14
 */
@Repository
public interface OwnerDatasRepository extends
		PagingAndSortingRepository<OwnerDatas, Integer> {
	public OwnerDatas findTop1ByOwnerCode(String ownerCode);

	public OwnerDatas findTop1ByAdminunitOrderByOwnerCodeDesc(String adminunit);

	public OwnerDatas findTop1ByOwnerCodeOrderByAdminunitDesc(String ownerCode);

	public OwnerDatas findOneById(int id);

}
