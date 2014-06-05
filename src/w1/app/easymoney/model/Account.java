package w1.app.easymoney.model;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Account extends Node {
    public static final String CODE_ACCOUNT = "ACCT";
    public static final String CODE_ACCOUNT_DEBIT = "DEBIT";
    public static final String CODE_ACCOUNT_CREDIT = "CREDIT";

    public static final String TYPE_CREDIT = "C";
    public static final String TYPE_DEBIT = "D";
    private String mType;

    public static Account get(int id) throws Exception {
        Node node = Node.get(id);
        if (node == null){
            return null;
        }

        return valueOf(node);
    }

    private static List<Account> CACHE;
    public static List<Account> getCache() throws Exception {
        if (CACHE == null){
            Node rootDebit = Node.buildByCode(CODE_ACCOUNT_DEBIT);
            if (rootDebit == null){
                throw new Exception("Can't find root of DEBIT");
            }

            Node rootCredit = Node.buildByCode(CODE_ACCOUNT_CREDIT);
            if (rootCredit == null){
                throw new Exception("Can't find root of CREDIT");
            }

            int c_d = 0;
            if (rootDebit.getChildren() != null && rootDebit.getChildren().size() > 0){
                c_d = rootDebit.getChildren().size();
            }

            int c_c = 0;
            if (rootCredit.getChildren() != null && rootCredit.getChildren().size() > 0){
                c_c = rootCredit.getChildren().size();
            }

            if (c_c+ c_d == 0){
                return null;
            }

            CACHE = new ArrayList<Account>(c_c + c_d);
            for(int i = 0; i < c_c; i ++){
                CACHE.add(valueOf(rootCredit.getChildren().get(i)));
            }

            for(int i = 0; i < c_d; i ++ ){
                CACHE.add(valueOf(rootDebit.getChildren().get(i)));
            }
        }

        return CACHE;
    }

    private static Node ROOT_CREDIT;
    public static Node getCreidtRoot() throws ParseException {
       if (ROOT_CREDIT == null){
          ROOT_CREDIT = Node.getByCode(Account.CODE_ACCOUNT_CREDIT);
       }

        return ROOT_CREDIT;
    }

    private static Node ROOT_DEBIT;
    public static Node getDebitRoot() throws ParseException {
        if (ROOT_DEBIT == null){
            ROOT_DEBIT = Node.getByCode(Account.CODE_ACCOUNT_DEBIT);
        }

        return ROOT_DEBIT;
    }

    public Account(String type) throws Exception {
        Node node;
        if (type == Account.TYPE_CREDIT){
            node = getCreidtRoot();
        }else{
            if (type == Account.TYPE_DEBIT){
                node = getDebitRoot();
            }else{
                throw new Exception("Invalid type.");
            }
        }

        if (node == null){
            throw new Exception("Can't find the root node of Account.");
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

        if (a.isChildOf(Account.CODE_ACCOUNT_CREDIT)){
            a.mType = Account.TYPE_CREDIT;
        }else{
            if (a.isChildOf(Account.CODE_ACCOUNT_DEBIT)){
                a.mType = Account.TYPE_DEBIT;
            }else{
               return null;
            }
        }


        return a;
    }

    public String getType() {
        return mType;
    }

    @Override
    public void save() throws Exception {
        if (!super.isChildOf(CODE_ACCOUNT)){
            throw new Exception("It's not an Account node.");
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
