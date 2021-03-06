package comix.bgteams.app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class BgTeamsWallpaperSettings extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		getPreferenceManager().setSharedPreferencesName(BgTeamsWallpaperService.SHARED_PREFS_NAME);
		addPreferencesFromResource(R.xml.wallpaper_settings);
		getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onDestroy() {
		getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
		super.onDestroy();
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

	}
}
