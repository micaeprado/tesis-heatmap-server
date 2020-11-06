package com.tesis.demo.service;

import com.tesis.demo.model.FileData;
import com.tesis.demo.model.Header;
import com.tesis.demo.model.enumeration.ObjectType;
import com.tesis.demo.repository.FileDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

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

    public ObjectType getFieldObjectType(String field, String fileName) {
        List<FileData> fileDataList = fileDataRepository.findByFileName(fileName);
        if(fileDataList != null) {
            FileData fileData = fileDataList.get(0);
            Header header = fileData.getHeader()
                    .stream()
                    .filter(header1 -> header1.getHeader().equals(field))
                    .collect(Collectors.toList())
                    .get(0);
            return header.getObjectType();
        } else {
            throw new EntityNotFoundException("there is no file with that name");
        }
    }
}
