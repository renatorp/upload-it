package com.example.uploadit.store.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.uploadit.entity.FileMetadata;
import com.example.uploadit.store.IFileInMemoryDataStore;

@Component
public class FileInMemoryDataStore implements IFileInMemoryDataStore {

	private static final List<FileMetadata> fileStore = new ArrayList<>();

	@Override
	public Optional<FileMetadata> findMetadataFileById(String uuid) {
		return fileStore.stream().filter(m -> m.getId().equals(uuid)).findFirst();
	}

	@Override
	public void insertMetadataFile(FileMetadata fileMetadata) {
		fileStore.add(fileMetadata);
	}

	@Override
	public Optional<FileMetadata> findMetadataFileByUserAndFileName(String fileName, String userId) {
		return fileStore.stream().filter(m -> m.getFileName().equals(fileName) && m.getUserId().equals(userId))
				.findFirst();
	}

	@Override
	public void deleteFileMetadataByUserIdAndFileName(String fileName, String userId) {
		fileStore.removeIf(m -> m.getFileName().equals(fileName) && m.getUserId().equals(userId));
	}

	@Override
	public List<FileMetadata> findAllFilesMetadata() {
		return fileStore;
	}
	
	@Override
	public List<FileMetadata> findDirtyFilesMetadataByUser(String userId) {
		return fileStore.stream()
					.filter(m -> m.getUserId().equals(userId) && m.isDirty())
					.collect(Collectors.toList());
	}
}
