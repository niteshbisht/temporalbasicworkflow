package io.temporal.samples.springboot.wmworkflow.services;

import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.samples.springboot.wmworkflow.GlobalStockPlanWflow;
import io.temporal.samples.springboot.wmworkflow.pojo.GlobalSPlanRequest;
import io.temporal.samples.springboot.wmworkflow.pojo.GlobalSplanInfo;
import io.temporal.samples.springboot.wmworkflow.pojo.StepUpdateReq;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockPlanService {
  @Autowired WorkflowClient client;

  private static final Map<String, String> data = new ConcurrentHashMap<>();

  public Map<String, String> createWorkflow(GlobalSPlanRequest sPlanRequest) {
    GlobalStockPlanWflow workflow =
        client.newWorkflowStub(
            GlobalStockPlanWflow.class,
            WorkflowOptions.newBuilder().setTaskQueue("globalstockworkflowTask").build());
    WorkflowExecution workflowExecution = WorkflowClient.start(workflow::start, sPlanRequest);
    data.put(sPlanRequest.getClientId(), workflowExecution.getWorkflowId());
    return data;
  }

  public GlobalSplanInfo getWorkflowById(String workflowId) {
    GlobalStockPlanWflow globalStockPlanWflow =
        client.newWorkflowStub(GlobalStockPlanWflow.class, workflowId);
    return globalStockPlanWflow.getState();
  }

  public void setWorkflowState(StepUpdateReq stepUpdateReq, String workflowId) {
    GlobalStockPlanWflow globalStockPlanWflow =
        client.newWorkflowStub(GlobalStockPlanWflow.class, workflowId);
    globalStockPlanWflow.updateState(stepUpdateReq.getProcessSteps(), stepUpdateReq.getData());
  }

  public Map<String, String> getWorkflows() {
    return data;
  }
}
