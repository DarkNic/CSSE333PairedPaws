import java.awt.Color;
import java.awt.geom.Point2D;

import util.Random;

public class Pulsar extends Ball {
	private static final int SIZE = 30;
	private static final float INTENSITY_STEP = 0.01f;

	private boolean isIncreasing = true;
	private float intensity;

	public Pulsar(BallEnvironment world) {
		super(world);
		double x = Random.randRange(0, world.getSize().width);
		double y = Random.randRange(0, world.getSize().height);
		setCenterPoint(new Point2D.Double(x, y));
		this.intensity = 0.0f;
	}

	@Override
	public Color getColor() {
		return new Color(this.intensity, this.intensity, this.intensity);
	}

	@Override
	public void updateColor() {
		if (this.isIncreasing) {
			this.intensity += INTENSITY_STEP;
			if (this.intensity > 1.0f) {
				this.intensity = 1.0f;
				this.isIncreasing = !this.isIncreasing;// checks whether the intensity has reached the given value
			}
		} else {
			this.intensity -= INTENSITY_STEP;
			if (this.intensity < 0.0f) {
				this.intensity = 0.0f;
				this.isIncreasing = !this.isIncreasing;// checks whether the intensity has reached the given value and
														// reverses intensity growth of necessary
			}
		}
	}

	@Override
	public void updateSize() {
		// Nothing to do
	}

	@Override
	public void updatePosition() {
		// Nothing to do
	}

	@Override
	public double getDiameter() {
		return SIZE;// returns current diameter of Ball
	}
}
