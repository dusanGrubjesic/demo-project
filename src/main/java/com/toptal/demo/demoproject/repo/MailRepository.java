package com.toptal.demo.demoproject.repo;

import com.toptal.demo.demoproject.entities.MailEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * @author dusan.grubjesic
 */
public interface MailRepository extends CrudRepository<MailEntity, Integer> {
	boolean countByRefNumberIsGreaterThanEqual(String refNumber, int x);

}
