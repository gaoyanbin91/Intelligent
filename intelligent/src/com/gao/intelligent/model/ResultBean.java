package com.gao.intelligent.model;

import java.io.Serializable;

/**
 * Created by gaoyanbin on 2018/5/30.
 * 描述:
 */
public class ResultBean implements Serializable {

    /**
     * resultCode : 1
     * resultDesc : null
     * resultData : null
     * total : 0
     * page : 0
     * rows : null
     * pageSize : 0
     * object : {"id":"bd23d10b63aed8050163aeec114f0005","introducer":"bd23d10b62dbb7be0162dbbcb8040002","priority":"0","code":"wt1515","phone":"锕1","name":"看一下问题提出人","fjCustomerId":"bd23d10b62d7cbc70162d7f1fdec000a","fjProductionLineId":"402881e85ad53f8a015ad5ad3a000002","state":"1","type":"wt2","createTime":"2018-05-30 10:41:37","creater":"admin","delFlag":0,"questionDetail":"s'da"}
     * sCount : 0
     * fCount : 0
     */

    private String resultCode;
    private Object resultDesc;
    private Object resultData;
    private int total;
    private int page;
    private Object rows;
    private int pageSize;
    private int sCount;
    private int fCount;

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

    public Object getRows() {
        return rows;
    }

    public void setRows(Object rows) {
        this.rows = rows;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
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

}
