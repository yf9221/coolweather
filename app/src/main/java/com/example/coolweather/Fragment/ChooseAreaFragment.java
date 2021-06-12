package com.example.coolweather.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.coolweather.MainActivity;
import com.example.coolweather.R;
import com.example.coolweather.WeatherActivity;
import com.example.coolweather.db.City;
import com.example.coolweather.db.County;
import com.example.coolweather.db.Province;
import com.example.coolweather.util.HttpUtil;
import com.example.coolweather.util.Utility;
import org.jetbrains.annotations.NotNull;
import org.litepal.LitePal;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class ChooseAreaFragment extends Fragment {

    public static final int LEVEL_PROVINCE=0;
    public static final int LEVEL_CITY=1;
    public static final int LEVEL_COUNTY=2;
    public static boolean isMunicipality =false;
    private static final String TAG = "ChooseAreaFragment";
    private TextView titleText;
    private Button backButton;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> dataList=new ArrayList<>();

    /**
     * 省列表
     */
  private List<Province> proninceList;
    /**
     * 市列表
     */
    private List<City> cityList;
    /**
     * 县列表
     */
    private List<County> countyList;
    /**
     * 选中的省份
     */
    private Province selectedProvince;
    /**
     * 选中的城市
     */
    private City selectedCity;
    /**
     * 当前选中的级别
     */
    private int CurrentLevel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.choose_area,container,false);
        titleText =view.findViewById(R.id.title_text);
        backButton=view.findViewById(R.id.back_button);
        listView=view.findViewById(R.id.list_view);
        adapter=new ArrayAdapter<>(getContext(),R.layout.support_simple_spinner_dropdown_item,dataList);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (CurrentLevel == LEVEL_PROVINCE) {
                    selectedProvince=proninceList.get(position);
                    if (selectedProvince.getProvinceName().equals("北京")) {
                        isMunicipality=true;
                        queryCounties();
                    }else if (selectedProvince.getProvinceName().equals("重庆")){
                        isMunicipality=true;
                        queryCounties();
                    }else if (selectedProvince.getProvinceName().equals("天津")){
                        isMunicipality=true;
                        queryCounties();
                    }else if (selectedProvince.getProvinceName().equals("上海")){
                        isMunicipality=true;
                        queryCounties();
                    }else {
                        isMunicipality=false;
                        queryCities();
                    }

                }else if(CurrentLevel==LEVEL_CITY){
                    isMunicipality=false;
                    selectedCity=cityList.get(position);
                    queryCounties();
                }else if(CurrentLevel==LEVEL_COUNTY){
                    String weatherId=countyList.get(position).getLocationId();
                    String countyName=countyList.get(position).getCountyName();
                    if (getActivity() instanceof MainActivity) {
                        Intent intent=new Intent(getActivity(), WeatherActivity.class);
                        intent.putExtra("locationId",weatherId);
                        intent.putExtra("countyName",countyName);
                        startActivity(intent);
                        getActivity().finish();
                    }else if (getActivity() instanceof WeatherActivity){
                        WeatherActivity activity= (WeatherActivity) getActivity();
                        activity.mDrawerLayout.closeDrawers();
                        activity.mSwipeRefreshLayout.setRefreshing(true);
                        activity.setCountyName(countyName);
                        activity.requestWeather(weatherId);
                    }

                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CurrentLevel==LEVEL_COUNTY) {
                    if (isMunicipality) {
                        queryProvinces();
                    }else {
                        queryCities();
                    }

                }else if(CurrentLevel==LEVEL_CITY){
                    queryProvinces();
                }
            }
        });
        queryProvinces();
    }



    /**
     * 查询全国所有的省，优先从数据库查询，如果没有查询到再去服务器上查询
     */
    private void queryProvinces() {
        titleText.setText("中国");
        backButton.setVisibility(View.GONE);
        proninceList = LitePal.findAll(Province.class);
        if (proninceList.size()>0){
            dataList.clear();
            for (Province province : proninceList) {
                dataList.add(province.getProvinceName());

            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            CurrentLevel=LEVEL_PROVINCE;
       }else {
            String address="https://api.jisuapi.com/area/province?appkey=1fa9f17141372938";
            queryFromServer(address,"province");
       }
    }

    /**
     * 查询选中市内所有的县,优先从数据库查询,如果没有查询到再去服务器上查询
     */
    private void queryCounties() {
        backButton.setVisibility(View.VISIBLE);
        if (!isMunicipality) {
            titleText.setText(selectedCity.getCityName());
            countyList = LitePal.where("cityCode=?",String.valueOf(selectedCity.getCityCode())).find(County.class);
        }else {
            countyList = LitePal.where("cityCode=?",String.valueOf(selectedProvince.getProvinceCode())).find(County.class);
            titleText.setText(selectedProvince.getProvinceName());
        }
        if (countyList.size()>0){
            dataList.clear();
            for (County county : countyList) {
                if(county.getLocationId()!=null){
                    dataList.add(county.getCountyName());
                    Log.d(TAG, county.getCountyName()+": locationId为"+county.getLocationId());
                }

            }
             adapter.notifyDataSetChanged();
            listView.setSelection(0);
            CurrentLevel=LEVEL_COUNTY;

        }else {
            String cityCode=null;
            if (!isMunicipality) {
                 cityCode = selectedCity.getCityCode();
            }else {
                 cityCode = selectedProvince.getProvinceCode();

            }
            String address="https://api.jisuapi.com/area/town?parentid="+cityCode+"&appkey=1fa9f17141372938";
            queryFromServer(address,"county");
        }

    }

    /**
     * 查询选中的省中有所的市,优先从数据库查询,如果没有查询到再去服务器上查询
     */
    private void queryCities() {
        titleText.setText(selectedProvince.getProvinceName());
        backButton.setVisibility(View.VISIBLE);
        cityList = LitePal.where("provinceCode=?",String.valueOf(selectedProvince.getProvinceCode())).find(City.class);
        if (cityList.size()>0){
            dataList.clear();
            for (City city : cityList) {
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            CurrentLevel=LEVEL_CITY;

        }else {
            String provinceName = selectedProvince.getProvinceName();
            String address="https://api.jisuapi.com/area/city?parentid="+selectedProvince.getProvinceCode()+"&appkey=1fa9f17141372938";
            Log.d(TAG, "queryCities: "+address);
            queryFromServer(address,"city");
        }

    }


    /**
     * 根据传入的地址和类型从服务器上的查询省市县数据
     * @param address
     * @param type
     */
    private void queryFromServer(String address, final String type) {
        HttpUtil.sendOkHttpReques(address, new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull final IOException e) {
                        //通过runOnUiThread()方法回到主线程处理逻辑
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                closeProgressDialog();
                                Toast.makeText(getContext(),"加载失败",Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        });
                    }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String responseText = response.body().string();
                        boolean result=false;
                        if ("province".equals(type)) {
                            result = Utility.handleProvinceResponse(responseText);
                        }else if("city".equals(type)){
                            result = Utility.handleCityResponse(responseText,selectedProvince.getProvinceName());
                        }else if("county".equals(type)){
                              result = Utility.handeCountyResponse(responseText);
                        }
                        if (result){
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    closeProgressDialog();
                                    if ("province".equals(type)) {
                                        queryProvinces();
                                    }else if("city".equals(type)){
                                        queryCities();
                                    }else if("county".equals(type)){
                                        queryCounties();
                                    }
                                }
                            });
                        }
                    }
                    });

                }

    /**
     * 关闭进度对话框
     */
    private void closeProgressDialog() {

    }
}
