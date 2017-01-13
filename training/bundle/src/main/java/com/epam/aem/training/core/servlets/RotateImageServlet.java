package com.epam.aem.training.core.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;

import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.Rendition;
import com.day.image.Layer;
import com.epam.aem.training.core.services.RotateService;

@SlingServlet(
		resourceTypes = {"sling/servlet/default"},
		extensions={"jpg", "png"},
		selectors="ud")
public class RotateImageServlet extends SlingSafeMethodsServlet {
	
	@Reference
	RotateService rotateService;

	@Override
	protected void doGet(SlingHttpServletRequest request,
			SlingHttpServletResponse response) throws ServletException,
			IOException {
		Resource resource = request.getResource();
		Resource imageResource = request.getResourceResolver().resolve(resource.getPath().replaceAll(".ud", ""));
		if(!ResourceUtil.isNonExistingResource(imageResource)){
			Asset imageAsset = imageResource.adaptTo(Asset.class);
			Rendition original = imageAsset.getOriginal();
			Layer layer = new Layer(original.getStream());
			Layer rotatedLayer = rotateService.rotateLayer(layer);
			response.setContentType(rotatedLayer.getMimeType());
			response.setStatus(HttpServletResponse.SC_OK);
			rotatedLayer.write(rotatedLayer.getMimeType(), 1.0, response.getOutputStream());
		}else {
            response.sendError(SlingHttpServletResponse.SC_NOT_FOUND);
        }
	}
	
}
