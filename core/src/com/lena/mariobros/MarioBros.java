package com.lena.mariobros;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lena.mariobros.Screens.PlayScreen;

public class MarioBros extends Game {
	public static final int V_WIDTH = 400; //virtual width for game
	public static final int V_HEIGHT = 208; //virtual height for game

	public SpriteBatch batch; //only want to create one spritebatch because it is memory intensive
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
//		batch.dispose();
//		img.dispose();
	}
}
