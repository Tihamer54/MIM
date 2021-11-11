package discovery;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.icu.text.RuleBasedNumberFormat;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

//import com.opencsv.CSVReader;


public class Utils implements java.io.Serializable {

	static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(Utils.class);

	public static void main(String[] args) {
		//		setPassword("1234ABCD");
		//		login("1234ABCD");
		//		System.out.println("Try breaking the 8-character alphanumeric password using the brute force approach.");
		//		breakLogin();
		//		System.out.println("Starting main Utils");
		//		BigDecimal bigDecimal0 = new BigDecimal(00000.00);
		//		BigDecimal bigDecimal2 = new BigDecimal(2);
		//		BigDecimal bigDecimal4 = new BigDecimal(4);  
		//		BigDecimal bigDecimal20 = new BigDecimal(2.000);
		//		System.out.println("Positive cases:");
		//		System.out.println("bigDecimal0=" + bigDecimal0 + " == zero is " + Utils.equalsZero(bigDecimal0));
		//		System.out.println("bigDecimal2=" + bigDecimal2 + " <  bigDecimal4=" + bigDecimal4 + " is " + Utils.lessThan(bigDecimal2, bigDecimal4));
		//		System.out.println("bigDecimal2=" + bigDecimal2 + " == bigDecimal20=" + bigDecimal20 + " is " + Utils.equals(bigDecimal2, bigDecimal20));
		//		System.out.println("bigDecimal2=" + bigDecimal2 + " <= bigDecimal20=" + bigDecimal20 + " is " + Utils.equals(bigDecimal2, bigDecimal20));
		//		System.out.println("bigDecimal2=" + bigDecimal2 + " <= bigDecimal4=" + bigDecimal4 + " is " + Utils.lessThanOrEquals(bigDecimal2, bigDecimal4));
		//		System.out.println("bigDecimal4=" + bigDecimal4 + " >  bigDecimal2=" + bigDecimal2 + " is " + Utils.greaterThan(bigDecimal4, bigDecimal2));
		//		System.out.println("bigDecimal4=" + bigDecimal4 + " >= bigDecimal2=" + bigDecimal2 + " is " + Utils.greaterThanOrEquals(bigDecimal4, bigDecimal2));
		//		System.out.println("bigDecimal2=" + bigDecimal2 + " >= bigDecimal20=" + bigDecimal20 + " is " + Utils.greaterThanOrEquals(bigDecimal2, bigDecimal20));
		//		System.out.println("Negative cases:");
		//		System.out.println("bigDecimal2=" + bigDecimal2 + " == zero is " + Utils.equalsZero(bigDecimal2));
		//		System.out.println("bigDecimal2=" + bigDecimal2 + " == bigDecimal4=" + bigDecimal4 + " is " + Utils.equals(bigDecimal2, bigDecimal4));
		//		System.out.println("bigDecimal4=" + bigDecimal4 + " <  bigDecimal2=" + bigDecimal2 + " is " + Utils.lessThan(bigDecimal4, bigDecimal2));
		//		System.out.println("bigDecimal4=" + bigDecimal4 + " <= bigDecimal2=" + bigDecimal2 + " is " + Utils.lessThanOrEquals(bigDecimal4, bigDecimal2));
		//		System.out.println("bigDecimal2=" + bigDecimal2 + " >  bigDecimal4=" + bigDecimal4 + " is " + Utils.greaterThan(bigDecimal2, bigDecimal4));
		//		System.out.println("bigDecimal2=" + bigDecimal2 + " >= bigDecimal4=" + bigDecimal4 + " is " + Utils.greaterThanOrEquals(bigDecimal2, bigDecimal4));

		System.out.println("stateToAbbreviation TreeMap=" + getStateAbbreviationTreeMap().toString());
		System.out.println("abbreviationToState TreeMap=" + getAbbreviationStateTreeMap().toString());
		
		String s1 = "What is 'man' that thou art mindful of him?";
		String s2 = "What is ‘Man’ that thou art mindful of him?";
		System.out.println("s1=" + s1 + "\ns2=" + s2 + "\n equalsAsciiIgnoreCase=" + equalsAsciiIgnoreCase(s1, s2));
		System.out.println("s1=" + s1 + "\ns2=" + s2 + "\n = equalsAscii=" + equalsAscii(s1, s2));
	}


	public static String[][] stateAbreviations = 
		{{"Alabama", "AL"},
				{"Alaska", "AK"}, {"Arizona", "AZ"}, {"Arkansas", "AR"}, {"California", "CA"}, {"Colorado", "CO"},
				{"Connecticut", "CT"}, {"Delaware", "DE"}, {"Florida", "FL"}, {"Georgia", "GA"}, {"Hawaii", "HI"},
				{"Idaho", "ID"}, {"Illinois", "IL"}, {"Indiana", "IN"}, {"Iowa", "IA"}, {"Kansas", "KS"},
				{"Kentucky", "KY"}, {"Louisiana", "LA"}, {"Maine", "ME"}, {"Maryland", "MD"}, {"Massachusetts", "MA"},
				{"Michigan", "MI"}, {"Minnesota", "MN"}, {"Mississippi", "MS"}, {"Missouri", "MO"}, {"Montana", "MT"},
				{"Nebraska", "NE"}, {"Nevada", "NV"}, {"New Hampshire", "NH"}, {"New Jersey", "NJ"}, {"New Mexico", "NM"},
				{"New York", "NY"}, {"North Carolina", "NC"}, {"North Dakota", "ND"}, {"Ohio", "OH"}, {"Oklahoma", "OK"}, {"Oregon", "OR"},  
				{"Pennsylvania", "PA"}, {"Rhode Island", "RI"}, {"South Carolina", "SC"}, {"South Dakota", "SD"}, {"Tennessee", "TN"},
				{"Texas", "TX"}, {"Utah", "UT"}, {"Vermont", "VT"}, {"Virginia", "VA"}, {"Washington", "WA"},
				{"West Virginia", "WV"}, {"Wisconsin", "WI"}, {"Wyoming", "WY"}, {"District of Columbia", "DC"},
				{"American Samoa", "AS"}, {"Federated States of Micronesia", "FM"}, {"Guam", "GU"}, {"Marshall Islands", "MH"},
				{"Northern Mariana Islands", "MP"}, {"Palau", "PW"}, {"Puerto Rico", "PR"}, {"Virgin Islands", "VI"}};

	public static TreeMap<String, String> getStateAbbreviationTreeMap() {
		TreeMap<String, String> stateToAbbreviation = new TreeMap();
		for (String[] pair : stateAbreviations) {
			//System.out.println("state=" + pair[0] + " abbreviation=" + pair[1]);
			stateToAbbreviation.put(pair[0], pair[1]);
		}
		return stateToAbbreviation;
	}

	public static TreeMap<String, String> getAbbreviationStateTreeMap() {
		TreeMap<String, String> abbreviationToState = new TreeMap();
		for (String[] pair : stateAbreviations) {
			//System.out.println("state=" + pair[0] + " abbreviation=" + pair[1]);
			abbreviationToState.put(pair[1], pair[0]);
		}
		return abbreviationToState;
	}


	/**
	 * Google's Diff Match Patch is good, but it was a pain to install into my Java maven project. 
	 * Just adding a maven dependency did not work; eclipse just created the directory and added the lastUpdated info files. 
	 * Finally, on the third try, I added the following to my pom:
	<dependency>
	    <groupId>fun.mike</groupId>
	     <artifactId>diff-match-patch</artifactId>
	    <version>0.0.2</version>
	</dependency>
	Then I manually placed the jar and source jar files into my .m2 repo from https://search.maven.org/search?q=g:fun.mike%20AND%20a:diff-match-patch%20AND%20v:0.0.2

	After all that, the following code worked:

		import fun.mike.dmp.Diff;
		import fun.mike.dmp.DiffMatchPatch;

		DiffMatchPatch dmp = new DiffMatchPatch();
		LinkedList<Diff> diffs = dmp.diff_main("Hello World.", "Goodbye World.");
		System.out.println(diffs);

	The ruleResults:

	[Diff(DELETE,"Hell"), Diff(INSERT,"G"), Diff(EQUAL,"o"), Diff(INSERT,"odbye"), Diff(EQUAL," World.")]

	Obviously, this was not originally written (or even ported fully) into Java. (diff_main? I can feel the C burning into my eyes :-) ) 
	Still, it works. And for people working with long and complex strings, it can be a valuable tool.

	DiffMatchPatch dmp = new DiffMatchPatch();
    LinkedList<Diff> diffs = dmp.diff_main("Hello World.", "Goodbye World.");
    System.out.println(diffs);
    dmp.diff_cleanupSemanticLossless(diffs);
    System.out.println(diffs);
	 */


	public static String exposedPassword = "1234asdf";

	public static void setPassword(String newPassword) {
		exposedPassword = newPassword;
	}

	public static boolean login(String password) {
		if (exposedPassword.equals(password)) {
			System.out.println("Password accepted: " + exposedPassword);
			return true;
		}
		return false;
	}

