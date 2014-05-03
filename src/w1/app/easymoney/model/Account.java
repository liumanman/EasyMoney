package w1.app.easymoney.model;

import java.sql.SQLException;
import java.text.ParseException;

public class Account extends Node {
    public static final String CODE_ACCOUNT = "ACCT";
    public static final String CODE_ACOUNT_DEBIT = "DEBIT";
    public static final String CODE_ACOUNT_CREDIT = "CREDIT";

    public static final String TYPE_CREDIT = "C";
    public static final String TYPE_DEBIT = "D";
    private String mType;

    public Account(String type) throws Exception {
        Node node;
        if (type == Account.TYPE_CREDIT){
            node = this.getByCode(Account.CODE_ACOUNT_CREDIT);
        }else{
            if (type == Account.TYPE_DEBIT){
                node = this.getByCode(Account.CODE_ACOUNT_DEBIT);
            }else{
                throw new Exception("Invalid type.");
            }
        }

        mParentID = node.getID();
        mParent = node;

        this.mType = type;

    }
    private Account(){

    }

    public static Account valueOf(Node node) throws Exception {
        Account a = new Account();
        a.setParent(node.getParent());
        a.setLevel(node.getLevel());
        a.setCode(node.getCode());
        a.setInDate(node.getInDate());
        a.setDescription(node.getDescription());
        a.setEditDate(node.getEditDate());
        a.setEditUserID(node.getEditUserID());
        a.setID(node.getID());
        a.setName(node.getName());

        a.mParent = node.mParent;
        a.mLevel = node.mLevel;
        a.mParentID = node.mParentID;
        a.mID = node.mID;
        a.mChildren = null;
        a.mCode = node.mCode;
        a.mDescription = node.mDescription;
        a.mEditDate = node.mEditDate;
        a.mEditUserID = node.mEditUserID;
        a.mInDate = node.mInDate;
        a.mInUserID = node.mInUserID;
        a.mName = node.mName;

        if (a.isChildOf(Account.CODE_ACOUNT_CREDIT)){
            a.mType = Account.TYPE_CREDIT;
        }else{
            if (a.isChildOf(Account.CODE_ACOUNT_DEBIT)){
                a.mType = Account.TYPE_DEBIT;
            }else{
                throw new Exception("Can't decide account type.");
            }
        }


        return a;
    }

    public String getType() {
        return mType;
    }

//    public void save() throws ParseException, SQLException {
//        super.save();
//
//    }

}
