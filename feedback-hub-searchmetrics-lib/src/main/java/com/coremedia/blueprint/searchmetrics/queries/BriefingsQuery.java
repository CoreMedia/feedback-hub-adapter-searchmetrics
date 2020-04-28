package com.coremedia.blueprint.searchmetrics.queries;

/**
 *  query {
 *           content_experience {
 *             briefings_list {
 *               count,
 *               briefings {
 *                 id
 *               }
 *             }
 *           }
 * }
 *
 *  query {
 *           content_experience {
 *             briefing(id:"AXBiev9FVzDCx3DQ3_MV") {
 *               id
 *               name
 *               topics {
 *                 created
 *                 email_notification
 *                 state
 *                 type
 *                 value
 *                 content_states {
 *                   parts {
 *                     questions
 *                     terms
 *                   }
 *                 }
 *               }
 *               topics_coverage {
 *                 topic
 *                 keywords_coverage {
 *                   keyword
 *                   keyword_type
 *                   current_frequency
 *                   target_frequency
 *                 }
 *               }
 *             }
 *           }
 * }
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
