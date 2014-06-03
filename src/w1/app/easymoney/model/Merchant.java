package w1.app.easymoney.model;

import org.w3c.dom.NodeList;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by el17 on 5/2/2014.
 */
public class Merchant extends Node {
    public static final String CODE_MERCHANT = "MER";

    public static Merchant valueOf(Node node) throws ParseException {
        if (!node.isChildOf(CODE_MERCHANT)){
           return null;
        }

        Merchant merchant = new Merchant();
        merchant.mInUserID = node.mInUserID;
        merchant.mParentID = node.mParentID;
        merchant.mParent = node.mParent;
        merchant.mCode = node.mCode;
        merchant.mName = node.mName;
        merchant.mEditDate = node.mEditDate;
        merchant.mEditUserID = node.mEditUserID;
        merchant.mChildren = node.mChildren;
        merchant.mDescription = node.mDescription;
        merchant.mID = node.mID;
        merchant.mLevel = node.mLevel;

        return merchant;
    }

    public static List<Merchant> getAll() throws ParseException {
        Node root = Node.buildByCode(CODE_MERCHANT);
        if (root == null) {
            return null;
        }

        final List<Node> nodeList = root.getChildren();
        if (nodeList == null || nodeList.size() < 1){
            return null;
        }

        List<Merchant> merchantList = new ArrayList<Merchant>(nodeList.size());
        for(int i = 0; i < merchantList.size(); i ++){
            merchantList.add(valueOf(nodeList.get(i)));
        }

        return merchantList;

    }

    private static List<Merchant> CACHE;
    public static List<Merchant> getCACHE() throws ParseException {
        if (CACHE == null){
           CACHE = getAll();
        }

        return CACHE;
    }

    private static Node ROOT;
    public static Node getRoot() throws ParseException {
        if (ROOT == null){
            ROOT = Node.getByCode(CODE_MERCHANT);
        }

        return ROOT;
    }

    public Merchant() throws ParseException {
        super.mParent = getRoot();
        super.mParentID = getRoot().getID();
    }

    @Override
    public void save() throws Exception {
        if (!super.isChildOf(CODE_MERCHANT)){
            throw new Exception("It's not a Merchant node.");
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
