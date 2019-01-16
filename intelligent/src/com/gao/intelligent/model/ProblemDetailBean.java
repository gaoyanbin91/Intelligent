package com.gao.intelligent.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Yanbin on 2018/11/8.
 * 描述:问题详情实体
 */
public class ProblemDetailBean implements Serializable {

    /**
     * resultCode : 1
     * resultDesc : null
     * resultData : null
     * total : 0
     * page : 0
     * rows : [{"id":"402880c26710b7bf016710c6455d0001","appQuestionId":"402880c26710b7bf016710c6450c0000","beforeState":0,"afterState":1,"leaderTime":"2018-11-14 13:51:29","leaderId":"bd23d10b62d7cbc70162d7f1fdec000a","leaderName":"杨小顺","remark":"金春七线","state":0}]
     * pageSize : 0
     * object : {"id":"402880c26710b7bf016710c6450c0000","questionDescribe":"测试号","equipType":"水循环","introducerId":"bd23d10b62d7cbc70162d7f1fdec000a","introducerName":"杨小顺","introducerTel":"13955021515","introducerTime":"2018-11-14 13:51:29","lineId":"402881e85ad53f8a015ad5ad3a000002","lineName":"金春七线","factoryId":"bd23d10b62d7cbc70162d7e2ceb90007","factoryName":"安徽金春无纺布股份有限公司","placeOnFile":0,"leaderId":"bd23d10b62d7cbc70162d7f1fdec000a","leaderName":"杨小顺","leaderTime":"2018-11-14 13:51:29","processState":1,"version":0,"appQuestionPic":[{"id":"402880c26710b7bf016710c645cc0002","appQuestionId":"402880c26710b7bf016710c6450c0000","address":"//2018-11-14/4252f21cfc7d4eb6a568515e642d674b.png","personType":"1","uploadTime":"2018-11-14 13:51:29","type":"1"},{"id":"402880c26710b7bf016710c645d30003","appQuestionId":"402880c26710b7bf016710c6450c0000","address":"//2018-11-14/a01048a7c1414afc8173ecc595200b76.mp4","personType":"1","uploadTime":"2018-11-14 13:51:29","type":"2"}]}
     * sCount : 0
     * fCount : 0
     * object1 : null
     */

    private String resultCode;
    private Object resultDesc;
    private Object resultData;
    private int total;
    private int page;
    private int pageSize;
    private ObjectBean object;
    private int sCount;
    private int fCount;
    private Object object1;
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

    public ObjectBean getObject() {
        return object;
    }

