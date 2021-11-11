package discovery;

/**
Java: Random Name Generator
Started by Sinipull, Aug 05 2009 04:43 AM 
java generator 

Description: This class generates random names from syllables, and provides programmer a simple way to set a group of rules for generator to avoid unpronounceable and bizarre names. 

SYLLABLE FILE REQUIREMENTS/FORMAT: 
1) all syllables are separated by line break. 
2) Syllable should not contain or start with whitespace, as this character is ignored and only first part of the syllable is read. 
3) + and - characters are used to set rules, and using them in other way, may result in unpredictable results.
4) Empty lines are ignored.

SYLLABLE CLASSIFICATION:
Name is usually composed from 3 different class of syllables, which include prefix, middle part and suffix. 
To declare syllable a prefix in the file, insert "-" as a first character of the line. 
To declare syllable a suffix in the file, insert "+" as a first character of the line. 
everything else is read as middle part.

NUMBER OF SYLLABLES: 
Names may have any positive number of syllables. In case of 2 syllables, name will be composed from prefix and suffix. 
In case of 1 syllable, name will be chosen from amongst the prefixes.
In case of 3 and more syllables, name will begin with prefix, is filled with middle parts and ended with suffix.

ASSIGNING RULES:
I included a way to set 4 kind of rules for every syllable. To add rules to the syllables, write them right after the syllable and SEPARATE WITH WHITESPACE. (example: "aad +v -c"). The order of rules is not important.

RULES:
1) +v means that next syllable must definitely start with a vowel.
2) +c means that next syllable must definitely start with a consonant.
3) -v means that this syllable can only added to another syllable, that ends with a vowel.
4) -c means that this syllable can only added to another syllable, that ends with a consonant.
So, our example: "aad +v -c" means that "aad" can only be after consonant and next syllable must start with vowel.
Beware of creating logical mistakes, like providing only syllables ending with consonants, but expecting only vowels, which will be detected and RuntimeException will be thrown.

TO RUN:
Create a new NameGenerator object, provide the syllable file, and create names using compose() method.

EXAMPLE SYLLABLE FILE (Roman Names):
-a
-al
-au +c
-an
-ba
-be
-bi
-br +v
-da
-di
-do
-du
-e
-eu +c
-fa
bi
be
bo
bu
nul +v
gu
da
au +c -c
fri
gus
+tus
+lus
+lius
+nus
+es
+ius -c
+cus
+tor
+cio
+tin

and results:

Aubicio
Bebecus
Batin
Baes
Alboes
Dubilius
Banus
Alcus
Beguslius
Dinules
Dubecio
Beguses
Belus
Bibinus
Dibilius
Dacus
Antin
Braulius
Dobotor
Eucio
Dutus

EXAMPLE SYLLABLE FILE (Elven Names):
-Ael
-Aer
-af
-ah
-am
-ama
-an
-ang +v
-ansr +v
-cael
-dae +c
-dho
-eir
-fi
-fir
-la
-seh
-sel
-ev
-fis
-hu
-ha
-gar
-gil
-ka
-kan
-ya
-za
-zy
-mara
-mai +c
-lue +c
-ny
-she
-sum
-syl
ae +c -c
ael -c
dar
deth +v
dre -v
drim -v
dul
ean -c
el
emar
hal
iat -c
mah
ten
que -v +c
ria
rail
ther
thus
thi
san
+ael -c
+dar
+deth
+dre
+drim
+dul
+ean -c
+el
+emar
+nes
+nin
+oth
+hal
+iat
+mah
+ten
+ther
+thus
+thi
+ran
+ath
+ess
+san
+yth
+las
+lian
+evar
and results:
Amaelran
Kaemaroth
Shedul
Kathi
Aftenlas
Ansraellas
Angevar
Aerten
Caeltennes
Ladreevar
Aerlas
Evel
Maiquelian
Evraildar
Ahlas
Zyran
Ahsanten
Shethernes
Sylsan
Sheran
Hutenmah
Fimahlian
Nyoth
Kanael
Luedullian
Fiemardeth
Syldul
Seldar
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class is released under GNU general public license
 * 
 * Description: This class generates random names from syllables, and provides programmer a 
 * simple way to set a group of rules for generator to avoid unpronounceable and bizarre names. 
 * 
 * SYLLABLE FILE REQUIREMENTS/FORMAT: 
 * 1) all syllables are separated by line break. 
 * 2) Syllable should not contain or start with whitespace, as this character is ignored and only first part of the syllable is read. 
 * 3) + and - characters are used to set rules, and using them in other way, may result in unpredictable results.
 * 4) Empty lines are ignored.
 * 
 * SYLLABLE CLASSIFICATION:
 * Name is usually composed from 3 different class of syllables, which include prefix, middle part and suffix. 
 * To declare syllable as a prefix in the file, insert "-" as a first character of the line.  
 * To declare syllable as a suffix in the file, insert "+" as a first character of the line.  
 * everything else is read as a middle part.
 * 
 * NUMBER OF SYLLABLES: 
 * Names may have any positive number of syllables. In case of 2 syllables, name will be composed from prefix and suffix. 
 * In case of 1 syllable, name will be chosen from amongst the prefixes.
 * In case of 3 and more syllables, name will begin with prefix, is filled with middle parts and ended with suffix.
 * 
 * ASSIGNING RULES:
 * You can set four kinds of rules for every syllable. To add rules to the syllables, write them right after the 
 * syllable and SEPARATE WITH WHITESPACE. (example: "aad +v -c"). The order of rules is not important.
 * 
 * RULES:
 * 1) +v means that next syllable must definitely start with a Vowel.
 * 2) +c means that next syllable must definitely start with a consonant.
 * 3) -v means that this syllable can only be added to another syllable, that ends with a Vowel.
 * 4) -c means that this syllable can only be added to another syllable, that ends with a Consonant.
 * So, our example: "aad +v -c" means that "aad" can only be after consonant and next syllable must start with Vowel.
 * Beware of creating logical mistakes, like providing only syllables ending with consonants, but expecting only Vowels, which will be detected 
 * and RuntimeException will be thrown.
 * 
 * TO RUN:
 * Create a new NameGenerator object, provide the syllable file, and create names using compose() method. See the main.
 * 
 * http://forum.codecall.net/topic/49665-java-random-name-generator/
 */
