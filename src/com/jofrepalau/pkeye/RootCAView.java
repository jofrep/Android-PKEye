package com.jofrepalau.pkeye;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class RootCAView extends Activity{
	TableLayout mTableLayout = null;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    mTableLayout = new TableLayout(this);
	    mTableLayout.setBackgroundColor(Color.argb(255,0, 0, 0));
	    mTableLayout.setGravity(Gravity.CENTER_VERTICAL);
	    System.out.println("RootCAView created");

	    setContentView(mTableLayout);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	  this.finish();
	    }
	    return false;
	}

}
