package com.bunnings.catalog.impl;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.bunnings.catalog.CSVFileProcess;
import com.bunnings.catalog.CatalogMerge;
import com.bunnings.catalog.dto.Barcode;
import com.bunnings.catalog.dto.Product;


/**
 * 
 * @author xsvng
 * 
 * This class is to merge the two catalog which we receive as CSV files
 *
 */
public class CatalogMergeImpl implements CatalogMerge {

	private static final String INPUT_FILES_PATH="src/input/";
	private static final String OUTPUT_FILES_PATH="src/output/";
	private static final String COMPANY_A_NAME="A";
	private static final String COMPANY_B_NAME="B";
	
	
	public Set<Product> doCatalogMerge() throws Exception { 

			List<Product> collectionOfCatalogAProducts = new ArrayList<>();
			List<Product> collectionOfCatalogBProducts  = new ArrayList<>();
			List<Barcode> collectionOfABarcodes  = new ArrayList<>(); 
			List<Barcode> collectionOfBBarcodes  = new ArrayList<>();
			
			/**
			 * This method is to convert all the CSV files with the specific bean objects
			 */
			try {
				Path path = Paths.get(INPUT_FILES_PATH);
				Path absolutePath = path.toAbsolutePath();
				File [] files = null;
				if(absolutePath != null) {
					files = new File(absolutePath.toString()).listFiles(obj -> obj.isFile() && obj.getName().endsWith(".csv"));
				}
				
				
				if(files.length > 0) {
						for(File inputFile : files) {
							String refinedFileName = inputFile.getName().substring(0, inputFile.getName().indexOf('.'));
							switch(refinedFileName) {
							case "catalogA":
								collectionOfCatalogAProducts.addAll(CSVFileProcess.convertFileToObject(inputFile, Product.class));
								collectionOfCatalogAProducts.forEach(product -> product.setCompanyName(COMPANY_A_NAME));
								break;
							case "catalogB":
								collectionOfCatalogBProducts.addAll(CSVFileProcess.convertFileToObject(inputFile, Product.class));
								collectionOfCatalogBProducts.forEach(product -> product.setCompanyName(COMPANY_B_NAME));
								break;
							case "barcodesA":		
								collectionOfABarcodes.addAll(CSVFileProcess.convertFileToObject(inputFile, Barcode.class));
								break;
							case "barcodesB":		
								collectionOfBBarcodes.addAll(CSVFileProcess.convertFileToObject(inputFile, Barcode.class));
								break;
							}
						}
			  }else {
				  throw new Exception("Error in retrieving the CSV files from the input path");
			  }
				
			} catch (Exception e1) {
				throw new Exception("Error in converting the CSV file to object");
			}
			
			Map<String, List<String>> listOfCatABarcodes = new HashMap<>();
			createSupplierBarcodeMap(collectionOfABarcodes, listOfCatABarcodes);
			Map<String, List<String>> listOfCatBBarcodes = new HashMap<>();
			createSupplierBarcodeMap(collectionOfBBarcodes, listOfCatBBarcodes);
			Set<Product> combinedList =  getCombinedListOfProducts(listOfCatABarcodes,listOfCatBBarcodes,collectionOfABarcodes,collectionOfBBarcodes, collectionOfCatalogAProducts,
					collectionOfCatalogBProducts);
			//Write the product list to a CSV file
			Path path1 = Paths.get(OUTPUT_FILES_PATH + "result.csv");
			Path absolutePath1 = path1.toAbsolutePath();
			CSVFileProcess.writeToCsv(absolutePath1, combinedList);
			return combinedList != null ? combinedList : null;
	}

