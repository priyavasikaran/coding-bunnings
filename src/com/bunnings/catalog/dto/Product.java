package com.bunnings.catalog.dto;

import com.opencsv.bean.CsvBindByPosition;

public class Product {
	
	@CsvBindByPosition(position = 0)
	public String sku;
	@CsvBindByPosition(position = 1)
	public String description;
	public String companyName;
	
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	@Override
	public boolean equals(Object o) {
	    if (!(o instanceof Product))
	        return false;
	    Product n = (Product) o;
	    return n.sku.equals(sku) || n.description.contains(description);
	}

}
