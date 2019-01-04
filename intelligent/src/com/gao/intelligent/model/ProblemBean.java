package com.gao.intelligent.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Yanbin on 2018/11/7.
 * 描述: 问题实体
 */
public class ProblemBean implements Serializable {

    /**
     * resultCode : 1
     * resultDesc : null
     * resultData : null
     * total : 3
     * page : 1
     * rows : [{"id":"402880c266ec4d580166ec4ebfda0001","questionDescribe":"测试号","equipType":"梳理机","introducerId":"bd23d10b62d7cbc70162d7f1fdec000a","introducerName":"杨小顺","introducerTime":"2018-11-07 11:54:36","lineId":"bd23d10b62d7cbc70162d7f1fdec000a","lineName":"金春七线","factoryId":"bd23d10b62d7cbc70162d7e2ceb90007","factoryName":"安徽金春无纺布股份有限公司","leaderId":"bd23d10b62d7cbc70162d7f1fdec000a","leaderName":"杨小顺","processState":0,"nextState":1},{"id":"402880c266ec4d580166ec4fb00f0004","questionDescribe":"测试Hi好","equipType":"梳理机","introducerId":"bd23d10b62d7cbc70162d7f1fdec000a","introducerName":"杨小顺","introducerTime":"2018-11-07 11:55:37","lineId":"bd23d10b62d7cbc70162d7f1fdec000a","lineName":"金春七线","factoryId":"bd23d10b62d7cbc70162d7e2ceb90007","factoryName":"安徽金春无纺布股份有限公司","leaderId":"bd23d10b62d7cbc70162d7f1fdec000a","leaderName":"杨小顺","processState":0,"nextState":1},{"id":"402880c266ec4d580166ec504b3d0009","questionDescribe":"阿尔high","equipType":"梳理机","introducerId":"bd23d10b62d7cbc70162d7f1fdec000a","introducerName":"杨小顺","introducerTime":"2018-11-07 11:56:17","lineId":"bd23d10b62d7cbc70162d7f1fdec000a","lineName":"金春七线","factoryId":"bd23d10b62d7cbc70162d7e2ceb90007","factoryName":"安徽金春无纺布股份有限公司","leaderId":"bd23d10b62d7cbc70162d7f1fdec000a","leaderName":"杨小顺","processState":0,"nextState":1}]
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
         * id : 402880c266ec4d580166ec4ebfda0001
         * questionDescribe : 测试号
         * equipType : 梳理机
         * introducerId : bd23d10b62d7cbc70162d7f1fdec000a
         * introducerName : 杨小顺
         * introducerTime : 2018-11-07 11:54:36
         * lineId : bd23d10b62d7cbc70162d7f1fdec000a
         * lineName : 金春七线
         * factoryId : bd23d10b62d7cbc70162d7e2ceb90007
         * factoryName : 安徽金春无纺布股份有限公司
         * leaderId : bd23d10b62d7cbc70162d7f1fdec000a
         * leaderName : 杨小顺
         * processState : 0
         * nextState : 1
         */

        private String id;
        private String questionDescribe;
        private String equipType;
        private String introducerId;
        private String introducerName;
        private String introducerTime;
        private String lineId;
        private String lineName;
        private String factoryId;
        private String factoryName;
        private String leaderId;
        private String leaderName;
        private int processState;
        private int nextState;
        private String productPerson;
        private String salePersonTime;
        private String productPersonTime;
        private String backContactFlag;

        public String getBackContactFlag() {
            return backContactFlag;
        }

        public void setBackContactFlag(String backContactFlag) {
            this.backContactFlag = backContactFlag;
        }

        public String getSalePersonTime() {
            return salePersonTime;
        }

        public void setSalePersonTime(String salePersonTime) {
            this.salePersonTime = salePersonTime;
        }

        public String getProductPersonTime() {
            return productPersonTime;
        }

        public void setProductPersonTime(String productPersonTime) {
            this.productPersonTime = productPersonTime;
        }

        public String getSalePerson() {
            return salePerson;
        }

        public void setSalePerson(String salePerson) {
            this.salePerson = salePerson;
        }

        public String getProductPersonId() {
            return productPersonId;
        }

        public void setProductPersonId(String productPersonId) {
            this.productPersonId = productPersonId;
        }

        public String getAnswerPerson() {
            return answerPerson;
        }

        public void setAnswerPerson(String answerPerson) {
            this.answerPerson = answerPerson;
        }

        public String getAnswerPersonId() {
            return answerPersonId;
        }

        public void setAnswerPersonId(String answerPersonId) {
            this.answerPersonId = answerPersonId;
        }

        private String salePerson;
        private String productPersonId;
        private String answerPerson;
        private String answerPersonId;
        public String getProductPerson() {
            return productPerson;
        }

        public void setProductPerson(String productPerson) {
            this.productPerson = productPerson;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getQuestionDescribe() {
            return questionDescribe;
        }

        public void setQuestionDescribe(String questionDescribe) {
            this.questionDescribe = questionDescribe;
        }

        public String getEquipType() {
            return equipType;
        }

        public void setEquipType(String equipType) {
            this.equipType = equipType;
        }

        public String getIntroducerId() {
            return introducerId;
        }

        public void setIntroducerId(String introducerId) {
            this.introducerId = introducerId;
        }

        public String getIntroducerName() {
            return introducerName;
        }

        public void setIntroducerName(String introducerName) {
            this.introducerName = introducerName;
        }

        public String getIntroducerTime() {
            return introducerTime;
        }

        public void setIntroducerTime(String introducerTime) {
            this.introducerTime = introducerTime;
        }

        public String getLineId() {
            return lineId;
        }

        public void setLineId(String lineId) {
            this.lineId = lineId;
        }

        public String getLineName() {
            return lineName;
        }

        public void setLineName(String lineName) {
            this.lineName = lineName;
        }

        public String getFactoryId() {
            return factoryId;
        }

        public void setFactoryId(String factoryId) {
            this.factoryId = factoryId;
        }

        public String getFactoryName() {
            return factoryName;
        }

        public void setFactoryName(String factoryName) {
            this.factoryName = factoryName;
        }

        public String getLeaderId() {
            return leaderId;
        }

        public void setLeaderId(String leaderId) {
            this.leaderId = leaderId;
        }

        public String getLeaderName() {
            return leaderName;
        }

        public void setLeaderName(String leaderName) {
            this.leaderName = leaderName;
        }

        public int getProcessState() {
            return processState;
        }

        public void setProcessState(int processState) {
            this.processState = processState;
        }

        public int getNextState() {
            return nextState;
        }

        public void setNextState(int nextState) {
            this.nextState = nextState;
        }
    }
}
