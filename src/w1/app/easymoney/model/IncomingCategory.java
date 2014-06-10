package w1.app.easymoney.model;

import android.provider.DocumentsContract;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by el17 on 6/6/2014.
 */
public class IncomingCategory extends Node {
    public static final String CODE_INCOMING_CATEGORY = "IC";

    public static IncomingCategory valueOf(Node node) throws Exception {
        if (!node.isChildOf(IncomingCategory.CODE_INCOMING_CATEGORY)){
            return null;
        }

        IncomingCategory ic = new IncomingCategory();
        ic.mEditUserID = node.mEditUserID;
        ic.mName = node.mName;
        ic.mEditDate = node.mEditDate;
        ic.mCode = node.mCode;
        ic.mChildren = node.mChildren;
        ic.mParent = node.mParent;
        ic.mDescription = node.mDescription;
        ic.mID = node.mID;
        ic.mInDate = node.mInDate;
        ic.mLevel = node.mLevel;
        ic.mInUserID = node.mInUserID;
        ic.mParentID = node.mParentID;

        if (ic.mChildren == null){
            ic.mChildren = new ArrayList<Node>(8);
        }

        return ic;
    }

    public static List<IncomingCategory> getAll() throws Exception {
        Node root = Node.buildByCode(CODE_INCOMING_CATEGORY);
        if (root == null) {
            return null;
        }

        List<Node> nodeList = root.getChildren();
        if (nodeList == null || nodeList.size() < 1) {
            return null;
        }

        List<IncomingCategory> icList = new ArrayList<IncomingCategory>(nodeList.size());
        for (int i = 0; i < nodeList.size(); i++) {
            icList.add(valueOf(nodeList.get(i)));
        }

        //todo

        return icList;
    }

    private static List<IncomingCategory> CACHE;
    public static List<IncomingCategory> getCache() throws Exception {
        if (CACHE == null){
            CACHE = getAll();
        }

        return CACHE;
    }

    public static IncomingCategory get(int id) throws Exception {
        Node node = Node.get(id);
        if (node == null){
            return null;
        }

        return valueOf(node);
    }

    private IncomingCategory(){

    }

    private static Node ROOT;
    public static Node getRoot() throws ParseException {
        if (ROOT == null){
            ROOT = Node.getByCode(CODE_INCOMING_CATEGORY);
        }

        return ROOT;
    }

    public static IncomingCategory create() throws ParseException {
        IncomingCategory ic = new IncomingCategory();
        ic.mParent = getRoot();
        ic.mParentID = ic.mParent.getID();
        ic.mChildren = new ArrayList<Node>(8);

        return ic;
    }


    public IncomingCategory createSub() throws Exception {
        if (mID < 1){
            throw new Exception("Id is null.");
        }

        IncomingCategory ic = new IncomingCategory();
        ic.mParent = this;
        ic.mParentID = this.mID;
        ic.mChildren = new ArrayList<Node>(8);

        this.mChildren.add(ic);

        return ic;
    }

    @Override
    public void save() throws Exception {
        super.save();

        CACHE = null;
    }

    @Override
    public void delete() throws Exception {
        super.delete();

        CACHE = null;

    }
}
