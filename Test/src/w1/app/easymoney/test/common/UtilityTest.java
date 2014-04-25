package w1.app.easymoney.test.common;

import junit.framework.TestCase;
import w1.app.easymoney.common.Utility;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by el17 on 4/24/2014.
 */
public class UtilityTest extends TestCase{

    public void test() throws ParseException {
        this.doTestGetLastDayInMonth();
        this.doTestGetFirstDayInMonth();
    }

    private void doTestGetLastDayInMonth() throws ParseException {
        Date date = Utility.StringToDate("2013-11-30 12:33:25");

        date = Utility.getLastDayInMonth(date);

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DATE);

        assertEquals(2013, y);
        assertEquals(10, m);
        assertEquals(30, d);
    }

    private void doTestGetFirstDayInMonth() throws ParseException {
        Date date = Utility.StringToDate("2014-03-18 08:33:45");
        date = Utility.getFirstDayInMonth(date);

        Calendar c = Calendar.getInstance();
        c.setTime(date);

       int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DATE);

        assertEquals(2014, y);
        assertEquals(2, m);
        assertEquals(1, d);
    }


}
