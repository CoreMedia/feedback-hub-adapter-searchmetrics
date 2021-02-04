package com.coremedia.labs.plugins.searchmetrics.documents;

import java.util.List;

/**
 *
 */
public class Questions {
  private String id;
  private String topic;
  private List<Question> data;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTopic() {
    return topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }

  public List<Question> getData() {
    return data;
  }

  public void setData(List<Question> data) {
    this.data = data;
  }
}
