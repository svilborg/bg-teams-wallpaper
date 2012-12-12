package comix.bgteams.app;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.modifier.LoopEntityModifier;
import org.anddev.andengine.entity.modifier.ParallelEntityModifier;
import org.anddev.andengine.entity.modifier.RotationModifier;
import org.anddev.andengine.entity.modifier.ScaleModifier;
import org.anddev.andengine.entity.modifier.SequenceEntityModifier;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.extension.ui.livewallpaper.BaseLiveWallpaperService;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.opengl.util.GLHelper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

public class BgTeamsWallpaperService extends BaseLiveWallpaperService implements SharedPreferences.OnSharedPreferenceChangeListener {
	// ===========================================================
	// Constants
	// ===========================================================

	public static final String SHARED_PREFS_NAME = "livewallpapertemplatesettings";

	// Camera Constants

	private static int CAMERA_WIDTH = 480;
	private static int CAMERA_HEIGHT = 720;

	private static final float DEMO_VELOCITY = 30.0f;

	private static int TYPE = 3;

	// ===========================================================
	// Fields
	// ===========================================================

	// Shared Preferences
	private SharedPreferences sharedPreferences;

	private String prefTeam = "Beroe.png";

	private Texture mTexture;

	private TiledTextureRegion mTiledTextureRegion;
	private TextureRegion mTextureRegion;

	private boolean settingsChanged;

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// @Override
	// public void onCreate() {
	// // TODO Auto-generated method stub
	// super.onCreate();
	// // get the system properties
	// sharedPreferences =
	// BgTeamsWallpaperService.this.getSharedPreferences(SHARED_PREFS_NAME, 0);
	// sharedPreferences.registerOnSharedPreferenceChangeListener(this);
	// onSharedPreferenceChanged(sharedPreferences, null);
	//
	// prefTeam = sharedPreferences.getString("teams_options", "Beroe.png");
	//
	// //pSharedPrefs.getString("teams_options", "Beroe.png");
	//
	// Log.d("onCreate", "ONCREATE :: " + prefTeam);
	// }

