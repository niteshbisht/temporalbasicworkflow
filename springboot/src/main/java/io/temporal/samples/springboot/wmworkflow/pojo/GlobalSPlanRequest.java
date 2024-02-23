package io.temporal.samples.springboot.wmworkflow.pojo;

import lombok.Data;

@Data
public class GlobalSPlanRequest {
  private String clientName;
  private String clientId;
}
