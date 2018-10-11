package com.gao.intelligent.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gaoyanbin on 2018/6/27.
 * 描述:
 */
public class ParamsBean implements Serializable {

    /**
     * data : [{"code":"SL1_XBM_A","id":"10be735a5a47468c","name":"下剥棉","record":1530067790884,"status":true,"value":"55.1","varunit":"m/min"},{"code":"SL2_SMXYL_A","id":"18029a28ad73906f","name":"上棉箱压力","record":1530067790665,"status":true,"value":"701.07","varunit":"Pa"},{"code":"JR_JRSD_A","id":"1ebab5992743a90b","name":"卷绕速度","record":1530067790532,"status":true,"value":"135.97","varunit":"m/min"},{"code":"SL1_SCWL_A","id":"213def01b78f7305","name":"上出网帘","record":1530067790884,"status":true,"value":"105.83","varunit":"m/min"},{"code":"SL2_XMXYL_A","id":"22aac035bf6351da","name":"下棉箱压力","record":1530067790665,"status":true,"value":"251.01","varunit":"Pa"},{"code":"SL2_SBM_A","id":"2511288046551e5a","name":"上剥棉","record":1530067790665,"status":true,"value":"52.13","varunit":"m/min"},{"code":"SL2_CQFJ2_A","id":"335fe9c15dcc965","name":"吹气风机2","record":1530067790665,"status":true,"value":"45","varunit":"Hz"},{"code":"SL2_MXFJ1_A","id":"4c6fd371d2e37d9","name":"棉箱风机1","record":1530067790665,"status":true,"value":"21","varunit":"Hz"},{"code":"SL1_SBM_A","id":"4e2417fefb8fc83e","name":"上剥棉","record":1530067790884,"status":true,"value":"52.02","varunit":"m/min"},{"code":"SL2_SDF_A","id":"5d579a22bab6d4da","name":"上道夫","record":1530067790665,"status":true,"value":"112.89","varunit":"m/min"},{"code":"SL1_SMXYL_A","id":"6135ca28a9cfc0eb","name":"上棉箱压力","record":1530067790884,"status":true,"value":"763.68","varunit":"Pa"},{"code":"SL2_XXL_A","id":"625f80030dfd11a1","name":"胸锡林","record":1530067790665,"status":true,"value":"449.98","varunit":"m/min"},{"code":"SL1_XNJ1_A","id":"633dba2f345c883e","name":"下凝聚1","record":1530067790884,"status":true,"value":"58.7","varunit":"m/min"},{"code":"SL1_PDC_A","id":"697ed4effb06ecd","name":"皮带秤","record":1530067790884,"status":true,"value":"3.77","varunit":"m/min"},{"code":"SL2_XNJ2_A","id":"6c6e5ecca56d9d75","name":"下凝聚2","record":1530067790665,"status":true,"value":"30.87","varunit":"m/min"},{"code":"SL2_ZXLGZG_A","id":"742c6872dfc72407","name":"主锡林工作辊","record":1530067790665,"status":true,"value":"80.03","varunit":"m/min"},{"code":"SL2_XCWL_A","id":"775bf90ec9a0d901","name":"下出网帘","record":1530067790666,"status":true,"value":"114.7","varunit":"m/min"},{"code":"SL2_WMLL_A","id":"78283bb8d366b1be","name":"喂棉罗拉","record":1530067790745,"status":true,"value":"4.67","varunit":"m/min"},{"code":"SL2_ZXLBQG_A","id":"7c15d9f745ffa948","name":"主锡林剥取辊","record":1530067790745,"status":true,"value":"158.38","varunit":"m/min"},{"code":"SL1_ZXLGZG_A","id":"7ca7d71c334e0c29","name":"主锡林工作辊","record":1530067790884,"status":true,"value":"80.07","varunit":"m/min"},{"code":"SL2_XNJ1_A","id":"7fdeae5395f94c3a","name":"下凝聚1","record":1530067790745,"status":true,"value":"60.82","varunit":"m/min"},{"code":"SL2_MXJM_A","id":"83884f3d42262e34","name":"棉箱给棉","record":1530067790745,"status":true,"value":"0","varunit":""},{"code":"SL1_XXLGZG_A","id":"8ff49e9671596508","name":"胸锡林工作辊","record":1530067790884,"status":true,"value":"44.94","varunit":"m/min"},{"code":"SL2_XXLGZG_A","id":"930f761d7785c78","name":"胸锡林工作辊","record":1530067790745,"status":true,"value":"44.98","varunit":"m/min"},{"code":"SL1_SNJ2_A","id":"937f74b7922d2ef4","name":"上凝聚2","record":1530067790884,"status":true,"value":"27.76","varunit":"m/min"},{"code":"SL1_SDF_A","id":"944fdbc84fc512e2","name":"上道夫","record":1530067790884,"status":true,"value":"114.87","varunit":"m/min"},{"code":"","id":"963cb1412177ea3d","name":"烘干机主传动速度","record":1530067233159,"status":false,"value":"","varunit":"m/min"},{"code":"SL1_XXL_A","id":"998db89b50a52857","name":"胸锡林","record":1530067790884,"status":true,"value":"449.98","varunit":"m/min"},{"code":"SL1_CQFJ2_A","id":"9a05556a660d36c3","name":"吹气风机2","record":1530067790884,"status":true,"value":"45","varunit":"Hz"}]
     * remark :
     * scode : 0011
     * stype : 1
     */

    private String remark;
    private String scode;
    private String stype;
    private List<DataBean> data;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getScode() {
        return scode;
    }

    public void setScode(String scode) {
        this.scode = scode;
    }

    public String getStype() {
        return stype;
    }

    public void setStype(String stype) {
        this.stype = stype;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * code : SL1_XBM_A
         * id : 10be735a5a47468c
         * name : 下剥棉
         * record : 1530067790884
         * status : true
         * value : 55.1
         * varunit : m/min
         */

        private String code;
        private String id;
        private String name;
        private long record;
        private boolean status;
        private String value;
        private String varunit;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

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

        public long getRecord() {
            return record;
        }

        public void setRecord(long record) {
            this.record = record;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getVarunit() {
            return varunit;
        }

        public void setVarunit(String varunit) {
            this.varunit = varunit;
        }
    }
}
