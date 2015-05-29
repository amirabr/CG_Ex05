package ex5.models;

import java.awt.Color;
import java.util.Random;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

public class Planet implements IRenderable {
	
	public enum Planets { Sun, Mercury, Venus, Earth, Mars, Jupiter, Saturn, Uranus, Neptune, Pluto, Moon }
	
	private Planets name; 		// The planet's name
	private Planet moon; 		// The planet's moon
	private int angle; 			// The planet's location along its orbit
	
	private boolean isAxes; 	// Show axes?
	
	/**
	 * Constructor.
	 * @param name - the planet's name
	 */
	public Planet(Planets name) {
		this.name = name;
		this.angle = randomAngle();
		this.isAxes = true;
		
		if (this.name.equals(Planets.Earth)) {
			this.moon = new Planet(Planets.Moon);
		}
	}
	
	/**
	 * Getter for name.
	 * @return
	 */
	public Planets name() {
		return name;
	}
	
	/**
	 * Returns the planet's radius.
	 * @return
	 */
	private double planetRadius() {
		switch (name) {
			case Sun:		return 1.0;
			case Mercury: 	return 0.3;
			case Venus:		return 0.4;
			case Earth:		return 0.5;
			case Mars:		return 0.4;
			case Jupiter:	return 0.7;
			case Saturn:	return 0.6;
			case Uranus:	return 0.5;
			case Neptune:	return 0.5;
			case Pluto:		return 0.1;
			case Moon:		return 0.1;
			default:		return 0.5;
		}
	}
	
	/**
	 * Returns the planet's orbit radius.
	 * @return
	 */
	private double orbitRadius() {
		switch (name) {
			case Sun:		return 0.0;
			case Mercury: 	return 1.5;
			case Venus:		return 2.5;
			case Earth:		return 4.0;
			case Mars:		return 6.0;
			case Jupiter:	return 9.0;
			case Saturn:	return 12.0;
			case Uranus:	return 14.0;
			case Neptune:	return 16.0;
			case Pluto:		return 18.0;
			case Moon:		return 0.7;
			default:		return 22.0;
		}
	}
	
	/**
	 * Returns the planet's color.
	 * @return
	 */
	private Color planetColor() {
		switch (name) {
			case Sun:		return new Color(245, 110, 27);
			case Mercury: 	return new Color(196, 192, 188);
			case Venus:		return new Color(227, 208, 123);
			case Earth:		return new Color(123, 178, 227);
			case Mars:		return new Color(222, 135, 64);
			case Jupiter:	return new Color(219, 183, 154);
			case Saturn:	return new Color(221, 227, 179);
			case Uranus:	return new Color(220, 242, 250);
			case Neptune:	return new Color(137, 162, 240);
			case Pluto:		return new Color(219, 219, 219);
			case Moon:		return new Color(247, 249, 252);
			default:		return new Color(255, 0, 225);
		}
	}
	
	/**
	 * Returns the planet's orbit color.
	 * @return
	 */
	private Color orbitColor() {
		return new Color(179, 252, 252);
		/*switch (name) {
			case Sun:		return new Color(0, 1, 1);
			case Mercury: 	return new Color(0, 1, 1);
			case Venus:		return new Color(0, 1, 1);
			case Earth:		return new Color(0, 1, 1);
			case Mars:		return new Color(0, 1, 1);
			case Jupiter:	return new Color(0, 1, 1);
			case Saturn:	return new Color(0, 1, 1);
			case Uranus:	return new Color(0, 1, 1);
			case Neptune:	return new Color(0, 1, 1);
			case Pluto:		return new Color(0, 1, 1);
			case Moon:		return new Color(0, 1, 1);
			default:		return new Color(0, 1, 1);
		}*/
	}
	
	/**
	 * Returns the planet's orbital inclination.
	 * @return
	 */
	private double orbitalInclination() {
		switch (name) {
			case Sun:		return 0.0;
			case Mercury: 	return 7.0;
			case Venus:		return 3.39;
			case Earth:		return 0.0;
			case Mars:		return 1.85;
			case Jupiter:	return 1.3;
			case Saturn:	return 2.49;
			case Uranus:	return 0.77;
			case Neptune:	return 1.77;
			case Pluto:		return 17.2;
			case Moon:		return 20.0;
			default:		return 0.0;
		}
	}
	
	/**
	 * Returns the planet's axial tilt.
	 * @return
	 */
	private double axialTilt() {
		switch (name) {
			case Sun:		return 0.0;
			case Mercury: 	return 2.0;
			case Venus:		return 2.0;
			case Earth:		return 23.45;
			case Mars:		return 24.0;
			case Jupiter:	return 3.1;
			case Saturn:	return 26.7;
			case Uranus:	return 97.9;
			case Neptune:	return 28.8;
			case Pluto:		return 57.5;
			case Moon:		return 0.0;
			default:		return 0.0;
		}
	}
	
	public void renderPlanet(GL gl, GLU glu, GLUquadric quad) {
		
		// Changing stuff to draw the planet
		gl.glPushMatrix();
		
		// Move the planet somewhere on its orbit
		double x = this.orbitRadius() * Math.cos(angle);
		double z = this.orbitRadius() * Math.sin(angle);
		gl.glTranslated(x, 0.0, z);
		
		// Rotate around Z to mimic axial tilt
		gl.glRotated(this.axialTilt(), 0.0, 0.0, 1.0);
		
		// Mix some colors
		float[] compArray = new float[3];
		this.planetColor().getColorComponents(compArray);
		gl.glColor3fv(compArray, 0);
		
		// Set material properties
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE, compArray, 0);
		
