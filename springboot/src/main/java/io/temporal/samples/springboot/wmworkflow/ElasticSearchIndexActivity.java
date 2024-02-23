package io.temporal.samples.springboot.wmworkflow;

import io.temporal.activity.ActivityInterface;
import io.temporal.samples.springboot.wmworkflow.pojo.GlobalSplanInfo;
import java.util.Map;

@ActivityInterface
public interface ElasticSearchIndexActivity {
  Map<String, String> stageDataToElastic(GlobalSplanInfo globalSplanInfo);
}
