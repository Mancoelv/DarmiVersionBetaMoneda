package com.darmi.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MenuScreen extends BaseScreen{
    private Stage stage;
    private Skin skin;
    private Image logo, fondo;
    private TextButton play,creditos,ranking,exit;

    public MenuScreen(final MainGame game) {
        super(game);
        //definimos el stage
        stage=new Stage(new FitViewport(640,360));
        //instanciamos las skins
        skin= new Skin(Gdx.files.internal("skin/uiskin.json"));
        //Instanciamos la imagen del logo y el fondo
        logo=new Image(game.getManager().get("logo.png", Texture.class));
        fondo=new Image(game.getManager().get("fondo.png",Texture.class));
        //Instanciamos los botones del menu
        play=new TextButton("Jugar", skin);
        creditos=new TextButton("Creditos", skin);
        ranking=new TextButton("Ranking", skin);
        exit=new TextButton("Salir", skin);

        //El boton play nos llevara la pantalla de seleccion de jugador
        play.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                game.setScreen(game.modoJuegoScreen);
            }
        });
        //el boton de creditos nos llevara a la pantalla de creditos
        creditos.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.creditsScreen);
            }
        });
        //el boton del ranking nos llevara a la pantalla de ranking
        ranking.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.rankingScreen);
            }
        });
        //el boton exit nos ayudara a salir del juego
        exit.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        //Asignamos tamaño y posicion a nuestros elementos que cargaremos en la pantalla
        fondo.setSize(800,600);
        logo.setSize(300,300);
        logo.setPosition(((stage.getWidth()-logo.getWidth())/2)/2-100,(stage.getHeight()-logo.getHeight())/2);
        play.setSize(200,50);
        play.setPosition(((stage.getWidth()-play.getWidth())/2)*2-100,(stage.getHeight()/2)+100);
        ranking.setSize(200,50);
        ranking.setPosition(((stage.getWidth()-play.getWidth())/2)*2-100,(stage.getHeight()/2)+30);
        creditos.setSize(200,50);
        creditos.setPosition(((stage.getWidth()-play.getWidth())/2)*2-100,stage.getHeight()/2-45);
        exit.setSize(200,50);
        exit.setPosition(((stage.getWidth()-play.getWidth())/2)*2-100,(stage.getHeight()/2)-120);

        //Añadimos todos estos recursos como actores del stage
        stage.addActor(fondo);
        stage.addActor(logo);
        stage.addActor(play);
        stage.addActor(creditos);
        stage.addActor(ranking);
        stage.addActor(exit);
    }
    @Override
    public void show() {
        //Se ejecuta solo cuando se inicia la pantalla
        //Procesa todos los procesos del stage
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        //Cuando cerramos la pantalla ponemos a null este InputProcessor
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        //eliminamos el stage y las skin
        stage.dispose();
        skin.dispose();
    }

    @Override
    public void render(float delta) {
        //gl es lo que esta por debajo de la tarjeta grafica y le decimos que se vacie todos los
        // colores que tiene en pantalla
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //Si preferimos limpiar la pantalla dejando otro color de fondo usamos la siguiente linea
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        stage.act();
        stage.draw();
    }
}

