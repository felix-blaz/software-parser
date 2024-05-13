class TreeNode {
    String label;
    TreeNode[] children;

    TreeNode(String label, TreeNode... children) {
        this.label = label;
        this.children = children;
    }

    String printTree(String indent, boolean isLast) {
        System.out.print(indent);
        if (isLast) {
            System.out.print("└─");
            indent += "  ";
        } else {
            System.out.print("├─");
            indent += "│ ";
        }
        System.out.println(label);

        for (int i = 0; i < children.length - 1; i++) {
            children[i].printTree(indent, false);
        }
        if (children.length > 0) {
            children[children.length - 1].printTree(indent, true);
        }
        return indent;
    }
}