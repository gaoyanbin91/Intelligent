package com.gao.intelligent.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Yanbin on 2018/12/10.
 * 描述:
 */
public class LeaderNameBean implements Serializable {


    /**
     * resultCode : 1
     * resultDesc : null
     * resultData : null
     * total : 0
     * page : 0
     * rows : [{"id":"bd23d10b62dbb7be0162dbc88a770004","userName":"yangxiaoshun","userCname":"杨小顺","password":"$2a$10$9u/j3Qu83ZzZpO8VNRSwPeBd8IwgjhNXdfOuByMd8wI4gpd5Iycqe","mobile":"13955021515","email":"zhouyang@ahjinchun.com","enabled":"0","remark":"安徽金春无纺布","createName":"anonymousUser","createTime":1524105776000,"delFlag":0,"sysOrgId":"bd23d10b62d7cbc70162d7e2ceb90007","sysRoleId":"bd23d10b66e1bdc00166e27c6cf10009","deviceId":"cfd8b3d9fba9488c8933f62b97458d34"},{"id":"bd23d10b62dbb7be0162dbcb00000005","userName":"caosongting","userCname":"曹松亭","password":"$2a$10$MJzH7yDQoqzzCJs68FudjeKMk551t/MPEbZwWauWnnC2nYZtJSe26","mobile":"13955021517","email":"suntao@ahjinchun.com","enabled":"0","remark":"安徽金春无纺布","createName":"anonymousUser","createTime":1524105937000,"delFlag":0,"sysOrgId":"bd23d10b62d7cbc70162d7e2ceb90007","sysRoleId":"bd23d10b66e1bdc00166e27c6cf10009"}]
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
         * id : bd23d10b62dbb7be0162dbc88a770004
         * userName : yangxiaoshun
         * userCname : 杨小顺
         * password : $2a$10$9u/j3Qu83ZzZpO8VNRSwPeBd8IwgjhNXdfOuByMd8wI4gpd5Iycqe
         * mobile : 13955021515
         * email : zhouyang@ahjinchun.com
         * enabled : 0
         * remark : 安徽金春无纺布
         * createName : anonymousUser
         * createTime : 1524105776000
         * delFlag : 0
         * sysOrgId : bd23d10b62d7cbc70162d7e2ceb90007
         * sysRoleId : bd23d10b66e1bdc00166e27c6cf10009
         * deviceId : cfd8b3d9fba9488c8933f62b97458d34
         */

        private String id;
        private String userName;
        private String userCname;
        private String password;
        private String mobile;
        private String email;
        private String enabled;
        private String remark;
        private String createName;
        private long createTime;
        private int delFlag;
        private String sysOrgId;
        private String sysRoleId;
        private String deviceId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserCname() {
            return userCname;
        }

        public void setUserCname(String userCname) {
            this.userCname = userCname;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getEnabled() {
            return enabled;
        }

        public void setEnabled(String enabled) {
            this.enabled = enabled;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getCreateName() {
            return createName;
        }

        public void setCreateName(String createName) {
            this.createName = createName;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(int delFlag) {
            this.delFlag = delFlag;
        }

        public String getSysOrgId() {
            return sysOrgId;
        }

        public void setSysOrgId(String sysOrgId) {
            this.sysOrgId = sysOrgId;
        }

        public String getSysRoleId() {
            return sysRoleId;
        }

        public void setSysRoleId(String sysRoleId) {
            this.sysRoleId = sysRoleId;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }
    }
}
