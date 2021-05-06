package node;

import java.util.Arrays;
import java.util.List;


public class BNode {

    int degree = 0;         // 树的最小度数

    List<Integer> keys;     // 关键字集合

    List<String> data;          // 数据,与关键字通过索引一一对应

    List<BNode> children;       // 子节点集合

    BNode(int degree) {

    }

    /**
     * 分割子节点回新的子树
     *
     * @param child 需要分割的满子节点
     * @return 生成的子树
     */
    BNode splitChild(BNode child) {
        //TODO

        return null;
    }

    /**
     * 插入数据
     *
     * @param newKey 新插入的键值
     * @param data   数据
     * @return 新的根节点
     */
    BNode insert(int newKey, String data) {
        //TODO

        return null;
    }

    /**
     * 以键搜索对应的数据
     * @param key 特定的键
     * @return 键对应的数据, 键不存在则返回 null
     */
    String research(int key) {
        //TODO

        return null;
    }

    /**
     * 在树中删除某个键和对应的数据
     * @return 新的根节点
     */
    BNode delete(int key) {
        //TODO

        return null;
    }

    @Override
    public String toString() {
        //TODO

        return null;
    }
}
