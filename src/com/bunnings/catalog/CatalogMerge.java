package com.bunnings.catalog;

import java.util.Set;

import com.bunnings.catalog.dto.Product;

public interface CatalogMerge {
	
	/**
	 * Interface method for the catalog merge
	 * @throws Exception
	 */
	public Set<Product> doCatalogMerge() throws Exception;
	

}
