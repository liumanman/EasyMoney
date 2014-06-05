package w1.app.easymoney.test.model;

import junit.framework.TestCase;
import w1.app.easymoney.common.Utility;
import w1.app.easymoney.data.DatabaseOperator;
import w1.app.easymoney.model.*;

import java.sql.SQLException;
import java.util.Date;

/**
 * Created by el17 on 6/5/2014.
 */
public class OutgoingTest extends TestCase{
    private OutgoingCategory res;
    private Account boa;
    private Member evan;
    private Merchant costco;
    private Project pro1;

    protected void setUp() throws Exception {
        super.setUp();

        DatabaseOperator database = DatabaseOperator.getOperator();
        database.getWritableDatabase().execSQL("delete from " + Node.TABLE_NAME);
        database.getWritableDatabase().execSQL("delete from " + Transaction.TABLE_NAME);

        Node account = new Node();
        account.setName("Account");
        account.setDescription("Account");
        account.setCode(Account.CODE_ACCOUNT);
        account.save();

        Node credit = new Node();
        credit.setName("Credit");
        credit.setDescription("credit");
        credit.setCode(Account.CODE_ACCOUNT_CREDIT);
        credit.setParentID(account.getID());
        credit.save();

        Node debit = new Node();
        debit.setName("Debit");
        debit.setDescription("Debit");
        debit.setCode(Account.CODE_ACCOUNT_DEBIT);
        debit.setParentID(account.getID());
        debit.save();

        boa = new Account(Account.TYPE_DEBIT);
        boa.setName("BOA");
        boa.setDescription("BOA checking");
        boa.save();

        Node member = new Node();
        member.setName("Member");
        member.setDescription("Member");
        member.setCode(Member.CODE_MEMBER);
        member.save();

        evan = new Member();
        evan.setName("Evan");
        evan.setDescription("Manman Liu");
        evan.save();

        Node oc = new Node();
        oc.setName("OC");
        oc.setDescription("OC");
        oc.setCode(OutgoingCategory.CODE_OUTGOING_CATEGORY);
        oc.save();

        OutgoingCategory food = OutgoingCategory.create();
        food.setName("Food");
        food.setDescription("Food");
        food.save();

        res = food.createSub();
        res.setName("Restaurant");
        res.setDescription("Restaurant");
        res.save();

        Node merchant = new Node();
        merchant.setName("Merchant");
        merchant.setDescription("Merchant");
        merchant.setCode(Merchant.CODE_MERCHANT);
        merchant.save();

        costco = new Merchant();
        costco.setName("Costco");
        costco.setDescription("Costco");
        costco.save();

        Node project = new Node();
        project.setName("Project");
        project.setDescription("Project");
        project.setCode(Project.CODE_PROJECT);
        project.save();

        pro1 = new Project();
        pro1.setName("Project1");
        pro1.setDescription("Project1");
        pro1.save();

    }

    public void test() throws Exception {
        int id = doTestIssue();
        this.doTestModify(id);

    }
    private int doTestIssue() throws Exception {
        Outgoing outgoing = new Outgoing();
        outgoing.setAmount(999);
        outgoing.setTranDate(Utility.StringToDate("2014-06-04 00:00:00"));
        outgoing.setOC(res);
        outgoing.setAccount(boa);
        outgoing.setMember(evan);
        outgoing.setMerchant(costco);
        outgoing.setProject(pro1);

        outgoing.save();
        if (outgoing.getID() < 1){
            fail();
        }

        Outgoing outgoing2 = Outgoing.get(outgoing.getID());
        assertNotNull(outgoing2);
        assertEquals(999, outgoing2.getAmount());
        assertEquals("2014-06-04 00:00:00", Utility.DateToString(outgoing2.getTranDate()));


        assertNotNull(outgoing2.getOC());
        assertNotNull(outgoing2.getAccount());
        assertNotNull(outgoing2.getMemeber());
        assertNotNull(outgoing2.getMerchant());
        assertNotNull(outgoing2.getProject());

        assertEquals(res.getID(), outgoing2.getOC().getID());
        assertEquals(boa.getID(), outgoing2.getAccount().getID());
        assertEquals(evan.getID(),outgoing2.getMemeber().getID());
        assertEquals(costco.getID(), outgoing2.getMerchant().getID());
        assertEquals(pro1.getID(), outgoing2.getProject().getID());

        return outgoing.getID();
    }

    private void doTestModify(int id) throws Exception {
        Outgoing outgoing = Outgoing.get(id);
        outgoing.setAmount(1999);
        outgoing.setTranDate(Utility.StringToDate("2014-06-05 00:00:00"));
        outgoing.save();

        Outgoing outgoing2 = Outgoing.get(outgoing.getID());
        assertNotNull(outgoing2);
        assertEquals(1999, outgoing2.getAmount());
        assertEquals("2014-06-05 00:00:00", Utility.DateToString(outgoing2.getTranDate()));


        assertNotNull(outgoing2.getOC());
        assertNotNull(outgoing2.getAccount());
        assertNotNull(outgoing2.getMemeber());
        assertNotNull(outgoing2.getMerchant());
        assertNotNull(outgoing2.getProject());

        assertEquals(res.getID(), outgoing2.getOC().getID());
        assertEquals(boa.getID(), outgoing2.getAccount().getID());
        assertEquals(evan.getID(),outgoing2.getMemeber().getID());
        assertEquals(costco.getID(), outgoing2.getMerchant().getID());
        assertEquals(pro1.getID(), outgoing2.getProject().getID());
    }

}
