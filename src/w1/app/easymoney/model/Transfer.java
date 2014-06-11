package w1.app.easymoney.model;

import w1.app.easymoney.data.TN_RelationDBH;
import w1.app.easymoney.entity.TN_Relation;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by el17 on 6/11/2014.
 */
public class Transfer extends Transaction {
    public static final String CODE_TRANSFER = "TRAN";
    private static final String CODE_FROM_MEMBER = "FM";
    private static final String CODE_TO_MEMBER = "TM";
    private static final String CODE_FROM_ACCOUNT = "FA";
    private static final String CODE_TO_ACCOUNT = "TA";

    private HashMap<String, TN_Relation> mMap;
    private Transfer(){

    }

    public static Transfer valueOf(Transaction tran) throws Exception {
        if (tran.mTN_Relation == null || tran.mTN_Relation.size() < 1){
            tran.mTN_Relation = TN_RelationDBH.getByTranID(tran.getID());
        }

        if (tran.mTN_Relation == null || tran.mTN_Relation.size() < 1){
            return null;
        }

        Transfer t = new Transfer();
        t.mTN_Relation = tran.mTN_Relation;
        t.mMap = new HashMap<String, TN_Relation>(5);

        for ( int i = 0; i < t.mTN_Relation.size(); i ++){
            Node node = Node.get(t.mTN_Relation.get(i).getNodeID());

            if (t.mFromAccount == null || t.mToAccount == null){
                Account a = Account.valueOf(node);
                if (a != null){
                    if (a.getType().equals(Account.TYPE_DEBIT)) {
                        if (t.mTN_Relation.get(i).getAmount() < 0) {
                            t.mFromAccount = a;
                            t.mMap.put(CODE_FROM_ACCOUNT, t.mTN_Relation.get(i));
                        } else {
                            t.mToAccount = a;
                            t.mMap.put(CODE_TO_ACCOUNT, t.mTN_Relation.get(i));
                        }
                    }else{
                        if (t.mTN_Relation.get(i).getAmount() > 0) {
                            t.mFromAccount = a;
                            t.mMap.put(CODE_FROM_ACCOUNT, t.mTN_Relation.get(i));
                        } else {
                            t.mToAccount = a;
                            t.mMap.put(CODE_TO_ACCOUNT, t.mTN_Relation.get(i));
                        }
                    }

                    continue;
                }
            }

            if (t.mFromMember == null || t.mToMember == null){
                Member m = Member.valueOf(node);
                if (m != null){
                    if (t.mTN_Relation.get(i).getAmount() < 0){
                        t.mFromMember = m;
                        t.mMap.put(CODE_FROM_MEMBER, t.mTN_Relation.get(i));
                    }else{
                        t.mToMember = m;
                        t.mMap.put(CODE_TO_MEMBER, t.mTN_Relation.get(i));
                    }

                    continue;
                }
            }

            if (t.mTN_Relation.get(i).getNodeID() != getRoot().getID()){
                return null;
            }
        }

        t.mAmount = tran.mAmount;
        t.mComment = tran.mComment;
        t.mEditDate = tran.mEditDate;
        t.mEditUserID = tran.mEditUserID;
        t.mID = tran.mID;
        t.mTranDate = tran.mTranDate;
        t.mInDate = tran.mInDate;
        t.mInUserID = tran.mInUserID;

        return t;
    }

    public static Transfer get(int id) throws Exception {
        Transaction tran = Transaction.get(id);
        if (tran == null){
            return null;
        }else {
            return valueOf(tran);
        }
    }

    public static Transfer create() throws ParseException {
        Transfer t = new Transfer();
        t.mTN_Relation = new ArrayList<TN_Relation>(5);
        t.mMap = new HashMap<String, TN_Relation>(4);

        TN_Relation r = new TN_Relation();
        r.setNodeID(getRoot().getID());
        t.mTN_Relation.add(r);

        return t;
    }

    private static Node ROOT;
    public static Node getRoot() throws ParseException {
        if (ROOT == null){
            ROOT = Node.getByCode(CODE_TRANSFER);
        }

        return ROOT;
    }

    private Member mFromMember;
    public Member getFromMember(){
        return mFromMember;
    }
    public void setFromMember(Member from){
        if (from == null){
            if (mFromMember != null){
                TN_Relation r = mMap.get(CODE_FROM_MEMBER);
                mTN_Relation.remove(r);
                mMap.remove(CODE_FROM_MEMBER);
                mFromMember = null;
            }
        }else {
            TN_Relation r = mMap.get(CODE_FROM_MEMBER);
            if (r == null){
                r = new TN_Relation();
                mTN_Relation.add(r);
            }
            r.setNodeID(from.getID());
            mFromMember = from;
        }
    }

    private Member mToMember;
    public Member getToMember(){
        return mToMember;
    }
    public void setToMember(Member to){
        if (to == null){
            if (mToMember != null){
                TN_Relation r = mMap.get(CODE_TO_MEMBER);
                mTN_Relation.remove(r);
                mMap.remove(CODE_TO_MEMBER);
                mToMember = null;
            }
        }else {
            TN_Relation r = mMap.get(CODE_TO_MEMBER);
            if (r == null){
                r = new TN_Relation();
                mTN_Relation.add(r);
            }
            r.setNodeID(to.getID());
            mToMember = to;
        }
    }

    private Account mFromAccount;
    public Account getFromAccount(){
        return mFromAccount;
    }
    public void setFromAccount(Account from){
        if (from == null){
            if (mFromAccount != null){
                TN_Relation r = mMap.get(CODE_FROM_ACCOUNT);
                mTN_Relation.remove(r);
                mMap.remove(CODE_FROM_ACCOUNT);
                mFromAccount = null;
            }
        }else {
            TN_Relation r = mMap.get(CODE_FROM_ACCOUNT);
            if (r == null){
                r = new TN_Relation();
                mTN_Relation.add(r);
            }
            r.setNodeID(from.getID());
            mFromAccount = from;
        }

    }

    private Account mToAccount;
    public Account getToAccount(){
        return mToAccount;
    }
    public void setToAccount(Account to){
        if (to == null){
            if (mToAccount != null){
                TN_Relation r = mMap.get(CODE_TO_ACCOUNT);
                mTN_Relation.remove(r);
                mMap.remove(CODE_TO_ACCOUNT);
                mToAccount = null;
            }
        }else {
            TN_Relation r = mMap.get(CODE_TO_ACCOUNT);
            if (r == null){
                r = new TN_Relation();
                mTN_Relation.add(r);
            }
            r.setNodeID(to.getID());
            mToAccount = to;
        }
    }
}
