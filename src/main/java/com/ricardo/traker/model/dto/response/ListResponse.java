package com.ricardo.traker.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class ListResponse<classtype> {
    @JsonProperty("total")
    private long total;

    @JsonProperty("items")
    private List<classtype> items;
}
