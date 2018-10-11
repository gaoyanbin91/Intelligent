package com.gao.intelligent.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Yanbin on 2018/7/16.
 * 描述:
 */
public class DrviceBean implements Serializable {
    /**
     * resultCode : 1
     * resultDesc : null
     * resultData : null
     * total : 0
     * page : 0
     * rows : [{"id":"00000000638c7e1001638fad238e024f","model":"AL-1300","name":"称量机","factoryNo":"111111123","fjEquipId":"00000000638c7e1001638fad2388024e","fjProductionLineId":"402881e85ad53f8a015ad5ad3a000002","createTime":1527123879000,"delFlag":0},{"id":"00000000638c7e1001638fad23920250","model":"AL-1300","name":"称量机","factoryNo":"111111123","fjEquipId":"00000000638c7e1001638fad2388024e","fjProductionLineId":"402881e85ad53f8a015ad5ad3a000002","createTime":1527123879000,"delFlag":0},{"id":"00000000638c7e1001638fad239d0252","model":"MH-1500","name":"水循环","factoryNo":"","fjEquipId":"00000000638c7e1001638fad23970251","fjProductionLineId":"402881e85ad53f8a015ad5ad3a000002","createTime":1527123879000,"delFlag":0},{"id":"00000000638c7e1001638fad23a20253","model":"MH-1500","name":"水循环","factoryNo":"","fjEquipId":"00000000638c7e1001638fad23970251","fjProductionLineId":"402881e85ad53f8a015ad5ad3a000002","createTime":1527123879000,"delFlag":0}]
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
         * id : 00000000638c7e1001638fad238e024f
         * model : AL-1300
         * name : 称量机
         * factoryNo : 111111123
         * fjEquipId : 00000000638c7e1001638fad2388024e
         * fjProductionLineId : 402881e85ad53f8a015ad5ad3a000002
         * createTime : 1527123879000
         * delFlag : 0
         */

        private String id;
        private String model;
        private String name;
        private String factoryNo;
        private String fjEquipId;
        private String fjProductionLineId;
        private long createTime;
        private int delFlag;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFactoryNo() {
            return factoryNo;
        }

        public void setFactoryNo(String factoryNo) {
            this.factoryNo = factoryNo;
        }

        public String getFjEquipId() {
            return fjEquipId;
        }

        public void setFjEquipId(String fjEquipId) {
            this.fjEquipId = fjEquipId;
        }

        public String getFjProductionLineId() {
            return fjProductionLineId;
        }

        public void setFjProductionLineId(String fjProductionLineId) {
            this.fjProductionLineId = fjProductionLineId;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(int delFlag) {
            this.delFlag = delFlag;
        }
    }
}