	/**
	 * The array of integers [3,4,7] has three elements and six permutations:
		n! = 3! = 1 x 2 x 3 = 6
		Permutations: [3,4,7]; [3,7,4]; [4,7,3]; [4,3,7]; [7,3,4]; [7,4,3]
	 */
	public static void breakLogin() {
		String possiblePassword = "";
		Character[] possibleCharacters = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
				'1', '2', '3', '4', '5', '6', '7', '8','9', '0'};
		int length = 8;
		//printAllRecursive(8, possibleCharacters);
		permutate(possibleCharacters);

	}

	public static ArrayList<ArrayList<Character>> permutate(Character[] num) {
		ArrayList<ArrayList<Character>> result = new ArrayList<ArrayList<Character>>();
		// Start from an empty list
		result.add(new ArrayList<Character>());
		for (int i = 0; i < num.length; i++) {
			//list of list in current iteration of the array num
			ArrayList<ArrayList<Character>> current = new ArrayList<ArrayList<Character>>();
			for (ArrayList<Character> word : result) {
				// # of locations to insert is largest index + 1
				for (int j = 0; j < word.size()+1; j++) {
					// + add num[i] to different locations
					word.add(j, num[i]);
					ArrayList<Character> temp = new ArrayList<Character>(word);
					current.add(temp);
					//System.out.println(temp);
					// - remove num[i] add
					word.remove(j);
				}
				System.out.println(Arrays.toString(word.toArray()));
			}
			result = new ArrayList<ArrayList<Character>>(current);
		}
		return result;
	}

	public static void printAllRecursive(int n, char[] elements) {
		if (n == 1) {
			String word = printArray(elements);
			System.out.println("Word: " + word);
			if (login(word)) {
				System.out.println("Password Broken: " + word);
				System.exit(0);
			}
		} else {
			for (int i = 0; i < n-1; i++) {
				printAllRecursive(n-1, elements);
				if (n%2 == 0) { swap(elements, i, n-1); }
				else { swap(elements, 0, n-1); }
			}
			printAllRecursive(n-1, elements);
		}
	}


	private static char[] swap(char[] input, int a, int b) {
		char tmp = input[a];
		input[a] = input[b];
		input[b] = tmp;
		return input;
	}

	private static String printArray(char[] input) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < input.length; i++) {
			//System.out.print(input[i]);
			result.append(input[i]);
		}
		return result.toString();
	}

	/*
	 * CMS (and MDP) does not handle non-ascii characters consistently.
	 * The following functions should help compare strings that aren't *Exactly* the same.
	 */
	public static boolean equalsAscii(String string1, String string2) {
		if (string1 == null) {
			if (string2 == null) { return true; }
			else { return false; }	
		} else {
			return asciiOnly(string1).equals(asciiOnly(string2));
		} 
	}

	public static boolean equalsAsciiIgnoreCase(String string1, String string2) {
		if (string1 == null) {
			if (string2 == null) { return true; }
			else { return false; }	
		} else {
			return asciiOnly(string1).equalsIgnoreCase(asciiOnly(string2));
		} 
	}

	public static boolean equalsAsciiSpaceless(String string1, String string2) {
		if (string1 == null) {
			if (string2 == null) { return true; }
			else { return false; }	
		} else {
			return asciiOnly(removeSpaces(string1)).equals(asciiOnly(removeSpaces(string2)));
		} 
	}

	public static String removeNonAscii(String stringToClean) {
		System.out.println("\n stringToClean ==> " + Normalizer.normalize(stringToClean, Normalizer.Form.NFD));
		return Normalizer.normalize(stringToClean, Normalizer.Form.NFC);
		//return stringToClean.replaceAll("[^\\x00-\\x7F]", "");  //7F == 127 (DEL)
	}

	public static String removeSpaces(String stringToClean) {
		return stringToClean.replaceAll(" ", "");
	}
	
	static String asciiOnly(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        char chars[] = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
           // if ((32 <= chars[i] && chars[i] < 58) || (64 < chars[i] && chars[i] < 91) || (96 < chars[i] && chars[i] < 123)) {
        	if (32 <= chars[i] && chars[i] < 127) {
                stringBuffer.append(chars[i]);
            }
        }
        //System.out.println("\n stringToClean ==> " + stringBuffer.toString());
        return stringBuffer.toString();
    } 
	
	
	public static boolean equalsZero(BigDecimal x) {
		return (0 == x.compareTo(BigDecimal.ZERO));
	}
	public static boolean equals(BigDecimal x, BigDecimal y) {
		return (0 == x.compareTo(y));
	}
	public static boolean lessThan(BigDecimal x, BigDecimal y) {
		return (-1 == x.compareTo(y));
	}
	public static boolean lessThanOrEquals(BigDecimal x, BigDecimal y) {
		return (x.compareTo(y) <= 0);
	}
	public static boolean greaterThan(BigDecimal x, BigDecimal y) {
		return (1 == x.compareTo(y));
	}
	public static boolean greaterThanOrEquals(BigDecimal x, BigDecimal y) {
		return (x.compareTo(y) >= 0);
	}




	/**
	 * For reading the first line of ordinary text files
	 * @param pathWithFileName
	 * @return
	 * @throws FileNotFoundException 
	 */
	public static String readFileFirstLine(String pathWithFileName) {
		File file = new File(pathWithFileName);
		String line = ""; 
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file)); 
			line = bufferedReader.readLine();
		} catch (IOException ex) {
			log.error("Error.  Could not read file '"+ pathWithFileName + ". " + ex.getMessage());
			ex.printStackTrace();
		} 
		return line;
	}

	

		public static DecimalFormat decimalFormat2 =  new DecimalFormat("00");

		public String[] all50StatesDcUsa = {
				"Alabama","Alaska","Arizona","Arkansas","California","Colorado","Connecticut","Delaware",
				"District of Columbia","Florida","Georgia","Hawaii","Idaho","Illinois","Indiana","Iowa",
				"Kansas","Kentucky","Lousiana","Maine","Maryland","Massachusetts","Michigan","Minnesota",
				"Mississippi","Missouri","Montana","Nebraska","Nevada","New Hampshire","New Jersey", "New Mexico",
				"New York","North Carolina","North Dakota","Ohio","Oklahoma","Oregon","Pennsylvania","Rhode Island",
				"South Carolina","South Dakota","Tennessee","Texas","Utah","Vermont","Virginia",
				"Washington","West Virginia","Wisconsin","Wyoming"};

		public String[] greekLetters = {"Alpha", "Beta", "Gamma", "Delta", "Epsilon", "Zeta", "Eta", "Theta", "Iota", "Kappa", 
				"Lambda", "Mu", "Nu", "Xi", "Omicron", "Pi", "Rho", "Sigma", "Tau", "Upsilon", "Phi", "Chi", "Psi", "Omega"	};

		/**
		 * For Testing and Development only.
		 * Also useful for figuring out how to use them elsewhere.
		 * @param args
		 */
		public static void main2(String[] args) {
			System.out.println("\n Starting Utils Main.");
			//System.out.println("getOrdinal result = " + getOrdinal(4));
			//System.out.println("grep result = " + grep("Fail", new File("target/whatever"))); \< and \>
			// to screen out After from failed: "^.*\\bAfter\\b.*$"
			//				System.out.println("\ngrep result=\n" + 
			//						grep("^.*\\b(Failed|ERROR)\\b.*$", "^.*\\bscenarioFailed\\b.*$", 
			//								new File("target/whatever/Log-Repeater-91-091-Ninetieth-URARunnerTest-DolphinsLayTEST.txt"), false));			
			System.out.println("\n Starting Directory Grep\n");
			String result = Utils.grep("^.*\\b(Failed|ERROR)\\b.*$", "^.*\\bscenarioFailed\\b.*$", new File("src\\main\\java"), true);
			System.out.println("\n Final Grep Result\n" + result);

			String text = "This_is_the_end";
			System.out.println("\n text=" + underscoreTermToCamelCase(text));
			text = "What do you want?";
			System.out.println("\n text=" + underscoreTermToCamelCase(text));
			text = "This_ will_ _NOT_end well.";
			System.out.println("\n text=" + underscoreTermToCamelCase(text));
			text = "Many,they've always suspected.";
			System.out.println("\n text=" + underscoreTermToCamelCase(text));
			text = "input cms-login-submit";
			System.out.println("\n text=" + underscoreTermToCamelCase(text));

			text = "Unfortunately many always suspected the very very worst from him";
			System.out.println("\n text=" + firstNWords(text, 2));
			System.out.println("\n text=" + firstNWords(text, 10));
			System.out.println("\n firstNWordsNoRepeat=" + firstNWordsNoRepeat(text, 10));
			System.out.println("\n removeDuplicates=" + removeDuplicateSubstrings("The Info Is The first Info that Then disappears Information.", 3));
			System.out.println("\n removeDuplicates=" + removeDuplicateSubstrings("inputUseridCmsLoginUseridUserid", 3));
			System.out.println("\n removeDuplicates=" + removeDuplicateSubstrings("inputNdc1CmsLoginNdc1Ndc1", 3));
			for (int i=0; i<10; i++) {
				System.out.println(createTestDrugManufacturerContact("12345", "Joe's Plumbing Supplies"));
			}
			NameGenerator romanNameGenerator = new NameGenerator("roman", true);
			NameGenerator elvenNameGenerator = new NameGenerator("eleven", false);
			NameGenerator goblinNameGenerator = new NameGenerator("goblin", false);
			NameGenerator fantasyNameGenerator = new NameGenerator("fantasy", false);
			for (int i=0; i<10; i++) {
				System.out.println("\nRoman:   " + romanNameGenerator.compose(3));  // The rules are not rich enough to generate very different Roman names
				System.out.println("Elven:   " + elvenNameGenerator.compose(3));
				System.out.println("Goblin:  " + goblinNameGenerator.compose(3));
				System.out.println("Fantasy: " + fantasyNameGenerator.compose(3));
				System.out.println("Human: " + getFullHumanName(true));  //false for non-random
				System.out.println("Project: " + getUniqueNameFromAbbreviation("MDP", true));
			}

			//		File[] files = {
			//			new File("Spectrum_9990.txt"), 
			//			new File("Spectrum_1000.txt"),
			//			new File("Spectrum_9991.txt"), 
			//			new File("Spectrum_1.txt"),
			//			new File("Spectrum_14.txt"),
			//			new File("Spectrum_2.txt"),
			//			new File("Spectrum_7.txt"),     
			//			new File("Spectrum_999.txt")};
			//	log.info("Before Sorting: ");
			//	for (File f : files) {
			//		log.info(f.getName());
			//	}
			//	Arrays.sort(files);
			//	log.info("\nAfter Sorting: ");
			//	for (File f : files) {
			//		log.info(f.getName());
			//	}
			//	log.info("\nAfter Number Sorting: ");
			//	files = Utils.sortByNumber(files);
			//	for (File f : files) {
			//		log.info(f.getName());
			//	}

			//		System.out.println("\n Startin BN name: " + Utils.getUniqueNameFromAbbreviation("BN", false));
			//		for (int i=1; i<100; i++) {
			//			String acronym = getAbbreviationFromNumber(i);
			//			System.out.println("getAbbreviationFromNumber=" + acronym + " = " + Utils.getUniqueNameFromAbbreviation(acronym, false));
			//		}
			//		
			//		String longString = "1. SDUD State Utilization-MDRP Features R3-SDUD-PROD Disabled\n" + 
			//				"2. SDUD SDUD - State Users Profile R3-SDUD-PROD Disabled\n" + 
			//				"3. RA RA - Landing Page R1-RA-PROD Disabled\n" + 
			//				"4. SDUD SDUD - CMS users Profile Page R3-SDUD-PROD Disabled\n";
			//		System.out.println("grep result = " + grep("^.*\\. RA", longString));
			// System.out.println("getOrdinal result = " + getOrdinal(4));
		}

		/**
		 * Start your process with the statement:
		 * long startTime = System.currentTimeMillis();
		 * and then at the end, call: 
		 * measureElapsedTime(startTime);
		 * 
		 * @param startTime
		 * @return
		 */
		public static String measureElapsedTime(long startTime) {
			long endTime = System.currentTimeMillis();
			long elapsed = endTime - startTime;
			long secondsInMilli = 1000;
			long minutesInMilli = secondsInMilli * 60;
			long hoursInMilli = minutesInMilli * 60;
			long daysInMilli = hoursInMilli * 24;
			long elapsedDays = elapsed / daysInMilli;
			elapsed = elapsed % daysInMilli;
			long elapsedHours = elapsed / hoursInMilli;
			elapsed = elapsed % hoursInMilli;
			long elapsedMinutes = elapsed / minutesInMilli;
			elapsed = elapsed % minutesInMilli;
			double elapsedSeconds = (double)elapsed / (double)secondsInMilli;
			//elapsed = elapsed - (elapsedDays*daysInMilli) - (elapsedHours*hoursInMilli) - (elapsedMinutes*minutesInMilli) - (elapsedSeconds*secondsInMilli);
			String elapsedString = elapsedDays + " days " + elapsedHours + " hours, " + 
					elapsedMinutes + " minutes, and " + elapsedSeconds + " seconds.";
			System.out.println("Finished in " + elapsedString);
			return elapsedString;
		}


		public static String getOrdinal(String index) {
			int i = Integer.parseInt(index.trim());
			return getOrdinal(i);
		}

		public static String getOrdinal(int i) {
			RuleBasedNumberFormat numberFormat = new RuleBasedNumberFormat(Locale.UK, RuleBasedNumberFormat.SPELLOUT);
			return numberFormat.format(i, "%spellout-ordinal");
		}

		public static void writeToFile(String fileName, String text) {
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
				bw.write(text);
				bw.close();
				//log.info("Finished writing to file " + fileName);
			} catch (IOException e) {
				System.err.println("Error trying to write to file " + fileName + " Reason: " + e.getMessage());
				e.printStackTrace();
			}
		}

		public static void appendToFile(String fileName, String text) {
			// In order to append text to a file, you need to open file into append mode, 
			//you do it by using FileReader and passing append = true 
			FileWriter fw = null; 
			BufferedWriter bw = null; 
			PrintWriter pw = null; 
			//System.out.println("appendToFile: fileName=" + fileName + " text=" + text);
			try { 
				fw = new FileWriter(fileName, true); 
				bw = new BufferedWriter(fw); 
				pw = new PrintWriter(bw); 
				pw.println(text); 
				//log.info("Data Successfully appended into file " + fileName); 
				pw.flush(); 
			} catch (IOException ioex) {
				log.info("Error. Something went wrong while trying to append to file " + fileName + " appended text=" + Utils.limit(text)); 
			}
			finally { 
				try { 
					pw.close(); 
					bw.close(); 
					fw.close(); 
				} catch (IOException io) {
					log.info("Failed to close file " + fileName); 
				}
			}
		}


		public static String readIfExists(String pathWithFileName) { 
			File file = new File(pathWithFileName);
			if (file.exists()) {
				log.info("readIfExists: Found file " + pathWithFileName);
				return readFile(pathWithFileName);
			} else {
				log.info("readIfExists: File " + pathWithFileName);
				return "";
			}
		}


		/**
		 * 
		 * @param resourceName
		 * @return
		 */
		public static String readResource(String resourceName) { 
			URL url = null;
			File file = null;
			String path = "";
			//log.info("readResource: resourceName=" + resourceName);
			Utils utils = new Utils();
			ClassLoader classLoader = utils.getClass().getClassLoader();
			//log.info("readResource: classLoader="+classLoader);
			//log.info("readResource: classLoader.getResource(resourceName)="+classLoader.getResource(resourceName));
			if (null == classLoader.getResource(resourceName)) {
				file = new File(resourceName);
				if (file.exists()) {
					try {
						String contents = readFile(resourceName);
						if (contents != null && !contents.equals("")) {
							//log.info("readResource: Read " + contents.length() + " characters from resource " + resourceName);
							return contents;
						}
						url = file.toURI().toURL();
						//log.info("readResource: let's try url=" + url);
					} catch (MalformedURLException e) {
						log.error("readResource: ClassLoader resource is null, and file (" + resourceName + ") cannot be read or found.");
						e.printStackTrace();
					}
				}
			} else {
				path = classLoader.getResource(resourceName).toExternalForm();
			}
			//log.info("readResource: pathbefore="+path);
			// Inside a war file, path is set to: 
			// path=vfs:/C:/workarea/JBossEAP/jboss-eap-6.4/bin/content/MDP-drools-api.war/WEB-INF/classes/rules/Product.drl
			//14:08:38,738 ERROR [stderr] (http-/127.0.0.1:8080-1) java.io.FileNotFoundException: 
			// C:\workarea\JBossEAP\jboss-eap-6.4\bin\content           \MDP-drools-api.war\WEB-INF\classes\rules\Product.drl (The system cannot find the path specified)
			// C:\workarea\JBossEAP\jboss-eap-6.4\standalone\deployments\MDP-drools-api.war\WEB-INF\classes\rules\Product.drl  (actual location)
			// otherwise (in the IDE), it looks like:
			// file:/C:/workarea/workspace/MDP-drools-api/target/classes/rules/Product.drl (note the the Eclipse IDE generally does not display classes)
			if (path.startsWith("vfs:\\")) {
				path = path.replace("\\bin\\content\\", "\\standalone\\deployments\\");  // only kicks in when executed in jar.
				path = path.replace("C:\\workarea\\JBossEAP\\jboss-eap-6.4\\bin\\vfs:\\", "vfs:\\"); // only kicks in when executed in jar.
				log.info("readResource: pathafter="+path);	
				file = new File(path);
				try {
					url = file.toURI().toURL();
				} catch (MalformedURLException e) {
					System.err.println("readResource: Error trying to reformat pathafter="+path);
					e.printStackTrace();
				}
				log.info("readResource: jar url="+url);
			} else if (url != null && !url.toString().startsWith("file:")){
				File thisFile = new File((new Utils()).getClass().getClassLoader().getResource(resourceName).getFile());
				//log.info("readResource: thisFile=" + thisFile + " exists? " + thisFile.exists());
				url = classLoader.getResource(resourceName);
				log.info("readResource: classLoader url=" + thisFile + " exists? " + thisFile.exists());
			}
			try {
				log.info("readResource: Trying to read url=" + url);
				if (url == null) {
					log.error("Could not find resourceName." + resourceName);
					return "readResource failed: Could not find resource " + resourceName;
				}
				InputStream stream = url.openStream();
				final int bufferSize = 1024;
				final char[] buffer = new char[bufferSize];
				final StringBuilder out = new StringBuilder();
				Reader in = new InputStreamReader(stream, "UTF-8");
				for (; ; ) {
					int rsz = in.read(buffer, 0, buffer.length);
					if (rsz < 0)
						break;
					out.append(buffer, 0, rsz);
				}
				String contents = out.toString();
				//log.info("readResource: Read contents (" + contents.length() + " characters) from resource " + url);
				return contents;
			} catch (MalformedURLException malUrlEx) {
				System.err.println("Broke malUrlEx in Utils.readResource resourceName="+ resourceName);
				malUrlEx.printStackTrace();
			} catch (UnsupportedEncodingException unSupEnEx) {
				System.err.println("Broke unSupEnEx in Utils.readResource resourceName="+ resourceName);
				unSupEnEx.printStackTrace();
			} catch (IOException ioex) {
				System.err.println("Broke ioex in Utils.readResource resourceName="+ resourceName);
				ioex.printStackTrace();
			}
			return "readResource failed: Could not find resource " + resourceName;
		}



		// This is here for development purposes only
		public static void renameReport() {
			File file = new File("target/rerun.txt");
			System.out.println("renameReport file exists: " + file.exists());
			String absolutePath = file.getAbsolutePath();
			System.out.println("absolutePath: " + absolutePath);
			String prePath = absolutePath.replaceAll("target.rerun.txt", "");
			System.out.println("prePath: " + prePath);
			String contents = readResource("target/rerun.txt").trim();  // This works in windows. Should work in Linux
			// For test and development: contents = "Login.feature:4 SummaryUtilization.feature:4 StateUtilization.feature:4 StateUtilizationHighLowReview.feature:4 UserManagement.feature:4:27:48 TouchTabs.feature:4";
			// prepend each item with the correct full path.  E.g. C:\Users\ttoth-fejel\Git\MDP_TestAutomation\CucumberMDP\src\main\java\gov\hhs\cms\cukes\features\ 
			//	String username = System.getProperty("user.name");
			//	String suPath = "C:\\Users\\USERNAME\\Git\\MDP-Repos_MDP-TestData\\src\\main\\resources\\stateutilization\\".replace("USERNAME", username);
			//	String path = "C:\\Users\\USERNAME\\Git\\MDP_TestAutomation\\CucumberMDP\\src\\main\\java\\gov\\hhs\\cms\\cukes\\features\\".replace("USERNAME", username);
			//	String relativePath = "src\\main\\java\\gov\\hhs\\cms\\cukes\\features\\".replace("USERNAME", username);
			System.out.println("renameReport resource contents=<<" + contents + ">>");
			String fileSeparator = System.getProperty("file.separator");
			String featureMiddlePath = "src" + fileSeparator + "main" + fileSeparator + "java" + fileSeparator +"gov" + fileSeparator + 
					"hhs" + fileSeparator+"cms" +fileSeparator+"cukes" +fileSeparator+"features" + fileSeparator;
			String featureRegex = ".*src.main.java.gov.hhs.cms.cukes.features.*";
			if (contents.matches(featureRegex)) {
				System.out.println("renameReport already modified; skipping auto-edit");
				return;
			}
			String result = "";
			String fullPath = " " + prePath + featureMiddlePath;
			for (String filename : contents.split(" ")) {
				result = result + fullPath  + filename;
			}
			System.out.println("result=" + result);
			writeToFile(file.getAbsolutePath(), result.trim() + "\n");
		}

		public static String underscoreTermToCamelCase(String text) {
			return underscoreTermToCamelCase(text, false); 
		}

		public static String underscoreTermToCamelCase(String text, boolean leaveRestAlone) {
			//System.out.println("text='" + text + "'.");
			text = text.trim();
			StringBuilder result = new StringBuilder(text.length());
			text = text.replace("_"," ").replace("."," ").replace(","," ").replace("-"," "); //.replace("\"", "").replace("'", "").replace(":", "");  // other punctuation taken care of at end
			//System.out.println("text2=" + text);
			boolean firstIteration = true;
			for (String word : text.split(" ")) {
				if (firstIteration) {
					result.append(word.toLowerCase());
					if (leaveRestAlone) {	
						result.append(word.substring(0,1) + word.substring(1));
					} else {
						result.append(word.substring(0,1).toLowerCase() + word.substring(1));
					}
					firstIteration = false;
				} else {
					if (!word.isEmpty()) {
						result.append(Character.toUpperCase(word.charAt(0)));
						if (leaveRestAlone) {	
							result.append(word.substring(1));
						} else {
							result.append(word.substring(1).toLowerCase());
						}
					}
				}
			}//TODO: Make sure that first character is not number
			return result.toString().replaceAll("[^a-zA-Z0-9 ]", "").trim();
		}


		public static String first(String[] stringArray) {
			if (stringArray == null) { return null; } 
			if (stringArray.length <= 1) { return stringArray[0]; } 
			return stringArray[0];
		}


		public static String punctuationSpliterRegex = "[~\\+\\-\\\\'\\\" \\.\\,\\(\\)\\[\\]{}\\t\\!@#\\$%\\^&\\*]";
		/**
		 * From Lisp:  All but first.
		 * @param parsableString
		 * @return
		 */
		public static String rest(String parsableString) {
			if (parsableString == null) { return ""; }
			if (parsableString.equals("")) { return ""; }
			String[] stringArray = parsableString.split(punctuationSpliterRegex);
			//System.out.println("stringArray: " + Arrays.toString(stringArray));
			String[] restArray = rest(stringArray);
			return Arrays.toString(restArray).replace("[", "").replace("]", "").replace(",", "").trim().replaceAll(" +", " ");
		}
		/**
		 * From Lisp:  All but first.
		 * @param stringArray
		 * @return
		 */
		static public String[] rest(String[] stringArray) {
			if (stringArray == null) { return null; } 
			if (stringArray.length <= 1) { return null; } 
			return Arrays.copyOfRange(stringArray, 1, stringArray.length);
		}
		
		static public ArrayList<String> rest(ArrayList<String> arrayList) {
			if (arrayList == null) { return null; } 
			if (arrayList.size() <= 1) { return new ArrayList<String>(); } 
			ArrayList<String> result = (ArrayList<String>)arrayList.clone();
			result.remove(0);
			return result;
		}
		
		static public ArrayList<String> clean(ArrayList<String> arrayList) {
			if (arrayList == null) { return null; } 
			ArrayList<String> result = new ArrayList<String>();
			if (arrayList.size() < 1) { return new ArrayList<String>(); } 
			for (String item : arrayList) {
				result.add(item.replace("\n", " ").replace("\r", "").replace("  ", " ").trim());
			}
			return result;
		}
		
		static public String clean(String text) {
			if (StringUtils.isEmpty(text)) { return ""; } 
			return text.replace("\n", " ").replace("\r", "").replace("  ", " ").trim();
		}
		

		public static HashMap<String, String> createTestDrugManufacturerContact(String ndc1, String labelerName) {
			HashMap<String, String> labeler = new HashMap<String, String>();
			labelerName = labelerName.trim().replace(",", ""); // Replace all commas so that we don't run into problems later.
			if (ndc1.length()<5) { ndc1 = "0" + ndc1; }        // Fix ndc1 if it needs to be fixed.
			String optionalEffectiveDate = "01/01/2000 12:00:00 AM";
			String terminationDate = "";
			String legalContactName = getFullHumanName(false); 
			String legalCorporation = labelerName + " " + Utils.getWord(legalContactName.substring(0,1).toLowerCase(), "noun", false) + " Division";
			System.out.println("createTestDrugManufacturerContact for " + ndc1 + " " + labelerName + " legalContactName: " + legalContactName + " legalCorporation: " + legalCorporation);
			String legalAddress1 = (legalCorporation.length() * 34) + " " + Utils.getWord(legalContactName.substring(0,1).toLowerCase(), "verb", false) + " Street";
			String legalAddress2 = "Suite " + (legalAddress1.length() * 5 + 3);
			String legalAddress3 = "";
			String[] legalCityStateZip = getCityStateZip(false);
			System.out.println("createTestDrugManufacturerContact: legalCityStateZip=" + Arrays.toString(legalCityStateZip));

			String legalCity = legalCityStateZip[0].trim();
			String legalState = legalCityStateZip[1].trim();
			String legalZip = legalCityStateZip[2].trim();
			String legalPhone = quasiRandomZip(legalState, legalZip);
			String legalEmail = "mdp.legal." + squishOutPunctuation(labelerName) + "@DCCA.com";

			String invoiceContactName = getFullHumanName(false); 
			String invoiceCorporation = labelerName + " " + Utils.getWord(invoiceContactName.substring(0,1).toLowerCase(), "noun", false) + " Division";
			String invoiceAddress1 = (invoiceCorporation.length() * 34) + " " + Utils.getWord(invoiceContactName.substring(0,1).toLowerCase(), "verb", false) + " Street";
			String invoiceAddress2 = "# " + (invoiceAddress1.length() * 5 + 3);
			String invoiceAddress3 = "";
			String[] invoiceCityStateZip = getCityStateZip(false);
			String invoiceCity = invoiceCityStateZip[0].trim();
			String invoiceState = invoiceCityStateZip[1].trim();
			String invoiceZip = invoiceCityStateZip[2].trim();
			String invoicePhone = quasiRandomZip(invoiceState, invoiceZip);
			String invoiceEmail = "mdp.invoice." + squishOutPunctuation(labelerName) + "@DCCA.com";

			String technicalContactName = getFullHumanName(false); 
			String technicalCorporation = labelerName + " " + Utils.getWord(technicalContactName.substring(0,1).toLowerCase(), "noun", false) + " Division";
			String technicalAddress1 = (technicalCorporation.length() * 34) + " " + Utils.getWord(technicalContactName.substring(0,1).toLowerCase(), "verb", false) + " Street";
			String technicalAddress2 = "Office " + (technicalAddress1.length() * 5 + 3);
			String technicalAddress3 = "";
			String[] technicalCityStateZip = getCityStateZip(false);
			System.out.println("createTestDrugManufacturerContact: technicalCityStateZip=" + Arrays.toString(technicalCityStateZip));
			String technicalCity = technicalCityStateZip[0].trim();
			String technicalState = technicalCityStateZip[1].trim();
			String technicalZip = technicalCityStateZip[2].trim();
			String technicalPhone = quasiRandomZip(technicalState, technicalZip);
			String technicalEmail = "mdp.technical." + squishOutPunctuation(labelerName) + "@DCCA.com";
			// System.out.println(rowNumber + ". generateFromFdaCompositeFile: putting labelerCode=" + labelerCode + " '" + labelerName + "'");
			String activeIndicator = "1";
			String signatoryTitle = getTitle(false); 
			String signatoryFullName = getFullHumanName(false); 
			String signatoryEmail = "mdp.signatory." + squishOutPunctuation(signatoryFullName) + "@DCCA.com";
			String signatoryAddress1 = (signatoryFullName.length() * 34) + " " + Utils.getWord(signatoryFullName.substring(0,1).toLowerCase(), "verb", false) + " Street";
			String signatoryAddress2 = "Office " + (signatoryFullName.length() * 5 + 3);
			String[] signatoryCityStateZip = getCityStateZip(false);
			System.out.println("createTestDrugManufacturerContact: signatoryCityStateZip=" + Arrays.toString(signatoryCityStateZip));
			String signatoryCity = signatoryCityStateZip[0].trim();
			String signatoryState = signatoryCityStateZip[1].trim();
			String signatoryZip = signatoryCityStateZip[2].trim();

			//		String[] example = {"00002", "ELI LILLY AND COMPANY", "01/01/1991 12:00:00 AM", "", "JOSH O'HARRA", 
			//				"ELI LILLY AND COMPANY", "555 12 TH STREET N.W.", "SUITE 600", "", "WASHINGTON", "DC", "20004", "2024341035", "O'HARRA_JOSH_T@LILLY.COM", "LISA NORTON", 
			//				"ELI LILLY AND COMPANY", "LILLY CORPORATE CENTER", "DROP CODE 5012", "", "INDIANAPOLIS", "IN", "46285", "3172771592", "LLYMEDICAID@LILLY.COM", "ERIKA LIGHBOURNE", 
			//				"ELI LILLY AND COMPANY", "LILLY CORPORATE CENTER", "", "", "INDIANAPOLIS", "IN", "46285", "3172778070", "LIGHTBOURNE_ERIKA@LILLY.COM", "1"};
			labeler.put("ndc1", ndc1);
			labeler.put("labelerName", labelerName);
			labeler.put("optionalEffectiveDate", optionalEffectiveDate);
			labeler.put("terminationDate", terminationDate);
			labeler.put("legalContactName", legalContactName);
			labeler.put("legalCorporation", legalCorporation);
			labeler.put("legalAddress1", legalAddress1);
			labeler.put("legalAddress2", legalAddress2);
			labeler.put("legalAddress3", legalAddress3);
			labeler.put("legalCity", legalCity);
			labeler.put("legalState", legalState);
			labeler.put("legalZip", legalZip);
			labeler.put("legalPhone", legalPhone);
			labeler.put("legalEmail", legalEmail);
			labeler.put("invoiceContactName", invoiceContactName);
			labeler.put("invoiceCorporation", invoiceCorporation);
			labeler.put("invoiceAddress1", invoiceAddress1);
			labeler.put("invoiceAddress2", invoiceAddress2);
			labeler.put("invoiceAddress3", invoiceAddress3);
			labeler.put("invoiceCity", invoiceCity);
			labeler.put("invoiceState", invoiceState);
			labeler.put("invoiceZip", invoiceZip);
			labeler.put("invoicePhone", invoicePhone);
			labeler.put("invoiceEmail", invoiceEmail);
			labeler.put("technicalContactName", technicalContactName);
			labeler.put("technicalCorporation", technicalCorporation);
			labeler.put("technicalAddress1", technicalAddress1);
			labeler.put("technicalAddress2", technicalAddress2);
			labeler.put("technicalAddress3", technicalAddress3);
			labeler.put("technicalCity", technicalCity);
			labeler.put("technicalState", technicalState);
			labeler.put("technicalZip", technicalZip);
			labeler.put("technicalPhone", technicalPhone);
			labeler.put("technicalEmail", technicalEmail);
			labeler.put("activeIndicator", activeIndicator);
			labeler.put("signatoryTitle", signatoryTitle);
			labeler.put("signatoryFullName", signatoryFullName);
			labeler.put("signatoryEmail", signatoryEmail);
			labeler.put("signatoryAddress1", signatoryAddress1);
			labeler.put("signatoryAddress2", signatoryAddress2);
			labeler.put("signatoryCity", signatoryCity);
			labeler.put("signatoryState", signatoryState);
			labeler.put("signatoryZip", signatoryZip);
			System.out.println("Created Test labeler" + labeler.toString());
			return labeler;
		}

		public static String[] titles = {
				"President", "Vice President", "Chairman of the Board", "Chief Council", "Chief Executive Officer",
				"Chief Operating Officer", "Legal Director", "Assistant Vice President", "Manager of Operations", "Operations Manager",
				"Cubicle Denzien", "Research Engineer", "Artist in Residence", "Speaker to Banks", "Assistant Bottle Washer" };

		public static int nonRandomTitleIndex = 0;

		public static String getTitle(boolean randomFlag) {
			int index = 0;
			if (randomFlag) {
				index = 1 + random.nextInt(15);  // there are 15 possible titles above
			} else  {
				index = nonRandomTitleIndex;
				nonRandomTitleIndex = (nonRandomTitleIndex + 1)%15;
			}
			String title = titles[index];
			System.out.println("getTitle: index= " + index + " title=" + title);
			return title;
		}

		public static LinkedHashMap <String,String> cityStatesMap = new LinkedHashMap <String,String>();

		public static LinkedHashMap <String,String> fillCityStates() {
			String citiesStates = Utils.readResource("src\\main\\resources\\words\\CitiesStates1000.csv");
			//System.out.println("citiesStates=" + citiesStates);
			cityStatesMap = new LinkedHashMap <String,String>();
			final CSVParser parser = new CSVParserBuilder().withSeparator(',').withQuoteChar('\"').withIgnoreQuotations(false).build();
			final CSVReader csvReader = new CSVReaderBuilder(new StringReader(citiesStates)).withSkipLines(0).withCSVParser(parser).build();
			int lineNumber = 0;
			try {
				lineNumber = lineNumber + 1;
				String[] rowCityState = csvReader.readNext(); // Read the first row (header)
				//String labelerHeader = Arrays.toString(rowCityState).replace("[", "").replace("]", "");
				//System.out.println("\n" + lineNumber + ". getCityStateZip header? row=" + Arrays.toString(rowCityState));
				while ((rowCityState = csvReader.readNext()) != null) {
					lineNumber = lineNumber + 1;
					//System.out.println(lineNumber + ". Extracting data from " + Arrays.toString(rowCityState));
					if (lineNumber == 1) {
						//System.out.println("Skipping header");
						continue;
					}
					String city = rowCityState[0];
					String stateAbbreviation = getStateAbbreviation(rowCityState[1]);
					//System.out.println(lineNumber + ". City=" + city + " stateName=" + rowCityState[1] + " " +stateAbbreviation);
					cityStatesMap.put(city, stateAbbreviation);
				}
			} catch (Exception ex){
				ex.printStackTrace();
			} finally {
				try {
					csvReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			nonRandomCityStateIndex = 1;
			return cityStatesMap; 
		}
		
	public static Map<String, String> stateAbreviationMap = new HashMap<String, String>();
		
		public static Map<String, String> fillStateAbbreviation() {
			Map<String, String> stateAbreviationMap = new HashMap<String, String>();
			for (String[] pair : stateAbreviations) {
				stateAbreviationMap.put(pair[0].trim(), pair[1].trim());
			}
			return stateAbreviationMap;
		}
		
		public static String getStateAbbreviation(String stateName) {
			if (stateAbreviationMap.size() == 0) { stateAbreviationMap = fillStateAbbreviation(); }
			return stateAbreviationMap.get(stateName.trim());
		}

		
		
		public static String getIngredient(boolean randomFlag) {
			String ingredient = "";
			ingredient = getBiochemical(randomFlag) + " " + getChemical(randomFlag);
			return ingredient;
		}
		
		public static int bioIndex = 0;
		public static String[] biochemicalsList = {};
		public static String getBiochemical(boolean randomFlag) {
			if (biochemicalsList.length == 0) { fillBiochemicals(); }
			if (randomFlag) {
				return StringUtils.substring(biochemicalsList[new Random().nextInt(biochemicalsList.length)].trim(), 0, 63);
			} else {
				String thisBiochemical = biochemicalsList[bioIndex];
				bioIndex = bioIndex + 1;
				if (bioIndex >= biochemicalsList.length) { bioIndex = bioIndex%biochemicalsList.length; }
				return StringUtils.substring(thisBiochemical.trim(), 0, 63);
			}	
		}
		
		public static String[] fillBiochemicals() {
			String biochemicalsFilename = "\\words\\Biochemicals.txt";
			String biochemicals = Utils.readResource(biochemicalsFilename);
			biochemicalsList = biochemicals.split("\n");  // Fill global variable.
			return biochemicalsList;
		}
		
		public static int chemIndex = 0;
		public static String[] chemicalsList = {};
		public static String getChemical(boolean randomFlag) {
			if (chemicalsList.length == 0) { fillChemicals(); }
			if (randomFlag) {
				return StringUtils.substring(chemicalsList[new Random().nextInt(chemicalsList.length)].trim(), 0, 63);
			} else {
				String thisChemical = chemicalsList[chemIndex];
				chemIndex = chemIndex + 1;
				if (chemIndex >= chemicalsList.length) { chemIndex = chemIndex%chemicalsList.length; }
				return StringUtils.substring(thisChemical.trim(), 0, 63);
			}	
		}
		
		public static String[] fillChemicals() {
			String chemicalsFilename = "\\words\\Chemicals.txt";
			String chemicals = Utils.readResource(chemicalsFilename);
			chemicalsList = chemicals.split("\n");  // Fill global variable.
			return chemicalsList;
		}

		
		// This is a linked hashmap because that way it gets ordered in the same order as we put it in (so we get the same result each time).
			public static LinkedHashMap <String,String> cityZipsMap = new LinkedHashMap <String,String>();
			
			public static LinkedHashMap <String,String> fillCityZip() {
				cityZipsMap = new LinkedHashMap <String,String>();
				String cityZips = Utils.readResource("src\\main\\resources\\words\\CityZip.csv");
				//System.out.println("fillCityZip: cityZips= " + cityZips);
				final CSVParser parser = new CSVParserBuilder().withSeparator(',').withQuoteChar('\"').withIgnoreQuotations(false).build();
				final CSVReader csvReader = new CSVReaderBuilder(new StringReader(cityZips)).withSkipLines(0).withCSVParser(parser).build();
				String[] rowCityzip = null;
				try {
					rowCityzip = csvReader.readNext();
					System.out.println("fillCityZip: skipping header " + Arrays.toString(rowCityzip));
					while ((rowCityzip = csvReader.readNext()) != null) {
						//System.out.println("Mapping data from " + Arrays.toString(rowCityzip));
						if (rowCityzip[0].length() > 0) { cityZipsMap.put(rowCityzip[0], rowCityzip[1]); }
					}
				} catch (CsvValidationException | IOException e) {
					e.printStackTrace();
				}
				return cityZipsMap;
			}
			
			public static String getZipFromCity(String desiredCity) {
				if (cityZipsMap.size() == 0) { fillCityZip(); }
				return cityZipsMap.get(desiredCity);
			}

		public static int nonRandomCityStateIndex = 0;

		public static String[] getCityStateZip(boolean randomFlag) {
			if (cityStatesMap.size() == 0) { fillCityStates(); }
			if (cityZipsMap.size() == 0) { fillCityZip(); }
			int index = 0;
			if (randomFlag) {
				index = 1 + random.nextInt(241);  // there are only about 244 cities in the cityZipsMap
			} else  {
				index = nonRandomCityStateIndex;
				nonRandomCityStateIndex = (nonRandomCityStateIndex + 1)%242;
			}
			//System.out.println("getCityStateZip: index= " + index);
			Map.Entry<String, String> entry = (Map.Entry<String, String>) cityZipsMap.entrySet().toArray()[index];
			String city = entry.getKey();
			String state = cityStatesMap.get(city);
			String zip = entry.getValue();
			String[] cityStateZip = {city, state, zip};
			return cityStateZip;
		}

		public static String squishOutPunctuation(String string) {
			return string.replaceAll("([\\t\\,\\.? \\[\\]{}()+-/\\\\])", ""); 
		}

		public static String toJavaVariable(String text) {
			return underscoreTermToCamelCase(text);
		}
		
		public static String toJavaVariable(String text, boolean leaveRestAlone) {
			return underscoreTermToCamelCase(text, leaveRestAlone);
		}

		public static String quasiRandomZip(String stateAbreviation, String zip) {
			String twoDigits = "72";
			int index = 0;
			for (String[] statepair : stateAbreviations) {
				index = index + 1;
				if (statepair[1].equals(stateAbreviation)) {
					twoDigits = decimalFormat2.format(index);
					break;
				}
			}
			return "800" + twoDigits + StringUtils.reverse(zip);
		}
		
		

		public static void zipFile(String filePath) {
			try {
				File fileToZip = new File(filePath);
				System.out.println("Zipping from " + filePath); 
				FileInputStream fileInputStream = new FileInputStream(fileToZip);
				String zipFileName = fileToZip.getParent() + "\\" + fileToZip.getName().replace(".", "-").concat(".zip");
				System.out.println("Zipping to " + zipFileName); 
				FileOutputStream fos = new FileOutputStream(zipFileName);
				ZipOutputStream zipOut = new ZipOutputStream(fos);
				ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
				zipOut.putNextEntry(zipEntry);
				byte[] bytes = new byte[4096];
				int length;
				int count = 0;
				while((length = fileInputStream.read(bytes)) >= 0) {
					zipOut.write(bytes, 0, length);
					if (count%100 == 0) { System.out.println(); }
					System.out.print("."); 
					count = count + 1;
				}
				fileInputStream.close();
				zipOut.closeEntry();
				zipOut.close();
				System.out.println();
			} catch (FileNotFoundException fnfex) {
				System.err.format("The file " + filePath + " does not exist. " + fnfex.getMessage());
				fnfex.printStackTrace();
			} catch (IOException ioex) {
				System.err.println("I/O error while trying to zip " + filePath + ". " + ioex.getMessage());
				ioex.printStackTrace();
			}
			System.out.println("Finished zipping " + filePath);
		}

		public static String formatNumber(String numberString, DecimalFormat decimalFormat) {
			double numberDouble = Double.parseDouble(numberString);
			return decimalFormat.format(numberDouble);
		}


		public static String cleanCamel(String phrase) { 
			String cleanPhrase = phrase.trim();
			cleanPhrase = cleanPhrase.replaceAll("[^a-zA-Z]", " ");
			cleanPhrase = WordUtils.capitalizeFully(cleanPhrase, new char[]{' '});
			cleanPhrase = cleanPhrase.replace(" ", "");
			return cleanPhrase;
		}


		public static String removeDuplicateSubstrings(String text, int minLength) {
			//System.out.println("removeDuplicates from text=" + text + " minLength=" + minLength);
			String copy = text;
			for (int i=(text.length()/2); i>=minLength; i--) {
				copy = removeDuplicate(copy, i);
			}
			//System.out.println("removeDuplicateSubstrings: final copy=" + copy);
			return copy;
		}

		public static String removeDuplicate(String text, int minLength) {
			//System.out.println("removeDuplicates from text=" + text + " minLength=" + minLength);
			String copy = text;
			final int smartLength = text.length() - minLength;
			for (int i=0; i<smartLength; i++) {
				if (copy.length() < i+minLength) { break; }
				String word = copy.substring(i, i+minLength);
				//System.out.println(i + ". copy.substring(0, i)='" + copy.substring(0, i) + " word='" + word + "' copy.replace(word, \"\")=" + copy.substring(i).replace(word, ""));
				//String newCopy = copy.substring(0, i) + word + copy.substring(i).replace(word, "");             // case sensitive;  final copy=The Info Is  firstthat n disappearsformation.
				String newCopy = copy.substring(0, i) + word + copy.substring(i).replaceAll("(?i)"+word, ""); // case Insensitive;    final copy=The Info Is  firstthat n disappearsformation.
				copy = newCopy; // target.replaceAll("(?i)foo", "");
				//System.out.println(i + ". copy=" + copy);
			}
			//System.out.println("removed duplicates of length " + minLength + " in copy=" + copy);
			return copy;
		}
		
		
		/**
		 * Only raises first letter to upper case; rest is left alone.
		 * @param string
		 * @return
		 */
		public static String upCaseFirst(String string) {
			if (StringUtils.isEmpty(string)) {
				return "";
			} else {
			return string.substring(0, 1).toUpperCase() + string.substring(1);
			}
		}
		
		
		/**
		 * First letter of each word is capitalized; rest is lower case.
		 * @param string
		 * @return
		 */
		public static String titleCase(String string) {
			StringBuffer result = new StringBuffer();
			if (StringUtils.isEmpty(string)) {
				return "";
			} else {
				String[] textArray = string.split("\\W");
				for (String word : textArray) {
					result.append(string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase());
				}
				return result.toString();
			}
		}


		/**
		 * 
		 * @param text
		 * @param n - the number of words you want
		 * @return
		 */
		public static String firstNWords(String text, int n) {
			String result = "";
			if (text == null) { 
				return ""; 
			} else {
				String[] texts = text.split(" ", n+1);
				//System.out.println("length = " + texts.length);
				for (int i=0; i<n; i++) {
					if (texts.length >= i+1) { 
						//System.out.println(i + ". texts[i]= " + texts[i]);
						result = result + " " + texts[i]; 
					} else {
						return result.trim();
					}
				}
			}
			return result.trim();
		}

		public static String firstNWordsNoRepeat(String text, int n) {
			String result = "";
			if (text == null) { 
				return ""; 
			} else {
				String[] texts = text.split(" ", n+1);
				//System.out.println("length = " + texts.length);
				for (int i=0; i<n; i++) {
					if (texts.length >= i+1) { 
						//System.out.println(i + ". texts[i]= " + texts[i]);
						if (i==0) { 
							result = result + " " + texts[i]; 
						} else {
							if (!texts[i].equals(texts[i-1])) {
								result = result + " " + texts[i]; 
							}
						}
					} else {
						return result.trim();
					}
				}
			}
			return result.trim();
		}


		public static void fixRubyFunctionNames(String pathWithFileName) {
			//File file = new File(pathWithFileName);
			String edited = "";
			//String after = "Annotation annotation = CukeUtils.initWhen(scenario, log);";
			String contents = readFile(pathWithFileName);
			String[] javaCode = contents.split("\n");
			for (String lineCode : javaCode) {
				System.out.println("code: " + lineCode);
				if (lineCode.trim().startsWith("public void ") && lineCode.endsWith("throws Throwable {")) {
					System.out.println("Found code: " + lineCode);
					String rubyStyleName = lineCode.replace("public void","").replace(" throws Throwable {","").trim();
					if (rubyStyleName.contains("_")) {
						System.out.println("rubyStyleName: " + rubyStyleName);
						String javaStyleName = underscoreTermToCamelCase(rubyStyleName);
						System.out.println("javaStyleName: " + javaStyleName);
						String newLine = lineCode.replace(rubyStyleName, javaStyleName);
						System.out.println("Replacing code: " + newLine);
						edited = edited + "\n" + newLine;
					} else {
						System.out.println("Copying code: " + lineCode);
						edited = edited + "\n" + lineCode;
					}
				} else if (lineCode.contains("throw new PendingException();")){
					System.out.println("skipping code: " + lineCode);
				} else {
					System.out.println("Copying code: " + lineCode);
					edited = edited + "\n" + lineCode;
				}
			}
			System.out.println("Final Edit: " + edited);
			writeToFile(pathWithFileName, edited);
		}


		/**
		 * To select lines, start with ^.* and end with .*$
		 * To select whole words, use the \b word boundary
		 * To do case-insensitive searches, prepend your regex with (?si) 
		 * Eg. to find 'failed' and 'Failed' but not 'unfailed', use "(?si)^.*\\bfailed\\b.*$"
		 * @param regex
		 * @param fileOrDirectory
		 * @return
		 */
		public static String grep(String regex, File fileOrDirectory) {
			return grep(regex, null, fileOrDirectory, false);
		}

		public static String grep(String regex, File fileOrDirectory, boolean prependWithFileName) {
			return grep(regex, null, fileOrDirectory, prependWithFileName);
		}

		public static String grep(String regex, String filteredRegex, File fileOrDirectory, boolean prependWithFileName) {
			String collected = "";
			log.info("grep: Starting fileOrDirectory=" + fileOrDirectory.getAbsolutePath());
			try {
				String baseDirectory = fileOrDirectory.getCanonicalPath();
				//log.info("grep: baseDirectory=" + baseDirectory);
				if (fileOrDirectory.exists()) {
					if (fileOrDirectory.isDirectory()) {
						log.info("grep: Listing directory " + fileOrDirectory.getAbsolutePath());
						String[] pathnames = fileOrDirectory.list();
						for (String pathname : pathnames) {
							log.info("Searching: " + baseDirectory + "\\" + pathname);
							String thisResult = grep(regex, filteredRegex, Utils.readFile(baseDirectory + "\\" + pathname));
							if (!thisResult.equals("")) {
								collected = collected + "\n" + (prependWithFileName ? pathname + "\n" : "") + thisResult + "\n";
							}
						}
					} else if (fileOrDirectory.isFile() && fileOrDirectory.canRead()) {
						log.info("grep: Searching file " + fileOrDirectory.getAbsolutePath() + " for regex='" + regex + "'.");
						return grep(regex, filteredRegex, Utils.readFile(fileOrDirectory));
					} else {
						log.error("Unrecognized File " + fileOrDirectory + " unknown Error.");
					}
				} else {
					log.error("Error File " + fileOrDirectory + " does not exist or can't be read.");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return collected;
		}


		/**
		 * 
		 * @param regex
		 * @param longTextToBeSearched
		 * @return
		 */
		public static String grep(String regex, String longTextToBeSearched) {
			return grep(regex, null, longTextToBeSearched);
		}

		public static String grep(String regex, String filterOutRegex, String longTextToBeSearched) {
			String result = "";
			String filteredResult = "";
			Pattern pattern = null;        // Compiled Regular Expression pattern
			Pattern patternFilter = null;
			//log.info("grepping for '" + regex + "' but not '" + filterOutRegex + "' in a string containing " + longTextToBeSearched.length() + " characters.");
			try {
				pattern = Pattern.compile(regex);
			} catch (PatternSyntaxException psex) {
				System.err.println("grep: Invalid regular expression syntax for '" + regex + "'. " + psex.getDescription() + " " + psex.getMessage());
				return null;
			}
			BufferedReader reader = new BufferedReader(new StringReader(longTextToBeSearched));
			String line = "";
			try {
				while ((line = reader.readLine()) != null) {
					Matcher matcher = pattern.matcher(line);
					if (matcher.find()) {
						//System.out.println(line);
						result = result + "\n" + line;
					}
				}
			} catch (Exception ex) {
				System.err.println("grep: Error reading line='" + line + "'." + ex.getMessage());
				return null;
			}
			//log.info("\nunfiltered result:\n" + result);
			if (!StringUtils.isEmpty(filterOutRegex)) {
				try {
					patternFilter = Pattern.compile(filterOutRegex);
				} catch (PatternSyntaxException psex) {
					System.err.println("grep: Invalid regular expression syntax for filterOutRegex '" + filterOutRegex + "'. " + psex.getDescription() + " " + psex.getMessage());
					return null;
				}
				BufferedReader readerResult = new BufferedReader(new StringReader(result.trim()));
				try {
					while ((line = readerResult.readLine()) != null) {
						Matcher matcher = patternFilter.matcher(line);
						if (matcher.find()) {
							//System.out.println("filtering out " + line);
						} else {
							//System.out.println("keeping " + line);
							filteredResult = filteredResult + "\n" + line;
						}
					}
				} catch (Exception ex) {
					System.err.println("grep: Error reading line='" + line + "'." + ex.getMessage());
					return null;
				}
				result = filteredResult;
			}
			return result.trim();
		}




		public static String runDosCommand(String command, String directoryPath) {
			System.out.println("runDosCommand: " + command + " at " + directoryPath);
			String result = "";
			try {
				String[] envp = null;
				File directory = new File(directoryPath);
				Process process = Runtime.getRuntime().exec(command, envp, directory);
				BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
				BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
				String standardOutput;
				log.info("Standard output: ");
				while ((standardOutput = stdInput.readLine()) != null) {
					log.info(standardOutput);
					result = result + standardOutput + "\n";
				}
				if (stdError.ready()) { System.err.println("Standard error: "); }
				while ((standardOutput = stdError.readLine()) != null) {
					log.info(standardOutput);
					result = result + standardOutput + "\n";
				}
			} catch (Exception ex) {
				System.err.println("Failed. TagRepeater.runCommand command failed (" + command + "). "+ ex.getMessage());
				ex.printStackTrace(System.err);
			}
			return result;
		}

		/**
		 * For writing ordinary string text to ordinary files.
		 * @param fileName
		 * @param text
		 */
		public static void writeFile(String fileName, String text) {
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
				bw.write(text);
				log.info("Finished writing to file " + fileName + " text: " + text.length() + " characters.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}


		/**
		 * For reading ordinary text files
		 * @param pathWithFileName
		 * @return
		 * @throws FileNotFoundException 
		 */
		public static String readFile(String pathWithFileName) {
			return readFile(pathWithFileName, true);
		}

		public static String readFile(String pathWithFileName, boolean quietFlag) {
			if (pathWithFileName == null) {
				log.error("Error. Could not read null pathWithFileName.");
				return "";
			}
			File file = new File(pathWithFileName);
			return readFile(file, quietFlag);
		}

		public static String readFile(File file) {
			return readFile(file, true);
		}

		public static String readFile(File file, boolean quietFlag) {
			if (file == null) {
				log.error("Error. Could not read null file.");
				return "";
			}
			String line;
			StringBuilder stringBuilder = new StringBuilder(""); 
			try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
				while ((line = bufferedReader.readLine()) != null) {
					if (!quietFlag) { log.info(line); }
					stringBuilder.append(line).append("\n");
				}
			} catch (IOException ex) {
				log.error("Error.  Could not read file '"+ file.getName() + ". " + ex.getMessage());
				ex.printStackTrace();
			} 
			return stringBuilder.toString();
		}

		public static String getFullHumanName(boolean randomFlag) {
			return getHumanName("first", randomFlag) + " " + getHumanName("last", randomFlag);
		}

		public static  ArrayList<String> firstNamesListArray = new ArrayList<String>();

		public static ArrayList<String> fillFirstNames() {
			String firstNames = Utils.readResource("src\\main\\resources\\words\\FirstNames.txt");
			String[] firstNamesArray = firstNames.split("\n"); 
			firstNamesListArray = (ArrayList<String>)Arrays.stream(firstNamesArray).map(String::trim).collect(Collectors.toList());
			return firstNamesListArray;  
		}

		public static ArrayList<String> lastNamesListArray = new ArrayList<String>();

		public static ArrayList<String> fillLastNames() {
			String lastNames = Utils.readResource("src\\main\\resources\\words\\LastNames.txt");
			String[] lastNamesArray = lastNames.split("\n");  
			lastNamesListArray = (ArrayList<String>)Arrays.stream(lastNamesArray).map(String::trim).collect(Collectors.toList());
			return lastNamesListArray;  
		}

		public static String getHumanName(String type, boolean randomFlag) {
			String word = "unknown";
			String words = ""; 
			ArrayList<String> wordList = new ArrayList<String>();
			if (type.equalsIgnoreCase("first")) {
				if (firstNamesListArray.size() == 0) { fillFirstNames(); }
				wordList = firstNamesListArray;
			} else if (type.equalsIgnoreCase("last")) {
				if (lastNamesListArray.size() == 0) { fillLastNames(); }
				wordList = lastNamesListArray;
			} else {
				if (firstNamesListArray.size() == 0) { fillFirstNames(); }
				wordList = firstNamesListArray;
			}
			if (wordList.size() <= 0) {
				System.out.println("getHumanName trying to find a name, but the list of " + type + " name possibilities is empty.");
				System.out.println("Error: wordList=" + Arrays.toString(wordList.toArray()));
			}
			if (randomFlag) {
				int randomPick = random.nextInt(wordList.size());
				word = wordList.get(randomPick);
			} else {
				nonRandom = nonRandom + 1;
				int wordArraySize = wordList.size();
				word = wordList.get(nonRandom % wordArraySize);
			}
			return word;
		}


		/**
		 * Turns 1 into AA, 2 int AB, etc. all the way to ZZZZZ...
		 * @param number
		 * @return
		 */
		public static String getAbbreviationFromNumber(int number) {
			String result = "";
			int quotient, remainder;
			quotient = number + 25;  // Because we want to start with AA, not A.  ZZZZZ is the limit!
			while (quotient >= 0) {
				remainder = quotient % 26;
				result = (char)(remainder + 65) + result;
				quotient = (int)Math.floor(quotient/26) - 1;
			}
			//System.out.print(result);
			return result;
		}


		/**
		 * Returns an expanded "Acronym Expansion" for a string.
		 * E.g. "AL" ==> "AstrovirusesLie
		 * @param abbreviation
		 * @param randomFlag
		 * @return
		 */
		public static String getUniqueNameFromAbbreviation(String abbreviation, boolean randomFlag) {
			if (abbreviation.isEmpty()) { return "NumbNull"; }
			String uniqueName = "";
			String letter1 = abbreviation.toLowerCase().substring(0,1);
			if (abbreviation.length() == 1) { 
				uniqueName = getWord(letter1, "noun", randomFlag);
			} else if (abbreviation.length() == 2) {  
				String letter2 = abbreviation.toLowerCase().substring(1,2);
				uniqueName = getWord(letter1, "noun", randomFlag) + getWord(letter2, "verb", randomFlag);
			} else if (abbreviation.length() == 3) {  
				String letter2 = abbreviation.toLowerCase().substring(1,2);
				String letter3 = abbreviation.toLowerCase().substring(2,3);
				uniqueName = getWord(letter1, "noun", randomFlag) + getWord(letter2, "verb", randomFlag) + 
						getWord(letter3, "adverb", randomFlag);
			} else if (abbreviation.length() == 4) {  
				String letter2 = abbreviation.toLowerCase().substring(1,2);
				String letter3 = abbreviation.toLowerCase().substring(2,3);
				String letter4 = abbreviation.toLowerCase().substring(3,4);
				uniqueName = getWord(letter1, "adjective", randomFlag) + getWord(letter2, "noun", randomFlag) + 
						getWord(letter3, "verb", randomFlag) + getWord(letter4, "adverb", randomFlag);
			} else { 
				uniqueName = "";
				for (int i=0; i<abbreviation.length(); i++) {
					String letter = abbreviation.toLowerCase().substring(i, i+1);
					if (i%4==0) {
						uniqueName = uniqueName + getWord(letter, "adjective", randomFlag);
					} else if (i%4==1) {
						uniqueName = uniqueName + getWord(letter, "noun", randomFlag);
					} else if (i%4==2) {
						uniqueName = uniqueName + getWord(letter, "verb", randomFlag);
					} else {
						uniqueName = uniqueName + getWord(letter, "adverb", randomFlag);
					} 
				}
			}
			//System.out.println("uniqueName=" + uniqueName);
			return uniqueName;
		}


		/**
		 * Returns a word of type noun, verb, adverb, or adjective.
		 * 
		 * @param letter
		 * @param type
		 * @param randomFlag
		 * @return
		 */
		public static String getWord(String letter, String type, boolean randomFlag) {
			String word = "unknown";
			String words = ""; 
			String nounss = ""; 
			String verbs = ""; 
			String adverbs = ""; 
			String adjectives = ""; 
			String[] wordArray = {};
			if (type.equalsIgnoreCase("noun")) {
				if (nounArray.length == 0) { fillNouns(); }
				wordArray = nounArray;
			} else if (type.equalsIgnoreCase("verb")) {
				if (verbArray.length == 0) { fillVerbs(); }
				wordArray = verbArray;
			} else if (type.equalsIgnoreCase("adverb")) {
				if (adverbArray.length == 0) { fillAdverbs(); }
				wordArray = adverbArray;
			} else if (type.equalsIgnoreCase("adjective")) {
				if (adjectiveArray.length == 0) { fillAdjectives(); }
				wordArray = adjectiveArray;
			} else {
				if (nounArray.length == 0) { fillNouns(); }
				wordArray = nounArray;
			}
			ArrayList<String> wordList = getRestrictedWordList(letter, wordArray);
			if (wordList.size() <= 0) {
				System.out.println("getWord failed to find words for " + letter + " in type " + type);
				System.out.println("Error: wordList=" + Arrays.toString(wordList.toArray()));
			}
			if (randomFlag) {
				int randomPick = random.nextInt(wordList.size());
				word = wordList.get(randomPick);
			} else {
				nonRandom = nonRandom + 1;
				int wordArraySize = wordList.size();
				word = wordList.get(nonRandom % wordArraySize);
			}
			return word.substring(0, 1).toUpperCase() + word.substring(1) ;
		}

		static public ArrayList<String> getRestrictedWordList(String letter, String[] wordArray) {
			ArrayList<String> wordList = new ArrayList<String>();
			if (letter.matches("[A-Za-z]")) {
				for (String thisNoun : wordArray) {
					if (thisNoun.startsWith(letter)) {
						wordList.add(thisNoun.trim());
					}
				}
			} else {
				//System.out.println("Warning. getRestrictedWordList was not given a letter, but instead: '" + letter + "', ASCII: '" + displayAsciiCode(letter, true) + "'.");
				for (String thisWord : wordArray) {
					if (!thisWord.trim().equals("")) { wordList.add(thisWord.trim()); }
				}
			}
			//System.out.println("\n getRestrictedWordList: Words that start with letter '" + letter + "'=" + Arrays.toString(wordList.toArray()));
			return wordList;
		}

		public static Random random = new Random();
		public static int nonRandom = 0;
		public static String[]  nounArray = {};
		public static String[]  verbArray = {};
		public static String[]  adverbArray = {};
		public static String[]  adjectiveArray = {};
		//public static String[][]  cityZipArray = {};
		//public static String[]  cityStateZipArray = {};

		public static String[] fillNouns() {
			String nouns = Utils.readResource("src/main/resources/words/Nouns.txt");
			nounArray = nouns.split("\n");  
			//System.out.println("nouns=" + Arrays.toString(nounArray));
			return nounArray;
		}

		public static String[] fillVerbs() {
			String verbs = Utils.readResource("src/main/resources/words/Verbs.txt");
			verbArray = verbs.split("\n");  
			return verbArray;  
		}

		public static String[] fillAdverbs() {
			String adverbs = Utils.readResource("src/main/resources/words/Adverbs.txt");
			adverbArray = adverbs.split("\n");  
			return adverbArray;  
		}

		public static String[] fillAdjectives() {
			String adjectives = Utils.readResource("src/main/resources/words/Adjectives.txt");
			adjectiveArray = adjectives.split("\n");  // Fill global variable.
			return adjectiveArray;
		}

		public static final SimpleDateFormat simpleMMddyyyyFormat = new SimpleDateFormat("MM/dd/yyyy");

		static public boolean isValidMMddyyyyDate(String dateString) {
			//log.info("\nstringToDate parsing "+dateString);
			String message = "";
			if (dateString == null || dateString.equals("")) {
				message = "Failed: empty stringToDate: Null or blank date ("+dateString+"). Expecting date format to be MM/DD/YYYY ";
				log.error(message);
				return false;
			} else if (dateString.matches("^(0[1-9]|1[012])/(0[1-9]|[12][0-9]|3[01])/(1|2)[0-9][0-9][0-9]$")) {
				message = "Success: ("+dateString+"). Expecting date format to be MM/DD/YYYY ";
				log.info(message);
				return true;
			} else {
				message = "Failed: Unknown date format ("+dateString+"). Expecting date format to be MM/DD/YYYY.";
				log.error(message);
				return false;
			}
		}

		
		public static File[] sortByNumber(File[] files) {
			Arrays.sort(files, new Comparator<File>() {
				//@Override
				public int compare(File o1, File o2) {
					int n1 = extractNumber(o1.getName());
					int n2 = extractNumber(o2.getName());
					return n1 - n2;
				}
				private int extractNumber(String name) {
					int i = 0;
					try {
						int start = name.indexOf('_')+1;
						int end = name.lastIndexOf('.');
						String number = name.substring(start, end);
						i = Integer.parseInt(number);
					} catch(Exception e) {
						i = 0; // if filename does not match the format then default to 0
					}
					return i;
				}
			});
			return files;
		}


		public static String getCallerClassName() {
			StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
			for (int i=0; i<stackTrace.length; i++) {
				StackTraceElement ste = stackTrace[i];
				//System.out.println("Ste=" + ste.getClassName());
				if (!ste.getClassName().equals(Utils.class.getName()) && ste.getClassName().indexOf("java.lang.Thread")!=0) {
					return ste.getClassName();
				}
			}
			return null;
		}


		public static boolean assertArraysRegexEquals(String[] expectedRowValues, String[] actualRowValues, String context, Logger log) {
			final int expectedSize = expectedRowValues.length;
			final int actualSize = actualRowValues.length;
			String errorMessage = "";
			if (expectedSize != actualSize) {
				errorMessage = "Failed: Regex comparison - Actual row (" + Arrays.toString(actualRowValues) + ") is different size (" + actualSize + 
						") than expected size (" + expectedSize + "); expected row is (" + Arrays.toString(expectedRowValues) + ").";
				Assert.fail(errorMessage);
				return false;
			} else {
				for (int i=0; i<expectedSize; i++) {
					String expected = expectedRowValues[i];
					String actual = actualRowValues[i];
					if (!actual.matches(expected)) {
						errorMessage = "Failed: Regex comparison - Actual item # " + i + " (" + actual + ") is different than expected (" + expected + ").";
						Assert.fail(errorMessage);
						return false;
					} 
				}
			}
			log.info("Success: Regex comparison - Actual row (" + Arrays.toString(actualRowValues) + ") matches the expected row (" + Arrays.toString(expectedRowValues) + ").");
			return true;
		}


		/**
		 * Every item in stringArray must exist in (the presumably long) textToSearch.
		 * @param stringArray
		 * @param textToSearch
		 * @return
		 */
		public static boolean findAll(String[] stringArray, String textToSearch) {
			for (String smallString : stringArray) 
				if (!textToSearch.contains(smallString)) { 
					log.error("Could not find smallString='" + smallString + "' in '" + Arrays.toString(stringArray) + "'.");
					return false; 
				} 
			// else { System.out.println("Found smallString=" + smallString); }
			return true;
		}


		/**
		 * Copies the contents of an ArrayList into a new String[].
		 * @param arrayList
		 * @return
		 */
		public static String[] arrayListStringToStringArray(ArrayList<String> arrayList)   { 
			String stringArray[] = new String[arrayList.size()]; 
			final int arrayListSize = arrayList.size();
			for (int j=0; j<arrayListSize; j++) { 
				stringArray[j] = arrayList.get(j); 
			} 
			return stringArray; 
		}


		/**
		 * If each of the actualRowValues matches each of the corresponding expectedRowValues, then wonderful.
		 * Otherwise Assert Fail with error message.
		 * @param expectedRowValues
		 * @param actualRowValues
		 * @param context
		 * @param log
		 * @return
		 */
		public static boolean assertArrayEquals(String[] expectedRowValues, String[] actualRowValues, String context, Logger log) {
			final int expectedSize = expectedRowValues.length;
			final int actualSize = actualRowValues.length;
			if (expectedSize != actualSize) {
				String errorMessage = "Failed: Equality comparison - Actual row (" + Arrays.toString(actualRowValues) + ") is different size (" + actualSize + 
						") than expected size (" + expectedSize + "); expected row is (" + Arrays.toString(expectedRowValues) + ").";
				Assert.fail(errorMessage);
				return false;
			} else {
				for (int i=0; i<expectedSize; i++) {
					String expected = expectedRowValues[i];
					String actual = actualRowValues[i];
					if (!actual.equals(expected)) {
						String errorMessage = "Failed: comparison - Actual item # " + i + " (" + actual + ") is different than expected (" + expected + ").";
						Assert.fail(errorMessage);
						return false;
					} 
				}
			}
			log.info("Success: comparison - Actual row (" + Arrays.toString(actualRowValues) + ") matches the expected row (" + Arrays.toString(expectedRowValues) + ").");
			return true;
		}


		/**
		 * Returns a copy of an array but only up to the length's item.
		 * @param array
		 * @param length
		 * @return
		 */
		public static String[] limitArray(String[] array, int length) {
			return Arrays.copyOfRange(array, 0, length);
		}


		/**
		 * Sometimes equals is just too exact. Especially when there may be rounding errors.
		 * @param a
		 * @param b
		 * @param epsilon
		 * @return
		 */
		public static boolean almostEqual(BigDecimal a, BigDecimal b, double epsilon) {
			BigDecimal zero = new BigDecimal(0.0);
			BigDecimal absA = a.abs();
			BigDecimal absB = b.abs();
			BigDecimal epsilonBig = new BigDecimal(epsilon);
			BigDecimal min = new BigDecimal(Double.MIN_NORMAL);
			BigDecimal diff = (a.subtract(b));
			if (a.equals(b)) {
				return true;
			}
			else if (a.equals(zero) || b.equals(zero) || diff.abs().compareTo(min) < 0) {
				// If a or b is zero or both are extremely close to it
				// then relative error is less meaningful here.
				// NOT SURE HOW RELATIVE EPSILON WORKS IN THIS CASE
				return diff.compareTo(epsilonBig.multiply(min)) < 0;
			} else {
				// Use relative error
				//        	System.out.println("diff = " + diff);
				//        	System.out.println("epsilon = " + epsilon);
				//        	System.out.println("diff / Math.min((absA + absB), Double.MAX_VALUE) = " + diff / Math.min((absA + absB), Double.MAX_VALUE));
				//return (diff / Math.min((absA + absB), Double.MAX_VALUE) < epsilon);  // I don't understand this, so I'm writing my own.
				BigDecimal aMinusE = absA.subtract(epsilonBig);
				BigDecimal aPlusE = absA.add(epsilonBig);
				BigDecimal bMinusE = absB.subtract(epsilonBig);
				BigDecimal bPlusE = absB.add(epsilonBig);
				return (Utils.between(aMinusE, absB, aPlusE) || 
						Utils.between(bMinusE, absA, bPlusE));
			}
		}


		public static boolean almostEqual(double a, double b, double epsilon) {
			final double absA = Math.abs(a);
			final double absB = Math.abs(b);
			final double diff = Math.abs(a - b);
			if (a == b) {
				return true;
			}
			else if ( a == 0 || b == 0 || diff < Double.MIN_NORMAL) {
				// a or b is zero or both are extremely close to it
				// relative error is less meaningful here
				// NOT SURE HOW RELATIVE EPSILON WORKS IN THIS CASE
				return diff < (epsilon * Double.MIN_NORMAL);
			}
			else {
				// use relative error
				//        	System.out.println("diff = " + diff);
				//        	System.out.println("epsilon = " + epsilon);
				//        	System.out.println("diff / Math.min((absA + absB), Double.MAX_VALUE) = " + diff / Math.min((absA + absB), Double.MAX_VALUE));
				//return (diff / Math.min((absA + absB), Double.MAX_VALUE) < epsilon);
				return (absA - epsilon < absB && absB < absA + epsilon) || (absB - epsilon < absA && absA < absB + epsilon);
			}
		}


		public static boolean equalsIgnoreExtraWhitespace(String a, String b) {
			return (a.trim().replaceAll("\\s+", " ").equalsIgnoreCase(b.trim().replaceAll("\\s+", " ")));
		}


		/**
		 * @author hkabir
		 * @param dayToSubtract
		 * @return
		 */
		public static String getPreviousDate(int dayToSubtract) {
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Instant now = Instant.now(); //current date
			Instant previousDate = now.minus(Duration.ofDays(dayToSubtract));
			Date dateFormatting = Date.from(previousDate);
			String modifiedDate = dateFormat.format(dateFormatting);
			log.info("The Previous Date with format MM/DD/YYYY is: "+ modifiedDate);
			return modifiedDate;
		}


		/**
		 * @author hkabir
		 */
		public static String getCurrentSystemDate() {
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Date date = new Date();
			String dateFormatted= dateFormat.format(date);
			log.info("The Current System date with format MM/DD/YYYY is: "+dateFormatted);
			return dateFormatted;
		}


		/**
		 * @author hkabir
		 * @param daysToAdd
		 * @return
		 */
		public static String getFutureDate(int daysToAdd) {
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Instant now = Instant.now(); //current date
			Instant futureDate = now.plus(Duration.ofDays(daysToAdd));
			Date dateFormatting = Date.from(futureDate);
			String modifiedDate = dateFormat.format(dateFormatting);
			log.info("The Future date with format MM/DD/YYYY is: "+ modifiedDate);
			return modifiedDate;
		}


		/**
		 * Ignoring the decimal value from the input
		 * @author hkabir
		 * 
		 * @param inputValue
		 * @param decimalPoint
		 * @return
		 */
		public static String ignoreDecimalPoint(String inputValue, int decimalPoint) {
			String output = "";
			if (inputValue.contains(".")) {
				output = String.valueOf(inputValue).split("\\.")[decimalPoint];
				return output;
			} else {
				return output = inputValue;
			}
		}


		/**
		 * To compare actual vs expected graphics.
		 * For example:
		 		String title = "ActualCheckMarkTerms";
				WebElement agreeCheckbox = CukeUtils.findBy(By.xpath("//label[@id='cms-label-tc']"), annotation, log);
				File elementScreenshot = agreeCheckbox.getScreenshotAs(OutputType.FILE);
				File actualCheckMarkFile = new File("./target/" + title + ".png");
				FileUtils.copyFile(elementScreenshot, actualCheckMarkFile);
				Messenger.addInfo("Clicked Terms & Conditions checkbox once", log);
				if (CukeUtils.isEqual(new File("./src/main/resources/AgreeToTermsChecked.png"), actualCheckMarkFile)) {
					Messenger.addInfo("Checkbox is marked", log);
				} else if (CukeUtils.isEqual(new File("./src/main/resources/AgreeToTermsUnchecked.png"), actualCheckMarkFile)) {
					agreeCheckbox.click();
				} else {
					Messenger.addError("The Terms & Conditions checkbox is neither checked nor unchecked. Like Shrodinger's Cat, it is indeterminate. " +
								CukeUtils.prettyAnnotation(annotation), log);
				}
		 * 
		 * @author ttoth-fejel
		 * @param firstFile
		 * @param secondFile
		 * @return
		 */
		public static boolean isEqual(File firstFile, File secondFile) {
			try {
				log.info("isEqual firstFile=" + firstFile.getName() + " secondFile=" + secondFile);
				boolean result = FileUtils.contentEquals(firstFile, secondFile);
				log.info("isEqual result=" + result);
				return result;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}


		/**
		 * For BigDecimals, true if someNumber is between less and more.
		 * BTW, a.compareTo(b) returns a number greater than zero if a > b, 0 if a == b, and less than zero if a < b
		 * @param less
		 * @param someNumber
		 * @param more
		 * @return
		 */
		public static boolean between(BigDecimal less, BigDecimal someNumber, BigDecimal more) {
			//System.out.println("less=" + less + " someNumber=" + someNumber + " more=" + more);
			//System.out.println("less.compareTo(someNumber)=" + less.compareTo(someNumber) + " more.compareTo(someNumber)=" + more.compareTo(someNumber));
			return less.compareTo(someNumber) < 0 && more.compareTo(someNumber) > 0;
		}


		/**
		 * C:\Users\ttoth-fejel\Documents\MACTUS>DIR *.VBS
			 Volume in drive C has no label.
			 Volume Serial Number is 60E1-7E0E

			 Directory of C:\Users\ttoth-fejel\Documents\MACTUS

			10/07/2020  01:37 PM               591 KillEverything.vbs
			               1 File(s)            591 bytes
			               0 Dir(s)  59,812,941,824 bytes free

			C:\Users\ttoth-fejel\Documents\MACTUS>cscript KillEverything.vbs
			Microsoft (R) Windows Script Host Version 5.812
			Copyright (C) Microsoft Corporation. All rights reserved.

			C:\Users\ttoth-fejel\Documents\MACTUS>

		 	Call KillProcess("UFT.exe")
			Call KillProcess("QtpAutomationAgent.exe")
			Call KillProcess("iexplore.exe")
			'Call KillProcess("chrome.exe")
			'Call KillProcess("firefox.exe")

			Sub KillProcess(ByVal ProcessName)
			    On Error Resume Next
			    Dim objWMIService
			    Set objWMIService = GetObject("winmgmts:{impersonationLevel=impersonate}!\\.\root\cimv2")
			    Dim colProcesses
			    Set colProcesses = objWMIService.ExecQuery("SELECT * FROM Win32_Process WHERE Name='" & ProcessName &"'")
			    ConsoleOutput("Terminating Process : " & ProcessName)
			    For Each objProcess in colProcesses
			        intTermProc = objProcess.Terminate
			    Next
			    On Error GoTo 0
			End Sub

		 * C:\Users\ttoth-fejel>taskkill /?
			TASKKILL [/S system [/U username [/P [password]]]]
			         { [/FI filter] [/PID processid | /IM imagename] } [/T] [/F]

			Description:
			    This tool is used to terminate tasks by process id (PID) or image name.

			Parameter List:
			    /S    system           Specifies the remote system to connect to.
			    /U    [domain\]user    Specifies the user context under which the
			                           command should execute.
			    /P    [password]       Specifies the password for the given user
			                           context. Prompts for input if omitted.
			    /FI   filter           Applies a filter to select a set of tasks.
			                           Allows "*" to be used. ex. imagename eq acme*
			    /PID  processid        Specifies the PID of the process to be terminated.
			                           Use TaskList to get the PID.
			    /IM   imagename        Specifies the image name of the process
			                           to be terminated. Wildcard '*' can be used
			                           to specify all tasks or image names.
			    /T                     Terminates the specified process and any
			                           child processes which were started by it.
			    /F                     Specifies to forcefully terminate the process(es).
			    /?                     Displays this help message.

			Filters:
			    Filter Name   Valid Operators           Valid Value(s)
			    -----------   ---------------           -------------------------
			    STATUS        eq, ne                    RUNNING |
			                                            NOT RESPONDING | UNKNOWN
			    IMAGENAME     eq, ne                    Image name
			    PID           eq, ne, gt, lt, ge, le    PID value
			    SESSION       eq, ne, gt, lt, ge, le    Session number.
			    CPUTIME       eq, ne, gt, lt, ge, le    CPU time in the format
			                                            of hh:mm:ss.
			                                            hh - hours,
			                                            mm - minutes, ss - seconds
			    MEMUSAGE      eq, ne, gt, lt, ge, le    Memory usage in KB
			    USERNAME      eq, ne                    User name in [domain\]user format
			    MODULES       eq, ne                    DLL name
			    SERVICES      eq, ne                    Service name
			    WINDOWTITLE   eq, ne                    Window title

			    NOTE
			    1) Wildcard '*' for /IM switch is accepted only when a filter is applied.
			    2) Termination of remote processes will always be done forcefully (/F).
			    3) "WINDOWTITLE" and "STATUS" filters are not considered when a remote
			       machine is specified.
			Examples:
			    TASKKILL /IM notepad.exe
			    TASKKILL /PID 1230 /PID 1241 /PID 1253 /T
			    TASKKILL /F /IM cmd.exe /T
			    TASKKILL /F /FI "PID ge 1000" /FI "WINDOWTITLE ne untitle*"
			    TASKKILL /F /FI "USERNAME eq NT AUTHORITY\SYSTEM" /IM notepad.exe
			    TASKKILL /S system /U domain\\username /FI "USERNAME ne NT*" /IM *
			    TASKKILL /S system /U username /P password /FI "IMAGENAME eq note*"

			C:\Users\ttoth-fejel>
		 *  processID
		 */
		public static void killWindowsTask(int processID) {
			// This could get pretty dangerous.
			runDosCommand("taskkill /?", "ALLUSERSPROFILE");
		}


		public static String limit(String possiblyLongMultiLineString) {
			if (possiblyLongMultiLineString == null) { return "null"; }
			String ellipses = "";
			if (possiblyLongMultiLineString.length() > 100) { ellipses = "..."; }
			return possiblyLongMultiLineString.substring(0, Math.min(100, possiblyLongMultiLineString.length())).replace("\n", " \\n ").replace("\r", "") + ellipses;
		}

		public static String limit(String possiblyLongString, int limit) {
			if (possiblyLongString == null) { return "null"; }
			return possiblyLongString.substring(0, Math.min(limit, possiblyLongString.length())).replace("\n", " ");
		}


		public static String limitWithoutEllipse(String possiblyLongMultiLineString) {
			if (possiblyLongMultiLineString == null) { return "null"; }
			return possiblyLongMultiLineString.substring(0, Math.min(100, possiblyLongMultiLineString.length())).replace("\n", " \\n ").replace("\r", "");
		}


		/**
		 * A generous definition of strings. I.e. two empty string (null or "") are equal.
		 * If only one is empty, then they aren't equal, but they don't crash, just warn.
		 * Other strings are compared with case insensitive equals.
		 * Actually, this is what StringUtils.equalsIgnoreCase does, except it doesn't warn you.
		 * @param string1
		 * @param string2
		 */
		public static boolean stringEquals(String string1, String string2) {
			if (StringUtils.isEmpty(string1) && StringUtils.isEmpty(string2)) {
				log.warn("Two empty strings are equal.");
				return true;
			}
			if (string1==null || string2==null) {
				log.warn("A string is null.");
				return false;
			}
			return StringUtils.equalsIgnoreCase(string1, string2);
		}


		/*
		 * A null-safe version of the regular expression matcher
		 */
		public static boolean matchesNullSafe(String string1, String string2) {
			if (StringUtils.isEmpty(string1) && StringUtils.isEmpty(string2)) {
				// log.warn("Two empty strings are equal.");
				return true;
			}
			if (string1==null || string2==null) {
				log.warn("A string is null.");
				return false;
			}
			return string1.matches(string2);
		}


		public static String arrayListStringArrayToString(ArrayList<ArrayList<String>>  table) {
			StringBuilder result = new StringBuilder(128);
			for (ArrayList<String> row : table) {
				if (row != null && row.size()>1 && !"".equals(String.join("", row))) {
					//System.out.println("row=" + Arrays.toString(row));
					String csvRow = String.join(", ", row);
					//System.out.println(csvRow);
					result.append(csvRow + "\n");
				} else {
					System.out.println("Skipping emptyish row=" + row);
				}
			}
			return result.toString().trim();
		}


		/**
		 * TODO: Shouldn't this be random Labeler ID?
		 * @author hkabir
		 */
		public static int randomNumber(){
			int number = 10000 + new Random().nextInt(90000);
			System.out.println(number);
			return number;
		}


		/**
		 * This is because Excel "helps" you turn number-looking strings into numbers.
		 * @author hkabir
		 *
		 * @param input
		 * @return
		 */
		public static String padZerosForLengthFive(String input) {
			String output;
			int len=input.length();
			if (len==2){
				output="000"+ input;
			} else if (len==3){
				output="00"+ input;
			} else if (len==4){
				output="0"+ input;
			} else if (len==1) {
				output="0000"+ input;
			} else{
				output=input;
			}
			return output;
		}


		/**
		 * To handle Excel's "help" for Package Sizes.
		 * 
		 * @param input
		 * @return
		 */
		public static String padZeroToMakeLengthofTwo(String input) {
			String output;
			int len=input.length();
			if (len==1){
				output="0"+ input;
			}else{
				output=input;
			}
			return output;
		}





}
