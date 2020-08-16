package nbody;

import java.util.concurrent.RecursiveAction;

/**
 * NbodyAdvanceTask
 */
public class NbodyAdvanceTask extends RecursiveAction {

    public static final int ADVANCE_THRESHOLD = 1000;
	public static final int APPLY_THRESHOLD = 100;

    private NBody[] bodies;
    private int begin;
    private int end;
    private double dt;

    public NbodyAdvanceTask(NBody[] bodies,int begin,int end,double dt2){
        this.bodies  = bodies;
        this.begin = begin;
        this.end = end;
        this.dt = dt2;
    }


    @Override
    protected void compute() {
        if (end-begin <= APPLY_THRESHOLD) {
            executeSequencial(bodies,begin,end,dt);
        }

        NbodyAdvanceTask t1 = new NbodyAdvanceTask(bodies, begin, (end-begin)/2,dt);
        t1.fork();
        NbodyAdvanceTask t2 = new NbodyAdvanceTask(bodies, (end-begin)/2, end,dt);
        t2.fork();

        t1.join();
        t2.join();


    }

    private void executeSequencial(NBody[] bs, int b, int e,double dt2) {
        
        for (int i = b; i < e;++i) { 
			NBody iBody = bodies[i];
			for (int j = i + 1; j < e; ++j) {
				final NBody body = bodies[j];
				double dx = iBody.x - body.x;
				double dy = iBody.y - body.y;
				double dz = iBody.z - body.z;

				double dSquared = dx * dx + dy * dy + dz * dz;
				double distance = Math.sqrt(dSquared);
				double mag = dt2 / (dSquared * distance);

				synchronized(iBody){
                    iBody.vx -= dx * body.mass * mag;
                    iBody.vy -= dy * body.mass * mag;
                    iBody.vz -= dz * body.mass * mag;
                }
                
                synchronized(body){
                    body.vx += dx * iBody.mass * mag;
                    body.vy += dy * iBody.mass * mag;
                    body.vz += dz * iBody.mass * mag;
                }
            }
        }    		
    }
}