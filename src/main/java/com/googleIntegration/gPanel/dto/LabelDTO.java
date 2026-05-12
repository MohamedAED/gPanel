package com.googleIntegration.gPanel.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LabelDTO {

    private String id;
    private String name;
    private String type;
    private String labelListVisibility;
    private String messageListVisibility;

}
