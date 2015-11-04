package com.ru.tgra.game;


import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.ru.tgra.graphics.*;
import com.ru.tgra.graphics.shapes.*;
import com.ru.tgra.graphics.shapes.g3djmodel.G3DJModelLoader;
import com.ru.tgra.graphics.shapes.g3djmodel.MeshModel;
import com.ru.tgra.game.LabMeshTexGame;
import com.ru.tgra.graphics.ModelMatrix;
import com.ru.tgra.graphics.shapes.SphereGraphic;
import com.ru.tgra.graphics.shapes.Maze;
import com.ru.tgra.graphics.Point3D;
import com.ru.tgra.graphics.shapes.Projectile;
import com.ru.tgra.graphics.Vector3D;

public class LabMeshTexGame extends ApplicationAdapter implements InputProcessor {

	Shader shader;

	private float angle;
	private float leftAngle;
	private float upAngle;
	private float objectRotationAngle;
	private float objectPitchAngle;
	private float reloadAngle;
	
	public static int colorLoc;

	private Camera cam;
	private Camera topCam;
	private ArrayList<Projectile> projectiles;
	
	private float fov = 110.0f;

	private Texture tex;
	
	Random rand = new Random();
	
	private Maze maze;
	
	private boolean justPressed;
	private boolean oldJustPressed;
	
	private Sound sound;
	private Sound clayBreak;
	private Sound thrower;
	private Sound reload;
	Random r;
	
	MeshModel model, shotgun;
	private int ammo;
	long reloadTime;

	@Override
	public void create () {
		
		reloadAngle = 0;
		reloadTime = System.currentTimeMillis();
		ammo = 2;
		
		r = new Random();
		
		
		justPressed = false;
		oldJustPressed = false;
		projectiles = new ArrayList<Projectile>();
		leftAngle = 315;
		upAngle = 0;
		
		//Input processor

		Gdx.input.setInputProcessor(this);

		DisplayMode disp = Gdx.graphics.getDesktopDisplayMode();
		Gdx.graphics.setDisplayMode(disp.width, disp.height, true);

		shader = new Shader();

		tex = new Texture(Gdx.files.internal("textures/dice.png"));
		
		maze = new Maze(15, 15);

		
		model = G3DJModelLoader.loadG3DJFromFile("untitled2.g3dj");
		shotgun = G3DJModelLoader.loadG3DJFromFile("baerrel.g3dj");
		
		Gdx.gl.glClearColor(0.4f, 0.4f, 1.0f, 1.0f);

		BoxGraphic.create();
		SphereGraphic.create();

		ModelMatrix.main = new ModelMatrix();
		ModelMatrix.main.loadIdentityMatrix();
		shader.setModelMatrix(ModelMatrix.main.getMatrix());

		cam = new Camera();
		cam.look(new Point3D(1.5f, 1f, -0.5f), new Point3D(2.5f,1,-1.5f), new Vector3D(0,1,0));

		topCam = new Camera();
		//orthoCam.orthographicProjection(-5, 5, -5, 5, 3.0f, 100);
		//topCam.perspectiveProjection(30.0f, 1, 3, 100);
		
		Gdx.input.setCursorCatched(true);
		sound = Gdx.audio.newSound(Gdx.files.internal("ShotgunBoom.mp3"));
		clayBreak = Gdx.audio.newSound(Gdx.files.internal("ClayBreaking.mp3"));
		thrower = Gdx.audio.newSound(Gdx.files.internal("Thrower.mp3"));
		reload = Gdx.audio.newSound(Gdx.files.internal("reload2.mp3"));

		//TODO: try this way to create a texture image
		/*Pixmap pm = new Pixmap(128, 128, Format.RGBA8888);
		for(int i = 0; i < pm.getWidth(); i++)
		{
			for(int j = 0; j < pm.getWidth(); j++)
			{
				pm.drawPixel(i, j, rand.nextInt());
			}
		}
		tex = new Texture(pm);*/

	}

	private void input()
	{
	}
	
