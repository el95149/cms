/*
 * Copyright(c) 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sastix.cms.common.content;

import java.util.Date;

import javax.validation.constraints.NotNull;

/**
 * The specific object holds all the information related to a Revision.
 */
public class RevisionDTO {

	/**
	 * The title (i.e. time-stamp) of the revision.
	 */
	@NotNull
	private String title;

	/**
	 * The creation date
	 */
	@NotNull
	private Date createdAt;

	/**
	 * The update date
	 */
	private Date updatedAt;

	/**
	 * The deletion date
	 */
	private Date deletedAt;

	/**
	 * The Resource this revision is tied to
	 */
	@NotNull
	private ResourceDTO resourceDTO;

	/**
	 * Default Constructor.
	 */
	public RevisionDTO() {
		// Empty
	}

	
	/**
	 *     /**
     * Constructor with the mandatory fields.
	 * @param title A string representing the row creation time-stamp
	 * @param createdAt The creation date
     * @param resourceDTO the matching Resource
	 */
	public RevisionDTO(String title, Date createdAt, ResourceDTO resourceDTO) {
		super();
		this.title = title;
		this.createdAt = createdAt;
		this.resourceDTO = resourceDTO;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Date getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}

	public ResourceDTO getResourceDTO() {
		return resourceDTO;
	}


	public void setResourceDTO(ResourceDTO resourceDTO) {
		this.resourceDTO = resourceDTO;
	}

}
