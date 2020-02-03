package com.coremedia.blueprint.searchmetrics.queries;

/**
 *
 */
public class BriefingsQuery implements Query {
  private static final String TEMPLATE = "{ \"query\" : \"{\n" +
          "  content_experience {\n" +
          "    briefings_list(filter:\n" +
          "      {project_ids:'projectId'}\n" +
          "    ) {\n" +
          "      count\n" +
          "      briefings {\n" +
          "        id\n" +
          "        story\n" +
          "        text_length\n" +
          "      }\n" +
          "    }\n" +
          "  }\n" +
          "}\" }";

  private Integer projectId;

  public BriefingsQuery(Integer projectId) {
    this.projectId = projectId;
  }

  @Override
  public String toString() {
    return TEMPLATE.replaceAll("'projectId'", String.valueOf(projectId));
  }
}
