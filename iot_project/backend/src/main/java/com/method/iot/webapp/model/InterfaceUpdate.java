package com.method.iot.webapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InterfaceUpdate {
    private Action action;
    @JsonProperty("success")
    private boolean success;
    @JsonProperty("ids")
    private List<String> ids;
}
