package io.temporal.samples.springboot.wmworkflow.controller;

import io.temporal.samples.springboot.wmworkflow.pojo.GlobalSPlanRequest;
import io.temporal.samples.springboot.wmworkflow.pojo.GlobalSplanInfo;
import io.temporal.samples.springboot.wmworkflow.pojo.StepUpdateReq;
import io.temporal.samples.springboot.wmworkflow.services.StockPlanService;
import java.net.URI;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class GlobalStockDataController {
  @Autowired private StockPlanService service;
  private static final String host = "http://localhost:8080";

  @PostMapping("/stock")
  public ResponseEntity<Map<String, String>> createWorkflow(
      @RequestBody GlobalSPlanRequest request) {
    Map<String, String> workflow = service.createWorkflow(request);
    return ResponseEntity.created(
            URI.create(host + "/api/stock/".concat(workflow.get(request.getClientId()))))
        .body(workflow);
  }

  @GetMapping("/stock/{id}")
  public ResponseEntity<GlobalSplanInfo> getWorkflowData(@PathVariable String id) {
    return ResponseEntity.ok(service.getWorkflowById(id));
  }

  @PatchMapping("/stock/{id}")
  public ResponseEntity<String> updateWorkflowState(
      @RequestBody StepUpdateReq stepUpdateReq, @PathVariable String id) {
    service.setWorkflowState(stepUpdateReq, id);
    return ResponseEntity.ok("updated");
  }

  @GetMapping("/all")
  ResponseEntity<Map<String, String>> getWorkflows() {
    return ResponseEntity.ok(service.getWorkflows());
  }
}
