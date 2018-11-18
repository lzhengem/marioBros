package com.lena.mariobros.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.lena.mariobros.MarioBros;
import com.lena.mariobros.Screens.PlayScreen;

public class Mario extends Sprite {
    public World world; //the world mario will live in
    public Body b2body;
    private TextureRegion marioStand;


    public Mario(World world, PlayScreen screen){
        //gets all the sprite action that the little mario can do
        super(screen.getAtlas().findRegion("little_mario"));
        this.world = world;
        defineMario();
        marioStand = new TextureRegion(getTexture(),0,0,16,16); //the standing mario in the atlas starts from 0,0 in the top left corner and is 16 wide, 16 height
        //set the bounds so screen knows how large to display
        setBounds(0,0,16/MarioBros.PPM, 16/MarioBros.PPM);
        setRegion(marioStand);
    }

    public void defineMario(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32/ MarioBros.PPM,32/MarioBros.PPM); //set the body at these coordinates
        bdef.type = BodyDef.BodyType.DynamicBody; //this body can move and is affected by velocity
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5/MarioBros.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);

    }
}
