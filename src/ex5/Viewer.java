package ex5;

import java.awt.Point;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import com.sun.opengl.util.FPSAnimator;

import ex5.math.Point3D;
import ex5.math.Vec;
import ex5.models.IRenderable;

/**
 * An OpenGL model viewer 
 *
 */
public class Viewer implements GLEventListener {
	
	private double zoom = 0.0; 					// How much to zoom in? >0 mean magnify, <0 means shrink
	private Point mouseFrom, mouseTo; 			// From where to where was the mouse dragged between the last redraws?
	private boolean isWireframe = false; 		// Should we display wireframe or not?
	private boolean isAxes = true; 				// Should we display axes or not?
	private IRenderable model; 					// Model to display
	private FPSAnimator ani; 					// This object is responsible to redraw the model with a constant FPS
	private GLAutoDrawable m_drawable = null; 	// We store the drawable OpenGL object to refresh the scene
	private boolean isModelCamera = false; 		// Whether the camera is relative to the model, rather than the world (ex6)
	private boolean isModelInitialized = false; // Whether model.init() was called.
	private int canvasWidth, canvasHeight; 		// Canvas measurements 
	private double[] rotationMatrix =           // Cumulated rotation
						{1.0, 0.0, 0.0, 0.0, 
			  			 0.0, 1.0, 0.0, 0.0,
			  			 0.0, 0.0, 1.0, 0.0,
			  			 0.0, 0.0, 0.0, 1.0};

	public Viewer() {
		
	}
	
