package com.method.iot.webapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceStateModel {
    @JsonProperty("state")
    private State state;
    @JsonProperty("illumination")
    private Illumination illumination;
}
