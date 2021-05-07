package node;

import node.BNode;

public class Test {

    public static void main(String[] args) {

        BNode node = new BNode(2);

        BNode result = node.insert(1).insert(3).insert(2).insert(7).insert(9).insert(6)
                .insert(4).insert(8).insert(5).insert(-1);
        System.out.println(result);
    }
}
