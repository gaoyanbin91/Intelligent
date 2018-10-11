package com.gao.intelligent.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gaoyanbin on 2018/6/22.
 * 描述:工艺参数实体
 */
public class ProcessParamBean implements Serializable {

    /**
     * fCount : 0
     * page : 0
     * pageSize : 0
     * resultCode : 1
     * rows : [{"createTime":"2018-06-19 22:00:00","delFlag":0,"equipmentName":"梳理机1","fjBusinessType":"A","fjCustomerId":"bd23d10b62d7cbc70162d7f1fdec000a","fjEquipmentId":"bd23d10b62d7f65c0162d8223684002b","fjParamConfigId":"10be735a5a47468c","fjTechModelId":"000000006417fc9901641ad6b10d00a8","fjVarCode":"SL1_XBM","fjVarunit":"m/min","id":"000000006417fc9901641ad6b13000a9","paramAddress":"DB1.DBD100","paramName":"下剥棉","paramType":"DBD","value":"64.08"},{"createTime":"2018-06-19 22:00:00","delFlag":0,"equipmentName":"梳理机2","fjBusinessType":"A","fjCustomerId":"bd23d10b62d7cbc70162d7f1fdec000a","fjEquipmentId":"0000000062f5356d0162f59219aa000a","fjParamConfigId":"18029a28ad73906f","fjTechModelId":"000000006417fc9901641ad6b10d00a8","fjVarCode":"SL2_SMXYL","fjVarunit":"Pa","id":"000000006417fc9901641ad6b13200aa","paramAddress":"DB1.DBD4","paramName":"上棉箱压力","paramType":"DBD","value":"795.16"},{"createTime":"2018-06-19 22:00:00","delFlag":0,"equipmentName":"卷绕机","fjBusinessType":"A","fjCustomerId":"bd23d10b62d7cbc70162d7f1fdec000a","fjEquipmentId":"bd23d10b62d7f65c0162d8245b150033","fjParamConfigId":"1ebab5992743a90b","fjTechModelId":"000000006417fc9901641ad6b10d00a8","fjVarCode":"JR_JRSD","fjVarunit":"m/min","id":"000000006417fc9901641ad6b13300ab","paramAddress":"DB1.DBD4","paramName":"卷绕速度","paramType":"DBD","value":"164.5"},{"createTime":"2018-06-19 22:00:00","delFlag":0,"equipmentName":"梳理机1","fjBusinessType":"A","fjCustomerId":"bd23d10b62d7cbc70162d7f1fdec000a","fjEquipmentId":"bd23d10b62d7f65c0162d8223684002b","fjParamConfigId":"213def01b78f7305","fjTechModelId":"000000006417fc9901641ad6b10d00a8","fjVarCode":"SL1_SCWL","fjVarunit":"m/min","id":"000000006417fc9901641ad6b13400ac","paramAddress":"DB1.DBD108","paramName":"上出网帘","paramType":"DBD","value":"118.77"},{"createTime":"2018-06-19 22:00:00","delFlag":0,"equipmentName":"梳理机2","fjBusinessType":"A","fjCustomerId":"bd23d10b62d7cbc70162d7f1fdec000a","fjEquipmentId":"0000000062f5356d0162f59219aa000a","fjParamConfigId":"22aac035bf6351da","fjTechModelId":"000000006417fc9901641ad6b10d00a8","fjVarCode":"SL2_XMXYL","fjVarunit":"Pa","id":"000000006417fc9901641ad6b13500ad","paramAddress":"DB1.DBD12","paramName":"下棉箱压力","paramType":"DBD","value":"261.6"},{"createTime":"2018-06-19 22:00:00","delFlag":0,"equipmentName":"梳理机2","fjBusinessType":"A","fjCustomerId":"bd23d10b62d7cbc70162d7f1fdec000a","fjEquipmentId":"0000000062f5356d0162f59219aa000a","fjParamConfigId":"2511288046551e5a","fjTechModelId":"000000006417fc9901641ad6b10d00a8","fjVarCode":"SL2_SBM","fjVarunit":"m/min","id":"000000006417fc9901641ad6b13700ae","paramAddress":"DB1.DBD92","paramName":"上剥棉","paramType":"DBD","value":"58.29"}]
     * sCount : 0
     * total : 0
     */

    private int fCount;
    private int page;
    private int pageSize;
    private String resultCode;
    private int sCount;
    private int total;
    private List<RowsBean> rows;

    public int getFCount() {
        return fCount;
    }

    public void setFCount(int fCount) {
        this.fCount = fCount;
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

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public int getSCount() {
        return sCount;
    }

    public void setSCount(int sCount) {
        this.sCount = sCount;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * createTime : 2018-06-19 22:00:00
         * delFlag : 0
         * equipmentName : 梳理机1
         * fjBusinessType : A
         * fjCustomerId : bd23d10b62d7cbc70162d7f1fdec000a
         * fjEquipmentId : bd23d10b62d7f65c0162d8223684002b
         * fjParamConfigId : 10be735a5a47468c
         * fjTechModelId : 000000006417fc9901641ad6b10d00a8
         * fjVarCode : SL1_XBM
         * fjVarunit : m/min
         * id : 000000006417fc9901641ad6b13000a9
         * paramAddress : DB1.DBD100
         * paramName : 下剥棉
         * paramType : DBD
         * value : 64.08
         */

        private String createTime;
        private int delFlag;
        private String equipmentName;
        private String fjBusinessType;
        private String fjCustomerId;
        private String fjEquipmentId;
        private String fjParamConfigId;
        private String fjTechModelId;
        private String fjVarCode;
        private String fjVarunit;
        private String id;
        private String paramAddress;
        private String paramName;
        private String paramType;
        private String value;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(int delFlag) {
            this.delFlag = delFlag;
        }

        public String getEquipmentName() {
            return equipmentName;
        }

        public void setEquipmentName(String equipmentName) {
            this.equipmentName = equipmentName;
        }

        public String getFjBusinessType() {
            return fjBusinessType;
        }

        public void setFjBusinessType(String fjBusinessType) {
            this.fjBusinessType = fjBusinessType;
        }

        public String getFjCustomerId() {
            return fjCustomerId;
        }

        public void setFjCustomerId(String fjCustomerId) {
            this.fjCustomerId = fjCustomerId;
        }

        public String getFjEquipmentId() {
            return fjEquipmentId;
        }

        public void setFjEquipmentId(String fjEquipmentId) {
            this.fjEquipmentId = fjEquipmentId;
        }

        public String getFjParamConfigId() {
            return fjParamConfigId;
        }

        public void setFjParamConfigId(String fjParamConfigId) {
            this.fjParamConfigId = fjParamConfigId;
        }

        public String getFjTechModelId() {
            return fjTechModelId;
        }

        public void setFjTechModelId(String fjTechModelId) {
            this.fjTechModelId = fjTechModelId;
        }

        public String getFjVarCode() {
            return fjVarCode;
        }

        public void setFjVarCode(String fjVarCode) {
            this.fjVarCode = fjVarCode;
        }

        public String getFjVarunit() {
            return fjVarunit;
        }

        public void setFjVarunit(String fjVarunit) {
            this.fjVarunit = fjVarunit;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getParamAddress() {
            return paramAddress;
        }

        public void setParamAddress(String paramAddress) {
            this.paramAddress = paramAddress;
        }

        public String getParamName() {
            return paramName;
        }

        public void setParamName(String paramName) {
            this.paramName = paramName;
        }

        public String getParamType() {
            return paramType;
        }

        public void setParamType(String paramType) {
            this.paramType = paramType;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
