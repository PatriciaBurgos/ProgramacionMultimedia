/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cescristorey;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 *
 * @author patri
 */
public class FinalScreen implements Screen {

	final Columna game;

        int columnasPasadas;
        
	OrthographicCamera camera;
        
        private SpriteBatch batch;
        private Texture texture;
        private TextureRegion region;
        
        private Texture gameover;

	public FinalScreen(final Columna game, int dropGathered) {
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
                this.columnasPasadas = dropGathered;
                
                batch = new SpriteBatch();   
                texture = new Texture(Gdx.files.internal("background-day.png"));
                region = new TextureRegion(texture,0,0,800,480);
                
                gameover = new Texture(Gdx.files.internal("gameover.png"));
	}
        
        @Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0.1f, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                
                batch.begin();
                batch.draw(region,0,0);
                batch.end();

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
                game.batch.draw(gameover, 100, 350);
                game.font.draw(game.batch, "HAS PASADO " + columnasPasadas + " COLUMNAS", 100, 320);
		game.font.draw(game.batch, "TOCA LA PANTALLA PARA VOLVER A EMPEZAR", 100, 300);
		game.batch.end();

		if (Gdx.input.isTouched()) {
			game.setScreen(new GameScreen(game));
			dispose();
		}
	}

    @Override
    public void show() {
    }

    @Override
    public void resize(int i, int i1) {
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
        gameover.dispose();
    }
}

