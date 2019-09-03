package com.jiama.JinNeng.ui.main.contract;

import com.jiama.JinNeng.bean.Main2Bean;
import com.jiama.JinNeng.bean.PayBean;
import com.jiama.JinNeng.bean.UpdateBean;
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

import java.util.List;

public interface MainContract {

    interface MainView extends BaseView {//首页 UI接口
        void backMainData(MainBean mainBean);
        void backMessageData(MessageBean bean);
    }

    interface Main2View extends BaseView{
        void backMain(Main2Bean bean);
        void backUpdateApp(UpdateBean bean);
    }


    interface MainAddressCityView extends BaseView{//选择城市
        void ReturnCity(LocationBean bean);
    }

    interface MainParkView extends BaseView{//深圳公园列表UI
        void backParkData(MainParkBean bean);
    }

    interface ParkDetailView extends BaseView{
        void backParkDetail(MainPlayDetailBean bean);
    }

    interface GovernmentInfoView extends BaseView{
        void backGovernmentBanner(MainGovernBannerBean banner);
        void backGovernmentInfo(MainGovernBean bean);

    }

    interface GovernmentDetailView extends BaseView{
        void backGovernmentDetail(MainGovernBean.JsonResultBean.ListBean bean);
    }

    interface FlowerInfoView extends BaseView{
        void backFlowerCategory(List<FlowerBean.FlowerTypeBean> flowercategory);
        void backFlowerBanner(List<FlowerBean.FlowBannerBean> flowBannerBeans);
        void backFlowerLists(List<FlowerBean.FlowListBean> flowListBeans);
        void backFlowerFilter(FlowerFilterBean flowerFilterBean);
    }

    interface FlowerSearchView extends BaseView{
        void backFlowerLists(List<FlowerBean.FlowListBean> flowListBeans);
        void backHotLists(HotTagBean bean);
    }

    interface FlowerDetailView extends BaseView{
        void backFlowerDetailData(FlowerBean.FlowerDetailBean flowerDetailBean);
        void backFlowerAddShoppingCart(String code);
        void backFlowerCollection(String code);
        void backFlowerBuyResult(int code, int cartId, String msg);
    }

    interface MainFlowerCommentView extends BaseView {
        void backFlowerComments(FlowerCommentBean flowerCommentBean);
    }

    interface MainConfirmOrderView extends BaseView {
        void backConsigneeAddress(ConsigneeBean bean);
        void backConfirmCheckout(FlowerBean.FlowerOrderBean bean);
        void backConfirmCartPay(PayBean bean);
    }

    /*interface MainGreenCoinPlayView extends BaseView {
        void backGreenCoinPlay(int code,List<>);
    }*/

    interface MainGreenCoinCatch extends BaseView {
        void backCatchData(GreenCoinCatchBean bean);
        void backCatchSign(SignBean bean);
        void bacCatchSignResult(String json);
        void backCatchReceiveReward(ReceiveRewardBean bean);
        void backShareApp(ShareAppBean bean);
        void backCathBanner(CatchBanner catchBanner);
    }

    interface MainMessageView extends BaseView {
        void backMessageData(MessageBean bean);
    }

    interface MainGreenCoinCatchQuestionView extends BaseView {
        void backQuestion(QuestionBean bean);
        void backNextQuestion(QuestionNextBean bean);
    }

    interface MainGreenCoinPlay extends BaseView {
        void backGreenCoinPlay(MainParkBean mainParkBean);
    }

    interface MainGreenPlayDetail extends BaseView {
        void backGreenPlayDetail(MainPlayDetailBean bean);
        void backThumb(String json);
        void backGoodPost(String json);
    }

    interface MainGreenShow extends BaseView {
        void backGreenShow(MainParkBean mainParkBean);
    }

    interface MainGreenShowDetail extends BaseView {
        void backGreenShowDetail(MainShowDetailBean bean);
    }

    interface MeCoinPlayShowPicView extends BaseView{
        void backUploadShow(int code, String msg);
    }

    interface MainGreenCoinShowPublishView extends BaseView{
        void backUploadShow(int code, String msg);
    }

    interface MainGreenCoinPlayAppointView extends BaseView {
        void backAppoint(AppointBean bean);
    }

    interface MainAppointRecordView extends BaseView{
        void backAppointRecord(AppointRecordBean bean);
    }

    interface MainAppointDetailView extends BaseView{
        void backAppointDetail(AppointRecordBean.JsonResultBean.ListBean bean);
    }

}
