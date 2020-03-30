package com.toptal.demo.demoproject.entities;

import lombok.Data;
import org.joda.time.DateTime;

import javax.persistence.Entity;
import javax.persistence.Id;


/**
 * @author dusan.grubjesic
 */
@Entity(name = "mail")
@Data
public class MailEntity {

	@Id
	private int userId;
	private String refNumber;
	private DateTime creationDate;
}
