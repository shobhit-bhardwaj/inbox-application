package com.inboxapp.entity;

import java.util.List;

import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Table(value = "emails_by_user_folder")
public class EmailList {

	@PrimaryKey
	private EmailListKey key;

	@CassandraType(type = Name.LIST, typeArguments = Name.TEXT)
	private List<String> to;

	@CassandraType(type = Name.TEXT)
	private String subject;

	@CassandraType(type = Name.BOOLEAN)
	private boolean unread;

	@Transient
	private String prettyAgoTime;
}