	private void update()
	{
		leftAngle %= 360;
		float deltaTime = Gdx.graphics.getDeltaTime();
	
		float soundAngle = leftAngle;
		soundAngle %= 360;
		float pan;
		if(soundAngle < 180 && soundAngle > 0){
			pan = soundAngle / 180;
			pan = 1-pan;
			
			//System.out.println(pan);
		}
		else{
			
		}
		
		
		angle += 180.0f * deltaTime;
		
		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			cam.slide(-3.0f * deltaTime, 0, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			cam.slide(3.0f * deltaTime, 0, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			cam.walkForward(3.0f * deltaTime);
		}
		

		if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			cam.walkForward(-3.0f * deltaTime);
		}
		long currTime = System.currentTimeMillis();
		if(currTime - reloadTime > 3000 && ammo == 0){
			ammo = 2;
		}
		
		
		if(Gdx.input.isKeyPressed(Input.Keys.R) && currTime - reloadTime > 3000 && reloadTime != 0) {
			reloadTime = System.currentTimeMillis();
			reload.play(1);
			ammo = 0;
		}
	
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && !justPressed && currTime - reloadTime > 3000) {
				ammo--;
				justPressed = true;
				Projectile projectile = new Projectile();
				projectile.setX(cam.eye.x);
				projectile.setY(cam.eye.y);
				projectile.setZ(cam.eye.z);
				projectile.setPitch(upAngle);
				projectile.setRotation(leftAngle);
				projectiles.add(projectile);
				sound.play(1);
				if(ammo == 0){
					reloadTime = currTime;
					reload.play(1);
					
				}
		}
		else if (!Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
			
			justPressed = false;
		}
		
		
				
		
		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			Gdx.graphics.setDisplayMode(500, 500, false);

