package com.toptal.demo.demoproject.repo;

import com.toptal.demo.demoproject.entities.UserEntity;
import com.toptal.demo.demoproject.management.UserStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author dusan.grubjesic
 */
@Repository
public interface UserInfoRepository extends CrudRepository<UserEntity, Integer> {

	Optional<UserEntity> findByUserNameEquals(String userName);

	default void updateStatus(int userId, UserStatus userStatus) {
		Optional<UserEntity> userEntity = findById(userId);
		userEntity.ifPresent(s -> {
			s.setStatus(userStatus);
			save(s);
		});
	}

	boolean existsUserEntityByUserName(String userName);
}
