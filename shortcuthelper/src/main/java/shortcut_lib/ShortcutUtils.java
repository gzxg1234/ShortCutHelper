package shortcut_lib;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

/**
 * Created by xuyisheng on 15/10/30.
 * Version 1.0
 */
public final class ShortcutUtils {

    // Action 添加Shortcut
    public static final String ACTION_ADD_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";
    // Action 移除Shortcut
    public static final String ACTION_REMOVE_SHORTCUT = "com.android.launcher.action.UNINSTALL_SHORTCUT";

    private ShortcutUtils() throws InstantiationException {
        throw new InstantiationException("This class is not for instantiation");
    }

    /**
     * 添加快捷方式
     *
     * @param context      context
     * @param actionIntent 要启动的Intent
     * @param name         name
     * @param allowRepeat  是否允许重复
     * @param iconBitmap   快捷方式图标
     */
    private static void addShortcut(Context context, Intent actionIntent, String name,
                                   boolean allowRepeat, Bitmap iconBitmap, int iconRes) {
        Intent addShortcutIntent = new Intent(ACTION_ADD_SHORTCUT);
        // 是否允许重复创建
        addShortcutIntent.putExtra("duplicate", allowRepeat);
        // 快捷方式的标题
        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
        // 快捷方式的图标
        if (iconBitmap != null) {
            addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, iconBitmap);
        } else {
            addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(context, iconRes));
        }
        // 快捷方式的动作
        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, actionIntent);
        context.sendBroadcast(addShortcutIntent);
    }

    public static void addShortcut(Context context, Intent actionIntent, String name,
                                   boolean allowRepeat, @DrawableRes int iconRes) {
        addShortcut(context, actionIntent, name, allowRepeat, null, iconRes);
    }

    public static void addShortcut(Context context, Intent actionIntent, String name,
                                   boolean allowRepeat, @NonNull Bitmap icon) {
        addShortcut(context, actionIntent, name, allowRepeat, icon, 0);
    }


    /**
     * 移除快捷方式
     *
     * @param context      context
     * @param actionIntent 要启动的Intent
     * @param name         name
     */
    public static void removeShortcut(Context context, Intent actionIntent, String name) {
        Intent intent = new Intent(ACTION_REMOVE_SHORTCUT);
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
//        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.putExtra("duplicate", false);
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, actionIntent);
        context.sendBroadcast(intent);
    }

}
