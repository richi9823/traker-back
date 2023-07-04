package com.ricardo.traker.service;

import com.ricardo.traker.model.entity.ImageEntity;
import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    void uploadImage(MultipartFile file) throws IOException;

    @Transactional
    ImageEntity getImageById(String id);

    @Transactional
    byte[] getImage(String id);
}
