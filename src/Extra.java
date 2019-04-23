import java.awt.Color;
import java.awt.geom.Point2D;

import util.Random;

public class Extra extends AbstractBouncer {

	private Color color = Color.BLACK;
	private static final int SIZE = 20;
	private double finalSize;
	private double radius;// initializes variables for center point, size of circle, max size of circle,
							// color, window height and width, radius
	private BallEnvironment aWorld;// initializes variable for aWorld
	private int x;
	private int change = 1;

	public Extra(BallEnvironment world) {
		super(world);
		this.aWorld = world;
		this.radius = SIZE / 2;// Sets radius size
		this.finalSize = Random.randInterval(Math.round(this.radius * this.radius * Math.PI * 5),
				Math.round(this.radius * this.radius * Math.PI * 20));// Sets final circle size to a random
		this.x = 1;
	}

	public Extra(BallEnvironment world, Point2D.Double origin) {
		super(world, origin);
		this.aWorld = world;
		this.radius = SIZE / 2;
		this.finalSize = Random.randInterval(Math.round(this.radius * this.radius * Math.PI * 2),
				Math.round(this.radius * this.radius * Math.PI * 10));
		// same constructor other than it taking a set center point
		this.x = 1;
	}

	@Override
	public Color getColor() {
		return this.color;
	}

	@Override
	public void updateSize() {
		double currentArea = this.radius * this.radius * Math.PI;
		if (currentArea >= this.finalSize) {// Checks whether the radius is bigger than the the final size value
			this.change = -1;
		}
		if (this.radius <= SIZE / 2) {
			this.change = 1;
		}
		if (this.x % 555 == 0) {
			this.aWorld
					.addBall(new Extra(aWorld, new Point2D.Double(getCenterPoint().getX(), getCenterPoint().getY())));
			// constructs 2 Exploder balls
		}
		this.radius += this.change * 0.4;// increases the size of the radius

	}

	@Override
	public void updateColor() {
		this.x++;
		if (this.x % 13 == 0) {
			this.color = Color.YELLOW;
		} else {
			this.color = Color.BLACK;
		}

	}

	@Override
	public double getDiameter() {
		return this.radius * 2;
	}

}
