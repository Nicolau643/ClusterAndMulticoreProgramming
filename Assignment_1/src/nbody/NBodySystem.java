package nbody;

import java.util.Arrays;
import java.util.Random;
import java.util.function.BiFunction;

public class NBodySystem {
	
	public static final int DEFAULT_ITERATIONS = 5;
	public static final int DEFAULT_SIZE = 50000;

	static final double PI = 3.141592653589793;
	static final double SOLAR_MASS = 4 * PI * PI;
	
	protected NBody[] bodies;

	volatile Double px = 0.0;
	volatile Double py = 0.0;
	volatile Double pz = 0.0;

	public NBodySystem(int n, long seed) {
		Random random = new Random(seed);
		bodies = new NBody[n];
		
		BiFunction<Integer, Integer, Void> func = (a,b) -> { 
			for (int i = a; i < b;i++) { 
				bodies[i] = new NBody(random);
				px += bodies[i].vx * bodies[i].mass;
				py += bodies[i].vy * bodies[i].mass;
				pz += bodies[i].vz * bodies[i].mass;
			} 
		  return null;
		};
		/*
		for (int i = 0; i < n; i++) {
			bodies[i] = new NBody(random);
			px += bodies[i].vx * bodies[i].mass;
			py += bodies[i].vy * bodies[i].mass;
			pz += bodies[i].vz * bodies[i].mass;
		}*/

		ParallelLib.makeTask(func, n);
		
		bodies[0].offsetMomentum(px, py, pz);
	}
	
	public void advance(double dt) {

		NbodyAdvanceTask t = new NbodyAdvanceTask(bodies, 0, bodies.length, dt);
		t.compute();

		
		for (NBody body : bodies) {
			body.x += dt * body.vx;
			body.y += dt * body.vy;
			body.z += dt * body.vz;
		}
	}

	public double energy() {
		double dx, dy, dz, distance;
		double e = 0.0;

		for (int i = 0; i < bodies.length; ++i) {
			NBody iBody = bodies[i];
			e += 0.5 * iBody.mass * (iBody.vx * iBody.vx + iBody.vy * iBody.vy + iBody.vz * iBody.vz);

			for (int j = i + 1; j < bodies.length; ++j) {
				NBody jBody = bodies[j];
				dx = iBody.x - jBody.x;
				dy = iBody.y - jBody.y;
				dz = iBody.z - jBody.z;

				distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
				e -= (iBody.mass * jBody.mass) / distance;
			}
		}
		return e;
	}
}