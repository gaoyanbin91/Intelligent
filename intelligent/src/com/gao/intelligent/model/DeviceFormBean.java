package com.gao.intelligent.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Yanbin on 2018/12/11.
 * 描述:
 */
public class DeviceFormBean implements Serializable {

    /**
     * resultCode : 1
     * resultDesc : null
     * resultData : null
     * total : 0
     * page : 0
     * rows : null
     * pageSize : 0
     * object : {"id":"bd23d10b679bbd4001679bcad4f00000","appQuestionId":"bd23d10b679b2ab201679b677563000b","orgId":"bd23d10b62d7cbc70162d7e2ceb90007","orgName":"安徽金春无纺布股份有限公司","lineName":"金春二线","recipients":"张晓","telephone":"13637045537","createDate":1544507021000,"list":[{"id":"bd23d10b679bbd4001679bcad52f0002","pid":"bd23d10b679bbd4001679bcad4f00000","name":"回家","size":"空","remark":"555"}]}
     * sCount : 0
     * fCount : 0
     */

    private String resultCode;
    private String resultDesc;
    private String resultData;
    private int total;
    private int page;
    private Object rows;
    private int pageSize;
    private ObjectBean object;
    private int sCount;
    private int fCount;

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

    public String getResultData() {
        return resultData;
    }

    public void setResultData(String resultData) {
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

    public static class ObjectBean {
        /**
         * id : bd23d10b679bbd4001679bcad4f00000
         * appQuestionId : bd23d10b679b2ab201679b677563000b
         * orgId : bd23d10b62d7cbc70162d7e2ceb90007
         * orgName : 安徽金春无纺布股份有限公司
         * lineName : 金春二线
         * recipients : 张晓
         * telephone : 13637045537
         * createDate : 1544507021000
         * list : [{"id":"bd23d10b679bbd4001679bcad52f0002","pid":"bd23d10b679bbd4001679bcad4f00000","name":"回家","size":"空","remark":"555"}]
         */

        private String id;
        private String appQuestionId;
        private String orgId;
        private String orgName;
        private String lineName;
        private String recipients;
        private String telephone;
        private long createDate;
        private List<ListBean> list;

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

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public String getLineName() {
            return lineName;
        }

        public void setLineName(String lineName) {
            this.lineName = lineName;
        }

        public String getRecipients() {
            return recipients;
        }

        public void setRecipients(String recipients) {
            this.recipients = recipients;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public long getCreateDate() {
            return createDate;
        }

        public void setCreateDate(long createDate) {
            this.createDate = createDate;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : bd23d10b679bbd4001679bcad52f0002
             * pid : bd23d10b679bbd4001679bcad4f00000
             * name : 回家
             * size : 空
             * remark : 555
             */

            private String id;
            private String pid;
            private String name;
            private String size;
            private String remark;
            private String num;

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSize() {
                return size;
            }

            public void setSize(String size) {
                this.size = size;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }
        }
    }
}
