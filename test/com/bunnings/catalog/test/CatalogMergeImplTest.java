package com.bunnings.catalog.test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.bunnings.catalog.CatalogMerge;
import com.bunnings.catalog.dto.Product;
import com.bunnings.catalog.impl.CatalogMergeImpl;

import junit.framework.Assert;

public class CatalogMergeImplTest {
	
	CatalogMerge catalogMerge;
	
	private static final String INPUT_TEST_PATH="test/inputTestFolder/";
	
	@Before
	public void init()
    {
		catalogMerge = Mockito.mock(CatalogMergeImpl.class);
		
    }
	
	@Test
	public void testCatalogMergeWithEmptyFilePath() throws Exception
	{
		Path path = Paths.get("");
		Set<Product> productList = catalogMerge.doCatalogMerge();
		Assert.assertEquals(Set.of(), productList);
		
	}
	
	@Test
	public void testCatalogMergeWithEmptyFilesFromPath() throws Exception
	{
		Path path = Paths.get(INPUT_TEST_PATH);
		Set<Product> productList = catalogMerge.doCatalogMerge();
		Assert.assertEquals(Set.of(), productList);
		
	}
	
	@Test
	public void testCatalogMergeWithFilesFromPath() throws Exception
	{
		Path path = Paths.get("test/input/");
		Path absolutePath = path.toAbsolutePath();
		System.out.println(absolutePath);
		Set<Product> productList = catalogMerge.doCatalogMerge();
		System.out.println(productList);
		Assert.assertEquals(Set.of(), productList);
		
	}

}
