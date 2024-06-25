package com.ishimweemmy.templates.springboot.v1.services;

import com.ishimweemmy.templates.springboot.v1.exceptions.InvalidFileException;
import com.ishimweemmy.templates.springboot.v1.models.File;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface IFileService {

    File getById(UUID id);

    File create(MultipartFile document, String directory);

    boolean delete(UUID id);

    String getFileExtension(String fileName);

    String handleFileName(String fileName, UUID id) throws InvalidFileException;

    boolean isValidExtension(String fileName) throws InvalidFileException;

    File getByName(String filename);
}
