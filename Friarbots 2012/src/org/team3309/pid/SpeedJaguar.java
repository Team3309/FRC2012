package org.team3309.pid;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.smartdashboard.SendablePIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SpeedJaguar implements SpeedController, PIDSource, PIDOutput,
		Runnable {

	private CANJaguar mJaguar = null;
	private SendablePIDController mController = null;
	private Encoder mEncoder = null;

	public static final double DEAD_ZONE = 15;
	public static final double sMaxSpeed = 4500;
	private int canId = 0;

	private boolean enabled = false;

	private Thread mThread;

	private int lastCount = 0;
	private double mRPM = 0;

	private int REVOLUTION = 360; // encoder ticks per revolution
	private static final int DELTA_T = 100; // 100ms between each sample

	/*public SpeedJaguar(int canId, int aChannel, int bChannel) {
		this(canId, new Encoder(aChannel, bChannel));
	}*/
	
	public SpeedJaguar(int canId, int aChannel, int bChannel){
		this.canId = canId;
		try {
			mJaguar = new CANJaguar(canId);
		} catch (CANTimeoutException e) {
			e.printStackTrace();
		}
		// treat P as D and I as P and D as I
		// this is necessary when using speed control
		mController = new SendablePIDController(0, 1000, 0.0, this, this);
		mController.setInputRange(-sMaxSpeed, sMaxSpeed);
		mController.setOutputRange(-sMaxSpeed, sMaxSpeed);
		mController.setTolerance(10);
		mEncoder = new Encoder(aChannel, bChannel);
		mEncoder.setDistancePerPulse(2.0 / 360.0); // 360 counts to go 2'
		mEncoder.start();
		SmartDashboard.putData("Jag" + this.canId + " PID", mController);

		mThread = new Thread(this, "SpeedJaguar" + canId);
		mThread.start();
		System.out.println(mThread);
	}
	
	public void setPID(double p, double i, double d){
		// treat P as D and I as P and D as I
		// this is necessary when using speed control
		mController.setPID(i, d, p);
	}
	
	public void setEncoderTicksPerRev(int ticks){
		REVOLUTION = ticks;
	}

	public void pidWrite(double output) {
		try {
			mJaguar.setX(output / sMaxSpeed);
		} catch (CANTimeoutException e) {
			e.printStackTrace();
		}
		SmartDashboard.putDouble("SpeedJag"+canId+" set", output/sMaxSpeed);
	}

	public double pidGet() {
		return mRPM;
	}

	public double get() {
		return mRPM;
	}
	
	public double getVoltageSet(){
		return mJaguar.get();
	}

	public void set(double x) {
		if (!enabled)
			enable();
		if (Math.abs(x) < DEAD_ZONE) {
			try {
				mJaguar.setX(0);
			} catch (CANTimeoutException e) {
				e.printStackTrace();
			}
			mController.setSetpoint(0);
			disable();
		}
		mController.setSetpoint(x);
	}

	public void set(double x, byte arg1) {
		if (!enabled)
			enable();
		if (Math.abs(x) < DEAD_ZONE) {
			try {
				mJaguar.setX(0);
			} catch (CANTimeoutException e) {
				e.printStackTrace();
			}
			mController.setSetpoint(0);
			disable();
		}
		mController.setSetpoint(x);
	}

	public void disable() {
		enabled = false;
		try {
			mJaguar.disableControl();
		} catch (CANTimeoutException e) {
			e.printStackTrace();
		}
		mController.disable();
		mEncoder.stop();
	}

	public void enable() {
		System.out.println("Enabling SpeedJaguar"+canId);
		mController.enable();
		mEncoder.start();
		try {
			mJaguar.enableControl();
		} catch (CANTimeoutException e) {
			e.printStackTrace();
		}
		enabled = true;
	}

	public void run() {
		while (true) {
			if (enabled) {
				int curCount = mEncoder.get();
				double revms = (60000 * Math.abs(curCount - lastCount) / REVOLUTION)
						/ DELTA_T;
				mRPM = revms; // 60000*rev/ms = rev/min
				SmartDashboard.putDouble(canId + "RPM", mRPM);
				try {
					Thread.sleep(DELTA_T);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				lastCount = curCount;
			}
		}
	}

	public void setPercentVbus(double x) {
		if(!enabled)
			enable();
		mJaguar.set(x);
	}

}
