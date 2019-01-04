package com.gao.intelligent.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gaoyanbin on 2018/5/21.
 * 描述:用户信息和产线列表
 */
public class UserLineBean implements Serializable {

    /**
     * fCount : 0
     * object : {"email":"admin@qq.com","list":[{"contractDate":"2018-04-18 08:00:00","createTime":"2018-04-18 17:22:53","creater":"anonymousUser","delFlag":0,"fjCustomerId":"bd23d10b62d7cbc70162d7f1fdec000a","id":"402881e85ad53f8a015ad5ad3a000002","installationDate":"2018-04-18 08:00:00","ipcAddress":"安徽","ipcIp":"192.168.0.200","jkList":[],"leval":"2","name":"金春七线","putDate":"2018-04-18","scanTime":3000,"timeNo":"1525397112964"},{"contractDate":"2018-04-18 08:00:00","createTime":"2018-04-18 17:20:20","creater":"anonymousUser","delFlag":0,"fjCustomerId":"bd23d10b62d7cbc70162d7f25ee1000b","id":"bd23d10b62d7f65c0162d80e024e000b","installationDate":"2018-04-18 08:00:00","ipcAddress":"江阴市","ipcIp":"192.168.0.200","jkList":[],"leval":"2","name":"向阳一线","putDate":"2018-04-18","scanTime":3000,"timeNo":"2"},{"contractDate":"2018-04-18 08:00:00","createTime":"2018-04-18 17:21:38","creater":"anonymousUser","delFlag":0,"fjCustomerId":"bd23d10b62d7cbc70162d7f1fdec000a","id":"bd23d10b62d7f65c0162d80f3671000c","installationDate":"2018-04-18 08:00:00","ipcAddress":"安徽","ipcIp":"192.168.0.200","jkList":[],"leval":"2","name":"金春六线","putDate":"2018-04-18","scanTime":3000,"timeNo":"2"}],"mobile":"15121094209","sysOrgCode":"10","sysOrgId":"bd23d10b62d7cbc70162d7d78dc60001","sysOrgName":"综合服务平台","type":"0","userCname":"admin","userName":"admin"}
     * page : 0
     * pageSize : 0
     * resultCode : 1
     * resultDesc : 该用户为纺机人员
     * sCount : 0
     * total : 0
     */

    private int fCount;
    private ObjectBean object;
    private int page;
    private int pageSize;
    private String resultCode;
    private String resultDesc;
    private int sCount;
    private int total;

    public int getFCount() {
        return fCount;
    }

    public void setFCount(int fCount) {
        this.fCount = fCount;
    }

    public ObjectBean getObject() {
        return object;
    }

