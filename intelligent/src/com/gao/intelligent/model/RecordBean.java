package com.gao.intelligent.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gaoyanbin on 2018/6/12.
 * 描述:运行记录实体
 */
public class RecordBean implements Serializable {

    /**
     * page : 1
     * resultCode : 1
     * rows : [{"beginEnd":"2018-06-11 08:00:00-2018-06-11 20:00:00","createTime":"2018-06-12 01:00:00","delFlag":0,"fjCustomerId":"bd23d10b62d7cbc70162d7f1fdec000a","fjProductionLineId":"402881e85ad53f8a015ad5ad3a000002","groupNo":"甲组","id":"bd23d10b63ee4ba50163efca43ed02f3","production":"0","recordDate":"2018-06-11 00:00:00","runLength":0,"stopLength":720},{"beginEnd":"2018-06-07 20:00:00-2018-06-08 08:00:00","createTime":"2018-06-09 01:00:00","delFlag":0,"fjCustomerId":"bd23d10b62d7cbc70162d7f1fdec000a","fjProductionLineId":"402881e85ad53f8a015ad5ad3a000002","groupNo":"乙组","id":"bd23d10b63deea300163e0572f9601d2","production":"0","recordDate":"2018-06-07 00:00:00","runLength":285,"stopLength":435},{"beginEnd":"2018-06-08 08:00:00-2018-06-08 20:00:00","createTime":"2018-06-09 01:00:00","delFlag":0,"fjCustomerId":"bd23d10b62d7cbc70162d7f1fdec000a","fjProductionLineId":"402881e85ad53f8a015ad5ad3a000002","groupNo":"甲组","id":"bd23d10b63deea300163e057302301da","production":"0","recordDate":"2018-06-08 00:00:00","runLength":650,"stopLength":70},{"beginEnd":"2018-06-05 20:00:00-2018-06-06 08:00:00","createTime":"2018-06-07 01:00:00","delFlag":0,"fjCustomerId":"bd23d10b62d7cbc70162d7f1fdec000a","fjProductionLineId":"402881e85ad53f8a015ad5ad3a000002","groupNo":"乙组","id":"0000000063d3f7f00163d60a76b601bc","production":"0","recordDate":"2018-06-05 00:00:00","runLength":707,"stopLength":13},{"beginEnd":"2018-06-05 08:00:00-2018-06-05 20:00:00","createTime":"2018-06-06 01:00:00","delFlag":0,"fjCustomerId":"bd23d10b62d7cbc70162d7f1fdec000a","fjProductionLineId":"402881e85ad53f8a015ad5ad3a000002","groupNo":"甲组","id":"0000000063cebe550163d0e41ab4027a","production":"0","recordDate":"2018-06-05 00:00:00","runLength":629,"stopLength":91},{"beginEnd":"2018-06-04 08:00:00-2018-06-04 20:00:00","createTime":"2018-06-05 01:00:00","delFlag":0,"fjCustomerId":"bd23d10b62d7cbc70162d7f1fdec000a","fjProductionLineId":"402881e85ad53f8a015ad5ad3a000002","groupNo":"甲组","id":"0000000063ca77c50163cbbdbed9010a","production":"0","recordDate":"2018-06-04 00:00:00","runLength":719,"stopLength":1},{"beginEnd":"2018-06-01 08:00:00-2018-06-01 20:00:00","createTime":"2018-06-02 01:00:00","delFlag":0,"fjCustomerId":"bd23d10b62d7cbc70162d7f1fdec000a","fjProductionLineId":"402881e85ad53f8a015ad5ad3a000002","groupNo":"甲组","id":"0000000063bad5db0163bc4aaaca0238","production":"0","recordDate":"2018-06-01 00:00:00","runLength":718,"stopLength":2},{"beginEnd":"2018-05-30 20:00:00-2018-05-31 08:00:00","createTime":"2018-06-01 01:00:00","delFlag":0,"fjCustomerId":"bd23d10b62d7cbc70162d7f1fdec000a","fjProductionLineId":"402881e85ad53f8a015ad5ad3a000002","groupNo":"乙组","id":"0000000063b5a68d0163b7244eda01d9","production":"0","recordDate":"2018-05-30 00:00:00","runLength":715,"stopLength":5}]
     * total : 23
     */

    private int fCount;
    private int page;
    private int pageSize;
    private String resultCode;
    private int sCount;
    private int total;
    private List<RowsBean> rows;

    public void setfCount(int fCount) {
        this.fCount = fCount;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setsCount(int sCount) {
        this.sCount = sCount;
    }

    public int getfCount() {

        return fCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getsCount() {
        return sCount;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
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
         * beginEnd : 2018-06-11 08:00:00-2018-06-11 20:00:00
         * createTime : 2018-06-12 01:00:00
         * delFlag : 0
         * fjCustomerId : bd23d10b62d7cbc70162d7f1fdec000a
         * fjProductionLineId : 402881e85ad53f8a015ad5ad3a000002
         * groupNo : 甲组
         * id : bd23d10b63ee4ba50163efca43ed02f3
         * production : 0
         * recordDate : 2018-06-11 00:00:00
         * runLength : 0
         * stopLength : 720
         */

        private String beginEnd;
        private String createTime;
        private int delFlag;
        private String fjCustomerId;
        private String fjProductionLineId;
        private String groupNo;
        private String id;
        private String production;
        private String recordDate;
        private int runLength;
        private int stopLength;

        public String getBeginEnd() {
            return beginEnd;
        }

        public void setBeginEnd(String beginEnd) {
            this.beginEnd = beginEnd;
        }

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

        public String getGroupNo() {
            return groupNo;
        }

        public void setGroupNo(String groupNo) {
            this.groupNo = groupNo;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getProduction() {
            return production;
        }

        public void setProduction(String production) {
            this.production = production;
        }

        public String getRecordDate() {
            return recordDate;
        }

        public void setRecordDate(String recordDate) {
            this.recordDate = recordDate;
        }

        public int getRunLength() {
            return runLength;
        }

        public void setRunLength(int runLength) {
            this.runLength = runLength;
        }

        public int getStopLength() {
            return stopLength;
        }

        public void setStopLength(int stopLength) {
            this.stopLength = stopLength;
        }
    }
}
