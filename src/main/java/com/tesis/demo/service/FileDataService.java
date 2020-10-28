package com.tesis.demo.service;

import com.tesis.demo.model.FileData;
import com.tesis.demo.model.Header;
import com.tesis.demo.repository.FileDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileDataService {

    private final FileDataRepository fileDataRepository;

    public FileData save(FileData fileData) {
        return fileDataRepository.save(fileData);
    }

    public FileData saveFileData(String originalFilename, List<Header> header) {
        FileData fileData = FileData.builder()
                .fileName(originalFilename)
                .header(header)
                .build();

        return save(fileData);
    }

    public List<FileData> getAllFileData() {
        return fileDataRepository.findAll();
    }

}
