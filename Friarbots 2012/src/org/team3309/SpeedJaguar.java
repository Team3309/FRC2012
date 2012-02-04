package org.team3309;

import com.sun.squawk.util.MathUtils;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.smartdashboard.SendablePIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SpeedJaguar implements SpeedController, PIDSource, PIDOutput{
	
	private CANJaguar mJaguar = null;
	private SendablePIDController mController = null;
	
	private double maxRpm = 500;
	private int canId = 0;
	
	public SpeedJaguar(int canId){
		this.canId = canId;
		try {
			mJaguar = new CANJaguar(canId);
			mJaguar.setSpeedReference(CANJaguar.SpeedReference.kQuadEncoder);
			mJaguar.configEncoderCodesPerRev(360);
		} catch (CANTimeoutException e) {
			e.printStackTrace();
		}
		mController = new SendablePIDController(.5, 0, 0, this, this);
		mController.setInputRange(-maxRpm, maxRpm);
		mController.setOutputRange(-maxRpm, maxRpm);
		SmartDashboard.putData("Jag"+canId+" PID", mController);
		mController.enable();
	}

	public void pidWrite(double output) {
		try {
			mJaguar.setX(output / maxRpm);
		} catch (CANTimeoutException e) {
			e.printStackTrace();
		}
		SmartDashboard.putDouble("speedJagSet", output / maxRpm);
		System.out.println("setting "+canId+": "+output / maxRpm);
	}

	public double pidGet() {
		try {
			SmartDashboard.putInt("Jag"+canId+"RPM", (int) MathUtils.round(mJaguar.getSpeed()));
			return mJaguar.getSpeed();
		} catch (CANTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public void disable() {
		try {
			mJaguar.disableControl();
		} catch (CANTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mController.disable();
	}

	public double get() {
		return mJaguar.get();
	}

	public void set(double x) {
		if(mController.isEnable())
			mController.enable();
		mController.setSetpoint(x);
	}

	public void set(double x, byte arg1) {
		if(mController.isEnable())
			mController.enable();
		mController.setSetpoint(x);
	}

}
