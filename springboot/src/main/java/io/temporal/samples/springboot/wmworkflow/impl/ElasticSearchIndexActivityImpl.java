package io.temporal.samples.springboot.wmworkflow.impl;

import io.temporal.samples.springboot.wmworkflow.ElasticSearchIndexActivity;
import io.temporal.samples.springboot.wmworkflow.pojo.GlobalSplanInfo;
import io.temporal.spring.boot.ActivityImpl;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
@ActivityImpl(taskQueues = "ElasticSearchActivityTaskQueue")
public class ElasticSearchIndexActivityImpl implements ElasticSearchIndexActivity {
  private static final Map<String, GlobalSplanInfo> updateRecords = new ConcurrentHashMap<>();

  @Override
  public Map<String, String> stageDataToElastic(GlobalSplanInfo globalSplanInfo) {
    String uuid = UUID.randomUUID().toString().replace("-", "");
    updateRecords.put(uuid, globalSplanInfo);
    return Map.of(uuid, globalSplanInfo.getClientId());
  }
}
