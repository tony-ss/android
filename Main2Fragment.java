package com.jiama.JinNeng.ui.main.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.LocaleList;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jiama.JinNeng.R;
import com.jiama.JinNeng.baseapp.App;
import com.jiama.JinNeng.bean.Main2Bean;
import com.jiama.JinNeng.bean.UpdateBean;
import com.jiama.JinNeng.common.DespatchTool;
import com.jiama.JinNeng.ui.life.activity.LifeDoorTicketActivity;
import com.jiama.JinNeng.ui.main.activity.MainActivity;
import com.jiama.JinNeng.ui.main.activity.MainFlowerDetailActivity;
import com.jiama.JinNeng.ui.main.contract.MainContract;
import com.jiama.JinNeng.ui.main.presenter.MainPresenter;
import com.jiama.JinNeng.widget.MyUpdateVersionDialog;
import com.tony.corelibrary.base.BaseFragment;
import com.tony.corelibrary.common.commonutils.DisplayUtil;
import com.tony.corelibrary.widget.imgpicker.bean.Image;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class Main2Fragment extends BaseFragment implements MainContract.Main2View{

    @BindView(R.id.recycleview)
    RecyclerView recycleview;

    CommonAdapter commonAdapter;
    List<Main2Bean.ProductBean.ListBeanX> list = new ArrayList<>();

    HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    LayoutInflater inflater;
    Banner banner;
    ImageView img_top;
    ImageView img_introduce;
    MainPresenter mainPresenter;
    static JzvdStd jzvdStd;
    public Main2Fragment() {
    }

    public static Main2Fragment newInstance() {
        Main2Fragment fragment = new Main2Fragment();
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_main2;
    }

    @Override
    public void initPresenter() {
        mainPresenter = new MainPresenter((MainActivity)getActivity(),this);
    }

    @Override
    protected void initView() {
        inflater = LayoutInflater.from(context);
        recycleview.setLayoutManager(new LinearLayoutManager(context));
        commonAdapter = new CommonAdapter<Main2Bean.ProductBean.ListBeanX>(context, R.layout.item_main_layout, list) {
            @Override
            protected void convert(ViewHolder holder, Main2Bean.ProductBean.ListBeanX s, int position) {
                ImageView img_product = holder.getView(R.id.img_product);
                Glide.with(context).load(s.getGoodsImg()).error(R.mipmap.moren).placeholder(R.mipmap.moren).error(R.mipmap.moren).placeholder(R.mipmap.moren).into(img_product);
                holder.setText(R.id.tv_title,s.getGoodsName());
               // holder.setText(R.id.tv_salenum,String.format("已售%s件",s.getSales()));
                holder.setText(R.id.tv_price,s.getPrice()+"");
                holder.setOnClickListener(R.id.rela_content, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString("goodsId",String.valueOf(s.getGoodsId()));
                        startActivity(MainFlowerDetailActivity.class,bundle);
                    }
                });
            }
        };
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(commonAdapter);
        View view_head = inflater.inflate(R.layout.main_head_layout,null,false);
        banner = view_head.findViewById(R.id.banner);
        img_top = view_head.findViewById(R.id.img_top);
        View view_footer = inflater.inflate(R.layout.main_footer_layout,null,false);
        img_introduce = view_footer.findViewById(R.id.img_introduce);
        img_introduce.setMaxHeight(DisplayUtil.getScreenHeight(context)*10);
        img_introduce.setMaxWidth(DisplayUtil.getScreenWidth(context));
        jzvdStd = view_footer.findViewById(R.id.videoplayer);
        jzvdStd.thumbImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mHeaderAndFooterWrapper.addHeaderView(view_head);
        mHeaderAndFooterWrapper.addFootView(view_footer);
        recycleview.setAdapter(mHeaderAndFooterWrapper);
        String uuid = getIMSI()+getIMEI();
        mainPresenter.updateApp(packageName(context),uuid
                ,getMetric(),getdip(),GetNetworkType(),getLocalLanager());
        handler = new JingNengHandler();
    }
    JingNengHandler handler;
    String username;
    public void queryMainData(){
        if (App.sUser!=null && App.sUser.isLogin()){
            username = App.sUser.getJsonResult().getUsername();
        }else {
            username=null;
        }
        mainPresenter.queryMainPage(username,"1.0.0");
    }

    /**
     * 获取手机IMSI号
     */
    public String getIMSI() {
        TelephonyManager mTelephonyMgr = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return  "";
        }
        String imsi = mTelephonyMgr.getSubscriberId();
        return imsi ;
    }

    /**
     * 获取手机IMEI号
     */
    public String getIMEI() {
        TelephonyManager telephonyManager = (TelephonyManager) getActivity().getSystemService(context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return "";
        }
        String imei = telephonyManager.getDeviceId();
        return imei;
    }

    public String getdip(){
        DisplayMetrics dm = getResources().getDisplayMetrics();
        return dm.densityDpi+"";
    }

    public String getMetric(){
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        return  screenWidth+"x"+screenHeight;
    }

    public String getLocalLanager(){
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0);
        } else locale = Locale.getDefault();

        return locale.getLanguage();
    }

    public String GetNetworkType()
    {
        String strNetworkType = "";

        NetworkInfo networkInfo = ((ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
        {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI)
            {
                strNetworkType = "WIFI";
            }
            else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE)
            {
                String _strSubTypeName = networkInfo.getSubtypeName();

                Log.e("cocos2d-x", "Network getSubtypeName : " + _strSubTypeName);

                // TD-SCDMA   networkType is 17
                int networkType = networkInfo.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                        strNetworkType = "2G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                        strNetworkType = "3G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                        strNetworkType = "4G";
                        break;
                    default:
                        // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                        if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA") || _strSubTypeName.equalsIgnoreCase("CDMA2000"))
                        {
                            strNetworkType = "3G";
                        }
                        else
                        {
                            strNetworkType = _strSubTypeName;
                        }

                        break;
                }

                Log.e("cocos2d-x", "Network getSubtype : " + Integer.valueOf(networkType).toString());
            }
        }

        Log.e("cocos2d-x", "Network Type : " + strNetworkType);
        if(strNetworkType.equals("WIFI")){
            strNetworkType = "1";
        }else if (strNetworkType.equals("3G")){
            strNetworkType = "3";
        }else if (strNetworkType.equals("4G")){
            strNetworkType = "4";
        }else if (strNetworkType.equals("2G")){
            strNetworkType = "2";
        }else{
            strNetworkType = "0";
        }
        return strNetworkType;
    }

    public static String packageName(Context context) {
        PackageManager manager = context.getPackageManager();
        String name = null;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return name;
    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {

    }
    static String videourl="";
    @Override
    public void backMain(Main2Bean bean) {
        if (bean!=null){
            videourl = bean.getVideoUrl();
            if (bean.getVideoUrl()!=null && bean.getVideoUrl().length()>0){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap = getNetVideoBitmap(bean.getVideoUrl());
                        Message msg = Message.obtain();
                        msg.what = 1000;
                        msg.obj = bitmap;
                        handler.sendMessage(msg);
                    }
                }).start();
            }
            if (bean.getImageIntruduce()!=null && bean.getImageIntruduce().length()>0){
                Glide.with(context).load(bean.getImageIntruduce()).error(R.mipmap.moren).placeholder(R.mipmap.moren).into(img_introduce);
            }
            if (bean.getProductTopImg()!=null && bean.getProductTopImg().length()>0){
                Glide.with(context).load(bean.getProductTopImg()).into(img_top);
            }
            list.clear();
            if (bean.getmProductBean()!=null && bean.getmProductBean().getList()!=null){
                list.addAll(bean.getmProductBean().getList());
            }
            List<String> images = new ArrayList<>();
            if (bean.getBannerMain()!=null && bean.getBannerMain().getList()!=null){
                for (int i = 0; i < bean.getBannerMain().getList().size(); i++) {
                    images.add(bean.getBannerMain().getList().get(i).getPicurl());
                }
            }

            //设置banner样式(显示圆形指示器)
            banner.setBannerStyle(com.youth.banner.BannerConfig.CIRCLE_INDICATOR);
            //设置指示器位置（指示器居右）
            banner.setIndicatorGravity(BannerConfig.RIGHT);
            //设置图片加载器
            banner.setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    Glide.with(getActivity()).load(String.valueOf(path)).error(R.mipmap.moren).placeholder(R.mipmap.moren).into(imageView);
                }
            });
            //设置图片集合
            banner.setImages(images);
            //设置banner动画效果
