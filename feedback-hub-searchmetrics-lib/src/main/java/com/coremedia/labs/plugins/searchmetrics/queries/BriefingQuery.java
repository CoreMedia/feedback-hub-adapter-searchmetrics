package com.coremedia.labs.plugins.searchmetrics.queries;

/**
 *
 */
public class BriefingQuery implements Query {
  private static final String TEMPLATE = "{ \"query\" : \"{  \n" +
          "  content_experience {\n" +
          "    briefing(id:\\\"'id'\\\") {\n" +
          "      project_id\n" +
          "      id\n" +
          "      infos {\n" +
          "        content_score_goal\n" +
          "      }" +
          "      content\n" +
          "      main_topic\n" +
          "      name\n" +
          "      language\n" +
          "      target_score\n" +
          "      target_length\n" +
          "      content_score\n" +
          "      content_length\n" +
          "      title,\n" +
          "      questions {\n" +
          "        id\n" +
          "        topic\n" +
          "        data {\n" +
          "          id\n" +
          "          group\n" +
          "          question\n" +
          "          local_rank\n" +
          "          global_rank\n" +
          "        }\n" +
          "      }\n" +
          "    } \n" +
          "  }\n" +
          "}\" }";

  private String id;

  public BriefingQuery(String id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return TEMPLATE.replaceAll("'id'", id);
  }
}
