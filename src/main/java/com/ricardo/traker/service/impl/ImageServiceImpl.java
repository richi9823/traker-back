package com.ricardo.traker.service.impl;

import com.ricardo.traker.model.entity.ImageEntity;
import com.ricardo.traker.repository.ImageRepository;
import com.ricardo.traker.service.ImageService;
import com.ricardo.traker.util.ImageUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public void uploadImage(MultipartFile file) throws IOException {

        imageRepository.save(ImageEntity.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtil.compressImage(file.getBytes())).build());

    }

    @Override
    @Transactional
    public ImageEntity getImageById(String id) {
        Optional<ImageEntity> dbImage = imageRepository.findById(id);

        return ImageEntity.builder()
                .name(dbImage.get().getName())
                .type(dbImage.get().getType())
                .imageData(ImageUtil.decompressImage(dbImage.get().getImageData())).build();

    }

    @Override
    @Transactional
    public byte[] getImage(String id) {
        Optional<ImageEntity> dbImage = imageRepository.findById(id);
        byte[] image = ImageUtil.decompressImage(dbImage.get().getImageData());
        return image;
    }
}
