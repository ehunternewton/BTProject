package avl_tree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

/**
 *
 * @author hunternewton
 */
public class Node {
    String name;
    int salary, height;
    Node left, right;
 
    Node(String name, int salary) {
        this.name = name;
        this.salary = salary;
        height = 1;
    }
    
    public static LinkedList<Node> load() throws IOException {
        FileReader in = new FileReader("/Users/hunternewton/NetBeansProjects/"
                                        + "AVL_Tree/src/avl_tree/test.txt");
        BufferedReader br = new BufferedReader(in);

        LinkedList<Node> e = new LinkedList<Node>();

        String line = br.readLine();
        while (line != null && !line.equals("")) 
        {
            String arr[] = line.split(", ");
            if (arr.length == 2) {
                e.add(new Node(arr[0], Integer.parseInt(arr[1])));
            } 
            line = br.readLine();
        }
        return e;
    }
}


