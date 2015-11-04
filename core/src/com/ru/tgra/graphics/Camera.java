package com.ru.tgra.graphics;

import java.nio.FloatBuffer;
//import javax.vecmath.Point3d;

import com.badlogic.gdx.utils.BufferUtils;
import com.ru.tgra.graphics.shapes.Cell;
import com.ru.tgra.graphics.shapes.Maze;

public class Camera {

	public Point3D eye;
	Vector3D u;
	Vector3D v;
	Vector3D n;
	private float absolutePitchAngle;
	
	boolean orthographic;
	
	float left;
	float right;
	float bottom;
	float top;
	float near;
	float far;
	
	private FloatBuffer matrixBuffer;
	
	public Camera()
	{

		absolutePitchAngle = 90;
		//this.viewMatrixPointer = viewMatrixPointer;
		//this.projectionMatrixPointer = projectionMatrixPointer;

		matrixBuffer = BufferUtils.newFloatBuffer(16);
		
		eye = new Point3D();
		u = new Vector3D(1,0,0);
		v = new Vector3D(0,1,0);
		n = new Vector3D(0,0,1);
		
		orthographic = true;
		
		this.left = -1;
		this.right = 1;
		this.bottom = -1;
		this.top = 1;
		this.near = -1;
		this.far = 1;
	}
	
	public void look(Point3D eye, Point3D center, Vector3D up)
	{
		this.eye.set(eye.x, eye.y, eye.z);
		n = Vector3D.difference(eye, center);
		u = up.cross(n);
		n.normalize();
		u.normalize();
		v = n.cross(u);
	}
	
	public void setEye(float x, float y, float z) {
		eye.set(x,y,z);
	}
	
	public void slide(float delU, float delV, float delN)
	{
		float originX = eye.x;
		float originZ = eye.z;
		int locX = -1;
		int locZ = -1;
		
		
		collisionCheck(delU, delV, delN, originX, originZ, locX, locZ, false);
		//extraObjectCollision(originX, originZ);
	}
	
	public void walkForward(float del)
	{
		float originX = eye.x;
		float originZ = eye.z;
		int locX = -1;
		int locZ = -1;
		collisionCheck(0, 0, del, originX, originZ, locX, locZ, true);
		//extraObjectCollision(originX, originZ);
		
	}
	public void extraObjectCollision(float originX, float originZ){
		if(eye.z < -14){
			double xd = eye.x-7.5f;
			double zd = eye.z-(-16f);
			if(Math.sqrt(Math.pow(xd, 2)+Math.pow(zd, 2)) < 0.5f){
				
				eye.x = originX;
				eye.z = originZ;
			}
		}

	}
	
