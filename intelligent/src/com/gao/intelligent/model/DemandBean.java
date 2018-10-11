package com.gao.intelligent.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gaoyanbin on 2018/5/28.
 * 描述:服务请求实体
 */
public class DemandBean implements Serializable {

    /**
     * resultCode : 1
     * resultDesc : null
     * resultData : null
     * total : 12
     * page : 1
     * rows : [{"id":"402881f063a5efeb0163a5f2755c0001","introducer":"bd23d10b62dbb7be0162dbc88a770004","priority":"重要","code":"ks2","phone":"17839056840","name":"狂鼠2号","fjCustomerId":"bd23d10b62d7cbc70162d7f1fdec000a","fjProductionLineId":"402881e85ad53f8a015ad5ad3a000002","state":"0","type":"问题一","createTime":"2018-05-28 16:52:00","creater":"杨小顺","delFlag":0,"questionDetail":"狂鼠2号来袭"},{"id":"bd23d10b63a437be0163a483786900eb","introducer":"admin","code":"44","phone":"148","name":"估计","fjProductionLineId":"402881e85ad53f8a015ad5ad3a000002","state":"0","type":"4028810362bca6180162bca99c720001","createTime":"2018-05-28 10:11:10","creater":"bd23d10b62dbb7be0162dbbcb8040002","delFlag":0},{"id":"bd23d10b63a437be0163a487a49c00f2","introducer":"admin","code":"44","phone":"148","name":"估计","fjProductionLineId":"402881e85ad53f8a015ad5ad3a000002","state":"0","type":"4028810362bca6180162bca99c720001","createTime":"2018-05-28 10:15:43","creater":"bd23d10b62dbb7be0162dbbcb8040002","delFlag":0}]
     */

    private String resultCode;
    private Object resultDesc;
    private Object resultData;
    private int total;
    private int page;
    private List<RowsBean> rows;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public Object getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(Object resultDesc) {
        this.resultDesc = resultDesc;
    }

    public Object getResultData() {
        return resultData;
    }

    public void setResultData(Object resultData) {
        this.resultData = resultData;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * id : 402881f063a5efeb0163a5f2755c0001
         * introducer : bd23d10b62dbb7be0162dbc88a770004
         * priority : 重要
         * code : ks2
         * phone : 17839056840
         * name : 狂鼠2号
         * fjCustomerId : bd23d10b62d7cbc70162d7f1fdec000a
         * fjProductionLineId : 402881e85ad53f8a015ad5ad3a000002
         * state : 0
         * type : 问题一
         * createTime : 2018-05-28 16:52:00
         * creater : 杨小顺
         * delFlag : 0
         * questionDetail : 狂鼠2号来袭
         */

        private String id;
        private String introducer;
        private String priority;
        private String code;
        private String phone;
        private String name;
        private String fjCustomerId;
        private String fjProductionLineId;
        private String state;
        private String type;
        private String createTime;
        private String creater;
        private int delFlag;
        private String questionDetail;
        private String typeName;

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getTypeName() {

            return typeName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIntroducer() {
            return introducer;
        }

        public void setIntroducer(String introducer) {
            this.introducer = introducer;
        }

        public String getPriority() {
            return priority;
        }

        public void setPriority(String priority) {
            this.priority = priority;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFjCustomerId() {
            return fjCustomerId;
        }

        public void setFjCustomerId(String fjCustomerId) {
            this.fjCustomerId = fjCustomerId;
        }

        public String getFjProductionLineId() {
            return fjProductionLineId;
        }

        public void setFjProductionLineId(String fjProductionLineId) {
            this.fjProductionLineId = fjProductionLineId;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCreater() {
            return creater;
        }

        public void setCreater(String creater) {
            this.creater = creater;
        }

        public int getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(int delFlag) {
            this.delFlag = delFlag;
        }

        public String getQuestionDetail() {
            return questionDetail;
        }

        public void setQuestionDetail(String questionDetail) {
            this.questionDetail = questionDetail;
        }
    }
}