    public void setObject(ObjectBean object) {
        this.object = object;
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

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
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

    public static class ObjectBean {
        /**
         * email : admin@qq.com
         * list : [{"contractDate":"2018-04-18 08:00:00","createTime":"2018-04-18 17:22:53","creater":"anonymousUser","delFlag":0,"fjCustomerId":"bd23d10b62d7cbc70162d7f1fdec000a","id":"402881e85ad53f8a015ad5ad3a000002","installationDate":"2018-04-18 08:00:00","ipcAddress":"安徽","ipcIp":"192.168.0.200","jkList":[],"leval":"2","name":"金春七线","putDate":"2018-04-18","scanTime":3000,"timeNo":"1525397112964"},{"contractDate":"2018-04-18 08:00:00","createTime":"2018-04-18 17:20:20","creater":"anonymousUser","delFlag":0,"fjCustomerId":"bd23d10b62d7cbc70162d7f25ee1000b","id":"bd23d10b62d7f65c0162d80e024e000b","installationDate":"2018-04-18 08:00:00","ipcAddress":"江阴市","ipcIp":"192.168.0.200","jkList":[],"leval":"2","name":"向阳一线","putDate":"2018-04-18","scanTime":3000,"timeNo":"2"},{"contractDate":"2018-04-18 08:00:00","createTime":"2018-04-18 17:21:38","creater":"anonymousUser","delFlag":0,"fjCustomerId":"bd23d10b62d7cbc70162d7f1fdec000a","id":"bd23d10b62d7f65c0162d80f3671000c","installationDate":"2018-04-18 08:00:00","ipcAddress":"安徽","ipcIp":"192.168.0.200","jkList":[],"leval":"2","name":"金春六线","putDate":"2018-04-18","scanTime":3000,"timeNo":"2"}]
         * mobile : 15121094209
         * sysOrgCode : 10
         * sysOrgId : bd23d10b62d7cbc70162d7d78dc60001
         * sysOrgName : 综合服务平台
         * type : 0
         * userCname : admin
         * userName : admin
         */

        private String email;
        private String mobile;
        private String sysOrgCode;
        private String sysOrgId;
        private String sysOrgName;
        private String type;
        private String userCname;
        private String userName;
        private String customerId;
        private String signatureAddress;

        public String getSignatureAddress() {
            return signatureAddress;
        }

        public void setSignatureAddress(String signatureAddress) {
            this.signatureAddress = signatureAddress;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        private String id
                ;

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        private List<ListBean> list;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getSysOrgCode() {
            return sysOrgCode;
        }

        public void setSysOrgCode(String sysOrgCode) {
            this.sysOrgCode = sysOrgCode;
        }

        public String getSysOrgId() {
            return sysOrgId;
        }

        public void setSysOrgId(String sysOrgId) {
            this.sysOrgId = sysOrgId;
        }

        public String getSysOrgName() {
            return sysOrgName;
        }

        public void setSysOrgName(String sysOrgName) {
            this.sysOrgName = sysOrgName;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUserCname() {
            return userCname;
        }

        public void setUserCname(String userCname) {
            this.userCname = userCname;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * contractDate : 2018-04-18 08:00:00
             * createTime : 2018-04-18 17:22:53
             * creater : anonymousUser
             * delFlag : 0
             * fjCustomerId : bd23d10b62d7cbc70162d7f1fdec000a
             * id : 402881e85ad53f8a015ad5ad3a000002
             * installationDate : 2018-04-18 08:00:00
             * ipcAddress : 安徽
             * ipcIp : 192.168.0.200
             * jkList : []
             * leval : 2
             * name : 金春七线
             * putDate : 2018-04-18
             * scanTime : 3000
             * timeNo : 1525397112964
             */

            private String contractDate;
            private String createTime;
            private String creater;
            private int delFlag;
            private String fjCustomerId;
            private String id;
            private String installationDate;
            private String ipcAddress;
            private String ipcIp;
            private String leval;
            private String name;
            private String putDate;
            private int scanTime;
            private String timeNo;
            private List<?> jkList;

            public String getContractDate() {
                return contractDate;
            }

            public void setContractDate(String contractDate) {
                this.contractDate = contractDate;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getCreater() {
                return creater;
            }

            public void setCreater(String creater) {
                this.creater = creater;
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

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getInstallationDate() {
                return installationDate;
            }

            public void setInstallationDate(String installationDate) {
                this.installationDate = installationDate;
            }

            public String getIpcAddress() {
                return ipcAddress;
            }

            public void setIpcAddress(String ipcAddress) {
                this.ipcAddress = ipcAddress;
            }

            public String getIpcIp() {
                return ipcIp;
            }

            public void setIpcIp(String ipcIp) {
                this.ipcIp = ipcIp;
            }

            public String getLeval() {
                return leval;
            }

            public void setLeval(String leval) {
                this.leval = leval;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPutDate() {
                return putDate;
            }

            public void setPutDate(String putDate) {
                this.putDate = putDate;
            }

            public int getScanTime() {
                return scanTime;
            }

            public void setScanTime(int scanTime) {
                this.scanTime = scanTime;
            }

            public String getTimeNo() {
                return timeNo;
            }

            public void setTimeNo(String timeNo) {
                this.timeNo = timeNo;
            }

            public List<?> getJkList() {
                return jkList;
            }

            public void setJkList(List<?> jkList) {
                this.jkList = jkList;
            }
        }
    }
}
