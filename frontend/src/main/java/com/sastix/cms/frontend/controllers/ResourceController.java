/**
 * 
 */
package com.sastix.cms.frontend.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sastix.cms.client.impl.CmsClient;
import com.sastix.cms.common.content.CreateResourceDTO;
import com.sastix.cms.common.content.LockedResourceDTO;
import com.sastix.cms.common.content.ResourceDTO;
import com.sastix.cms.common.content.ResourceQueryDTO;
import com.sastix.cms.common.content.UpdateResourceDTO;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@Controller
public class ResourceController {

	private static final String DEFAULT_RESOURCE_MEDIA_TYPE = "text/html";

	static final Logger LOG = (Logger) LoggerFactory.getLogger(ResourceController.class);

	@Value("${cms.tenantId}")
	private String tenantId;

	@Autowired
	private CmsClient cmsClient;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@RequestMapping("/resources")
	public String listResources(Model model) {
		// String uid = cmsClient.getUID();
		// ResourceQueryDTO resourceQueryDTO = new ResourceQueryDTO(uid);
		// ResourceDTO resourceDTO = cmsClient.queryResource(resourceQueryDTO);
		// LOG.info(resourceDTO.toString());
		return "resources";
	}

	@RequestMapping("/createResource")
	public String createResourceForm(Model model) {
		model.addAttribute("resource", new CreateResourceDTO(DEFAULT_RESOURCE_MEDIA_TYPE, null, null, tenantId));
		return "resource";
	}

	@RequestMapping(value = "/createResource", method = RequestMethod.POST)
	public String createResource(@Valid @ModelAttribute("resource") CreateResourceDTO resource,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			LOG.error(bindingResult.getAllErrors().toString());
			return "resource";
		}

		ResourceDTO createdResource = cmsClient.createResource(resource);
		LOG.info(createdResource.toString());
		return "redirect:/resources";
	}

	@RequestMapping("/updateResource")
	public String updateResourceForm(@RequestParam("uid") String uid, Model model) {
		ResourceQueryDTO resourceQueryDTO = new ResourceQueryDTO(uid);
		ResourceDTO resourceDTO = cmsClient.queryResource(resourceQueryDTO);
		LockedResourceDTO lockResource = cmsClient.lockResource(resourceDTO);
		LOG.info(resourceDTO.toString());
		LOG.info(lockResource.toString());
		// lock and new author are always the same, in this implemenation
		UpdateResourceDTO updateResourceDTO = new UpdateResourceDTO(lockResource.getResourceUID(),
				lockResource.getAuthor(), lockResource.getLockID(), lockResource.getLockExpirationDate(),
				lockResource.getResourceName(), lockResource.getAuthor(), lockResource.getResourceName());
		model.addAttribute("resource", updateResourceDTO);
		return "updateResource";
	}

	@RequestMapping(value = "/updateResource", method = RequestMethod.POST)
	public String updateResource(@Valid @ModelAttribute("resource") UpdateResourceDTO resource,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			LOG.error(bindingResult.getAllErrors().toString());
			return "updateResource";
		}
		LockedResourceDTO updateResource = cmsClient.updateResource(resource);
		LOG.info(updateResource.toString());
		cmsClient.unlockResource(updateResource);
		return "redirect:/resources";
	}

	@RequestMapping(value = "/deleteResource", method = RequestMethod.POST)
	public String deleteResource(@ModelAttribute("resource") UpdateResourceDTO resource, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			LOG.error(bindingResult.getAllErrors().toString());
			return "updateResource";
		}
		ResourceDTO deletedResource = cmsClient.deleteResource(resource);
		LOG.info(deletedResource.toString());
		return "redirect:/resources";
	}

}
