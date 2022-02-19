package com.inboxapp.dao;

import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.inboxapp.entity.Folder;

@Repository
public interface FolderDao extends CassandraRepository<Folder, String> {

	public List<Folder> findByUserId(String userId);
}