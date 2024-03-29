import ValueExpression from "@coremedia/studio-client.client-core/data/ValueExpression";
import LocalComboBox from "@coremedia/studio-client.ext.ui-components/components/LocalComboBox";
import { is } from "@jangaroo/runtime";
import Config from "@jangaroo/runtime/Config";
import BriefingInfo from "../model/BriefingInfo";
import BriefingComboBox from "./BriefingComboBox";

interface BriefingComboBoxBaseConfig extends Config<LocalComboBox>, Partial<Pick<BriefingComboBoxBase,
  "briefingInfoExpression" |
  "briefingsExpression"
>> {
}

class BriefingComboBoxBase extends LocalComboBox {
  declare Config: BriefingComboBoxBaseConfig;

  #briefingInfoExpression: ValueExpression = null;

  get briefingInfoExpression(): ValueExpression {
    return this.#briefingInfoExpression;
  }

  set briefingInfoExpression(value: ValueExpression) {
    this.#briefingInfoExpression = value;
  }

  #briefingsExpression: ValueExpression = null;

  get briefingsExpression(): ValueExpression {
    return this.#briefingsExpression;
  }

  set briefingsExpression(value: ValueExpression) {
    this.#briefingsExpression = value;
  }

  constructor(config: Config<BriefingComboBox> = null) {
    super(config);
  }

  protected briefingTransformer(d: BriefingInfo): string {
    if (is(d, BriefingInfo)) {
      return d.getBriefingId();
    }
    return null;
  }

  protected briefingReverseTransformer(briefingId: string): BriefingInfo {
    const briefings: Array<any> = this.briefingsExpression.getValue();
    for (const b of briefings as BriefingInfo[]) {
      if (b.getBriefingId() === briefingId) {
        return b;
      }
    }
    return null;
  }
}

export default BriefingComboBoxBase;
