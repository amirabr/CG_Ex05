package ex5.models;

import javax.media.opengl.GL;

public class Cube implements IRenderable {
	
	public Cube() {
		
	}

	@Override
	public void render(GL gl) {

		double r = 1.0;
		
		// We don't want the cube to be affected by lighting
		gl.glDisable(GL.GL_LIGHTING);
		
		// Start drawing the cube
		gl.glBegin(GL.GL_QUADS);
		
		gl.glColor3d(0,0,1);
		gl.glVertex3d(-r,-r,+r);
		gl.glColor3d(1,0,1);
		gl.glVertex3d(+r,-r,+r);
		gl.glColor3d(1,1,1);
		gl.glVertex3d(+r,+r,+r);
		gl.glColor3d(0,1,1);
		gl.glVertex3d(-r,+r,+r);
		
		gl.glColor3d(0,0,0);
		gl.glVertex3d(-r,-r,-r);
		gl.glColor3d(0,0,1);
		gl.glVertex3d(-r,-r,+r);
		gl.glColor3d(0,1,1);
		gl.glVertex3d(-r,+r,+r);
		gl.glColor3d(0,1,0);
		gl.glVertex3d(-r,+r,-r);

		gl.glColor3d(1,0,1);
		gl.glVertex3d(+r,-r,+r);
		gl.glColor3d(1,0,0);
		gl.glVertex3d(+r,-r,-r);
		gl.glColor3d(1,1,0);
		gl.glVertex3d(+r,+r,-r);
		gl.glColor3d(1,1,1);
		gl.glVertex3d(+r,+r,+r);
		
		gl.glColor3d(1,1,0);
		gl.glVertex3d(+r,+r,-r);
		gl.glColor3d(1,0,0);
		gl.glVertex3d(+r,-r,-r);
		gl.glColor3d(0,0,0);
		gl.glVertex3d(-r,-r,-r);
		gl.glColor3d(0,1,0);
		gl.glVertex3d(-r,+r,-r);			

		gl.glColor3d(0,1,1);
		gl.glVertex3d(-r,+r,+r);
		gl.glColor3d(1,1,1);
		gl.glVertex3d(+r,+r,+r);
		gl.glColor3d(1,1,0);
		gl.glVertex3d(+r,+r,-r);
		gl.glColor3d(0,1,0);
		gl.glVertex3d(-r,+r,-r);
				
		gl.glColor3d(0,0,0);
		gl.glVertex3d(-r,-r,-r);
		gl.glColor3d(1,0,0);
		gl.glVertex3d(+r,-r,-r);
		gl.glColor3d(1,0,1);
		gl.glVertex3d(+r,-r,+r);
		gl.glColor3d(0,0,1);
		gl.glVertex3d(-r,-r,+r);	
		
		// End of cube
	    gl.glEnd();
	    
	    // Re-enable lighting
	    gl.glEnable(GL.GL_LIGHTING);
		
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
