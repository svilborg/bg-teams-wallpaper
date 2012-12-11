package comix.bgteams.app;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.modifier.LoopEntityModifier;
import org.anddev.andengine.entity.modifier.RotationModifier;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.extension.ui.livewallpaper.BaseLiveWallpaperService;
import org.anddev.andengine.opengl.texture.ITexture;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.BaseTexture.ITextureStateListener;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.opengl.texture.source.ITextureSource;
import org.anddev.andengine.opengl.util.GLHelper;
import org.anddev.andengine.util.Debug;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

public class BgTeamsWallpaperService extends BaseLiveWallpaperService implements SharedPreferences.OnSharedPreferenceChangeListener {
	// ===========================================================
	// Constants
	// ===========================================================

	public static final String SHARED_PREFS_NAME = "livewallpapertemplatesettings";

	// Camera Constants

	private static int CAMERA_WIDTH = 480;
	private static int CAMERA_HEIGHT = 720;

	private static final float DEMO_VELOCITY = 30.0f;

	// ===========================================================
	// Fields
	// ===========================================================

	// Shared Preferences
	private SharedPreferences mSharedPreferences;

	private Texture mTexture;

	private TiledTextureRegion mFaceTextureRegion;
	private TextureRegion mTextureRegion;

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public org.anddev.andengine.engine.Engine onLoadEngine() {
		// get screen size
		WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		DisplayMetrics displayMetrics = new DisplayMetrics();
		display.getMetrics(displayMetrics);
		int height = displayMetrics.heightPixels;
		int width = displayMetrics.widthPixels;

		CAMERA_WIDTH = width;
		CAMERA_HEIGHT = height;

		// Camera mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		// return new org.anddev.andengine.engine.Engine(new EngineOptions(true,
		// ScreenOrientation.PORTRAIT, new FillResolutionPolicy(), mCamera));

		Camera mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new org.anddev.andengine.engine.Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera));
	}

	@Override
	public void onLoadComplete() {

	}

	@Override
	protected void onTap(final int pX, final int pY) {

	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences pSharedPrefs, String pKey) {

	}

	@Override
	public void onLoadResources() {
		initResourcesStatic();
	}

	public void initResourcesStatic() {
		this.mTexture = new Texture(128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mTextureRegion = TextureRegionFactory.createFromAsset(this.mTexture, this, "gfx/bfs.png", 0, 0);

		this.mEngine.getTextureManager().loadTexture(this.mTexture);
	}

	public void initResourcesMovingBall() {
		this.mTexture = new Texture(256, 256, TextureOptions.DEFAULT);
		this.mFaceTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mTexture, this, "gfx/bfs.png", 0, 0, 1, 1);

		this.mEngine.getTextureManager().loadTexture(this.mTexture);
	}

	// @Override
	// public Scene onLoadScene() {
	// this.mEngine.registerUpdateHandler(new FPSLogger());
	//
	// final Scene scene = new Scene();
	// scene.setBackground(new ColorBackground(0.09804f, 0.6274f, 0.8784f));
	//
	// /*
	// * Calculate the coordinates for the face, so its centered on the
	// * camera.
	// */
	// final int centerX = (CAMERA_WIDTH - this.mTextureRegion.getWidth()) / 2;
	// final int centerY = (CAMERA_HEIGHT - this.mTextureRegion.getHeight()) /
	// 2;
	//
	// /* Create the face and add it to the scene. */
	// final Sprite face = new Sprite(centerX, centerY, this.mTextureRegion) {
	// // @Override
	// // protected void applyRotation(final GL10 pGL) {
	// // /* Disable culling so we can see the backside of this sprite. */
	// // GLHelper.disableCulling(pGL);
	// //
	// // final float rotation = this.mRotation;
	// //
	// // if(rotation != 0) {
	// // final float rotationCenterX = this.mRotationCenterX;
	// // final float rotationCenterY = this.mRotationCenterY;
	// //
	// // pGL.glTranslatef(rotationCenterX, rotationCenterY, 0);
	// // /* Note we are applying rotation around the y-axis and not the
	// // z-axis anymore! */
	// // pGL.glRotatef(rotation, 0, 1, 0);
	// // pGL.glTranslatef(-rotationCenterX, -rotationCenterY, 0);
	// // }
	// // }
	// //
	// // @Override
	// // protected void drawVertices(final GL10 pGL, final Camera pCamera)
	// // {
	// // super.drawVertices(pGL, pCamera);
	// //
	// // /* Enable culling as 'normal' entities profit from culling. */
	// // GLHelper.enableCulling(pGL);
	// // }
	// };
	// // face.registerEntityModifier(new LoopEntityModifier(new
	// // RotationModifier(6, 0, 360)));
	// scene.attachChild(face);
	//
	// return scene;
	// }

	public Sprite loadSpriteStatic() {
		/*
		 * Calculate the coordinates for the face, so its centered on the
		 * camera.
		 */
		final int centerX = (CAMERA_WIDTH - this.mTextureRegion.getWidth()) / 2;
		final int centerY = (CAMERA_HEIGHT - this.mTextureRegion.getHeight()) / 2;

		/* Create the face and add it to the scene. */
		return new Sprite(centerX, centerY, this.mTextureRegion);
		
	}

	public Ball loadSpriteMovingBall() {
		final int centerX = (CAMERA_WIDTH - this.mFaceTextureRegion.getWidth()) / 2;
		final int centerY = (CAMERA_HEIGHT - this.mFaceTextureRegion.getHeight()) / 2;
		
		final Ball ball = new Ball(centerX, centerY, this.mFaceTextureRegion);
		
		return ball;
	}

