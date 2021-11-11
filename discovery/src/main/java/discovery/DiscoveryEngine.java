package discovery;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.swing.JFileChooser;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DiscoveryEngine {
	
	static final Logger logger = LoggerFactory.getLogger(DiscoveryEngine.class);

	static HashMap<String, Row> uniqueTabs = new HashMap<String, Row>();
	static HashMap<String, ArrayList<Object>> topTen = new HashMap<String, ArrayList<Object>>();

	public static void main(String[] args) {
		System.out.println("Starting main DiscoveryEngine");
		DiscoveryEngine discoveryEngine = new DiscoveryEngine();
		/* create data:
		  -10, -9, 100, 181, true, 0.0
			-9, -8, 81, 145, false, 4.358898943540674
			-8, -7, 64, 113, true, 6.0
			-7, -6, 49, 85, false, 7.14142842854285
			-6, -5, 36, 61, true, 8.0
			-5, -4, 25, 41, false, 8.660254037844387
			-4, -3, 16, 25, true, 9.16515138991168
			-3, -2, 9, 13, false, 9.539392014169456
			-2, -1, 4, 5, true, 9.797958971132712
			-1, 0, 1, 1, false, 9.9498743710662
			0, 1, 0, 1, true, 10.0
			1, 2, 1, 5, false, 9.9498743710662
			2, 3, 4, 13, true, 9.797958971132712
			3, 4, 9, 25, false, 9.539392014169456
			4, 5, 16, 41, true, 9.16515138991168
			5, 6, 25, 61, false, 8.660254037844387
			6, 7, 36, 85, true, 8.0
			7, 8, 49, 113, false, 7.14142842854285
			8, 9, 64, 145, true, 6.0
			9, 10, 81, 181, false, 4.358898943540674
			10, 11, 100, 221, true, 0.0
		 */
		ArrayList<ArrayList<Object>> data = new ArrayList<ArrayList<Object>>();
		Object[][] rawData = new Object[32][32];
		for (int i=-10; i<=10; i++) {
			double circle = Math.sqrt(100 -i*i);
			System.out.println(i + ", " + (i+1) + ", " + (i*i) + ", " + (i*i + (i+1)*(i+1)) + ", " + ((i%2==0) ? "true" : "false") + ", " + circle);
			rawData[i][0] = i;
			rawData[i][1] = i+1;
			rawData[i][2] = i*i + 3*i + 4;
			rawData[i][3] = (i*i + (i+1)*(i+1));
			rawData[i][4] = ((i%2==0) ? "true" : "false");
			rawData[i][5] = Math.sqrt(100 -i*i);
		}
		
	}
	
	public void run() {
		long begin = System.currentTimeMillis();
		String filename = "C:\\Users\\ttoth-fejel\\Documents\\FUL Reports\\MDP-May 2018 - Pretest run\\ACA_FUL_Price_TrueUp_2018_5 Sanjay.xltm";
		// Alternatively, Create and run a file chooser (for non-Eclipse users)
//		JFileChooser fc = new JFileChooser();
//		int returnVal = fc.showOpenDialog(null);
//		String filename = fc.getSelectedFile().getAbsolutePath();
//		System.out.println("\n DiscoveryEngine.run: returnVal " + returnVal + " filename=" + filename);

		HashMap<Integer,String> alphabetMap  = new HashMap<Integer, String>() {{
			put(1, "A");  	put(2, "B");  	put(3, "C");  	put(4, "D");
			put(5, "E");  	put(6, "F");  	put(7, "G");  	put(8, "H");
			put(9, "I"); 	put(10, "J");  	put(11, "K");  	put(12, "L");
			put(13, "M");  	put(14, "N");  	put(15, "O");  	put(16, "P");
			put(17, "Q"); 	put(18, "R");  	put(19, "S");  	put(20, "T");
			put(21, "U");  	put(22, "V");  	put(23, "W");  	put(24, "X");
			put(25, "Y");  	put(26, "Z");   put(27, "AA");
		}};

		double mostFrequentNumberAll = Double.MAX_VALUE;
		String longestAll = "";
		String regex = "^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$";   // Date format ~ mm/dd/yyyy
		Pattern pattern = Pattern.compile(regex);
		Date earliestAll = new Date();
		Date latestAll = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Map<String, String[]> priceActualHeadingsMap = new HashMap<String, String[]>();
		Map<String, String[]> unmatchedActualHeadingsMap = new HashMap<String, String[]>();
		Map<String, String[]> trueUpActualHeadingsMap = new HashMap<String, String[]>();
		Map<String, String[]> thisHeadingsMap = new HashMap<String, String[]>();
		ArrayList<ArrayList<Object>> extremeDetails = new ArrayList<ArrayList<Object>>();
		ArrayList<ArrayList<Object>> fiveIList = new ArrayList<ArrayList<Object>>();
		ArrayList<ArrayList<Object>> nonARatedList = new ArrayList<ArrayList<Object>>();
		ArrayList<ArrayList<Object>> sDrugsList = new ArrayList<ArrayList<Object>>();
		ArrayList<ArrayList<Object>> groupsWithMultpleGcnList = new ArrayList<ArrayList<Object>>();
		ArrayList<ArrayList<Object>> gncsInMultipleGroupsList = new ArrayList<ArrayList<Object>>();
		ArrayList<ArrayList<Object>> fulGroupsExcludedMultipleNadac = new ArrayList<ArrayList<Object>>();
		ArrayList<ArrayList<Object>> fulGroupsExcludedNoNadac = new ArrayList<ArrayList<Object>>();
		ArrayList<ArrayList<Object>> acaFulGroupsForPublication = new ArrayList<ArrayList<Object>>();

		try {
			FileInputStream excelFile = new FileInputStream(new File(filename));
			System.out.println("\n DiscoveryEngine: Reading file " + filename);
			Workbook workbook = new XSSFWorkbook(excelFile);
			int numberOfSheets = workbook.getNumberOfSheets();
			String fileType = "";
			if (numberOfSheets==3) { fileType = "UNMATCHED"; }
			else if (numberOfSheets==16) { fileType = "PRICE"; }
			else if (numberOfSheets==20) { fileType = "TRUE-UP"; } 
			else {
				System.out.println("\n Finished the file " + filename + " that contains " + numberOfSheets + " sheets/tabs. Unrecognizable FUL Report.");
				return;
			}
			System.out.println("fileType=" + fileType);
			for (int sheetNumber=0; sheetNumber<numberOfSheets; sheetNumber++) {
				org.apache.poi.ss.usermodel.Sheet datatypeSheet = workbook.getSheetAt(sheetNumber);    //SI or SS?
				String sheetName = datatypeSheet.getSheetName();
				System.out.println("\n" + sheetNumber + ". Reading sheet " + sheetName);
				double maxSheet = 0.0;
				double mostFrequentNumberSheet = Double.MAX_VALUE;
				String longestSheet = "";
				Date earliestSheet = new Date();
				Date latestSheet = new Date();
				Map<String, Integer> freqMap = new HashMap<String, Integer>();
				String mapAccessor = fileType + datatypeSheet.getSheetName();
				Iterator<Row> iterator = datatypeSheet.iterator();
				int rowNumber = 0;
				outerloop:
					while (iterator.hasNext()) {
						Row currentRow = iterator.next();
						ArrayList<Object> record = new ArrayList<Object>();
						uniqueTabs.put(mapAccessor, currentRow);

						//Get Headings
						if (rowNumber==0) {
							Iterator<Cell> cellIterator0 = currentRow.iterator();
							System.out.print("Columns for tab " + sheetName + ":");
							while (cellIterator0.hasNext()) {
								Cell currentCell = cellIterator0.next();
								//System.out.println("currentCell=" + currentCell.toString().trim() + " " + currentCell.getCellTypeEnum());
								//getCellTypeEnum shown as deprecated for version 3.15
								//getCellTypeEnum will be renamed to getCellType starting from version 4.0
								// System.out.print(sheetName + ":--" + currentCell.getCellTypeEnum() + ":");
								if (currentCell.getCellTypeEnum() == CellType.STRING) {
									String thisValue = currentCell.getStringCellValue();
									record.add(thisValue);
									if (StringUtils.isEmpty(thisValue)) {
										System.out.println("~~empty~~");
									} else {
										System.out.print("--" + thisValue + "--");
									}
								} else {
									record.add(null);
									System.out.print("==UnknownType==");
								}
							}
							System.out.println();

							ArrayList<String> recordStrings = new ArrayList<String>();
							for (Object columnObject : record) {
								recordStrings.add((String)columnObject);
							}
							String[] columnHeadings = new String[recordStrings.size()];
							System.arraycopy(recordStrings.toArray(), 0, columnHeadings, 0, columnHeadings.length);
							if (fileType.equals("UNMATCHED")) { 
								unmatchedActualHeadingsMap.put(sheetName, columnHeadings); 
								thisHeadingsMap = unmatchedActualHeadingsMap; 
							}
							else if (fileType.equals("PRICE")) { 
								priceActualHeadingsMap.put(sheetName, columnHeadings); 
								thisHeadingsMap = priceActualHeadingsMap;
							}
							else if (fileType.equals("TRUE-UP")) { 
								trueUpActualHeadingsMap.put(sheetName, columnHeadings); 
								thisHeadingsMap = trueUpActualHeadingsMap;
							} 
							System.out.print(numberOfSheets + ". thisHeadingsMap: ");
							for (Map.Entry<String, String[]> entry : thisHeadingsMap.entrySet()) {
								String key = entry.getKey();
								String[] values = entry.getValue();
								System.out.println(" key=" + key + " values=" + Arrays.toString(values)); 
							}
						} 

						// Check XML for missing Nulls
						//System.out.println(j + ". getPhysicalNumberOfCells=" + currentRow.getPhysicalNumberOfCells());
						//System.out.println(j + ". Row=" + currentRow.toString());
						// Unfortunately, Microsoft seems to have forgotten to put in Null for empty cells, and POI misses it.  
						// So I need to handle the resulting disaster and drop into XML.
						String xml = currentRow.toString();
						DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
						DocumentBuilder builder = factory.newDocumentBuilder(); // org.w3c.dom.Document
						Document document = builder.parse(new InputSource(new StringReader(xml)));
						Element rootElement = document.getDocumentElement();
						//System.out.println("\n rootElement=" + rootElement.toString());
						if (rootElement != null && rootElement.getChildNodes() != null) {
							NodeList subList = rootElement.getChildNodes();
							//System.out.println("\n subList=" + subList.toString());
							if (subList != null && subList.getLength() > 0) {
								//System.out.println("subList.getLength=" + subList.getLength());
								int m = 0;
								for (int k=1; k<subList.getLength(); k++) {
									String cellValue = subList.item(k).getTextContent().trim();
									//System.out.println(k + ". subList name=" + subList.item(k).getNodeName() + " value=" + subList.item(k).getNodeValue() + 
									//		". subList.item(k).getTextContent()=" + cellValue);
									if (subList.item(k).getNodeName().equals("main:c")) {
										m = m + 1;
									}
									//	if (subList.item(k).getNodeName().equals("#text")) {
									//			 m = m + 1;
									//	}
									NamedNodeMap attributesList = subList.item(k).getAttributes();
									if (attributesList != null) {
										//			            			for (int n = 0; n < attributesList.getLength(); n++) {
										//				            	        System.out.println(n + ". Attribute: "
										//				            	                + attributesList.item(n).getNodeName() + " = "
										//				            	                + attributesList.item(n).getNodeValue());
										//				            	    }
										String cellName = attributesList.item(0).getNodeValue();
										// (cellName.length() > 1 && System.out.println(m + ". cellName=" + cellName + " starts with " + cellName.substring(0, 1) + "=?" + alphabetMap.get(m));
										if (!(cellName.substring(0, 1).equals(alphabetMap.get(m)) || 
												cellName.substring(0, 2).equals(alphabetMap.get(m)))) {
											System.out.println("thisHeadingsMap.get(sheetName)=" + Arrays.toString(thisHeadingsMap.get(sheetName)));
											System.out.println("Ugly Excel format (cellName.substring(0, 1)=" + cellName.substring(0, 1) +
													" alphabetMap.get(m)=" + alphabetMap.get(m) + ") for null content to keep POI ignorant of empty cell. " + // currentRow.toString() + // dumps xml
													"Appears to be missing the entry in column #" + m + " ("  + (m<=0 ? "null" : thisHeadingsMap.get(sheetName)[m-1]) + ")." + 
													" rowNumber=" + (rowNumber+1) + " filename=" + filename + " type=" + fileType + " tab='" + sheetName + "'.");   
											// Insert? TBD?
											continue outerloop;
										} else {
											//System.out.println(m + ". cellName=" + cellName + " matches."); 
										}
									}
								}
							}
						}

						// Run for non-first rows:
						if (rowNumber!=0) {
							Iterator<Cell> cellIterator = currentRow.iterator();
							System.out.print(sheetName + ":");
							while (cellIterator.hasNext()) {
								Cell currentCell = cellIterator.next();
								//System.out.println("currentCell=" + currentCell.toString().trim() + " " + currentCell.getCellTypeEnum());
								//getCellTypeEnum shown as deprecated for version 3.15
								//getCellTypeEnum will be renamed to getCellType starting from version 4.0
								// System.out.print(sheetName + ":--" + currentCell.getCellTypeEnum() + ":");
								if (currentCell.getCellTypeEnum() == CellType.STRING) {
									String thisValue = currentCell.getStringCellValue();
									record.add(thisValue);
									System.out.print("--" + thisValue + "--");
									int prev = 0;
									// get previous count
									if (freqMap.get(thisValue) != null) {
										prev = freqMap.get(thisValue);
									}
									freqMap.put(thisValue, prev + 1);
									if (StringUtils.isEmpty(thisValue)) {
										System.out.println("~~empty~~");
									} else if (pattern.matcher(thisValue).matches()) {
										//System.out.println("Checking date " + thisValue);
										Date thisDate;
										try {
											thisDate = simpleDateFormat.parse(thisValue);
											if (thisDate.before(earliestSheet)) { 
												earliestSheet = thisDate; 
											}
											if (thisDate.after(latestSheet)) { 
												latestSheet = thisDate; 
											}
										} catch (ParseException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									} else if (thisValue.length() > longestSheet.length()) {
										longestSheet = thisValue;
									}
								} else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
									double thisNumber = currentCell.getNumericCellValue();
									if (thisNumber > maxSheet) { 
										maxSheet = thisNumber; 
									}
									record.add(thisNumber);
									System.out.print("++" + thisNumber + "++");
								} else {
									record.add(null);
									System.out.print("==" + null + "==");
								}
							}
							System.out.println();

							if (sheetName.equals("Extreme Details")) { extremeDetails.add(record); }
							if (sheetName.equals("5I")) { fiveIList.add(record); }
							if (sheetName.equals("NON-A Rated NDCs")) { nonARatedList.add(record); }
							if (sheetName.equals("S Drugs")) { sDrugsList.add(record); }
							if (sheetName.equals("Groups with Mult GCN")) { groupsWithMultpleGcnList.add(record); }
							if (sheetName.equals("GCNs in Mult Groups")) { gncsInMultipleGroupsList.add(record); }
							if (sheetName.equals("FUL Groups Excluded-Mult NADAC")) { fulGroupsExcludedMultipleNadac.add(record); }
							if (sheetName.equals("FUL Groups Excluded-No NADAC")) { fulGroupsExcludedNoNadac.add(record); }
							if (sheetName.equals("ACA FUL Groups for Publication")) { acaFulGroupsForPublication.add(record); }
						}
						rowNumber = rowNumber + 1;
					}  // End of this sheet

				System.out.println("\n " + sheetNumber + ". Sheet Summary: ");   // unordered
				Map<String, Integer> sorted = freqMap
						.entrySet()
						.stream()
						.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))   // Collections.reverseOrder(Map.Entry.comparingByValue())   // Map.Entry.comparingByValue()
						.collect( Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2,
										LinkedHashMap::new));	
				System.out.println(sheetNumber + "Frequency map, sorted by values: " + sorted);
				System.out.println(sheetNumber + "Sheet max: " + maxSheet);
				System.out.println(sheetNumber + "Sheet longest: '" + longestSheet + "'");
				System.out.println(sheetNumber + ". End of sheet " + datatypeSheet.getSheetName());
			}  // end of all sheets
