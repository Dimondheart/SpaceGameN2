package xyz.digitalcookies.ogetest;

import xyz.digitalcookies.objective.scene.Scene;
import xyz.digitalcookies.objective.scene.Body;
import xyz.digitalcookies.objective.graphics.GraphicsManager;
import xyz.digitalcookies.objective.utility.ExtendedMath;

/** Basic body for all bodies in space. */
public class SpaceBody extends Body
{
	private GalacticRegionScene scene;
	private double x;
	private double y;
	private double radius;
	private double rotation;
	private double rv;
	private MoveVector velocity;
	private double maxSpeed;
	private double vm;
	private double vd;
	private double am;
	private double ad;
	private MoveVector accel;
	protected long lastUpdate;
	
	public SpaceBody(GalacticRegionScene scene)
	{
		setPos(0, 0);
		setRadius(6);
		setMaxSpeed(10);
		velocity = new MoveVector();
		velocity.zeroVector();
		accel = new MoveVector();
		accel.zeroVector();
		setScene(scene);
	}
	
	@Override
	public boolean setScene(Scene scene)
	{
		if (scene instanceof GalacticRegionScene)
		{
			this.scene = (GalacticRegionScene) scene;
			return true;
		}
		return false;
	}
	
	public GalacticRegionScene getScene()
	{
		return scene;
	}
	
	public void setPos(double x, double y)
	{
		setX(x);
		setY(y);
	}
	
	public double getX()
	{
		return x;
	}
	
	public int getScreenX()
	{
		return (int) (getX()*getScene().getScale() + getScene().getOffsetX());
	}
	
	public void setX(double x)
	{
		this.x = x;
	}
	
	public double getY()
	{
		return y;
	}
	
	public int getScreenY()
	{
		return (int)
				(
					GraphicsManager.getMainLayerSet().getLayerSetHeight()
					- (getY()*getScene().getScale() + scene.getOffsetY())
				);
	}
	
	public void setY(double y)
	{
		this.y = y;
	}
	
	public double getRadius()
	{
		return radius;
	}
	
	public double getScreenRadius()
	{
		return getRadius()*getScene().getScale();
	}
	
	public void setRadius(double radius)
	{
		this.radius = radius;
	}
	
	public double getRotation()
	{
		return rotation;
	}
	
	public void setRotation(double r)
	{
		rotation = r % 360;
		if (rotation < 0)
		{
			rotation = 360 + rotation;
		}
	}
	
	public double getRotationVector()
	{
		return rv;
	}
	
	public void setRotationVector(double rv)
	{
		this.rv = rv;
	}
	
	public MoveVector getVelocity()
	{
		return velocity;
	}
	
	public void applyVelocity(double vm)
	{
		getVelocity().setVector(vm, rotation);
	}
	
	public double getMaxSpeed()
	{
		return maxSpeed;
	}
	
	public void setMaxSpeed(double speed)
	{
		this.maxSpeed = speed;
	}
	
	public MoveVector getAccel()
	{
		return accel;
	}
	
	public void applyAccel(double am)
	{
		getAccel().setVector(am, rotation);
	}
	
	public void update()
	{
		long currTime = scene.getTimer().getTimeNano();
		double elapsed = (currTime - lastUpdate)/1000000.0/1000.0;
		velocity.applyAccel(accel, elapsed);
		if (velocity.getMagnitude() > maxSpeed)
		{
			velocity.setMagnitude(maxSpeed);
		}
		setX(getX() + velocity.getCompX()*elapsed);
		setY(getY() + velocity.getCompY()*elapsed);
		setRotation(getRotation() + getRotationVector()*elapsed);
		lastUpdate = currTime;
	}
	
	public double distanceTo(SpaceBody other, boolean toClosestEdge)
	{
		double distance = 0;
		double dx = other.getX()-getX();
		double dy = other.getY()-getY();
		distance = Math.pow(Math.pow(dx, 2.0) + Math.pow(dy, 2.0), 0.5);
		if (toClosestEdge)
		{
			distance -= (getRadius() + other.getRadius());
		}
		return distance;
	}
	
	public double directionTo(SpaceBody other)
	{
		double direction = 0;
		double dx = other.getX()-getX();
		double dy = other.getY()-getY();
		return calcDirection(dx, dy);
	}
	
	protected double calcDirection(double cx, double cy)
	{
		// Handle the special angles that could result in invalid calcs
		if (cx == 0)
		{
			if (cy > 0)
			{
				return 90;
			}
			else if (cy < 0)
			{
				return 270;
			}
			return 0;
		}
		else if (cy == 0)
		{
			if (cx >= 0)
			{
				return 0;
			}
			else if (cx < 0)
			{
				return 180;
			}
			return 0;
		}
		double dir = 0;
		dir = ExtendedMath.radToDeg(Math.atan(Math.abs(cy/cx)));
		if (cx > 0 && cy > 0)
		{
			// do nothing
		}
		else if (cx < 0 && cy > 0)
		{
			dir = 180 - dir;
		}
		else if (cx < 0 && cy < 0)
		{
			dir = 180 + dir;
		}
		else if (cx > 0 && cy < 0)
		{
			dir = 360 - dir;
		}
		return dir;
	}
}
