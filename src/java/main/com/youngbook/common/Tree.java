package com.youngbook.common;

import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class  Tree implements IJsonable {
    private String id;
    private String parentId;
    private String name;
    private IJsonable data;
    private Tree parent;
    private List<Tree> children = new ArrayList<Tree>();

    private String _state = "open";

    public Tree() {

    }

    @Override
    public JSONObject toJsonObject4Treegrid() {

        JSONObject json = this.toJsonObject();
        json.element("id", this.getId());
        json.element("name", this.getName());
        json.element("parentId", this.getParent().getId());
        if (this.getChildren().size() > 0) {
            this.set_state("closed");
        }
        json.element("state", this.get_state());

        return json;
    }

    public String toString() {
        return "id: "+this.id+" parent:" + this.parentId;
    }

    public Tree(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Tree(String id, String name, String parentId, IJsonable data) {
        this.id=id;
        this.data=data;
        this.name=name;
        this.parentId = parentId;
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject json = new JSONObject();
        json.element("id", this.getId());
        json.element("name", this.getName());
        json.element("parentId", this.getParent().getId());
        return json;
    }

    @Override
    public JSONObject toJsonObject4Form() {
        JSONObject json = new JSONObject();
        json.element("id", this.getId());
        json.element("parentId", this.getParent());
        return json;
    }

    @Override
    public JSONObject toJsonObject4Form(String prefix) {
        JSONObject json = new JSONObject();
        json.element(prefix + ".id", this.getId());
        json.element(prefix + ".parentId", this.getParent());
        return json;
    }

    @Override
    public JSONObject toJsonObject4Tree() {
        JSONObject json = new JSONObject();
        json.element("id", this.getId());
        json.element("text", this.getName());
        return json;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public IJsonable getData() {
        return data;
    }

    public void setData(IJsonable data) {
        this.data = data;
    }

    public Tree getParent() {
        return parent;
    }

    public void setParent(Tree parent) {
        this.parent = parent;
    }

    public List<Tree> getChildren() {
        return children;
    }

    public void setChildren(List<Tree> children) {
        this.children = children;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String get_state() {
        return _state;
    }

    public void set_state(String _state) {
        this._state = _state;
    }
}
