package com.youngbook.action.production;

import com.youngbook.action.BaseAction;
import com.youngbook.common.*;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.entity.po.production.ProductPropertyPO;
import com.youngbook.service.production.ProductPropertyService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;


public class ProductPropertyAction extends BaseAction {

    private ProductPropertyPO productProperty = new ProductPropertyPO();

    @Autowired
    ProductPropertyService productPropertyService;


    public String load() throws Exception {

        productProperty = productPropertyService.loadById(productProperty.getId(), getConnection());

        if (productProperty == null) {
            MyException.newInstance("查询失败").throwException();
        }

        getResult().setReturnValue(productProperty.toJsonObject4Form());

        return SUCCESS;
    }


    public String getPropertiesByProductId() throws Exception {

        if (productProperty != null && !StringUtils.isEmpty(productProperty.getProductId())) {
            Pager pager = Pager.getInstance(getRequest());
            pager = productPropertyService.getPropertiesByProductId(productProperty.getProductId(), pager.getCurrentPage(), pager.getShowRowCount(), getConnection());

            getResult().setReturnValue(pager.toJsonObject());
        }

        return SUCCESS;
    }

    public String newProperty() throws Exception {

        ProductPropertyPO newProperty = productPropertyService.newProperty(productProperty, getConnection());

        if (newProperty == null) {
            getResult().setMessage("保存失败");
        }
        else {
            getResult().setMessage("保存成功");
            getResult().setReturnValue(newProperty);
        }

        return SUCCESS;
    }

    public ProductPropertyPO getProductProperty() {
        return productProperty;
    }

    public void setProductProperty(ProductPropertyPO productProperty) {
        this.productProperty = productProperty;
    }

}
