import SearchmetricsPropertyNames from "../SearchmetricsPropertyNames";
import BeanImpl from "@coremedia/studio-client.client-core-impl/data/impl/BeanImpl";


class Question extends BeanImpl {

  constructor(json:any) {
    super(json);
  }

  getGroup():string {
    return this.get(SearchmetricsPropertyNames.GROUP);
  }
}
export default Question;
