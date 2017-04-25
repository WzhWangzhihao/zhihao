package com.asia_eagle.money.erqing.activity;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.asia_eagle.money.erqing.R;
import com.asia_eagle.money.erqing.entity.Token;
import com.asia_eagle.money.erqing.util.BaseApplication;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.io.File;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;

/**
 * @author wzh
 */
public class App extends BaseApplication {
    static final String TAG = App.class.getSimpleName();

    /**
     * 列表中显示图片的选项
     */
    private DisplayImageOptions listViewDisplayImageOptions;

    @Override
    public void onCreate() {
        super.onCreate();
        //设置列表图片显示配置
        listViewDisplayImageOptions = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.loadinghead)
                // 设置图片在下载期间显示的图片
                .showImageOnLoading(R.mipmap.loadinghead)
                // 设置图片Uri为空或是错误的时候显示的图片
                .showImageForEmptyUri(R.mipmap.loadinghead)
                // 设置图片加载/解码过程中错误时候显示的图片
                .showImageOnFail(R.mipmap.loadinghead)
                // 设置下载的图片是否缓存在内存中
                .cacheInMemory(true)
                // 设置下载的图片是否缓存在SD卡中
                .cacheOnDisk(true)
                // 加载图片时会在磁盘中加载缓存
                .cacheOnDisc(true)
                // decodingOptions)//设置图片的解码配置
                .considerExifParams(true)
                // 设置图片以如何的编码方式显示
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .connectTimeout(45000L, TimeUnit.MILLISECONDS)
//                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(45000L, TimeUnit.MILLISECONDS)
                .readTimeout(45000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    public static App getInstance() {
        return (App) BaseApplication.getInstance();
    }


    /**
     * 初始化ImageLoader
     *
     * @param context
     */
    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(200 * 1024 * 1024) // 200 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .threadPoolSize(4)
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(config);
    }

    public DisplayImageOptions getListViewDisplayImageOptions() {
        return listViewDisplayImageOptions;
    }


    /**
     * 判断手机格式
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^1[3-8]\\d{9}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static String instId;
    public static String merNum;

    public static String getInstId() {
        return instId;
    }

    public static void setInstId(String instId) {
        App.instId = instId;
    }

    public static String getMerNum() {
        return merNum;
    }

    public static void setMerNum(String merNum) {
        App.merNum = merNum;
    }

    /**
     * 保存Token
     */
    public static Token token;

    public static Token getToken() {
        return token;
    }

    public static void setToken(Token token) {
        App.token = token;
    }

    /**
     * 获取路径
     *
     * @return
     */
    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();
    }

    /**
     * 获取毫秒 保存格式为。jpg
     *
     * @return
     */
    public static String timeDate() {
        String s = "";
        try {
            Date date = new Date();
            long unixTimestamp = date.getTime() / 1000;
            System.out.println(unixTimestamp);
            s = unixTimestamp + ".jpg";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * 不闪动的加载头像
     */
    public static void setPhotoNoShan(String url, final ImageView iv_head) {
        if (TextUtils.isEmpty(url)) {
//            iv_head.setImageResource(R.mipmap.loadinghead);
        } else {
            ImageLoader.getInstance().loadImage(url, App.getInstance().getListViewDisplayImageOptions(), new ImageLoadingListener() {


                @Override
                public void onLoadingStarted(String arg0, View arg1) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onLoadingFailed(String arg0, View arg1,
                                            FailReason arg2) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
                    iv_head.setImageBitmap(arg2);

                }

                @Override
                public void onLoadingCancelled(String arg0, View arg1) {
                    // TODO Auto-generated method stub

                }
            });
        }
    }
}
