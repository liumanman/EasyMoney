package w1.app.easymoney.model;

import w1.app.easymoney.data.TN_RelationDBH;
import w1.app.easymoney.entity.TN_Relation;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by el17 on 5/2/2014.
 */
public class Outgoing extends Transaction {
    public Outgoing()  {

        super.mTN_Relation = new ArrayList<TN_Relation>(5);
    }

    public static Outgoing get(int id) throws Exception {
       Transaction tran = Transaction.get(id);
        if (tran == null){
            return null;
        }

        return get(tran);
    }

    public static Outgoing get(Transaction tran) throws Exception {
        if (tran.mTN_Relation == null){
            tran.mTN_Relation = TN_RelationDBH.getByTranID(tran.getID());
        }

        Outgoing og = new Outgoing();

        for(int i = 0; i < tran.mTN_Relation.size(); i ++){
            Node node = Node.get(tran.mTN_Relation.get(i).getNodeID());

            if (og.mAccount == null) {
                Account account = Account.valueOf(node);
                if (account != null) {
                    og.mAccount = account;
                    continue;
                }
            }

            if (og.mOC == null){
                OutgoingCategory oc = OutgoingCategory.valueOf(node);
                if (oc != null){
                    og.mOC = oc;
                    continue;
                }
            }




        }
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

        if (!oc.isChildOf(OutgoingCategory.CODE_OUTGOING_CATEGORY)) {
            throw new Exception("The node is not outgoing category.");
        }

        mOC = OutgoingCategory.getInstance(oc);
        TN_Relation r = new TN_Relation();
        r.setNodeID(mOC.getID());

        super.mTN_Relation.add(r);
    }

    private Account mAccount;
    public Account getAccount() {
        return mAccount;
    }
    public void setAccount(Account account) throws Exception {
        if (account == null) {
            throw new Exception("Account can't be null.");
        }

        if (!account.isChildOf(Account.CODE_ACCOUNT)) {
            throw new Exception("The node is not an Acount.");
        }

        mAccount = account;
        TN_Relation r = new TN_Relation();
        r.setNodeID(mAccount.getID());
        super.mTN_Relation.add(r);

    }

    private Member mMember;
    public Member getMemeber() {
        return mMember;
    }
    public void setMember(Member member) throws Exception {
        if (member == null) {
            throw new Exception("Member can't be null");
        }

        if (!member.isChildOf()) {
            throw new Exception("The node is not a Member.");
        }

        mMember = member;
        TN_Relation r = new TN_Relation();
        r.setNodeID(mMember.getID());
        super.mTN_Relation.add(r);
    }

    private Project mProject;
    public Project getProject() {
        return mProject;
    }
    public void setProject(Project project) throws Exception {
        if (project == null) {
            throw new Exception("Project can't be null");
        }

        if (!project.isChildOf(Project.CODE_PROJECT)) {
            throw new Exception("The node is not a Project");
        }

        mProject = project;
        TN_Relation r = new TN_Relation();
        r.setNodeID(project.getID());
        super.mTN_Relation.add(r);
    }

    private Merchant mMerchant;
    public Merchant getMerchant() {
        return mMerchant;
    }
    public void setMerchant(Merchant merchant) throws Exception {
        if (merchant == null) {
            throw new Exception("Merchant can't be null");
        }

        if (!merchant.isChildOf(Merchant.CODE_MERCHANT)) {
            throw new Exception("The node is not a Merchant.");
        }

        mMerchant = merchant;
        TN_Relation r = new TN_Relation();
        r.setNodeID(merchant.getID());
        super.mTN_Relation.add(r);
    }


}
