package com.ru.tgra.graphics.shapes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.ru.tgra.graphics.ModelMatrix;
import com.ru.tgra.graphics.Shader;

public class Maze {
	public static Cell[][] cells;
	public static int width;
	public static int height;
	Shader shader;
	Texture tex, grass;
	
	public Maze(int width, int height){
		tex = new Texture(Gdx.files.internal("textures/fence.png"));
		grass = new Texture(Gdx.files.internal("textures/grass.png"));
		
		shader = new Shader();
		Maze.width = width;
		Maze.height = height;
		cells = new Cell[width][height];
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				
					cells[i][j] = new Cell(false, false);
					
					if(i==0){
						cells[i][j].eastWall=true;
					}
					if(i == width-1){
							cells[i][j].eastWall=true;
					}
					if(j == 0){
						cells[i][j].northWall=true;
					}
					if(j == height-1){
						cells[i][j].northWall=true;
					}
					if(j == 3){
						cells[i][j].northWall = true;
					}
				
			}
		}
		
		cells[2][2].northWall = true;
		cells[2][2].eastWall = true;
		cells[1][2].eastWall = true;
		
		cells[4][2].northWall = true;
		cells[4][2].eastWall = true;
		cells[3][2].eastWall = true;
		
		cells[6][2].northWall = true;
		cells[6][2].eastWall = true;
		cells[5][2].eastWall = true;
		
		cells[8][2].northWall = true;
		cells[8][2].eastWall = true;
		cells[7][2].eastWall = true;
		
		cells[10][2].northWall = true;
		cells[10][2].eastWall = true;
		cells[9][2].eastWall = true;

		cells[12][2].northWall = true;
		cells[12][2].eastWall = true;
		cells[11][2].eastWall = true;
		

	}
	
	public void drawMaze(){
		ModelMatrix.main.pushMatrix();
		
		
		//floor
		ModelMatrix.main.pushMatrix();
		shader.setMaterialDiffuse(0.2f, 0.75f, 0.2f, 1.0f);
		ModelMatrix.main.pushMatrix();
		ModelMatrix.main.addTranslation(0, -49.8f, 0);
		ModelMatrix.main.addScale(50, 100f, 50f);
		shader.setModelMatrix(ModelMatrix.main.getMatrix());
		BoxGraphic.drawSolidCube(shader, grass);
		ModelMatrix.main.popMatrix();
		
		shader.setMaterialDiffuse(0.2f, 0.2f, 0.2f, 1.0f);
		shader.setMaterialSpecular(0.1f, 0.4f, 0.0f, 1.0f);
		shader.setMaterialEmission(0.3f, 0.3f, 0.3f, 1.0f);
		ModelMatrix.main.addTranslation(0,1,0);
		for(int i = 0; i < width; i++){
			ModelMatrix.main.pushMatrix();
				for(int j = 0; j < height; j++){
					cells[i][j].draw(shader, tex);
					ModelMatrix.main.addTranslation(0, 0, -1);
				}
			ModelMatrix.main.popMatrix();
			ModelMatrix.main.addTranslation(1,0,0);
		}
		ModelMatrix.main.popMatrix();
	}
	public static Cell getNorth(int x, int z){
		if(z >= height-1){
			return null;
		}
		else{
			return cells[x][z+1];
		}
	}
	public static Cell getSouth(int x, int z){
		if(z <= 0){
			return null;
		}
		else{
			return cells[x][z-1];
		}
	}
	public static Cell getEast(int x, int z){
		if(x >= width-1){
			return null;
		}
		else{
			return cells[x+1][z];
		}
	}
	public static Cell getWest(int x, int z){
		if(x <= 0){
			return null;
		}
		else{
			return cells[x-1][z];
		}
	}
	
}
