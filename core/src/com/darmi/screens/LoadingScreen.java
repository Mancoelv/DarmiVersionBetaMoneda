package com.darmi.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;


public class LoadingScreen extends BaseScreen{
    private Stage stage;
    private Skin skin;
    private Label loading;
    private MainGame game;

    public LoadingScreen(MainGame game) {
        super(game);
        //asignamos a nuestra variable game el valor game del main
        this.game=game;
        //definimos el stage
        stage=new Stage(new FitViewport(640,360));
        //instanciamos las skins
        skin=new Skin(Gdx.files.internal("skin/uiskin.json"));
        //Instanciamos una label que colocaremos en nuestra pantalla
        loading=new Label("Cargando...",skin);
        //le damos una posicion a la label
        loading.setPosition(320-loading.getWidth()/2,180-loading.getHeight()/2);
        //Añadimos la label al stage como actor
        stage.addActor(loading);
    }

    @Override
    public void render(float delta) {
        //Pintamos la pantalla de negro
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Si hemos finalizado de cargar los recursos terminamos de cargar las
        //pantallas definidas en un metodo del main
        if(game.getManager().update()){
            game.finishLoading();
        }else{
            //si no hemos terminado de cargar los recursos del manager mostraremos
            //el progreso en la label definida anteriormente mas un porcentaje de carga
            int progress=(int)(game.getManager().getProgress()*100);
            loading.setText("Cargando..."+progress+"%");

        }
        //Actalizamos y pintamos el stage
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        //eliminamos el stage y las skin
        stage.dispose();
        skin.dispose();
    }
}
