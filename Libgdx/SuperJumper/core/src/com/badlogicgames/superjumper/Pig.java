/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.badlogicgames.superjumper;

/**
 *
 * @author patri
 */

public class Pig extends DynamicGameObject {
	public static final float PIG_WIDTH = 0.5f;
	public static final float PIG_HEIGHT = 0.8f;
	public static final int PIG_SCORE = -10;
        public static final float PIG_VELOCITY = 1.5f;
        
	float stateTime =0;

	public Pig (float x, float y) {
		super(x, y, PIG_WIDTH, PIG_HEIGHT);
		stateTime = 0;
                velocity.set(PIG_VELOCITY, 0);
	}

	public void update (float deltaTime) {
            position.add(velocity.y * deltaTime, velocity.x * deltaTime);
            bounds.x = position.x - PIG_WIDTH / 2;
            bounds.y = position.y - PIG_HEIGHT / 2;

            if (position.y < PIG_HEIGHT / 2) {
                    position.y = PIG_HEIGHT / 2;
                    velocity.y = PIG_VELOCITY;
            }
            if (position.y > World.WORLD_HEIGHT - PIG_HEIGHT / 2) {
                    position.y = World.WORLD_HEIGHT - PIG_HEIGHT / 2;
                    velocity.y = -PIG_VELOCITY;
            }
            stateTime += deltaTime;
	
	}
}
