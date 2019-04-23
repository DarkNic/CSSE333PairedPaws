import java.awt.Color;
import java.awt.geom.Point2D;

import util.Random;

public class Exploder extends AbstractBouncer {
	private static final int SIZE = 15;

	private double finalSize;
	private Color color = Color.MAGENTA;
	private double radius;// initializes variables for center point, size of circle, max size of circle,
							// color, window height and width, radius
	private BallEnvironment aWorld;// initializes variable for aWorld

	public Exploder(BallEnvironment world) {
		super(world);
		this.aWorld = world;
		this.radius = SIZE / 2;// Sets radius size
		this.finalSize = Random.randInterval(Math.round(this.radius * this.radius * Math.PI * 2),
				Math.round(this.radius * this.radius * Math.PI * 10));// Sets final circle size to a random
	} // size that is 2 to 10 times the size of the initial circle

	public Exploder(BallEnvironment world, Point2D.Double origin) {
		super(world, origin);
		this.aWorld = world;
		this.radius = SIZE / 2;
		this.finalSize = Random.randInterval(Math.round(this.radius * this.radius * Math.PI * 2),
				Math.round(this.radius * this.radius * Math.PI * 10));
		// same constructor other than it taking a set center point
	}

	@Override
	public Color getColor() {
		// Does Nothing
		return this.color;
	}

	@Override
	public void updateSize() {
		double currentArea = this.radius * this.radius * Math.PI;
		if (currentArea >= this.finalSize) {// Checks whether the radius is bigger than the the final size value
			this.aWorld.addBall(
					new Exploder(aWorld, new Point2D.Double(getCenterPoint().getX(), getCenterPoint().getY())));
			this.aWorld.addBall(
					new Exploder(aWorld, new Point2D.Double(getCenterPoint().getX(), getCenterPoint().getY())));
													// constructs 2 Exploder balls
			die();// Destroys the initial Exploder Ball
		} else {
			this.radius += 0.03;// increases the size of the radius
		}
	}

	@Override
	public void updateColor() {
	}

	@Override
	public double getDiameter() {
		return this.radius * 2;// returns the diameter of the ball
	}

}
