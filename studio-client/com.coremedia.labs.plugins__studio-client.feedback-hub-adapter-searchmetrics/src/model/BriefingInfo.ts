import SearchmetricsPropertyNames from "../SearchmetricsPropertyNames";
import BeanImpl from "@coremedia/studio-client.client-core-impl/data/impl/BeanImpl";


class BriefingInfo extends BeanImpl {

  constructor(json:any) {
    super(json);
  }

  getBriefingId():string {
    return this.get(SearchmetricsPropertyNames.ID);
  }

  getStory():string {
    return this.get(SearchmetricsPropertyNames.STORY);
  }

  setStory(name:string):string {
    this.set(SearchmetricsPropertyNames.STORY, name);
  }
}
export default BriefingInfo;