//	public Scene onLoadScene2() {
//		this.mEngine.registerUpdateHandler(new FPSLogger());
//
//		final Scene scene = new Scene();
//		scene.setBackground(new ColorBackground(0.09804f, 0.6274f, 0.8784f));
//
//		final int centerX = (CAMERA_WIDTH - this.mFaceTextureRegion.getWidth()) / 2;
//		final int centerY = (CAMERA_HEIGHT - this.mFaceTextureRegion.getHeight()) / 2;
//		
//		final Ball ball = new Ball(centerX, centerY, this.mFaceTextureRegion);
//
//		scene.attachChild(ball);
//
//		return scene;
//	}

	@Override
	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());

		final Scene scene = new Scene();
		scene.setBackground(new ColorBackground(0.09804f, 0.6274f, 0.8784f));


		scene.attachChild(loadSpriteStatic());

		return scene;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	private static class Ball extends AnimatedSprite {
		private final PhysicsHandler mPhysicsHandler;

		public Ball(final float pX, final float pY, final TiledTextureRegion pTextureRegion) {
			super(pX, pY, pTextureRegion);

			this.mPhysicsHandler = new PhysicsHandler(this);
			this.registerUpdateHandler(this.mPhysicsHandler);

			this.mPhysicsHandler.setVelocity(DEMO_VELOCITY, DEMO_VELOCITY);
		}

		@Override
		protected void onManagedUpdate(final float pSecondsElapsed) {
			if (this.mX < 0) {
				this.mPhysicsHandler.setVelocityX(DEMO_VELOCITY);
			} else if (this.mX + this.getWidth() > CAMERA_WIDTH) {
				this.mPhysicsHandler.setVelocityX(-(DEMO_VELOCITY));
			}

			if (this.mY < 0) {
				this.mPhysicsHandler.setVelocityY(DEMO_VELOCITY);
			} else if (this.mY + this.getHeight() > CAMERA_HEIGHT) {
				this.mPhysicsHandler.setVelocityY(-(DEMO_VELOCITY));
			}

			super.onManagedUpdate(pSecondsElapsed);
		}
	}

	@Override
	public void onPauseGame() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onResumeGame() {
		// TODO Auto-generated method stub
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}