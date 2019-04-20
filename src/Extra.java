import java.awt.Color;
import java.awt.geom.Point2D;

import util.Random;

public class Extra extends AbstractBouncer{

	private Color color=Color.BLACK;
	private static final int SIZE = 30;

	private double finalSize;

	private double radius;// initializes variables for center point, size of circle, max size of circle,
							// color, window height and width, radius

	private BallEnvironment aWorld;// initializes variable for aWorld

	private int x;
	
	public Extra(BallEnvironment world) {
		super(world);
		this.aWorld = world;
		this.radius = SIZE / 2;// Sets radius size
		this.finalSize = Random.randInterval(Math.round(this.radius * this.radius * Math.PI * 2),
				Math.round(this.radius * this.radius * Math.PI * 10));// Sets final circle size to a random
		this.x=1;
	}

	public Extra(BallEnvironment world, Point2D.Double origin) {
		super(world, origin);
		this.aWorld = world;
		this.radius=SIZE/2;
		this.finalSize = Random.randInterval(Math.round(this.radius * this.radius * Math.PI * 2),
				Math.round(this.radius * this.radius * Math.PI * 10));
		// same constructor other than it taking a set center point
		int x=1;
	}
	
	@Override
	public Color getColor() {
		return this.color;
	}
	
	@Override
	public void updateSize() {
		double currentArea = this.radius * this.radius * Math.PI;
		if (currentArea >= this.finalSize) {// Checks whether the radius is bigger than the the final size value
			this.aWorld.addBall(
					new Extra(aWorld, new Point2D.Double(getCenterPoint().getX(), getCenterPoint().getY())));
			this.aWorld.addBall(
					new Extra(aWorld, new Point2D.Double(getCenterPoint().getX(), getCenterPoint().getY())));
																				// constructs 2 Exploder balls
			die();// Destroys the initial Exploder Ball
		} else {
			this.radius += 0.1;// increases the size of the radius
		}
	}
	
	@Override
	public void updateColor() {
		this.x++;
		if(this.x%13==0) {
			this.color=Color.YELLOW;
		}
		else {
			this.color=Color.BLACK;
		}
		
	}
	
	@Override
	public double getDiameter() {
		// TODO Auto-generated method stub
		return this.radius*2;
	}

}

