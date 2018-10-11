package com.gao.intelligent.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gaoyanbin on 2018/5/29.
 * 描述: 服务请求详情实体
 */
public class DemandDetailBean implements Serializable {

    /**
     * fCount : 0
     * object : {"code":"ks2","createTime":"2018-05-28 16:52:00","creater":"杨小顺","delFlag":0,"detailList":[{"content":"123123","createTime":"2018-05-29 11:03:59","creater":"杨小顺","delFlag":0,"fjQuestionId":"402881f063a5efeb0163a5f2755c0001","id":"bd23d10b63a58aa30163a9da328c01ee","picList":[{"address":"//2018-05-29/a24ad0cf87ea40129165dd423521802e.png","fjQuestionDetailId":"bd23d10b63a58aa30163a9da328c01ee","id":"bd23d10b63a58aa30163a9d9e98a01ed","type":"1","uploadTime":"2018-05-29 11:03:41"}],"queFlag":"0"},{"content":"狂鼠2号来袭","createTime":"2018-05-28 16:52:01","creater":"杨小顺","delFlag":0,"fjQuestionId":"402881f063a5efeb0163a5f2755c0001","id":"402881f063a5efeb0163a5f2757f0002","picList":[{"address":"//2018-05-28/a1a49bd3286242db82597a2d97da2d10.png","fjQuestionDetailId":"402881f063a5efeb0163a5f2757f0002","id":"402881f063a63c5b0163a63edd990003","type":"1","uploadTime":"2018-05-28 18:15:28"},{"address":"//2018-05-28/4e86304122f849b19a461c58ddb69c4a.mp4","fjQuestionDetailId":"402881f063a5efeb0163a5f2757f0002","id":"402881f063a63c5b0163a640f84b0006","type":"2","uploadTime":"2018-05-28 18:17:46"},{"address":"//2018-05-28/a1a49bd3286242db82597a2d97da2d10.png","fjQuestionDetailId":"402881f063a5efeb0163a5f2757f0002","id":"bd23d10b63a437be0163a483787e00ed","type":"1","uploadTime":"2018-05-28 10:11:10"}],"queFlag":"0"},{"content":"222","creater":"杨小顺","delFlag":0,"fjQuestionId":"402881f063a5efeb0163a5f2755c0001","id":"2321","picList":[]}],"fjCustomerId":"bd23d10b62d7cbc70162d7f1fdec000a","fjProductionLineId":"402881e85ad53f8a015ad5ad3a000002","id":"402881f063a5efeb0163a5f2755c0001","introducer":"bd23d10b62dbb7be0162dbc88a770004","name":"狂鼠2号","phone":"17839056840","priority":"重要","questionDetail":"狂鼠2号来袭","state":"0","type":"问题一"}
     * page : 0
     * pageSize : 0
     * resultCode : 1
     * sCount : 0
     * total : 0
     */

    private int fCount;
    private ObjectBean object;
    private int page;
    private int pageSize;
    private String resultCode;
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
         * code : ks2
         * createTime : 2018-05-28 16:52:00
         * creater : 杨小顺
         * delFlag : 0
         * detailList : [{"content":"123123","createTime":"2018-05-29 11:03:59","creater":"杨小顺","delFlag":0,"fjQuestionId":"402881f063a5efeb0163a5f2755c0001","id":"bd23d10b63a58aa30163a9da328c01ee","picList":[{"address":"//2018-05-29/a24ad0cf87ea40129165dd423521802e.png","fjQuestionDetailId":"bd23d10b63a58aa30163a9da328c01ee","id":"bd23d10b63a58aa30163a9d9e98a01ed","type":"1","uploadTime":"2018-05-29 11:03:41"}],"queFlag":"0"},{"content":"狂鼠2号来袭","createTime":"2018-05-28 16:52:01","creater":"杨小顺","delFlag":0,"fjQuestionId":"402881f063a5efeb0163a5f2755c0001","id":"402881f063a5efeb0163a5f2757f0002","picList":[{"address":"//2018-05-28/a1a49bd3286242db82597a2d97da2d10.png","fjQuestionDetailId":"402881f063a5efeb0163a5f2757f0002","id":"402881f063a63c5b0163a63edd990003","type":"1","uploadTime":"2018-05-28 18:15:28"},{"address":"//2018-05-28/4e86304122f849b19a461c58ddb69c4a.mp4","fjQuestionDetailId":"402881f063a5efeb0163a5f2757f0002","id":"402881f063a63c5b0163a640f84b0006","type":"2","uploadTime":"2018-05-28 18:17:46"},{"address":"//2018-05-28/a1a49bd3286242db82597a2d97da2d10.png","fjQuestionDetailId":"402881f063a5efeb0163a5f2757f0002","id":"bd23d10b63a437be0163a483787e00ed","type":"1","uploadTime":"2018-05-28 10:11:10"}],"queFlag":"0"},{"content":"222","creater":"杨小顺","delFlag":0,"fjQuestionId":"402881f063a5efeb0163a5f2755c0001","id":"2321","picList":[]}]
         * fjCustomerId : bd23d10b62d7cbc70162d7f1fdec000a
         * fjProductionLineId : 402881e85ad53f8a015ad5ad3a000002
         * id : 402881f063a5efeb0163a5f2755c0001
         * introducer : bd23d10b62dbb7be0162dbc88a770004
         * name : 狂鼠2号
         * phone : 17839056840
         * priority : 重要
         * questionDetail : 狂鼠2号来袭
         * state : 0
         * type : 问题一
         */

