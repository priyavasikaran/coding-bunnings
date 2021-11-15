package com.bunnings.catalog.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import com.bunnings.catalog.CSVFileProcess;
import com.bunnings.catalog.CatalogMerge;
import com.bunnings.catalog.dto.Barcode;
import com.bunnings.catalog.dto.Product;
import com.bunnings.catalog.impl.CatalogMergeImpl;
import junit.framework.Assert;

/**
 * 
 * @author xsvng
 * 
 * Test cases for the Catalog merge class
 *
 */

@RunWith(MockitoJUnitRunner.Silent.class) 
public class CatalogMergeImplTest {
	
	@Mock
	private CatalogMerge catalogMerge;
	
	@Mock
	private CSVFileProcess csvFileProcess;
	
	@Mock
	private Barcode barcode;
	
	@Mock
	private Product product;
	
	List<Barcode> barcodeListA;
	List<Barcode> barcodeListB;
	Map<String, List<String>> listOfCatABarcodes;
	Map<String, List<String>> listOfCatBBarcodes;
	List<String> barcodesA1;
	List<String> barcodesB1;
	List<String> barcodesA2;
	List<String> barcodesB2;
	Set<String> skuList;
	Map<String, List<String>> listOfCatBarcodes;
	List<Product> catalogAList;
	List<Product> catalogBList;
	Set<Product> finalList;
	
	
	@Before
	public void init()
    {		
		catalogMerge= mock(CatalogMergeImpl.class);
		csvFileProcess= mock(CSVFileProcess.class);
		barcodeListA = new ArrayList<>();
		barcodeListB = new ArrayList<>();
		listOfCatABarcodes = new HashMap<String, List<String>>();
		listOfCatBBarcodes = new HashMap<String, List<String>>();
		barcodesA1 = new ArrayList<>();
		barcodesB1 = new ArrayList<>();
		barcodesA2 = new ArrayList<>();
		barcodesB2 = new ArrayList<>();
		skuList = new HashSet<>();
		listOfCatBarcodes = new HashMap<String, List<String>>();
		catalogAList = new ArrayList<>();
		catalogBList = new ArrayList<>();
		finalList = new HashSet<>();
		
    }
	
	@Test
	public void testCombinedSKUList() throws Exception
	{
		Barcode barcodeA1 = Mockito.mock(Barcode.class);
		barcodeA1.setBarcodes("RES2342");
		barcodeA1.setSku("BUNNINGS001");
		barcodeA1.setSupplierId("00001");
		Barcode barcodeA2 = Mockito.mock(Barcode.class);
		barcodeA2.setBarcodes("RES2345");
		barcodeA2.setSku("BUNNINGS002");
		barcodeA2.setSupplierId("00002");
		
		Barcode barcodeB1 = Mockito.mock(Barcode.class);
		barcodeB1.setBarcodes("RES2342");
		barcodeB1.setSku("BUNNINGS003");
		barcodeB1.setSupplierId("00001");
		Barcode barcodeB2 = Mockito.mock(Barcode.class);
		barcodeB2.setBarcodes("TRES789");
		barcodeB2.setSku("BUNNINGS004");
		barcodeB2.setSupplierId("00002");
	
		barcodeListA.add(barcodeA1);
		barcodeListA.add(barcodeA2);
		
		barcodeListB.add(barcodeB1);
		barcodeListB.add(barcodeB2);
		
		barcodesA1.add("RES2342");
		barcodesA2.add("RES2345");
		barcodesB1.add("RES2342");
		barcodesB2.add("TRES789");
		
		listOfCatABarcodes.put("00001", barcodesA1);
		listOfCatABarcodes.put("00002", barcodesA2);
		listOfCatBBarcodes.put("00001", barcodesB1);	
		listOfCatBBarcodes.put("00002", barcodesB2);
		
		skuList.add("BUNNINGS001");
		skuList.add("BUNNINGS002");
		skuList.add("BUNNINGS004");
		
		when(catalogMerge.getCombinedSKUList(listOfCatABarcodes, listOfCatBBarcodes, barcodeListA, barcodeListB)).thenReturn(skuList);
		final Set<String> result = catalogMerge.getCombinedSKUList(listOfCatABarcodes, listOfCatBBarcodes, barcodeListA, barcodeListB);
		Assert.assertEquals(result, skuList);		
		
	}
	
