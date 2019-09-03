package com.jiama.JinNeng.ui.main.presenter;

import com.jiama.JinNeng.bean.Main2Bean;
import com.jiama.JinNeng.bean.PayBean;
import com.jiama.JinNeng.bean.UpdateBean;
import com.jiama.JinNeng.ui.me.model.MeModel;
import com.tony.corelibrary.base.BaseActivity;
import com.tony.corelibrary.base.BaseView;
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
import com.jiama.JinNeng.ui.main.contract.MainContract;
import com.jiama.JinNeng.ui.main.model.MainModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainPresenter {
    MainContract.MainView IMainView;
    MainContract.Main2View IMain2View;
    MainContract.MainAddressCityView IMainAddressCityView;
    MainContract.MainParkView IMainParkView;
    MainContract.ParkDetailView IParkDetailView;
    MainContract.GovernmentDetailView IGovernmentDetailView;
    MainContract.GovernmentInfoView IGovernmentInfoView;
    MainContract.FlowerInfoView IFlowerInfoView;
    MainContract.FlowerSearchView IFlowerSearchView;
    MainContract.FlowerDetailView IFlowerDetailView;
    MainContract.MainFlowerCommentView IMainFlowerCommentView;
    MainContract.MainConfirmOrderView IMainConfirmOrderView;
    MainContract.MainGreenCoinCatch IMainGreenCoinCatch;
    MainContract.MainMessageView IMainMessageView;
    MainContract.MainGreenCoinCatchQuestionView IMainGreenCoinCatchQuestionView;
    MainContract.MainGreenCoinPlay IMainGreenCoinPlay;
    MainContract.MainGreenPlayDetail IMainGreenPlayDetail;
    MainContract.MainGreenShow IMainGreenShow;
    MainContract.MainGreenShowDetail IMainGreenShowDetail;
    MainContract.MeCoinPlayShowPicView IMeCoinPlayShowPicView;
    MainContract.MainGreenCoinShowPublishView IMainGreenCoinShowPublishView;
    MainContract.MainGreenCoinPlayAppointView IMainGreenCoinPlayAppointView;
    MainContract.MainAppointRecordView IMainAppointRecordView;
    MainContract.MainAppointDetailView IMainAppointDetailView;

    MainModel mMainModel;
    BaseActivity context;
    public MainPresenter(BaseActivity context,BaseView IBaseView){
        this.context = context;
        if (IBaseView instanceof MainContract.MainView){
            this.IMainView = (MainContract.MainView) IBaseView;
        }else if (IBaseView instanceof MainContract.MainAddressCityView){
            this.IMainAddressCityView = (MainContract.MainAddressCityView) IBaseView;
        }else if (IBaseView instanceof MainContract.MainParkView){
            this.IMainParkView = (MainContract.MainParkView) IBaseView;
        }else if (IBaseView instanceof MainContract.ParkDetailView){
            this.IParkDetailView = (MainContract.ParkDetailView) IBaseView;
        }else if (IBaseView instanceof MainContract.GovernmentDetailView){
            this.IGovernmentDetailView = (MainContract.GovernmentDetailView) IBaseView;
        }else if (IBaseView instanceof MainContract.GovernmentInfoView){
            this.IGovernmentInfoView = (MainContract.GovernmentInfoView) IBaseView;
        }else if (IBaseView instanceof MainContract.FlowerInfoView){
            this.IFlowerInfoView = (MainContract.FlowerInfoView) IBaseView;
        }else if (IBaseView instanceof MainContract.FlowerSearchView){
            this.IFlowerSearchView = (MainContract.FlowerSearchView) IBaseView;
        }else if (IBaseView instanceof MainContract.FlowerDetailView){
            IFlowerDetailView = (MainContract.FlowerDetailView) IBaseView;
        }else if (IBaseView instanceof MainContract.MainFlowerCommentView){
            IMainFlowerCommentView = (MainContract.MainFlowerCommentView) IBaseView;
        }else if (IBaseView instanceof MainContract.MainConfirmOrderView){
            IMainConfirmOrderView = (MainContract.MainConfirmOrderView) IBaseView;
        }else if (IBaseView instanceof MainContract.MainGreenCoinCatch){
            IMainGreenCoinCatch = (MainContract.MainGreenCoinCatch) IBaseView;
        }else if (IBaseView instanceof MainContract.MainMessageView){
            IMainMessageView = (MainContract.MainMessageView) IBaseView;
        }else if (IBaseView instanceof MainContract.MainGreenCoinCatchQuestionView){
            IMainGreenCoinCatchQuestionView = (MainContract.MainGreenCoinCatchQuestionView) IBaseView;
        }else if (IBaseView instanceof MainContract.MainGreenCoinPlay){
            IMainGreenCoinPlay = (MainContract.MainGreenCoinPlay) IBaseView;
        }else if (IBaseView instanceof MainContract.MainGreenPlayDetail){
            IMainGreenPlayDetail = (MainContract.MainGreenPlayDetail) IBaseView;
        }else if (IBaseView instanceof MainContract.MainGreenShow){
            IMainGreenShow = (MainContract.MainGreenShow) IBaseView;
        }else if (IBaseView instanceof MainContract.MainGreenShowDetail){
            IMainGreenShowDetail = (MainContract.MainGreenShowDetail) IBaseView;
        }else if(IBaseView instanceof MainContract.MeCoinPlayShowPicView){
            IMeCoinPlayShowPicView = (MainContract.MeCoinPlayShowPicView) IBaseView;
        }else if(IBaseView instanceof MainContract.MainGreenCoinShowPublishView){
            IMainGreenCoinShowPublishView = (MainContract.MainGreenCoinShowPublishView) IBaseView;
        }else if(IBaseView instanceof MainContract.MainGreenCoinPlayAppointView){
            IMainGreenCoinPlayAppointView = (MainContract.MainGreenCoinPlayAppointView) IBaseView;
        }else if(IBaseView instanceof MainContract.MainAppointRecordView){
            IMainAppointRecordView = (MainContract.MainAppointRecordView) IBaseView;
        }else if(IBaseView instanceof MainContract.MainAppointDetailView){
            IMainAppointDetailView = (MainContract.MainAppointDetailView) IBaseView;
        }else if (IBaseView instanceof MainContract.Main2View){
            IMain2View = (MainContract.Main2View) IBaseView;
        }
        InitModel(context);
    }

    private void InitModel(BaseActivity context){
        this.mMainModel = new MainModel(context);
    }

    public void queryCity(String json){
        mMainModel.queryCity(json, new OnResponCallback<LocationBean>() {
            @Override
            public void onRes(String res, List<LocationBean> list, LocationBean locationBean) {
                IMainAddressCityView.ReturnCity(locationBean);
            }
        });
    }

    public void queryMainPage(String username,String version){
        mMainModel.queryMainPage(username,version,new OnResponCallback<Main2Bean>() {
            @Override
            public void onRes(String res, List<Main2Bean> list, Main2Bean mainBean) {
                IMain2View.backMain(mainBean);
            }
        });
    }

    public void queryMainParkPage(String username,String channelId,String pageSize,String cardtype,String page){
        mMainModel.queryMainParkPage(username, channelId, pageSize, cardtype, page, new OnResponCallback<MainParkBean>() {
            @Override
            public void onRes(String res, List list, MainParkBean o) {
                IMainParkView.backParkData(o);
            }
        });
    }

    public void queryMainParkDetailPage(String id){
        mMainModel.queyMainParkDetailPage(id, new OnResponCallback<MainPlayDetailBean>() {
            @Override
            public void onRes(String res, List list, MainPlayDetailBean o) {
                IParkDetailView.backParkDetail(o);
            }
        });
    }

    public void queryGovernmentDetail(String articleId){
        mMainModel.queryMainGovernmentDetailInfo(articleId, new OnResponCallback<MainGovernBean.JsonResultBean.ListBean>() {
            @Override
            public void onRes(String res, List<MainGovernBean.JsonResultBean.ListBean> list, MainGovernBean.JsonResultBean.ListBean listBean) {
                IGovernmentDetailView.backGovernmentDetail(listBean);
            }
        });
    }

    public void queryGovernmentBanner(String username,String id,String clientType,String version){
        mMainModel.queryGovernmentDetailBanner(username,id,clientType,version, new OnResponCallback<MainGovernBannerBean>() {
            @Override
            public void onRes(String res, List<MainGovernBannerBean> list, MainGovernBannerBean mainGovernBannerBean) {
                IGovernmentInfoView.backGovernmentBanner(mainGovernBannerBean);
            }
        });
    }

    public void queryGovernmentInfo(String username,String channelid,String pageSize,String page){
        mMainModel.queryGovernmentInfo(username,channelid,pageSize,page, new OnResponCallback<MainGovernBean>() {
            @Override
            public void onRes(String res, List<MainGovernBean> list, MainGovernBean mainGovernBean) {
                IGovernmentInfoView.backGovernmentInfo(mainGovernBean);
            }
        });
    }

    public void queryFlowerCategoryInfo(){
        mMainModel.queryFlowerCategoryInfo(new OnResponCallback<FlowerBean.FlowerTypeBean>() {
            @Override
            public void onRes(String res, List<FlowerBean.FlowerTypeBean> list, FlowerBean.FlowerTypeBean bean) {
                IFlowerInfoView.backFlowerCategory(list);
            }
        });
    }

    public void queryFlowerList(String attrIds,String attrValues,String sort,String isAscending,String classifyId,String pageSize,String page){
        mMainModel.queryFlowerList(attrIds,attrValues,sort,isAscending, classifyId, pageSize, page, new OnResponCallback<FlowerBean.FlowListBean>() {
            @Override
            public void onRes(String res, List<FlowerBean.FlowListBean> list, FlowerBean.FlowListBean bean) {
                IFlowerInfoView.backFlowerLists(list);
            }
        });
    }

    public void queryFlowerBanner(String types,String channelid){
        mMainModel.queryFlowerBanner(types, channelid, new OnResponCallback<FlowerBean.FlowBannerBean>() {
            @Override
            public void onRes(String res, List<FlowerBean.FlowBannerBean> list, FlowerBean.FlowBannerBean bean) {
                IFlowerInfoView.backFlowerBanner(list);
            }
        });
    }

    public void queryFlowerSearch(String page,String pageSize, String keyword){
        mMainModel.queryFlowerList(keyword, pageSize, page, new OnResponCallback<FlowerBean.FlowListBean>() {
            @Override
            public void onRes(String res, List<FlowerBean.FlowListBean> list, FlowerBean.FlowListBean flowListBean) {
                IFlowerSearchView.backFlowerLists(list);
            }


        });
    }

    public void queryFlowerDetail(String goodsId){
        mMainModel.queryFlowerDetail(goodsId, new OnResponCallback<FlowerBean.FlowerDetailBean>() {
            @Override
            public void onRes(String res, List<FlowerBean.FlowerDetailBean> list, FlowerBean.FlowerDetailBean bean) {
                IFlowerDetailView.backFlowerDetailData(bean);
            }
        });
    }

    public void queryFlowerComments(String types,String goodsId,String pageSize,String page){
        mMainModel.queryFlowerComments(types,goodsId,pageSize,page, new OnResponCallback<FlowerCommentBean>() {
            @Override
            public void onRes(String res, List<FlowerCommentBean> list, FlowerCommentBean flowerCommentBean) {
                IMainFlowerCommentView.backFlowerComments(flowerCommentBean);
            }
        });
    }

    public void doFlowerCollection(String goodsId,String isFavorite){
        mMainModel.doFlowerCollection(goodsId, isFavorite, new OnResponCallback() {
            @Override
            public void onRes(String res, List list, Object o) {
                IFlowerDetailView.backFlowerCollection(res);
            }
        });
    }

    public void doAddShoppingCart(String number,String gspecIds,String statex,String ids,String goodsId,String pid,
                                  String isSelected,String names){
        mMainModel.doAddShoppingCart(number, gspecIds, statex, ids, goodsId, pid, isSelected, names, new OnResponCallback() {
            @Override
            public void onRes(String res, List list, Object o) {
                IFlowerDetailView.backFlowerAddShoppingCart(res);
            }
        });
    }

    public void doFlowerBuy(String id,String ids,String subject,String numberMode,String statex,String payment_type,String names,String total_fee,
                            String type,String gspecIds,String number){
        mMainModel.doFlowerBuy(id, ids, subject, numberMode, statex, payment_type, names, total_fee, type, gspecIds, number, new OnResponCallback() {
            @Override
            public void onRes(String res, List list, Object o) {
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    int code = jsonObject.getInt("code");
                    String msg = "";
                    int cartId=-1;
                    if (code == 0){
                        JSONObject jsonObject1 = jsonObject.getJSONObject("jsonResult");
                        cartId = jsonObject1.getInt("cartId");
                    }else {
                        msg = jsonObject.getString("msg");
                    }
                    IFlowerDetailView.backFlowerBuyResult(code,cartId,msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void queryConsigneeAddress() {
        mMainModel.queryConsigneeAddress(new OnResponCallback<ConsigneeBean>() {
            @Override
            public void onRes(String res, List<ConsigneeBean> list, ConsigneeBean o) {
                IMainConfirmOrderView.backConsigneeAddress(o);
            }
        });
    }

    public void doConfirmOrderCheckout(String cartId) {
        mMainModel.doConfirmOrderCheckout(cartId, new OnResponCallback<FlowerBean.FlowerOrderBean>() {
            @Override
            public void onRes(String res, List<FlowerBean.FlowerOrderBean> list, FlowerBean.FlowerOrderBean o) {
                IMainConfirmOrderView.backConfirmCheckout(o);
            }
        });
    }

    public void doConfirmOrderCartPatchPay(String addressId,String payment,String payPassword,Object array,String orderNumber){
        mMainModel.doConfirmOrderCartPatchPay(addressId,payment,payPassword,array,orderNumber,new OnResponCallback<PayBean>() {
            @Override
            public void onRes(String res, List list, PayBean o) {
                IMainConfirmOrderView.backConfirmCartPay(o);
            }
        });

    }

    public void queryGreenCoinPlay(String channelid,String pageSize,String sectionid,String page ){
        mMainModel.queryGreenCoinPlay(channelid, pageSize, sectionid, page, new OnResponCallback<MainParkBean>() {
            @Override
            public void onRes(String res, List<MainParkBean> list, MainParkBean o) {
                IMainGreenCoinPlay.backGreenCoinPlay(o);
            }
        });


    }

    public void queryGreenCoinShow(String channelid,String pageSize,String sectionid,String page ){
        mMainModel.queryGreenCoinShow(channelid, pageSize, sectionid, page, new OnResponCallback<MainParkBean>() {
            @Override
            public void onRes(String res, List<MainParkBean> list, MainParkBean o) {
                IMainGreenShow.backGreenShow(o);
            }
        });
    }

    public void queryGreenCoinShowDetail(String id){
        mMainModel.queryMainShowDetailPage(id, new OnResponCallback<MainShowDetailBean>() {
            @Override
            public void onRes(String res, List<MainShowDetailBean> list, MainShowDetailBean o) {
                IMainGreenShowDetail.backGreenShowDetail(o);
            }
        });
    }

    public void queryGreenCoinCatch(){
        mMainModel.queryGreenCoinCatch(new OnResponCallback<GreenCoinCatchBean>() {
            @Override
            public void onRes(String res, List<GreenCoinCatchBean> list, GreenCoinCatchBean o) {
                IMainGreenCoinCatch.backCatchData(o);
            }
        });
    }

    public void queryGreenCoinCathBanner(){
        mMainModel.queryGreenCoinCathBanner(new OnResponCallback<CatchBanner>() {
            @Override
            public void onRes(String res, List<CatchBanner> list, CatchBanner catchBanner) {
                IMainGreenCoinCatch.backCathBanner(catchBanner);
            }
        });
    }

    public void greenCoinCatchCheckSign(String type,String timestamp){
        mMainModel.greenCoinCatchCheckSign(type, timestamp, new OnResponCallback<SignBean>() {
            @Override
            public void onRes(String res, List<SignBean> list, SignBean o) {
                IMainGreenCoinCatch.backCatchSign(o);
            }
        });
    }

    public void doGreenCoinCatchSign(String timestamp){

        mMainModel.doGreenCoinCatchSign(timestamp, new OnResponCallback() {
            @Override
            public void onRes(String res, List list, Object o) {
                IMainGreenCoinCatch.bacCatchSignResult(res);
            }
        });
    }

    public void queryMessages(String pageSize,String page){
        mMainModel.queryMessages(pageSize, page, new OnResponCallback<MessageBean>() {
            @Override
            public void onRes(String res, List<MessageBean> list, MessageBean o) {
                IMainMessageView.backMessageData(o);
            }
        });
    }
    public void queryMessages2(String pageSize,String page){
        mMainModel.queryMessages(pageSize, page, new OnResponCallback<MessageBean>() {
            @Override
            public void onRes(String res, List<MessageBean> list, MessageBean o) {
                IMainView.backMessageData(o);
            }
        });
    }

    public void queryGreenCoinQuestion(){
        mMainModel.queryGreenCoinQuestion(new OnResponCallback<QuestionBean>() {
            @Override
            public void onRes(String res, List<QuestionBean> list, QuestionBean o) {
                IMainGreenCoinCatchQuestionView.backQuestion(o);
            }
        });
    }

    public void queryGreenCoinNextQuestion(String questionId,String answerPos,String answer,String taskId){
        mMainModel.queryGreenCoinNextQuestion(questionId, answerPos, answer,taskId, new OnResponCallback<QuestionNextBean>() {
            @Override
            public void onRes(String res, List list, QuestionNextBean o) {
                IMainGreenCoinCatchQuestionView.backNextQuestion(o);
            }
        });
    }

    public void queryGreenCoinPlayDetail(String id){
        mMainModel.queyMainParkDetailPage(id, new OnResponCallback<MainPlayDetailBean>() {
            @Override
            public void onRes(String res, List<MainPlayDetailBean> list, MainPlayDetailBean o) {
                IMainGreenPlayDetail.backGreenPlayDetail(o);
            }
        });
    }

    public void doPostGoodComment(String id,String isThumbsup){
        mMainModel.doPostGoodComment(id,isThumbsup, new OnResponCallback<String>() {
            @Override
            public void onRes(String res, List<String> list, String o) {
                IMainGreenPlayDetail.backGoodPost(o);
            }
        });
    }

    public void queryHotTag(){
        mMainModel.queryHotTag(new OnResponCallback<HotTagBean>() {
            @Override
            public void onRes(String res, List list, HotTagBean o) {
                IFlowerSearchView.backHotLists(o);
            }
        });
    }

    public void doShowCoinPlayPic(boolean isVideo,String id,String title,String content,List<String> files){
        mMainModel.doShowCoinPlayPic(isVideo,id,title,content, files, new OnResponCallback() {
            @Override
            public void onRes(String res, List list, Object o) {

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(res);
                    int code = jsonObject.getInt("code");
                    String msg = jsonObject.getString("msg");
                    if (DespatchTool.toLogin(context,code)) {
                        return;
                    }
                    IMeCoinPlayShowPicView.backUploadShow(code,msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void doGreenCoinShowPublish(boolean isVideo,String id,String title,String content,String thumb,List<String> files){
        mMainModel.doPushlishShow(isVideo,id,title,content,thumb, files, new OnResponCallback() {
            @Override
            public void onRes(String res, List list, Object o) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(res);
                    int code = jsonObject.getInt("code");
                    String msg = jsonObject.getString("msg");
                    if (DespatchTool.toLogin(context,code)) {
                        return;
                    }
                    IMainGreenCoinShowPublishView.backUploadShow(code,msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void doPostAppoint(String eventId,String name,String mobile,String idcard,String areaId){
        mMainModel.doPostAppoint(eventId, name, mobile, idcard, areaId, new OnResponCallback<AppointBean>() {
            @Override
            public void onRes(String res, List list, AppointBean o) {
                IMainGreenCoinPlayAppointView.backAppoint(o);
            }
        });
    }

    public void queryAppointRecord(String eventId){
        mMainModel.queryAppointRecord(eventId, new OnResponCallback<AppointRecordBean>() {
            @Override
            public void onRes(String res, List list, AppointRecordBean o) {
                IMainAppointRecordView.backAppointRecord(o);
            }
        });
    }

    public void queryAppointDetail(String appointcode){
        mMainModel.queryAppointDetail(appointcode, new OnResponCallback<AppointRecordBean.JsonResultBean.ListBean>() {
            @Override
            public void onRes(String res, List list, AppointRecordBean.JsonResultBean.ListBean o) {
                IMainAppointDetailView.backAppointDetail(o);
            }
        });
    }

    public void doReceiveQuestionReward(String taskId){
        mMainModel.doReceiveQuestionReward(taskId,new OnResponCallback<ReceiveRewardBean>() {
            @Override
            public void onRes(String res, List list, ReceiveRewardBean bean) {
                IMainGreenCoinCatch.backCatchReceiveReward(bean);
            }
        });
    }

    public void doShareApp(String appid,String isShare){
        mMainModel.doShareApp(appid, isShare, new OnResponCallback<ShareAppBean>() {
            @Override
            public void onRes(String res, List list, ShareAppBean o) {
                IMainGreenCoinCatch.backShareApp(o);
            }
        });
    }

    public void doThumbsup(String cardId,String isFavorite){
        mMainModel.doThumbsup(cardId, isFavorite, new OnResponCallback() {
            @Override
            public void onRes(String res, List list, Object o) {
                IMainGreenPlayDetail.backThumb(res);
            }
        });
    }

    public void postUnreadMsg(String ids){
        mMainModel.postUnreadMsg(ids);
    }

    public void queryFlowerFilter(String classifyId){
        mMainModel.queryFlowerFilter(classifyId,new OnResponCallback<FlowerFilterBean>(){
            @Override
            public void onRes(String res, List<FlowerFilterBean> list, FlowerFilterBean flowerFilterBean) {
                IFlowerInfoView.backFlowerFilter(flowerFilterBean);
            }
        });
    }


    public void updateApp(String version,String uuid,String screen,String dpi,String networkinfo,String oslanguage){
        MeModel mMeModel = new MeModel(context);
        mMeModel.updateApp(version, uuid, screen, dpi, networkinfo, oslanguage, new OnResponCallback<UpdateBean>() {
            @Override
            public void onRes(String res, List list, UpdateBean o) {
                IMain2View.backUpdateApp(o);
            }
        });
    }

}