public class NameGenerator {

	static String username = System.getProperty("user.name");
	static String directory = "C:\\Users\\USERNAME\\Git\\MDP-Repos_MDP-TestData\\src\\main\\resources\\words\\".replace("USERNAME", username);
	static String romanFilePath = directory + "RulesRomanWords.txt";
	static String elevenFilePath = directory + "RulesElvenWords.txt";
	static String goblinFilePath = directory + "RulesGoblinWords.txt";
	static String fantasyFilePath = directory + "RulesFantasyWords.txt";
	boolean randomFlag = true;
	int counter = 0;

	public static void main(String[] args) {
		NameGenerator romanNameGenerator = new NameGenerator("roman", true);
		NameGenerator elvenNameGenerator = new NameGenerator("eleven", false);
		NameGenerator goblinNameGenerator = new NameGenerator("goblin", false);
		NameGenerator fantasyNameGenerator = new NameGenerator("fantasy", false);
		for (int i=0; i<1000; i++) {
			System.out.println("Roman:   " + romanNameGenerator.compose(3));  // The rules are not rich enough to generate very different Roman names
			System.out.println("Elven:   " + elvenNameGenerator.compose(3));
			System.out.println("Goblin:  " + goblinNameGenerator.compose(3));
			System.out.println("Fantasy: " + fantasyNameGenerator.compose(3));
		}
	}


	ArrayList<String> prefixes = new ArrayList<String>();
	ArrayList<String> mid = new ArrayList<String>();
	ArrayList<String> suffixes = new ArrayList<String>();

	final private static char[] Vowels = {'a', 'e', 'i', 'o', 'u', 'ä', 'ö', 'õ', 'ü', 'y'};
	final private static char[] consonants = {'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y'};

