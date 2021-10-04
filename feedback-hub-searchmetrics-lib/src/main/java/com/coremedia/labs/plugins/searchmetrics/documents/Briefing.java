package com.coremedia.labs.plugins.searchmetrics.documents;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 *
 * {
 *   "brief_id":"b941f3e3-b9a0-4b93-94a1-2eb6c2f84958",
 *   "brief_name":"Best avocado recipes",
 *   "brief_state":"briefing_draft",
 *   "project_id":954846,
 *   "project_name":"Cookbook blog",
 *   "account_id":984985,
 *   "owner_id":"e5745d6ra82y638d51047691a2b0e70b",
 *   "assignee_id":"e5745d6ra82y638d51047691a2b0e70b",
 *   "language":"en",
 *   "country":"GB",
 *   "url":"www.my-website.com",
 *   "url_title":"My great avocado recipes",
 *   "target_word_count":500,
 *   "target_content_score":500,
 *   "notes":"Brief content in creation...",
 *   "creation_date":"2007-12-03T10:15:30+01:00",
 *   "due_date":"2007-12-03T10:15:30+01:00",
 *   "refreshed_date":"2007-12-03T10:15:30+01:00",
 *   "is_tracked":true,
 *   "search_volume":-2147483648,
 *   "marketshare":{
 *     "current":1,
 *     "trend":1.2,
 *     "direction":20
 *   },
 *   "visibility":{
 *     "current":1,
 *     "trend":1.5,
 *     "direction":15
 *   },
 *   "duplication_information":[
 *     {
 *       "url":"http://www.example.com",
 *       "title":"URL title",
 *       "duplication_score":47,
 *       "duplication_text":[
 *         {
 *           "start":0,
 *           "end":150,
 *           "text":"Sample text present in another url.."
 *         }
 *       ]
 *     }
 *   ],
 *   "latest_content_version":{
 *     "content_version_id":"c8d1262ca571-4a6c-95a0-87821a58eec1",
 *     "content_score":0.9,
 *     "coverage_score":0.9,
 *     "natural_language_score":0.9,
 *     "repetition_score":0.9,
 *     "word_count":255,
 *     "title":"Avocado Recipes in June",
 *     "content":"<p>This is a very good text about avocado recipes</p>",
 *     "author_id":"e5745d6ra82y638d51047691a2b0e70b",
 *     "meta_title":"Avocado recipes",
 *     "meta_description":"Excellent avocado recipes for this years summer season",
 *     "length_score":0.9,
 *     "readability_score":0.9,
 *     "created_at":"2007-12-03T10:15:30+01:00",
 *     "modified_at":"2007-12-03T10:15:30+01:00"
 *   },
 *   "topics":[
 *     {
 *       "topic_id":"123e4567-e89b-12d3-a456-426614174000",
 *       "topic_type":"main_topic",
 *       "topic":"avocado",
 *       "live_topic_loaded_state":"success",
 *       "content_optimization_loaded_state":"success",
 *       "questions_loaded_state":"success",
 *       "questions":[
 *         {
 *           "question_id":"b941f3e3-b9a0-4b93-94a1-2eb6c2f84958",
 *           "question_text":"How to prepare avocado ?",
 *           "is_selected":false,
 *           "question_type":"What",
 *           "local_rank":1
 *         }
 *       ],
 *       "competitors":[
 *         {
 *           "url":"https://www.nice-url.com",
 *           "title":"Top summer vacations 2021",
 *           "position":1,
 *           "wordCount":550
 *         }
 *       ],
 *       "topic_keywords":[
 *         {
 *           "id":"b941f3e3-b9a0-4b93-94a1-2eb6c2f84958",
 *           "keyword":"avocado",
 *           "category":"[must_have, recommended, additional]",
 *           "current_frequency":2,
 *           "target_frequency":3,
 *           "example_phrases":[
 *             "new avocado recipe",
 *             "avocado benefits are huge"
 *           ]
 *         }
 *       ]
 *     }
 *   ],
 *   "brief_keywords":[
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
 *   ],
 *   "content_sequence_id":12345,
 *   "url_content_score":{
 *     "content_score":0.81
 *   }
 * }
 */
