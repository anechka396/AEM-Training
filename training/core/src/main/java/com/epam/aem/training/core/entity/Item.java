package com.epam.aem.training.core.entity;

import java.io.Serializable;

import com.day.cq.wcm.api.Page;

public class Item implements Serializable {
	
	private Page page;
	

	public Item() {
		super();
	}

	public Item(Page page) {
		super();
		this.page = page;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((page == null) ? 0 : page.hashCode());
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
		Item other = (Item) obj;
		if (page == null) {
			if (other.page != null)
				return false;
		} else if (!page.equals(other.page))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Item [page=" + page + "]";
	}
}
