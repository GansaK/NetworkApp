package com.example.wifiapp;

import java.util.ArrayList;
import java.util.Set;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private BluetoothAdapter BA;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.networklist);
		BA = BluetoothAdapter.getDefaultAdapter();
		IntentFilter filter = new IntentFilter(
				BluetoothAdapter.ACTION_STATE_CHANGED);
		this.registerReceiver(mReceiver, filter);
		

	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.menu_wifi:
			Intent i = new Intent(this , Wifi.class);
			startActivity(i);
			return true;
		case R.id.menu_bluetooth:
			Toast.makeText(this, "You are already in the activity.", Toast.LENGTH_SHORT).show();
			return true ;
		
		default :
			return super.onOptionsItemSelected(item);
		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.menu, menu);
		return true;
	}

	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@SuppressLint("ShowToast")
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
				int pState = intent.getIntExtra(
						BluetoothAdapter.EXTRA_PREVIOUS_STATE, 0);
				int nState = intent
						.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
				Log.w(pState + " ", nState + " ");
				String pstate = null, nstate = null;
				switch (pState) {
				case BluetoothAdapter.STATE_ON:
					pstate = "ON";
					break;
				case BluetoothAdapter.STATE_OFF:
					pstate = "OFF";
					break;
				case BluetoothAdapter.STATE_TURNING_OFF:
					pstate = "TURNING OFF";
					break;
				case BluetoothAdapter.STATE_TURNING_ON:
					pstate = "TURNING ON";
					break;
				}
				switch (nState) {
				case BluetoothAdapter.STATE_ON:
					nstate = "ON";
					break;
				case BluetoothAdapter.STATE_OFF:
					nstate = "OFF";
					break;
				case BluetoothAdapter.STATE_TURNING_OFF:
					nstate = "TURNING OFF";
					break;
				case BluetoothAdapter.STATE_TURNING_ON:
					nstate = "TURNING ON";
					break;
				}
				String msg = "Previous State : " + pstate + " New State : "
						+ nstate;
				Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG)
						.show();
			}

		}
	};

	public void showList(View view) {

		ArrayList<String> list = new ArrayList<String>();
		Set<BluetoothDevice> pairedDevice = BA.getBondedDevices();

		for (BluetoothDevice device : pairedDevice) {
			list.add("Device_Name: " + device.getName() + "\n" + "Device_Address: " + device.getAddress());
			Log.w("Device", device.getName());
		}

		ListView lv = (ListView) findViewById(R.id.listView1);

		lv.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list));

	}
 
	public void turnOn(View view) {

		if (!BA.isEnabled()) {
			Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(turnOn, 0);
		} else
			BA.disable();
	}

	

	
}
