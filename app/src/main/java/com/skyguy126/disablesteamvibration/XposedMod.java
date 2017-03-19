package com.skyguy126.disablesteamvibration;

import android.os.Build;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class XposedMod implements IXposedHookLoadPackage {

    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {

        if (!lpparam.packageName.equals("com.valvesoftware.android.steam.community"))
            return;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            XposedBridge.log("[DSV] This module only works with Android KitKat or later.");
            return;
        }

        try {
            XposedHelpers.findAndHookMethod("com.valvesoftware.android.steam.community.NotificationSender",
                    lpparam.classLoader, "vibrate", new XC_MethodReplacement() {
                        @Override
                        protected Object replaceHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                            return null;
                        }
                    });

        } catch (Throwable t) {
            XposedBridge.log("[DSV] Error: " + t.getMessage());
            return;
        }

        XposedBridge.log("[DSV] Initialized");
    }
}
