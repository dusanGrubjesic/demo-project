package com.toptal.demo.demoproject.entities;

import lombok.Data;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.sql.Timestamp;


/**
 * @author dusan.grubjesic
 */
@Entity(name = "mail")
@Data
public class MailEntity {

	@Id
	private int userId;

	@Column(name = "reference_number")
	private String refNumber;

	@Column(insertable = false)
	private Timestamp creationDate;
}
