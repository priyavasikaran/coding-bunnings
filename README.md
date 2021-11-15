# Bunnings Catalog Merge 

Actual repository of the coding challenge can be found here
https://github.com/tosumitagrawal/codingskills

Business Requirement from Bunnings is to merge two different catalogs (A and B) into one based on few of the requisites given. The logic is worked out to find the common products between each catalogs based on the barcodes supplied by the vendors. 

Catalog, Barcodes and Supplier data feed is provided via CSV files and the final catalog list is expected in a CSV file.

## Getting Started

The solution has been worked out in Java including the CSV file processing steps and JUnit cases.

Interface design pattern is used in the application. Main.java is the  entry point of the application. 

There are three important classes

- CatalogMergeImpl.java  - Implementation class of the CatalogMerge interface. Consists of reading and converting CSV files data to POJO Objects, comparing the barcodes and creates the unique list of products

- CSVFileProcess.java - Standalone methods to read and write CSV file using the [opencsv](http://opencsv.sourceforge.net/) library. 
 
- CatalogMergeImplTest.java - JUnit classes for the CatalogMerge interface class.

## Usage

All the csv files to be processed should present at the src/input folder path.

### Prerequisites

JDK 1.8

JRE 1.8

#### Build

Import the application into any IDE and build from it

### Run the application

It can be run from the IDE as "Run as Java Application" or from the Command line

## Improvements

- Separate standalone methods for CSV reader and writer and convert to objects
- Modularity of the code is maintained.
