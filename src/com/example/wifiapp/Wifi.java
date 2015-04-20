package com.example.wifiapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class Wifi extends Activity {
	
	public WifiManager wifi;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.networklist);
		wifi = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
		Button scanlist = (Button)findViewById(R.id.list);
		scanlist.setText("Active Networks");
		Button configuredlist = (Button)findViewById(R.id.turnOn);
		configuredlist.setText("Configured Networks");
	}
	
	public void showList(View view){
		
		List<ScanResult> list =  wifi.getScanResults();
		ArrayList<String> networks = new ArrayList<String>();
		for(ScanResult wc : list){
			String msg = "SSID: " + wc.SSID + "\n" + "MAC_Address: "  + wc.BSSID + "\n" + "Network_Strength: " + wc.level ;
			networks.add(msg);
		}
		
		ListView lv = (ListView)findViewById(R.id.listView1);
		lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,networks));
	}
	
	public void turnOn(View view){
		
		List<WifiConfiguration> list = wifi.getConfiguredNetworks();
		ArrayList<String> networks = new ArrayList<String>();
		for(WifiConfiguration wc : list){
			String msg = "SSID: " + wc.SSID + "\n" + "Netowrk_ID: " + wc.networkId ;
			networks.add(msg);
		}
		
		ListView lv = (ListView)findViewById(R.id.listView1);
		lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,networks));
	}

	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.menu_bluetooth:
			Intent i = new Intent(this , MainActivity.class);
			startActivity(i);
			return true;
		case R.id.menu_wifi:
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

}
