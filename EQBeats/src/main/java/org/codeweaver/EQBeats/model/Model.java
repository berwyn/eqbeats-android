package org.codeweaver.eqbeats.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Berwyn Codeweaver on 23/06/13.
 */
public abstract class Model {

	private long	id;
	private String	link;
	private String	description;
	@SerializedName("html_description")
	private String	htmlDescription;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHtmlDescription() {
		return htmlDescription;
	}

	public void setHtmlDescription(String htmlDescription) {
		this.htmlDescription = htmlDescription;
	}
}
