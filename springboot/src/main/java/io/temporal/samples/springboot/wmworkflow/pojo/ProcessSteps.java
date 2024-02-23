package io.temporal.samples.springboot.wmworkflow.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProcessSteps {
  CREATED("Created"),
  REVIEW("REVIEWED"),
  OPS_APPROVED("OPSAPPROVED"),
  BRANCHAPPROVED("BRANCHAPPROVED"),
  COMPLETED("COMPLETED");

  private final String value;
}
