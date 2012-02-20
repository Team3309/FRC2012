package org.team3309.subsystems;

import java.io.IOException;
import java.io.InputStreamReader;

import javax.microedition.io.Connector;
import javax.microedition.io.SocketConnection;

import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;
import org.team3309.VisionKeys;

import com.sun.squawk.io.BufferedReader;

import edu.wpi.first.wpilibj.command.Subsystem;

public class VisionSubsystem extends Subsystem {

	VisionSubsystem instance 	= null;
	SocketConnection socket 	= null;
	BufferedReader in 			= null;

	JSONObject data				= null;

	boolean connected 			= false;

	public VisionSubsystem() {
		try {
			socket 	= (SocketConnection) Connector.open(VisionKeys.BRAIN_SOCKET);
			in 		= new BufferedReader(new InputStreamReader(socket.openDataInputStream()));
			getInfo.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public VisionSubsystem getInstance() {
		if (instance == null)
			instance = new VisionSubsystem();
		return instance;
	}

	protected void initDefaultCommand() {
	}

	Thread getInfo = new Thread() {
		public void run() {
			String tempIn = "";
			org.json.me.JSONObject obj;
			while(true){
				try {
					if(!tempIn.equals(in.readLine())){
						tempIn = in.readLine();
						data = new JSONObject(tempIn);
					}	
					Thread.sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				}	
			}
		}
	};

	public double getRPM(){
		double rpm = 0;
		try {
			rpm = (double) data.get(VisionKeys.RPM);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return rpm;
	}

	public double getAngle(){
		double angle = 0;
		try{
			angle = (double) data.get(VisionKeys.OFF_ANGLE);
		} catch(JSONException e){
			e.printStackTrace();
		}
		return angle;
	}

}
