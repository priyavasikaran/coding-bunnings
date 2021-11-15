package com.bunnings.catalog.dto;


import com.opencsv.bean.CsvBindByPosition;

public class Barcode {
	
	@CsvBindByPosition(position = 0)
	private String supplierId;
	@CsvBindByPosition(position = 1)
	private String sku;
	@CsvBindByPosition(position = 2)
	private String barcodes;
	
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getBarcodes() {
		return barcodes;
	}
	public void setBarcodes(String barcodes) {
		this.barcodes = barcodes;
	}
	
	@Override
	public boolean equals(Object o) {
	    if (!(o instanceof Barcode))
	        return false;
	    Barcode n = (Barcode) o;
	    return n.barcodes.equals(barcodes);
	}

}
