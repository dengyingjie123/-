package com.youngbook.common.easyui.entity;

import com.youngbook.common.KVObject;
import com.youngbook.common.KVObjects;

import javax.servlet.http.HttpServletRequest;


public class MenuToolbar {

    private String id = "";
    private String title = "";
    private String icon = "";

    public MenuToolbar (String id, String title) {
        this(id, title, IconStyle.BLANK);
    }

    public MenuToolbar (String id, String title, String icon) {
        this.id = id;
        this.title = title;
        this.icon = icon;
    }

    private KVObjects menuButtons = new KVObjects();

    public MenuButton newMenuButton(String id, String name, String icon, int index) {
        MenuButton menuButton = new MenuButton(id, name, icon);

        KVObject kvObject = new KVObject(index, menuButton);

        menuButtons.add(kvObject);

        return menuButton;
    }

    public String printHtml(HttpServletRequest request) {
        StringBuffer sbHtml = new StringBuffer();

        StringBuffer sbMenuDefinition = new StringBuffer();
        StringBuffer sbMenuButton = new StringBuffer();


        for (int i = 0; i < menuButtons.size(); i++) {
            MenuButton menuButton = (MenuButton) menuButtons.getByIndex(i + 1).getValue();

            sbMenuDefinition.append(menuButton.printDefinition(request));

            sbMenuButton.append(menuButton.printButtons(request));
        }


        sbHtml.append("<div class=\"easyui-panel\" style=\"padding:2px; background-color: #FAFAFA\" title=\""+title+"\">");
        sbHtml.append(sbMenuDefinition);

        sbHtml.append("</div>");
        sbHtml.append(sbMenuButton);

        return sbHtml.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public KVObjects getMenuButtons() {
        return menuButtons;
    }

    public void setMenuButtons(KVObjects menuButtons) {
        this.menuButtons = menuButtons;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
