package w1.app.easymoney.model;

import java.text.ParseException;

/**
 * Created by el17 on 5/2/2014.
 */
public class OutgoingCategory extends Node {
    public static final String CODE_OUTGOING_CATEGORY = "OC";

    public static OutgoingCategory valueOf(Node node) throws Exception {
        if (!node.isChildOf(OutgoingCategory.CODE_OUTGOING_CATEGORY)){
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

    private OutgoingCategory(){

    }
}
