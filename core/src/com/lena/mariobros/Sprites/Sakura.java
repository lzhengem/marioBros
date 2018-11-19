package com.lena.mariobros.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.lena.mariobros.MarioBros;
import com.lena.mariobros.Screens.PlayScreen;

public class Sakura extends Sprite {
    public World world;
    public Body b2body;
    private TextureRegion sakuraStand;

    public Sakura(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("Sakura_move"));
        this.world = world;
        defineSakura();
        sakuraStand = new TextureRegion(getTexture(),0,0,30,30);
        setBounds(0,0,20/ MarioBros.PPM, 16/MarioBros.PPM);
        setRegion(sakuraStand);

    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
    }

    public void defineSakura(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32/MarioBros.PPM, 32/MarioBros.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6/MarioBros.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

}
