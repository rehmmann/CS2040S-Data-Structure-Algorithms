

/**
 * ScapeGoat Tree class
 *
 * This class contains some of the basic code for implementing a ScapeGoat tree.
 * This version does not include any of the functionality for choosing which node
 * to scapegoat.  It includes only code for inserting a node, and the code for rebuilding
 * a subtree.
 */

public class SGTree {

    // Designates which child in a binary tree
    enum Child {LEFT, RIGHT}

    /**
     * TreeNode class.
     *
     * This class holds the data for a node in a binary tree.
     *
     * Note: we have made things public here to facilitate problem set grading/testing.
     * In general, making everything public like this is a bad idea!
     *
     */
    public static class TreeNode {
        int key;
		int weight;
        public TreeNode left = null;
        public TreeNode right = null;

        TreeNode(int k) {
            key = k;
            this.weight = 1;
        }

        TreeNode(int k,int weight) {
            key = k;
            this.weight = weight;
        }

        public void incrementWeight(){
            this.weight++;
        }

        public void setWeight(int w){
            this.weight = w;
        }

    }

    // Root of the binary tree
    public TreeNode root = null;
    static int i = 0;
    /**
     * Count the number of nodes in the specified subtree
     *
     * @param node  the parent node, not to be counted
     * @param child the specified subtree
     * @return number of nodes
     */
//    public int countNodes(TreeNode node, Child child) {
//        // TODO: Implement this
//        return 0;
//    }

    public static int countNodes(TreeNode node, Child child) {
        if (child == Child.RIGHT) {
            return helper(node.right);
        } else if (child == Child.LEFT) {
            return helper(node.left) ;
        }
        return 0;
    }

    public static int helper(TreeNode t) {//Counts number of nodes
        if(t == null){
            return 0;
        }
        else {
            return helper(t.left) + helper(t.right) + 1;
        }
    }

    /**
     * Build an array of nodes in the specified subtree.
     *
     * @param node  the parent node, not to be included in returned array
     * @param child the specified subtree
     * @return array of nodes
     */
//    TreeNode[] enumerateNodes(TreeNode node, Child child) {
//        // TODO: Implement this
//        return new TreeNode[0];
//    }

    public static TreeNode[] enumerateNodes(TreeNode node, Child child) {

        int size = countNodes(node,child);
        // TODO: Implement this
        if(child == Child.RIGHT){
            node = node.right;
        }
        else if(child == Child.LEFT){
            node = node.left;
        }

        TreeNode[] myArr = new TreeNode[size];//Declare array
        SGTree.i = 0;
        helper2(node,myArr);

        return myArr;
    }

    public static void helper2(TreeNode node,TreeNode[] arr){//In-order Traversal
        if(node.left != null){
            helper2(node.left,arr);
        }

        arr[i] = node;
        SGTree.i += 1;

        if(node.right != null){
            helper2(node.right,arr);
        }
    }

    /**
     * Builds a tree from the list of nodes Returns the node that is the new root of the subtree
     *
     * @param nodeList ordered array of nodes
     * @return the new root node
     */
//    TreeNode buildTree(TreeNode[] nodeList) {
//        // TODO: Implement this
//        return new TreeNode(0);
//    }

    public  TreeNode buildTree(TreeNode[] nodeList) {

        // TODO: Implement this

        int middle = (nodeList.length)/2;

        int l = 0;

        int r = nodeList.length - 1;


        return helper3(l,middle,r,nodeList);

    }

    public static TreeNode helper3(int l, int m,int r,TreeNode[] arr){

        if(l > r){

            return null;

        }


        else{

            TreeNode node = arr[m];

            arr[m].left = helper3(l , (l+m-1)/2, m-1, arr);//left

            arr[m].right = helper3(m+1, ((m+r+1)/2 ), r, arr);//right

            return node;

        }

    }
    /**
     * Determines if a node is balanced.  If the node is balanced, this should return true.  Otherwise, it should return false.
     * A node is unbalanced if either of its children has weight greather than 2/3 of its weight.
     *
     * @param node a node to check balance on
     * @return true if the node is balanced, false otherwise
     */
    public static boolean checkBalance(TreeNode node) {// determines whether the specified tree node is unbalanced.
        // TODO: Implement this
        if(node == null){
            return true;
        }
        if(node.left != null) {
            if (node.left.weight > node.weight * 2 / 3) {
                return false;
            }
        }
        if(node.right != null) {
             if (node.right.weight > node.weight * 2 / 3) {
                return false;
            }
        }
        return true;
    }

