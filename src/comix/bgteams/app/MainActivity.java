/*
 *    Copyright (C) 2010 Stewart Gateley <birbeck@gmail.com>
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package comix.bgteams.app;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		TextView infoText = (TextView) findViewById(R.id.textView1);
		infoText.setText(Html.fromHtml(getString(R.string.how_to_enable)));

		final Button set_wallpaper = (Button) findViewById(R.id.set_wallpaper);
		set_wallpaper.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER);
				i.putExtra("android.live_wallpaper.package", getPackageName());
				i.putExtra("android.live_wallpaper.settings", BgTeamsWallpaperSettings.class);
				startActivity(i);
			}
		});

	}

}
