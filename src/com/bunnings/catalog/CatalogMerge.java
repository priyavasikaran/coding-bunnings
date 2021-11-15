package com.bunnings.catalog;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bunnings.catalog.dto.Barcode;
import com.bunnings.catalog.dto.Product;

public interface CatalogMerge {
	
	/**
	 * Interface method for the catalog merge
	 * @throws Exception
	 */
	public Set<Product> doCatalogMerge() throws Exception;
	
	public Set<String> getCombinedSKUList(Map<String, List<String>> listOfCatABarcodes,
			Map<String, List<String>> listOfCatBBarcodes, List<Barcode> collectionOfABarcodes, List<Barcode> collectionOfBBarcodes) throws Exception;
	
    public Set<Product> getCombinedListOfProducts(Map<String, List<String>> listOfCatABarcodes,
    		Map<String, List<String>> listOfCatBBarcodes, List<Barcode> collectionOfABarcodes, List<Barcode> collectionOfBBarcodes, List<Product> collectionOfCatalogAProducts, List<Product> collectionOfCatalogBProducts) throws Exception;

    public Map<String, List<String>> createSupplierBarcodeMap(List<Barcode> collectionOfBarcodes, Map<String, List<String>> listOfCatBarcodes) throws Exception;
    
}