		// Draw the planet
		glu.gluSphere(quad, this.planetRadius(), 50, 50);
		
	}
	
	public void renderOrbit(GL gl) {
		
		// Disable lighting before drawing
		gl.glDisable(GL.GL_LIGHTING);
		
		// Changing stuff to draw the orbit
		gl.glPushMatrix();
		
		// Rotate around Z to mimic orbital inclination
		gl.glRotated(this.orbitalInclination(), 0.0, 0.0, 1.0);
		
		// Change drawing color to desired orbit color
		float[] compArray = new float[3];
		this.orbitColor().getColorComponents(compArray);
		gl.glColor3fv(compArray, 0);
		
		// Draw a line loop
		gl.glLineWidth(0.5f);
		DrawCircle(gl, this.orbitRadius(), 100);
		
		// Re-enable lighting
		gl.glEnable(GL.GL_LIGHTING);
		
	}
	
	public void renderAxes(GL gl) {
		
		// Disable lighting before drawing
		gl.glDisable(GL.GL_LIGHTING);
		
		// Set a dynamic length for the axes
		// so they'll look great on any planet
		double length = this.planetRadius() * 1.5;
		
		// Set line width and start drawing
		gl.glLineWidth(2);
		gl.glBegin(GL.GL_LINES);
		
		// X axis is RED
		gl.glColor3d(1.0, 0.0, 0.0);
		gl.glVertex3d(0.0, 0.0, 0.0);
		gl.glVertex3d(length, 0.0, 0.0);
		
		// Y axis is GREEN
		gl.glColor3d(0.0, 1.0, 0.0);
		gl.glVertex3d(0.0, 0.0, 0.0);
		gl.glVertex3d(0.0, length, 0.0);
		
		// Z axis is BLUE
		gl.glColor3d(0.0, 0.0, 1.0);
		gl.glVertex3d(0.0, 0.0, 0.0);
		gl.glVertex3d(0.0, 0.0, length);
		
		// End drawing
		gl.glEnd();
		
		// Re-enable lighting
		gl.glEnable(GL.GL_LIGHTING);
		
	}
	
	private void renderRing(GL gl, GLU glu, GLUquadric quad) {
		
		// Changing stuff to draw the ring
		gl.glPushMatrix();
		
		// Mix some colors
		float[] compArray = new float[3];
		this.planetColor().getColorComponents(compArray);
//		gl.glColor3fv(compArray, 0);
		
		// Set material properties
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE, compArray, 0);
		
		// gluDisc renders a disc on the Z=0 plane,
		// so we need to rotate 90 degrees around X
		gl.glRotated(90.0, 1.0, 0.0, 0.0);
		
		// Draw the up-facing disc
		glu.gluDisk(quad, 0.75, 1.0, 50, 50);
		
	    // Flip side
		gl.glRotated(180.0, 0.0, 1.0, 0.0);
	    
		// Draw the down-facing disc
	    glu.gluDisk(quad, 0.75, 1.0, 50, 50);
	    
	    // Go back
	    gl.glPopMatrix();
		
	}
	
	@Override
	public void render(GL gl) {
		
		GLU glu = new GLU();
		GLUquadric quad = glu.gluNewQuadric();
		
		// Draw the orbit (first push - orbital incline)
		renderOrbit(gl);
		
		// Draw the planet (second push - axial tilt)
		renderPlanet(gl, glu, quad);
		
		// Draw the ring if it's saturn (push & pop inside)
		if (name.equals(Planets.Saturn)) {
			renderRing(gl, glu, quad);
		}
		
		// Draw the moon if it's earth (push & pop inside)
		if (name.equals(Planets.Earth)) {
			moon.render(gl);
		}
		
		// Draw the axes (no push - no need)
		if (isAxes) {
			renderAxes(gl);
		}
		
		// Two pops - bring it back to normal
		gl.glPopMatrix();
		gl.glPopMatrix();
		
	}

	@Override
	public void init(GL gl) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void control(int type, Object params) {

		switch (type) {
	    	case IRenderable.TOGGLE_AXES: 
	    		isAxes = !isAxes;
	    		if (name.equals(Planets.Earth)) {
	    			moon.control(IRenderable.TOGGLE_AXES, null);
	    		}
	    		break;
		}
		
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

	/**
	 * Draws a circle with radius r around (0,0,0).
	 * This method was modified from "http://slabode.exofire.net/circle_draw.shtml".
	 * @param gl
	 * @param r
	 * @param num_segments
	 */
	private void DrawCircle(GL gl, double r, int num_segments) {
		
		double theta = 2 * Math.PI / (double)(num_segments); 
		double c = Math.cos(theta);
		double s = Math.sin(theta);
		double t;

		double x = r; 	// We start at angle = 0 
		double z = 0; 
	    
		gl.glBegin(GL.GL_LINE_LOOP); 
		for(int ii = 0; ii < num_segments; ii++) { 
			 
			// Output vertex
			gl.glVertex3d(x, 0.0, z);
	        
			// Apply the rotation matrix
			t = x;
			x = c * x - s * z;
			z = s * t + c * z;
			
		} 
		gl.glEnd();
		
	}
	
	/**
	 * Returns a random angle between 0 and 360 degrees.
	 * @return
	 */
	private int randomAngle() {
		
		int min = 0;
		int max = 360;
		Random rand = new Random();
		return rand.nextInt((max - min) + 1) + min;
		
	}
	
}
