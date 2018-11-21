package com.lena.mariobros.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.lena.mariobros.Sprites.InteractiveTileObject;

//contact listener tells what happens when 2 box2d objects touch each other
public class WorldContactListener implements ContactListener {
    //this is called when 2 fixtures begin to collide
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA(); //contact has 2 fixtures, A and B
        Fixture fixB = contact.getFixtureB();

        //if either of the fixtures is head, then it is mario hitting soemthing with his head
        if("head".equals(fixA.getUserData()) || "head".equals(fixB.getUserData())){
            //get the head and the object it is colliding with
            Fixture head = fixA.getUserData().equals("head") ? fixA : fixB;
            Fixture object = head == fixA ? fixB : fixA;

            //if mario is hitting an interactive tile object, call onHeadHit
            if(object.getUserData() instanceof InteractiveTileObject){
                ((InteractiveTileObject) object.getUserData()).onHeadHit();

            }
        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    //once colide, can change chraacteristcs after hitting
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    //what happens after hitting - like angles and stuff
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
