package w1.app.easymoney.model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by el17 on 5/2/2014.
 */
public class Project extends Node {
    public static final String CODE_PROJECT = "PRO";

    public static Project valueOf(Node node) throws ParseException {
        if (!node.isChildOf(CODE_PROJECT)){
           return null;
        }

        Project project = new Project();
        project.mInUserID = node.mInUserID;
        project.mLevel = node.mLevel;
        project.mCode = node.mCode;
        project.mID = node.mID;
        project.mName = node.mName;
        project.mEditUserID = node.mEditUserID;
        project.mChildren = node.mChildren;
        project.mDescription = node.mDescription;
        project.mEditDate = node.mEditDate;
        project.mInDate = node.mInDate;
        project.mParent = node.mParent;
        project.mParentID = node.mParentID;

        return project;

    }

    public static List<Project> getAll() throws ParseException {
        Node root = Node.buildByCode(CODE_PROJECT);
        if (root == null || root.getChildren() == null || root.getChildren().size() < 1){
            return null;
        }

        List<Project> projectList = new ArrayList<Project>(root.getChildren().size());
        for(int i = 0; i < projectList.size(); i ++){
            projectList.add(valueOf(root.getChildren().get(i)));
        }

        return projectList;
    }

    private  static List<Project> CACHE;
    public static List<Project> getCACHE() throws ParseException {
        if (CACHE == null){
            CACHE = getAll();
        }

        return CACHE;
    }

    private  static Node ROOT;
    public static Node getRoot() throws ParseException {
        if (ROOT == null){
            ROOT = Node.getByCode(CODE_PROJECT);
        }

        return ROOT;
    }

    @Override
    public void save() throws Exception {
        if (!super.isChildOf(CODE_PROJECT)){
            throw new Exception("It's not a Project node.");
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
