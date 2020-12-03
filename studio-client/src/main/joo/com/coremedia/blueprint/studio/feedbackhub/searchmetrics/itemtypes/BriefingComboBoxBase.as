package com.coremedia.blueprint.studio.feedbackhub.searchmetrics.itemtypes {
import com.coremedia.blueprint.studio.feedbackhub.searchmetrics.model.BriefingInfo;
import com.coremedia.ui.components.LocalComboBox;
import com.coremedia.ui.data.ValueExpression;

[ResourceBundle('com.coremedia.blueprint.studio.feedbackhub.searchmetrics.FeedbackHubSearchmetrics')]
public class BriefingComboBoxBase extends LocalComboBox {

  [Bindable]
  public var briefingInfoExpression:ValueExpression;

  [Bindable]
  public var briefingsExpression:ValueExpression;

  public function BriefingComboBoxBase(config:BriefingComboBox = null) {
    super(config);
  }

  protected function briefingTransformer(d:BriefingInfo):String {
    if (d is BriefingInfo) {
      return d.getBriefingId();
    }
    return null;
  }

  protected function briefingReverseTransformer(briefingId:String):BriefingInfo {
    var briefings:Array = briefingsExpression.getValue();
    for each(var b:BriefingInfo in briefings) {
      if (b.getBriefingId() === briefingId) {
        return b;
      }
    }
    return null;
  }
}
}
