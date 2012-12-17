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
