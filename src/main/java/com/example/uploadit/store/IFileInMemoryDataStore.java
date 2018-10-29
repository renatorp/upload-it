package com.example.uploadit.store;

import java.util.List;
import java.util.Optional;

import com.example.uploadit.entity.FileMetadata;

public interface IFileInMemoryDataStore {

	Optional<FileMetadata> findMetadataFileById(String uuid);

	void insertMetadataFile(FileMetadata fileMetadata);

	Optional<FileMetadata> findMetadataFileByUserAndFileName(String fileName, String userId);

	void deleteFileMetadataByUserIdAndFileName(String originalFilename, String userId);

	List<FileMetadata> findAllFilesMetadata();

	List<FileMetadata> findDirtyFilesMetadataByUser(String userId);
}
