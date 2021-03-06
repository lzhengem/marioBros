package com.lena.mariobros.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lena.mariobros.MarioBros;
import com.lena.mariobros.Scenes.Hud;
import com.lena.mariobros.Sprites.Mario;
import com.lena.mariobros.Sprites.Sakura;
import com.lena.mariobros.Tools.B2WorldCreator;
import com.lena.mariobros.Tools.WorldContactListener;

public class PlayScreen implements Screen {
    private MarioBros game;
    private TextureAtlas atlas;

    //basic playscreen variables
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;
//    private Mario player;
    private Sakura player;

    private Music music;
    //Tiled map variables
    private TmxMapLoader maploader; //loads in our map
    private TiledMap map; //actual map
    private OrthogonalTiledMapRenderer renderer; //renders map to screen

    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr; //graphical representation of bodies and fixtures inside of box2d world


    public PlayScreen(MarioBros game){
//        atlas = new TextureAtlas("Mario_and_Enemies.pack");
        atlas = new TextureAtlas("Sakura.pack");
        this.game = game;
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(MarioBros.V_WIDTH /MarioBros.PPM,MarioBros.V_HEIGHT/MarioBros.PPM,gamecam); //fits the width or height onto the screen. our screen is 800 x 480
        hud = new Hud(game.batch);

        maploader = new TmxMapLoader();
        map = maploader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1/MarioBros.PPM);
        //center around our viewport /2 because the default is to center at 0,0
        gamecam.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()/2,0);

        world = new World(new Vector2(0,-10),true); //means if object is not called, this world wont take note of it
        b2dr = new Box2DDebugRenderer();

        new B2WorldCreator(this);
        //create the mario player in the world
//        player = new Mario(this);
        player = new Sakura(this);

        world.setContactListener(new WorldContactListener());
        music = MarioBros.manager.get("audio/music/mario_music.ogg", Music.class);
        music.setLooping(true);
        music.play();
    }


    public TextureAtlas getAtlas(){
        return atlas;
    }
    @Override
    public void show() {

    }
    public void handleInput(float dt){
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) //if up was pressed
            player.b2body.applyLinearImpulse(new Vector2(0,4f),player.b2body.getWorldCenter(),true); //move the player vertically 4f, from its center, and this wakes up the object

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2)
            player.b2body.applyLinearImpulse(new Vector2(0.1f,0), player.b2body.getWorldCenter(), true);
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2)
            player.b2body.applyLinearImpulse(new Vector2(-0.1f,0), player.b2body.getWorldCenter(), true);
    }
    //does updating of gameworld
    public void update(float dt){
        //check if there is any input
        handleInput(dt);

        world.step(1/60f,6,2);

        //move mario whenever something gets changed
        player.update(dt);

        //update the hud
        hud.update(dt);

        gamecam.position.x = player.b2body.getPosition().x;
        //update camera everytime something happens
        gamecam.update();
        renderer.setView(gamecam); //only render what gamecam can see

    }

    @Override
    public void render(float delta) {
        update(delta);
        //clear the screen
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //render our game map
        renderer.render();

        //renderer our Box2DDebugLines
        b2dr.render(world,gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);//main camera
        game.batch.begin(); //open the box to put in the textures
        player.draw(game.batch);
        game.batch.end();

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

    public TiledMap getMap(){
        return map;
    }

    public World getWorld(){
        return world;
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();

    }
}
