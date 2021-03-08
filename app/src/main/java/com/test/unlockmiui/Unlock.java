package com.test.unlockmiui;

import android.content.Context;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

/**
 * @author zhang
 */
public class Unlock implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("com.xiaomi.mirror")) {
            return;
        }
        XposedBridge.log("已加载MIUI+应用。");

        findAndHookMethod("com.xiaomi.mirror.ak", lpparam.classLoader, "a", Context.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                param.setResult(true);
                XposedBridge.log("替换方法返回值成功。");
                // this will be called before the clock was updated by the original method
            }
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                param.setResult(true);
//                XposedBridge.log("替换方法返回值成功。");
//                // this will be called after the clock was updated by the original method
//            }
        });
    }
}
