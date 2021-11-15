package com.bunnings.catalog;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.bunnings.catalog.dto.Product;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

/**
 * 
 * @author xsvng
 * 
 * This class consists of all the CSV file processing methods
 *
 */
public class CSVFileProcess{

	public static <T> List<T> convertFileToObject(File file, Class<T> target) throws Exception {
		if (file == null) {
			throw new Exception("No file uploaded!");
		}
		try {

			CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(new FileReader(file))
					.withType(target)
					.withSkipLines(1)
					.withIgnoreLeadingWhiteSpace(true)
					.build();
			return csvToBean.parse();
		} catch (IOException e) {
			throw new Exception("Can't convert file to target class");
		}
	}

	public static void writeToCsv(Path path, Set<Product> productList) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
		File file = new File(path.toString());
		// create FileWriter object with file as parameter
		FileWriter outputfile = new FileWriter(file);
		// create CSVWriter with ',' as separator
		CSVWriter writer = new CSVWriter(outputfile, ',',
				CSVWriter.NO_QUOTE_CHARACTER,
				CSVWriter.DEFAULT_ESCAPE_CHARACTER,
				CSVWriter.DEFAULT_LINE_END);

		List<String[]> data = new ArrayList<String[]>();
		data.add(new String[] { "SKU", "Description", "Source" });
		productList.forEach(product -> data.add(new String[] {product.getSku(), product.getDescription(), product.getCompanyName()}));
		writer.writeAll(data);
		// closing writer connection
		writer.close();
	}
}
