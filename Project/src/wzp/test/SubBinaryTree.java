package wzp.test;

/**
 * Created by WZP on 2016/4/21.
 */
class TNode {
    public TNode left;
    public TNode right;
    public int val;
}

public class SubBinaryTree {
    public static int isSubTree(TNode root1, TNode root2) {
        if (sub(root1, root2) == 1) return 1;
        if (isSubTree(root1.left, root2) == 1) return 1;
        if (isSubTree(root1.right, root2) == 1) return 1;
        return -1;
    }

    private static int sub(TNode root1, TNode root2) {
        if (root2 == null) return 1;
        if (root1 == null) return -1;
        if (root1.val != root2.val) return -1;
        if (sub(root1.left, root2.left) == -1) return -1;
        if (sub(root1.right, root2.right) == -1) return -1;
        return 1;
    }
}
