//package node;
//
//import java.util.*;
//
//
//public class TreeNode implements Comparable<Object> {
//
//    int degree;         // 树的最小度数
//
//    List<Integer> keys;     // 关键字集合
//
//    List<TreeNode> children;       // 子节点集合
//
//    boolean isLeaf = true;
//
//    public TreeNode(int degree) {
//        this.degree = degree;
//        keys = new ArrayList<>();
//        children = new ArrayList<>();
//    }
//
//    /**
//     * 分割子节点回新的子树
//     *
//     * @param child 需要分割的满子节点
//     */
//    void splitChild(TreeNode child) {
//        this.keys.add(child.keys.get(degree - 1));
//        Collections.sort(this.keys);
//        this.isLeaf = false;
//
//        TreeNode left = new TreeNode(degree);
//        TreeNode right = new TreeNode(degree);
//        left.isLeaf = child.isLeaf;
//        right.isLeaf = child.isLeaf;
//
//        left.addSplitKeys(child, 0, degree - 1);
//        left.addSplitChildren(child, 0, degree);
//
//        right.addSplitKeys(child, degree, 2 * degree - 1);
//        right.addSplitChildren(child, degree, 2 * degree);
//
//        this.children.remove(child);
//        this.addChild(left);
//        this.addChild(right);
//    }
//
//    private void addSplitKeys(TreeNode resource, int from, int to) {
//        for (int i = from; i < to; i++) {
//            this.keys.add(resource.keys.get(i));
//        }
//    }
//
//    private void addSplitChildren(TreeNode resource, int from, int to) {
//        if (resource.children.isEmpty()) {
//            return;
//        }
//        for (int i = from; i < to; i++) {
//            this.children.add(resource.children.get(i));
//        }
//    }
//
//    /**
//     * 插入数据
//     *
//     * @param key 新插入的键值
//     * @return 新的根节点
//     */
//    TreeNode insert(int key) {
//        if (this.isFull()) {
//            BNode newRoot = new BNode(degree);
//            newRoot.addChild(this);
//
//            newRoot.splitChild(this);
//            newRoot.insertNotFull(key);
//            return newRoot;
//        }
//
//        insertNotFull(key);
//        return this;
//    }
//
//    void insertNotFull(int key) {
//        if (this.isLeaf) {
//            keys.add(key);
//            Collections.sort(keys);
//            return;
//        }
//
//        TreeNode child = children.get(getKeyRankFromZero(key));
//        if (child.isFull()) {
//            this.splitChild(child);
//            child = children.get(getKeyRankFromZero(key));
//        }
//        child.insertNotFull(key);
//    }
//
//
//    int getKeyRankFromZero(int key) {
//        int rank = 0;
//        Collections.sort(keys);
//        for (Integer integer : keys) {
//            if (integer > key) {
//                break;
//            }
//            rank += 1;
//        }
//        return rank;
//    }
//
//
//    private void addChild(TreeNode node) {
//        children.add(node);
//        Collections.sort(children);
//    }
//
//
//    private boolean isFull() {
//        return keys.size() >= 2 * degree - 1;
//    }
//
////    /**
////     * 以键搜索对应的数据
////     *
////     * @param key 特定的键
////     * @return 键对应的数据, 键不存在则返回 null
////     */
//
//    /**
//     * 为了简单起见,判断传入的key是否在树中即可
//     */
//    boolean research(int key) {
//        //TODO
//
//
//        return true;
//    }
//
//    /**
//     * 在树中删除某个键和对应的数据
//     *
//     * @return 新的根节点
//     */
//    TreeNode delete(int key) {
//        if (this.isLeaf) {
//            this.keys.remove(keys.indexOf(key));
//            return this;
//        }
//        // key in current node
//        if (keys.contains(key)) {  .
//            if (this.keys.size() >= degree) {
//                deleteLegal(key);
//                return this;
//            }
//
//            int indexOfKey = keys.indexOf(key);
//            TreeNode preChild = this.children.get(indexOfKey);
//            TreeNode nextChild = this.children.get(indexOfKey + 1);
//            if (preChild.keys.size() >= degree) {
//                int lastKeyOfChild = preChild.keys.get(preChild.keys.size() - 1);
//                this.keys.set(indexOfKey, lastKeyOfChild);
//
//                preChild.deleteLegal(lastKeyOfChild);
//                return this;
//            } else if (nextChild.keys.size() >= degree) {
//                int firstKeyOfChild = nextChild.keys.get(0);
//                this.keys.set(indexOfKey, firstKeyOfChild);
//
//                nextChild.deleteLegal(firstKeyOfChild);
//                return this;
//            } else {
//                TreeNode newChild = mergeTwoAdjacentChildren(preChild, nextChild);
//                newChild.deleteLegal(key);
//                if (this.keys.isEmpty()) {
//                    return newChild;
//                }
//            }
//        }
//        // key not in current node
//        else {
//            int rank = getKeyRankFromZero(key);
//            TreeNode nodeContainsKey = children.get(rank);
//
//            if (nodeContainsKey.keys.size() < degree) {
//                extendNode(rank, nodeContainsKey);
//            }
//
//            if (this.keys.size() == 0) {
//                this.children.get(0).deleteLegal(key);
//                return this.children.get(0);
//            }
//        }
//        return this;
//    }
//
//    private void deleteLegal(int key) {
//        if (this.isLeaf) {
//            this.keys.remove(keys.indexOf(key));
//            return;
//        }
//
//        if (keys.contains(key)) {
//            int indexOfKey = keys.indexOf(key);
//            TreeNode preChild = this.children.get(indexOfKey);
//            TreeNode nextChild = this.children.get(indexOfKey + 1);
//            if (preChild.keys.size() >= degree) {
//                int lastKeyOfChild = preChild.keys.get(preChild.keys.size() - 1);
//                this.keys.set(indexOfKey, lastKeyOfChild);
//
//                preChild.deleteLegal(lastKeyOfChild);
//            } else if (nextChild.keys.size() >= degree) {
//                int firstKeyOfChild = nextChild.keys.get(0);
//                this.keys.set(indexOfKey, firstKeyOfChild);
//
//                nextChild.deleteLegal(firstKeyOfChild);
//            } else {
//                TreeNode newChild = mergeTwoAdjacentChildren(preChild, nextChild);
//                newChild.deleteLegal(key);
//            }
//        }else {
//            int rank = getKeyRankFromZero(key);
//            TreeNode nodeContainsKey = children.get(rank);
//
//            if (nodeContainsKey.keys.size() < degree) {
//                extendNode(rank, nodeContainsKey);
//            }
//            if (this.keys.isEmpty()) {
//                this.children.get(0).deleteLegal(key);
//            }else {
//                rank = getKeyRankFromZero(key);
//                nodeContainsKey = children.get(rank);
//                nodeContainsKey.deleteLegal(key);
//            }
//        }
//    }
//
//
//    private void extendNode(int rank, TreeNode nodeContainsKey) {
//        TreeNode preSibling = null;
//        TreeNode nextSibling = null;
//        if (rank != 0) {
//            preSibling = this.children.get(rank - 1);
//        }
//        if (rank != keys.size()) {
//            nextSibling = this.children.get(rank + 1);
//        }
//
//        // rotate right
//        if (preSibling != null && preSibling.keys.size() >= degree) {
//            rotateRight(preSibling, nodeContainsKey);
//        }
//        // rotate left
//        else if (nextSibling != null && nextSibling.keys.size() >= degree) {
//            rotateLeft(nextSibling, nodeContainsKey);
//        }
//        // merge
//        else {
//            if (preSibling != null) {
//                mergeTwoAdjacentChildren(preSibling, nodeContainsKey);
//            } else if (nextSibling != null) {
//                mergeTwoAdjacentChildren(nodeContainsKey, nextSibling);
//            } else {
//                System.out.println("error");
//            }
//        }
//    }
//
//    private TreeNode mergeTwoAdjacentChildren(TreeNode left, TreeNode right) {
//        TreeNode node = new TreeNode(degree);
//
//        node.isLeaf = left.isLeaf;
//
//        node.children.addAll(left.children);
//        node.children.addAll(right.children);
//
//        int middleKey = this.keys.get(this.children.indexOf(left));
//
//        node.keys.addAll(left.keys);
//        node.keys.add(middleKey);
//        node.keys.addAll(right.keys);
//
//        this.keys.remove(this.children.indexOf(left));
//        this.children.remove(left);
//        this.children.remove(right);
//        this.children.add(node);
//
//        return node;
//    }
//
//    private void rotateLeft(TreeNode nextChild, TreeNode nodeContainsKey) {
//        int indexOfMiddleKey = this.children.indexOf(nodeContainsKey);
//        int middleKey = this.keys.get(indexOfMiddleKey);
//
//        nodeContainsKey.keys.add(middleKey);
//        this.keys.remove(indexOfMiddleKey);
//
//        this.keys.add(nextChild.keys.get(0));
//        Collections.sort(this.keys);
//        nextChild.keys.remove(0);
//
//        if (!nextChild.isLeaf) {
//            nodeContainsKey.children.add(nextChild.children.get(0));
//            nextChild.children.remove(0);
//        }
//    }
//
//    private void rotateRight(TreeNode preChild, TreeNode nodeContainsKey) {
//        int indexOfMiddleKey = this.children.indexOf(preChild);
//        int middleKey = this.keys.get(indexOfMiddleKey);
//
//        nodeContainsKey.keys.add(middleKey);
//        Collections.sort(nodeContainsKey.keys);
//        this.keys.remove(indexOfMiddleKey);
//
//        this.keys.add(preChild.keys.get(preChild.keys.size() - 1));
//        Collections.sort(this.keys);
//        preChild.keys.remove(preChild.keys.size() - 1);
//
//        if (!preChild.isLeaf) {
//            nodeContainsKey.children.add(preChild.children.get(preChild.children.size() - 1));
//            preChild.children.remove(preChild.children.size() - 1);
//        }
//    }
//
//
//    @Override
//    public String toString() {
//        StringBuilder builder = new StringBuilder();
//        Queue<TreeNode> visited = new LinkedList<>();
//        Queue<TreeNode> row = new LinkedList<>();
//
//        row.add(this);
//        visited.add(this);
//        while (!visited.isEmpty()) {
//            while (!row.isEmpty()) {
//                TreeNode node = row.poll();
//                builder.append(node.keys.toString());
//                builder.append("  ");
//            }
//            builder.append("\n");
//
//            for (TreeNode node : visited) {
//                row.addAll(node.children);
//            }
//            visited.clear();
//            visited.addAll(row);
//        }
//
//        return builder.toString();
//    }
//
//    @Override
//    public int compareTo(Object o) {
//        return keys.get(0) - ((TreeNode) o).keys.get(0);
//    }
//}
