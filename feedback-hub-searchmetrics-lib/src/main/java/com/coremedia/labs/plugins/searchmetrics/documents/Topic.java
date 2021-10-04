package com.coremedia.labs.plugins.searchmetrics.documents;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * {
 *   "topic_id":"123e4567-e89b-12d3-a456-426614174000",
 *   "topic_type":"main_topic",
 *   "topic":"avocado",
 *   "live_topic_loaded_state":"success",
 *   "content_optimization_loaded_state":"success",
 *   "questions_loaded_state":"success",
 *   "questions":[
 *     {
 *       "question_id":"b941f3e3-b9a0-4b93-94a1-2eb6c2f84958",
 *       "question_text":"How to prepare avocado ?",
 *       "is_selected":false,
 *       "question_type":"What",
 *       "local_rank":1
 *     }
 *   ],
 *   "competitors":[
 *     {
 *       "url":"https://www.nice-url.com",
 *       "title":"Top summer vacations 2021",
 *       "position":1,
 *       "wordCount":550
 *     }
 *   ],
 *   "topic_keywords":[
 *     {
 *       "id":"b941f3e3-b9a0-4b93-94a1-2eb6c2f84958",
 *       "keyword":"avocado",
 *       "category":"[must_have, recommended, additional]",
 *       "current_frequency":2,
 *       "target_frequency":3,
 *       "example_phrases":[
 *         "new avocado recipe",
 *         "avocado benefits are huge"
 *       ]
 *     }
 *   ]
 * }
 */
public class Topic {
  @JsonProperty("topic_id")
  private String topicId;

  @JsonProperty("topic_type")
  private String topicType;

  private String topic;

  @JsonProperty("live_topic_loaded_state")
  private String liveTopicLoadedState;

  @JsonProperty("content_optimization_loaded_state")
  private String contentOptimizationLoadedState;

  @JsonProperty("questions_loaded_state")
  private String questionsLoadedState;

  private List<Question> questions;

  private List<Competitor> competitors;

  @JsonProperty("topic_keywords")
  private List<Keyword> topicKeywords;
}
