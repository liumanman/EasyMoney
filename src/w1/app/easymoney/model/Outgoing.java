package w1.app.easymoney.model;

import w1.app.easymoney.data.TN_RelationDBH;
import w1.app.easymoney.entity.TN_Relation;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by el17 on 5/2/2014.
 */
public class Outgoing extends Transaction {
    private Outgoing() {

    }

    public static Outgoing create(){
        Outgoing outgoing = new Outgoing();

        outgoing.mTN_Relation = new ArrayList<TN_Relation>(5);
        outgoing.mMap = new HashMap<String, TN_Relation>(5);

        return outgoing;
    }

    public static Outgoing get(int id) throws Exception {
        Transaction tran = Transaction.get(id);
        if (tran == null) {
            return null;
        }

        return valueOf(tran);
    }

    public static Outgoing valueOf(Transaction tran) throws Exception {
        if (tran.mTN_Relation == null) {
            tran.mTN_Relation = TN_RelationDBH.getByTranID(tran.getID());
        }

        if(tran.mTN_Relation == null || tran.mTN_Relation.size() < 1){
            return null;
        }

        Outgoing og = new Outgoing();
        og.mTN_Relation = tran.mTN_Relation;
        og.mMap = new HashMap<String, TN_Relation>(5);

        for (int i = 0; i < og.mTN_Relation.size(); i++) {
            Node node = Node.get(og.mTN_Relation.get(i).getNodeID());

            if (og.mAccount == null) {
                Account account = Account.valueOf(node);
                if (account != null) {
                    og.mAccount = account;
                    og.mMap.put(Account.CODE_ACCOUNT, og.mTN_Relation.get(i));

                    continue;
                }
            }

            if (og.mOC == null) {
                OutgoingCategory oc = OutgoingCategory.valueOf(node);
                if (oc != null) {
                    og.mOC = oc;
                    og.mMap.put(OutgoingCategory.CODE_OUTGOING_CATEGORY, og.mTN_Relation.get(i));
                    continue;
                }
            }

            if (og.mMember == null) {
                Member member = Member.valueOf(node);
                if (member != null) {
                    og.mMember = member;
                    og.mMap.put(Member.CODE_MEMBER, og.mTN_Relation.get(i));

                    continue;
                }

            }

            if (og.mProject == null) {
                Project project = Project.valueOf(node);
                if (project != null) {
                    og.mProject = project;
                    og.mMap.put(Project.CODE_PROJECT, og.mTN_Relation.get(i));

                    continue;
                }
            }

            if (og.mMerchant == null) {
                Merchant merchant = Merchant.valueOf(node);
                if (merchant != null) {
                    og.mMerchant = merchant;
                    og.mMap.put(Merchant.CODE_MERCHANT, og.mTN_Relation.get(i));

                    continue;
                }
            }
        }

        if (og.mOC == null){
            return null;
        }

        og.mAmount = tran.mAmount;
        og.mComment = tran.mComment;
        og.mEditDate = tran.mEditDate;
        og.mEditUserID = tran.mEditUserID;
        og.mID = tran.mID;
        og.mTranDate = tran.mTranDate;
        og.mInDate = tran.mInDate;
        og.mInUserID = tran.mInUserID;

        return og;


    }


    //Getter and Setter
    private OutgoingCategory mOC;
    public OutgoingCategory getOC() {
        return mOC;
    }
    public void setOC(OutgoingCategory oc) throws Exception {
        if (oc == null) {
            throw new Exception("Outgoing Category can't be null.");
        }

//        if (!oc.isChildOf(OutgoingCategory.CODE_OUTGOING_CATEGORY)) {
//            throw new Exception("The node is not outgoing category.");
//        }

        this.setNode(oc);
    }

    private Account mAccount;
    public Account getAccount() {
        return mAccount;
    }
    public void setAccount(Account account) throws Exception {
        if (account == null) {
            if (mAccount != null) {
                TN_Relation r = mMap.get(Account.CODE_ACCOUNT);
                mMap.remove(Account.CODE_ACCOUNT);
                super.mTN_Relation.remove(r);
                mAccount = null;
            }
        } else {
            this.setNode(account);
        }
    }

    private Member mMember;
    public Member getMemeber() {
        return mMember;
    }
    public void setMember(Member member) throws Exception{
        if (member == null) {
            if (mMember != null) {
                TN_Relation r = mMap.get(Member.CODE_MEMBER);
                mMap.remove(Member.CODE_MEMBER);
                super.mTN_Relation.remove(r);
                mMember = null;
            }
        } else {
            this.setNode(member);
        }
    }

    private Project mProject;
    public Project getProject() {
        return mProject;
    }
    public void setProject(Project project) throws Exception {
        if (project == null) {
            if (mProject != null) {
                TN_Relation r = mMap.get(Project.CODE_PROJECT);
                mMap.remove(Project.CODE_PROJECT);
                super.mTN_Relation.remove(r);
                mProject = null;
            }
        } else {
            this.setNode(project);
        }
    }

    private Merchant mMerchant;
    public Merchant getMerchant() {
        return mMerchant;
    }
    public void setMerchant(Merchant merchant) throws Exception {
        if (merchant == null) {
            if (mMerchant != null) {
                TN_Relation r = mMap.get(Merchant.CODE_MERCHANT);
                mMap.remove(Merchant.CODE_MERCHANT);
                super.mTN_Relation.remove(r);
                mMerchant = null;
            }
        } else {
            this.setNode(merchant);
        }
    }

    private HashMap<String, TN_Relation> mMap;

    private void setNode(Node node) throws Exception {
        if (node == null) {
            throw new Exception("Node can't be null.");
        }
        String code;

        if (node instanceof OutgoingCategory) {
            mOC = (OutgoingCategory) node;
            code = OutgoingCategory.CODE_OUTGOING_CATEGORY;

        } else if (node instanceof Account) {
            mAccount = (Account) node;
            code = Account.CODE_ACCOUNT;
        } else if (node instanceof Member) {
            mMember = (Member) node;
            code = Member.CODE_MEMBER;
        } else if (node instanceof Project) {
            mProject = (Project) node;
            code = Project.CODE_PROJECT;
        } else if (node instanceof Merchant) {
            mMerchant = (Merchant) node;
            code = Merchant.CODE_MERCHANT;
        } else {
            throw new Exception("Invalid node");
        }

        TN_Relation r = mMap.get(code);
        if (r == null) {
            r = new TN_Relation();
            mTN_Relation.add(r);
            mMap.put(code, r);
        }
        r.setNodeID(node.getID());
    }
    public void save() throws Exception {
        if (mOC == null){
            throw new Exception("Outgoing category can't be null");
        }

        if (mAmount < 0){
            throw new Exception("Amount can't be less than 0.");
        }

        this.refresh();

        super.save();
    }
    private void refresh(){
        TN_Relation r = mMap.get(OutgoingCategory.CODE_OUTGOING_CATEGORY);
        r.setAmount(mAmount);

        r = mMap.get(Account.CODE_ACCOUNT);
//        if (mAccount.getType().equals(Account.CODE_ACCOUNT_CREDIT)){
//            r.setAmount(mAmount);
//        }else {
//            r.setAmount(-mAmount);
//        }
        r.setAmount(mAmount);

        r = mMap.get(Member.CODE_MEMBER);
        if (r != null){
            r.setAmount(mAmount);
        }

        r = mMap.get(Merchant.CODE_MERCHANT);
        if (r != null){
            r.setAmount(getAmount());
        }

        r = mMap.get(Project.CODE_PROJECT);
        if (r != null){
            r.setAmount(mAmount);
        }
    }

}