	@Override
	public void display(GLAutoDrawable drawable) {
		
		GL gl = drawable.getGL();
		
		// Initialize the model
		if (!isModelInitialized) {
			model.init(gl);
			isModelInitialized = true;
		}
		
		// Clear the window before drawing
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

		// Setup the camera
		setupCamera(gl);
		
		// Setup axes
		if (isAxes) {
			renderAxes(gl);
		}
		
		// Setup wireframe
		if (isWireframe) {
			gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_LINE);
		} else {
			gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_FILL);
		}

		// Render the model
		model.render(gl);
		
	}

	private void setupCamera(GL gl) {
		
		if (!isModelCamera) {
			
			//Camera is in an absolute location

			// Reset the MODELVIEW matrix
			gl.glMatrixMode(GL.GL_MODELVIEW);
			gl.glLoadIdentity();
			
			// Activate the virtual trackball
			virtualTrackball(gl);
			
			// Step 4.2 (of the virtual trackball) - Apply the cumulated rotation second
			gl.glMultMatrixd(rotationMatrix, 0);
			
			// Store the new MODELVIEW matrix to the rotationMatrix
			gl.glGetDoublev(GL.GL_MODELVIEW_MATRIX, rotationMatrix, 0);
			
			// Now reset it, and apply zoom
			gl.glLoadIdentity();
            gl.glTranslated(0, 0, -2.5); 		// Apply our automatic zoom (move camera back)
            gl.glTranslated(0, 0, -zoom); 		// Apply the user-specified zoom
            
            // Left-multiply the saved rotation matrix to get the full camera transformation 
            gl.glMultMatrixd(rotationMatrix, 0);
			
			//By this point, we should have already changed the point of view, now set these to null
			//so we don't change it again on the next redraw.
			mouseFrom = null;
			mouseTo = null;
			
		} else {
			
			//Camera is relative to the model
			gl.glLoadIdentity();
			model.setCamera(gl);
			
		}
	}
	
	@Override
	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
			boolean deviceChanged) {
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		
		GL gl = drawable.getGL();
		drawable.setGL(new javax.media.opengl.DebugGL(gl));

		gl.glCullFace(GL.GL_BACK);    	// Set culling face to back face
        gl.glEnable(GL.GL_CULL_FACE); 	// Enable back face culling

        gl.glEnable(GL.GL_DEPTH_TEST); 	// Depth test
		gl.glEnable(GL.GL_NORMALIZE); 	// Normal normalization
		/*		
		gl.glEnable(GL.GL_LIGHTING); 	// Enable lighting
		gl.glLightModelf(GL.GL_LIGHT_MODEL_TWO_SIDE, GL.GL_TRUE);
		*/
		// Initialize display callback timer
		ani = new FPSAnimator(30, true);
		ani.add(drawable);
		
		m_drawable = drawable;
		
		if (model.isAnimated()) //Start animation (for ex6)
			startAnimation();
		else
			stopAnimation();
		
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		
		GL gl = drawable.getGL();
	    
		// Remember the width and height of the canvas for the trackball
	    canvasWidth = width;
	    canvasHeight = height;
	    
	    // Set the projection to perspective
	    gl.glMatrixMode(GL.GL_PROJECTION);
	    gl.glLoadIdentity();
	    
	    //gl.glFrustum(-0.1, 0.1, -0.1 * height / width, 0.1 * height / width, 0.1, 1000.0);
	    GLU glu = new GLU();
	    glu.gluPerspective(90, (double)width/height, 0.1, 1000);
	    
	}

	/**
	 * Stores the mouse coordinates for trackball rotation.
	 * 
	 * @param from
	 *            2D canvas point of drag beginning
	 * @param to
	 *            2D canvas point of drag ending
	 */
	public void storeTrackball(Point from, Point to) {
		//The following lines store the rotation for use when next displaying the model.
		//After you redraw the model, you should set these variables back to null.
		if (!isModelCamera) {
			if (null == mouseFrom)
				mouseFrom = from;
			mouseTo = to;
			m_drawable.repaint();
		}
	}

	/**
	 * Zoom in or out of object. s<0 - zoom out. s>0 zoom in.
	 * 
	 * @param s
	 *            Scalar
	 */
	public void zoom(double s) {
		if (!isModelCamera) {
			zoom += s*0.1;
			m_drawable.repaint();
		}
	}

	/**
	 * Toggle rendering method. Either wireframes (lines) or fully shaded
	 */
	public void toggleRenderMode() {
		isWireframe = !isWireframe;
		m_drawable.repaint();
	}
	
	/**
	 * Toggle whether little spheres are shown at the location of the light sources.
	 */
	public void toggleLightSpheres() {
		model.control(IRenderable.TOGGLE_LIGHTS, null);
		m_drawable.repaint();
	}

	/**
	 * Toggle whether axes are shown.
	 */
	public void toggleAxes() {
		isAxes = !isAxes;
		model.control(IRenderable.TOGGLE_AXES, null);
		m_drawable.repaint();
	}
	
	public void toggleModelCamera() {
		isModelCamera =! isModelCamera;
		m_drawable.repaint();
	}
	
	/**
	 * Start redrawing the scene with 60 FPS
	 */
	public void startAnimation() {
		if (!ani.isAnimating())
			ani.start();
	}
	
	/**
	 * Stop redrawing the scene with 60 FPS
	 */
	public void stopAnimation() {
		if (ani.isAnimating())
			ani.stop();
	}
	
	private void renderAxes(GL gl) {
		gl.glLineWidth(2);
		boolean flag = gl.glIsEnabled(GL.GL_LIGHTING);
		gl.glDisable(GL.GL_LIGHTING);
		gl.glBegin(GL.GL_LINES);
		
		// X axis is RED
		gl.glColor3d(1, 0, 0);
		gl.glVertex3d(0, 0, 0);
		gl.glVertex3d(10, 0, 0);
		
		// Y axis is GREEN
		gl.glColor3d(0, 1, 0);
		gl.glVertex3d(0, 0, 0);
		gl.glVertex3d(0, 10, 0);
		
		// Z axis is BLUE
		gl.glColor3d(0, 0, 1);
		gl.glVertex3d(0, 0, 0);
		gl.glVertex3d(0, 0, 10);
		
		gl.glEnd();
		if(flag)
			gl.glEnable(GL.GL_LIGHTING);
	}

	public void setModel(IRenderable model) {
		this.model = model;
		isModelInitialized = false;
	}
	
	/**
	 * Implements the virtual trackball described in appendix A.
	 * @param gl
	 */
	private void virtualTrackball(GL gl) {
		
		// Don't do anything if the mouse hasn't moved
		if (mouseFrom == null || mouseTo == null) {
			return;
		}
		
		// Step 1 - Transform canvas coordinates to view plane
		// Step 2 - Project view plane coordinates onto sphere
		Point3D p1 = mapPointToUnitCircle(mouseFrom);
		Point3D p2 = mapPointToUnitCircle(mouseTo);
		
		// Step 3 - Compute rotation
		Point3D o = new Point3D(0, 0, 0);
		Vec v1 = Point3D.vectorBetweenTwoPoints(o, p1);
		Vec v2 = Point3D.vectorBetweenTwoPoints(o, p2);
		v1.normalize();
		v2.normalize();
		Vec axis = Vec.crossProd(v1, v2);
		if (axis.length() != 0) {
			axis.normalize();
			double alpha = Math.toDegrees(Math.acos(Vec.dotProd(v1, v2))); 
			
			// Step 4.1 - Apply the newly calculated rotation first
			gl.glRotated(alpha, axis.x, axis.y, axis.z);
		}
		
	}
	
	/**
	 * Maps a point on the canvas to a point on the unit sphere.
	 * (Based on Appendix A)
	 * @param p - the point on the canvas
	 * @return the point on the unit sphere
	 */
	private Point3D mapPointToUnitCircle(Point p) {
		
		double x, y, z;
		
		x = ( (2 * p.getX()) / canvasWidth ) - 1;
		y = 1 - ( (2 * p.getY()) / canvasHeight );
		z = 2 - x*x - y*y;
		if (z < 0) {
			z = 0;
		} else {
			z = Math.sqrt(z);
		}
		
		return new Point3D(x, y, z);
		
	}
	
}
