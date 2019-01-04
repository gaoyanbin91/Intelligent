package com.gao.intelligent.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gaoyanbin on 2018/5/23.
 * 描述:
 */
public class LineBean implements Serializable {


    /**
     * id : 402881e85ad53f8a015ad5ad3a000002
     * fjCustomerId : bd23d10b62d7cbc70162d7f1fdec000a
     * name : 金春七线
     * ipcIp : 192.168.0.200
     * ipcAddress : 安徽滁州
     * creater : anonymousUser
     * createTime : 2016-02-01 17:22:53
     * delFlag : 0
     * timeNo : 1525397112991
     * scanTime : 3000
     * contractNo : 2017-516
     * contractDate : 2017-05-05 08:00:00
     * installationDate : 2017-05-05 08:00:00
     * putDate : 2017-05-05
     * typeId : bd23d10b638566b101638590d05b000b
     * lineWidth : 1.8
     * enabled : 0
     * leval : 2
     * jkList : []
     */

    private String id;
    private String fjCustomerId;
    private String name;
    private String ipcIp;
    private String ipcAddress;
    private String creater;
    private String createTime;
    private int delFlag;
    private String timeNo;
    private int scanTime;
    private String contractNo;
    private String contractDate;
    private String installationDate;
    private String putDate;
    private String typeId;
    private String lineWidth;
    private String enabled;
    private String leval;
    private List<?> jkList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFjCustomerId() {
        return fjCustomerId;
    }

    public void setFjCustomerId(String fjCustomerId) {
        this.fjCustomerId = fjCustomerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpcIp() {
        return ipcIp;
    }

    public void setIpcIp(String ipcIp) {
        this.ipcIp = ipcIp;
    }

    public String getIpcAddress() {
        return ipcAddress;
    }

    public void setIpcAddress(String ipcAddress) {
        this.ipcAddress = ipcAddress;
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

    public String getTimeNo() {
        return timeNo;
    }

    public void setTimeNo(String timeNo) {
        this.timeNo = timeNo;
    }

    public int getScanTime() {
        return scanTime;
    }

    public void setScanTime(int scanTime) {
        this.scanTime = scanTime;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getContractDate() {
        return contractDate;
    }

    public void setContractDate(String contractDate) {
        this.contractDate = contractDate;
    }

    public String getInstallationDate() {
        return installationDate;
    }

    public void setInstallationDate(String installationDate) {
        this.installationDate = installationDate;
    }

    public String getPutDate() {
        return putDate;
    }

    public void setPutDate(String putDate) {
        this.putDate = putDate;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(String lineWidth) {
        this.lineWidth = lineWidth;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getLeval() {
        return leval;
    }

    public void setLeval(String leval) {
        this.leval = leval;
    }

    public List<?> getJkList() {
        return jkList;
    }

    public void setJkList(List<?> jkList) {
        this.jkList = jkList;
    }
}
