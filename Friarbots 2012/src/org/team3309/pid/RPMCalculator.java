package org.team3309.pid;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.smartdashboard.SendablePIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RPMCalculator implements Runnable{
	
	private static final int DELTA_T = 100;
	private static final int REVOLUTION = 1;
	
	private Counter mCounter;
	private double mRPM = 0;
	private boolean enabled = true;
	private int lastCount = 0;
	
	public RPMCalculator(int port){
		mCounter = new Counter(port);
		mCounter.start();
		new Thread(this).start();
	}

	public void run() {
		while (true) {
			if (enabled) {
				int curCount = mCounter.get();
				double revms = (60000 * Math.abs(curCount - lastCount) / REVOLUTION)
						/ DELTA_T;
				mRPM = revms; // 60000*rev/ms = rev/min
				SmartDashboard.putDouble("RPM", mRPM);
				try {
					Thread.sleep(DELTA_T);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				lastCount = curCount;
			}
		}
	}
	
	public double getRpm(){
		return mRPM;
	}
	
}
