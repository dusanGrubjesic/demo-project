package com.toptal.demo.demoproject.services;

import com.toptal.demo.demoproject.entities.MailEntity;
import com.toptal.demo.demoproject.entities.UserEntity;
import com.toptal.demo.demoproject.repo.MailRepository;
import org.joda.time.DateTime;
import org.joda.time.Months;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * @author dusan.grubjesic
 */
@Service
public class MailService {

	@Autowired
	private MailRepository mailRepository;

	private String generateRefNumber() {
		SecureRandom random = new SecureRandom();
		return new BigInteger(30, random).toString(32).toUpperCase();
	}

	public void generateMail(UserEntity userEntity) {
		String refNumber = generateRefNumber();
		MailEntity mailEntity = new MailEntity();
		mailEntity.setUserId(userEntity.getId());
		mailEntity.setRefNumber(refNumber);
		mailRepository.save(mailEntity);

		sendMail(userEntity, refNumber);
	}

	private void sendMail(UserEntity userEntity, String refNumber) {

	}

	public boolean validateMail(String refNumber) {
		return mailRepository.countByRefNumberIsGreaterThanEqual(refNumber, 1);
	}

	public boolean isNotOlderThan6Months(DateTime userVerificationMailCreationDateTime) {
		return Months.monthsBetween(
					DateTime.now(),
					userVerificationMailCreationDateTime)
				.getMonths() < 6;
	}
}
