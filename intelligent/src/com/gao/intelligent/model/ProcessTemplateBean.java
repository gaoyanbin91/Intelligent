package com.gao.intelligent.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gaoyanbin on 2018/6/22.
 * 描述: 工艺参数模板列表实体
 */
public class ProcessTemplateBean implements Serializable {

    /**
     * resultCode : 1
     * resultDesc : null
     * resultData : null
     * total : 0
     * page : 0
     * rows : [{"id":"000000006417fc9901641ad6b10d00a8","batchNum":"2018-06-19","fjCustomerId":"bd23d10b62d7cbc70162d7f1fdec000a","fjProductionLineId":"402881e85ad53f8a015ad5ad3a000002","name":"42g80:20平纹","weight":"42g","ratio":"80:20","tech":"平纹","createTime":"2018-06-20 09:37:15","creater":"","delFlag":0},{"id":"0000000063ee60c30163f2a5a40d05a6","batchNum":"2018-06-12","fjCustomerId":"bd23d10b62d7cbc70162d7f1fdec000a","fjProductionLineId":"402881e85ad53f8a015ad5ad3a000002","name":"40G3:2提花工艺","weight":"40g","ratio":"3:2","tech":"提花","createTime":"2018-06-12 14:18:52","creater":"","delFlag":0}]
     * pageSize : 0
     * object : null
     * sCount : 0
     * fCount : 0
     */

    private String resultCode;
    private Object resultDesc;
    private Object resultData;
    private int total;
    private int page;
    private int pageSize;
    private Object object;
    private int sCount;
    private int fCount;
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

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public int getSCount() {
        return sCount;
    }

    public void setSCount(int sCount) {
        this.sCount = sCount;
    }

    public int getFCount() {
        return fCount;
    }

    public void setFCount(int fCount) {
        this.fCount = fCount;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * id : 000000006417fc9901641ad6b10d00a8
         * batchNum : 2018-06-19
         * fjCustomerId : bd23d10b62d7cbc70162d7f1fdec000a
         * fjProductionLineId : 402881e85ad53f8a015ad5ad3a000002
         * name : 42g80:20平纹
         * weight : 42g
         * ratio : 80:20
         * tech : 平纹
         * createTime : 2018-06-20 09:37:15
         * creater :
         * delFlag : 0
         */

        private String id;
        private String batchNum;
        private String fjCustomerId;
        private String fjProductionLineId;
        private String name;
        private String weight;
        private String ratio;
        private String tech;
        private String createTime;
        private String creater;
        private int delFlag;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBatchNum() {
            return batchNum;
        }

        public void setBatchNum(String batchNum) {
            this.batchNum = batchNum;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getRatio() {
            return ratio;
        }

        public void setRatio(String ratio) {
            this.ratio = ratio;
        }

        public String getTech() {
            return tech;
        }

        public void setTech(String tech) {
            this.tech = tech;
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
    }
}
