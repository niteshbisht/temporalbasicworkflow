package io.temporal.samples.springboot.wmworkflow.pojo;

import lombok.Data;

@Data
public class GlobalSplanInfo {
  private ProcessSteps steps;
  private String clientId;
  private String clientName;
  private String data;
}