	public void collisionCheck(float delU, float delV, float delN, float originX, float originZ, int locX, int locZ, boolean forward){
		
		float padding = 0.35f;

		for(int i = 0; i < Maze.width; i++){
			if(Math.floor(eye.x) == i){
				for(int j = 0; j < Maze.height; j++){
					if(Math.floor(eye.z) == -j){
						locX = i;
						locZ = j;
						break;
					}
				}
				break;
			}
		}
		
		if(forward){
			eye.x -= delN*n.x;
			eye.z -= delN*n.z;
		}
		else{
			eye.x += delU*u.x + delV*v.x + delN*n.x;
			//eye.y += delU*u.y + delV*v.y + delN*n.y;
			eye.z += delU*u.z + delV*v.z + delN*n.z;
		}
		
		if(locX < 0 || locZ < 0){
			return;
		}
		
		Cell cell = Maze.cells[locX][locZ];
		float relX = eye.x-locX;
		float relZ = eye.z+locZ;
		
		if(cell.northWall){
			if(relZ <= padding){
				eye.z = originZ;
			}
		}
		if(cell.eastWall){
			if(relX >= 1-padding){
				eye.x = originX;
			}
		}
		
		// Check south wall
		cell = Maze.getSouth(locX, locZ);
		if(cell != null){
			if(cell.northWall){
				if(relZ >= 1-padding){
					eye.z = originZ;
				}
			}
		}
		
		// Check west wall
		cell = Maze.getWest(locX, locZ);
		if(cell != null){
			if(cell.eastWall){
				if(relX < padding){
					eye.x = originX;
				}
			}
		}
		
		// Check westNorth wall
		cell = Maze.getWest(locX, locZ);
		if(cell != null){
			if(cell.northWall && !Maze.cells[locX][locZ].northWall){
				if(relX < padding && relZ < padding){
					eye.x = locX + padding;
					
					
				}
			}else{
				cell = Maze.getWest(locX, locZ);
				//System.out.println("westNortheastwall1");
				if(cell != null){
					cell = Maze.getNorth(locX-1, locZ);
					if(cell != null){
						if(cell.eastWall && !Maze.getWest(locX, locZ).eastWall){
							
							if(relX < padding && relZ < padding){
								eye.z = -locZ + padding;
							}
						}
					}
				}
			}
		}
		
		//southwestNorthwall
		cell = Maze.getSouth(locX, locZ);
		if(cell != null){
			cell = Maze.getWest(locX, locZ-1);
			if(cell != null){
				if(cell.northWall && !Maze.getSouth(locX, locZ).northWall){
					if(relX < padding && relZ > 1-padding){
						eye.x = locX + padding;
					}
				}else{
					//southWestEastwall
					cell = Maze.getSouth(locX, locZ);
					if(cell != null){
						cell = Maze.getWest(locX, locZ-1);
						if(cell != null){
							if(cell.eastWall && !Maze.getWest(locX, locZ).eastWall){
								if(relX < padding && relZ > 1-padding){
									eye.z = -locZ + 1-padding;
								}
							}
						}
					}
				}
			}
		}
		//southeastNorthwall
		cell = Maze.getSouth(locX, locZ);
		if(cell != null){
			cell = Maze.getEast(locX, locZ-1);
			if(cell != null){
				if(cell.northWall && !Maze.getSouth(locX, locZ).northWall){
					if(relX > 1-padding && relZ > 1-padding){
						eye.x = locX + 1-padding;
					}
				}
				else{
					//southEastwall
					cell = Maze.getSouth(locX, locZ);
					if(cell != null){
						if(cell.eastWall && !Maze.cells[locX][locZ].eastWall){
							if(relX > 1-padding && relZ > 1-padding){
								eye.z = -locZ + 1-padding;
							}
						}
					}
				}
			}
		}
		
		
		//NorthEastwall
		cell = Maze.getNorth(locX, locZ);
		
		if(cell != null){
				if(cell.eastWall && !Maze.cells[locX][locZ].eastWall){
					if(relX > 1-padding && relZ < padding){
						eye.z = -locZ + padding;
					}
				}
				else{
					//eastNorthwall
					cell = Maze.getEast(locX, locZ);
					if(cell != null){
						if(cell.northWall && !Maze.cells[locX][locZ].northWall){
							if(relX > 1-padding && relZ < padding){
								eye.x = locX + 1-padding;
							}
						}
					}
				}
		}
		
	}

	
	public void roll(float angle)
	{
		float radians = angle * (float)Math.PI / 180.0f;
		float c = (float)Math.cos(radians);
		float s = (float)Math.sin(radians);
		Vector3D t = new Vector3D(u.x, u.y, u.z);
		
		u.set(t.x * c - v.x * s, t.y * c - v.y * s, t.z * c - v.z * s);
		v.set(t.x * s + v.x * c, t.y * s + v.y * c, t.z * s + v.z * c);
	}
	
	public void rotateY(float angle)
	{
		float radians = angle * (float)Math.PI / 180.0f;
		float c = (float)Math.cos(radians);
		float s = -(float)Math.sin(radians);
		
		u.set(c * u.x - s * u.z, u.y, s * u.x + c * u.z);
		v.set(c * v.x - s * v.z, v.y, s * v.x + c * v.z);
		n.set(c * n.x - s * n.z, n.y, s * n.x + c * n.z);
	}
	
