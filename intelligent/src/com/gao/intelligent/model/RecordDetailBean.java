package com.gao.intelligent.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gaoyanbin on 2018/6/12.
 * 描述:运行记录详情
 */
public class RecordDetailBean implements Serializable {


    /**
     * resultCode : 1
     * resultDesc : null
     * resultData : null
     * total : 1
     * page : 1
     * rows : [{"id":"bd23d10b63deea300163e057302b01db","scDeviceRunningId":"bd23d10b63deea300163e057302301da","stopTime":70,"recordDate":"2018-06-08 00:00:00","startDate":"2018-06-08 10:32:20","endDate":"2018-06-08 11:42:49"}]
     * pageSize : 10
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
         * id : bd23d10b63deea300163e057302b01db
         * scDeviceRunningId : bd23d10b63deea300163e057302301da
         * stopTime : 70
         * recordDate : 2018-06-08 00:00:00
         * startDate : 2018-06-08 10:32:20
         * endDate : 2018-06-08 11:42:49
         */

        private String id;
        private String scDeviceRunningId;
        private int stopTime;
        private String recordDate;
        private String startDate;
        private String endDate;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getScDeviceRunningId() {
            return scDeviceRunningId;
        }

        public void setScDeviceRunningId(String scDeviceRunningId) {
            this.scDeviceRunningId = scDeviceRunningId;
        }

        public int getStopTime() {
            return stopTime;
        }

        public void setStopTime(int stopTime) {
            this.stopTime = stopTime;
        }

        public String getRecordDate() {
            return recordDate;
        }

        public void setRecordDate(String recordDate) {
            this.recordDate = recordDate;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }
    }
}