	/**
	 * Get the matching and non matching SKU as list
	 * 
	 * @param listOfCatABarcodes
	 * @param listOfCatBBarcodes
	 * @param collectionOfABarcodes
	 * @param collectionOfBBarcodes
	 * @return
	 * @throws Exception 
	 */
	public Set<String> getCombinedSKUList(Map<String, List<String>> listOfCatABarcodes,
			Map<String, List<String>> listOfCatBBarcodes, List<Barcode> collectionOfABarcodes, List<Barcode> collectionOfBBarcodes) throws Exception {
		
		//Collecting the matching SKU from first catalog
		Set<String> matchingSKU;
		try {
			//This method will give the list of suppliers which has same barcodes in both the catalogs
			Set<String> listofSuppliers = listOfCatABarcodes.entrySet().stream()
					.filter(d -> d.getValue().stream().anyMatch(p->listOfCatBBarcodes.get(d.getKey()).contains(p)))
					.map(Map.Entry::getKey)
					.collect(Collectors.toSet());
			
			//Apart from the list of suppliers, collecting the SKU from non matching suppliers from both catalog
			Set<String> nonMatchingSKUFromA = collectionOfABarcodes.stream().filter(e -> !listofSuppliers.contains(e.getSupplierId()))
					.map(Barcode::getSku)
					.collect(Collectors.toSet());
			
			Set<String> nonMatchingSKUfromB = collectionOfBBarcodes.stream().filter(e -> !listofSuppliers.contains(e.getSupplierId()))
					.map(Barcode::getSku)
					.collect(Collectors.toSet());


			nonMatchingSKUFromA.addAll(nonMatchingSKUfromB);

			matchingSKU = collectionOfABarcodes.stream().filter(e -> listofSuppliers.contains(e.getSupplierId()))
					.map(Barcode::getSku)
					.collect(Collectors.toSet());

			matchingSKU.addAll(nonMatchingSKUFromA);
		} catch (Exception e) {
			throw new Exception("Error in collecting the matching SKU's" + e.getMessage());
		}
        return matchingSKU;
	}

	
	
	/**
	 * Get the list of products from the combined SKU list
	 * 
	 * @param listOfCatABarcodes
	 * @param listOfCatBBarcodes
	 * @param collectionOfABarcodes
	 * @param collectionOfBBarcodes
	 * @param collectionOfCatalogAProducts
	 * @param collectionOfCatalogBProducts
	 * @return
	 * @throws Exception 
	 */
	public Set<Product> getCombinedListOfProducts(Map<String, List<String>> listOfCatABarcodes,
		Map<String, List<String>> listOfCatBBarcodes, List<Barcode> collectionOfABarcodes, List<Barcode> collectionOfBBarcodes, List<Product> collectionOfCatalogAProducts, List<Product> collectionOfCatalogBProducts) throws Exception {
		Set<Product> combinedList = new HashSet<>();
		try {
			
			if(listOfCatABarcodes.size() > 0 && listOfCatBBarcodes.size() > 0) {
				Set<String> combinedSKU = getCombinedSKUList(listOfCatABarcodes,listOfCatBBarcodes,collectionOfABarcodes,collectionOfBBarcodes);
				if(!combinedSKU.isEmpty() && combinedSKU.size() > 0) {	
					combinedList =  Stream.of(collectionOfCatalogAProducts, collectionOfCatalogBProducts)
							.flatMap(List::stream)
							.filter(e -> combinedSKU.contains(e.getSku()))
							.filter(distinctByKeys(Product::getSku))
							.collect(Collectors.toSet());

					combinedList.forEach(p -> System.out.println(p.getDescription() + ' ' + p.getCompanyName() + ' ' + p.getSku()));
					
				}
			}
		} catch (Exception e) {
			throw new Exception("Error in getting the combined product list" + e.getMessage());
		} 
		return combinedList;
	}

	/**
	 * Creating the map of suppliers and barcodes
	 * 
	 * @param collectionOfBarcodes
	 * @param listOfCatBarcodes
	 * @return
	 * @throws Exception 
	 */
	public Map<String, List<String>> createSupplierBarcodeMap(List<Barcode> collectionOfBarcodes, Map<String, List<String>> listOfCatBarcodes) throws Exception {
		try {
			collectionOfBarcodes.forEach(barcode -> {
				listOfCatBarcodes.computeIfAbsent(barcode.getSupplierId(), k -> new ArrayList<>()).add(barcode.getBarcodes());
			});
		} catch (Exception e) {
			throw new Exception("Error in creating the map" + e.getMessage());
		}
		
		return listOfCatBarcodes;
	}


	/**
	 * 
	 * @param <T>
	 * @param keyExtractors
	 * @return
	 */
	@SafeVarargs
	private static <T> Predicate<T> distinctByKeys(Function<? super T, ?>... keyExtractors) 
	{
		final Map<List<?>, Boolean> seen = new ConcurrentHashMap<>();

		return t -> 
		{
			final List<?> keys = Arrays.stream(keyExtractors)
					.map(ke -> ke.apply(t))
					.collect(Collectors.toList());

			return seen.putIfAbsent(keys, Boolean.TRUE) == null;
		};
	}

}
