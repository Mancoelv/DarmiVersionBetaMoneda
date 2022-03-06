package com.darmi.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.darmi.constants.Constants;
import com.darmi.entities.PlayerEntity;
import com.darmi.entities.VehiculosEntity;
import com.darmi.others.Chronometer;

import java.util.ArrayList;

public class GameScreenPuntos extends BaseScreen {
    private MainGame game;
    //Scene2D
    private Stage stage,stage2;
    //Box2D
    private World world;

    //Screen
    private Camera camara;
    private Viewport ventana;

    //graphics
    private SpriteBatch loteSprites;
    private Texture background;

    //timing
    private float backgroundOffset;

    //Jugador
    private PlayerEntity jugador;

    //Vehiculos
    private ArrayList<VehiculosEntity> vehiculos;
    private VehiculosEntity vehiculo;
    private int vehicleVelocity;
    private Texture moneda;

    //Cronometro aparicion de vehiculos
    private Chronometer vehiclesRespawn;
    private Chronometer levelUp;

    //Nivel de juego
    int level;
    //Música
    private Music musica;

    //Control de colisiones
    private boolean choque;

    //Control del tiempo
    private long time;
    private long lastTime;
    private long delta;

    //Dibujamos tiempo en la pantalla
    private Label tiempo;
    private Skin skin;

    //Añadimos sonido de choque
    private Sound coin;

    //Puntuacion
    private Label puntuacion;
    private int puntos;


    public GameScreenPuntos(final MainGame game) {
        super(game);
        //Asignamos el sonido de recoger moneda
        coin=game.getManager().get("moneda.ogg");
        //configuramos un label con el tiempo
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        tiempo=new Label(time+" Seg ",skin);
        tiempo.setSize(10,10);
        tiempo.setPosition(550,300);
        //Configramos la label para mostrar los puntos
        puntos=0;
        puntuacion=new Label(puntos+" P ",skin);
        puntuacion.setSize(10,10);
        puntuacion.setPosition(50,300);


        //añadimos la música
        musica=game.getManager().get("song.ogg");

        this.game=game;
        stage2 = new Stage(new FitViewport(640,360));
        stage = new Stage(new FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT));
        world = new World(new Vector2(0, 0), true);
        loteSprites=new SpriteBatch();
        camara = new OrthographicCamera();
        ventana=new StretchViewport(Constants.WORLD_WIDTH,Constants.WORLD_HEIGHT,camara);
        background=new Texture("carreteras.png");
        backgroundOffset=0;
        vehiculos = new ArrayList<>();
        vehicleVelocity = -30;
        vehiclesRespawn = new Chronometer();
        vehiclesRespawn.run(3000);
        levelUp = new Chronometer();
        levelUp.run(25000);
        level = 0;
        moneda = new Texture("moneda.png");
        choque = false;

        //Inicializamos las variables para controlar el tiempo
        delta = 0;
        time = 0;
        lastTime = System.currentTimeMillis();

//        stage.addActor(tiempo);
        world.setContactListener(new ContactListener() {
            //Controlamos si un objeto ha chocado
            private boolean chocado(Contact contact, Object userA, Object userB){
                return (contact.getFixtureA().getUserData().equals(userA)&&contact.getFixtureB().getUserData().equals(userB))||
                        (contact.getFixtureA().getUserData().equals(userB)&&contact.getFixtureB().getUserData().equals(userA));
            }

            @Override
            public void beginContact(Contact contact) {
                if (chocado(contact,"jugador","vehiculo")){
                    choque = true;
                    coin.play();
                }
            }

            @Override
            public void endContact(Contact contact) {
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });

    }

    @Override
    public void show() {
        //Se ejecuta solo cuando se inicia la pantalla
        Texture jugadorTexture=game.texture;
        jugador=new PlayerEntity(world,jugadorTexture, new Vector2(5,30));

        stage.addActor(jugador);

        //modificamos el volumen al 75%
//        musica.setVolume(0.75f);
        musica.play();
    }

    @Override
    public void hide() {
        //paramos la música
        musica.stop();

        //desacoplamos el jugador
        jugador.detach();
        jugador.remove();
    }

    @Override
    public void render(float delta) {
        //Controlamos el tiempo que ha pasado en segundos. Con el tiempo actual menos el tiempo en el que ha empezado, la diferencia es el tiempo que ha pasado
        this.delta += System.currentTimeMillis() - lastTime;
        //Guardamos la hora actual
        lastTime = System.currentTimeMillis();
        time=this.delta/1000;
        if(choque){
            choque = false;
            //Vibración tras chocar nuestro vehiculo
            try {
                Gdx.input.vibrate(600);
            } catch (Exception e) {
                System.out.println(e);
            }
            puntos=puntos+10;
            vehiculo.detach();
        }
        tiempo.setText(game.gameOverScreen.escribirTiempo(time));
        stage2.addActor(tiempo);
        puntuacion.setText(puntos+" Puntos");
        stage2.addActor(puntuacion);

        vehiclesRespawn.update();
        levelUp.update();
        if(!vehiclesRespawn.isRunning()){
            vehiculo=new VehiculosEntity(world,moneda, new Vector2((75 + (int)(Math.random() * 25)),(15 + (int)(Math.random() * 85))),vehicleVelocity);
            stage.addActor(vehiculo);
            vehiculos.add(vehiculo);
            if (level >= 2){
                for (int i = 0; i < (int)(Math.random() * 2); i++){
                    vehiculo=new VehiculosEntity(world,moneda, new Vector2((75 + (int)(Math.random() * 25)),(15 + (int)(Math.random() * 85))),vehicleVelocity);
                    stage.addActor(vehiculo);
                    vehiculos.add(vehiculo);
                }
            }
            if (!levelUp.isRunning()){
                level++;
                vehicleVelocity -= 10;
                levelUp.run(20000);
            }
            vehiclesRespawn.run(3000);
        }

        //Añadimos dentro del lote lo que queremos dibujar
        loteSprites.begin();
        //scrolling background
        backgroundOffset= (float) (backgroundOffset+0.50);
        //si el largo de nuestra imagen de fondo llega a 0 reseteamos el backgroundOffset
        if(backgroundOffset%Constants.WORLD_WIDTH==0){
            backgroundOffset=0;
        }

        loteSprites.draw(background,-backgroundOffset,0,Constants.WORLD_WIDTH,Constants.WORLD_HEIGHT);
        loteSprites.draw(background,-backgroundOffset+Constants.WORLD_WIDTH,0,Constants.WORLD_WIDTH,Constants.WORLD_HEIGHT);
        loteSprites.end();


        stage.act();
        //step sirve para actualizar las fuerzas, gravedad, etc
        world.step(delta,6,2);
        stage.draw();
        stage2.draw();
    }
    @Override
    public void resize(int width, int height) {
        ventana.update(width,height,true);
        loteSprites.setProjectionMatrix(camara.combined);
    }

    @Override
    public void dispose() {
        //eliminamos recursos para evitar que se queden procesos abiertos
        stage.dispose();
        stage2.dispose();
        world.dispose();
        skin.dispose();
        loteSprites.dispose();
        musica.dispose();
    }
}
