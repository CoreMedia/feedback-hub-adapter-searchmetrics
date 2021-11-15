import BeanImpl from "@coremedia/studio-client.client-core-impl/data/impl/BeanImpl";
import SearchmetricsPropertyNames from "../SearchmetricsPropertyNames";

class DuplicateCheck extends BeanImpl {

  constructor(json: any) {
    super(json);
  }

  getUrl(): string {
    return this.get(SearchmetricsPropertyNames.URL);
  }

  getDuplicationScore(): number {
    const number: number = this.get(SearchmetricsPropertyNames.DUPLICATION_SCORE);
    if (number < 0) {
      return 0;
    }
    return number;
  }
}

export default DuplicateCheck;