public class Briefing {
  @JsonProperty("brief_id")
  private String briefId;

  @JsonProperty("brief_name")
  private String briefName;

  @JsonProperty("brief_state")
  private String briefState;

  @JsonProperty("project_id")
  private String projectId;

  @JsonProperty("project_name")
  private String projectName;

  @JsonProperty("account_id")
  private String accountId;

  private String language;
  private String country;
  private String url;

  @JsonProperty("url_title")
  private String urlTitle;

  @JsonProperty("target_word_count")
  private int targetWordCount;

  @JsonProperty("target_content_score")
  private int targetContentScore;

  private String notes;

  @JsonProperty("creation_date")
  private String creationDate;

  @JsonProperty("due_date")
  private String dueDate;

  @JsonProperty("refreshed_date")
  private String refreshedDate;

  @JsonProperty("is_tracked")
  private boolean isTracked;

  @JsonProperty("duplication_information")
  private List<DuplicationInformation> duplicationInformation;

  @JsonProperty("latest_content_version")
  private ContentVersion latestContentVersion;

  private List<Topic> topics;

  @JsonProperty("brief_keywords")
  private List<Keyword> briefKeywords;

  @JsonProperty("content_sequence_id")
  private int contentSequenceId;

  public String getBriefId() {
    return briefId;
  }

  public void setBriefId(String briefId) {
    this.briefId = briefId;
  }

  public String getBriefName() {
    return briefName;
  }

  public void setBriefName(String briefName) {
    this.briefName = briefName;
  }

  public String getBriefState() {
    return briefState;
  }

  public void setBriefState(String briefState) {
    this.briefState = briefState;
  }

  public String getProjectId() {
    return projectId;
  }

  public void setProjectId(String projectId) {
    this.projectId = projectId;
  }

  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUrlTitle() {
    return urlTitle;
  }

  public void setUrlTitle(String urlTitle) {
    this.urlTitle = urlTitle;
  }

  public int getTargetWordCount() {
    return targetWordCount;
  }

  public void setTargetWordCount(int targetWordCount) {
    this.targetWordCount = targetWordCount;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public String getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(String creationDate) {
    this.creationDate = creationDate;
  }

  public String getDueDate() {
    return dueDate;
  }

  public void setDueDate(String dueDate) {
    this.dueDate = dueDate;
  }

  public String getRefreshedDate() {
    return refreshedDate;
  }

  public void setRefreshedDate(String refreshedDate) {
    this.refreshedDate = refreshedDate;
  }

  public boolean isTracked() {
    return isTracked;
  }

  public void setTracked(boolean tracked) {
    isTracked = tracked;
  }

  public List<DuplicationInformation> getDuplicationInformation() {
    return duplicationInformation;
  }

  public void setDuplicationInformation(List<DuplicationInformation> duplicationInformation) {
    this.duplicationInformation = duplicationInformation;
  }

  public ContentVersion getLatestContentVersion() {
    return latestContentVersion;
  }

  public void setLatestContentVersion(ContentVersion latestContentVersion) {
    this.latestContentVersion = latestContentVersion;
  }

  public List<Topic> getTopics() {
    return topics;
  }

  public void setTopics(List<Topic> topics) {
    this.topics = topics;
  }

  public List<Keyword> getBriefKeywords() {
    return briefKeywords;
  }

  public void setBriefKeywords(List<Keyword> briefKeywords) {
    this.briefKeywords = briefKeywords;
  }

  public int getContentSequenceId() {
    return contentSequenceId;
  }

  public void setContentSequenceId(int contentSequenceId) {
    this.contentSequenceId = contentSequenceId;
  }

  public int getTargetContentScore() {
    return targetContentScore;
  }

  public void setTargetContentScore(int targetContentScore) {
    this.targetContentScore = targetContentScore;
  }
}