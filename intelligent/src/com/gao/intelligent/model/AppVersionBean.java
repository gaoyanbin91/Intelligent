package com.gao.intelligent.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Yanbin on 2018/8/20.
 * 描述:app版本实体
 */
public class AppVersionBean implements Serializable {


    /**
     * fCount : 0
     * page : 0
     * pageSize : 0
     * resultCode : 1
     * rows : [{"address":"/gk/yishengchan.apk","delFlag":0,"id":"8a948a676656c241016656d7c5d40003","name":"yishengchan.apk","version":"1"}]
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
         * address : /gk/yishengchan.apk
         * delFlag : 0
         * id : 8a948a676656c241016656d7c5d40003
         * name : yishengchan.apk
         * version : 1
         */

        private String address;
        private int delFlag;
        private String id;
        private String name;
        private String version;
        private String updateContent;

        public String getUpdateContent() {
            return updateContent;
        }

        public void setUpdateContent(String updateContent) {
            this.updateContent = updateContent;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(int delFlag) {
            this.delFlag = delFlag;
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

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }
}
