package com.toptal.demo.demoproject.services;

import com.toptal.demo.demoproject.entities.MailEntity;
import com.toptal.demo.demoproject.entities.UserEntity;
import com.toptal.demo.demoproject.repo.MailRepository;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.Months;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Timestamp;

/**
 * @author dusan.grubjesic
 */
@Service
public class MailService {

	@Autowired
	private MailRepository mailRepository;

	@Autowired
	private UserService userService;

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
		if (mailRepository.existsMailEntityByRefNumber(refNumber)) {
			MailEntity mailEntity = mailRepository.findByRefNumber(refNumber);
			if (isNotOlderThan6Months(mailEntity.getCreationDate())) {
				userService.confirmEmail(mailEntity.getUserId());
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public boolean isNotOlderThan6Months(Timestamp userVerificationMailCreationDateTime) {

		return Months.monthsBetween(
				DateTime.now(),
				LocalDateTime.fromDateFields(userVerificationMailCreationDateTime).toDateTime()
		)
				.getMonths() < 6;
	}

	public void resendVerificationMail(String mail) {
	}
}
