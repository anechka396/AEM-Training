package com.epam.aem.training.core.entity;

import java.io.Serializable;
import java.util.List;

public class Column  implements Serializable{

	private List<Item> items;
	private String header;
	private Boolean hideInMobileView;

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public Boolean getHideInMobileView() {
		return hideInMobileView;
	}

	public void setHideInMobileView(Boolean hideInMobileView) {
		this.hideInMobileView = hideInMobileView;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((header == null) ? 0 : header.hashCode());
		result = prime
				* result
				+ ((hideInMobileView == null) ? 0 : hideInMobileView.hashCode());
		result = prime * result + ((items == null) ? 0 : items.hashCode());
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
		Column other = (Column) obj;
		if (header == null) {
			if (other.header != null)
				return false;
		} else if (!header.equals(other.header))
			return false;
		if (hideInMobileView == null) {
			if (other.hideInMobileView != null)
				return false;
		} else if (!hideInMobileView.equals(other.hideInMobileView))
			return false;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Column [items=" + items + ", header=" + header
				+ ", hideInMobileView=" + hideInMobileView + "]";
	}

	
}
