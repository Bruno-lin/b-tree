package node;

import java.util.*;

public class TreeNode implements Comparable<Object>{
    List<Integer> keys;
    List<TreeNode> children;
    boolean isLeaf = true;
    int degree;

    public TreeNode(int degree) {
        this.degree = degree;
        keys = new ArrayList<>();
        children = new ArrayList<>();
    }

    /**
     * 删除指定关键字
     * @param key　树中存在的关键字
     * @return 树的头节点
     */
    public TreeNode delete(int key) {
        if (this.isLeaf) {
            keys.remove(getKeyIndex(key));
            return this;
        }

        if (keys.contains(key)) {
            deleteContainsKey(key);
        } else {
            TreeNode nodeContainsKey = getChildContainsKey(key);
            extendNode(nodeContainsKey);

            nodeContainsKey.delete(key);
        }

        return this.keys.isEmpty() ? children.get(0) : this;
    }

    private void extendNode(TreeNode nodeContainsKey) {
        if (!nodeContainsKey.isTooShort()) {
            return;
        }

        TreeNode longSibling = getLongerSiblingDefaultPre(nodeContainsKey);
        if (longSibling.isTooShort()) {
            mergePreAndNextNode(nodeContainsKey, longSibling);
        } else {
            rotate(nodeContainsKey, longSibling);
        }
    }

    private void rotate(TreeNode nodeContainsKey, TreeNode longSibling) {
        int nodeIndex = children.indexOf(nodeContainsKey);
        int siblingIndex = children.indexOf(longSibling);
        int middleIndex = Math.min(nodeIndex, siblingIndex);
        int middleKey = keys.get(middleIndex);

        nodeContainsKey.keys.add(middleKey);
        Collections.sort(nodeContainsKey.keys);

        if (nodeIndex > siblingIndex) {
            this.keys.set(middleIndex, longSibling.getLastKey());
            nodeContainsKey.children.add(longSibling.getLastChild());
            longSibling.removeLastChild();
        } else {
            this.keys.set(middleIndex, longSibling.keys.get(0));
            nodeContainsKey.children.add(longSibling.children.get(0));
            longSibling.keys.remove(0);
        }
    }

    private void removeLastChild() {
        children.remove(children.size() - 1);
    }

    private TreeNode getLastChild() {
        return children.get(children.size() - 1);
    }

    private TreeNode getLongerSiblingDefaultPre(TreeNode node) {
        int index = children.indexOf(node);
        if (index == 0) {
            return children.get(index + 1);
        }
        return children.get(index - 1);
    }


    private TreeNode getChildContainsKey(int key) {
        int rank = getKeyRank(key);
        return children.get(rank);
    }

    int getKeyRank(int key) {
        int rank = 0;
        for (Integer integer : keys) {
            if (integer > key) {
                break;
            }
            rank += 1;
        }
        return rank;
    }

    void mergePreAndNextNode(TreeNode pre, TreeNode next) {
        int preIndex = children.indexOf(pre);
        int nextIndex = children.indexOf(next);
        int middleIndex = Math.min(preIndex, nextIndex);
        int middleKey = keys.get(middleIndex);

        pre.keys.addAll(next.keys);
        pre.keys.add(middleKey);
        Collections.sort(pre.keys);

        pre.children.addAll(next.children);
        Collections.sort(pre.children);

        this.children.remove(next);
        this.keys.remove(middleIndex);
    }

    private void deleteContainsKey(int key) {
        int index = getKeyIndex(key);
        TreeNode preNode = children.get(index);
        TreeNode nextNode = children.get(index + 1);

        if (!preNode.isTooShort()) {
            int preKey = preNode.getLastKey();
            keys.set(index, preKey);
            preNode.delete(preKey);

        } else if (!nextNode.isTooShort()) {
            int nextKey = nextNode.keys.get(0);
            keys.set(index, nextKey);
            nextNode.delete(nextKey);

        } else {
            mergePreAndNextNode(preNode, nextNode);
            preNode.delete(key);
        }

    }

    private int getKeyIndex(int key) {
        return keys.indexOf(key);
    }

    boolean isTooShort() {
        return keys.size() < degree;
    }

    private int getLastKey() {
        return keys.get(keys.size() - 1);
    }


    /**
     * 分割子节点回新的子树
     * @param child 需要分割的满子节点
     */
    void splitChild(TreeNode child) {
        this.keys.add(child.keys.get(degree - 1));
        Collections.sort(this.keys);
        this.isLeaf = false;

        TreeNode left = new TreeNode(degree);
        TreeNode right = new TreeNode(degree);
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

    private void addSplitKeys(TreeNode resource, int from, int to) {
        for (int i = from; i < to; i++) {
            this.keys.add(resource.keys.get(i));
        }
    }

    private void addSplitChildren(TreeNode resource, int from, int to) {
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
    TreeNode insert(int key) {
        if (this.isFull()) {
            TreeNode newRoot = new TreeNode(degree);
            newRoot.addChild(this);

            newRoot.splitChild(this);
            newRoot.insertNotFull(key);
            return newRoot;
        }

        insertNotFull(key);
        return this;
    }

    private boolean isFull() {
        return keys.size() >= 2 * degree - 1;
    }

    void insertNotFull(int key) {
        if (this.isLeaf) {
            keys.add(key);
            Collections.sort(keys);
            return;
        }

        TreeNode child = children.get(getKeyRank(key));
        if (child.isFull()) {
            this.splitChild(child);
            child = children.get(getKeyRank(key));
        }
        child.insertNotFull(key);
    }

    private void addChild(TreeNode node) {
        children.add(node);
        Collections.sort(children);
    }

    /**
     * 搜索关键字并返回所在节点
     * @param key
     * @return
     */
    TreeNode search(int key) {
        int i = 0;

        while (i < this.keys.size() && key > this.keys.get(i)) {
            i++;
        }
        if (i < this.keys.size() && keys.get(i) == key) {
            return this;
        }

        if (isLeaf) {
            return null;
        }
        return children.get(i).search(key);
    }

    @Override
    public int compareTo(Object o) {
        return keys.get(0) - ((TreeNode)o).keys.get(0);
    }

        @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Queue<TreeNode> visited = new LinkedList<>();
        Queue<TreeNode> row = new LinkedList<>();

        row.add(this);
        visited.add(this);
        while (!visited.isEmpty()) {
            while (!row.isEmpty()) {
                TreeNode node = row.poll();
                builder.append(node.keys.toString());
                builder.append("  ");
            }
            builder.append("\n");

            for (TreeNode node : visited) {
                row.addAll(node.children);
            }
            visited.clear();
            visited.addAll(row);
        }

        return builder.toString();
    }
}
