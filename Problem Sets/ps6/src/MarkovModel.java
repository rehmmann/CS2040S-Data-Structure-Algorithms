import java.util.*;

/**
 * This is the main class for your Markov Model.
 *
 * Assume that the text will contain ASCII characters in the range [1,255].
 * ASCII character 0 (the NULL character) will be treated as a non-character.
 *
 * Any such NULL characters in the original text should be ignored.
 */
public class MarkovModel {

	// Use this to generate random numbers as needed
	private Random generator = new Random();

	// This is a special symbol to indicate no character
	public static final char NOCHARACTER = (char) 0;

	public int order;

	public HashMap<String,HashMap<String,Integer>> firstMap;

	public String myText;
	/**
	 * Constructor for MarkovModel class.
	 *
	 * @param order the number of characters to identify for the Markov Model sequence
	 * @param seed the seed used by the random number generator
	 */
	public MarkovModel(int order, long seed) {//The order of the Markov model is the length of that preceding string.
		// Initialize your class here
		this.order = order;
		// Initialize the random number generator
		generator.setSeed(seed);
	}

	/**
	 * Builds the Markov Model based on the specified text string.
	 */
	public void initializeText(String text) {
		firstMap = new HashMap<>();
		// Build the Markov model here
		this.myText = text;
		ArrayList<String> first = new ArrayList<>();
		for (int i = 0; i < text.length() - order; i++) {

			String prefix = text.substring(i, i + order);//abc
			String letter = text.substring(i + order, i + order + 1);//a
//			System.out.println("I is " + i + " i plus order is " + (i+order) + prefix);
//			System.out.println(prefix);
//			System.out.println(letter);
			if (!first.contains(prefix)) {
				HashMap<String, Integer> secondMap = new HashMap<>();
				first.add(prefix);
				secondMap.put(letter, 1);
				firstMap.put(prefix, secondMap);
			} else if (first.contains(prefix)) {
				HashMap<String, Integer> secondMap = firstMap.get(prefix);
				if (secondMap.containsKey(letter)) {
					int count = secondMap.get(letter);
					secondMap.put(letter, count + 1);
				} else if (!secondMap.containsKey(letter)) {
					secondMap.put(letter, 1);
				}
				firstMap.put(prefix, secondMap);
			}

		}
//		System.out.println(first.toString());
//		System.out.println(firstMap.toString());
//		for(int i = 0;i < first.size();i++){

		}


	/**
	 * Returns the number of times the specified kgram appeared in the text.
	 */
	public int getFrequency(String kgram) {
		if(firstMap.get(kgram) == null || kgram.length() != order){
			return 0;
		}
		HashMap<String,Integer> secondMap = firstMap.get(kgram);
		int counter = 0;
		for(String key:secondMap.keySet()){
			counter += secondMap.get(key);
		}
//		int total = myText.length();
//		int kgramLength = kgram.length();
//		int temp = total - total % kgramLength;
//		for(int i = 0;i < temp;i++){
//
//		}
		return counter;
	}

	/**
	 * Returns the number of times the character c appears immediately after the specified kgram.
	 */
	public int getFrequency(String kgram, char c) {
		String temp = Character.toString(c);
		if(firstMap.get(kgram)== null){
			return 0;
		}
		if(firstMap.get(kgram).get(temp) == null){
			return 0;
		}
		return firstMap.get(kgram).get(temp);
	}

	/**
	 * Generates the next character from the Markov Model.
	 * Return NOCHARACTER if the kgram is not in the table, or if there is no
	 * valid character following the kgram.
	 */
	public char nextCharacter(String kgram) {
		// See the problem set description for details
		// on how to make the random selection.
		if(!firstMap.containsKey(kgram)){
			return NOCHARACTER;
		}
		HashMap<String,Integer> secondMap = firstMap.get(kgram);
		ArrayList<Character> allChar = new ArrayList<>();//Store all chars sorted
		ArrayList<Character> seq = new ArrayList<>();//{a,a,a,b,c,c,f,..}
		for(String key : secondMap.keySet()){
			allChar.add(key.charAt(0));
		}
		Collections.sort(allChar);//Sorts according to alphabetic order
		for(int i = 0;i < allChar.size();i++){
			Character curr = allChar.get(i);
			int prob = secondMap.get(curr.toString());
			for (int j = 0;j < prob;j++){
				seq.add(curr);
			}
		}
//		System.out.println(seq.toString());
		int randomNumber = generator.nextInt(seq.size());
//		System.out.println(randomNumber);
		return seq.get(randomNumber);
	}

	public static void main(String[] args) {
		MarkovModel m = new MarkovModel(2 , 100);
		m.initializeText("abedab c");
		System.out.println(m.nextCharacter("ab"));
	}
}