package com.inboxapp.entity;

import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Table(value = "emails_by_id")
public class Email {

	@Id
	@PrimaryKeyColumn(name = "emailId", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private UUID emailId;

	@CassandraType(type = Name.TEXT)
	private String from;

	@CassandraType(type = Name.LIST, typeArguments = Name.TEXT)
	private List<String> to;

	@CassandraType(type = Name.TEXT)
	private String subject;

	@CassandraType(type = Name.TEXT)
	private String body;
}