package com.ricardo.traker.controller;

import com.ricardo.traker.model.dto.response.AlertResponse.AlertResponseDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/asset")
public interface AssetApi {

    @RequestMapping(value = "/{assetId}", method = RequestMethod.GET, produces = {"application/octet-stream"})
    ResponseEntity<byte[]> getAsset(@PathVariable String assetId);
}
