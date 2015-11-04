package com.ru.tgra.graphics.shapes;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.ru.tgra.game.LabMeshTexGame;
import com.ru.tgra.graphics.ModelMatrix;
import com.ru.tgra.graphics.Shader;
import com.ru.tgra.graphics.shapes.g3djmodel.MeshModel;

public class Projectile {
	private float pitch;
	private float rotation;
	private float x, y, z, move;
	private boolean pigeon;
	private Vector3 v;
	private boolean hit;
	private Random r;
	
	public void setHit(boolean hit){
		this.hit = hit;
	}
	
	public Projectile(){
		r = new Random();
		v = new Vector3();
		hit = false;
		pigeon = false;
	}
	public void setPigeon(boolean isIt){
		pigeon = isIt;
	}
	
	public float getPitch() {
		return pitch;
	}
	public void setPitch(float pitch) {
		this.pitch = pitch;
	}
	public float getRotation() {
		return rotation;
	}
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getZ() {
		return z;
	}
	public void setZ(float z) {
		this.z = z;
	}
	public float getAbsoluteY(){
		ModelMatrix.main.pushMatrix();
		ModelMatrix.main.addTranslation(x, y, z);
		ModelMatrix.main.addRotationY(rotation);
		ModelMatrix.main.addRotationX(pitch);
		ModelMatrix.main.addTranslation(0, 0, move);
		float X = ModelMatrix.main.matrix.get(12);
		float Y = ModelMatrix.main.matrix.get(13);
		float Z = ModelMatrix.main.matrix.get(14);
		
		ModelMatrix.main.popMatrix();
		
		return Y;
	}
	
	public float getAbsoluteX(){
		ModelMatrix.main.pushMatrix();
		ModelMatrix.main.addTranslation(x, y, z);
		ModelMatrix.main.addRotationY(rotation);
		ModelMatrix.main.addRotationX(pitch);
		ModelMatrix.main.addTranslation(0, 0, move);
		float X = ModelMatrix.main.matrix.get(12);
		float Y = ModelMatrix.main.matrix.get(13);
		float Z = ModelMatrix.main.matrix.get(14);
		ModelMatrix.main.popMatrix();
		
		return X;
	}
	
	public boolean getPigeon(){
		return pigeon;
	}
	
	public float getAbsoluteZ(){
		ModelMatrix.main.pushMatrix();
		ModelMatrix.main.addTranslation(x, y, z);
		ModelMatrix.main.addRotationY(rotation);
		ModelMatrix.main.addRotationX(pitch);
		ModelMatrix.main.addTranslation(0, 0, move);
		float X = ModelMatrix.main.matrix.get(12);
		float Y = ModelMatrix.main.matrix.get(13);
		float Z = ModelMatrix.main.matrix.get(14);
		ModelMatrix.main.popMatrix();
		
		return Z;
	}
	
	public float getMove(){
		return move;
	}
	
	public Vector3 getV(){
		return v;
	}
	
	public double distance(Vector3 v2){
		return Math.sqrt(v.dst2(v2));
	}
	
	public void drawProjectile(Shader shader, MeshModel model){
		float dt = Gdx.graphics.getDeltaTime();
		ModelMatrix.main.pushMatrix();
			ModelMatrix.main.addTranslation(x, y, z);
			ModelMatrix.main.addRotationY(rotation);
			ModelMatrix.main.addRotationX(pitch);
			ModelMatrix.main.addTranslation(0, 0, move);
			float X = ModelMatrix.main.matrix.get(12);
			float Y = ModelMatrix.main.matrix.get(13);
			float Z = ModelMatrix.main.matrix.get(14);
			
			v.x = X;
			v.y = Y;
			v.z = Z;
				
			ModelMatrix.main.addScale(0.1f, 0.1f, 0.1f);
			
			move -= dt * 4;
			
			if(pigeon && !hit){
				ModelMatrix.main.addScale(1f, 3f, 1f);
				ModelMatrix.main.addRotationX(90);
				Gdx.gl.glUniform4f(LabMeshTexGame.colorLoc, 1.0f, 0.647f, 0.0f, 1.0f);
				pitch -= 0.18f;
				
				shader.setModelMatrix(ModelMatrix.main.getMatrix());
				model.draw(shader);
				
				
			}
			else if(!pigeon){
				Gdx.gl.glUniform4f(LabMeshTexGame.colorLoc, 0.0f, 0.0f, 0.0f, 1.0f);
				ModelMatrix.main.pushMatrix();
				ModelMatrix.main.addTranslation(0, 0, -4f);
				move -= dt * 20;
				boolean negative;
				boolean negative2;
				int sign = 1;
				int sign2 = 1;
				for(int i = 0; i < 12; i++){
					ModelMatrix.main.pushMatrix();
					negative = r.nextBoolean();
					negative2 = r.nextBoolean();
					if(negative){
						sign = -1;
					}
					else{
						sign = 1;
					}
					if(negative2){
						sign2 = -1;
					}
					else{
						sign2 = 1;
					}
					ModelMatrix.main.addTranslation(sign * r.nextFloat()/2, sign2 * r.nextFloat()/2, 0);
					ModelMatrix.main.addScale(0.05f, 0.05f, 0.05f);
					shader.setModelMatrix(ModelMatrix.main.getMatrix());
					SphereGraphic.drawSolidSphere(shader, null, null);
					ModelMatrix.main.popMatrix();
				}
				ModelMatrix.main.popMatrix();
				
				
				
			}
			
			Gdx.gl.glUniform4f(LabMeshTexGame.colorLoc, 1.0f, 1.0f, 1.0f, 1.0f);
			
			
		
		ModelMatrix.main.popMatrix();
	}
	
	
	
	
}
