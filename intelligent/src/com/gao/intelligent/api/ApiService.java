package com.gao.intelligent.api;

/**
 * Created by gaoyanbin on 2018/3/26.
 * 描述:url地址
 */

public class ApiService {

    //public static final String BASE_URL = "http://";//外网
//    public static final String base = "192.168.1.2:8080/";
//    public static final String base = "192.168.0.66:8080/";
    public static final String base = "106.15.205.62:8080/";
//    public static final String base = "61.163.81.139:8090/";
    public static final String BASE_URL = "http://"+base;//外网
//    public static final String BASE_URL = "http://192.168.1.108:8080/";//龙飞

    public static final String LOGIN_UP = BASE_URL + "app/auth";// 登陆
    public static final String QUERY_USER_MEAASGE = BASE_URL + "app/employee/getEmployeeInfo";//获取个人信息

    public static final String QUERY_DEMAND_SERVICE_LIST = BASE_URL + "app/question/queryByPage";// 查询服务请求列表

    public static final String QUERY_LINE_DATAS = BASE_URL + "app/question/queryLineCombo";// 查询产线数据
    public static final String QUERY_QUS_CLASSFIY = BASE_URL + "app/question/queryCombo";// 报修——获取问题分类
    public static final String SAVE_GUARANTEE = BASE_URL + "app/question/addOrSolve";// 新增服务请求
    public static final String QUERY_LINE_VALUES = BASE_URL + "app/employee/queryParamsByLineId";//

    public static final String QUERY_PROBLEM_DETAIL = BASE_URL + "app/question/queryDetail";// 查询问题详情
    public static final String QUERY_APP_VERSON = BASE_URL + "api/v1/gkversion/download";//查询app版本
    public static final String REVISE_PASSWORD = BASE_URL + "app/employee/saveNewPwd";//修改密码
    public static final String QUERY_LADER_NAMES = BASE_URL + "app/question/getCustomerLeader";//获取审批人列表
    public static final String SAVE_SIGN_PNG = BASE_URL + "app/question/saveSignature";//上传签名文件
    public static final String QUERY_LEADER_LISTS = BASE_URL + "app/question/queryCustomerQuestion";// 领导查询
    public static final String PREVIE_CONTACT_FORM = BASE_URL + "app/question/previewBackContact";//预览联系函
    public static final String QUERY_FORM_LIST = BASE_URL + "app/question/queryBackContext";//回函表数据

    public static final String SING_HUI_NAME = BASE_URL + "app/question/customerSignatureLogistics";//厂家领导回函表签字
    public static final String QUERY_ERROR_DETAIL = BASE_URL + "app/question/queryError";//查询故障详情
}
