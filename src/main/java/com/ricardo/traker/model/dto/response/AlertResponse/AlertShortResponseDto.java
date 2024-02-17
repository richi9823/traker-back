package com.ricardo.traker.model.dto.response.AlertResponse;


import com.ricardo.traker.enums.TypeAlertEnum;
import lombok.Data;

@Data
public class AlertShortResponseDto {

    private Long id;

    private String name;

    private Boolean silenced;

    private TypeAlertEnum type;

}
