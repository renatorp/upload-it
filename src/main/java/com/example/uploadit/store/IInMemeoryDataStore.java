package com.example.uploadit.store;

import java.util.Optional;

import com.example.uploadit.entity.FileMetadata;

public interface IInMemeoryDataStore {

	Optional<FileMetadata> findMetadataFileById(String uuid);

	void insertMetadataFile(FileMetadata fileMetadata);

}
