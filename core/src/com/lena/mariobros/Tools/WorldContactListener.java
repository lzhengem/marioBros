package com.lena.mariobros.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

//contact listener tells what happens when 2 box2d objects touch each other
public class WorldContactListener implements ContactListener {
    //this is called when 2 fixtures begin to collide
    @Override
    public void beginContact(Contact contact) {
        Gdx.app.log("Begin contact", "");

    }

    @Override
    public void endContact(Contact contact) {
        Gdx.app.log("End contact", "");

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
