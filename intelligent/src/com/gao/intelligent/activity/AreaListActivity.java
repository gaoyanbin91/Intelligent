package com.gao.intelligent.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.TypeReference;
import com.gao.intelligent.R;
import com.gao.intelligent.adapter.AreaListAdapter;
import com.gao.intelligent.base.BaseActivity;
import com.gao.intelligent.model.LanguageDataModel;
import com.gao.intelligent.utils.LogUtils;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by gaoyanbin on 2018/4/20.
 * 描述: 地 域 页 面
 */
public class AreaListActivity extends BaseActivity implements  AdapterView.OnItemClickListener  {

    private static final String TAG = AreaListActivity.class.getName();
    private AreaListAdapter areaListAdapter;
    @BindView(R.id.listview)
    ListView mListView;


    @Override
    protected int provideContentViewId() {
        return R.layout.activity_area_list;
    }


    @Override
    public void initData() {

        try{
            InputStream inputStream =  getResources().getAssets().open("regcode.txt");
            InputStreamReader inputStreamReader = null;
            try {
                inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
            BufferedReader reader = new BufferedReader(inputStreamReader);
            StringBuffer sb = new StringBuffer("");
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    LogUtils.info("line==>"+line);
                    sb.append(line);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            LogUtils.info(sb.toString());
            Gson gson = new Gson();
            List<LanguageDataModel> mList = gson.fromJson(sb.toString(),new TypeReference<List<LanguageDataModel>>(){}.getType());
            areaListAdapter = new AreaListAdapter(this,mList);
            mListView.setAdapter(areaListAdapter);

        }catch (Throwable e){
            LogUtils.e(TAG,e);
        }
        initListeren();
    }

    private void initListeren() {
        try{
            mListView.setOnItemClickListener(this);
        }catch (Throwable e){
            LogUtils.e(TAG,e);
        }
    }

    @OnClick({R.id.toolbar})
    public void onClick(){
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LanguageDataModel cityModel = (LanguageDataModel) parent.getItemAtPosition(position);
        Intent intent = new Intent();
        intent.putExtra("language", cityModel);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }
}
