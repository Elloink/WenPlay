package com.example.wen.wenplay.common.apkparset;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**

 */

public class AndroidApkParser {


    public static Resources getResources(Context context , String apkPath) throws Exception {
        String PATH_AssetManager = "android.content.res.AssetManager";
        Class assetMagCls = Class.forName(PATH_AssetManager);
        Constructor assetMagCt = assetMagCls.getConstructor((Class[]) null);
        Object assetMag = assetMagCt.newInstance((Object[]) null);
        Class[] typeArgs = new Class[1];
        typeArgs[0] = String.class;
        Method assetMag_addAssetPathMtd = assetMagCls.getDeclaredMethod("addAssetPath",
                typeArgs);
        Object[] valueArgs = new Object[1];
        valueArgs[0] = apkPath;
        assetMag_addAssetPathMtd.invoke(assetMag, valueArgs);
        Resources res = context.getResources();
        typeArgs = new Class[3];
        typeArgs[0] = assetMag.getClass();
        typeArgs[1] = res.getDisplayMetrics().getClass();
        typeArgs[2] = res.getConfiguration().getClass();
        Constructor resCt = Resources.class.getConstructor(typeArgs);
        valueArgs = new Object[3];
        valueArgs[0] = assetMag;
        valueArgs[1] = res.getDisplayMetrics();
        valueArgs[2] = res.getConfiguration();
        res = (Resources) resCt.newInstance(valueArgs);
        return res;
    }


    /**
     * 根据该文件的路径将apk转化为AndroidApk对象
     * @param context 上下文
     * @param path 文件的路径
     * @return
     */
    public static AndroidApk getUninstallAPK(Context context, String path) {

        PackageManager pm = context.getPackageManager();

       PackageInfo info =  pm.getPackageArchiveInfo(path,PackageManager.GET_ACTIVITIES);

        if(info !=null){ //如果apk文件没有下载完，则拿不到PackageInfo，因此需要判断空

            AndroidApk apk = new AndroidApk();

            apk.setPackageName(info.packageName);
            apk.setApkPath(path);
            apk.setAppVersionCode(info.versionCode+"");
            apk.setAppVersionName(info.versionName);

            //下面的只能拿到已经安装的APP的名称和图标，未安装的需要利用反射拿到Resources对象来获取res目录里面的资源
        /*   ApplicationInfo applicationInfo = info.applicationInfo;
            CharSequence charSequence = applicationInfo.loadLabel(pm);
            Drawable drawable = applicationInfo.loadIcon(pm);*/

            Resources res =null;

            try {
                res = getResources(context,path);

                apk.setAppName(res.getString(info.applicationInfo.labelRes));

                Drawable icon = res.getDrawable(info.applicationInfo.icon);
                apk.setDrawable(icon);


            } catch (Exception e) {
                e.printStackTrace();
            }

            return  apk;

        }

        return null;



    }
}
