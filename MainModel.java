package com.jiama.JinNeng.ui.main.model;

import android.util.Log;

import com.google.gson.Gson;
import com.jiama.JinNeng.bean.Main2Bean;
import com.jiama.JinNeng.bean.PayBean;
import com.tamic.novate.Throwable;
import com.tamic.novate.callback.ResponseCallback;
import com.tamic.novate.callback.RxStringCallback;
import com.tamic.novate.request.NovateRequestBody;
import com.tony.corelibrary.base.BaseActivity;
import com.tony.corelibrary.widget.imgpicker.utils.LogUtils;
import com.jiama.JinNeng.baseapp.Api;
import com.jiama.JinNeng.baseapp.App;
import com.jiama.JinNeng.bean.AppointBean;
import com.jiama.JinNeng.bean.AppointRecordBean;
import com.jiama.JinNeng.bean.CatchBanner;
import com.jiama.JinNeng.bean.ConsigneeBean;
import com.jiama.JinNeng.bean.FlowerBean;
import com.jiama.JinNeng.bean.FlowerCommentBean;
import com.jiama.JinNeng.bean.FlowerFilterBean;
import com.jiama.JinNeng.bean.GreenCoinCatchBean;
import com.jiama.JinNeng.bean.HotTagBean;
import com.jiama.JinNeng.bean.LocationBean;
import com.jiama.JinNeng.bean.MainBean;
import com.jiama.JinNeng.bean.MainBean.GreenCoin_Play;
import com.jiama.JinNeng.bean.MainGovernBannerBean;
import com.jiama.JinNeng.bean.MainGovernBean;
import com.jiama.JinNeng.bean.MainParkBean;
import com.jiama.JinNeng.bean.MainPlayDetailBean;
import com.jiama.JinNeng.bean.MainShowDetailBean;
import com.jiama.JinNeng.bean.MessageBean;
import com.jiama.JinNeng.bean.QuestionBean;
import com.jiama.JinNeng.bean.QuestionNextBean;
import com.jiama.JinNeng.bean.ReceiveRewardBean;
import com.jiama.JinNeng.bean.ShareAppBean;
import com.jiama.JinNeng.bean.SignBean;
import com.jiama.JinNeng.common.DespatchTool;
import com.jiama.JinNeng.common.OnResponCallback;
import com.jiama.JinNeng.common.novate.NovateManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;


public class MainModel {

    BaseActivity context;

    public MainModel(BaseActivity context){
        this.context = context;
    }

    /**
     * //调用地区接口,获取城市列表
     * @param json
     * @param callback
     */
    public void queryCity(String json,final OnResponCallback callback){
        //调用地区接口
        NovateManager.getNovate().rxJson(Api.CityUrl,json,new RxStringCallback(){
            @Override
            public void onError(Object tag, Throwable e) {

            }

            @Override
            public void onCancel(Object tag, Throwable e) {

            }

            @Override
            public void onNext(Object tag, String response) {
                Log.i("MainFragment","response:"+response);
                Gson gson = new Gson();
                LocationBean bean = gson.fromJson(response,LocationBean.class);
                callback.onRes(null,null,bean);
            }
        });
    }

