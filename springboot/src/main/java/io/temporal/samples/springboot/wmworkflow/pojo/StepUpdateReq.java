package io.temporal.samples.springboot.wmworkflow.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StepUpdateReq {
  ProcessSteps processSteps;
  String data;
}
