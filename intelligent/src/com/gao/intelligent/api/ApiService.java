package com.gao.intelligent.api;

/**
 * Created by gaoyanbin on 2018/3/26.
 * 描述:url地址
 */

public class ApiService {

    //public static final String BASE_URL = "http://";//外网
    public static final String base = "106.15.205.62:80/";
//    public static final String base = "61.163.81.139:8080/";
    public static final String BASE_URL = "http://"+base;//外网
//    public static final String BASE_URL = "http://192.168.1.108:8080/";//龙飞

    public static final String LOGIN_UP = BASE_URL + "app/auth";// 登陆
    public static final String QUERY_USER_MEAASGE = BASE_URL + "app/employee/getEmployeeInfo";//获取个人信息

    public static final String QUERY_DEMAND_SERVICE_LIST = BASE_URL + "app/repair/queryByPage";// 查询服务请求列表

    public static final String QUERY_LINE_DATAS = BASE_URL + "app/employee/queryParamsByLineId";// 查询产线数据
    public static final String QUERY_QUS_CLASSFIY = BASE_URL + "app/repair/queryDicComboByCode";// 报修——获取问题分类
    public static final String SAVE_GUARANTEE = BASE_URL + "app/repair";// 新增服务请求


    public static final String QUERY_RECORD_LIST = BASE_URL + "app/runRecord/queryByRunPage";// 查询运行记录列表
    public static final String QUERY_RECORD_DETAIL_LIST = BASE_URL + "app/runRecord/queryByPid";// 查询运行记录详情
    public static final String SAVE_RECORD_DATA = BASE_URL + "app/runRecord/edit";// 保存补充数据
    public static final String SAVE_RECORD_DATA_FRIST = BASE_URL + "app/runRecord/save";// 保存补充数据1

    public static final String QUERY_BYID_DEMAND = BASE_URL + "app/repair/queryById";// 根据ID 查询服务请求详情
    public static final String SAVE_CHANGE_DEMAND= BASE_URL + "app/repair/edit";// 服务请求解决

    public static final String SAVE_REPLY_DATAS= BASE_URL + "app/repair/questionMessage";//服务请求回复


    public static final String QUERY_PROCESS_TEMPLATE_LIST= BASE_URL + "app/techModel/queryByLineId";// 根据产线id查询工艺模板列表
    public static final String QUERY_PROCESS_PRAAM_LIST= BASE_URL + "app/techModel/queryDetailByModelId";// 根据模板id查询工艺参数

    public static final String QUERY_PROCESS_PRAAM_IDS= BASE_URL + "api/v1/techModel/getTechIds?";// 根据产线id获取参数


    public static final String QUERY_DRVICE_LISTS= BASE_URL + "app/equip/queryByPage";// 根据产线查询设备清单-
    public static final String QUERY_DRVICE_FILES_LISTS= BASE_URL + "app/equip/queryFileByName";// 根据设备的名称和型号查询附件

    public static final String QUERY_APP_VERSON = BASE_URL + "api/v1/gkversion/download";//查询app版本
}
