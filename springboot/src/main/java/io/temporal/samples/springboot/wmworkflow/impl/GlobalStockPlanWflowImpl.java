package io.temporal.samples.springboot.wmworkflow.impl;

import io.temporal.activity.ActivityOptions;
import io.temporal.samples.springboot.wmworkflow.ElasticSearchIndexActivity;
import io.temporal.samples.springboot.wmworkflow.GlobalStockPlanWflow;
import io.temporal.samples.springboot.wmworkflow.pojo.GlobalSPlanRequest;
import io.temporal.samples.springboot.wmworkflow.pojo.GlobalSplanInfo;
import io.temporal.samples.springboot.wmworkflow.pojo.ProcessSteps;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;
import java.time.Duration;

@WorkflowImpl(taskQueues = "globalstockworkflowTask")
public class GlobalStockPlanWflowImpl implements GlobalStockPlanWflow {

  private ElasticSearchIndexActivity stageDataToElastic =
      Workflow.newActivityStub(
          ElasticSearchIndexActivity.class,
          ActivityOptions.newBuilder().setStartToCloseTimeout(Duration.ofSeconds(2)).build());

  private GlobalSplanInfo globalSplanInfo = new GlobalSplanInfo();

  @Override
  public void start(GlobalSPlanRequest request) {
    globalSplanInfo.setClientId(request.getClientId());
    globalSplanInfo.setClientName(request.getClientName());
    globalSplanInfo.setSteps(ProcessSteps.CREATED);
    Workflow.await(() -> globalSplanInfo.getSteps().equals(ProcessSteps.REVIEW)); // 2 days   Rejected
    stageDataToElastic.stageDataToElastic(globalSplanInfo);
    Workflow.await(() -> globalSplanInfo.getSteps().equals(ProcessSteps.OPS_APPROVED));
    stageDataToElastic.stageDataToElastic(globalSplanInfo);
    Workflow.await(() -> globalSplanInfo.getSteps().equals(ProcessSteps.BRANCHAPPROVED));
    stageDataToElastic.stageDataToElastic(globalSplanInfo);
    globalSplanInfo.setSteps(ProcessSteps.COMPLETED);
    stageDataToElastic.stageDataToElastic(globalSplanInfo);
  }

  @Override
  public GlobalSplanInfo getState() {
    return globalSplanInfo;
  }

  @Override
  public void updateState(ProcessSteps step, String data) {
    globalSplanInfo.setSteps(step);
    globalSplanInfo.setData(data);
  }
}
