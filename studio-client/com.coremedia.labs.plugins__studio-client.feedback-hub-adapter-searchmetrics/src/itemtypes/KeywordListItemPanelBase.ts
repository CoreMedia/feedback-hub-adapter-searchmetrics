import ValueExpression from "@coremedia/studio-client.client-core/data/ValueExpression";
import ValueExpressionFactory from "@coremedia/studio-client.client-core/data/ValueExpressionFactory";
import FeedbackItemPanel from "@coremedia/studio-client.main.feedback-hub-editor-components/components/itempanels/FeedbackItemPanel";
import StringUtil from "@jangaroo/ext-ts/String";
import Config from "@jangaroo/runtime/Config";
import int from "@jangaroo/runtime/int";
import FeedbackHubSearchmetrics_properties from "../FeedbackHubSearchmetrics_properties";
import SearchmetricsPropertyNames from "../SearchmetricsPropertyNames";
import KeywordScore from "../model/KeywordScore";
import KeywordListItemPanel from "./KeywordListItemPanel";

interface KeywordListItemPanelBaseConfig extends Config<FeedbackItemPanel> {
}

class KeywordListItemPanelBase extends FeedbackItemPanel {
  declare Config: KeywordListItemPanelBaseConfig;

  static readonly VIEW_LIMIT: int = 9;

  #keywordScoringsExpression: ValueExpression = null;

  #loadedMoreExpression: ValueExpression = null;

  constructor(config: Config<KeywordListItemPanel> = null) {
    super(config);
  }

  protected getLoadedMoreExpression(): ValueExpression {
    if (!this.#loadedMoreExpression) {
      this.#loadedMoreExpression = ValueExpressionFactory.createFromValue(false);
    }
    return this.#loadedMoreExpression;
  }

  protected loadMore(): void {
    this.getLoadedMoreExpression().setValue(true);
  }

  protected getButtonLabel(config: Config<KeywordListItemPanel>): string {
    const label = FeedbackHubSearchmetrics_properties.searchmetrics_load_more;
    const count: int = this.getKeywords(config).length;
    const more: int = count - KeywordListItemPanelBase.VIEW_LIMIT;
    return StringUtil.format(label, more);
  }

  protected getKeywordsExpression(config: Config<KeywordListItemPanel>): ValueExpression {
    if (!this.#keywordScoringsExpression) {
      this.#keywordScoringsExpression = ValueExpressionFactory.createFromFunction((): Array<any> => {
        let result = this.getKeywords(config);
        if (result.length > KeywordListItemPanelBase.VIEW_LIMIT && !this.getLoadedMoreExpression().getValue()) {
          result = result.splice(0, KeywordListItemPanelBase.VIEW_LIMIT);
        }

        return result;
      });
    }
    return this.#keywordScoringsExpression;
  }

  protected getButtonVisibilityExpression(config: Config<KeywordListItemPanel>): ValueExpression {
    return ValueExpressionFactory.createFromFunction((): boolean =>
      this.getKeywords(config).length > KeywordListItemPanelBase.VIEW_LIMIT && !this.getLoadedMoreExpression().getValue(),
    );
  }

  protected getKeywords(config: Config<KeywordListItemPanel>): Array<any> {
    const result = [];
    const contentOptResults: Array<any> = config.feedbackItem[SearchmetricsPropertyNames.CONTENT_OPT_RESULTS];
    if (contentOptResults) {
      for (const optResult of contentOptResults) {
        if (optResult.keyword === "all_topics") {
          const scores: Array<any> = optResult[config.feedbackItem["keywordType"]];
          for (const s of scores) {
            result.push(ValueExpressionFactory.createFromValue(new KeywordScore(s)));
          }
        }
      }
    }
    return result;
  }
}

export default KeywordListItemPanelBase;
