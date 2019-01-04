package com.gao.intelligent.model;

import java.io.Serializable;

/**
 * Created by Yanbin on 2018/11/22.
 * 描述:
 */
public class NoticeBean implements Serializable {


    /**
     * id : 8a948a676738f81a0167390195870003
     * _ALIYUN_NOTIFICATION_ID_ : 482065
     * url : open
     */

    private String id;
    private String _ALIYUN_NOTIFICATION_ID_;
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String get_ALIYUN_NOTIFICATION_ID_() {
        return _ALIYUN_NOTIFICATION_ID_;
    }

    public void set_ALIYUN_NOTIFICATION_ID_(String _ALIYUN_NOTIFICATION_ID_) {
        this._ALIYUN_NOTIFICATION_ID_ = _ALIYUN_NOTIFICATION_ID_;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
