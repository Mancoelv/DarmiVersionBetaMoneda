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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class RankingScreenPuntos extends BaseScreen{
    private Stage stage;
    private Skin skin;
    private TextButton volver, jugar,rankingTiempo;
    private Image fondo;
    private Label primero,segundo,tercero;

    //Lector de ficheros
    private BufferedReader bfr;
    private String linea;

    //Ranking
    private ArrayList<String> ranking;

    public RankingScreenPuntos(final MainGame game) {
        super(game);
        //instanciamos el fondo
        fondo=new Image(game.getManager().get("ranking.png", Texture.class));
        //definimos el escenario
        stage=new Stage(new FitViewport(640,360));
        //instanciamos las skin
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        //instanciamos el boton de volver al menu y volver a jugar
        volver = new TextButton("Volver al menu", skin);
        jugar = new TextButton("Jugar", skin);
        rankingTiempo=new TextButton("Tiempo",skin);
        //Instanciamos los textos de los 3 primeros puestos
        primero=new Label("Puesto 1: ",skin);
        segundo=new Label("Puesto 2: ",skin);
        tercero=new Label("Puesto 3: ",skin);



        //El boton volver nos llevara al menu principal
        volver.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.menuScreen);
            }
        });
        //El boton volver a jugar nos llevara a la pantalla de seleccion
        //de coche nuevo para volver a jugar
        jugar.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.modoJuegoScreen);
            }
        });
        rankingTiempo.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.rankingScreen);
            }
        });

        //Asignamos tamaño y posicion a nuestros elementos que cargaremos en la pantalla
        fondo.setSize(630,450);
        jugar.setSize(120, 40);
        jugar.setPosition(90, 50);
        volver.setSize(120, 40);
        volver.setPosition(420, 50);
        rankingTiempo.setPosition(500,300);
        primero.setPosition(230,220);
        segundo.setPosition(230,170);
        tercero.setPosition(230,120);


        //Inicializamos el array del ranking
        ranking = new ArrayList<>();

        //Añadimos todos estos recursos como actores del stage
        stage.addActor(fondo);
        stage.addActor(volver);
        stage.addActor(jugar);
        stage.addActor(primero);
        stage.addActor(segundo);
        stage.addActor(tercero);
        stage.addActor(rankingTiempo);
    }
    @Override
    public void show() {
        //Se ejecuta solo cuando se inicia la pantalla
        //Procesa todos los procesos del stage
        Gdx.input.setInputProcessor(stage);

//        try {
//            bfr = new BufferedReader(new FileReader("/data/data/com.darmi/files/ranking.txt"));
//            while ((linea = bfr.readLine()) != null) {
//                String partes[] = linea.split(" ");
//                minutos.add(Integer.parseInt(partes[0]));
//                segundos.add(Integer.parseInt(partes[2]));
//            }
//            bfr.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        ordenarArrays();
//        minutos.removeAll(minutos);
//        segundos.removeAll(segundos);
//        //Asignamos a cada label correspondiente los primeros puestos del ranking
//
//        if(ranking.size() == 0){
//            primero.setText("Puesto 1: ");
//            segundo.setText("Puesto 2: ");
//            tercero.setText("Puesto 3: ");
//        }else if(ranking.size() == 1){
//            primero.setText("Puesto 1: "+ranking.get(0));
//        }else if(ranking.size() == 2){
//            primero.setText("Puesto 1: "+ranking.get(0));
//            segundo.setText("Puesto 2: "+ranking.get(1));
//        }else{
//            primero.setText("Puesto 1: "+ranking.get(0));
//            segundo.setText("Puesto 2: "+ranking.get(1));
//            tercero.setText("Puesto 3: "+ranking.get(2));
//        }


    }

    private void ordenarArrays() {
//        int minAux;
//        int segAux;
//        //Ordenamos los arrays de igual manera, para que cada minuto quede con sus respectivos segundos
//        for(int i = 0; i < (minutos.size() - 1); i++){
//            for(int j = (i+1); j < minutos.size(); j++){
//                //Si el minuto que hay en la 1 posicion es más pequeño que el de la segunda, hacemos un cambio de posicion de este minuto y de sus respectivos
//                //segundos
//                if(minutos.get(i) < minutos.get(j)){
//                    minAux = minutos.get(i);
//                    minutos.set(i, minutos.get(j));
//                    minutos.set(j, minAux);
//
//                    segAux = segundos.get(i);
//                    segundos.set(i, segundos.get(j));
//                    segundos.set(j, segAux);
//
//                    //En el caso de que los minutos sean iguales, hacemos la comprobacion con los segundos y seguimos el mismo procedimiento que con los minutos
//                }else if (minutos.get(i) == minutos.get(j)){
//                    //Si los segundos de i son mas pequeños que los de j, hacemos el cambio de minutos y de segundos
//                    if(segundos.get(i) < segundos.get(j)) {
//                        minAux = minutos.get(i);
//                        minutos.set(i, minutos.get(j));
//                        minutos.set(j, minAux);
//
//                        segAux = segundos.get(i);
//                        segundos.set(i, segundos.get(j));
//                        segundos.set(j, segAux);
//                    }
//
//                }
//            }
//        }
//        for(int i = 0; i < minutos.size(); i++){
//            ranking.add(minutos.get(i) + " Min " + segundos.get(i) + " seg");
//        }
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
        //Actalizamos y pintamos el stage
        stage.act();
        stage.draw();
    }
}
