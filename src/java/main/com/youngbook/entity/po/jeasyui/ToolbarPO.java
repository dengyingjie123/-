package com.youngbook.entity.po.jeasyui;

import com.youngbook.common.config.Config;
import com.youngbook.entity.po.BasePO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Lee
 * Date: 14-11-7
 * Time: 上午12:20
 * To change this template use File | Settings | File Templates.
 */
public class ToolbarPO extends BasePO {

    private HttpServletRequest request = null;


    private List<ButtonPO> buttons = new ArrayList<ButtonPO>();

    private ToolbarPO() {

    }

    public static ToolbarPO getInstance(HttpServletRequest request) {
        ToolbarPO toolbar = new ToolbarPO();
        toolbar.setRequest(request);
        return toolbar;
    }

    public void addButton(ButtonPO button) throws Exception {
        if (button.getPermissionName() != null && !button.getPermissionName().equals("")) {
            if (Config.hasPermission(button.getPermissionName(), request)) {
                buttons.add(button);
            }
        }
        else {
            buttons.add(button);
        }
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject json = new JSONObject();

        JSONArray array = new JSONArray();
        for (ButtonPO button : buttons) {
            array.add(button.toJsonObject());
        }
        json.element("buttons", array);
        return json;
    }

    public List<ButtonPO> getButtons() {
        return buttons;
    }

    public void setButtons(List<ButtonPO> buttons) {
        this.buttons = buttons;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
}