	public void yaw(float angle)
	{
		float radians = angle * (float)Math.PI / 180.0f;
		float c = (float)Math.cos(radians);
		float s = -(float)Math.sin(radians);
		Vector3D t = new Vector3D(u.x, u.y, u.z);
		
		u.set(t.x * c - n.x * s, t.y * c - n.y * s, t.z * c - n.z * s);
		n.set(t.x * s + n.x * c, t.y * s + n.y * c, t.z * s + n.z * c);
	}
	
	public void pitch(float angle)
	{
		if(angle+absolutePitchAngle > 160 || angle+absolutePitchAngle < 5 ){
			return;
		}
		
		absolutePitchAngle += angle;
		float radians = angle * (float)Math.PI / 180.0f;
		float c = (float)Math.cos(radians);
		float s = (float)Math.sin(radians);
		Vector3D t = new Vector3D(n.x, n.y, n.z);
		
		n.set(t.x * c - v.x *s, t.y * c - v.y * s, t.z * c - v.z *s);
		v.set(t.x * s + v.x * c,  t.y * s + v.y * c,  t.z * s + v.z * c);
	}
	
	public void orthographicProjection(float left, float right, float bottom, float top, float near, float far)
	{
		this.left = left;
		this.right = right;
		this.bottom = bottom;
		this.top = top;
		this.near = near;
		this.far = far;
		orthographic = true;
	}
	
	public void perspectiveProjection(float fov, float ratio, float near, float far)
	{
		this.top = near * (float)Math.tan(((double)fov / 2.0) * Math.PI / 180.0);
		this.bottom = -top;
		this.right = ratio * top;
		this.left = -right;
		this.near = near;
		this.far = far;
		
		orthographic = false;
	}
	
	public FloatBuffer getViewMatrix() {
		
		float[] pm = new float[16];
		
		Vector3D minusEye = new Vector3D(-eye.x, -eye.y, -eye.z);
		
		pm[0] = u.x; pm[4] = u.y; pm[8] = u.z; pm[12] = minusEye.dot(u);
		pm[1] = v.x; pm[5] = v.y; pm[9] = v.z; pm[13] = minusEye.dot(v);
		pm[2] = n.x; pm[6] = n.y; pm[10] = n.z; pm[14] = minusEye.dot(n);
		pm[3] = 0.0f; pm[7] = 0.0f; pm[11] = 0.0f; pm[15] = 1.0f;
		
		matrixBuffer.put(pm);
		matrixBuffer.rewind();
		
		return matrixBuffer;
	}
	
	public FloatBuffer getProjectionMatrix() {
		
		float[] pm = new float[16];
		
		if(orthographic)
		{
			pm[0] = 2.0f / (right - left); pm[4] = 0.0f; pm[8] = 0.0f; pm[12] = -(right + left) / (right - left);
			pm[1] = 0.0f; pm[5] = 2.0f / (top - bottom); pm[9] = 0.0f; pm[13] = -(top + bottom) / (top - bottom);
			pm[2] = 0.0f; pm[6] = 0.0f; pm[10] = 2.0f / (near - far); pm[14] = (near + far) / (near - far);
			pm[3] = 0.0f; pm[7] = 0.0f; pm[11] = 0.0f; pm[15] = 1.0f;
		}
		else
		{
			pm[0] = (2.0f * near) / (right - left); pm[4] = 0.0f; pm[8] = (right + left) / (right - left); pm[12] = 0.0f;
			pm[1] = 0.0f; pm[5] = (2.0f * near) / (top - bottom); pm[9] = (top + bottom) / (top - bottom); pm[13] = 0.0f;
			pm[2] = 0.0f; pm[6] = 0.0f; pm[10] = -(far +  near) / (far - near); pm[14] = -(2.0f * far * near) / (far - near);
			pm[3] = 0.0f; pm[7] = 0.0f; pm[11] = -1.0f; pm[15] = 0.0f;
		}
		
		matrixBuffer = BufferUtils.newFloatBuffer(16);
		matrixBuffer.put(pm);
		matrixBuffer.rewind();
		
		return matrixBuffer;
	}
}
