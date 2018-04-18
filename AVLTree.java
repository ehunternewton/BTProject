package avl_tree;

import java.io.*;
import java.util.*;

/**
 *
 * @author hunternewton
 */
public class AVLTree 
{
    Node root;
 
    // A utility function to get height of the tree
    int height(Node N)
    {
        if (N == null)
             return 0;
         return N.height;
    }
 
    // A utility function to get maximum of two integers
    int max(int a, int b)
    {
        return (a > b) ? a : b;
    }
 
    // A utility function to right rotate subtree rooted with y
    // See the diagram given above.
    Node rightRotate(Node y)
    {
        Node x = y.left;
        Node T2 = x.right;
 
        // Perform rotation
        x.right = y;
        y.left = T2;
 
        // Update heights
        y.height = max(height(y.left), height(y.right)) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;
 
        // Return new root
        return x;
    }
 
    // A utility function to left rotate subtree rooted with x
    // See the diagram given above.
    Node leftRotate(Node x)
    {
        Node y = x.right;
        Node T2 = y.left;
 
        // Perform rotation
        y.left = x;
        x.right = T2;
 
        //  Update heights
        x.height = max(height(x.left), height(x.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;
 
        // Return new root
        return y;
    }
 
    // Get Balance factor of node N
    int getBalance(Node N)
    {
        if (N == null)
            return 0;
        return height(N.left) - height(N.right);
    }
 
    Node insert(Node node, String name, int salary)
    {
        /* 1.  Perform the normal BST rotation */
        if (node == null) 
        {
            System.out.println("Employee added!");
            return (new Node(name, salary));
        }
 
        if (salary < node.salary)
        {
            if (node.salary - salary < 3000) 
            {
                System.out.printf("Could not add %s, salaries must be "
                        + "different by at least $3000%n", name);
                return node;
            }
            node.left = insert(node.left, name, salary);
        }
        else if (salary > node.salary)
        {
            if (salary - node.salary < 3000) {
                System.out.printf("Could not add %s, salaries must be "
                        + "different by at least $3000%n", name);
                return node;
            }
            node.right = insert(node.right, name, salary);
        }
        else // Equal keys not allowed
            return node;
        
 
        /* 2. Update height of this ancestor node */
        node.height = 1 + max(height(node.left),
                              height(node.right));
 
        /* 3. Get the balance factor of this ancestor
           node to check whether this node became
           Wunbalanced */
        int balance = getBalance(node);
 
        // If this node becomes unbalanced, then
        // there are 4 cases Left Left Case
        if (balance > 1 && salary < node.left.salary)
            return rightRotate(node);
 
        // Right Right Case
        if (balance < -1 && salary > node.right.salary)
            return leftRotate(node);
 
        // Left Right Case
        if (balance > 1 && salary > node.left.salary)
        {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
 
        // Right Left Case
        if (balance < -1 && salary < node.right.salary)
        {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
 
        /* return the (unchanged) node pointer */
        return node;
    }
 
    /* Given a non-empty binary search tree, return the
       node with minimum key value found in that tree.
       Note that the entire tree does not need to be
       searched. */
    Node minValueNode(Node node)
    {
        Node current = node;
 
        /* loop down to find the leftmost leaf */
        while (current.left != null)
           current = current.left;
 
        return current;
    }
 
    Node deleteNode(Node root, int salary)
    {
        // STEP 1: PERFORM STANDARD BST DELETE
        if (root == null) 
        {
            System.out.printf("$%s salary not found%n", salary);
            return root;
        }
 
        // If the key to be deleted is smaller than
        // the root's key, then it lies in left subtree
        if (salary < root.salary)
            root.left = deleteNode(root.left, salary);
 
        // If the key to be deleted is greater than the
        // root's key, then it lies in right subtree
        else if (salary > root.salary)
            root.right = deleteNode(root.right, salary);
 
        // if key is same as root's key, then this is the node
        // to be deleted
        else
        {
            System.out.printf("%s no longer works here...%n", root.name);
 
            // node with only one child or no child
            if ((root.left == null) || (root.right == null))
            {
                Node temp = null;
                if (temp == root.left)
                    temp = root.right;
                else
                    temp = root.left;
 
                // No child case
                if (temp == null)
                {
                    temp = root;
                    root = null;
                }
                else   // One child case
                    root = temp; // Copy the contents of
                                 // the non-empty child
            }
            else
            {
 
                // node with two children: Get the inorder
                // successor (smallest in the right subtree)
                Node temp = minValueNode(root.right);
 
                // Copy the inorder successor's data to this node
                root.salary = temp.salary;
 
                // Delete the inorder successor
                root.right = deleteNode(root.right, temp.salary);
            }
        }
 
        // If the tree had only one node then return
        if (root == null)
            return root;
 
        // STEP 2: UPDATE HEIGHT OF THE CURRENT NODE
        root.height = max(height(root.left), height(root.right)) + 1;
 
        // STEP 3: GET THE BALANCE FACTOR OF THIS NODE (to check whether
        //  this node became unbalanced)
        int balance = getBalance(root);
 
        // If this node becomes unbalanced, then there are 4 cases
        // Left Left Case
        if (balance > 1 && getBalance(root.left) >= 0)
            return rightRotate(root);
 
        // Left Right Case
        if (balance > 1 && getBalance(root.left) < 0)
        {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }
 
        // Right Right Case
        if (balance < -1 && getBalance(root.right) <= 0)
            return leftRotate(root);
 
        // Right Left Case
        if (balance < -1 && getBalance(root.right) > 0)
        {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }
 
        return root;
    }
    
    public String printNode(Node node) 
    {
        return (node.name + ", " + node.salary);
    }
 
    // A utility function to print preorder traversal of
    // the tree. The function also prints height of every
    // node
    void preOrder(Node node)
    {
        if (node != null)
        {
            System.out.println(node.name + ", $" + node.salary);
            preOrder(node.left);
            preOrder(node.right);
        }
    }
    
    void inOrder(Node node)
    {
        if (node != null) {
            inOrder(node.left);
            System.out.println(node.name + ", $" + node.salary);
            inOrder(node.right);
        }
    }
    
    public static Node search(Node headNode, int sal) 
    {
        if (sal < headNode.salary) {
            if (headNode.left != null) {
                search(headNode.left, sal);
            } else {
                System.out.printf("%nNo results for $%s salary%n%n", sal);
                return null;
            }
        } else if (sal > headNode.salary) {
            if (headNode.right != null) {
                search(headNode.right, sal);
            } else {
                System.out.printf("%nNo results for $%s salary%n%n", sal);
                return null;
            }
        } else if (sal == headNode.salary){
            System.out.printf("%n%s makes $%s per year%n%n", headNode.name, headNode.salary);
        } 
        return headNode;
    }
    
    public void save(Node mainNode, PrintWriter w) throws IOException 
    {
        if (mainNode == null)  
            return;
        boolean top_call = false;  
        if (w == null) {
            FileOutputStream outputStream = new FileOutputStream("/Users/"
                                            + "hunternewton/NetBeansProjects/"
                                            + "AVL_Tree/src/avl_tree/test.txt");
            w = new PrintWriter(outputStream); 
            top_call = true;  
        }
        save(mainNode.left, w);
        w.println(printNode(mainNode));
        save(mainNode.right, w);

        if (top_call)  
            w.close();
    }

    public void save() throws IOException{
        save(root, null);
    }
    
    public static boolean tryParseInt(String value) {  
        try {  
            Integer.parseInt(value);  
            return true;  
        } catch (NumberFormatException e) {  
            return false;  
        }  
    }
    
    public static int menu() {

        int selection;
        String str;
        Scanner input = new Scanner(System.in);

        /***************************************************/
        System.out.println("-------------------------");
        System.out.println("Choose from these choices");
        System.out.println("-------------------------");
        System.out.println("1 - Display Employee Database by Salary");
        System.out.println("2 - Display Binary Tree Height & PreOrder");
        System.out.println("3 - Add an Employee");
        System.out.println("4 - Search for an Employee by Salary");
        System.out.println("5 - Delete an Employee by Salary");
        System.out.println("6 - Save Employee Database & Quit");
        System.out.println("7 - Quit without saving");

        str = input.next();
        while (!AVLTree.tryParseInt(str)) {  
            System.out.println("Invalid entry, try again: ");
            str = input.next();
        }
        selection = Integer.parseInt(str);  
        return selection;        
    }
 
    public static void main(String[] args) throws IOException
    {
        AVLTree tree = new AVLTree();
        
        LinkedList<Node> btNodes = Node.load();
        
        for(Node btn : btNodes) {
           tree.root = tree.insert(tree.root, btn.name, btn.salary);
        }
        
        int choice = AVLTree.menu();
        
        while (choice != 7)
        if (choice == 1) {
            //display in order
            System.out.println("-------------------------");
            System.out.println("Employee, Salary");
            System.out.println("-------------------------");
            tree.inOrder(tree.root);
            choice = AVLTree.menu();
        } else if (choice == 2) {
            // display height
            System.out.println("-------------------------");
            System.out.println("Total Height: " + tree.height(tree.root));
            System.out.println("Left Subtree Height: " 
                                + tree.height(tree.root.left));
            System.out.println("Right Subtree Height: " 
                                + tree.height(tree.root.right));
            System.out.println("-------------------------");
            System.out.println("PreOrder:");
            System.out.println("-------------------------");
            tree.preOrder(tree.root);
            choice = AVLTree.menu();
        } else if (choice == 3) {
            //add an employee
            Scanner input = new Scanner(System.in);
            System.out.println("\nEnter employee name: ");
            String name = input.nextLine();
            System.out.println("Enter employee's salary: ");
            String salIn = input.nextLine();
            while (!AVLTree.tryParseInt(salIn)) {  
                System.out.println("Invalid entry, try again: ");
                salIn = input.next();
            }
            int sal = Integer.parseInt(salIn);
            tree.insert(tree.root, name, sal);
            choice = AVLTree.menu();
        } else if (choice == 4) {
            //search 
            Scanner input = new Scanner(System.in);
            System.out.println("Enter employee's salary: ");
            String salIn = input.nextLine();
            while (!AVLTree.tryParseInt(salIn)) {  
                System.out.println("Invalid entry, try again: ");
                salIn = input.next();
            }
            int sal = Integer.parseInt(salIn);
            tree.search(tree.root, sal);
            choice = AVLTree.menu();
        } else if (choice == 5) {
            //delete
            Scanner input = new Scanner(System.in);
            System.out.println("Enter employee's salary "
                                + "you wish to delete: ");
            String salIn = input.nextLine();
            while (!AVLTree.tryParseInt(salIn)) {  
                System.out.println("Invalid entry, try again: ");
                salIn = input.next();
            }
            int sal = Integer.parseInt(salIn);
            tree.deleteNode(tree.root, sal);
            choice = AVLTree.menu();
        } else if (choice == 6) {
            //save 
            tree.save();
            choice = 7;
        }else {
            choice = AVLTree.menu();
        }
        
        System.exit(0);
    }
}