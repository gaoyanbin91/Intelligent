package com.gao.intelligent.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gaoyanbin on 2018/5/23.
 * 描述:
 */
public class LineValueBean implements Serializable {


    /**
     * scode : 0011
     * stype : 1
     * remark :
     * data : [{"name":"产品克重","varunit":"g/㎡","id":"00000000635c2eec01635c367adc0002","value":"45","status":true},{"name":"产品幅宽","varunit":"mm","id":"00000000635c2eec01635c381b230003","value":"3200","status":true},{"name":"产品规格","varunit":"","id":"00000000635c2eec01635c3f670c0008","value":"20180452","status":true},{"name":"卷绕速度","varunit":"m/min","id":"1ebab5992743a90b","value":"0","status":true},{"name":"卷绕长度","varunit":"m","id":"2da344c13e8206bb","value":"0","status":true}]
     */

    private String scode;
    private String stype;
    private String remark;
    private List<DataBean> data;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * name : 产品克重
         * varunit : g/㎡
         * id : 00000000635c2eec01635c367adc0002
         * value : 45
         * status : true
         */

        private String name;
        private String varunit;
        private String id;
        private String value;
        private boolean status;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getVarunit() {
            return varunit;
        }

        public void setVarunit(String varunit) {
            this.varunit = varunit;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }
    }
}