	@Test
	public void testSupplierBarcodeMapCreation() throws Exception
	{
		barcodesA1.add("RES2342");
		barcodesA2.add("RES2345");
		barcodesB1.add("RES2342");
		barcodesB2.add("TRES789");
		
		listOfCatABarcodes.put("00001", barcodesA1);
		listOfCatABarcodes.put("00002", barcodesA2);

		when(catalogMerge.createSupplierBarcodeMap(barcodeListA, listOfCatBarcodes)).thenReturn(listOfCatABarcodes);
		final Map<String, List<String>> result = catalogMerge.createSupplierBarcodeMap(barcodeListA, listOfCatBarcodes);
		Assert.assertEquals(result, listOfCatABarcodes);		
		
	}
	
	
	@Test
	public Set<Product> testCombinedListOfProducts() throws Exception
	{
		Barcode barcodeA1 = Mockito.mock(Barcode.class);
		barcodeA1.setBarcodes("RES2342");
		barcodeA1.setSku("BUNNINGS001");
		barcodeA1.setSupplierId("00001");

		Barcode barcodeA2 = Mockito.mock(Barcode.class);
		barcodeA2.setBarcodes("RES2345");
		barcodeA2.setSku("BUNNINGS002");
		barcodeA2.setSupplierId("00002");
		
		Barcode barcodeB1 = Mockito.mock(Barcode.class);
		barcodeB1.setBarcodes("RES2342");
		barcodeB1.setSku("BUNNINGS003");
		barcodeB1.setSupplierId("00001");
		Barcode barcodeB2 = Mockito.mock(Barcode.class);
		barcodeB2.setBarcodes("TRES789");
		barcodeB2.setSku("BUNNINGS004");
		barcodeB2.setSupplierId("00002");
	
		barcodeListA.add(barcodeA1);
		barcodeListA.add(barcodeA2);
		
		barcodeListB.add(barcodeB1);
		barcodeListB.add(barcodeB2);
		
		barcodesA1.add("RES2342");
		barcodesA2.add("RES2345");
		barcodesB1.add("RES2342");
		barcodesB2.add("TRES789");
		
		listOfCatABarcodes.put("00001", barcodesA1);
		listOfCatABarcodes.put("00002", barcodesA2);
		listOfCatBBarcodes.put("00001", barcodesB1);	
		listOfCatBBarcodes.put("00002", barcodesB2);
		
		Product productA1 = Mockito.mock(Product.class);
		productA1.setCompanyName("A");
		productA1.setDescription("Bunnings Test Product 1");
		productA1.setSku("BUNNINGS001");
		
		Product productA2 = Mockito.mock(Product.class);
		productA2.setCompanyName("A");
		productA2.setDescription("Bunnings Test Product 2");
		productA2.setSku("BUNNINGS002");
		
		Product productB1 = Mockito.mock(Product.class);
		productB1.setCompanyName("B");
		productB1.setDescription("Bunnings Test Product 3");
		productB1.setSku("BUNNINGS003");
		
		Product productB2 = Mockito.mock(Product.class);
		productB2.setCompanyName("B");
		productB2.setDescription("Bunnings Test Product 4");
		productB2.setSku("BUNNINGS004");
		
		catalogAList.add(productA1);
		catalogAList.add(productA2);
		
		catalogBList.add(productB1);
		catalogBList.add(productB2);
	
		
		finalList.add(productA1);
		finalList.add(productA2);
		finalList.add(productB2);

		when(catalogMerge.getCombinedListOfProducts(listOfCatABarcodes, listOfCatBBarcodes, barcodeListA, barcodeListB, catalogAList, catalogBList)).thenReturn(finalList);
		final Set<Product> result = catalogMerge.getCombinedListOfProducts(listOfCatABarcodes, listOfCatBBarcodes, barcodeListA, barcodeListB, catalogAList, catalogBList);
		Assert.assertEquals(result, finalList);
		return result;		
		
	}


}
