package w1.app.easymoney.entity;

/**
 * Created by el17 on 4/25/2014.
 */
public class Account extends Node {
    public static String TYPE_CREDIT = "C";
    public static String TYPE_DEBIT = "D";

    public static String CODE_ACCOUNT_CREDIT = "CREDIT";
    public static String CODE_ACCOUNT_DEBIT = "DEBIT";

    private String mType;

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        this.mType = type;
    }
}
