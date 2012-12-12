package comix.bgteams.app;

import java.util.HashMap;
import java.util.Map;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;

public class BgTeamsWallpaperSettings extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

	// default values just in case you have multiple color pickers
	private Map<String, Integer> COLOR_PREFERENCES;

	private void initializeMaps() {
		COLOR_PREFERENCES = new HashMap<String, Integer>() {
			{
				put("color_picker", 1827005926);
			}
		};
	}

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		getPreferenceManager().setSharedPreferencesName(BgTeamsWallpaperService.SHARED_PREFS_NAME);
		addPreferencesFromResource(R.xml.wallpaper_settings);
		getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

		// initialize the color picker key/default map
		// initializeMaps();
	}

	/**
	 * returns true if the preference is in the color picker preference,
	 * COLOR_PREFERENCES
	 * 
	 * @param key
	 * @return
	 */
	private boolean isColorPreference(String key) {

		if (COLOR_PREFERENCES.containsKey(key))
			return true;
		else
			return false;

	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
		super.onDestroy();
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

	}
}
