package com.toptal.demo.demoproject.services;

import com.toptal.demo.demoproject.entities.MailEntity;
import com.toptal.demo.demoproject.entities.UserEntity;
import com.toptal.demo.demoproject.repo.MailRepository;
import com.toptal.demo.demoproject.repo.UserInfoRepository;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.Months;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Optional;

/**
 * @author dusan.grubjesic
 */
@Service
public class MailService {

	@Autowired
	private MailRepository mailRepository;
	@Autowired
	private UserInfoRepository userInfoRepository;

	@Value("${base.api.url}")
	private String baseApiUrl;
	@Value("${verfication.mail.url}")
	private String verificationMailUrl;


	@Autowired
	private UserService userService;

	private String generateRefNumber() {
		SecureRandom random = new SecureRandom();
		return new BigInteger(60, random).toString(32).toUpperCase();
	}

	public void generateMail(UserEntity userEntity) {
		String refNumber = generateRefNumber();
		MailEntity mailEntity = new MailEntity();
		mailEntity.setUserId(userEntity.getId());
		mailEntity.setRefNumber(refNumber);
		mailRepository.save(mailEntity);

		sendVerificationMail(userEntity.getId(), refNumber);
	}

	private void sendVerificationMail(int userId, String refNumber) {
		Optional<UserEntity> userEntity = userInfoRepository.findById(userId);
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		userEntity.ifPresent(s -> {
			simpleMailMessage.setTo(s.getEmail());
			simpleMailMessage.setText(baseApiUrl+verificationMailUrl+refNumber);
			simpleMailMessage.setSubject("Demo project verification mail");
		});
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
		String newRefNumber = generateRefNumber();
		Optional<UserEntity> optionalUserEntity = userInfoRepository.findByEmail(mail);

		optionalUserEntity
				.flatMap(b -> mailRepository.findById(b.getId()))
				.ifPresent(s -> {
					s.setRefNumber(newRefNumber);
					mailRepository.save(s);
					sendVerificationMail(s.getUserId(), newRefNumber);
				});
	}
}
