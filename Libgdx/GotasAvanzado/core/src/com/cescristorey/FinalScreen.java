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

/**
 *
 * @author patri
 */
public class FinalScreen implements Screen {

	final Drop game;

        int dropGathered;
        
	OrthographicCamera camera;

	public FinalScreen(final Drop game, int dropGathered) {
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
                this.dropGathered = dropGathered;

	}
        
        @Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0.1f, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		game.font.draw(game.batch, "FIN DEL JUEGO", 100, 150);
                game.font.draw(game.batch, "HAS RECOGIDO " + dropGathered + " GOTAS", 100, 200);
		game.font.draw(game.batch, "TOCA LA PANTALLA PARA VOLVER A EMPEZAR", 100, 100);
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
    }
}

