package w1.app.easymoney.model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by el17 on 5/2/2014.
 */
public class Member extends  Node {
    public static final String CODE_MEMBER = "MEM";

    public static Member valueOf(Node node) throws Exception {
        //TODO
        if (!node.isChildOf(Member.CODE_MEMBER)){
            return null;
        }

        Member member = new Member();
        member.mEditUserID = node.mEditUserID;
        member.mName = node.mName;
        member.mEditDate = node.mEditDate;
        member.mCode = node.mCode;
        member.mChildren = node.mChildren;
        member.mParent = node.mParent;
        member.mDescription = node.mDescription;
        member.mID = node.mID;
        member.mInDate = node.mInDate;
        member.mLevel = node.mLevel;
        member.mInUserID = node.mInUserID;
        member.mParentID = node.mParentID;

        return member;

    }

    public static List<Member> getAll() throws Exception {
        Node root = Node.buildByCode(Member.CODE_MEMBER);
        if (root == null){
            return null;
        }

        List<Node> nodeList = root.getChildren();

        if (nodeList == null || nodeList.size() < 1){
            return null;
        }

        List<Member> memberList = new ArrayList<Member>(nodeList.size());
        for(int i = 0; i < nodeList.size(); i ++){
           memberList.add(Member.valueOf(nodeList.get(i)));

        }

        return memberList;
    }

    private static List<Member> CACHE;
    public static List<Member> getCache() throws Exception {
       if (CACHE == null){
           CACHE = getAll();
       }

        return CACHE;
    }

    public static Member get(int id) throws Exception {
        Node node = Node.get(id);
        if (node == null){
            return null;
        }

        return valueOf(node);
    }

    public Member() throws Exception {
        super.mParentID = getRoot().getID();
        super.mParent = getRoot();
    }

    private static Node NODE_MEMBER;
    public static Node  getRoot() throws Exception {
        if (NODE_MEMBER == null){
           NODE_MEMBER = Node.getByCode(CODE_MEMBER);
        }

        if (NODE_MEMBER == null){
            throw new Exception("can't find the root of member.");
        }

        return NODE_MEMBER;
    }

    @Override
    public void save() throws Exception {
        if(!super.isChildOf(CODE_MEMBER)){
            throw new Exception("IT's not a Member node.");
        }

        super.save();

        CACHE = null;
    }

    @Override
    public void delete() throws Exception {
        super.delete();

        CACHE = null;
    }
}
