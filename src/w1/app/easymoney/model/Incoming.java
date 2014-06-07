package w1.app.easymoney.model;

/**
 * Created by el17 on 6/6/2014.
 */
public class Incoming extends Transaction {
    private Incoming(){

    }

    public static Incoming create(){
        //todo
        return null;
    }

    public static Incoming valueOf(Transaction tran){
        //todo
        return null;
    }

    private IncomingCategory mIC;
    public IncomingCategory getIC(){
        return mIC;
    }
    public void setIC(IncomingCategory ic){
        //todo
    }

    private Account mAccount;
    public Account getAccount(){
        return mAccount;
    }
    public void setAccount(){
        //todo
    }

    private Member mMember;
    public Member getMember(){
        return mMember;
    }
    public void setMember(){
        //todo
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


}
