package com.wisesoft.mytinker;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.wisesoft.mytinker.tinker.Log.MyLogImp;
import com.wisesoft.mytinker.tinker.util.SampleApplicationContext;
import com.wisesoft.mytinker.tinker.util.TinkerManager;

/**
 * TODO :
 * Created by owen
 * on 2017-11-01.
 */
@SuppressWarnings("unused")
@DefaultLifeCycle(application = "com.wisesoft.mytinker.App",
        flags = ShareConstants.TINKER_ENABLE_ALL,
        loadVerifyFlag = false)
public class AppLike extends DefaultApplicationLike {

    public static final String TAG = "AppLike";

    public AppLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        Log.d(TAG, "onBaseContextAttached: ");
        initTinker();
    }

    private void initTinker() {
        SampleApplicationContext.application = getApplication();
        SampleApplicationContext.context = getApplication();
        TinkerManager.setTinkerApplicationLike(this);

        TinkerManager.initFastCrashProtect();
        //should set before tinker is installed
        TinkerManager.setUpgradeRetryEnable(true);

        //optional set logIml, or you can use default debug log
        TinkerInstaller.setLogIml(new MyLogImp());

        //installTinker after load multiDex
        //or you can put com.tencent.tinker.** to main dex
        TinkerManager.installTinker(this);
        Tinker tinker = Tinker.with(getApplication());
    }
}
