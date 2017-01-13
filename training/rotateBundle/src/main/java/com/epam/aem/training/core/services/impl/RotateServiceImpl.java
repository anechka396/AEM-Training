package com.epam.aem.training.core.services.impl;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import com.day.image.Layer;
import com.epam.aem.training.core.services.RotateService;

@Component(
		label = "Simple Rotate Service",
		description = "Simple Rptation Service for Taining project",
		metatype=false
		)
@Service
public class RotateServiceImpl  implements RotateService{

	@Override
	public Layer rotateLayer(Layer originalLayer) {
		originalLayer.rotate(180);
		return originalLayer;
	}

}
