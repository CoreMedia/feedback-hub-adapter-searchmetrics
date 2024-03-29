import BeanImpl from "@coremedia/studio-client.client-core-impl/data/impl/BeanImpl";
import SearchmetricsPropertyNames from "../SearchmetricsPropertyNames";

class KeywordScore extends BeanImpl {

  constructor(json: any) {
    super(json);
  }

  getTermId(): string {
    return this.get(SearchmetricsPropertyNames.TERM_ID);
  }
}

export default KeywordScore;
