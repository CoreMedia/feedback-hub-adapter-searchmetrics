package com.coremedia.labs.plugins.searchmetrics.queries;

/**
 *
 */
public class ValidateContentQuery implements Query {
  private static final String TEMPLATE = "{\"query\" : \"mutation {  \n" +
          "  content_validation(input: {\n" +
          "    briefing_id: \\\"'briefingId'\\\"\n" +
          "    content: \\\"'content'\\\"\n" +
          "    isEncoded: false    \n" +
          "  }) \n" +
          "  {\n" +
          "    notification\n" +
          "    id\n" +
          "    content\n" +
          "    length\n" +
          "    readability\n" +
          "    targetLength\n" +
          "    languageScore\n" +
          "    duplicationCheckResults {\n" +
          "      duplication_score\n" +
          "      level\n" +
          "      title\n" +
          "      url\n" +
          "    }\n" +
          "    contentOptResults {\n" +
          "      contentOptScore\n" +
          "      keyword\n" +
          "      MUST_HAVE {\n" +
          "        current\n" +
          "        keyword\n" +
          "        target\n" +
          "        term_id\n" +
          "      } \n" +
          "      ADDITIONAL {\n" +
          "        current\n" +
          "        keyword\n" +
          "        target\n" +
          "        term_id\n" +
          "      }\n" +
          "      RELEVANCE {\n" +
          "        current\n" +
          "        keyword\n" +
          "        target\n" +
          "        term_id\n" +
          "      }\n" +
          "    }\n" +
          "    \n" +
          "    overallScore\n" +
          "    targetOverallScore\n" +
          "    title\n" +
          "    contentScore {\n" +
          "      content_score\n" +
          "      coverage_score\n" +
          "      length_score\n" +
          "      natural_language_score\n" +
          "      repetition_score      \n" +
          "    }\n" +
          "  }\n" +
          "}\" }";

  private String briefingId;
  private String content;

  public ValidateContentQuery(String briefingId, String content) {
    this.briefingId = briefingId;
    this.content = content;
  }

  @Override
  public String toString() {
    String result = TEMPLATE.replace("'briefingId'", briefingId);
    result = result.replace("'content'", content);
    return result;
  }
}
