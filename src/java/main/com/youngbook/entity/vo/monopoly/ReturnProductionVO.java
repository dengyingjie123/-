package com.youngbook.entity.vo.monopoly;

/**
 * @description: 大富翁APP发挥到前端的产品对象，屏蔽部分数据
 * @author: 徐明煜
 * @createDate: 2019/1/15 17:41
 * @version: 1.1
 */
public class ReturnProductionVO {

    // id
    private String id = new String();

    // 产品名称
    private String name = new String();

    // 项目ID
    private String projectId = new String();

    // 产品概述
    private String productionDescription = new String();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProductionDescription() {
        return productionDescription;
    }

    public void setProductionDescription(String productionDescription) {
        this.productionDescription = productionDescription;
    }
}
