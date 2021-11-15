import SearchmetricsPropertyNames from "../SearchmetricsPropertyNames";
import BeanImpl from "@coremedia/studio-client.client-core-impl/data/impl/BeanImpl";


class Briefing extends BeanImpl {

  constructor(json:any) {
    super(json);
  }

  getBriefingId():string {
    return this.get(SearchmetricsPropertyNames.ID);
  }

  getName():string {
    return this.get(SearchmetricsPropertyNames.NAME);
  }

  getContent():string {
    return this.get(SearchmetricsPropertyNames.CONTENT);
  }

  getLanguage():string {
    return this.get(SearchmetricsPropertyNames.LANGUAGE);
  }
}
export default Briefing;
