package w1.app.easymoney.rule;

import w1.app.easymoney.common.Utility;
import w1.app.easymoney.entity.Node;
import w1.app.easymoney.entity.Transaction;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by el17 on 4/24/2014.
 */
public class OutgoingManager {
    public static int getOutgoingSummaryByCurrentDate() throws Exception {
        Node ocRoot = NodeManager.getByCode(Node.CODE_OUTGOING_CATEGORY);

        Date currentDay = new Date();

        return TransactionManager.getSummary(currentDay, currentDay, new int[]{ocRoot.getID()});
    }

    public static int getOutgoingSummaryByCurrentWeek() throws Exception {

        Node ocRoot = NodeManager.getByCode(Node.CODE_OUTGOING_CATEGORY);

        Date currentDay = new Date();
        Date sun = Utility.getTheSunday(currentDay);
        Date sat = Utility.getTheSaturday(currentDay);

        return TransactionManager.getSummary(sun, sat, new int[]{ocRoot.getID()});
    }

    public static int getOutgoingSumaryByCurrentMonth() throws Exception {

        Node ocRoot = NodeManager.getByCode(Node.CODE_OUTGOING_CATEGORY);

        Date date = new Date();
        Date start = Utility.getFirstDayInMonth(date);
        Date end = Utility.getLastDayInMonth(date);

        return TransactionManager.getSummary(start, end, new int[]{ocRoot.getID()});
    }

    public static List<Node> getCategory() throws ParseException {
        return NodeManager.getAllByCode(Node.CODE_OUTGOING_CATEGORY);
    }

    public static Transaction addOutgoingTransaction() throws Exception {
        throw new Exception("not finished yet.");
    }
}