    /**
     * 首页数据
     * @param username
     * @param version
     * @param callback
     */
    public void queryMainPage(String username,String version,final OnResponCallback callback){
        try {
            JSONObject obj = new JSONObject();
            JSONObject data = new JSONObject();
            data.put("id","homepage");
            data.put("username",username);
            data.put("version",version);
            data.put("clientType","android");
            obj.put("data",data);
            Log.i("MainFragment","obj:"+obj.toString());
            NovateManager.getNovate().rxJson(Api.getportal,obj.toString(),new RxStringCallback(){
                @Override
                public void onError(Object tag, Throwable e) {

                }

                @Override
                public void onCancel(Object tag, Throwable e) {

                }

                @Override
                public void onNext(Object tag, String response) {
                    Log.i("MainFragment","response:"+response);
                    Main2Bean main2Bean = new Main2Bean();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray array = jsonObject.getJSONObject("jsonResult").getJSONObject("typography").getJSONArray("list");
                        Gson gson = new Gson();
                        for (int i = 0; i < array.length(); i++) {
                            if (array.getJSONObject(i).getString("code").equals("magic1")) {
                                JSONArray imgs = array.getJSONObject(0).getJSONObject("section").getJSONObject("subSection").getJSONArray("list");
                                String imgtop = imgs.getJSONObject(0).getString("picUrl");
                                main2Bean.setProductTopImg(imgtop);
                            }else if (array.getJSONObject(i).getString("code").equals("hot")) {

                                Main2Bean.ProductBean product = gson.fromJson(array.getJSONObject(1).toString(),Main2Bean.ProductBean.class);
                                main2Bean.setmProductBean(product);
                            }else if (array.getJSONObject(i).getString("code").equals("article")) {
                                String videourl = array.getJSONObject(2).getJSONArray("list").getJSONObject(0).getString("videoUrl");
                                main2Bean.setVideoUrl(videourl);
                            }else if (array.getJSONObject(i).getString("code").equals("magic4")) {
                                JSONArray intreduces = array.getJSONObject(3).getJSONObject("section").getJSONObject("subSection").getJSONArray("list");
                                String pic =  intreduces.getJSONObject(0).getString("picUrl");
                                main2Bean.setImageIntruduce(pic);
                            }
                        }
                        JSONObject bannerobj = jsonObject.getJSONObject("jsonResult").getJSONObject("banner");
                        Main2Bean.BannerMain banner = gson.fromJson(bannerobj.toString(),Main2Bean.BannerMain.class);
                        main2Bean.setBannerMain(banner);

                        callback.onRes(null,null,main2Bean);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取深圳公园数据
     * @param username
     * @param channelId
     * @param pageSize
     * @param cardtype
     * @param page
     */
    public void queryMainParkPage(String username,String channelId,String pageSize,String cardtype,String page,final OnResponCallback callback){
        try {
            JSONObject obj = new JSONObject();
            JSONObject data = new JSONObject();
            data.put("username",username);
            data.put("channelid",channelId);
            data.put("pageSize",pageSize);
            data.put("card_type",cardtype);
            data.put("page",page);
            obj.put("data",data);
            Log.i("MainFragment","obj:"+obj.toString());
            NovateManager.getNovate().rxJson(Api.getcouponlist,obj.toString(),new RxStringCallback(){
                @Override
                public void onError(Object tag, Throwable e) {

                }

                @Override
                public void onCancel(Object tag, Throwable e) {

                }

                @Override
                public void onNext(Object tag, String response) {
                    LogUtils.i("MainFragment","queryMainParkPage_response:"+response);
                    try {
                        Gson gson = new Gson();
                        MainParkBean mainParkBean = gson.fromJson(response, MainParkBean.class);
                        callback.onRes(null,null,mainParkBean);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取公园详情
     * @param id
     * @param callback
     */
    public void queyMainParkDetailPage(String id,final OnResponCallback callback){
        try {
            JSONObject obj = new JSONObject();
            JSONObject data = new JSONObject();
            data.put("id",id);
            obj.put("data",data);
            Log.i("MainFragment","obj:"+obj.toString());
            NovateManager.getNovate().rxJson(Api.getcoupon,obj.toString(),new RxStringCallback(){
                @Override
                public void onError(Object tag, Throwable e) {

                }

                @Override
                public void onCancel(Object tag, Throwable e) {

                }

                @Override
                public void onNext(Object tag, String response) {
                    LogUtils.i("MainFragment","queyMainParkDetailPage_response:"+response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONObject jsonResult = jsonObject.getJSONObject("jsonResult");
                        Gson gson = new Gson();
                        MainPlayDetailBean ParkDetailBean = gson.fromJson(response, MainPlayDetailBean.class);
                        callback.onRes(null,null,ParkDetailBean);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void doPostGoodComment(String id,String isThumbsup,final OnResponCallback callback){
        try {
            JSONObject obj = new JSONObject();
            JSONObject data = new JSONObject();
            data.put("id",id);
            data.put("isThumbsup",isThumbsup);
            if (App.sUser!=null && App.sUser.getJsonResult()!=null){
                data.put("username", App.sUser.getJsonResult().getUsername());
            }
            obj.put("data",data);
            Log.i("MainFragment","obj:"+obj.toString());
            NovateManager.getNovate().rxJson(Api.thumbsupComment,obj.toString(),new RxStringCallback(){
                @Override
                public void onError(Object tag, Throwable e) {

                }

                @Override
                public void onCancel(Object tag, Throwable e) {

                }

                @Override
                public void onNext(Object tag, String response) {
                    Log.i("MainFragment","response:"+response);
                    callback.onRes(response,null,null);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void queryMainShowDetailPage(String id,final OnResponCallback callback){
        try {
            JSONObject obj = new JSONObject();
            JSONObject data = new JSONObject();
            data.put("id",id);
            obj.put("data",data);
            Log.i("MainFragment","obj:"+obj.toString());
            NovateManager.getNovate().rxJson(Api.getcoupon,obj.toString(),new RxStringCallback(){
                @Override
                public void onError(Object tag, Throwable e) {

                }

                @Override
                public void onCancel(Object tag, Throwable e) {

                }

                @Override
                public void onNext(Object tag, String response) {
                    LogUtils.i("MainFragment","queryMainShowDetailPage_response:"+response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONObject jsonResult = jsonObject.getJSONObject("jsonResult");
                        Gson gson = new Gson();
                        MainShowDetailBean ParkDetailBean = gson.fromJson(response, MainShowDetailBean.class);
                        callback.onRes(null,null,ParkDetailBean);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 政务信息详情
     * @param articleId
     * @param callback
     */
    public void queryMainGovernmentDetailInfo(String articleId,final OnResponCallback callback){
        try {
            JSONObject obj = new JSONObject();
            JSONObject data = new JSONObject();
            data.put("articleId",articleId);
            if (App.sUser!=null && App.sUser.getJsonResult()!=null){
                data.put("username", App.sUser.getJsonResult().getUsername());
            }
            obj.put("data",data);
            Log.i("MainFragment","obj:"+obj.toString());
            NovateManager.getNovate().rxJson(Api.getarchiveinfo,obj.toString(),new RxStringCallback(){
                @Override
                public void onError(Object tag, Throwable e) {

                }

                @Override
                public void onCancel(Object tag, Throwable e) {

                }

                @Override
                public void onNext(Object tag, String response) {
                    LogUtils.i("MainFragment","queryMainGovernmentDetailInfo_response:"+response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONObject jsonResult = jsonObject.getJSONObject("jsonResult");
                        Gson gson = new Gson();
                        MainGovernBean.JsonResultBean.ListBean bean = gson.fromJson(jsonResult.toString(),MainGovernBean.JsonResultBean.ListBean.class);
                        if (DespatchTool.toLogin(context,jsonObject.getInt("code"))) {
                            return;
                        }
                        callback.onRes(null,null,bean);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 政务信息列表页banner
     * @param username
     * @param id
     * @param clientType
     * @param version
     * @param callback
     */
    public void queryGovernmentDetailBanner(String username,String id,String clientType,String version,final OnResponCallback callback){
        try {
            JSONObject obj = new JSONObject();
            JSONObject data = new JSONObject();
            data.put("username",username);
            data.put("id",id);
            data.put("clientType",clientType);
            data.put("version",version);
            obj.put("data",data);
            Log.i("MainFragment","obj:"+obj.toString());
            NovateManager.getNovate().rxJson(Api.getportal,obj.toString(),new RxStringCallback(){
                @Override
                public void onError(Object tag, Throwable e) {

                }

                @Override
                public void onCancel(Object tag, Throwable e) {

                }

                @Override
                public void onNext(Object tag, String response) {
                    LogUtils.i("MainFragment","queryGovernmentDetailBanner_response:"+response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONObject jsonResult = jsonObject.getJSONObject("jsonResult");
                        JSONObject bannerobj = jsonResult.getJSONObject("banner");
                        JSONObject typography = jsonResult.getJSONObject("typography");
                        JSONArray listarray = typography.getJSONArray("list");
                        JSONArray scrollinfoarray = listarray.getJSONObject(0).getJSONArray("list");
                        Gson gson = new Gson();
                        List<MainGovernBannerBean.GovernInfoScrollInfoBean.GovernDown> governlist = new ArrayList<>();
                        MainGovernBannerBean.GovernInfoScrollInfoBean scrollInfoBean= new MainGovernBannerBean.GovernInfoScrollInfoBean();
                        for (int i = 0; i < scrollinfoarray.length(); i++) {
                            MainGovernBannerBean.GovernInfoScrollInfoBean.GovernDown governDownBean = gson.fromJson(scrollinfoarray.get(i).toString(), MainGovernBannerBean.GovernInfoScrollInfoBean.GovernDown .class);
                            governlist.add(governDownBean);
                        }
                        scrollInfoBean.setList(governlist);

                        MainGovernBannerBean bean = new MainGovernBannerBean();
                        MainGovernBannerBean.GovernInfoBanner bannerBean = gson.fromJson(bannerobj.toString(), MainGovernBannerBean.GovernInfoBanner.class);
                        bean.governInfoBanner = bannerBean;
                        bean.governInfoScrollInfo = scrollInfoBean;
                        callback.onRes(null,null,bean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 政务信息列表
     * @param username
     * @param channelid
     * @param pageSize
     * @param page
     * @param callback
     */
    public void queryGovernmentInfo(String username,String channelid,String pageSize,String page,final OnResponCallback callback){
        try {
            JSONObject obj = new JSONObject();
            JSONObject data = new JSONObject();
            data.put("username",username);
            data.put("channelid",channelid);
            data.put("pageSize",pageSize);
            data.put("page",page);
            obj.put("data",data);
            Log.i("MainFragment","obj:"+obj.toString());
            NovateManager.getNovate().rxJson(Api.getarchivelist,obj.toString(),new RxStringCallback(){
                @Override
                public void onError(Object tag, Throwable e) {

                }

                @Override
                public void onCancel(Object tag, Throwable e) {

                }

                @Override
                public void onNext(Object tag, String response) {
                    LogUtils.i("MainFragment","queryGovernmentInfo_response:"+response);
                    try {
                        Gson gson = new Gson();
                        MainGovernBean bean = gson.fromJson(response,MainGovernBean.class);
                        callback.onRes(null,null,bean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 我要养花 类型
     * @param callback
     */
    public void queryFlowerCategoryInfo(final OnResponCallback callback){
        try {
            JSONObject obj = new JSONObject();
            JSONObject data = new JSONObject();
            if (App.sUser!=null && App.sUser.getJsonResult()!=null){
                data.put("username", App.sUser.getJsonResult().getUsername());
            }
            obj.put("data",data);
            Log.i("MainFragment","obj:"+obj.toString());
            NovateManager.getNovate().rxJson(Api.getcategorylist,obj.toString(),new RxStringCallback(){
                @Override
                public void onError(Object tag, Throwable e) {

                }

                @Override
                public void onCancel(Object tag, Throwable e) {

                }

                @Override
                public void onNext(Object tag, String response) {
                    LogUtils.i("MainFragment","queryFlowerCategoryInfo_response:"+response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (DespatchTool.toLogin(context,jsonObject.getInt("code"))) {
                            return;
                        }
                        JSONObject jsonResult = jsonObject.getJSONObject("jsonResult");
                        JSONArray typearray = jsonResult.getJSONArray("list");
                        Gson gson = new Gson();
                        List<FlowerBean.FlowerTypeBean> typelist = new ArrayList<>();
                        for (int i = 0; i < typearray.length(); i++) {
                            JSONObject typeobj = typearray.getJSONObject(i);
                            FlowerBean.FlowerTypeBean bean = gson.fromJson(typeobj.toString(), FlowerBean.FlowerTypeBean.class);
                            typelist.add(bean);
                        }
                        callback.onRes(null,typelist,null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 我要养花 列表
     * @param isAscending
     * @param classifyId
     * @param pageSize
     * @param page
     * @param callback
     */
    public void queryFlowerList(String attrIds,String attrValues,String sort,String isAscending,String classifyId,String pageSize,String page,final OnResponCallback callback){

        try {
            JSONObject obj = new JSONObject();
            JSONObject data = new JSONObject();
            data.put("sort", sort);
            data.put("isAscending", isAscending);
            data.put("classifyId", classifyId);
            data.put("attrIds", attrIds);
            data.put("attrValues", attrValues);
            data.put("pageSize", pageSize);
            data.put("page", page);
            if (App.sUser!=null && App.sUser.getJsonResult()!=null){
                data.put("username", App.sUser.getJsonResult().getUsername());
            }
            obj.put("data",data);
            Log.i("MainFragment","obj:"+obj.toString());
            NovateManager.getNovate().rxJson(Api.getlist,obj.toString(),new RxStringCallback(){
                @Override
                public void onError(Object tag, Throwable e) {

                }

                @Override
                public void onCancel(Object tag, Throwable e) {

                }

                @Override
                public void onNext(Object tag, String response) {
                    LogUtils.i("MainFragment","queryFlowerList_response:"+response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (DespatchTool.toLogin(context,jsonObject.getInt("code"))) {
                            return;
                        }
                        JSONObject jsonResult = jsonObject.getJSONObject("jsonResult");
                        JSONArray flowerarray = jsonResult.getJSONArray("list");
                        Gson gson = new Gson();
                        List<FlowerBean.FlowListBean> flowerlist = new ArrayList<>();
                        for (int i = 0; i < flowerarray.length(); i++) {
                            JSONObject flowerobj = flowerarray.getJSONObject(i);
                            FlowerBean.FlowListBean  bean = gson.fromJson(flowerobj.toString(), FlowerBean.FlowListBean.class);
                            flowerlist.add(bean);
                        }
                        callback.onRes(null,flowerlist,null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * 我要养花 banner
     * @param types
     * @param channelid
     * @param callback
     */
    public void queryFlowerBanner(String types,String channelid,final OnResponCallback callback){

        try {
            JSONObject obj = new JSONObject();
            JSONObject data = new JSONObject();
            data.put("types", types);
            data.put("channelid", channelid);
            if (App.sUser!=null && App.sUser.getJsonResult()!=null){
                data.put("username", App.sUser.getJsonResult().getUsername());
            }
            obj.put("data",data);
            Log.i("MainFragment","obj:"+obj.toString());
            NovateManager.getNovate().rxJson(Api.getbannerlist,obj.toString(),new RxStringCallback(){
                @Override
                public void onError(Object tag, Throwable e) {

                }

                @Override
                public void onCancel(Object tag, Throwable e) {

                }

                @Override
                public void onNext(Object tag, String response) {
                    LogUtils.i("MainFragment","queryFlowerBanner_response:"+response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (DespatchTool.toLogin(context,jsonObject.getInt("code"))) {
                            return;
                        }
                        JSONObject jsonResult = jsonObject.getJSONObject("jsonResult");
                        JSONArray flowerarray = jsonResult.getJSONArray("list");
                        Gson gson = new Gson();
                        List<FlowerBean.FlowBannerBean> flowerBanner = new ArrayList<>();
                        for (int i = 0; i < flowerarray.length(); i++) {
                            JSONObject bannerobj = flowerarray.getJSONObject(i);
                            FlowerBean.FlowBannerBean  bean = gson.fromJson(bannerobj.toString(), FlowerBean.FlowBannerBean.class);
                            flowerBanner.add(bean);
                        }
                        callback.onRes(null,flowerBanner,null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * 我要养花 搜素
     * @param keyword
     * @param pageSize
     * @param page
     * @param callback
     */
    public void queryFlowerList(String keyword,String pageSize,String page,final OnResponCallback callback){
        try {
            JSONObject obj = new JSONObject();
            JSONObject data = new JSONObject();
            data.put("keyword", keyword);
            data.put("pageSize", pageSize);
            data.put("page", page);
            if (App.sUser!=null && App.sUser.getJsonResult()!=null){
                data.put("username", App.sUser.getJsonResult().getUsername());
            }
            obj.put("data",data);
            Log.i("MainFragment","obj:"+obj.toString());
            NovateManager.getNovate().rxJson(Api.getlist,obj.toString(),new RxStringCallback(){
                @Override
                public void onError(Object tag, Throwable e) {

                }

                @Override
                public void onCancel(Object tag, Throwable e) {

                }

                @Override
                public void onNext(Object tag, String response) {
                    LogUtils.i("MainFragment","queryFlowerList_response:"+response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (DespatchTool.toLogin(context,jsonObject.getInt("code"))) {
                            return;
                        }
                        JSONObject jsonResult = jsonObject.getJSONObject("jsonResult");
                        JSONArray flowerarray = jsonResult.getJSONArray("list");
                        Gson gson = new Gson();
                        List<FlowerBean.FlowListBean> flowerlist = new ArrayList<>();
                        for (int i = 0; i < flowerarray.length(); i++) {
                            JSONObject flowerobj = flowerarray.getJSONObject(i);
                            FlowerBean.FlowListBean  bean = gson.fromJson(flowerobj.toString(), FlowerBean.FlowListBean.class);
                            flowerlist.add(bean);
                        }
                        callback.onRes(null,flowerlist,null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * 查询商品详情
     * @param goodsId
     * @param callback
     */
    public void queryFlowerDetail(String goodsId,final OnResponCallback callback){
        try {
            JSONObject obj = new JSONObject();
            JSONObject data = new JSONObject();
            data.put("goodsId", goodsId);
            if (App.sUser!=null && App.sUser.getJsonResult()!=null){
                data.put("username", App.sUser.getJsonResult().getUsername());
            }
            obj.put("data",data);
            Log.i("MainFragment","obj:"+obj.toString());

            NovateManager.getNovate().rxJson(Api.getgoodsinfo,obj.toString(),new RxStringCallback(){
                @Override
                public void onError(Object tag, Throwable e) {

                }

                @Override
                public void onCancel(Object tag, Throwable e) {

                }

                @Override
                public void onNext(Object tag, String response) {
                    LogUtils.i("MainFragment","queryFlowerDetail_response:"+response);
                    try {
                        Gson gson = new Gson();
                        FlowerBean.FlowerDetailBean bean = gson.fromJson(response,FlowerBean.FlowerDetailBean.class);
                        if (DespatchTool.toLogin(context,bean.getCode())) {
                            return;
                        }
                        callback.onRes(null,null,bean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取商品评论列表
     * @param types
     * @param goodsId
     * @param pageSize
     * @param page
     * @param callback
     */
    public void queryFlowerComments(String types,String goodsId,String pageSize,String page,final OnResponCallback callback){
        try {
            JSONObject obj = new JSONObject();
            JSONObject data = new JSONObject();
            data.put("goodsId", goodsId);
            data.put("types", types);
            data.put("pageSize", pageSize);
            data.put("page", page);
            if (App.sUser!=null && App.sUser.getJsonResult()!=null){
                data.put("username", App.sUser.getJsonResult().getUsername());
            }
            obj.put("data",data);
            Log.i("MainFragment","obj:"+obj.toString());

            NovateManager.getNovate().rxJson(Api.getcommentlist,obj.toString(),new RxStringCallback(){
                @Override
                public void onError(Object tag, Throwable e) {

                }

                @Override
                public void onCancel(Object tag, Throwable e) {

                }

                @Override
                public void onNext(Object tag, String response) {
                    LogUtils.i("MainFragment","queryFlowerComments_response:"+response);
                    try {
                        Gson gson = new Gson();
                        FlowerCommentBean bean = gson.fromJson(response,FlowerCommentBean.class);
                        if (DespatchTool.toLogin(context,bean.getCode())) {
                            return;
                        }
                        callback.onRes(null,null,bean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 商品详情 收藏
     * @param goodsId
     * @param isFavorite
     * @param callback
     */
    public void doFlowerCollection(String goodsId,String isFavorite,final OnResponCallback callback){
        try {
            JSONObject obj = new JSONObject();
            JSONObject data = new JSONObject();
            data.put("goodsId", goodsId);
            data.put("isFavorite", isFavorite);
            if (App.sUser!=null && App.sUser.getJsonResult()!=null){
                data.put("username", App.sUser.getJsonResult().getUsername());
            }
            obj.put("data",data);
            Log.i("MainFragment","obj:"+obj.toString());
            NovateManager.getNovate().rxJson(Api.addfavorite,obj.toString(),new RxStringCallback(){
                @Override
                public void onError(Object tag, Throwable e) {

                }

                @Override
                public void onCancel(Object tag, Throwable e) {

                }

                @Override
                public void onNext(Object tag, String response) {
                    LogUtils.i("MainFragment","doFlowerCollection_response:"+response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String code = jsonObject.getString("code");
                        if (DespatchTool.toLogin(context,jsonObject.getInt("code"))) {
                            return;
                        }
                        callback.onRes(code,null,null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 商品详情 加入购物车
     * @param number
     * @param gspecIds
     * @param statex
     * @param goodsId
     * @param pid
     * @param isSelected
     * @param names
     * @param callback
     */
    public void doAddShoppingCart(String number,String gspecIds,String statex,String ids,String goodsId,String pid,
                                  String isSelected,String names,final OnResponCallback callback ){
        try {
            JSONObject obj = new JSONObject();
            JSONObject data = new JSONObject();
            data.put("number", number);
            data.put("gspecIds", gspecIds);
            data.put("statex", statex);
            data.put("ids", ids);
            data.put("goodsId", goodsId);
            data.put("pid", pid);
            data.put("isSelected", isSelected);
            data.put("names", names);
            if (App.sUser!=null && App.sUser.getJsonResult()!=null){
                data.put("username", App.sUser.getJsonResult().getUsername());
            }
            obj.put("data",data);
            Log.i("MainFragment","obj:"+obj.toString());
            NovateManager.getNovate().rxJson(Api.addcart,obj.toString(),new RxStringCallback(){
                @Override
                public void onError(Object tag, Throwable e) {

                }

                @Override
                public void onCancel(Object tag, Throwable e) {

                }

                @Override
                public void onNext(Object tag, String response) {
                    LogUtils.i("MainFragment","doFlowerCollection_response:"+response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String code = jsonObject.getString("code");
                        if (DespatchTool.toLogin(context,jsonObject.getInt("code"))) {
                            return;
                        }
                        callback.onRes(code,null,null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 商品详情 立即兑换
     * @param id
     * @param ids
     * @param subject
     * @param numberMode
     * @param statex
     * @param payment_type
     * @param names
     * @param total_fee
     * @param type
     * @param gspecIds
     * @param number
     * @param callback
     */
    public void doFlowerBuy(String id,String ids,String subject,String numberMode,String statex,String payment_type,String names,String total_fee,
                            String type,String gspecIds,String number,final OnResponCallback callback){
        try {
            JSONObject obj = new JSONObject();
            JSONObject data = new JSONObject();
            data.put("id", id);
            data.put("ids", ids);
            data.put("subject", subject);
            data.put("numberMode", numberMode);
            data.put("statex", statex);
            data.put("payment_type", payment_type);
            data.put("names", names);
            data.put("total_fee", total_fee);
            data.put("type", type);
            data.put("gspecIds", gspecIds);
            data.put("number", number);
            if (App.sUser!=null && App.sUser.getJsonResult()!=null){
                data.put("username", App.sUser.getJsonResult().getUsername());
            }
            obj.put("data",data);
            Log.i("MainFragment","obj:"+obj.toString());
            NovateManager.getNovate().rxJson(Api.postpay,obj.toString(),new RxStringCallback(){
                @Override
                public void onError(Object tag, Throwable e) {

                }

                @Override
                public void onCancel(Object tag, Throwable e) {

                }

                @Override
                public void onNext(Object tag, String response) {
                    LogUtils.i("MainFragment","doFlowerBuy_response:"+response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (DespatchTool.toLogin(context,jsonObject.getInt("code"))) {
                            return;
                        }
                        callback.onRes(response,null,null);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询收货人地址
     * @param callback
     */
    public void queryConsigneeAddress(final OnResponCallback callback){
        try {
            JSONObject obj = new JSONObject();
            JSONObject data = new JSONObject();
            if (App.sUser!=null && App.sUser.getJsonResult()!=null){
                data.put("username", App.sUser.getJsonResult().getUsername());
            }
            obj.put("data",data);
            Log.i("MainFragment","obj:"+obj.toString());

            NovateManager.getNovate().rxJson(Api.getaddresslist,obj.toString(),new RxStringCallback(){
                @Override
                public void onError(Object tag, Throwable e) {

                }

                @Override
                public void onCancel(Object tag, Throwable e) {

                }

                @Override
                public void onNext(Object tag, String response) {
                    LogUtils.i("MainFragment","queryConsigneeAddress_response:"+response);
                    try {
                        Gson gson = new Gson();
                        ConsigneeBean bean = gson.fromJson(response,ConsigneeBean.class);
                        if (DespatchTool.toLogin(context,bean.getCode())) {
                            return;
                        }
                        callback.onRes(null,null,bean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     *  订单交易 获取提交的订单详情
     * @param cartId
     * @param callback
     */
    public void doConfirmOrderCheckout(String cartId,final OnResponCallback callback){
        try {
            JSONObject obj = new JSONObject();
            JSONObject data = new JSONObject();
            data.put("cartId", cartId);
            if (App.sUser!=null && App.sUser.getJsonResult()!=null){
                data.put("username", App.sUser.getJsonResult().getUsername());
            }
            obj.put("data",data);
            Log.i("MainFragment","obj:"+obj.toString());
            NovateManager.getNovate().rxJson(Api.checkout,obj.toString(),new RxStringCallback(){
                @Override
                public void onError(Object tag, Throwable e) {

                }

                @Override
                public void onCancel(Object tag, Throwable e) {

                }

                @Override
                public void onNext(Object tag, String response) {
                    LogUtils.i("MainFragment","doConfirmOrderCheckout_response:"+response);
                    try {
                        Gson gson = new Gson();
                        FlowerBean.FlowerOrderBean bean = gson.fromJson(response,FlowerBean.FlowerOrderBean.class);
                        if (DespatchTool.toLogin(context,bean.getCode())) {
                            return;
                        }
                        callback.onRes(null,null,bean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 订单交易 最后的支付
     * @param addressId
     * @param payment
     * @param payPassword
     * @param array
     * @param orderNumber
     * @param callback
     */
    public void doConfirmOrderCartPatchPay(String addressId,String payment,String payPassword,Object array,String orderNumber,final OnResponCallback callback){
        try {
            JSONObject obj = new JSONObject();
            JSONObject data = new JSONObject();
            data.put("addressId", addressId);
            data.put("payment", payment);
            data.put("payPassword", payPassword);
            data.put("orderNumber",orderNumber);
            data.put("list",array);
            if (App.sUser!=null && App.sUser.getJsonResult()!=null){
                data.put("username", App.sUser.getJsonResult().getUsername());
            }
            obj.put("data",data);
            Log.i("MainFragment","obj:"+obj.toString());
            NovateManager.getNovate().rxJson(Api.cartpatchpay,obj.toString(),new RxStringCallback(){
                @Override
                public void onError(Object tag, Throwable e) {

                }

                @Override
                public void onCancel(Object tag, Throwable e) {

                }

                @Override
                public void onNext(Object tag, String response) {
                    LogUtils.i("MainFragment","doConfirmOrderCartPatchPay_response:"+response);
                    Gson gson = new Gson();
                    PayBean payBean =  gson.fromJson(response, PayBean.class);
                    callback.onRes(null,null,payBean);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 玩赚￥ 玩
     * @param channelid
     * @param pageSize
     * @param sectionid
     * @param page
     * @param callback
     */
    public void queryGreenCoinPlay(String channelid,String pageSize,String sectionid,String page ,final OnResponCallback callback){
        try {
            JSONObject obj = new JSONObject();
            JSONObject data = new JSONObject();
            data.put("channelid", channelid);
            data.put("pageSize", pageSize);
            data.put("sectionid", sectionid);
            data.put("cardType", "TICKET");
            data.put("page",page);
            if (App.sUser!=null && App.sUser.getJsonResult()!=null){
                data.put("username", App.sUser.getJsonResult().getUsername());
            }
            obj.put("data",data);
            Log.i("MainFragment","obj:"+obj.toString());
            NovateManager.getNovate().rxJson(Api.getcouponlist,obj.toString(),new RxStringCallback(){
                @Override
                public void onError(Object tag, Throwable e) {

                }

                @Override
                public void onCancel(Object tag, Throwable e) {

                }

                @Override
                public void onNext(Object tag, String response) {
                    LogUtils.i("MainFragment","queryGreenCoinPlay_response:"+response);
                    try {
                        Gson gson = new Gson();
                        MainParkBean bean = gson.fromJson(response,MainParkBean.class);
                        if (DespatchTool.toLogin(context,bean.getCode())) {
                            return;
                        }
                        callback.onRes(null,null,bean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 玩赚￥ 晒
     * @param channelid
     * @param pageSize
     * @param sectionid
     * @param page
     * @param callback
     */
    public void queryGreenCoinShow( String channelid,String pageSize,String sectionid,String page ,final OnResponCallback callback){
        try {
            JSONObject obj = new JSONObject();
            JSONObject data = new JSONObject();
            data.put("channelid", channelid);
            data.put("pageSize", pageSize);
            data.put("sectionid", sectionid);
            data.put("cardType", "TICKET");
            data.put("page",page);
            if (App.sUser!=null && App.sUser.getJsonResult()!=null){
                data.put("username", App.sUser.getJsonResult().getUsername());
            }
            obj.put("data",data);
            Log.i("MainFragment","obj:"+obj.toString());
            NovateManager.getNovate().rxJson(Api.getcouponlist,obj.toString(),new RxStringCallback(){
                @Override
                public void onError(Object tag, Throwable e) {

                }

                @Override
                public void onCancel(Object tag, Throwable e) {

                }

                @Override
                public void onNext(Object tag, String response) {
                    LogUtils.i("MainFragment","queryGreenCoinShow_response:"+response);
                    try {
                        Gson gson = new Gson();
                        MainParkBean bean = gson.fromJson(response,MainParkBean.class);
                        if (DespatchTool.toLogin(context,bean.getCode())) {
                            return;
                        }
                        callback.onRes(null,null,bean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 玩赚￥ 赚
     * @param callback
     */
    public void queryGreenCoinCatch(final OnResponCallback callback){
        try {
            JSONObject obj = new JSONObject();
            JSONObject data = new JSONObject();
            if (App.sUser!=null && App.sUser.getJsonResult()!=null){
                data.put("username", App.sUser.getJsonResult().getUsername());
            }
            obj.put("data",data);
            Log.i("MainFragment","obj:"+obj.toString());
            NovateManager.getNovate().rxJson(Api.gettasklist,obj.toString(),new RxStringCallback(){
                @Override
                public void onError(Object tag, Throwable e) {

                }

                @Override
                public void onCancel(Object tag, Throwable e) {

                }

                @Override
                public void onNext(Object tag, String response) {
                    LogUtils.i("MainFragment","queryGreenCoinCatch_response:"+response);
                    try {
                        Gson gson = new Gson();
                        GreenCoinCatchBean bean = gson.fromJson(response, GreenCoinCatchBean.class);
                        if (DespatchTool.toLogin(context,bean.getCode())) {
                            return;
                        }
                        callback.onRes(null,null,bean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void queryGreenCoinCathBanner(final OnResponCallback callback){
        try {
            JSONObject obj = new JSONObject();
            JSONObject data = new JSONObject();
            data.put("id","show");
            data.put("clientType","android");
            data.put("sub","1");
            data.put("version","1.0.0");
            if (App.sUser!=null && App.sUser.getJsonResult()!=null){
                data.put("username", App.sUser.getJsonResult().getUsername());
            }
            obj.put("data",data);
            Log.i("MainFragment","obj:"+obj.toString());
            NovateManager.getNovate().rxJson(Api.getportal,obj.toString(),new RxStringCallback(){
                @Override
                public void onError(Object tag, Throwable e) {

                }

                @Override
                public void onCancel(Object tag, Throwable e) {

                }

                @Override
                public void onNext(Object tag, String response) {
                    LogUtils.i("MainFragment","queryGreenCoinCathBanner_response:"+response);
                    try {
                        Gson gson = new Gson();
                        CatchBanner bean = gson.fromJson(response, CatchBanner.class);
                        if (DespatchTool.toLogin(context,bean.getCode())) {
                            return;
                        }
                        callback.onRes(null,null,bean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取一周签到情况
     * @param type
     * @param timestamp
     * @param callback
     */
    public void greenCoinCatchCheckSign(String type,String timestamp,final OnResponCallback callback){
        try {
            JSONObject obj = new JSONObject();
            JSONObject data = new JSONObject();
            data.put("type", type);
            data.put("timestamp", timestamp);
            if (App.sUser!=null && App.sUser.getJsonResult()!=null){
                data.put("username", App.sUser.getJsonResult().getUsername());
            }
            obj.put("data",data);
            Log.i("MainFragment","obj:"+obj.toString());
            NovateManager.getNovate().rxJson(Api.checksign,obj.toString(),new RxStringCallback(){
                @Override
                public void onError(Object tag, Throwable e) {

                }

                @Override
                public void onCancel(Object tag, Throwable e) {

                }

                @Override
                public void onNext(Object tag, String response) {
                    LogUtils.i("MainFragment","greenCoinCatchCheckSign_response:"+response);
                    try {
                        Gson gson = new Gson();
                        SignBean bean = gson.fromJson(response, SignBean.class);
                        if (DespatchTool.toLogin(context,bean.getCode())) {
                            return;
                        }
                        callback.onRes(null,null,bean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 签到
     * @param timestamp
     * @param callback
     */
    public void doGreenCoinCatchSign(String timestamp,final OnResponCallback callback){
        try {
            JSONObject obj = new JSONObject();
            JSONObject data = new JSONObject();
            data.put("timestamp", timestamp);
            if (App.sUser!=null && App.sUser.getJsonResult()!=null){
                data.put("username", App.sUser.getJsonResult().getUsername());
            }
            obj.put("data",data);
            Log.i("MainFragment","obj:"+obj.toString());
            NovateManager.getNovate().rxJson(Api.signday,obj.toString(),new RxStringCallback(){
                @Override
                public void onError(Object tag, Throwable e) {

                }

                @Override
                public void onCancel(Object tag, Throwable e) {

                }

                @Override
                public void onNext(Object tag, String response) {
                    LogUtils.i("MainFragment","doGreenCoinCatchSign_response:"+response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (DespatchTool.toLogin(context,jsonObject.getInt("code"))) {
                            return;
                        }
                        callback.onRes(response,null,null);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void queryMessages(String pageSize,String page,final OnResponCallback callback){

        try {
            JSONObject obj = new JSONObject();
            JSONObject data = new JSONObject();
            data.put("pageSize", pageSize);
            data.put("page", page);
            if (App.sUser!=null && App.sUser.getJsonResult()!=null){
                data.put("username", App.sUser.getJsonResult().getUsername());
            }
            obj.put("data",data);
            Log.i("MainFragment","obj:"+obj.toString());
            NovateManager.getNovate().rxJson(Api.getclassifymsg,obj.toString(),new RxStringCallback(){
                @Override
                public void onError(Object tag, Throwable e) {

                }

                @Override
                public void onCancel(Object tag, Throwable e) {

                }

                @Override
                public void onNext(Object tag, String response) {
                    LogUtils.i("MainFragment","queryMessages_response:"+response);
                    try {
                        Gson gson = new Gson();
                        MessageBean bean = gson.fromJson(response,MessageBean.class);
                       /* if (DespatchTool.toLogin(context,bean.getCode())) {
                            return;
                        }*/
                        callback.onRes(null,null,bean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void queryGreenCoinQuestion(final OnResponCallback callback){
        try {
            JSONObject obj = new JSONObject();
            JSONObject data = new JSONObject();
            if (App.sUser!=null && App.sUser.getJsonResult()!=null){
                data.put("username", App.sUser.getJsonResult().getUsername());
            }
            obj.put("data",data);
            Log.i("MainFragment","obj:"+obj.toString());
            NovateManager.getNovate().rxJson(Api.getquestion,obj.toString(),new RxStringCallback(){
                @Override
                public void onError(Object tag, Throwable e) {

                }

                @Override
                public void onCancel(Object tag, Throwable e) {

                }

                @Override
                public void onNext(Object tag, String response) {
                    LogUtils.i("MainFragment","queryGreenCoinQuestion_response:"+response);
                    try {
                        Gson gson = new Gson();
                        QuestionBean bean = gson.fromJson(response,QuestionBean.class);
                        if (DespatchTool.toLogin(context,bean.getCode())) {
                            return;
                        }
                        callback.onRes(null,null,bean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void queryGreenCoinNextQuestion(String questionId,String answerPos,String answer,String taskId,final OnResponCallback callback){
        try {
            JSONObject obj = new JSONObject();
            JSONObject data = new JSONObject();
            data.put("questionId", questionId);
            data.put("answerPos", answerPos);
            data.put("answer", answer);
            data.put("taskId",taskId);
            if (App.sUser!=null && App.sUser.getJsonResult()!=null){
                data.put("username", App.sUser.getJsonResult().getUsername());
            }
            obj.put("data",data);
            LogUtils.i("MainFragment","obj:"+obj.toString());
            NovateManager.getNovate().rxJson(Api.postquestion,obj.toString(),new RxStringCallback(){
                @Override
                public void onError(Object tag, Throwable e) {

                }

                @Override
                public void onCancel(Object tag, Throwable e) {

                }

                @Override
                public void onNext(Object tag, String response) {
                    LogUtils.i("MainFragment","queryGreenCoinNextQuestion_response:"+response);
                    try {
                        Gson gson = new Gson();
                        QuestionNextBean bean = gson.fromJson(response,QuestionNextBean.class);
                        if (DespatchTool.toLogin(context,bean.getCode())) {
                            return;
                        }
                        callback.onRes(null,null,bean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void queryHotTag(final OnResponCallback callback){
        NovateManager.getNovate().rxJson(Api.gethottag,"",new RxStringCallback(){
            @Override
            public void onError(Object tag, Throwable e) {

            }

            @Override
            public void onCancel(Object tag, Throwable e) {

            }

            @Override
            public void onNext(Object tag, String response) {
                LogUtils.i("MainFragment","queryHotTag_response:"+response);
                try {
                    Gson gson = new Gson();
                    HotTagBean bean = gson.fromJson(response,HotTagBean.class);
                    callback.onRes(null,null,bean);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void doShowCoinPlayPic(boolean isVideo,String id,String title,String content,List<String> files,final OnResponCallback callback){
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("title", title)
                .addFormDataPart("content", content)
                .addFormDataPart("id",id)
                .addFormDataPart("cardStar","5")
                .addFormDataPart("username", App.sUser.getJsonResult().getUsername());
        for (int i = 0; i < files.size(); i++) {
            if(isVideo){
                File file = new File(files.get(i));
                LogUtils.i("MainFragment","附件："+files.get(i));
                builder.addFormDataPart("file",file.getName(), RequestBody.create(MediaType.parse("video/mpeg4"), file));
            }else {
                File file = new File(files.get(i));
                LogUtils.i("MainFragment","附件："+files.get(i));
                builder.addFormDataPart("file",file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
            }
        }
        if (App.aMapLocation != null) {
            builder.addFormDataPart("lat", String.valueOf(App.aMapLocation.getLatitude()));
            builder.addFormDataPart("lng", String.valueOf(App.aMapLocation.getLongitude()));
            LogUtils.i("MainFragment", "id:"+id+"，content:" + content +",username:" + App.sUser.getJsonResult().getUsername()
                    + ",lat:" + String.valueOf(App.aMapLocation.getLatitude()) + ",lng:" + String.valueOf(App.aMapLocation.getLongitude()));
        } else {
            LogUtils.i("MainFragment", "id:"+id+"，content:" + content+ ",username:" + App.sUser.getJsonResult().getUsername());
        }
        RequestBody requestBody = builder.build();
        NovateRequestBody novateRequestBody = new NovateRequestBody(requestBody, new ResponseCallback() {
            @Override
            public Object onHandleResponse(ResponseBody response) throws Exception {

                return null;
            }

            @Override
            public void onError(Object tag, Throwable e) {

            }

            @Override
            public void onCancel(Object tag, Throwable e) {

            }

            @Override
            public void onNext(Object tag, Call call, Object response) {

            }
        });
        NovateManager.getNovate().rxUploadWithBody(Api.cardPostcommentUploadFiles, novateRequestBody, new ResponseCallback<String, ResponseBody>() {
            @Override
            public String onHandleResponse(ResponseBody response) throws Exception {
                //Log.i("MainFragment", "response:" + response.string());
                callback.onRes(response.string(), null, null);

                return null;
            }

            @Override
            public void onError(Object tag, Throwable e) {

            }

            @Override
            public void onCancel(Object tag, Throwable e) {

            }

            @Override
            public void onNext(Object tag, Call call, String response) {
            }
        });
    }

    public void doPushlishShow(boolean isVideo,String id,String title,String content,String thumb,List<String> files,final OnResponCallback callback){
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("title", title)
                .addFormDataPart("content", content)
                .addFormDataPart("id",id)
                .addFormDataPart("cardStar","5")
                .addFormDataPart("username", App.sUser.getJsonResult().getUsername());
        for (int i = 0; i < files.size(); i++) {
            if(isVideo){
                File file2 = new File(thumb);
                LogUtils.i("MainFragment","视频缩略图："+thumb);
                builder.addFormDataPart("thumb",file2.getName(), RequestBody.create(MediaType.parse("image/*"), file2));
                File file = new File(files.get(i));
                LogUtils.i("MainFragment","附件："+files.get(i));
                builder.addFormDataPart("file",file.getName(), RequestBody.create(MediaType.parse("video/*"), file));
            }else {
                File file = new File(files.get(i));
                LogUtils.i("MainFragment","附件："+files.get(i));
                builder.addFormDataPart("file",file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
            }
        }
        if (App.aMapLocation != null) {
            builder.addFormDataPart("lat", String.valueOf(App.aMapLocation.getLatitude()));
            builder.addFormDataPart("lng", String.valueOf(App.aMapLocation.getLongitude()));
            LogUtils.i("MainFragment", "id:"+id+"，content:" + content +",username:" + App.sUser.getJsonResult().getUsername()
                    + ",lat:" + String.valueOf(App.aMapLocation.getLatitude()) + ",lng:" + String.valueOf(App.aMapLocation.getLongitude()));
        } else {
            LogUtils.i("MainFragment", "id:"+id+"，content:" + content+ ",username:" + App.sUser.getJsonResult().getUsername());
        }
        RequestBody requestBody = builder.build();
        NovateRequestBody novateRequestBody = new NovateRequestBody(requestBody, new ResponseCallback() {
            @Override
            public Object onHandleResponse(ResponseBody response) throws Exception {

                return null;
            }

            @Override
            public void onError(Object tag, Throwable e) {

            }

            @Override
            public void onCancel(Object tag, Throwable e) {

            }

            @Override
            public void onNext(Object tag, Call call, Object response) {

            }
        });
        NovateManager.getNovate().rxUploadWithBody(Api.uploadcomment, novateRequestBody, new ResponseCallback<String, ResponseBody>() {
            @Override
            public String onHandleResponse(ResponseBody response) throws Exception {
                //Log.i("MainFragment", "response:" + response.string());
                callback.onRes(response.string(), null, null);

                return null;
            }

            @Override
            public void onError(Object tag, Throwable e) {

            }

            @Override
            public void onCancel(Object tag, Throwable e) {

            }

            @Override
            public void onNext(Object tag, Call call, String response) {
            }
        });


    }


    public void doPostAppoint(String eventId,String name,String mobile,String idcard,String areaId,final OnResponCallback callback){

        try {
            JSONObject obj = new JSONObject();
            JSONObject data = new JSONObject();
            data.put("eventId", eventId);
            data.put("name", name);
            data.put("mobile", mobile);
            data.put("idcard", idcard);
            data.put("areaId", areaId);
            if (App.sUser!=null && App.sUser.getJsonResult()!=null){
                data.put("username", App.sUser.getJsonResult().getUsername());
            }
            obj.put("data",data);
            LogUtils.i("MainFragment","obj:"+obj.toString());
            NovateManager.getNovate().rxJson(Api.appointment,obj.toString(),new RxStringCallback(){
                @Override
                public void onError(Object tag, Throwable e) {

                }

                @Override
                public void onCancel(Object tag, Throwable e) {

                }

                @Override
                public void onNext(Object tag, String response) {
                    LogUtils.i("MainFragment","doPostAppoint_response:"+response);
                    try {
                        Gson gson = new Gson();
                        AppointBean bean = gson.fromJson(response,AppointBean.class);
                        if (DespatchTool.toLogin(context,bean.getCode())) {
                            return;
                        }
                        callback.onRes(null,null,bean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void queryAppointRecord(String eventId,final OnResponCallback callback){
        try {
            JSONObject obj = new JSONObject();
            JSONObject data = new JSONObject();
            data.put("eventId", eventId);
            if (App.sUser!=null && App.sUser.getJsonResult()!=null){
                data.put("username", App.sUser.getJsonResult().getUsername());
            }
            obj.put("data",data);
            LogUtils.i("MainFragment","obj:"+obj.toString());
            NovateManager.getNovate().rxJson(Api.myappointmentlist,obj.toString(),new RxStringCallback(){
                @Override
                public void onError(Object tag, Throwable e) {

                }

                @Override
                public void onCancel(Object tag, Throwable e) {

                }

                @Override
                public void onNext(Object tag, String response) {
                    LogUtils.i("MainFragment","queryAppointRecord_response:"+response);
                    try {
                        Gson gson = new Gson();
                        AppointRecordBean bean = gson.fromJson(response,AppointRecordBean.class);
                        if (DespatchTool.toLogin(context,bean.getCode())) {
                            return;
                        }
                        callback.onRes(null,null,bean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void queryAppointDetail(String appointmentCode,final OnResponCallback callback){
        try {
            JSONObject obj = new JSONObject();
            JSONObject data = new JSONObject();
            data.put("appointmentCode", appointmentCode);
          if (App.sUser!=null && App.sUser.getJsonResult()!=null){
                data.put("username", App.sUser.getJsonResult().getUsername());
            }
            obj.put("data",data);
            LogUtils.i("MainFragment","obj:"+obj.toString());
            NovateManager.getNovate().rxJson(Api.getappointmentinfo,obj.toString(),new RxStringCallback(){
                @Override
                public void onError(Object tag, Throwable e) {

                }

                @Override
                public void onCancel(Object tag, Throwable e) {

                }

                @Override
                public void onNext(Object tag, String response) {
                    LogUtils.i("MainFragment","queryAppointDetail_response:"+response);
                    try {
                        Gson gson = new Gson();
                        JSONObject jsonObject = new JSONObject(response);
                        int code = jsonObject.getInt("code");
                        AppointRecordBean.JsonResultBean.ListBean bean = gson.fromJson(jsonObject.getJSONObject("jsonResult").toString(),AppointRecordBean.JsonResultBean.ListBean.class);
                        if (DespatchTool.toLogin(context,code)) {
                            return;
                        }
                        callback.onRes(null,null,bean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void doReceiveQuestionReward(String taskId,final OnResponCallback callback ){
        try {
            JSONObject obj = new JSONObject();
            JSONObject data = new JSONObject();
            data.put("taskId", taskId);
            if (App.sUser!=null && App.sUser.getJsonResult()!=null){
                data.put("username", App.sUser.getJsonResult().getUsername());
            }
            obj.put("data",data);
            LogUtils.i("MainFragment","obj:"+obj.toString());
            NovateManager.getNovate().rxJson(Api.receivereward,obj.toString(),new RxStringCallback(){
                @Override
                public void onError(Object tag, Throwable e) {

                }

                @Override
                public void onCancel(Object tag, Throwable e) {

                }

                @Override
                public void onNext(Object tag, String response) {
                    LogUtils.i("MainFragment","doReceiveQuestionReward_response:"+response);
                    try {
                        Gson gson = new Gson();
                        ReceiveRewardBean bean = gson.fromJson(response,ReceiveRewardBean.class);
                        if (DespatchTool.toLogin(context,bean.getCode())) {
                            return;
                        }
                        callback.onRes(null,null,bean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void doShareApp(String appid,String isShare,final OnResponCallback callback){
        try {
            JSONObject obj = new JSONObject();
            JSONObject data = new JSONObject();
            data.put("appid", appid);
            data.put("isShare", isShare);
            if (App.sUser!=null && App.sUser.getJsonResult()!=null){
                data.put("username", App.sUser.getJsonResult().getUsername());
            }
            obj.put("data",data);
            LogUtils.i("MainFragment","obj:"+obj.toString());
            NovateManager.getNovate().rxJson(Api.shareapp,obj.toString(),new RxStringCallback(){
                @Override
                public void onError(Object tag, Throwable e) {

                }

                @Override
                public void onCancel(Object tag, Throwable e) {

                }

                @Override
                public void onNext(Object tag, String response) {
                    LogUtils.i("MainFragment","doShareApp_response:"+response);
                    try {
                        Gson gson = new Gson();
                        ShareAppBean bean = gson.fromJson(response,ShareAppBean.class);
                        if (DespatchTool.toLogin(context,bean.getCode())) {
                            return;
                        }
                        callback.onRes(null,null,bean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void doThumbsup(String cardId,String isFavorite,final OnResponCallback callback){
        try {
            JSONObject obj = new JSONObject();
            JSONObject data = new JSONObject();
            data.put("cardId", cardId);
            data.put("isFavorite", isFavorite);

            if (App.sUser!=null && App.sUser.getJsonResult()!=null){
                data.put("username", App.sUser.getJsonResult().getUsername());
            }
            obj.put("data",data);
            LogUtils.i("MainFragment","obj:"+obj.toString());
            NovateManager.getNovate().rxJson(Api.cardaddfavorite,obj.toString(),new RxStringCallback(){
                @Override
                public void onError(Object tag, Throwable e) {

                }

                @Override
                public void onCancel(Object tag, Throwable e) {

                }

                @Override
                public void onNext(Object tag, String response) {
                    LogUtils.i("MainFragment","doThumbsup_response:"+response);
                    try {
                        callback.onRes(response,null,null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void postUnreadMsg(String msgclassId){
        try {
            JSONObject obj = new JSONObject();
            JSONObject data = new JSONObject();
            data.put("msgclassId", msgclassId);
            if (App.sUser!=null && App.sUser.getJsonResult()!=null){
                data.put("username", App.sUser.getJsonResult().getUsername());
            }
            obj.put("data",data);
            LogUtils.i("MainFragment","obj:"+obj.toString());
            NovateManager.getNovate().rxJson(Api.postunread,obj.toString(),new RxStringCallback(){
                @Override
                public void onError(Object tag, Throwable e) {

                }

                @Override
                public void onCancel(Object tag, Throwable e) {

                }

                @Override
                public void onNext(Object tag, String response) {
                    LogUtils.i("MainFragment","postUnreadMsg_response:"+response);
                    try {
                        /*Gson gson = new Gson();
                        ShareAppBean bean = gson.fromJson(response,ShareAppBean.class);
                        if (DespatchTool.toLogin(context,bean.getCode())) {
                            return;
                        }
                        callback.onRes(null,null,bean);*/
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void queryFlowerFilter(String classifyId,final OnResponCallback callback){
        try {
            JSONObject obj = new JSONObject();
            JSONObject data = new JSONObject();
            data.put("classifyId", classifyId);
            if (App.sUser!=null && App.sUser.getJsonResult()!=null){
                data.put("username", App.sUser.getJsonResult().getUsername());
            }
            obj.put("data",data);
            LogUtils.i("MainFragment","obj:"+obj.toString());
            NovateManager.getNovate().rxJson(Api.getcattr,obj.toString(),new RxStringCallback(){
                @Override
                public void onError(Object tag, Throwable e) {

                }

                @Override
                public void onCancel(Object tag, Throwable e) {

                }

                @Override
                public void onNext(Object tag, String response) {
                    LogUtils.i("MainFragment","queryFlowerFilter_response:"+response);
                    try {
                        Gson gson = new Gson();
                        FlowerFilterBean bean = gson.fromJson(response,FlowerFilterBean.class);
                        if (DespatchTool.toLogin(context,bean.getCode())) {
                            return;
                        }
                        callback.onRes(null,null,bean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

   /* public void getcommissionlist(){
        try {
            JSONObject obj = new JSONObject();
            JSONObject data = new JSONObject();
            data.put("classifyId", classifyId);
            if (App.sUser!=null && App.sUser.getJsonResult()!=null){
                data.put("username", App.sUser.getJsonResult().getUsername());
            }
            obj.put("data",data);
            LogUtils.i("MainFragment","obj:"+obj.toString());
            NovateManager.getNovate().rxJson(Api.getcattr,obj.toString(),new RxStringCallback(){
                @Override
                public void onError(Object tag, Throwable e) {

                }

                @Override
                public void onCancel(Object tag, Throwable e) {

                }

                @Override
                public void onNext(Object tag, String response) {
                    LogUtils.i("MainFragment","queryFlowerFilter_response:"+response);
                    try {
                        Gson gson = new Gson();
                        FlowerFilterBean bean = gson.fromJson(response,FlowerFilterBean.class);
                        if (DespatchTool.toLogin(context,bean.getCode())) {
                            return;
                        }
                        callback.onRes(null,null,bean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/

}