//        banner.setBannerAnimation(Transformer.DepthPage);
            //设置标题集合（当banner样式有显示title时）
//        banner.setBannerTitles(titles);
            //设置自动轮播，默认为true
//        banner.isAutoPlay(true);
            //设置轮播时间
            banner.setDelayTime(3000);
            //banner设置方法全部调用完毕时最后调用
            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    DespatchTool.dispatchPage(context,bean.getBannerMain().getList().get(position).getRedirecturl());
                }
            });
            banner.start();
        }
    }
    MyUpdateVersionDialog myUpdateVersionDialog;
    @Override
    public void backUpdateApp(UpdateBean updateBean) {
        if (updateBean!=null){
            if (updateBean.getCode()==0) {
                if (updateBean.getJsonResult()!=null){
                    if(updateBean.getJsonResult().getStatus()==1){//有更新
                        myUpdateVersionDialog = new MyUpdateVersionDialog(context,R.style.MyDialogStyle2,updateBean);
                        myUpdateVersionDialog.setCanceledOnTouchOutside(false);
                        myUpdateVersionDialog.setCancelable(false);
                        myUpdateVersionDialog.show();
                    }else if(updateBean.getJsonResult().getStatus()==0){
                        new com.tony.corelibrary.widget.MyToast(context,updateBean.getMsg(),2000).show();
                    }
                }
            }
        }
    }


    public static Bitmap getNetVideoBitmap(String videoUrl) {
        Bitmap bitmap = null;

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //根据url获取缩略图
            retriever.setDataSource(videoUrl, new HashMap());
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }
        return bitmap;
    }

    class JingNengHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1000:
                    Bitmap bitmap = (Bitmap) msg.obj;
                    jzvdStd.setVisibility(View.VISIBLE);
                    jzvdStd.thumbImageView.setImageBitmap(bitmap);
                    jzvdStd.setUp(videourl,"", Jzvd.SCREEN_WINDOW_LIST);
                    mHeaderAndFooterWrapper.notifyDataSetChanged();
                    break;
            }
        }
    }

}
