import Config from "@jangaroo/runtime/Config";
import FeedbackHubSearchmetrics_properties from "../FeedbackHubSearchmetrics_properties";
import DuplicatePanel from "./DuplicatePanel";
import ValueExpression from "@coremedia/studio-client.client-core/data/ValueExpression";
import Button from "@jangaroo/ext-ts/button/Button";
import Container from "@jangaroo/ext-ts/container/Container";
interface DuplicatePanelBaseConfig extends Config<Container>, Partial<Pick<DuplicatePanelBase,
  "bindTo"
>> {
}



class DuplicatePanelBase extends Container {
  declare Config: DuplicatePanelBaseConfig;

  #bindTo:ValueExpression = null;

  get bindTo():ValueExpression { return this.#bindTo; }
  set bindTo(value:ValueExpression) { this.#bindTo = value; }

  constructor(config:Config<DuplicatePanel> = null) {
    super(config);
  }

  protected formatPercentage(value:number):string {
    return value + "%";
  }

  protected linkClick(b:Button):void {
    var url = b.getText();
    window.open(url, "_blank");
  }
}
export default DuplicatePanelBase;
