
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import util.Random;

/**
 * A Ball is an abstract class that provides a default timePassed() and
 * getShape() implementations and several abstract methods that subclasses must
 * override to get the desired behavior.
 * 
 * The default implementation of {@link #timePassed()} calls the abstract
 * methods {@link #updatePosition()}, {@link #updateSize()}, and
 * {@link #updateColor()}, giving subclasses the chance to animate any of those
 * properties.
 * 
 * The default implementation of {@link #getShape()} calls the abstract method
 * 
 * @author Curt Clifton. Created Jan 22, 2011.
 */
public abstract class Ball implements Drawable, Temporal, Relocatable {
	private static final double MAX_VELOCITY = 2;
	private static final double MIN_VELOCITY = 0.3;

	protected Point2D centerPoint;
	private BallEnvironment world;

	private boolean isPaused = false;

	/**
	 * Constructs a new ball at location (0,0) in the given world.
	 * 
	 * @param world
	 */
	public Ball(BallEnvironment world) {
		this(world, new Point2D.Double());
	}

	/**
	 * Constructs a ball centered at the given point in the given world.
	 * 
	 * @param world
	 * 
	 * @param centerPoint
	 */
	public Ball(BallEnvironment world, Point2D centerPoint) {
		this.world = world;
		this.centerPoint = centerPoint;
	}

	protected static double randomVelocity() {
		double speed = Random.randInterval(MIN_VELOCITY, MAX_VELOCITY);
		if (Math.random() > 0.7) {
			return speed;
		} else {
			return -speed;
		}
	}

	// -------------------------------------------------------------------------
	// Utility accessors for subclasses

	/**
	 * Sets the center point of this object
	 * 
	 * @param centerPoint
	 */
	protected void setCenterPoint(Point2D centerPoint) {
		this.centerPoint = centerPoint;
	}

	/**
	 * Returns the value of the field called 'world'.
	 * 
	 * @return Returns the world.
	 */
	protected BallEnvironment getWorld() {
		return this.world;
	}

	// -------------------------------------------------------------------------
	// Temporal interface

	@Override
	public void timePassed() {
		if (this.isPaused) {
			return;
		}
		updateColor();
		updateSize();
		updatePosition();
	}

	@Override
	public void die() {
		this.world.removeBall(this);
	}

	@Override
	public boolean getIsPaused() {
		return this.isPaused;
	}

	@Override
	public void setIsPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}

	// -------------------------------------------------------------------------
	// Drawable interface (partial implementation, subclasses must help)

	@Override
	public Shape getShape() {
		double x = getCenterPoint().getX();
		double y = getCenterPoint().getY();
		double size = getDiameter();
		return new Ellipse2D.Double(x - size / 2, y - size / 2, size, size);
	}

	// -------------------------------------------------------------------------
	// Relocatable interface

	/**
	 * Re-centers this ball at the given point.
	 * 
	 * @param point
	 */
	@Override
	public void moveTo(Point2D point) {
		setCenterPoint(point);
	}

	/**
	 * Returns the center point of this object.
	 * 
	 * @return the center point
	 */
	@Override
	public Point2D getCenterPoint() {
		return this.centerPoint;
	}

	// -------------------------------------------------------------------------
	// Abstract "template methods" that must be overridden by subclasses.

	/**
	 * Updates the position of this object, if necessary, due to the passing of a
	 * "moment" in time.
	 */
	public abstract void updatePosition();

	/**
	 * Updates the size of this object, if necessary, due to the passing of a
	 * "moment" in time.
	 */
	public abstract void updateSize();

	/**
	 * Updates the color of this object, if necessary, due to the passing of a
	 * "moment" in time.
	 */
	public abstract void updateColor();

	/**
	 * Returns the diameter of this ball.
	 * 
	 * @return the diameter
	 */
	public abstract double getDiameter();

}
