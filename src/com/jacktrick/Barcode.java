package com.jacktrick;

import com.jacktrick.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Barcode extends Activity {
	String shopItems[] = new String[100];
	Intent intent = new Intent("com.google.zxing.client.android.SCAN");
	String youBought = "You bought:\n";
	int arrIndex = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		findViewById(R.id.button1).setOnClickListener(scan);
		findViewById(R.id.button2).setOnClickListener(buy);
	}
	
	public Button.OnClickListener scan = new Button.OnClickListener() {
	    public void onClick(View v) {
	        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
	        intent.setPackage("com.google.zxing.client.android");
	        intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
	        startActivityForResult(intent, 0);
	    }
	};

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
	    if (requestCode == 0) {
	    	final AlertDialog.Builder alertBox = new AlertDialog.Builder(this);
	        if (resultCode == RESULT_OK) {
	            String contents = intent.getStringExtra("SCAN_RESULT");
				shopItems[arrIndex] = contents;
				arrIndex++;
	        } else if (resultCode == RESULT_CANCELED) {
	        	alertBox.setMessage("Scan cancelled.");
	        	alertBox.show();      	
	        } else {
	        	alertBox.setMessage("Catastrophic failure. I don't even know what happened.");
	        }
	    }
	}
	
	public Button.OnClickListener buy = new Button.OnClickListener(){
		public void onClick(View v) {
			final AlertDialog.Builder alertBox = new AlertDialog.Builder(Barcode.this);
			for (int i = 0; i < arrIndex; i++) {
				youBought = (youBought + shopItems[i] + "\n");
			}
			alertBox.setMessage(youBought);
			alertBox.show();
			youBought = "You bought:\n";
		}
	};
}