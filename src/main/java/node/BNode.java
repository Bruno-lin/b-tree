package node;

import java.util.*;


public class BNode implements Comparable<Object> {

    int degree;         // 树的最小度数

    List<Integer> keys;     // 关键字集合

    List<BNode> children;       // 子节点集合

    boolean isLeaf = true;

    public BNode(int degree) {
        this.degree = degree;
        keys = new ArrayList<>();
        children = new ArrayList<>();
    }

    /**
     * 分割子节点回新的子树
     *
     * @param child 需要分割的满子节点
     */
    void splitChild(BNode child) {
        this.keys.add(child.keys.get(degree - 1));
        Collections.sort(this.keys);
        this.isLeaf = false;

        BNode left = new BNode(degree);
        BNode right = new BNode(degree);
        left.isLeaf = child.isLeaf;
        right.isLeaf = child.isLeaf;

        left.addSplitKeys(child, 0, degree - 1);
        left.addSplitChildren(child, 0, degree);

        right.addSplitKeys(child, degree, 2 * degree - 1);
        right.addSplitChildren(child, degree, 2 * degree);

        this.children.remove(child);
        this.addChild(left);
        this.addChild(right);
    }

    private void addSplitKeys(BNode resource, int from, int to) {
        for (int i = from; i < to; i++) {
            this.keys.add(resource.keys.get(i));
        }
    }

    private void addSplitChildren(BNode resource, int from, int to) {
        if (resource.children.isEmpty()) {
            return;
        }
        for (int i = from; i < to; i++) {
            this.children.add(resource.children.get(i));
        }
    }

    /**
     * 插入数据
     *
     * @param key 新插入的键值
     * @return 新的根节点
     */
    BNode insert(int key) {
        if (this.isFull()) {
            BNode newRoot = new BNode(degree);
            newRoot.addChild(this);

            newRoot.splitChild(this);
            newRoot.insertNotFull(key);
            return newRoot;
        }

        insertNotFull(key);
        return this;
    }

    void insertNotFull(int key) {
        if (this.isLeaf) {
            keys.add(key);
            Collections.sort(keys);
            return;
        }

        BNode child = children.get(getKeyRankFromZero(key));
        if (child.isFull()) {
            this.splitChild(child);
            child = children.get(getKeyRankFromZero(key));
        }
        child.insertNotFull(key);
    }


    int getKeyRankFromZero(int key) {
        int rank = 0;
        Collections.sort(keys);
        for (Integer integer : keys) {
            if (integer > key) {
                break;
            }
            rank += 1;
        }
        return rank;
    }


    private void addChild(BNode node) {
        children.add(node);
        Collections.sort(children);
    }


    private boolean isFull() {
        return keys.size() >= 2 * degree - 1;
    }

//    /**
//     * 以键搜索对应的数据
//     *
//     * @param key 特定的键
//     * @return 键对应的数据, 键不存在则返回 null
//     */

    /**
     * 为了简单起见,判断传入的key是否在树中即可
     */
    boolean research(int key) {
        //TODO


        return true;
    }

    /**
     * 在树中删除某个键和对应的数据
     *
     * @return 新的根节点
     */
    BNode delete(int key) {
        //TODO

        return null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Queue<BNode> visited = new LinkedList<>();
        Queue<BNode> row = new LinkedList<>();

        row.add(this);
        visited.add(this);
        while (!visited.isEmpty()) {
            while (!row.isEmpty()) {
                BNode node = row.poll();
                builder.append(node.keys.toString());
                builder.append("  ");
            }
            builder.append("\n");

            for (BNode node : visited) {
                row.addAll(node.children);
            }
            visited.clear();
            visited.addAll(row);
        }

        return builder.toString();
    }

    @Override
    public int compareTo(Object o) {
        return keys.get(0) - ((BNode) o).keys.get(0);
    }
}
