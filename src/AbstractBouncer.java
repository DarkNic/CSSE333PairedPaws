import java.awt.Color;
import java.awt.geom.Point2D;

import util.Random;

/**
 * A ball that bounces off the walls.
 * 
 * @author Curt Clifton. Created Jan 22, 2011.
 */
public abstract class AbstractBouncer extends Ball {
	private double velocity;
	private double angle;
	private double windowH;
	private double windowW;
	private double verticalDirection;
	private Point2D.Double newCenter;
	private double horizontalDirection;// Initialized the variables for center point, velocity, angle, window height,
										// window width,
										// vertical direction constant, and horizontal direction constant

	/**
	 * Constructs a abstract bouncer in the given world.
	 * 
	 * @param world
	 */
	public AbstractBouncer(BallEnvironment world) {
		super(world);
		this.windowH = world.getSize().getHeight();
		this.windowW = world.getSize().getWidth();// Sets values to the window height and width

		Point2D.Double initialcenterPoint = new Point2D.Double(world.getSize().width / 2, world.getSize().height / 2);
		setCenterPoint(initialcenterPoint);// sets the initial position to the middle of the window
		this.velocity = randomVelocity();// uses the method in Ball to create a random velocity
		this.angle = Random.randInterval(0, Math.PI * 2);// finds a random value between 0 and 2 Pi to act as an angle

		this.verticalDirection = 1;
		this.horizontalDirection = 1;// Sets the initial value to the direction constants
	}
	public AbstractBouncer(BallEnvironment world, Point2D.Double origin) {
		super(world);
		this.windowH = world.getSize().getHeight();
		this.windowW = world.getSize().getWidth();

		this.newCenter = new Point2D.Double();
		setCenterPoint(origin);// this is the same as the other constructor other than the center point is set
											// at given origin
		this.velocity = randomVelocity();
		this.angle = Random.randInterval(0, Math.PI * 2);

		this.verticalDirection = 1;
		this.horizontalDirection = 1;

	}
	public Point2D.Double getPosition() {
		return this.newCenter;// returns the current position of the ball
	}

	@Override
	public Color getColor() {
		// Does nothing
		return null;
	}

	public void updatePosition() {
		if (super.centerPoint.getX() >= this.windowW || super.centerPoint.getX() <= 0) {
			this.horizontalDirection = -this.horizontalDirection;// checks whether the ball has hit the left or right
																	// side of the box and
		} // changes the direction constant if it does hit a side
		if (super.centerPoint.getY() >= this.windowH || super.centerPoint.getY() <= 0) {
			this.verticalDirection = -this.verticalDirection;// checks whether the ball has hit the ceiling or the floor
																// of the box and
		} // changes the direction constant if it hits the vertical sides
		super.centerPoint.setLocation(
				super.centerPoint.getX() + (this.horizontalDirection * this.velocity * Math.cos(this.angle)),
				super.centerPoint.getY() + (this.verticalDirection * this.velocity * Math.sin(this.angle))); 
		moveTo(super.centerPoint);
	}// Enacts speed for one timePassed()

	@Override
	public void updateSize() {
		// does nothing
	}

	@Override
	public void updateColor() {
		// does nothing
	}

	@Override
	public double getDiameter() {
		// does nothing
		return 0;
	}
}
