package com.epam.aem.training.core.entity;

import java.io.Serializable;

public class SocialNetwork implements Serializable{
	
	private String title;
	private String url;
	private String iconclass;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIconclass() {
		return iconclass;
	}
	public void setIconclass(String iconclass) {
		this.iconclass = iconclass;
	}	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((iconclass == null) ? 0 : iconclass.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SocialNetwork other = (SocialNetwork) obj;
		if (iconclass == null) {
			if (other.iconclass != null)
				return false;
		} else if (!iconclass.equals(other.iconclass))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "SocialNetwork [title=" + title + ", url=" + url
				+ ", iconclass=" + iconclass + "]";
	}
}
