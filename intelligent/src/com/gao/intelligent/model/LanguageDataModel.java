package com.gao.intelligent.model;


import com.gao.intelligent.config.Comm;
import com.gao.intelligent.config.Global;

import java.io.Serializable;

/**
 * Created by gaoyanbin on 2018/4/20.
 * 描述:
 */
public class LanguageDataModel implements Serializable {

    public String id;
    public String code;
    public String simplified;
    public String traditional;
    public String english;

    public String fristChar;




    public String getLanguageDisplay(){

        if(Global.LANGUAGE_TYPE.equals(Comm.SIMPLIFIED_CHINESE)){
            return simplified;
        }else if(Global.LANGUAGE_TYPE.equals(Comm.TRADITIONAL_CHINESE)){
            return traditional;
        }else if(Global.LANGUAGE_TYPE.equals(Comm.ENGLISH)){
            return english;
        }

        return simplified;
    }


    @Override
    public boolean equals(Object model) {

        LanguageDataModel tempModel = (LanguageDataModel)model;

        if(tempModel.code.equals(this.code))
            return true;
        else
            return false;

    }

    @Override
    public String toString() {
        return "LanguageDataModel{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", simplified='" + simplified + '\'' +
                ", traditional='" + traditional + '\'' +
                ", english='" + english + '\'' +
                ", fristChar='" + fristChar + '\'' +
                '}';
    }
}
