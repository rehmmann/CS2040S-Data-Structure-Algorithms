import java.util.ArrayList;
import java.util.Arrays;
//Collaborated with Raveen Prabhu
public class Trie {

    // Wildcards
    final char WILDCARD = '.';
    //    TrieNode root;
    private class TrieNode {


        char key;
        boolean isLast;//indicates if the node is the end of a word
        TrieNode[] children = new TrieNode[75];

        // TODO: Create your TrieNode class here.
//	   int[] present_chars = new int[62];
        TrieNode(){
            this.key = '-';
            isLast = false;
        }


        TrieNode(char a){
            this.key = a;
            isLast = false;
        }

        public void setKey(char key) {
            this.key = key;
        }

        TrieNode identifyNode(char c){//returns node that has c as its key
//            for(TrieNode i:children){
//                if(i.key == c){
//                    return i;
//                }
            return children[c - 48];
        }
//            return null;
//        }

        void insertNode(TrieNode n){//Places a node in the children array
            if(containsNode(n)){
                //do nothing
            }
            else{
//                for(int i = 0;i < children.length;i++){
//                    if(children[i] == null){
//                        children[i] = n;
//                        break;
//                    }
//                }
                children[n.key - 48] = n;
            }
        }

        boolean containsNode(TrieNode n){//checks if trie node n is one of the children nodes,if n is null returns false
            if(n == null){
                return false;
            }
            else if(children[n.key - 48] == null){
                return false;
            }
            if(children[n.key - 48].key == n.key){//
                return true;
            }
            return false;
        }

        boolean containsKey(char key){//checks if key exists in the children array
//            for(int i = 0;i < children.length;i++){
//                if(children[i] == null){
//                    return false;
//                }
//
//                if(children[i].key == key){
//                    return true;
//                }
//            }
//            return false;
            if(children[key - 48] == null){
                return false;
            }
            return children[key - 48].key == key;
        }

        public void setLast(){
            this.isLast = true;
        }

//        public boolean hasChild(){
//            for(TrieNode n:children){
//                if(n != null){
//                    return true;
//                }
//            }
//            return false;
//        }
    }

    TrieNode root = new TrieNode();
    //TrieNode root = null;
    public Trie() {
        // TODO: Initialise a trie class here.
        TrieNode root;
    }


    // inserts string s into the Trie
    void insert(String s) {
        // TODO
        if(s == ""){
            root.setLast();//root because the end
        }
        if(contains(s)){
//            System.out.println("goes here");
            return;
        }
        TrieNode current = root;
        for(int i = 0;i < s.length();i++){
            char c = s.charAt(i);
//            if(i == s.length() - 1){//For last char set node to last
//               TrieNode temp = new TrieNode(c);
//                if (!current.containsKey(c)){//if current doesnt contains c as one of its children
//                    current.insertNode(temp);
//                    temp.setLast();
//                }
////                System.out.println(current.key);
//                break;
//            }
            if (current.containsKey(c)){//if current contains c as one of its children
                current = current.identifyNode(c);//sets current to node that has c as its key
//                System.out.println(current.key);
//                System.out.println("Loops");
            }
            else{
                TrieNode n = new TrieNode(c);
                current.insertNode(n);//Inserts new node with no children
                current = n;
//                System.out.println(c);
            }
        }
        current.setLast();
    }

