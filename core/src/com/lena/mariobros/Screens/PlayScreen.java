package com.lena.mariobros.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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

public class PlayScreen implements Screen {
    private MarioBros game;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;

    //Tiled map variables
    private TmxMapLoader maploader; //loads in our map
    private TiledMap map; //actual map
    private OrthogonalTiledMapRenderer renderer; //renders map to screen

    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr; //graphical representation of bodies and fixtures inside of box2d world


    public PlayScreen(MarioBros game){
        this.game = game;
//        this.texture = new Texture("badlogic.jpg");
        gamecam = new OrthographicCamera();
//        gamePort = new StretchViewport(800,490,gamecam); //stretches the photo to the screen
//        gamePort = new ScreenViewport(gamecam); //picture does not change size, it will get cut off from the screen if screen gets smaller
        gamePort = new FitViewport(MarioBros.V_WIDTH,MarioBros.V_HEIGHT,gamecam); //fits the width or height onto the screen. our screen is 800 x 480
        hud = new Hud(game.batch);

        maploader = new TmxMapLoader();
        map = maploader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        //center around our viewport /2 because the default is to center at 0,0
        gamecam.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()/2,0);

        world = new World(new Vector2(0,0),true); //means if object is not called, this world wont take note of it
        b2dr = new Box2DDebugRenderer();

        //define body and fixtures
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape(); //for fixture
        FixtureDef fdef = new FixtureDef();
        Body body;

        //create ground body and fixtures around ground in tiled
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {//our first object is at index 2 from bottom
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() + rect.getWidth()/2, rect.getY() + rect.getHeight()/2);
            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2, rect.getHeight()/2);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //create pipe body and fixtures around pipes in tiled
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {//our first object is at index 2 from bottom
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() + rect.getWidth()/2, rect.getY() + rect.getHeight()/2);
            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2, rect.getHeight()/2);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        //create brick body and fixtures around bricks in tiled
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {//our first object is at index 2 from bottom
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() + rect.getWidth()/2, rect.getY() + rect.getHeight()/2);
            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2, rect.getHeight()/2);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //create coin body and fixtures around bricks in tiled
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {//our first object is at index 2 from bottom
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() + rect.getWidth()/2, rect.getY() + rect.getHeight()/2);
            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2, rect.getHeight()/2);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

    }
    @Override
    public void show() {

    }
    public void handleInput(float dt){
        if(Gdx.input.isTouched()) //if screen is being touched or finger touches phone
            gamecam.position.x += 100 * dt;
    }
    //does updating of gameworld
    public void update(float dt){
        //check if there is any input
        handleInput(dt);
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
