import java.awt.Color;
import java.awt.geom.Point2D;

import util.Random;

public class Mover extends Ball {
	private static final int SIZE = 30;

	private Color color;
	private double velocity;
	private double angle;// initializes the variables for size, center point, color, velocity, and angle

	public Mover(BallEnvironment world) {
		super(world);
		Point2D.Double initialcenterPoint = new Point2D.Double(world.getSize().width / 2, world.getSize().height / 2);
		setCenterPoint(initialcenterPoint);// sets center point to the center of the window
		this.velocity = randomVelocity();// selects a random velocity from the method in Ball
		this.angle = Random.randInterval(0, Math.PI * 2);// generates a random number between 0 and 2 Pi to represent
															// and angle
		this.color = Color.GREEN;
	}

	@Override
	public Color getColor() {
		return this.color;// returns the current color
	}

	public void updatePosition() {
		super.centerPoint.setLocation(super.centerPoint.getX() + (this.velocity * Math.cos(this.angle)),
				super.centerPoint.getY());// sets horizontal speed
		super.centerPoint.setLocation(super.centerPoint.getX(),
				this.centerPoint.getY() + (this.velocity * Math.sin(this.angle)));// sets vertical speed
		super.centerPoint.setLocation(super.getCenterPoint().getX(), super.getCenterPoint().getY());
		Point2D.Double newCenter = new Point2D.Double(super.centerPoint.getX(), super.centerPoint.getY());
		// defines speed through a new point
		moveTo(newCenter);
	}// Enacts speed for one timePassed()

	@Override
	public void updateSize() {
		// Does Nothing

	}

	@Override
	public void updateColor() {
	}

	@Override
	public double getDiameter() {
		return SIZE;// returns current diameter of the ball
	}

}
