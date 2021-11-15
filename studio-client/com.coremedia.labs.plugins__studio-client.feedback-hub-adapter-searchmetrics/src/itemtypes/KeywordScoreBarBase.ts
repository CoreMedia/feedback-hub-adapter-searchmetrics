import Config from "@jangaroo/runtime/Config";
import { as, asConfig } from "@jangaroo/runtime";
import FeedbackHubSearchmetrics_properties from "../FeedbackHubSearchmetrics_properties";
import KeywordScoreBar from "./KeywordScoreBar";
import ValueExpression from "@coremedia/studio-client.client-core/data/ValueExpression";
import ValueExpressionFactory from "@coremedia/studio-client.client-core/data/ValueExpressionFactory";
import DisplayFieldSkin from "@coremedia/studio-client.ext.ui-components/skins/DisplayFieldSkin";
import FeedbackHelper from "@coremedia/studio-client.main.feedback-hub-editor-components/util/FeedbackHelper";
import Container from "@jangaroo/ext-ts/container/Container";
import DisplayField from "@jangaroo/ext-ts/form/field/Display";
interface KeywordScoreBarBaseConfig extends Config<Container>, Partial<Pick<KeywordScoreBarBase,
  "bindTo" |
  "labelPropertyName" |
  "valuePropertyName" |
  "targetValuePropertyName"
>> {
}



class KeywordScoreBarBase extends Container {
  declare Config: KeywordScoreBarBaseConfig;
  readonly BAR_ITEM_ID:string = "barLabel";

  #bindTo:ValueExpression = null;

  get bindTo():ValueExpression { return this.#bindTo; }
  set bindTo(value:ValueExpression) { this.#bindTo = value; }

  #labelPropertyName:string = null;

  get labelPropertyName():string { return this.#labelPropertyName; }
  set labelPropertyName(value:string) { this.#labelPropertyName = value; }

  #valuePropertyName:string = null;

  get valuePropertyName():string { return this.#valuePropertyName; }
  set valuePropertyName(value:string) { this.#valuePropertyName = value; }

  #targetValuePropertyName:string = null;

  get targetValuePropertyName():string { return this.#targetValuePropertyName; }
  set targetValuePropertyName(value:string) { this.#targetValuePropertyName = value; }

  constructor(config:Config<KeywordScoreBar> = null) {
    super(config);
  }

  protected getDisabledExpression(config:Config<KeywordScoreBar>):ValueExpression {
    return ValueExpressionFactory.createFromFunction(():boolean => {
      var score:number = config.bindTo.extendBy(config.valuePropertyName).getValue();
      score = score * 100 / config.bindTo.extendBy(config.targetValuePropertyName).getValue();
      return score <= 0;
    });
  }

  protected getIconSkin(config:Config<KeywordScoreBar>):string {
    var score:number = config.bindTo.extendBy(config.valuePropertyName).getValue();
    var target:number= config.bindTo.extendBy(config.targetValuePropertyName).getValue();
    if(score >= target) {
      return DisplayFieldSkin.GREEN.getSkin();
    }

    return DisplayFieldSkin.DEFAULT.getSkin();
  }

  protected override afterRender():void {
    super.afterRender();

    var barHeight:number = 8;
    var score:number = this.bindTo.extendBy(this.valuePropertyName).getValue();
    score = score * 100 / this.bindTo.extendBy(this.targetValuePropertyName).getValue();
    var color = "#4F4F4F";
    if(score >= 100) {
      score = 100;
      color = "#5ca03f";
    }

    var field =as( this.queryById(this.BAR_ITEM_ID),  DisplayField);
    field.setValue('<div style="width: 100%;text-align: center;">' +
            '<div style="height:' + barHeight + 'px;background-color:#dcdbdb;width: 100%;"></div>' +
            '<div style="height:' + barHeight + "px;margin-top:-" + barHeight + "px;background-color:" +
            color + ";width: " + FeedbackHelper.formatScore(score, 0) + '%;"></div>' +
            "</div>");
  }

  protected formatScore(score:number):string {
    return parseFloat("" + score).toFixed(0);
  }
}
export default KeywordScoreBarBase;
