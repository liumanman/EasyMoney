package w1.app.easymoney.rule;

import w1.app.easymoney.entity.Account;
import w1.app.easymoney.entity.Node;

import java.text.ParseException;
import java.util.List;

/**
 * Created by el17 on 4/25/2014.
 */
public class AccountManager {
    private static List<Node> CACHE;
    private static List<Node> getCache() throws ParseException {
        if (CACHE == null){
            CACHE = NodeManager.getAllByCode(Node.CODE_ACCOUNT);
        }

        return CACHE;
    }

    public static Account get(int id) throws Exception {
        Node node = NodeManager.get(id);
        if (node == null) { return null;}

        throw new Exception("not finished.");
    }

    private static String getType(Node node) throws Exception {
        getCache();

        for(int i = 0; i < CACHE.size(); i ++){
            if (CACHE.get(i).equals(Account.CODE_ACCOUNT_CREDIT)){
                for(int j = 0; j < CACHE.get(i).getChildren().size(); j ++){
                    if (node.getID() == CACHE.get(i).getChildren().get(j).getID()){
                        return Account.TYPE_CREDIT;
                    }
                }

                continue;
            }

            if (CACHE.get(i).equals(Account.CODE_ACCOUNT_DEBIT)){
                for(int j = 0; j < CACHE.get(i).getChildren().size(); j ++){
                    if (node.getID() == CACHE.get(i).getChildren().get(j).getID()){
                        return Account.TYPE_DEBIT;
                    }
                }

                continue;
            }


        }

        throw new Exception("Can't get the account type.");


    }
}
