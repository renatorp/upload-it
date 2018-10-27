package com.example.uploadit.store.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.uploadit.entity.FileMetadata;
import com.example.uploadit.store.IInMemeoryDataStore;

@Component
public class InMemoryDataStore implements IInMemeoryDataStore {

	private static Map<String, FileMetadata> fileStore = new HashMap<>();

	@Override
	public Optional<FileMetadata> findMetadataFileById(String uuid) {
		return Optional.ofNullable(fileStore.get(uuid));
	}

	@Override
	public void insertMetadataFile(FileMetadata fileMetadata) {
		fileStore.put(fileMetadata.getId(), fileMetadata);
	}
	
}
