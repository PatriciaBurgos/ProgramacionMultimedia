/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cescristorey;

/**
 *
 * @author patri
 */
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameScreen implements Screen {
	final Columna game;

	Texture columnaImage,columnaImage2;
	Texture pajaroImage, pajaroUpImage, pajaroDownImage;
	Sound die,hit,point,swoosh,wing;
	//Music rainMusic;
	OrthographicCamera camera;
	Rectangle pajaro;
	Array<Rectangle> columnas, columnas2;
	long ultimaColumnaTime;
	int columnas_pasadas;
        
        final float MAX_VELOCITY = 10f;
        final float GRAVITY = -4.5f;
        
        private SpriteBatch batch;
        private Texture texture;
        private TextureRegion region;
        
        protected Connection conn;
        protected String user;
        protected String password;
        protected String sgbd; 
        protected String ip;
        protected String ubicacion;
        protected String url;
        protected Statement stmt;    
        protected ResultSet rs;
        public String dbname; 
        public String tabladb;

        int score;
        
	public GameScreen(final Columna game) {
		this.game = game;

		// load the images for the droplet and the bucket, 64x64 pixels each
		columnaImage = new Texture(Gdx.files.internal("pipe-green.png"));
                columnaImage2 = new Texture(Gdx.files.internal("pipe-green2.png"));
		pajaroImage = new Texture(Gdx.files.internal("redbird-midflap.png"));
                pajaroUpImage = new Texture (Gdx.files.internal("redbird-upflap.png"));
                pajaroDownImage = new Texture (Gdx.files.internal("redbird-downflap.png"));

		// load the drop sound effect and the rain background "music"
		die = Gdx.audio.newSound(Gdx.files.internal("die.wav"));
                hit = Gdx.audio.newSound(Gdx.files.internal("hit.wav"));
                point = Gdx.audio.newSound(Gdx.files.internal("point.wav"));
                swoosh = Gdx.audio.newSound(Gdx.files.internal("swoosh.wav"));
                wing = Gdx.audio.newSound(Gdx.files.internal("wing.wav"));
//		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
//		rainMusic.setLooping(true);

		// create the camera and the SpriteBatch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		// create a Rectangle to logically represent the bucket
		pajaro = new Rectangle();
		pajaro.x = 64 / 2; // center the bucket horizontally
		pajaro.y = 480 /2 ; // bottom left corner of the bucket is 20 pixels above
						// the bottom screen edge
		pajaro.width = 64;
		pajaro.height = 64;

		// create the raindrops array and spawn the first raindrop
		columnas = new Array<Rectangle>();
                columnas2 = new Array<Rectangle>();
		spawnRaindrop();
                
                this.columnas_pasadas = 0;
                
                batch = new SpriteBatch();   
                texture = new Texture(Gdx.files.internal("background-night.png"));
                region = new TextureRegion(texture,0,0,800,480);

	}

	private void spawnRaindrop() {
		Rectangle columna = new Rectangle();
		columna.x = 800 - 64;
		columna.y = MathUtils.random(-310, -100);
		columna.width = 52;
		columna.height = 320;
		columnas.add(columna);
                
                Rectangle columna2 = new Rectangle();
		columna2.x = 800 - 64;
		columna2.y = columna.y +550;
		columna2.width = 52;
		columna2.height = 320;
		columnas2.add(columna2);                
                
		ultimaColumnaTime = TimeUtils.nanoTime();
	}

	@Override
	public void render(float delta) {
		// clear the screen with a dark blue color. The
		// arguments to glClearColor are the red, green
		// blue and alpha component in the range [0,1]
		// of the color to be used to clear the screen.
                Gdx.gl.glClearColor(1, 1, 1, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

                batch.begin();
                //usamos el metodo del spritebatch draw para dibujar la region,(OJO! no la textura, podriamos dibujarla también
                //pero deberiamos cuadrarla en la pantalla con las coordenadas (0,-544).
                batch.draw(region,0,0);
                //Fin de dibujar.
                batch.end();
                
		// tell the camera to update its matrices.
		camera.update();

		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		game.batch.setProjectionMatrix(camera.combined);

            try {
                // begin a new batch and draw the bucket and
                // all drops
                
                this.leer_scores();
            } catch (IOException ex) {
                Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
                
		game.batch.begin();
                
		game.font.draw(game.batch, "Columnas: " + columnas_pasadas, 0, 480);
                game.font.draw(game.batch, "MaxScore: " + this.score, 120, 480);
                
                boolean upTouched = Gdx.input.isTouched() && Gdx.input.getY() < Gdx.graphics.getHeight() / 2;
                
                if(Gdx.input.isKeyPressed(Input.Keys.UP) || upTouched){ 
                    swoosh.play();
                   // wing.play();
                    game.batch.draw(pajaroUpImage, pajaro.x, pajaro.y, pajaro.width, pajaro.height);                
                }
                else{                    
                    game.batch.draw(pajaroDownImage, pajaro.x, pajaro.y, pajaro.width, pajaro.height);
                }         
                
                
		for (Rectangle columna : columnas) {
                    game.batch.draw(columnaImage, columna.x, columna.y);
		}
                for (Rectangle columna2 : columnas2) {
                    game.batch.draw(columnaImage2, columna2.x, columna2.y);
		}
                
		game.batch.end();

		// process user input
//		if (Gdx.input.isTouched()) {
//			Vector3 touchPos = new Vector3();
//			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
//			camera.unproject(touchPos);
//			bucket.y = touchPos.y - 64 / 2;
//		}
//		boolean upTouched = Gdx.input.isTouched() && Gdx.input.getY() < Gdx.graphics.getHeight() / 2;
                if (Gdx.input.isKeyPressed(Input.Keys.UP) || upTouched) {                    
                    pajaro.y = pajaro.y + MAX_VELOCITY * 1.5f;                    
                }
                pajaro.y = pajaro.y + GRAVITY;
        
                
		// make sure the bucket stays within the screen bounds
		if (pajaro.y <= 0){
                    pajaro.y=0;
                    die.play();
                    if(this.score <this.columnas_pasadas){
                        try {
                            this.guardar_scores(columnas_pasadas);
                        } catch (IOException ex) {
                            Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    game.setScreen(new FinalScreen(game, this.columnas_pasadas));
                    //dispose();
                }
		if (pajaro.y > 480 - 64)
			pajaro.y = 480 - 64;

		// check if we need to create a new raindrop
		if (TimeUtils.nanoTime() - ultimaColumnaTime > 1500000000)
			spawnRaindrop();

		// move the raindrops, remove any that are beneath the bottom edge of
		// the screen or that hit the bucket. In the later case we increase the 
		// value our drops counter and add a sound effect.
		Iterator<Rectangle> iter = columnas.iterator();
                Iterator<Rectangle> iter2 = columnas2.iterator();
                
		while (iter.hasNext()) {
			Rectangle columna = iter.next();
                        
                        columna.x -= 300 * Gdx.graphics.getDeltaTime();
                                                                      
			if (columna.x + 64 < 0){
				iter.remove();                                
                        }
			if (columna.overlaps(pajaro)) {
                                hit.play();
                                if(this.score <this.columnas_pasadas){
                                    try {
                                        this.guardar_scores(columnas_pasadas);
                                    } catch (IOException ex) {
                                        Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
				game.setScreen(new FinalScreen(game, this.columnas_pasadas));
			}
		}
                while (iter2.hasNext()) {
			Rectangle columnas2 = iter2.next();
                        columnas2.x -= 300 * Gdx.graphics.getDeltaTime();
                                                                        
			if (columnas2.x + 64 < 0){
				iter2.remove();
                                point.play();
                                columnas_pasadas++;                                
                        }
			if (columnas2.overlaps(pajaro)) {
                                hit.play();
                                if(this.score <this.columnas_pasadas){
                                    try {
                                        this.guardar_scores(columnas_pasadas);
                                    } catch (IOException ex) {
                                        Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
				game.setScreen(new FinalScreen(game, this.columnas_pasadas));
			}
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		// start the playback of the background music
		// when the screen is shown
		//rainMusic.play();
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		columnaImage.dispose();
                columnaImage2.dispose();
		pajaroImage.dispose();
                pajaroUpImage.dispose();
                pajaroDownImage.dispose();
		die.dispose();
                hit.dispose();
                point.dispose();
                swoosh.dispose();
                wing.dispose();
		//rainMusic.dispose();
                batch.dispose();
                texture.dispose();
	}
        
        public void guardar_scores (int columnas_pasadas) throws FileNotFoundException, IOException{        
        File fichero = new File("Scores.dat");
        FileOutputStream fileout = new FileOutputStream(fichero);  
        ObjectOutputStream dataOS = new ObjectOutputStream(fileout);  

        dataOS.writeObject(columnas_pasadas);
        
        dataOS.close();   
        }
        
        public void leer_scores() throws FileNotFoundException, IOException, ClassNotFoundException{       
        File fichero = new File("Scores.dat");
        ObjectInputStream dataIS = new ObjectInputStream(new FileInputStream(fichero));
        
        try {
            while (true) { 
                this.score = (int) dataIS.readObject();
            }           
        } catch (EOFException eo) {
                System.out.println("FIN DE LECTURA.");
        } catch (StreamCorruptedException x) {
        }
        dataIS.close(); 
    }

}