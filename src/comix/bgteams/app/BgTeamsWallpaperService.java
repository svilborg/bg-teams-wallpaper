package comix.bgteams.app;

import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.service.wallpaper.WallpaperService.Engine;
import android.view.SurfaceHolder;

public class BgTeamsWallpaperService extends WallpaperService {

	@Override
	public Engine onCreateEngine() {
		return new BgTeamsEngine();
	}

	class BgTeamsEngine extends Engine {

		private Handler mHandler = new Handler();

		private Runnable mIteration = new Runnable() {
			public void run() {
				iteration();
				drawFrame();
			}
		};

		private boolean mVisible;

		@Override
		public void onDestroy() {
			super.onDestroy();
			// stop the animation
			mHandler.removeCallbacks(mIteration);
		}

		@Override
		public void onVisibilityChanged(boolean visible) {
			mVisible = visible;
			if (visible) {
				iteration();
				drawFrame();
			} else {
				// stop the animation
				mHandler.removeCallbacks(mIteration);
			}
		}

		@Override
		public void onSurfaceChanged(SurfaceHolder holder, int format,
				int width, int height) {
			iteration();
			drawFrame();
		}

		@Override
		public void onSurfaceDestroyed(SurfaceHolder holder) {
			super.onSurfaceDestroyed(holder);
			mVisible = false;
			// stop the animation
			mHandler.removeCallbacks(mIteration);
		}

		@Override
		public void onOffsetsChanged(float xOffset, float yOffset,
				float xOffsetStep, float yOffsetStep, int xPixelOffset,
				int yPixelOffset) {
			iteration();
			drawFrame();
		}

		protected void drawFrame() {
		}

		protected void iteration() {
			// Reschedule the next redraw in 40ms
			mHandler.removeCallbacks(mIteration);
			if (mVisible) {
				mHandler.postDelayed(mIteration, 1000 / 25);
			}
		}
	}
}
//
// // private Aquarium _aquarium;
//
// public BgTeamsEngine() {
// // this._aquarium = new Aquarium();
// // this._aquarium.initialize(getBaseContext(), getSurfaceHolder());
// }
//
// @Override
// public void onVisibilityChanged(boolean visible) {
// if (visible) {
// // this._aquarium.render();
// }
// }
//
// @Override
// public void onSurfaceChanged(SurfaceHolder holder, int format,
// int width, int height) {
// super.onSurfaceChanged(holder, format, width, height);
// }
//
// @Override
// public void onSurfaceCreated(SurfaceHolder holder) {
// super.onSurfaceCreated(holder);
// // this._aquarium.start();
// }
//
// @Override
// public void onSurfaceDestroyed(SurfaceHolder holder) {
// super.onSurfaceDestroyed(holder);
// // this._aquarium.stop();
// }
// }
