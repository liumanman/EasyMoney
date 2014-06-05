package w1.app.easymoney.model;

import java.text.ParseException;
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

        //todo

        return ogList;
    }

    private static List<OutgoingCategory> CACHE;
    public static List<OutgoingCategory> getCache() throws Exception {
        if (CACHE == null) {
            CACHE = getAll();
        }

        return CACHE;
    }

    public static OutgoingCategory get(int id) throws Exception {
        Node node = Node.get(id);
        if (node == null){
            return null;
        }

        return valueOf(node);
    }
    private OutgoingCategory() {
        this.mChildren = new ArrayList<Node>(8);
    }

    private static Node ROOT;
    public static Node getRoot() throws ParseException {
        if (ROOT == null){
            ROOT = Node.getByCode(CODE_OUTGOING_CATEGORY);
        }

        return ROOT;
    }

    public static OutgoingCategory create() throws ParseException {
        OutgoingCategory oc = new OutgoingCategory();
        oc.mParent = getRoot();
        oc.mParentID = oc.mParent.getID();

        return oc;
    }

    public OutgoingCategory createSub() throws Exception {
        if (mID < 1){
            throw new Exception("Id is null.");
        }

        OutgoingCategory c = new OutgoingCategory();
        c.mParent = this;
        c.mParentID = this.mID;

        this.getChildren().add(c);

        return c;
    }

    @Override
    public void save() throws Exception {
        if(!super.isChildOf(CODE_OUTGOING_CATEGORY)){
            throw new Exception("It's not a OC node.");
        }

        super.save();

        CACHE = null;
    }

    @Override
    public void delete() throws Exception {
        super.delete();

        CACHE = null;
    }
}