	@Override
	public org.anddev.andengine.engine.Engine onLoadEngine() {

		DisplayMetrics displayMetrics = getDisplayMertics();

		CAMERA_HEIGHT = displayMetrics.heightPixels;
		CAMERA_WIDTH = displayMetrics.widthPixels;

		Camera mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		mCamera.setZClippingPlanes(-100, 100);

		return new org.anddev.andengine.engine.Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera));
	}

	private DisplayMetrics getDisplayMertics() {
		// get screen size
		WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

		Display display = windowManager.getDefaultDisplay();
		DisplayMetrics displayMetrics = new DisplayMetrics();
		display.getMetrics(displayMetrics);

		return displayMetrics;
	}

	@Override
	public void onLoadComplete() {

	}

	@Override
	protected void onTap(final int pX, final int pY) {

	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences pSharedPrefs, String pKey) {
		prefTeam = pSharedPrefs.getString("teams_options", "Beroe.png");

		Log.d("WALLY", "CHANGED onSharedPreferenceChanged :: " + prefTeam);

		settingsChanged = true;
		if (TYPE == 2) {
			initResourcesMoving();
		} else {
			initResourcesStatic();
		}
	}

	private void initPreferances() {
		sharedPreferences = BgTeamsWallpaperService.this.getSharedPreferences(SHARED_PREFS_NAME, 0);
		sharedPreferences.registerOnSharedPreferenceChangeListener(this);
		onSharedPreferenceChanged(sharedPreferences, null);

		prefTeam = sharedPreferences.getString("teams_options", "Beroe.png");

		Log.d("WALLY", "INIT PREFERANCES :: " + prefTeam);
	}

	@Override
	public void onLoadResources() {

		Log.d("WALLY", "onLoadResources -------ON LOAD RESOURCES------");

		sharedPreferences = BgTeamsWallpaperService.this.getSharedPreferences(SHARED_PREFS_NAME, 0);
		sharedPreferences.registerOnSharedPreferenceChangeListener(this);
		onSharedPreferenceChanged(sharedPreferences, null);

		prefTeam = sharedPreferences.getString("teams_options", "Beroe.png");


	}

	public void initResourcesStatic() {
		this.mTexture = new Texture(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mTextureRegion = TextureRegionFactory.createFromAsset(this.mTexture, this, "logos256/" + prefTeam, 0, 0);

		this.mEngine.getTextureManager().loadTexture(this.mTexture);
	}

	public void initResourcesMoving() {
		this.mTexture = new Texture(256, 256, TextureOptions.DEFAULT);
		this.mTiledTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mTexture, this, "logos256/" + prefTeam, 0, 0, 1, 1);

		this.mEngine.getTextureManager().loadTexture(this.mTexture);
	}

	// @Override
	public Sprite loadSpriteRotation2D() {
		final Sprite sprite = loadSpriteStatic();

		final LoopEntityModifier entityModifier = new LoopEntityModifier(new ParallelEntityModifier(new RotationModifier(6, 0, 360)));

		sprite.registerEntityModifier(entityModifier);

		return sprite;
	}

	// @Override
	public Sprite loadSpritePulsate() {

		final Sprite sprite = loadSpriteStatic();

		final LoopEntityModifier entityModifier = new LoopEntityModifier(new ParallelEntityModifier(new SequenceEntityModifier(new ScaleModifier(3, 1, 1.5f), new ScaleModifier(3, 1.5f, 1))));

		sprite.registerEntityModifier(entityModifier);

		return sprite;
	}

	// @Override
	public Sprite loadSpriteRotation3D() {
		/*
		 * Calculate the coordinates for the sprite, so its centered on the
		 * camera.
		 */
		final int x = (CAMERA_WIDTH - this.mTextureRegion.getWidth()) / 2;
		final int y = (CAMERA_HEIGHT - this.mTextureRegion.getHeight()) / 2;

		/* Create the sprite and add it to the scene. */
		final Sprite sprite = new Sprite(x, y, this.mTextureRegion) {
			@Override
			protected void applyRotation(final GL10 pGL) {
				/* Disable culling so we can see the back side of this sprite. */
				GLHelper.disableCulling(pGL);

				final float rotation = this.mRotation;

				if (rotation != 0) {
					final float rotationCenterX = this.mRotationCenterX;
					final float rotationCenterY = this.mRotationCenterY;

					pGL.glTranslatef(rotationCenterX, rotationCenterY, 0);
					/*
					 * Note we are applying rotation around the y-axis and not
					 * the z-axis anymore!
					 */
					pGL.glRotatef(rotation, 0, 1, 0);
					pGL.glTranslatef(-rotationCenterX, -rotationCenterY, 0);
				}
			}

			@Override
			protected void drawVertices(final GL10 pGL, final Camera pCamera) {
				super.drawVertices(pGL, pCamera);

				/* Enable culling as 'normal' entities profit from culling. */
				GLHelper.enableCulling(pGL);
			}
		};

		sprite.registerEntityModifier(new LoopEntityModifier(new RotationModifier(16, 5, 355)));

		return sprite;
	}

	public Sprite loadSpriteStatic() {
		/*
		 * Calculate the coordinates for the sprite, so its centered on the
		 * camera.
		 */
		final int x = (CAMERA_WIDTH - this.mTextureRegion.getWidth()) / 2;
		final int y = (CAMERA_HEIGHT - this.mTextureRegion.getHeight()) / 2;

		return new Sprite(x, y, this.mTextureRegion);
	}

	public Ball loadSpriteMoving() {
		final int x = (CAMERA_WIDTH - this.mTiledTextureRegion.getWidth()) / 2;
		final int y = (CAMERA_HEIGHT - this.mTiledTextureRegion.getHeight()) / 2;

		final Ball ball = new Ball(x, y, this.mTiledTextureRegion);

		return ball;
	}

	@Override
	public Scene onLoadScene() {
		Log.d("WALLY", "onLoadResources ---- ON LAOD SCENE ---------");
		this.mEngine.registerUpdateHandler(new FPSLogger());

		final Scene scene = new Scene();
		scene.setBackground(new ColorBackground(0.09804f, 0.6274f, 0.8784f));

		if (TYPE == 2) {
			scene.attachChild(loadSpriteMoving());
		} else if (TYPE == 3) {
			scene.attachChild(loadSpriteRotation2D());
		} else if (TYPE == 4) {
			scene.attachChild(loadSpriteRotation3D());
		} else if (TYPE == 5) {
			scene.attachChild(loadSpritePulsate());
		} else {
			scene.attachChild(loadSpriteStatic());
		}

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
		
		Log.d("WALLY", "onLoadResources ---- ON PAUSE-GAME--------");
	}

	@Override
	public void onResumeGame() {
		// TODO Auto-generated method stub
		
		Log.d("WALLY", "onLoadResources ---- ON RESUME-GAME--------");
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d("WALLY", "onLoadResources ---- ON RESUME---------");
		
		if (settingsChanged) {

			if (TYPE == 2) {
				initResourcesMoving();
			} else {
				initResourcesStatic();
			}
			
			
			final Scene scene = new Scene();
			scene.setBackground(new ColorBackground(0.09804f, 0.6274f, 0.8784f));

			if (TYPE == 2) {
				scene.attachChild(loadSpriteMoving());
			} else if (TYPE == 3) {
				scene.attachChild(loadSpriteRotation2D());
			} else if (TYPE == 4) {
				scene.attachChild(loadSpriteRotation3D());
			} else if (TYPE == 5) {
				scene.attachChild(loadSpritePulsate());
			} else {
				scene.attachChild(loadSpriteStatic());
			}			
			
			settingsChanged = false;
		}
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}