	private String fileName;

	/**
	 * Create new random name generator object. refresh() is automatically called.
	 * @param fileName insert file name, where syllables are located
	 * @throws IOException
	 */
	public NameGenerator(String fileName) {
		this.fileName = fileName;
		refresh();
	}

	public NameGenerator(String type, boolean randomFlag) {
		if (type.equals("goblin")) {
			this.fileName = goblinFilePath;
		} else if (type.equals("elven")) {
			this.fileName = elevenFilePath;
		} else if (type.equals("fantasy")) {
			this.fileName = fantasyFilePath;
		} else {
			this.fileName = romanFilePath;
		}
		this.randomFlag = randomFlag;
		refresh();
	}


	public int semiRandom(ArrayList<String> arrayList){
		if (randomFlag) { return (int)(Math.random() * arrayList.size()); }
		else {
			// Increment or decrement the counter reliably from 0 to Range.
			counter = counter + 1;
			return counter % arrayList.size();
		}
	}

	/**
	 * Change the file. refresh() is automatically called during the process.
	 * @param fileName insert the file name, where syllables are located.
	 * @throws IOException
	 */
	public void changeFile(String fileName) throws IOException {
		if (fileName == null) throw new IOException("File name cannot be null");
		this.fileName = fileName;
		refresh();
	}


