package com.toptal.demo.demoproject;

import com.toptal.demo.demoproject.entities.UserEntity;
import com.toptal.demo.demoproject.entities.projections.UserInfoUserNameProjection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dusan.grubjesic
 */
@Repository
public interface UserInfoRepository extends CrudRepository<UserEntity, Integer> {
	UserEntity findByUserName(String userName);

	List<UserInfoUserNameProjection> findAllByUserName(String userName);
}
