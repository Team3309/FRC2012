package org.team3309.subsystems;

import java.io.InputStreamReader;

import javax.microedition.io.Connector;
import javax.microedition.io.SocketConnection;

import org.json.me.JSONObject;
import org.team3309.VisionKeys;

import com.sun.squawk.io.BufferedReader;

import edu.wpi.first.wpilibj.command.Subsystem;

public class VisionSubsystem extends Subsystem {

	private static VisionSubsystem instance = null;
	private SocketConnection socket = null;
	private BufferedReader in = null;
	private JSONObject data = null;
	private boolean connected = false;
	
	private Handler mHandler;

	private VisionSubsystem() {
		try {
			socket = (SocketConnection) Connector.open(VisionKeys.BRAIN_SOCKET);
			System.out.println("Opened socket");
			in = new BufferedReader(new InputStreamReader(socket.openDataInputStream()));
			mHandler = new Handler(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static VisionSubsystem getInstance() {
		if (instance == null)
			instance = new VisionSubsystem();
		return instance;
	}

	protected void initDefaultCommand() {
	}

	public double getRPM() {
		return data.optDouble(VisionKeys.RPM);
	}

	public double getOffAngle() {
		int ret = data.optInt(VisionKeys.OFF_ANGLE);
		if(Math.abs(ret) < 3)
			ret = 0;
		return ret;
	}

	public boolean canShoot() {
		return data.optBoolean(VisionKeys.CAN_SHOOT);
	}

	private class Handler implements Runnable {

		private BufferedReader in;

		protected Handler(BufferedReader reader) {
			in = reader;
			new Thread(this).start();
			System.out.println("Started Handler for vision");
		}

		public void run() {
			try {
				while (true) {
					String line = in.readLine();
					data = new JSONObject(line);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	}

}
