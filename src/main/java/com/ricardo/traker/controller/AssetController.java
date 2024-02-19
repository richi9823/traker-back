package com.ricardo.traker.controller;

import com.ricardo.traker.model.entity.ImageEntity;
import com.ricardo.traker.service.ImageService;
import com.ricardo.traker.util.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class AssetController implements AssetApi {

    @Autowired
    ImageService imageService;

    @Override
    public ResponseEntity<byte[]> getAsset(String assetId) {
        ImageEntity image = imageService.getImageById(assetId);
        byte[] asset = imageService.getImage(assetId);
        try {
            HttpHeaders responseHeaders = HttpUtils.getFileDownloadHeaders(asset, image.getName(), image.getType());
            return ResponseEntity.ok().headers(responseHeaders).body(asset);
        } catch (IOException e) {
            throw new RuntimeException("I/O Exception in getAsset()");
        }
    }
}
