package com.lena.mariobros.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.lena.mariobros.MarioBros;
import com.lena.mariobros.Sprites.Coin;

public class B2WorldCreator {
    public B2WorldCreator(World world, TiledMap map){
        //define body and fixtures
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape(); //for fixture
        FixtureDef fdef = new FixtureDef();
        Body body;

        //create ground body and fixtures around ground in tiled
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {//our first object is at index 2 from bottom
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth()/2)/ MarioBros.PPM, (rect.getY() + rect.getHeight()/2)/MarioBros.PPM);
            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2/MarioBros.PPM, rect.getHeight()/2/MarioBros.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //create pipe body and fixtures around pipes in tiled
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {//our first object is at index 2 from bottom
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth()/2)/MarioBros.PPM, (rect.getY() + rect.getHeight()/2)/MarioBros.PPM);
            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2/MarioBros.PPM, rect.getHeight()/2/MarioBros.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        //create brick body and fixtures around bricks in tiled
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {//our first object is at index 2 from bottom
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth()/2)/MarioBros.PPM, (rect.getY() + rect.getHeight()/2)/MarioBros.PPM);
            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2/MarioBros.PPM, rect.getHeight()/2/MarioBros.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //create coin body and fixtures around bricks in tiled
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {//our first object is at index 2 from bottom
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Coin(world, map, rect);
        }

    }
}
