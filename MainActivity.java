package cu.slam.muevefotosensorgiro;

import java.util.Scanner;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.SurfaceTexture;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.Menu;
import android.view.Surface;
import android.view.SurfaceView;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener{

	TextView Angle;
	SensorManager sm;
	Sensor saccel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Angle = (TextView)findViewById(R.id.textView2);
		int rotacion = getWindowManager().getDefaultDisplay().getRotation();
		switch (rotacion) {
		case 1:
			rotacion*=-90;			
			break;
		case 3:
			rotacion*=90;
			break;
		default:
			rotacion = 0;
			break;
		}
		Angle.setText(rotacion+"``");	
		sm = (SensorManager)getSystemService(SENSOR_SERVICE);
		saccel = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sm.registerListener(this, saccel,1);
	}	
	
	String PaginaHTML(int grado) {
		String index = "";
		String val = "";
		try {
			AssetManager am = getAssets();
			Scanner in = new Scanner(am.open("index.html"));
			while (in.hasNext()) {
				val = in.nextLine();				
				if (val.contains("deg")) {
					val = "<script>document.getElementById('screen').style.transform = "
							+ "'rotate(" + grado + "deg)'" + ";</script>";
					/*
					 * String rot = ""; for (int i = 0; i < val.length(); i++) {
					 * if (Character.isDigit((char) val.charAt(i))) { rot +=
					 * (val.charAt(i)); } } val = val.replace(rot, "" + grado);
					 * Log.e("chg", val);
					 */
				}
				index += val + "\n";				
			}

		} catch (Exception e) {
			Log.e("err", "" + e.getMessage() + "...");
		} finally {
			return index;
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		float[] ejes = event.values;
		float x = ejes[0];
		float y = ejes[1];
		float z = ejes[2];
		//Angle.setText("eje x: "+x+" eje Y: "+y+ "eje Z: "+z);
	}

}
