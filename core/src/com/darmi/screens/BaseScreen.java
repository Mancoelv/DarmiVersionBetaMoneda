package com.darmi.screens;

import com.badlogic.gdx.Screen;

//Clase que implementa Screen y nos permitira extender de ella dejando
//el codigo del resto de clases mas limpio
public class BaseScreen implements Screen {
    private MainGame game;

    public BaseScreen(MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

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
