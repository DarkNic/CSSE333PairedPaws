import java.awt.Color;
import java.awt.geom.Point2D;

import util.Random;

public class Exploder extends AbstractBouncer {
	private static final int SIZE = 15;

	private Point2D.Double centerPoint;
	private double finalSize;
	private Color color = new Color(255 - 255 - 204);
	private double windowH;
	private double windowW;
	private double radius;// initializes variables for center point, size of circle, max size of circle,
							// color, window height and width, radius

	private BallEnvironment aWorld;// initializes variable for aWorld

	public Exploder(BallEnvironment world) {
		super(world);
		// TODO Auto-generated constructor stub
		this.aWorld = world;
		this.windowH = world.getSize().getHeight();
		this.windowW = world.getSize().getWidth();// Sets values for window height and width, as well as aWorld

		this.radius = SIZE / 2;// Sets radius size
		this.finalSize = Random.randInterval(Math.sqrt(2) * SIZE / 2, Math.sqrt(10) * SIZE / 20);// Sets final circle
																									// size to a random
																									// number between
	} // 2 and 10 times the size of the initial circle

	public Exploder(BallEnvironment world, Point2D.Double origin) {
		super(world, origin);
		this.aWorld = world;
		this.windowH = world.getSize().getHeight();
		this.windowW = world.getSize().getWidth();

		this.radius = SIZE / 2;
		this.finalSize = Random.randInterval(Math.sqrt(2) * SIZE / 2, Math.sqrt(10) * SIZE / 2);// same constructor
																								// other than it taking
																								// a set center point

	}

	@Override
	public Color getColor() {
		// Does Nothing
		return this.color;
	}

	@Override
	public void updateSize() {
		if (this.radius >= this.finalSize) {// Checks whether the radius is bigger than the the final size value
			this.aWorld.addBall(
					new Exploder(aWorld, new Point2D.Double(getCenterPoint().getX(), getCenterPoint().getY())));
			this.aWorld.addBall(
					new Exploder(aWorld, new Point2D.Double(getCenterPoint().getX(), getCenterPoint().getY())));
			// constructs 2 Exploder balls
			die();// Destroys the initial Exploder Ball
		} else {
			this.radius += 0.05;// increases the size of the radius
		}
	}

	@Override
	public void updateColor() {
		this.color = new Color(255 - 255 - 204);// updates the color of the ball

	}

	@Override
	public double getDiameter() {
		return this.radius * 2;// returns the diameter of the ball
	}

}