    public void setObject(ObjectBean object) {
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

    public Object getObject1() {
        return object1;
    }

    public void setObject1(Object object1) {
        this.object1 = object1;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class ObjectBean {
        /**
         * id : 402880c26710b7bf016710c6450c0000
         * questionDescribe : 测试号
         * equipType : 水循环
         * introducerId : bd23d10b62d7cbc70162d7f1fdec000a
         * introducerName : 杨小顺
         * introducerTel : 13955021515
         * introducerTime : 2018-11-14 13:51:29
         * lineId : 402881e85ad53f8a015ad5ad3a000002
         * lineName : 金春七线
         * factoryId : bd23d10b62d7cbc70162d7e2ceb90007
         * factoryName : 安徽金春无纺布股份有限公司
         * placeOnFile : 0
         * leaderId : bd23d10b62d7cbc70162d7f1fdec000a
         * leaderName : 杨小顺
         * leaderTime : 2018-11-14 13:51:29
         * processState : 1
         * version : 0
         * appQuestionPic : [{"id":"402880c26710b7bf016710c645cc0002","appQuestionId":"402880c26710b7bf016710c6450c0000","address":"//2018-11-14/4252f21cfc7d4eb6a568515e642d674b.png","personType":"1","uploadTime":"2018-11-14 13:51:29","type":"1"},{"id":"402880c26710b7bf016710c645d30003","appQuestionId":"402880c26710b7bf016710c6450c0000","address":"//2018-11-14/a01048a7c1414afc8173ecc595200b76.mp4","personType":"1","uploadTime":"2018-11-14 13:51:29","type":"2"}]
         */

        private String id;
        private String questionDescribe;
        private String equipType;
        private String introducerId;
        private String introducerName;
        private String introducerTel;
        private String introducerTime;
        private String lineId;
        private String lineName;
        private String factoryId;
        private String factoryName;
        private int placeOnFile;
        private String leaderId;
        private String leaderName;
        private String leaderTime;
        private int processState;
        private String version;
        private String answer;
        private String salePerson;
        private String productPerson;
        private String backContactFlag;
        private String compoentNum;
        private String errorCode;

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public String getCompoentNum() {
            return compoentNum;
        }

        public void setCompoentNum(String compoentNum) {
            this.compoentNum = compoentNum;
        }

        public String getBackContactFlag() {
            return backContactFlag;
        }

        public void setBackContactFlag(String backContactFlag) {
            this.backContactFlag = backContactFlag;
        }

        public String getSalePerson() {
            return salePerson;
        }

        public void setSalePerson(String salePerson) {
            this.salePerson = salePerson;
        }

        public String getProductPerson() {
            return productPerson;
        }

        public void setProductPerson(String productPerson) {
            this.productPerson = productPerson;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        private List<AppQuestionPicBean> appQuestionPic;

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

        public String getIntroducerTel() {
            return introducerTel;
        }

        public void setIntroducerTel(String introducerTel) {
            this.introducerTel = introducerTel;
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

        public int getPlaceOnFile() {
            return placeOnFile;
        }

        public void setPlaceOnFile(int placeOnFile) {
            this.placeOnFile = placeOnFile;
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

        public String getLeaderTime() {
            return leaderTime;
        }

        public void setLeaderTime(String leaderTime) {
            this.leaderTime = leaderTime;
        }

        public int getProcessState() {
            return processState;
        }

        public void setProcessState(int processState) {
            this.processState = processState;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public List<AppQuestionPicBean> getAppQuestionPic() {
            return appQuestionPic;
        }

        public void setAppQuestionPic(List<AppQuestionPicBean> appQuestionPic) {
            this.appQuestionPic = appQuestionPic;
        }

        public static class AppQuestionPicBean {
            /**
             * id : 402880c26710b7bf016710c645cc0002
             * appQuestionId : 402880c26710b7bf016710c6450c0000
             * address : //2018-11-14/4252f21cfc7d4eb6a568515e642d674b.png
             * personType : 1
             * uploadTime : 2018-11-14 13:51:29
             * type : 1
             */

            private String id;
            private String appQuestionId;
            private String address;
            private String personType;
            private String uploadTime;
            private String type;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getAppQuestionId() {
                return appQuestionId;
            }

            public void setAppQuestionId(String appQuestionId) {
                this.appQuestionId = appQuestionId;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getPersonType() {
                return personType;
            }

            public void setPersonType(String personType) {
                this.personType = personType;
            }

            public String getUploadTime() {
                return uploadTime;
            }

            public void setUploadTime(String uploadTime) {
                this.uploadTime = uploadTime;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }

    public static class RowsBean {
        /**
         * id : 402880c26710b7bf016710c6455d0001
         * appQuestionId : 402880c26710b7bf016710c6450c0000
         * beforeState : 0
         * afterState : 1
         * leaderTime : 2018-11-14 13:51:29
         * leaderId : bd23d10b62d7cbc70162d7f1fdec000a
         * leaderName : 杨小顺
         * remark : 金春七线
         * state : 0
         */

        private String id;
        private String appQuestionId;
        private int beforeState;
        private int afterState;
        private String leaderTime;
        private String leaderId;
        private String leaderName;
        private String remark;
        private int state;



        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAppQuestionId() {
            return appQuestionId;
        }

        public void setAppQuestionId(String appQuestionId) {
            this.appQuestionId = appQuestionId;
        }

        public int getBeforeState() {
            return beforeState;
        }

        public void setBeforeState(int beforeState) {
            this.beforeState = beforeState;
        }

        public int getAfterState() {
            return afterState;
        }

        public void setAfterState(int afterState) {
            this.afterState = afterState;
        }

        public String getLeaderTime() {
            return leaderTime;
        }

        public void setLeaderTime(String leaderTime) {
            this.leaderTime = leaderTime;
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

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }
}
