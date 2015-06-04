/**
 * CG_EX5
 * Amir Abramovitch 200336626
 * Omri Gotlieb 302671136
 * 
 */

package ex5.math;

import java.util.Scanner;

/**
 * 3D vector class that contains three doubles. Could be used to represent
 * Vectors but also Points and Colors.
 * 
 */
public class Vec {
	
	public double x, y, z;

	/**
	 * Initialize vector to (0,0,0)
	 */
	public Vec() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}

	/**
	 * Initialize vector to given coordinates
	 * 
	 * @param x
	 *            Scalar
	 * @param y
	 *            Scalar
	 * @param z
	 *            Scalar
	 */
	public Vec(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Initializes vector to given coordinates
	 * 
	 * @param v
	 *            string representation of vector coordinates
	 */	
	public Vec(String v) {
		Scanner s = new Scanner(v);
		x = s.nextDouble();
		y = s.nextDouble();
		z = s.nextDouble();
		s.close();
	}

	/**
	 * Initialize vector values to given vector (copy by value)
	 * 
	 * @param v
	 *            Vector
	 */
	public Vec(Vec v) {
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}

	/**
	 * Calculates the reflection of the vector in relation to a given surface
	 * normal. The vector points at the surface and the result points away.
	 * 
	 * @return The reflected vector
	 */
	public Vec reflect(Vec normal) {
		Vec nor = normal.clone();
		nor.scale(nor.dotProd(this) * (-2));
		nor.add(this);
		return nor;
	}

	/**
	 * Adds a to vector
	 * 
	 * @param a
	 *            Vector
	 */
	public void add(Vec a) {
		this.x += a.x;
		this.y += a.y;
		this.z += a.z;
	}

	/**
	 * Subtracts from vector
	 * 
	 * @param a
	 *            Vector
	 */
	public void sub(Vec a) {
		this.x -= a.x;
		this.y -= a.y;
		this.z -= a.z;
	}
	
	/**
	 * Multiplies & Accumulates vector with given vector and a. v := v + s*a
	 * 
	 * @param s
	 *            Scalar
	 * @param a
	 *            Vector
	 */
	public void mac(double s, Vec a) {
		Vec mac = a.clone();
		mac.scale(s);
		add(mac);
	}

	/**
	 * Multiplies vector with scalar. v := s*v
	 * 
	 * @param s
	 *            Scalar
	 */
	public void scale(double s) {
		this.x *= s;
		this.y *= s;
		this.z *= s;
	}

	/**
	 * Pairwise multiplies with another vector
	 * 
	 * @param a
	 *            Vector
	 */
	public void scale(Vec a) {
		this.x *= a.x;
		this.y *= a.y;
		this.z *= a.z;
	}

	/**
	 * Inverses vector
	 * 
	 * @return Vector
	 */
	public void negate() {
		scale(new Vec(-1, -1, -1));
	}

	/**
	 * Computes the vector's magnitude
	 * 
	 * @return Scalar
	 */
	public double length() {
		return Math.sqrt(x*x + y*y + z*z);
	}

	/**
	 * Computes the vector's magnitude squared. Used for performance gain.
	 * 
	 * @return Scalar
	 */
	public double lengthSquared() {
		return x*x + y*y + z*z;	
	}

	/**
	 * Computes the dot product between two vectors
	 * 
	 * @param a
	 *            Vector
	 * @return Scalar
	 */
	public double dotProd(Vec a) {
		return x*a.x + y*a.y + z*a.z;
	}

	/**
	 * Normalizes the vector to have length 1. Throws exception if magnitude is zero.
	 * 
	 * @throws ArithmeticException
	 */
	public void normalize() throws ArithmeticException {
		double l = length();
		if (l == 0)
			throw new ArithmeticException("DividedByZero"); 
		this.x /= l;
		this.y /= l;
		this.z /= l;
	}

	/**
	 * Returns the angle in radians between this vector and the vector
	 * parameter; the return value is constrained to the range [0,PI].
	 * 
	 * @param v1
	 *            the other vector
	 * @return the angle in radians in the range [0,PI]
	 */
	public final double angle(Vec v1) {
		double dotProduct = this.dotProd(v1);
		double aMag = this.length();
		double bMag = v1.length();
		return Math.acos(dotProduct / (aMag * bMag));
	}

	/**
	 * Computes the cross product between two vectors using the right hand rule
	 * 
	 * @param a
	 *            Vector1
	 * @param b
	 *            Vector2
	 * @return Vector1 x Vector2
	 */
	public static Vec crossProd(Vec a, Vec b) {	
		double cx = a.y*b.z - a.z*b.y;
		double cy = a.z*b.x - a.x*b.z;
		double cz = a.x*b.y - a.y*b.x;
		return new Vec(cx, cy, cz);
	}

	/**
	 * Adds vectors a and b
	 * 
	 * @param a
	 *            Vector
	 * @param b
	 *            Vector
	 * @return a+b
	 */
	public static Vec add(Vec a, Vec b) {
		Vec add = a.clone();
		add.add(b);
		return add;
	}

	/**
	 * Subtracts vector b from a
	 * 
	 * @param a
	 *            Vector
	 * @param b
	 *            Vector
	 * @return a-b
	 */
	public static Vec sub(Vec a, Vec b) {
		Vec sub = a.clone();
		sub.sub(b);
		return sub;
	}

	/**
	 * Inverses vector's direction
	 * 
	 * @param a
	 *            Vector
	 * @return -1*a
	 */
	public static Vec negate(Vec a) {
		Vec newA = a.clone();
		newA.negate();
		return newA;
	}

	/**
	 * Scales vector a by scalar s
	 * 
	 * @param s
	 *            Scalar
	 * @param a
	 *            Vector
	 * @return s*a
	 */
	public static Vec scale(double s, Vec a) {
		Vec scale = a.clone();
		scale.scale(s);
		return scale;
	}

	/**
	 * Pair-wise scales vector a by vector b
	 * 
	 * @param a
	 *            Vector
	 * @param b
	 *            Vector
	 * @return a.*b
	 */
	public static Vec scale(Vec a, Vec b) {
		Vec pScale = a.clone();
		pScale.scale(b);
		return pScale;
	}

	/**
	 * Compares vector a to vector b
	 * 
	 * @param a
	 *            Vector
	 * @param b
	 *            Vector
	 * @return a==b
	 */
	public static boolean equals(Vec a, Vec b) {
		return a.equals(b);
	}

	/**
	 * Dot product of a and b
	 * 
	 * @param a
	 *            Vector
	 * @param b
	 *            Vector
	 * @return a.b
	 */
	public static double dotProd(Vec a, Vec b) {
		return a.dotProd(b);
	}
	
	/**
	 * Check if two vectors are linearly dependent 
	 * @param a - the first vector
	 * @param b - the second vector
	 * @return true if they are, false otherwise
	 */
	public static boolean linearlyDependent(Vec a, Vec b) {
		return (a.x / b.x == a.y / b.y) && (a.y / b.y == a.z / b.z);
	}
	
	/**
	 * Returns new tuple instance with identical coordinates.
	 */
	@Override
	public Vec clone() {
		return new Vec(this);
	}
	
	/**
	 * Compares tuple to another tuple.
	 * @param t - other tuple
	 * @return true if they have the same coordinates, false otherwise 
	 */
	public boolean equals(Vec v) {
		return ((this.x == v.x) && (this.y == v.y) && (this.z == v.z));
	}
	
	/**
	 * Returns a string representation of the tuple, in (x, y, z) format.
	 */
	public String toString() {
		return "(" + this.x + ", " + this.y + ", " + this.z + ")";
	}

}
