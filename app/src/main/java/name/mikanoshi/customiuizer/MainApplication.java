package name.mikanoshi.customiuizer;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import org.acra.ACRA;
import org.acra.collector.ApplicationStartupCollector;
import org.acra.collector.ConfigurationCollector;
import org.acra.collector.CustomDataCollector;
import org.acra.collector.DeviceFeaturesCollector;
import org.acra.collector.DisplayManagerCollector;
import org.acra.collector.LogCatCollector;
import org.acra.collector.MemoryInfoCollector;
import org.acra.collector.PackageManagerCollector;
import org.acra.collector.ReflectionCollector;
import org.acra.collector.SettingsCollector;
import org.acra.collector.SharedPreferencesCollector;
import org.acra.collector.SimpleValuesCollector;
import org.acra.collector.StacktraceCollector;
import org.acra.collector.ThreadCollector;
import org.acra.collector.TimeCollector;
import org.acra.config.CoreConfigurationBuilder;
import org.acra.data.StringFormat;
import org.acra.plugins.SimplePluginLoader;

import java.util.Locale;

import miui.core.SdkManager;
import name.mikanoshi.customiuizer.crashreport.DialogInteraction;
import name.mikanoshi.customiuizer.utils.Helpers;

import static org.acra.ReportField.*;

public class MainApplication extends Application {

	public MainApplication() {
		SdkManager.initialize(this, null);
	}

	public void onCreate() {
		super.onCreate();
		SdkManager.start(null);
	}

	@Override
	protected void attachBaseContext(Context base) {
		try {
			Context pContext = Helpers.getProtectedContext(base);
			Helpers.prefs = pContext.getSharedPreferences(Helpers.prefsName, Context.MODE_PRIVATE);
			if (Helpers.prefs.getBoolean("pref_key_miuizer_forcelocale", false)) Locale.setDefault(Locale.ENGLISH);
			super.attachBaseContext(pContext);
		} catch (Throwable t) {
			super.attachBaseContext(base);
			Log.e("prefs", "Failed to use protected storage!");
		}

		ACRA.DEV_LOGGING = false;
		CoreConfigurationBuilder builder = new CoreConfigurationBuilder(this).setPluginLoader(new SimplePluginLoader(
			ConfigurationCollector.class,
			ApplicationStartupCollector.class,
			ConfigurationCollector.class,
			CustomDataCollector.class,
			DeviceFeaturesCollector.class,
			DisplayManagerCollector.class,
			LogCatCollector.class,
			MemoryInfoCollector.class,
			PackageManagerCollector.class,
			ReflectionCollector.class,
			SettingsCollector.class,
			SharedPreferencesCollector.class,
			SimpleValuesCollector.class,
			StacktraceCollector.class,
			ThreadCollector.class,
			TimeCollector.class,
			DialogInteraction.class
		));
		builder.setBuildConfigClass(BuildConfig.class).setReportFormat(StringFormat.JSON).setLogcatArguments("-t", "500", "-v", "time").setSharedPreferencesName(Helpers.prefsName);
		builder.setReportContent(REPORT_ID, APP_VERSION_CODE, APP_VERSION_NAME, PACKAGE_NAME, FILE_PATH, PHONE_MODEL, BRAND, PRODUCT, ANDROID_VERSION,
			BUILD, TOTAL_MEM_SIZE, AVAILABLE_MEM_SIZE, CUSTOM_DATA, STACK_TRACE, INITIAL_CONFIGURATION, CRASH_CONFIGURATION, DISPLAY, USER_COMMENT, USER_EMAIL,
			USER_APP_START_DATE, USER_CRASH_DATE, DUMPSYS_MEMINFO, LOGCAT, INSTALLATION_ID, DEVICE_FEATURES, ENVIRONMENT, SHARED_PREFERENCES,
			SETTINGS_SYSTEM, SETTINGS_SECURE, SETTINGS_GLOBAL);
		ACRA.init(this, builder);
	}

}
