package io.temporal.samples.springboot.wmworkflow;

import io.temporal.samples.springboot.wmworkflow.pojo.GlobalSPlanRequest;
import io.temporal.samples.springboot.wmworkflow.pojo.GlobalSplanInfo;
import io.temporal.samples.springboot.wmworkflow.pojo.ProcessSteps;
import io.temporal.workflow.QueryMethod;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface GlobalStockPlanWflow {

  @WorkflowMethod
  void start(GlobalSPlanRequest request);

  @QueryMethod
  GlobalSplanInfo getState();

  @SignalMethod
  public void updateState(ProcessSteps step, String data);
}
