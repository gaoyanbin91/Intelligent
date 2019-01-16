package com.gao.intelligent.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Yanbin on 2019/1/9.
 * 描述: 故障代码
 */
public class ErrorBean implements Serializable {

    /**
     * resultCode : 1
     * resultDesc : null
     * resultData : null
     * total : 0
     * page : 0
     * rows : [{"id":"04AF5A7E87F24ED4B695A9C90054E4CB","errorCode":"A30502","errorName":" 功率单元：直流母线过电压","errorType":" 直流母线过电压 (4)","errorResponse":" 无","errorAnswer":" 无","errorReason":" 禁止脉冲时，功率单元检测出直流母线过电压。- 设备输入电压过高。\\n- 电源电抗器规格错误。\\n报警值（r0949, 十进制）：\\n直流母线电压[1 位 = 100 毫伏 ]。\\n参见： r0070 ( 直流母线电压实际值)\\n","errorDispose":" - 检查设备输入电压 (p0210)。- 检查电源电抗器的规格。\\n参见： p0210 ( 设备输入电压)\\n","state":"0"}]
     * pageSize : 0
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
         * id : 04AF5A7E87F24ED4B695A9C90054E4CB
         * errorCode : A30502
         * errorName :  功率单元：直流母线过电压
         * errorType :  直流母线过电压 (4)
         * errorResponse :  无
         * errorAnswer :  无
         * errorReason :  禁止脉冲时，功率单元检测出直流母线过电压。- 设备输入电压过高。\n- 电源电抗器规格错误。\n报警值（r0949, 十进制）：\n直流母线电压[1 位 = 100 毫伏 ]。\n参见： r0070 ( 直流母线电压实际值)\n
         * errorDispose :  - 检查设备输入电压 (p0210)。- 检查电源电抗器的规格。\n参见： p0210 ( 设备输入电压)\n
         * state : 0
         */

        private String id;
        private String errorCode;
        private String errorName;
        private String errorType;
        private String errorResponse;
        private String errorAnswer;
        private String errorReason;
        private String errorDispose;
        private String state;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public String getErrorName() {
            return errorName;
        }

        public void setErrorName(String errorName) {
            this.errorName = errorName;
        }

        public String getErrorType() {
            return errorType;
        }

        public void setErrorType(String errorType) {
            this.errorType = errorType;
        }

        public String getErrorResponse() {
            return errorResponse;
        }

        public void setErrorResponse(String errorResponse) {
            this.errorResponse = errorResponse;
        }

        public String getErrorAnswer() {
            return errorAnswer;
        }

        public void setErrorAnswer(String errorAnswer) {
            this.errorAnswer = errorAnswer;
        }

        public String getErrorReason() {
            return errorReason;
        }

        public void setErrorReason(String errorReason) {
            this.errorReason = errorReason;
        }

        public String getErrorDispose() {
            return errorDispose;
        }

        public void setErrorDispose(String errorDispose) {
            this.errorDispose = errorDispose;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }
}