        private String code;
        private String createTime;
        private String creater;
        private int delFlag;
        private String fjCustomerId;
        private String fjProductionLineId;
        private String id;
        private String introducer;
        private String name;
        private String phone;
        private String priority;
        private String questionDetail;
        private String state;
        private String type;
        private String typeName;

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getTypeName() {

            return typeName;
        }

        private List<DetailListBean> detailList;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
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

        public String getFjProductionLineId() {
            return fjProductionLineId;
        }

        public void setFjProductionLineId(String fjProductionLineId) {
            this.fjProductionLineId = fjProductionLineId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIntroducer() {
            return introducer;
        }

        public void setIntroducer(String introducer) {
            this.introducer = introducer;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPriority() {
            return priority;
        }

        public void setPriority(String priority) {
            this.priority = priority;
        }

        public String getQuestionDetail() {
            return questionDetail;
        }

        public void setQuestionDetail(String questionDetail) {
            this.questionDetail = questionDetail;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<DetailListBean> getDetailList() {
            return detailList;
        }

        public void setDetailList(List<DetailListBean> detailList) {
            this.detailList = detailList;
        }

        public static class DetailListBean {
            /**
             * content : 123123
             * createTime : 2018-05-29 11:03:59
             * creater : 杨小顺
             * delFlag : 0
             * fjQuestionId : 402881f063a5efeb0163a5f2755c0001
             * id : bd23d10b63a58aa30163a9da328c01ee
             * picList : [{"address":"//2018-05-29/a24ad0cf87ea40129165dd423521802e.png","fjQuestionDetailId":"bd23d10b63a58aa30163a9da328c01ee","id":"bd23d10b63a58aa30163a9d9e98a01ed","type":"1","uploadTime":"2018-05-29 11:03:41"}]
             * queFlag : 0
             */

            private String content;
            private String createTime;
            private String creater;
            private int delFlag;
            private String fjQuestionId;
            private String id;
            private String queFlag;
            private List<PicListBean> picList;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
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

            public String getFjQuestionId() {
                return fjQuestionId;
            }

            public void setFjQuestionId(String fjQuestionId) {
                this.fjQuestionId = fjQuestionId;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getQueFlag() {
                return queFlag;
            }

            public void setQueFlag(String queFlag) {
                this.queFlag = queFlag;
            }

            public List<PicListBean> getPicList() {
                return picList;
            }

            public void setPicList(List<PicListBean> picList) {
                this.picList = picList;
            }

            public static class PicListBean {
                /**
                 * address : //2018-05-29/a24ad0cf87ea40129165dd423521802e.png
                 * fjQuestionDetailId : bd23d10b63a58aa30163a9da328c01ee
                 * id : bd23d10b63a58aa30163a9d9e98a01ed
                 * type : 1
                 * uploadTime : 2018-05-29 11:03:41
                 */

                private String address;
                private String fjQuestionDetailId;
                private String id;
                private String type;
                private String uploadTime;

                public String getAddress() {
                    return address;
                }

                public void setAddress(String address) {
                    this.address = address;
                }

                public String getFjQuestionDetailId() {
                    return fjQuestionDetailId;
                }

                public void setFjQuestionDetailId(String fjQuestionDetailId) {
                    this.fjQuestionDetailId = fjQuestionDetailId;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getUploadTime() {
                    return uploadTime;
                }

                public void setUploadTime(String uploadTime) {
                    this.uploadTime = uploadTime;
                }
            }
        }
    }
}
