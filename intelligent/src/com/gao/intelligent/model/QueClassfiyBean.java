package com.gao.intelligent.model;

import java.io.Serializable;

/**
 * Created by gaoyanbin on 2018/5/28.
 * 描述:问题分类
 */
public class QueClassfiyBean implements Serializable {

    /**
     * id : 4028810362bca6180162bca99c720001
     * code : wt1
     * name : 问题一
     * parentId : 1
     * sort : 1
     * status : Y
     * remark :
     * delFlag : 0
     */

    private String id;
    private String code;
    private String name;
    private String parentId;
    private int sort;
    private String status;
    private String remark;
    private String delFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}
