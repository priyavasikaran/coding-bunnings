package com.bunnings.catalog;

import com.bunnings.catalog.impl.CatalogMergeImpl;

/**
 * 
 * @author xsvng
 * 
 * This class is to trigger the catalog merge from different CSV files
 * 
 */
public class Main {

	public static void main(String args[]) throws Exception {

		/**
		 * This is the method which invokes the catalog merge
		 */
		CatalogMerge catalog = new CatalogMergeImpl();
		try {
			catalog.doCatalogMerge();
		} catch (Exception e) {
			throw new Exception("Error in merging the catalogs" + e.getMessage());
		}

	}
}