    // checks whether string s exists inside the Trie or not
    boolean contains(String s) {//
        if(s.isEmpty()) {//Empty string represented by root node being set to last
            return root.isLast;
        }
        char lastChar = s.charAt(s.length()-1);//Last character
        TrieNode current = root;
        for (int i = 0;i < s.length() ; i++) {
            char c = s.charAt(i);
            if((int)c < 48 || (int) c > 122) {//Non-alpha numeric returns false
                return false;
            }
            else if(!current.containsKey(c)){
                return false;
            }
//            if (current.containsKey(c)){//if current contains c as one of its children
            current = current.identifyNode(c);//traverses the child
//                System.out.println(current.key);
//                System.out.println(c);
//                if(i == s.length() - 1){//traversed to the last node here
//                    if(current.key == lastChar && current.isLast){
//                        return true;
//                    }
//                }
        }
        return current.isLast;

//            else{
//                return false;
//            }
//        }
//        return false;
    }
    static int numOfWild = 0;
    // Search for string with prefix matching the specified pattern sorted by lexicographical order.
    // Return results in the specified ArrayList.
    // Only return at most the first limit results.
    void prefixSearch(String s, ArrayList<String> results, int limit) {
//        System.out.println(s);
        numOfWild = 0;
        TrieNode current = root;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
//            System.out.println(c);
            if (c == '.') {//find all possible characters
                numOfWild += 1;
                String beg = s.substring(0, i);//String before .
                String end = s.substring(i + 1);//String after .
//                char firstCharOfEnd = end.charAt(0);
//                System.out.println(beg);
//                System.out.println(end);
                for (int j = 0; j < 75; j++) {
                    TrieNode n = current.children[j];//potential node that could replace '.'
                    if (n != null){//&& n.containsKey(firstCharOfEnd) potential node connects to the node after
//                        System.out.println(s);
//                        System.out.println(beg + (char) (j + 48) + end);
                        prefixSearch(beg + (char) (j + 48) + end, results, limit);//put string back into the function with possible chars in place of .
                    }
                }
                break;
            } else if (current.containsKey(c)) {//curr contains c as its child
                current = current.identifyNode(c);//traverses down
                System.out.println(s);
                System.out.println(current.key);
            }
            else {
                return;
            }

            //current = last character in the string
// Method that grabs all possible children of current
//        System.out.println(current.key);
        }
//        System.out.println(s);
        if (current.isLast && !s.contains(".") && contains(s) && results.size() < limit) {
            results.add(s);
        }
        if (numOfWild == 0 && !s.contains(".")) {
//            System.out.println(s);
            prefixSearchHelper(s, results, current, limit);
        }
    }

    void prefixSearchHelper(String s,ArrayList<String> results, TrieNode currNode,int limit) {//helper("ab",node)
        System.out.println(results.size());
        if (results.size() < limit) {
//            if (currNode.isLast) {
//                results.add(s);
//            }
            for (int i = 0; i < 75; i++) {//check all children
                TrieNode node = currNode.children[i];
                if (node == null) {
                    continue;
                } else if (!node.isLast) {
                    prefixSearchHelper(s + node.key, results, node, limit);//
                }
                else if (node.isLast && !results.contains(s)) {
//                    System.out.println(s);
                    if(results.size() < limit) {
                        results.add(s + node.key);
                        prefixSearchHelper(s + node.key, results, node, limit);
                    }
                }
            }
        }
    }


    // Simplifies function call by initializing an empty array to store the results.
    // PLEASE DO NOT CHANGE the implementation for this function as it will be used
    // to run the test cases.
    String[] prefixSearch(String s, int limit) {
        ArrayList<String> results = new ArrayList<String>();
        prefixSearch(s, results, limit);
//        for(String i:results){
//            System.out.println(i);
//        }
        return results.toArray(new String[0]);
    }


    public static void main(String[] args) {
        Trie t = new Trie();

//        t.insert("peter");
//        t.insert("piper");
//        t.insert("picked");
//        t.insert("a");
//        t.insert("peck");
//        t.insert("of");
//        t.insert("pickled");
//        t.insert("peppers");
//        t.insert("pepppito");
//        t.insert("pepi");
//        t.insert("pik");
        t.insert("abbde");
        t.insert("abcd");
        t.insert("abcdef");
        t.insert("abdscd");
        t.insert("abed");
//        t.insert("pik");
//        t.insert("pik");
//        t.insert("");


//        System.out.println(t.contains("pi"));
//        for(int i = 0;i < t.root.children.length;i++){
//            if(t.root.children[i] == null){
//                break;
//            }
//            System.out.println(t.root.children[i].key);
//        }
//        System.out.println(t.root.children['p' - 48].children['i'-48].children['k'-48].isLast);
//        System.out.println(t.root.children[0].children[0].key);
//        System.out.println(t.root.children[0].children[0].children[0].key);
//        System.out.println(t.root.children[0].children[0].children[0].children[0].key);
//        System.out.println(t.root.children[0].children[0].children[0].children[0].children[0].key);


        String[] result1 = t.prefixSearch("ab.d",0);
//        String[] result2 = t.prefixSearch("pe.*", 10);
//        String[] result2 = t.prefixSearch("p", 10);
        System.out.println(Arrays.toString(result1));
        // result1 should be:
        // ["peck", "pepi", "peppers", "pepppito", "peter"]
        // result2 should contain the same elements with result1 but may be ordered arbitrarily
    }
}


//    You might want to check if there might be cases where isLast is not set correctly at the last char.