//			status = testAllSheetColumnHeadings(filename, fileType, status, priceActualHeadingsMap, unmatchedActualHeadingsMap, trueUpActualHeadingsMap);  
//			status = testFulPriceComputation(extremeDetails, status, fileType);
//			status = testFulExtremeFields(extremeDetails, status, fileType);
//			status = test5iFields(fiveIList, status, fileType);
//			status = testNonARated(nonARatedList, status, fileType);
//			// NR Amp and NDAs should both have no data
//			status = testColumnData(sDrugsList, status, fileType, "S Drugs");
//			status = testGroupsWithMultpleGcn(groupsWithMultpleGcnList, status, fileType, "Groups with Mult GCN");
//			status = testGncsInMultipleGroupsList(gncsInMultipleGroupsList, status, fileType, "GCNs in Mult Groups");
//			status = testColumnData(fulGroupsExcludedMultipleNadac, status, fileType, "FUL Groups Excluded-Mult NADAC");
//			status = testColumnData(fulGroupsExcludedNoNadac, status, fileType, "FUL Groups Excluded-No NADAC");
//			status = testColumnData(acaFulGroupsForPublication, status, fileType, "ACA FUL Groups for Publication");

		} catch (FileNotFoundException fnfex) {
			System.out.println("FileNotFoundException Error while trying to read file " + filename);
			fnfex.printStackTrace();
		} catch (IOException ioex) {
			System.out.println("IOException Error while trying to read file " + filename);
			ioex.printStackTrace();
		} catch (IllegalArgumentException iaex) {
			System.out.println("Possibly ran out of tabs, or ran into the Excel/POI missing data cell bug while trying to read file " + filename);
			iaex.printStackTrace();
		} catch (ParserConfigurationException pcex) {
			System.out.println("POI error while trying to parse xml in file " + filename);
			pcex.printStackTrace();
		} catch (SAXException saxex) {
			System.out.println("POI error while trying to reading xml in file " + filename);
			saxex.printStackTrace();
		}
		System.out.println("\n DiscoveryEngine: Finished processing file " + filename);

		//Mukheeth's email generator: :-P
		//		String alphabet = "abcdef ghijklm nopqrst uvwxyz ABCDEF GHIJKLM NOPQRST UVWXYZ";
		//	    int length = alphabet.length();
		//	    Random random = new Random();
		//	    System.out.print("Mukheeth's giberish: ");
		//	    for (int i = 0; i < 150; i++) {
		//	        System.out.print(alphabet.charAt(random.nextInt(length)));
		//	    }
		long end = System.currentTimeMillis();
		long timeElapsed = end - begin;
		logger.info("Time elapsed "+ timeElapsed/1000.0 + " seconds.");
		System.out.println();
	}

}
