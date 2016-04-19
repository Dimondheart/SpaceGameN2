package xyz.digitalcookies.ogetest;

/** A shape representing a circular region.
 * @author Bryan Charles Bettis
 */
public class Circle
{
	/** Vector that manages the position of this circle. */
	private PlaneVector position;
	/** The radius. */
	private double radius;
	
	/** Create a circle with a radius of 0, centered at x=0,y=0. */
	public Circle()
	{
		this(0, 0, 0);
	}
	
	/** Create a circle with the specified radius and centered over
	 * the specified coordinates.
	 * @param x the center x coordinate
	 * @param y the center y coordinate
	 * @param r the radius of the circle
	 */
	public Circle(double x, double y, double radius)
	{
		position = new PlaneVector(x, y, true);
		setRadius(radius);
	}
	
	public boolean contains(double x, double y)
	{
		return (distanceFromCenter(x, y) <= getRadius());
	}
	
	public boolean contains(Circle c)
	{
		// TODO implement
		return false;
	}
	
	public boolean contains(double x, double y, double radius)
	{
		return contains(new Circle(x, y, radius));
	}
	
	public boolean intersects(Circle c)
	{
		return distanceFromEdge(c, true) <= 0;
	}
	
	public boolean intersects(double x, double y, double radius)
	{
		return intersects(new Circle(x, y, radius));
	}
	
	public boolean intersects(double x1, double y1, double x2, double y2)
	{
		// TODO implement
		return false;
	}
	/** Check if the line formed by vector pq intersects this circle.
	 * The two 
	 * @param p a vector in component form pointing to the initial
	 * 		point of the line
	 * @param q the vector in component form pointing from the 
	 * 		end of the initial vector (p) to the terminal point
	 * @return if the line formed by the two vectors intersects the edge
	 * 		or inner region of this circle
	 */
	public boolean intersects(PlaneVector p, PlaneVector q)
	{
		return intersects(
				p.getX(),
				p.getY(),
				p.getX()+q.getX(),
				p.getY()+q.getY()
				);
	}
	
	public double distanceFromCenter(double x, double y)
	{
		return Math.hypot(x-getX(), y-getY());
	}
	
	public double distanceFromCenter(Circle circle, boolean toCenter)
	{
		if (toCenter)
		{
			return distanceFromCenter(circle.getX(), circle.getY());
		}
		else
		{
			return distanceFromCenter(circle.getX(), circle.getY()) - circle.getRadius(); 
		}
	}
	
	/** Get the distance between the specified point and the edge of
	 * this circle.
	 * @param x the x coordinate of the point
	 * @param y the y coordinate of the point
	 * @return the shortest distance between the specified point and
	 * 		the edge of this circle.
	 */
	public double distanceFromEdge(double x, double y)
	{
		return distanceFromCenter(x, y) - getRadius();
	}
	
	public double distanceFromEdge(Circle circle, boolean toEdge)
	{
		if (toEdge)
		{
			return distanceFromEdge(circle.getX(), circle.getY()) - circle.getRadius();
		}
		else
		{
			return distanceFromEdge(circle.getX(), circle.getY());
		}
	}
	
	public PlaneVector getPosition()
	{
		return position;
	}

	public double getX()
	{
		return position.getX();
	}
	
	public void setX(double x)
	{
		position.setX(x);
	}

	public double getY()
	{
		return position.getY();
	}
	
	public void setY(double y)
	{
		position.setY(y);
	}

	public boolean isEmpty()
	{
		return getRadius() <= 0;
	}
	
	public double getRadius()
	{
		return radius;
	}
	
	public void setRadius(double radius)
	{
		this.radius = Math.abs(radius);
	}
}