    /**
    * Rebuild the specified subtree of a node.
    *
    * @param node the part of the subtree to rebuild
    * @param child specifies which child is the root of the subtree to rebuild
    */
    public void rebuild(TreeNode node, Child child) {
        //Ensure it accounts for weights of the tree & rebuilds child tree
        // Error checking: cannot rebuild null tree
        if (node == null) return;
        // First, retrieve a list of all the nodes of the subtree rooted at child
        TreeNode[] nodeList = enumerateNodes(node, child);
        // Then, build a new subtree from that list
        TreeNode newChild = buildTree(nodeList);
        // Finally, replace the specified child with the new subtree
        if (child == Child.LEFT) {
            node.left = newChild;
        } else if (child == Child.RIGHT) {
            node.right = newChild;
        }
        fixWeights(newChild);//updates the weight of all nodes
}

    public static void fixWeights(TreeNode n){//Updates the weights of the child node
        if(n == null){
            return;
        }
        else if(n.right == null && n.left == null){
            n.weight = 1;
        }
        else if(n.right == null){
            fixWeights(n.left);
            n.weight = n.left.weight + 1;
        }
        else if(n.left == null){
            fixWeights(n.right);
            n.weight = n.right.weight + 1;
        }
        else{
            fixWeights(n.right);
            fixWeights(n.left);
            n.weight = n.left.weight + n.right.weight + 1;
        }
    }

//    Step1: Insert an element in the tree
//    Step2: Check if every node is balanced
//    Step3: Re-balance the parent node

    //Insert the specified key in the tree using a typical binary search tree insertion.  (Notice that this new node will be inserted as a leaf.)
    // Identify the highest unbalanced node on the root-to-leaf path to the newly inserted node.
    // If there is no such unbalanced node, then we are done. If there is an unbalanced node, then rebuild it

    /**
    * Insert a key into the tree
    *
    * @param key the key to insert
    */
    public void insert(int key) {//Check if nodes balanced
        if (root == null) {
            root = new TreeNode(key);
            return;
        }

        TreeNode node = root;

        while (true) {//Traversing to the correct node
            if (key <= node.key) {
                if (node.left == null) break;
                node.incrementWeight();//Increases weight of every node in the traversal path
                node = node.left;
            } else {
                if (node.right == null) break;
                node.incrementWeight();//Increases weight of every node in the traversal path
                node = node.right;
            }
        }
        if (key <= node.key) {//Insert key
            node.incrementWeight();
            node.left = new TreeNode(key);
        } else {
            node.incrementWeight();
            node.right = new TreeNode(key);
        }
        findNode(key);
    }

    public void findNode(int key) {//Finds unbalance node and calls rebuild on its parent
//        if (root == null) {
//            return;
//        }

        TreeNode node = root;

        while (true) {//Traversing to the correct node
            if (key <= node.key) {
                if (node.left == null) break;
//                node = node.left;
                if(!checkBalance(node.left)){//
                    System.out.println("rebuild left");
                    rebuild(node,Child.LEFT);//rebuilds
                    break;
                }
                node = node.left;
            }
            else {
                if (node.right == null) break;

                else if(!checkBalance(node.right)){//
                    System.out.println("rebuild right");
                    rebuild(node,Child.RIGHT);
                    break;
                }
                node = node.right;
            }

        }
    }


//    public void findNode(int key) {
//
//        TreeNode node = root;
//        System.out.printf("Key: %d\n", key);
//        System.out.printf("Root: %d\n", node.key);
//
//
//        while (true) {
//            System.out.printf("Current node: %d\n", node.key);
//            if (key <= node.key) {
//                System.out.println("left");
//                if (node.left == null) {
//                    //System.out.println(node.key);
//                    System.out.println("left, cuz node.left is null");
//                    break;
//                }
//
//                if (!checkBalance(node.left)) {
//                    System.out.println("rebuild left tree");
//                    rebuild(node, Child.LEFT);
//                    break;
//                }
//
//                node = node.left;
//                //System.out.println(node.key);
//
//
//            } else {
//                System.out.println("right");
//                if (node.right == null) {
//                    //System.out.println("right");
//                    break;
//                }
//                System.out.println("did not break from loop");
//
//
//                if (!checkBalance(node.right)) {
//                    System.out.println("rebuild right tree");
//                    rebuild(node, Child.RIGHT);
//                    break;
//                }
//                System.out.println("node gets updated\n");
//                node = node.right;
//
//
//            }
//        }
//    }
    // Simple main function for debugging purposes
    public static void main(String[] args) {
        SGTree tree = new SGTree();
//        for (int i = 0; i < 100; i++) {
//            tree.insert(i);
//        }


        tree.insert(4);
        tree.insert(2);
        tree.insert(6);
        tree.insert(5);
        tree.insert(7);
        tree.insert(9);
        tree.insert(10);
//        tree.rebuild(tree.root, Child.RIGHT);

//        fixWeights(tree.root);
//        tree.rebuild(tree.root, Child.RIGHT);
//        System.out.println(tree.root.right.weight);
//        System.out.println(tree.root.weight);
//        System.out.println(checkBalance(tree.root));

    }
}










