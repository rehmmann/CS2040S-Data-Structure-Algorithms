import com.sun.source.tree.Tree;
//import jdk.dynalink.beans.StaticClass;

import java.util.Arrays;

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
     * <p>
     * This class holds the data for a node in a binary tree.
     * <p>
     * Note: we have made things public here to facilitate problem set grading/testing.
     * In general, making everything public like this is a bad idea!
     */
    public static class TreeNode {
        int key;
        public TreeNode left = null;
        public TreeNode right = null;

        TreeNode(int k) {
            key = k;
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
    public static int countNodes(TreeNode node, Child child) {
        if (child == Child.RIGHT) {
            return helper(node.right) + 1;
        } else if (child == Child.LEFT) {
            return helper(node.left) + 1;
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

    public static TreeNode buildTree(TreeNode[] nodeList) {
        // TODO: Implement this
        int middle = (nodeList.length)/2;
        int l = 0;
        int r = nodeList.length - 1;
//        helper3(l, middle, r, nodeList);
//        return nodeList[middle];
        return helper3(l,middle,r,nodeList);
    }



    public static TreeNode helper3(int l, int m,int r,TreeNode[] arr){
        if(l > r){
            return null;
        }
//        if(l == m){
//            return null;
//        }
//        else if(m == r){
//            arr[m].left = arr[l];
//            arr[m].right = null;
//            return null;
//        }
        else{
            //arr[m].left = arr[(l+m)/2];
            //arr[m].right = arr[(m+r)/2 + 1];

//            System.out.println(arr[m].left.key);
//            System.out.print(l + " ") ;
//            System.out.print((l+m)/2 + " ");
//            System.out.print(m-1);
//            System.out.println("");
            TreeNode node = arr[m];

            arr[m].left = helper3(l , (l+m-1)/2, m-1, arr);//left
            arr[m].right = helper3(m+1, ((m+r+1)/2 ), r, arr);//right
            return node;
        }
    }

    /**
     * Rebuild the specified subtree of a node.
     *
     * @param node  the part of the subtree to rebuild
     * @param child specifies which child is the root of the subtree to rebuild
     */
//    public void rebuild(TreeNode node, Child child) {
//        // Error checking: cannot rebuild null tree
//        if (node == null) return;
//        // First, retrieve a list of all the nodes of the subtree rooted at child
//        TreeNode[] nodeList = enumerateNodes(node, child);
//        // Then, build a new subtree from that list
//        TreeNode newChild = buildTree(nodeList);
//        // Finally, replace the specified child with the new subtree
//        if (child == Child.LEFT) {
//            node.left = newChild;
//        } else if (child == Child.RIGHT) {
//            node.right = newChild;
//        }
//    }
    public static void traverse(TreeNode node) {
        if (node == null) {

        } else {
            traverse(node.left);
            System.out.println(node.key);
            traverse(node.right);
        }
    }
    /**
     * Insert a key into the tree
     *
     * @param key the key to insert
     */
    public  void insert(int key) {
        if (root == null) {
            root = new TreeNode(key);
            return;
        }

        TreeNode node = root;

        while (true) {//Traversing to the correct node
            if (key <= node.key) {
                if (node.left == null) break;
                node = node.left;
            } else {
                if (node.right == null) break;
                node = node.right;
            }
        }

        if (key <= node.key) {//Insert key
            node.left = new TreeNode(key);
        } else {
            node.right = new TreeNode(key);
        }
    }


    // Simple main function for debugging purposes
    public static void main(String[] args) {
        SGTree tree = new SGTree();


        SGTree t = new SGTree();

        TreeNode[] myArr = new TreeNode[6];
//        int[] num = {4,10,12,15,18,22,24,25,31,35,44,50,66,70,90};
//        int[] num = {20,30,35,40,45,50,55,60,70};
        int[] num = {0,1,2,3,4,5};
        for(int i = 0;i < myArr.length;i++){
            myArr[i] = new TreeNode(num[i]);
        }
        traverse(buildTree(myArr));
//        System.out.println(buildTree(myArr).right.left);
//        t.insert(4);
//        t.insert(5);
//        t.insert(3);
//        t.insert(2);
//        t.insert(1);
//        t.insert(6);
//        t.insert(7);
//        TreeNode[] output = enumerateNodes(t.root,Child.RIGHT);
//        for(int i = 0 ; i < output.length;i++){
//            System.out.println(output[i].key);
//        }

//        System.out.println(enumerateNodes(t.root,Child.LEFT)[0].key);
//        System.out.println(enumerateNodes(t.root,Child.LEFT)[1].key);
//        System.out.println(enumerateNodes(t.root,Child.LEFT)[2].key);
//
//        System.out.println(enumerateNodes(t.root,Child.RIGHT)[0].key);
//        System.out.println(enumerateNodes(t.root,Child.RIGHT)[1].key);
//        System.out.println(enumerateNodes(t.root,Child.RIGHT)[2].key);
//        System.out.println(a == Child.RIGHT);
//        tree.rebuild(tree.root, Child.RIGHT);
//            helper2(t.root ,"");
//        System.out.println(helper(new TreeNode(0)));
//        System.out.println(tree.helper(tree2)) ;
//        System.out.println(Child.LEFT);

    }
}
