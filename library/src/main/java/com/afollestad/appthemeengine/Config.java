package com.afollestad.appthemeengine;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * @author Aidan Follestad (afollestad)
 */
public final class Config {

    private final static String CONFIG_PREFS_KEY = "[[afollestad_app-theme-engine]]";
    private final static String IS_CONFIGURED_KEY = "is_configured";

    private final static String KEY_PRIMARY_COLOR = "primary_color";
    private final static String KEY_PRIMARY_COLOR_DARK = "primary_color_dark";
    private final static String KEY_ACCENT_COLOR = "accent_color";

    private final static String KEY_TEXT_COLOR_PRIMARY = "text_color_primary";
    private final static String KEY_TEXT_COLOR_SECONDARY = "text_color_secondary";

    private final static String KEY_APPLY_PRIMARYDARK_STATUSBAR = "apply_primarydark_statusbar";
    private final static String KEY_APPLY_PRIMARY_SUPPORTAB = "apply_primary_supportab";
    private final static String KEY_APPLY_PRIMARY_NAVBAR = "apply_primary_navbar";
    private final static String KEY_AUTO_GENERATE_PRIMARYDARK = "auto_generate_primarydark";

    private Context mContext;
    private SharedPreferences.Editor mEditor;


    @SuppressLint("CommitPrefEdits")
    protected Config(@NonNull Context context) {
        mContext = context;
        mEditor = prefs(context).edit();
    }

    public Config primaryColor(@ColorInt int color) {
        mEditor.putInt(KEY_PRIMARY_COLOR, color);
        if (autoGeneratePrimaryDark(mContext))
            primaryColorDark(ATE.darkenColor(color));
        return this;
    }

    public boolean isConfigured() {
        return prefs(mContext).getBoolean(IS_CONFIGURED_KEY, false);
    }

    public Config primaryColorRes(@ColorRes int colorRes) {
        return primaryColor(ContextCompat.getColor(mContext, colorRes));
    }

    public Config primaryColorAttr(@AttrRes int colorAttr) {
        return primaryColor(Util.resolveColor(mContext, colorAttr));
    }

    public Config primaryColorDark(@ColorInt int color) {
        mEditor.putInt(KEY_PRIMARY_COLOR_DARK, color);
        return this;
    }

    public Config primaryColorDarkRes(@ColorRes int colorRes) {
        return primaryColorDark(ContextCompat.getColor(mContext, colorRes));
    }

    public Config primaryColorDarkAttr(@AttrRes int colorAttr) {
        return primaryColorDark(Util.resolveColor(mContext, colorAttr));
    }

    public Config accentColor(@ColorInt int color) {
        mEditor.putInt(KEY_ACCENT_COLOR, color);
        return this;
    }

    public Config accentColorRes(@ColorRes int colorRes) {
        return accentColor(ContextCompat.getColor(mContext, colorRes));
    }

    public Config accentColorAttr(@AttrRes int colorAttr) {
        return accentColor(Util.resolveColor(mContext, colorAttr));
    }

    public Config textColorPrimary(@ColorInt int color) {
        mEditor.putInt(KEY_TEXT_COLOR_PRIMARY, color);
        return this;
    }

    public Config textColorPrimaryRes(@ColorRes int colorRes) {
        return textColorPrimary(ContextCompat.getColor(mContext, colorRes));
    }

    public Config textColorPrimaryAttr(@AttrRes int colorAttr) {
        return textColorPrimary(Util.resolveColor(mContext, colorAttr));
    }

    public Config textColorSecondary(@ColorInt int color) {
        mEditor.putInt(KEY_TEXT_COLOR_SECONDARY, color);
        return this;
    }

    public Config textColorSecondaryRes(@ColorRes int colorRes) {
        return textColorSecondary(ContextCompat.getColor(mContext, colorRes));
    }

    public Config textColorSecondaryAttr(@AttrRes int colorAttr) {
        return textColorSecondary(Util.resolveColor(mContext, colorAttr));
    }

    public Config coloredStatusBar(boolean colored) {
        mEditor.putBoolean(KEY_APPLY_PRIMARYDARK_STATUSBAR, colored);
        return this;
    }

    public Config coloredActionBar(boolean applyToActionBar) {
        mEditor.putBoolean(KEY_APPLY_PRIMARY_SUPPORTAB, applyToActionBar);
        return this;
    }

    public Config coloredNavigationBar(boolean applyToNavBar) {
        mEditor.putBoolean(KEY_APPLY_PRIMARY_NAVBAR, applyToNavBar);
        return this;
    }

    public Config autoGeneratePrimaryDark(boolean autoGenerate) {
        mEditor.putBoolean(KEY_AUTO_GENERATE_PRIMARYDARK, autoGenerate);
        return this;
    }

    public void commit() {
        if (mContext instanceof Activity)
            ATE.didValuesChange = true;
        mEditor.putBoolean(IS_CONFIGURED_KEY, true).commit();
    }

    public void apply(@NonNull Activity activity) {
        commit();
        ATE.apply(activity);
    }

    public void apply(@NonNull android.support.v4.app.Fragment fragment) {
        commit();
        ATE.apply(fragment);
    }

    public void apply(@NonNull android.app.Fragment fragment) {
        commit();
        ATE.apply(fragment);
    }

    public void apply(@NonNull View view) {
        commit();
        ATE.apply(view.getContext(), view);
    }

    @NonNull
    private static SharedPreferences prefs(@NonNull Context context) {
        return context.getSharedPreferences(CONFIG_PREFS_KEY, Context.MODE_PRIVATE);
    }

    @ColorInt
    public static int primaryColor(@NonNull Context context) {
        return prefs(context).getInt(KEY_PRIMARY_COLOR, Util.resolveColor(context, R.attr.colorPrimary));
    }

    @ColorInt
    public static int primaryColorDark(@NonNull Context context) {
        return prefs(context).getInt(KEY_PRIMARY_COLOR_DARK, Util.resolveColor(context, R.attr.colorPrimaryDark));
    }

    @ColorInt
    public static int accentColor(@NonNull Context context) {
        return prefs(context).getInt(KEY_ACCENT_COLOR, Util.resolveColor(context, R.attr.colorAccent));
    }

    @ColorInt
    public static int textColorPrimary(@NonNull Context context) {
        return prefs(context).getInt(KEY_TEXT_COLOR_PRIMARY, Util.resolveColor(context, android.R.attr.textColorPrimary));
    }

    @ColorInt
    public static int textColorSecondary(@NonNull Context context) {
        return prefs(context).getInt(KEY_TEXT_COLOR_SECONDARY, Util.resolveColor(context, android.R.attr.textColorSecondary));
    }

    public static boolean coloredStatusBar(@NonNull Context context) {
        return prefs(context).getBoolean(KEY_APPLY_PRIMARYDARK_STATUSBAR, true);
    }

    public static boolean coloredActionBar(@NonNull Context context) {
        return prefs(context).getBoolean(KEY_APPLY_PRIMARY_SUPPORTAB, true);
    }

    public static boolean coloredNavigationBar(@NonNull Context context) {
        return prefs(context).getBoolean(KEY_APPLY_PRIMARY_NAVBAR, false);
    }

    public static boolean autoGeneratePrimaryDark(@NonNull Context context) {
        return prefs(context).getBoolean(KEY_AUTO_GENERATE_PRIMARYDARK, true);
    }
}