package w1.app.easymoney.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by el17 on 5/2/2014.
 */
public class OutgoingCategory extends Node {
    public static final String CODE_OUTGOING_CATEGORY = "OC";

    public static OutgoingCategory valueOf(Node node) throws Exception {
        if (!node.isChildOf(OutgoingCategory.CODE_OUTGOING_CATEGORY)) {
            return null;
        }

        OutgoingCategory oc = new OutgoingCategory();
        oc.mEditUserID = node.mEditUserID;
        oc.mName = node.mName;
        oc.mEditDate = node.mEditDate;
        oc.mCode = node.mCode;
        oc.mChildren = node.mChildren;
        oc.mParent = node.mParent;
        oc.mDescription = node.mDescription;
        oc.mID = node.mID;
        oc.mInDate = node.mInDate;
        oc.mLevel = node.mLevel;
        oc.mInUserID = node.mInUserID;
        oc.mParentID = node.mParentID;

        return oc;

    }

    public static List<OutgoingCategory> getAll() throws Exception {
        Node root = Node.buildByCode(CODE_OUTGOING_CATEGORY);
        if (root == null) {
            return null;
        }

        List<Node> nodeList = root.getChildren();
        if (nodeList == null || nodeList.size() < 1) {
            return null;
        }

        List<OutgoingCategory> ogList = new ArrayList<OutgoingCategory>(nodeList.size());
        for (int i = 0; i < nodeList.size(); i++) {
            ogList.add(valueOf(nodeList.get(i)));
        }

        return ogList;
    }

    private static List<OutgoingCategory> CACHE;

    public static List<OutgoingCategory> getCache() throws Exception {
        if (CACHE == null) {
            CACHE = getAll();
        }

        return CACHE;
    }

    private OutgoingCategory() {

    }

    public OutgoingCategory(int parentID) throws Exception {

        mParentID = parentID;
    }

    @Override
    public void save() throws Exception {
        Node p = Node.get(mParentID);
        if (p == null || !p.isChildOf(CODE_OUTGOING_CATEGORY)) {
            throw new Exception("Parent id is not valid.");
        }

        mParent = p;

        super.save();

        CACHE = null;
    }

    @Override
    public void delete() throws Exception {
        super.delete();

        CACHE = null;
    }
}
