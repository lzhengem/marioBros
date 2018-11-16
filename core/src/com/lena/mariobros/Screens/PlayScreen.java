package com.lena.mariobros.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lena.mariobros.MarioBros;
import com.lena.mariobros.Scenes.Hud;

public class PlayScreen implements Screen {
    private MarioBros game;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;

    public PlayScreen(MarioBros game){
        this.game = game;
//        this.texture = new Texture("badlogic.jpg");
        gamecam = new OrthographicCamera();
//        gamePort = new StretchViewport(800,490,gamecam); //stretches the photo to the screen
//        gamePort = new ScreenViewport(gamecam); //picture does not change size, it will get cut off from the screen if screen gets smaller
        gamePort = new FitViewport(MarioBros.V_WIDTH,MarioBros.V_HEIGHT,gamecam); //fits the width or height onto the screen. our screen is 800 x 480
        hud = new Hud(game.batch);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //clear the screen
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //set the camera to show the hud
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        //draw the hud
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        //when screen size changes, then resize the gameport
        gamePort.update(width,height);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
