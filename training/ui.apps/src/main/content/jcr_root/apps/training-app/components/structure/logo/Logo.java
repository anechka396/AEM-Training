package apps.training_app.components.structure.logo;

import com.adobe.cq.sightly.WCMUse;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceUtil;
import com.day.cq.wcm.resource.details.AssetDetails;
  
public class Logo extends WCMUse {

   private Long width;
   private Long height;
   private String imageUrl;

    @Override
    public void activate() throws Exception {
        imageUrl = get("text", String.class);
        Resource imageResource = getRequest().getResourceResolver().resolve(imageUrl);
        if(!ResourceUtil.isNonExistingResource(imageResource)){
			AssetDetails assetDetails = new AssetDetails(imageResource);
			height = assetDetails.getHeight();
			width = assetDetails.getWidth();
		}
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Long getWidth(){
        return width;
    }

    public Long getHeight(){
        return height;
    }
}