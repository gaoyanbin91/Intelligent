package com.gao.intelligent.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Yanbin on 2018/7/16.
 * 描述:
 */
public class DrviceFileBean implements Serializable {

    /**
     * resultCode : 1
     * resultDesc : null
     * resultData : null
     * total : 0
     * page : 0
     * rows : [{"id":"0000000063429e140163437aba8f0006","name":"OPC DA Auto 2.02 开发文档.pdf","address":"//2018-05-09/a5cf6a0cfb6c4a679b5e96c395ca32c4.pdf","suffix":"pdf","realAddress":"//2018-05-09/a5cf6a0cfb6c4a679b5e96c395ca32c4.pdf","size":"2025935","uploadTime":"2018-05-09 13:58:27","fjProductManageId":"bd23d10b62d7cbc70162d7f1d20a0009","creater":"admin","createTime":"2018-05-09 13:58:27","delFlag":0},{"id":"0000000063ab83140163b00185df030f","name":"26使用手册（模板）.pdf","address":"//2018-05-30/498031dbaf09447891c9fb19173b4fec.pdf","suffix":"pdf","realAddress":"//2018-05-30/498031dbaf09447891c9fb19173b4fec.pdf","size":"370215","uploadTime":"2018-05-30 15:44:40","fjProductManageId":"bd23d10b62d7cbc70162d7f1d20a0009","creater":"admin","createTime":"2018-05-30 15:44:40","delFlag":0},{"id":"bd23d10b62db5f7d0162db7d8a890001","name":"积成水表118协议.pdf","address":"//2018-04-19/4d927216627d441aa8954227b2376069.pdf","suffix":"pdf","realAddress":"//2018-04-19/4d927216627d441aa8954227b2376069.pdf","size":"543400","uploadTime":"2018-04-19 09:21:01","fjProductManageId":"bd23d10b62d7cbc70162d7f1d20a0009","creater":"anonymousUser","createTime":"2018-04-19 09:21:01","delFlag":0},{"id":"bd23d10b631fe6d801631fe85f080000","name":"金融资料技术文档.pdf","address":"//2018-05-02/46af7ad8ab7f425fb76a1753d33fdac6.pdf","suffix":"pdf","realAddress":"//2018-05-02/46af7ad8ab7f425fb76a1753d33fdac6.pdf","size":"286286","uploadTime":"2018-05-02 16:11:48","fjProductManageId":"bd23d10b62d7cbc70162d7f1d20a0009","creater":"","createTime":"2018-05-02 16:11:52","delFlag":0},{"id":"bd23d10b6320136b0163201a82460000","name":"H5前端开发培训知识要点-water.pdf","address":"//2018-05-02/f584c89be8ce493fb72ced6568792873.pdf","suffix":"pdf","realAddress":"//2018-05-02/f584c89be8ce493fb72ced6568792873.pdf","size":"43173","uploadTime":"2018-05-02 17:06:38","fjProductManageId":"bd23d10b62d7cbc70162d7f1d20a0009","creater":"","createTime":"2018-05-02 17:06:38","delFlag":0}]
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
         * id : 0000000063429e140163437aba8f0006
         * name : OPC DA Auto 2.02 开发文档.pdf
         * address : //2018-05-09/a5cf6a0cfb6c4a679b5e96c395ca32c4.pdf
         * suffix : pdf
         * realAddress : //2018-05-09/a5cf6a0cfb6c4a679b5e96c395ca32c4.pdf
         * size : 2025935
         * uploadTime : 2018-05-09 13:58:27
         * fjProductManageId : bd23d10b62d7cbc70162d7f1d20a0009
         * creater : admin
         * createTime : 2018-05-09 13:58:27
         * delFlag : 0
         */

        private String id;
        private String name;
        private String address;
        private String suffix;
        private String realAddress;
        private String size;
        private String uploadTime;
        private String fjProductManageId;
        private String creater;
        private String createTime;
        private int delFlag;

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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getSuffix() {
            return suffix;
        }

        public void setSuffix(String suffix) {
            this.suffix = suffix;
        }

        public String getRealAddress() {
            return realAddress;
        }

        public void setRealAddress(String realAddress) {
            this.realAddress = realAddress;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getUploadTime() {
            return uploadTime;
        }

        public void setUploadTime(String uploadTime) {
            this.uploadTime = uploadTime;
        }

        public String getFjProductManageId() {
            return fjProductManageId;
        }

        public void setFjProductManageId(String fjProductManageId) {
            this.fjProductManageId = fjProductManageId;
        }

        public String getCreater() {
            return creater;
        }

        public void setCreater(String creater) {
            this.creater = creater;
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
    }
}
