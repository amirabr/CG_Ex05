package ex5.models;

import javax.media.opengl.GL;

public class Cube implements IRenderable {
	
	public Cube() {
		
	}

	@Override
	public void render(GL gl) {

		double r = 0.5D;
		
		gl.glBegin(GL.GL_QUADS);
		
		gl.glColor3d(1.0D, 0.0D, 0.0D);
	    gl.glVertex3d(-r, -r, r);
	    gl.glVertex3d(r, -r, r);
	    gl.glVertex3d(r, r, r);
	    gl.glVertex3d(-r, r, r);
	    
	    gl.glColor3d(0.0D, 1.0D, 0.0D);
	    gl.glVertex3d(-r, -r, -r);
	    gl.glVertex3d(-r, -r, r);
	    gl.glVertex3d(-r, r, r);
	    gl.glVertex3d(-r, r, -r);
	    
	    gl.glColor3d(0.0D, 0.0D, 1.0D);
	    gl.glVertex3d(r, -r, r);
	    gl.glVertex3d(r, -r, -r);
	    gl.glVertex3d(r, r, -r);
	    gl.glVertex3d(r, r, r);
	    
	    gl.glColor3d(1.0D, 1.0D, 0.0D);
	    gl.glVertex3d(r, r, -r);
	    gl.glVertex3d(r, -r, -r);
	    gl.glVertex3d(-r, -r, -r);
	    gl.glVertex3d(-r, r, -r);
	    
	    gl.glColor3d(0.0D, 1.0D, 1.0D);
	    gl.glVertex3d(-r, r, r);
	    gl.glVertex3d(r, r, r);
	    gl.glVertex3d(r, r, -r);
	    gl.glVertex3d(-r, r, -r);
	    
	    gl.glColor3d(1.0D, 0.0D, 1.0D);
	    gl.glVertex3d(-r, -r, -r);
	    gl.glVertex3d(r, -r, -r);
	    gl.glVertex3d(r, -r, r);
	    gl.glVertex3d(-r, -r, r);
	    
	    gl.glEnd();
		
	}

	@Override
	public void init(GL gl) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void control(int type, Object params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isAnimated() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCamera(GL gl) {
		// TODO Auto-generated method stub
		
	}
	
	public String toString() {
		return "Cube";
	}

}
