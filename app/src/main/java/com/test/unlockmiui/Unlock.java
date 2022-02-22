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
        XposedBridge.log("Loaded APP: MIUI+ BETA version");

        findAndHookMethod("com.xiaomi.mirror.ak", lpparam.classLoader, "a", Context.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                // this will be called before the clock was updated by the original method
                param.setResult(true);
                XposedBridge.log("Set the method result is successful。");
            }
        });
        // 尝试性对3.6.13进行兼容
        findAndHookMethod("com.xiaomi.mirror.utils.SystemUtils", lpparam.classLoader, "isModelSupport", Context.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                // this will be called before the clock was updated by the original method
                param.setResult(true);
                XposedBridge.log("Set the method result is successful。");
            }
        });
    }
}
