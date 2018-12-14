package com.lena.mariobros;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lena.mariobros.Screens.PlayScreen;

public class MarioBros extends Game {
	public static final int V_WIDTH = 400; //virtual width for game
	public static final int V_HEIGHT = 208; //virtual height for game
	public static final float PPM = 100;//pixels per meter. used because b2d uses meters use this to scale

	public static final short DEFAULT_BIT = 1;
	public static final short MARIO_BIT = 2;
	public static final short SAKURA_BIT = 2;
	public static final short BRICK_BIT = 4;
	public static final short COIN_BIT = 8;
	public static final short DESTROYED_BIT = 16;

	public SpriteBatch batch; //only want to create one spritebatch because it is memory intensive
	
	public static AssetManager manager;

	@Override
	public void create () {
		batch = new SpriteBatch();
		manager = new AssetManager();
		manager.load("audio/music/mario_music.ogg", Music.class);
		manager.load("audio/sounds/coin.wav",Sound.class);
		manager.load("audio/sounds/bump.wav",Sound.class);
		manager.load("audio/sounds/breakblock.wav",Sound.class);
		manager.finishLoading();

		setScreen(new PlayScreen(this));


	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		manager.dispose();
		batch.dispose();

	}
}
