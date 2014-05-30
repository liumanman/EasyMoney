package w1.app.easymoney.model;

import java.util.List;

/**
 * Created by el17 on 5/2/2014.
 */
public class Merchant extends Node {
    public static final String CODE_MERCHANT = "MER";

    public static Merchant valueOf(Node node){
        //todo

        return null;
    }

    public static List<Merchant> getAll(){
        //todo
        return null;
    }

    private static List<Merchant> CACHE;
    public static List<Merchant> getCACHE(){
        //todo
        return null;
    }

    private static Node ROOT;
    public static Node getRoot(){
        //todo
        return null;
    }

    public Merchant(){
        super.mParent = getRoot();
        super.mParentID = getRoot().getID();
    }

    @Override
    public void save() throws Exception {
        super.save();

        CACHE = null;
    }

    @Override
    public void delete() throws Exception {
        super.delete();

        CACHE = null;
    }
}