			sound.dispose();
			clayBreak.dispose();
			thrower.dispose();
			Gdx.app.exit();
		}
		float changeX = -0.2f * Gdx.input.getDeltaX();
		float changeY = -0.2f * Gdx.input.getDeltaY();
		leftAngle += changeX;
		
		if(upAngle + changeY <= 70 && upAngle + changeY >= -85){
			upAngle += changeY;
		}
		
		cam.rotateY(changeX);
		cam.pitch(changeY);	
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
			Projectile projectile = new Projectile();
			projectile.setX(0);
			projectile.setY(0);
			projectile.setZ(0);
			projectile.setPitch(40+r.nextFloat()*10);
			projectile.setRotation(-40+r.nextFloat()*10);
			projectile.setPigeon(true);
			projectiles.add(projectile);
			Projectile projectile2 = new Projectile();
			projectile2.setX(10);
			projectile2.setY(0);
			projectile2.setZ(0);
			projectile2.setPitch(45 + r.nextFloat()*10);
			projectile2.setRotation(50-r.nextFloat()*10);
			projectile2.setPigeon(true);
			projectiles.add(projectile2);
			
		}
		//do all updates to the game
	}
	private void display()
	{
		//do all actual drawing and rendering here
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
		//Gdx.gl.glDisable(GL20.GL_DEPTH_TEST);
/*
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		//Gdx.gl.glBlendFunc(GL20.GL_ONE, GL20.GL_ONE);
		//Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
*/
		for(int viewNum = 0; viewNum < 2; viewNum++)
		{
			if(viewNum == 0)
			{
				Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				cam.perspectiveProjection(fov, (float)Gdx.graphics.getWidth() / (float)(2*Gdx.graphics.getHeight()), 0.1f, 100.0f);
				shader.setViewMatrix(cam.getViewMatrix());
				shader.setProjectionMatrix(cam.getProjectionMatrix());
				shader.setEyePosition(cam.eye.x, cam.eye.y, cam.eye.z, 1.0f);
			}
			else
			{
				/*Gdx.gl.glViewport(Gdx.graphics.getWidth() / 2, 0, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());
				topCam.look(new Point3D(cam.eye.x, 20.0f, cam.eye.z), cam.eye, new Vector3D(0,0,-1));
				//orthoCam.look(new Point3D(7.0f, 40.0f, -7.0f), new Point3D(7.0f, 0.0f, -7.0f), new Vector3D(0,0,-1));
				topCam.perspectiveProjection(30.0f, (float)Gdx.graphics.getWidth() / (float)(2*Gdx.graphics.getHeight()), 3, 100);
				shader.setViewMatrix(topCam.getViewMatrix());
				shader.setProjectionMatrix(topCam.getProjectionMatrix());
				shader.setEyePosition(topCam.eye.x, topCam.eye.y, topCam.eye.z, 1.0f);*/
			}


			ModelMatrix.main.loadIdentityMatrix();
			

			float s = (float)Math.sin((angle / 2.0) * Math.PI / 180.0);
			float c = (float)Math.cos((angle / 2.0) * Math.PI / 180.0);

			shader.setLightPosition(0.0f + c * 3.0f, 5.0f, 0.0f + s * 3.0f, 1.0f);
			//shader.setLightPosition(3.0f, 4.0f, 0.0f, 1.0f);
			//shader.setLightPosition(cam.eye.x, cam.eye.y, cam.eye.z, 1.0f);


			float s2 = Math.abs((float)Math.sin((angle / 1.312) * Math.PI / 180.0));
			float c2 = Math.abs((float)Math.cos((angle / 1.312) * Math.PI / 180.0));

			shader.setSpotDirection(s2, -0.3f, c2, 0.0f);
			//shader.setSpotDirection(-cam.n.x, -cam.n.y, -cam.n.z, 0.0f);
			shader.setSpotExponent(0.0f);
			shader.setConstantAttenuation(1.0f);
			shader.setLinearAttenuation(0.00f);
			shader.setQuadraticAttenuation(0.00f);

			//shader.setLightColor(s2, 0.4f, c2, 1.0f);
			shader.setLightColor(1.0f, 1.0f, 1.0f, 1.0f);
			
			shader.setGlobalAmbient(0.3f, 0.3f, 0.3f, 1);

			//shader.setMaterialDiffuse(s, 0.4f, c, 1.0f);
			shader.setMaterialDiffuse(1.0f, 1.0f, 1.0f, 1.0f);
			shader.setMaterialSpecular(1.0f, 1.0f, 1.0f, 1.0f);
			//shader.setMaterialSpecular(0.0f, 0.0f, 0.0f, 1.0f);
			shader.setMaterialEmission(0, 0, 0, 1);
			shader.setShininess(50.0f);
			maze.drawMaze();
			/* Reticle */
			ModelMatrix.main.pushMatrix();
			ModelMatrix.main.addTranslation(cam.eye.x, cam.eye.y, cam.eye.z);
			ModelMatrix.main.addRotationY(leftAngle);
			ModelMatrix.main.addRotationX(upAngle);
			ModelMatrix.main.addTranslation(0, 0, -0.3f);
			ModelMatrix.main.addScale(0.005f, 0.005f, 0.1f);
			shader.setModelMatrix(ModelMatrix.main.getMatrix());
			SphereGraphic.drawSolidSphere(shader, null, null);
			ModelMatrix.main.popMatrix();
			
			if(System.currentTimeMillis() - reloadTime > 3000){
				/* Barrel */
				ModelMatrix.main.pushMatrix();
				ModelMatrix.main.addTranslation(cam.eye.x, cam.eye.y, cam.eye.z);
				ModelMatrix.main.addRotationY(leftAngle);
				ModelMatrix.main.addRotationX(upAngle);
				ModelMatrix.main.addTranslation(0.05f, -0.22f, 0.15f);
				ModelMatrix.main.addRotationY(180);
				
				ModelMatrix.main.addScale(0.15f, 0.15f, 0.3f);
				shader.setModelMatrix(ModelMatrix.main.getMatrix());
				shotgun.draw(shader);
				ModelMatrix.main.addTranslation(0.0f, -0.2f, 0f);
				shader.setModelMatrix(ModelMatrix.main.getMatrix());
				shotgun.draw(shader);
				
				ModelMatrix.main.popMatrix();
				reloadAngle = 0;
			}
			else{
				/* Barrel */
				ModelMatrix.main.pushMatrix();
				ModelMatrix.main.addTranslation(cam.eye.x, cam.eye.y, cam.eye.z);
				ModelMatrix.main.addRotationY(leftAngle);
				ModelMatrix.main.addRotationX(upAngle);
				if(System.currentTimeMillis() - reloadTime < 1000){
					
				}
				else if(System.currentTimeMillis() - reloadTime < 2000){
					ModelMatrix.main.addRotationX(reloadAngle--);
				}else{
					ModelMatrix.main.addRotationX(reloadAngle++);
				}
				ModelMatrix.main.addTranslation(0.05f, -0.2f, 0.15f);
				ModelMatrix.main.addRotationY(180);
				
				ModelMatrix.main.addScale(0.15f, 0.15f, 0.3f);
				shader.setModelMatrix(ModelMatrix.main.getMatrix());
				shotgun.draw(shader);
				ModelMatrix.main.addTranslation(0.0f, -0.2f, 0f);
				shader.setModelMatrix(ModelMatrix.main.getMatrix());
				shotgun.draw(shader);
				
				ModelMatrix.main.popMatrix();
			}
			
			/* Ammo */
			ModelMatrix.main.pushMatrix();
			ModelMatrix.main.addTranslation(cam.eye.x, cam.eye.y, cam.eye.z);
			ModelMatrix.main.addRotationY(leftAngle);
			ModelMatrix.main.addRotationX(upAngle);
			ModelMatrix.main.addTranslation(-0.2f, -0.25f, -0.2f);
			//ModelMatrix.main.addTranslation(0.1f, 0.1f, -0.5f);
			
			for(int i = 0; i < ammo; i++){
				ModelMatrix.main.pushMatrix();
				ModelMatrix.main.addScale(0.005f, 0.25f, 0.005f);
				ModelMatrix.main.addRotationX(90);
				shader.setModelMatrix(ModelMatrix.main.getMatrix());
				model.draw(shader);
				ModelMatrix.main.popMatrix();
				ModelMatrix.main.addTranslation(0.02f, 0, 0);
			}
			ModelMatrix.main.popMatrix();
			Gdx.gl.glUniform4f(colorLoc, 1.0f, 1.0f, 1.0f, 1.0f);

			for(Projectile p : projectiles){
				p.drawProjectile(shader, model);
			}
			
			for(int i = 0; i < projectiles.size(); i++){
				for(int j = 0; j < projectiles.size(); j++){
					if(i!=j && !projectiles.get(i).getPigeon()){
						if(projectiles.get(i).distance(projectiles.get(j).getV()) < 0.4f){
							projectiles.get(i).setHit(true);
							projectiles.get(j).setHit(true);
							Vector3 eyeVec = new Vector3();
							eyeVec.x = cam.eye.x;
							eyeVec.y = cam.eye.y;
							eyeVec.z = cam.eye.z;
							double distToPlayer = projectiles.get(i).distance(eyeVec);
							float volume = (float) ((float) 4/distToPlayer);
							if(volume > 1){
								volume = 1;
							}
							if(volume < 0){
								volume = 0;
							}
							long play = clayBreak.play(1);
							clayBreak.setVolume(play, volume);
							break;
						}
					}
				}
			}
			

			for(Projectile p : projectiles){
				if(p.getAbsoluteY() <= 0 || p.getAbsoluteY() > 30 || p.getAbsoluteZ() < -30 || p.getAbsoluteZ() > 30 || p.getAbsoluteX() < -30 || p.getAbsoluteX() > 30){
					projectiles.remove(p);
					break;
				}
			}
			
			shader.setGlobalAmbient(0.2f, 0.2f, 0.0f, 1);
			shader.setMaterialDiffuse(0.3f, 0.3f, 0.3f, 1.0f);
			shader.setMaterialSpecular(1.0f, 1.0f, 1.0f, 1.0f);
			shader.setMaterialEmission(0, 0, 0, 1);
			shader.setShininess(10.0f);
	
			//drawPyramids();
		}
	}
	@Override
	public void render () {
		
		input();
		//put the code inside the update and display methods, depending on the nature of the code
		update();
		display();

	}

	

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}


}