	/**
	 * Refresh names from file. No need to call that method, if you are not changing the file during the operation of program, as this method
	 * is called every time file name is changed or new NameGenerator object created.
	 * @throws IOException
	 */
	public void refresh() {
		FileReader input = null;
		BufferedReader bufRead;
		String line;
		try {
			input = new FileReader(fileName);
			bufRead = new BufferedReader(input);
			line="";   
			while (line != null){        
				line = bufRead.readLine();        
				if (line != null && !line.equals("")){
					if (line.charAt(0) == '-'){
						prefixes.add(line.substring(1).toLowerCase());                    
					}
					else if (line.charAt(0) == '+'){
						suffixes.add(line.substring(1).toLowerCase());                    
					}
					else{ 
						mid.add(line.toLowerCase());                    
					}
				}
			}        
			bufRead.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private String upper(String s){
		return s.substring(0,1).toUpperCase().concat(s.substring(1));
	}

	private boolean containsConsFirst(ArrayList<String> array){
		for (String s: array){
			if (consonantFirst(s)) return true;
		}
		return false;
	}

	private boolean containsVocFirst(ArrayList<String> array){
		for (String s: array){
			if (VowelFirst(s)) return true;
		}
		return false;
	}

	private boolean allowCons(ArrayList<String> array){
		for (String s: array){
			if (hatesPreviousVowels(s) || hatesPreviousConsonants(s) == false) return true;
		}
		return false;
	}

	private boolean allowVocs(ArrayList<String> array){
		for (String s: array){
			if (hatesPreviousConsonants(s) || hatesPreviousVowels(s) == false) return true;
		}
		return false;
	}

	private boolean expectsVowel(String s){
		if (s.substring(1).contains("+v")) return true;
		else return false;
	}
	private boolean expectsConsonant(String s){
		if (s.substring(1).contains("+c")) return true;
		else return false;        
	}
	private boolean hatesPreviousVowels(String s){
		if (s.substring(1).contains("-c")) return true;
		else return false;
	}
	private boolean hatesPreviousConsonants(String s){
		if (s.substring(1).contains("-v")) return true;
		else return false;        
	}    

	private String pureSyl(String s){
		s = s.trim();
		if (s.charAt(0) == '+' || s.charAt(0) == '-') s = s.substring(1);
		return s.split(" ")[0];
	}

	private boolean VowelFirst(String s){
		return (String.copyValueOf(Vowels).contains(String.valueOf(s.charAt(0)).toLowerCase()));        
	}

	private boolean consonantFirst(String s){
		return (String.copyValueOf(consonants).contains(String.valueOf(s.charAt(0)).toLowerCase()));        
	}

	private boolean VowelLast(String s){
		return (String.copyValueOf(Vowels).contains(String.valueOf(s.charAt(s.length()-1)).toLowerCase()));        
	}

	private boolean consonantLast(String s){
		return (String.copyValueOf(consonants).contains(String.valueOf(s.charAt(s.length()-1)).toLowerCase()));        
	}


	/**
	 * Compose a new name.
	 * @param numberSyllables The number of syllables used in name.
	 * @return Returns composed name as a String
	 * @throws RuntimeException when logical mistakes are detected inside chosen file, and program is unable to complete the name.
	 */

	public String compose(int numberSyllables){
		if (numberSyllables > 2 && mid.size() == 0) throw new RuntimeException("You are trying to create a name with more than 3 parts, which requires middle parts, " +
				"which you have none in the file "+fileName+". You should add some. Every word, which doesn't have + or - for a prefix is counted as a middle part.");
		if (prefixes.size() == 0) throw new RuntimeException("You have no prefixes to start creating a name. add some and use \"-\" prefix, to identify it as a prefix for a name. (example: -asd)");
		if (suffixes.size() == 0) throw new RuntimeException("You have no suffixes to end a name. add some and use \"+\" prefix, to identify it as a suffix for a name. (example: +asd)");
		if (numberSyllables < 1) throw new RuntimeException("compose(int syls) can't have less than 1 syllable");
		int expecting = 0; // 1 for Vowel, 2 for consonant
		int last = 0; // 1 for Vowel, 2 for consonant
		String name;
		int a = semiRandom(prefixes);

		if (VowelLast(pureSyl(prefixes.get(a)))) last = 1;
		else last = 2;

		if (numberSyllables > 2){
			if (expectsVowel(prefixes.get(a))){ 
				expecting = 1;                
				if (containsVocFirst(mid) == false) throw new RuntimeException("Expecting \"middle\" part starting with Vowel, " +
						"but there is none. You should add one, or remove requirement for one.. "); 
			}
			if (expectsConsonant(prefixes.get(a))){ 
				expecting = 2;                
				if (containsConsFirst(mid) == false) throw new RuntimeException("Expecting \"middle\" part starting with consonant, " +
						"but there is none. You should add one, or remove requirement for one.. "); 
			}
		}
		else {
			if (expectsVowel(prefixes.get(a))){ 
				expecting = 1;                
				if (containsVocFirst(suffixes) == false) throw new RuntimeException("Expecting \"suffix\" part starting with Vowel, " +
						"but there is none. You should add one, or remove requirement for one.. "); 
			}
			if (expectsConsonant(prefixes.get(a))){ 
				expecting = 2;                
				if (containsConsFirst(suffixes) == false) throw new RuntimeException("Expecting \"suffix\" part starting with consonant, " +
						"but there is none. You should add one, or remove requirement for one.. "); 
			}
		}
		if (VowelLast(pureSyl(prefixes.get(a))) && allowVocs(mid) == false) throw new RuntimeException("Expecting \"middle\" part that allows last character of prefix to be a Vowel, " +
				"but there is none. You should add one, or remove requirements that cannot be fulfilled.. the prefix used, was : \""+prefixes.get(a)+"\", which" +
				"means there should be a part available, that has \"-v\" requirement or no requirements for previous syllables at all.");

		if (consonantLast(pureSyl(prefixes.get(a))) && allowCons(mid) == false) throw new RuntimeException("Expecting \"middle\" part that allows last character of prefix to be a consonant, " +
				"but there is none. You should add one, or remove requirements that cannot be fulfilled.. the prefix used, was : \""+prefixes.get(a)+"\", which" +
				"means there should be a part available, that has \"-c\" requirement or no requirements for previous syllables at all.");

		int b[] = new int[numberSyllables];
		for (int i = 0; i<b.length-2; i++){

			do {
				b[i] = semiRandom(mid);
				//System.out.println("exp " +expecting+" VowelF:"+VowelFirst(mid.get(b[i]))+" syl: "+mid.get(b[i]));
			}            
			while (expecting == 1 && VowelFirst(pureSyl(mid.get(b[i]))) == false || expecting == 2 && consonantFirst(pureSyl(mid.get(b[i]))) == false 
					|| last == 1 && hatesPreviousVowels(mid.get(b[i])) || last == 2 && hatesPreviousConsonants(mid.get(b[i])));

			expecting = 0;
			if (expectsVowel(mid.get(b[i]))){ 
				expecting = 1;                
				if (i < b.length-3 && containsVocFirst(mid) == false) throw new RuntimeException("Expecting \"middle\" part starting with Vowel, " +
						"but there is none. You should add one, or remove requirement for one.. "); 
				if (i == b.length-3 && containsVocFirst(suffixes) == false) throw new RuntimeException("Expecting \"suffix\" part starting with Vowel, " +
						"but there is none. You should add one, or remove requirement for one.. "); 
			}
			if (expectsConsonant(mid.get(b[i]))){ 
				expecting = 2;                
				if (i < b.length-3 && containsConsFirst(mid) == false) throw new RuntimeException("Expecting \"middle\" part starting with consonant, " +
						"but there is none. You should add one, or remove requirement for one.. "); 
				if (i == b.length-3 && containsConsFirst(suffixes) == false) throw new RuntimeException("Expecting \"suffix\" part starting with consonant, " +
						"but there is none. You should add one, or remove requirement for one.. "); 
			}
			if (VowelLast(pureSyl(mid.get(b[i]))) && allowVocs(mid) == false && numberSyllables > 3) throw new RuntimeException("Expecting \"middle\" part that allows last character of last syllable to be a Vowel, " +
					"but there is none. You should add one, or remove requirements that cannot be fulfilled.. the part used, was : \""+mid.get(b[i])+"\", which " +
					"means there should be a part available, that has \"-v\" requirement or no requirements for previous syllables at all."); 

			if (consonantLast(pureSyl(mid.get(b[i]))) && allowCons(mid) == false && numberSyllables > 3) throw new RuntimeException("Expecting \"middle\" part that allows last character of last syllable to be a consonant, " +
					"but there is none. You should add one, or remove requirements that cannot be fulfilled.. the part used, was : \""+mid.get(b[i])+"\", which " +
					"means there should be a part available, that has \"-c\" requirement or no requirements for previous syllables at all.");
			if (i == b.length-3){                
				if (VowelLast(pureSyl(mid.get(b[i]))) && allowVocs(suffixes) == false) throw new RuntimeException("Expecting \"suffix\" part that allows last character of last syllable to be a Vowel, " +
						"but there is none. You should add one, or remove requirements that cannot be fulfilled.. the part used, was : \""+mid.get(b[i])+"\", which " +
						"means there should be a suffix available, that has \"-v\" requirement or no requirements for previous syllables at all."); 

				if (consonantLast(pureSyl(mid.get(b[i]))) && allowCons(suffixes) == false) throw new RuntimeException("Expecting \"suffix\" part that allows last character of last syllable to be a consonant, " +
						"but there is none. You should add one, or remove requirements that cannot be fulfilled.. the part used, was : \""+mid.get(b[i])+"\", which " +
						"means there should be a suffix available, that has \"-c\" requirement or no requirements for previous syllables at all.");
			}
			if (VowelLast(pureSyl(mid.get(b[i])))) last = 1;
			else last = 2;
		}        

		int c;
		do {
			c = semiRandom(suffixes);                    
		}            
		while (expecting == 1 && VowelFirst(pureSyl(suffixes.get(c))) == false || expecting == 2 && consonantFirst(pureSyl(suffixes.get(c))) == false 
				|| last == 1 && hatesPreviousVowels(suffixes.get(c)) || last == 2 && hatesPreviousConsonants(suffixes.get(c)));

		name = upper(pureSyl(prefixes.get(a).toLowerCase()));        
		for (int i = 0; i<b.length-2; i++){
			name = name.concat(pureSyl(mid.get(b[i]).toLowerCase()));            
		}
		if (numberSyllables > 1)
			name = name.concat(pureSyl(suffixes.get(c).toLowerCase()));
		return name;        
	}    

}

