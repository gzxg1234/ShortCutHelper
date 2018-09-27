package com.sanron.shortcuthelper;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.support.annotation.NonNull;


import java.util.Collections;

import shortcut_lib.ShortcutSuperUtils;
import shortcut_lib.ShortcutUtils;

/**
 * Author:sanron
 * Time:2018/9/27
 * Description:
 */
public class ShortcutHelper {

    @TargetApi(Build.VERSION_CODES.O)
    private static boolean isShortCutExist26(Context context, @NonNull String name, @NonNull Intent actionIntent) {
        ShortcutManager sm = (ShortcutManager) context.getSystemService(Context.SHORTCUT_SERVICE);
        if (sm != null) {
            if (sm.isRequestPinShortcutSupported()) {
                final String id = generateId(name, actionIntent);
                for (ShortcutInfo shortcutInfo : sm.getPinnedShortcuts()) {
                    if (id.equals(shortcutInfo.getId())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断是否存在快捷方式，不是所有桌面都有效
     *
     * @param context
     * @param name
     * @param actionIntent
     * @return
     */
    public static boolean isShortCutExist(Context context, @NonNull String name, @NonNull Intent actionIntent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return isShortCutExist26(context, name, actionIntent);
        } else {
            return ShortcutSuperUtils.isShortCutExist(context, name, actionIntent);
        }
    }

    /**
     * 添加快捷方式
     *
     * @param context
     * @param actionIntent
     * @param name
     * @param duplicate
     * @param icon
     */
    public static void addShortCut(Context context, @NonNull String name, @NonNull Intent actionIntent,
                                   boolean duplicate, @NonNull Bitmap icon) {

        //android8.0使用ShortcutManager创建快捷方式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ShortcutManager sm = (ShortcutManager) context.getSystemService(Context.SHORTCUT_SERVICE);
            if (sm != null) {
                if (sm.isRequestPinShortcutSupported()) {
                    if (!duplicate && isShortCutExist26(context, name, actionIntent)) {
                        return;
                    }
                    ShortcutInfo shortcutInfo = new ShortcutInfo.Builder(context, generateId(name, actionIntent))
                            .setIcon(Icon.createWithBitmap(icon))
                            .setShortLabel(name)
                            .setIntent(actionIntent)
                            .build();
                    sm.requestPinShortcut(shortcutInfo, null);
                }
            }
        } else {
            if (!duplicate && ShortcutSuperUtils.isShortCutExist(context, name, actionIntent)) {
                return;
            }
            ShortcutUtils.addShortcut(context, actionIntent, name, duplicate, icon);
        }
    }

    /**
     * 移除快捷方式
     * Android8.0以上只会使快捷方式无效，不会移除
     *
     * @param context
     * @param name
     * @param actionIntent
     */
    public static void removeShortcut(Context context, @NonNull String name, @NonNull Intent actionIntent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ShortcutManager sm = (ShortcutManager) context.getSystemService(Context.SHORTCUT_SERVICE);
            if (sm != null) {
                if (sm.isRequestPinShortcutSupported()) {
                    final String id = generateId(name, actionIntent);
                    sm.disableShortcuts(Collections.singletonList(id));
                }
            }
        } else {
            ShortcutUtils.removeShortcut(context, actionIntent, name);
        }
    }

    private static String generateId(String name, Intent intent) {
        return name + "#" + intent.toUri(0);
    }
}
