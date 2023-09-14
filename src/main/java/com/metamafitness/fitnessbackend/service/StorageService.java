package com.metamafitness.fitnessbackend.service;


import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface StorageService {

    public String save(final MultipartFile multipartFile);

    public S3ObjectInputStream findByName(String fileName);

    void delete(final String fileName);

    List<String> storeFiles(List<MultipartFile> multipartFiles);
}