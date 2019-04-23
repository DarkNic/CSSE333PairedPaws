import java.awt.Color;
import java.awt.geom.Point2D;

import util.Random;

public class Bouncer extends AbstractBouncer {
	private static final int SIZE = 30;
	private Color color = Color.YELLOW;// Initializes color variable and diameter of circle

	public Bouncer(BallEnvironment world) {
		super(world);
	}

	@Override
	public Color getColor() {
		return this.color;// returns the current color
	}

	@Override
	public void updateSize() {
		// most of the code for this Bouncer class is in the Abstract Bouncer class

	}

	@Override
	public void updateColor() {
//		this.color=Color.MAGENTA;//updates color to this giver color
	}

	@Override
	public double getDiameter() {
		return SIZE;
	}

}
