package w1.app.easymoney.test.rule;

import junit.framework.TestCase;
import w1.app.easymoney.rule.OutgoingManager;

/**
 * Created by el17 on 4/24/2014.
 */
public class OutgoingManagerTest extends TestCase {

    private void doTestGetOutgoingSummaryByCurrentWeek() throws Exception {
        int s = OutgoingManager.getOutgoingSummaryByCurrentWeek();
    }

    private void doTestGetOutgoingSummaryByCurrentMonth() throws Exception {
        int s = OutgoingManager.getOutgoingSumaryByCurrentMonth();
    }

    public void doTestGetOutgoingSummaryByCurrentDate() throws Exception {
        int s = OutgoingManager.getOutgoingSummaryByCurrentDate();
    }
}

