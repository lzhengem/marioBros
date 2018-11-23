package com.lena.mariobros.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.lena.mariobros.MarioBros;
import com.lena.mariobros.Screens.PlayScreen;

public class Sakura extends Sprite {
    public enum State{FALLING, JUMPING, STANDING, RUNNING};
    public State currentState;
    public State previousState;

    public World world;
    public Body b2body;
    private TextureRegion sakuraStand;

    private Animation<TextureRegion> sakuraRun;
    private Animation<TextureRegion> sakuraJump;
    private float stateTimer;
    private boolean runningRight;

    public Sakura(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("Sakura_move"));
        this.world = world;
        //set up sakura's current state of movements
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        //get the running regions
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 1; i< 4; i++){
            frames.add(new TextureRegion(getTexture(),i* 31,0,31,40));
        }

        sakuraRun = new Animation<TextureRegion>(0.1f,frames);
        frames.clear();

        //get jumping regions
        frames.add(new TextureRegion(getTexture(),0,93,31,40));
//        frames.add(new TextureRegion(getTexture(),290,93,31,50));
        sakuraJump = new Animation<TextureRegion>(0.1f,frames);

        defineSakura();
        sakuraStand = new TextureRegion(getTexture(),0,0,31,40);
        setBounds(0,0,20/ MarioBros.PPM, 25/MarioBros.PPM);
        setRegion(sakuraStand);

    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2 + 4/MarioBros.PPM);
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt){
        currentState = getState();
        TextureRegion region;
        switch(currentState){
            case JUMPING:
                region = sakuraJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = sakuraRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = sakuraStand;
                break;
        }
        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }


    public State getState(){
        if(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;
        else if(b2body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if (b2body.getLinearVelocity().x !=0)
            return State.RUNNING;
        else
            return State.STANDING;
    }

    public void defineSakura(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32/MarioBros.PPM, 32/MarioBros.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6/MarioBros.PPM);

        fdef.filter.categoryBits = MarioBros.SAKURA_BIT;
        fdef.filter.maskBits = MarioBros.DEFAULT_BIT | MarioBros.COIN_BIT | MarioBros.BRICK_BIT;


        fdef.shape = shape;
        b2body.createFixture(fdef);

        EdgeShape head = new EdgeShape(); //this creates a line between 2 points
        head.set(new Vector2(-2/MarioBros.PPM,6/MarioBros.PPM), new Vector2(2/MarioBros.PPM,6/MarioBros.PPM)); //2 behind mario, at 6 above to 2 in front of mario, 6 above
        fdef.shape = head;
        fdef.isSensor = true; //now it doesnt collide with anything in 2d world
        //to be identify this fixture as the head
        b2body.createFixture(fdef).setUserData("head");
    }

}
