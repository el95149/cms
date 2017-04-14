/**
 * 
 */
package com.sastix.cms.frontend.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sastix.cms.client.impl.CmsClient;

/**
 * @author <a href="mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@Controller
public class ResourceController {

	static final Logger LOG = (Logger) LoggerFactory.getLogger(ResourceController.class);
	
	@Autowired
	private CmsClient cmsClient;
	
	@RequestMapping("/resources")
	public String listResources(Model model) {
		LOG.info(cmsClient.toString());
		return "resources";
	}
	
	
}
