package com.darmi.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class CreditsScreen extends BaseScreen {
    private Stage stage;
    private Skin skin;
    private Label credits;
    private TextButton back;
    private Image coche;

    public CreditsScreen(final MainGame game) {
        super(game);
        //Instanciamos una imagen decorativa
        coche=new Image(game.getManager().get("cocheDeco.png", Texture.class));
        //definimos el stage
        stage = new Stage(new FitViewport(640, 360));
        //instanciamos las skins
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        //Instanciamos un boton para volver
        back = new TextButton("Volver", skin);
        //Instanciamos una label que explicara el objetivo del juego y menciona los creadores
        credits = new Label("\nJuego 2D de coches.\nAguanta el maximo tiempo posible en la carretera\nsin chocarte con otros vehiculos.\n\nTrabajo realizado por:\n Inmaculada Moran Rastrollo\nMiguel Angel Vaquero Mateos", skin);

        //Definimos un boton para volver a la pantalla de menu
        back.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.menuScreen);
            }
        });

        //Asignamos tamaño y posicion a nuestros elementos que cargaremos en la pantalla
        coche.setSize(250,250);
        coche.setPosition(((stage.getWidth()-coche.getWidth())/2)*2-100,10);
        credits.setPosition(20, 340 - credits.getHeight());
        back.setSize(200, 80);
        back.setPosition(40, 50);

        //Añadimos todos estos recursos como actores del stage
        stage.addActor(coche);
        stage.addActor(back);
        stage.addActor(credits);
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
        //pintamos el fondo de negro
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //Actualizamos y pintamos el stage
        stage.act();
        stage.draw();
    }
}
