import BeanImpl from "@coremedia/studio-client.client-core-impl/data/impl/BeanImpl";
import SearchmetricsPropertyNames from "../SearchmetricsPropertyNames";

class Question extends BeanImpl {

  constructor(json: any) {
    super(json);
  }

  getGroup(): string {
    return this.get(SearchmetricsPropertyNames.GROUP);
  }
}

export default Question;
