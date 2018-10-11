package com.gao.intelligent.model;

import java.io.Serializable;

/**
 * Created by gaoyanbin on 2018/5/22.
 * 描述:
 */
public class KeyBean implements Serializable {

    /**
     * scode : A1000
     * stype : 0
     * remark : 12131
     * sdata : {"ids":"2da344c13e8206bb,1ebab5992743a90b,00000000635c2eec01635c381b230003,00000000635c2eec01635c367adc0002,00000000635c2eec01635c3f670c0008"}
     */

    private String scode;
    private String stype;
    private String remark;
    private SdataBean sdata;

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

    public SdataBean getSdata() {
        return sdata;
    }

    public void setSdata(SdataBean sdata) {
        this.sdata = sdata;
    }

    public static class SdataBean {
        /**
         * ids : 2da344c13e8206bb,1ebab5992743a90b,00000000635c2eec01635c381b230003,00000000635c2eec01635c367adc0002,00000000635c2eec01635c3f670c0008
         */

        private String ids;

        public String getIds() {
            return ids;
        }

        public void setIds(String ids) {
            this.ids = ids;
        }
    }
}
