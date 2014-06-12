package w1.app.easymoney.model;

import w1.app.easymoney.data.TN_RelationDBH;
import w1.app.easymoney.entity.TN_Relation;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by el17 on 6/6/2014.
 */
public class Incoming extends Transaction {
    private Incoming(){

    }

    private HashMap<String,TN_Relation> mMap;
    public static Incoming create(){
        Incoming in = new Incoming();
        in.mTN_Relation = new ArrayList<TN_Relation>(3);
        in.mMap = new HashMap<String, TN_Relation>(3);

        return in;
    }

    public static Incoming valueOf(Transaction tran) throws Exception {
        if (tran.mTN_Relation == null || tran.mTN_Relation.size() < 1){
            tran.mTN_Relation = TN_RelationDBH.getByTranID(tran.getID());
        }
        if (tran.mTN_Relation == null || tran.mTN_Relation.size() < 1){
            return null;
        }

        Incoming in = new Incoming();
        in.mTN_Relation = tran.mTN_Relation;
        in.mMap = new HashMap<String, TN_Relation>(3);

        for(int i = 0; i < in.mTN_Relation.size(); i ++){
            Node node = Node.get(in.mTN_Relation.get(i).getNodeID() );

            if (in.mIC == null){
                IncomingCategory ic = IncomingCategory.valueOf(node);
                if (ic != null){
                    in.mIC = ic;
                    in.mMap.put(IncomingCategory.CODE_INCOMING_CATEGORY, in.mTN_Relation.get(i));

                    continue;
                }
            }

            if (in.mAccount == null){
               Account account = Account.valueOf(node);
                if (account != null){
                    in.mAccount = account;
                    in.mMap.put(Account.CODE_ACCOUNT, in.mTN_Relation.get(i));

                    continue;
                }
            }

            if (in.mMember == null){
                Member member = Member.valueOf(node);
                if (member != null){
                    in.mMember = member;
                    in.mMap.put(Member.CODE_MEMBER, in.mTN_Relation.get(i));

                    continue;
                }
            }

            if (in.mIC == null){
                return null;
            }

            in.mAmount = tran.mAmount;
            in.mComment = tran.mComment;
            in.mEditDate = tran.mEditDate;
            in.mEditUserID = tran.mEditUserID;
            in.mID = tran.mID;
            in.mTranDate = tran.mTranDate;
            in.mInDate = tran.mInDate;
            in.mInUserID = tran.mInUserID;
        }

        return in;
    }

    private IncomingCategory mIC;
    public IncomingCategory getIC(){
        return mIC;
    }
    public void setIC(IncomingCategory ic) throws Exception {
        if (ic == null){
            throw new Exception("Incoming category can't be null.");
        }else {
            this.setNode(ic);
        }
    }

    private Account mAccount;
    public Account getAccount(){
        return mAccount;
    }
    public void setAccount(Account account) throws Exception {
        if (account == null){
           if (mAccount != null){
               TN_Relation r = mMap.get(Account.CODE_ACCOUNT);
               mTN_Relation.remove(r);
               mMap.remove(Account.CODE_ACCOUNT);
               mAccount = null;
           }
        }else {
            this.setNode(account);
        }
    }

    private Member mMember;
    public Member getMember(){
        return mMember;
    }
    public void setMember(Member member) throws Exception {
        if (member == null){
            if (mMember != null){
                TN_Relation r = mMap.get(Member.CODE_MEMBER);
                mTN_Relation.remove(r);
                mMap.remove(Member.CODE_MEMBER);
                mMember = null;
            }
        }else {
            this.setNode(member);
        }

    }

    private void setNode(Node node) throws Exception {
        if (node == null){
            throw new Exception("Node can't be null.");
        }

        String code;
        if (node instanceof IncomingCategory){
            this.mIC = (IncomingCategory)node;
            code = IncomingCategory.CODE_INCOMING_CATEGORY;
        }else if (node instanceof Account) {
            this.mAccount = (Account)node;
            code = Account.CODE_ACCOUNT;
        }else if(node instanceof  Member){
            this.mMember = (Member)node;
            code = Member.CODE_MEMBER;
        }else{
            throw new Exception("Invalid node.");
        }

        TN_Relation r = mMap.get(code);
        if (r == null){
            r = new TN_Relation();
            this.mTN_Relation.add(r);
            this.mMap.put(code, r);
        }
        r.setNodeID(node.getID());
    }

//    private Account mFromAccount;
//    public Account getFromAccount(){
//        return mFromAccount;
//    }
//    public void setFromAccount(Account from){
//        //todo
//    }
//
//    private Account mToAccount;
//    public Account getToAccount(){
//        return mToAccount;
//    }
//    public void setToAccount(){
//        //todo
//    }

    private void refresh(){
       TN_Relation r = mMap.get(IncomingCategory.CODE_INCOMING_CATEGORY);
        r.setAmount(mAmount);

        if (mAccount != null) {
            r = mMap.get(Account.CODE_ACCOUNT);
            if (mAccount.getType().equals(Account.CODE_ACCOUNT_DEBIT)){
                r.setAmount(mAmount);
            }else {
                r.setAmount(-mAmount);
            }
        }

        if (mMember != null){
            r = mMap.get(Member.CODE_MEMBER);
            r.setAmount(mAmount);
        }
    }
    @Override
    public void  save() throws Exception {
        if (mIC == null){
            throw new Exception("Incoming category can't be null");
        }

        if (mAmount < 0){
            throw new Exception("Amount can't be less than 0.");
        }

        if (mAccount == null){
            throw new Exception("Account can't be null.");
        }

        this.refresh();
        super.save();
    }

    @Override
    public void delete() throws SQLException {
        super.delete();
    }
}
