package bstmap;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * 本次实验犯过的最大的错误就是各种变量名太混乱了，搞得最后什么东西都不是什么东西
 * @param <K>
 * @param <V>
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>{
    private int size = 0;
    private Node entry;
    private HashSet<K> hashSet = new HashSet<>();

    @Override
    public void clear() {
        entry = null;
        hashSet.clear();
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
       if (entry == null) {
           return false;
       }
       return entry.get(key) != null;
    }

    @Override
    public V get(K key) {
        if (entry == null) {
            return null;
        }
        Node lookup = entry.get(key);
        return lookup == null ? null : lookup.value;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * 这个方法真牛逼。完全不需要内部的insert来完成这个数据结构，而是使用一个辅助方法来完成这种结构。
     * @param key
     * @param value
     */
    @Override
    public void put(K key, V value) {
        entry = put(key, value, entry);
    }

    /**
     * 这里使用private方法的原因是方便递归。
     * 感觉这里的方法写的非常高级
     * @param key
     * @param value
     * @param node
     * @return
     */
    private Node put(K key, V value, Node node) {
        if(node == null) {
            size += 1;
            hashSet.add(key);
            return new Node(key, value);
        }
        int lookupIndex = key.compareTo(node.key);
        if (lookupIndex == 0) {
            node.value = value;
        } else if (lookupIndex < 0) {
            node.left = put(key, value, node.left);
        } else {
            node.right = put(key, value, node.right);
        }
        return node;
    }

    /**
     * 何苦每次都要找到这个节点再进行操作
     * 他妈的 直接就对他进行操作不就好了
     * 边找边操作
     * @return
     */

    @Override
    public Set<K> keySet() {
        return hashSet;
    }

    /**
     * 最蠢的方法，先查找然后再remove，这样才能得到二叉树的那个节点。
     * 使用节点递归请注意最重要的一步就是把节点返回。
     * 如 entry = Remove（entry, key）
     * @param key
     * @return
     */
    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("calls delete() with a null key");
        }
        Node p = entry.get(key);
        if (p == null) {
            throw new IllegalArgumentException("can not find this key");
        }
        entry = remove(key, entry);
        size --;
        return p.value;
    }

    /**
     * 他的方法太过于聪明了。
     * 他妈的
     * 其实还是一个递归算法
     *
     * 后面的删除的先后顺序也至关重要
     */
    private Node remove(K key, Node node) {
        if (node == null) {
            return null;
        }
        int compareInt = key.compareTo(node.key);
        if (compareInt < 0) {
            node.left = remove(key, node.left);
        } else if (compareInt > 0) {
            node.right = remove(key, node.right);
        } else {
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            Node t = node;
            node = findRightestNode(node.left);
            node.left = removeLargest(t.left);
            node.right = t.right;
        }
        return node;
    }

    /**
     * 关注父亲节点能够更好得理清结构
     * @param p
     * @return
     */
    private Node removeLargest(Node p) {
        if (p.right == null) {
            return p.left;
        }
            p.right = removeLargest(p.right);
        return p;
    }

    /**
     * Remove方法的辅助方法。首先得找到左子书的最大节点，就是最右边的节点
     * 看看怎么做
     * 使用递归，不断找到右节点。当右节点为null的时候就代表这个节点就是最右边的节点了
     */
    private Node findRightestNode(Node node) {
        if (node == null) {
            return null;
        }
        if (node.right == null) {
            return node;
        }
        return findRightestNode(node.right);
    }

    @Override
    public V remove(K key, V value) {
        Node p = entry.get(key);
        if (p == null) {
            throw new IllegalArgumentException("can not find the key");
        }
        if (p.value != value) {
            throw new IllegalArgumentException("the key mismatch the value");
        }
        entry = remove(key, entry);
        return value;
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }


    public void printInOrder() {
        printInOrder(entry);
    }

    private void printInOrder(Node tree) {
        if (tree == null) {
            return;
        }
        printInOrder(tree.left);
        System.out.println(tree.value);
        printInOrder(tree.right);
    }

    private class Node {
        private K key;
        private V value;
        private Node left, right;

        public Node(K k, V v) {
            key = k;
            value = v;
        }

        /**
         * 真的以后处理构造变量的时候，如果是本地的var都是用this.key代替好了。
         * 虽然这样写不美观，但是真的可以避免错误。
         * @param k
         * @return
         */
        public Node get(K k) {
            int whereToFind = k.compareTo(key);
            if(whereToFind == 0) {
                return this;
            } else if(whereToFind < 0 && left != null) {
                return left.get(k);
            } else if(whereToFind > 0 && right != null) {
                return right.get(k);
            }
            return null;
        }
    }
}
