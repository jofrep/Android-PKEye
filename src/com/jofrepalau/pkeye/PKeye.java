package com.jofrepalau.pkeye;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.KeyStore;
import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class PKeye extends Activity {
  EditText mEditText_password = null;
  EditText mEditText_keystore = null;
  TextView mTextView_Welcome = null;
  TextView mTextView_WelcomeLabel = null;
  TextView mTextView_LabelKeystore = null;
  TextView mTextView_LabelPassword = null;
  TextView mTextView_Simple = null;
  TextView mTextView_Extended = null;
  TextView mTextView_Export = null;
  TextView mTextView_Import = null;
  TableLayout mTableLayout = null;
  TableRow mRowA = null;
  TableRow mRowB = null;  
  TableRow mRowC = null;  
  TableRow mRowD = null;  
  TableRow mRowE = null;  
  TableRow mRowF = null;  
  TableRow mRowG = null;
  Button mButton_dial = null;
  Button mButton_Extended = null;
  Button mButton_Export = null;
  Button mButton_Import = null;
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mTableLayout = new TableLayout(this);
//    mTableLayout.setBackgroundColor(Color.argb(255,153, 0, 0));
    mTableLayout.setBackgroundColor(Color.argb(255,170, 14, 53));
    mTableLayout.setGravity(Gravity.CENTER_VERTICAL);
    mTableLayout.setVerticalScrollBarEnabled(true);
    mRowA = new TableRow(this);
    mRowB = new TableRow(this);
    mRowC = new TableRow(this);
    mRowD = new TableRow(this);
    mRowE = new TableRow(this);
    mRowF = new TableRow(this);
    mRowG = new TableRow(this);
    
    mTableLayout.addView(mRowA);
    mTextView_WelcomeLabel = new TextView(this);
    mTextView_WelcomeLabel.setTextColor(Color.argb(255 ,255, 255, 255));
    mTextView_WelcomeLabel.setText(" Information: ");
    mRowA.addView(mTextView_WelcomeLabel);
    
    mTextView_Welcome = new TextView(this);
    mTextView_Welcome.setWidth(50);
    mTextView_Welcome.setText("Configure here the location and password of your KeyStore. \nIf you want to have a look at the Android embedded KeyStore use the default values.");
    mRowA.addView(mTextView_Welcome);
   
    mTableLayout.addView(mRowB);
    mTextView_LabelKeystore = new TextView(this);
    mTextView_LabelKeystore.setTextColor(Color.argb(255 ,255, 255, 255));
    mTextView_LabelKeystore.setText(" Keystore:");
    mRowB.addView(mTextView_LabelKeystore);

    mEditText_keystore = new EditText(this);
    mEditText_keystore.setText("/system/etc/security/cacerts.bks");
    mEditText_keystore.setTextSize(12);
    mRowB.addView(mEditText_keystore);   
    
    mTableLayout.addView(mRowC);
    mTextView_LabelKeystore = new TextView(this);
    mTextView_LabelKeystore.setTextColor(Color.argb(255 ,255, 255, 255));
    mTextView_LabelKeystore.setText(" Password:");
    mRowC.addView(mTextView_LabelKeystore);

    mEditText_password = new EditText(this);
    mEditText_password.setText("changeit");
    mEditText_password.setTextSize(12);
    mRowC.addView(mEditText_password);   

    mTableLayout.addView(mRowD);
    mButton_dial = new Button(this);
    mButton_dial.setTextColor(Color.argb(255,153, 0, 0));
    mButton_dial.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
    mButton_dial.setText("Simple");
    mRowD.addView(mButton_dial);

    mTextView_Simple = new TextView(this);
    mTextView_Simple.setText(" List of Root CA Subject Names");
    mRowD.addView(mTextView_Simple);

    mButton_dial.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        readRootCAs();
      }
    });
    
    mTableLayout.addView(mRowE);
    mButton_Extended = new Button(this);
    mButton_Extended.setText("Extended");
    mButton_Extended.setTextColor(Color.argb(255,153, 0, 0));
    mButton_Extended.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
    mRowE.addView(mButton_Extended);

    mTextView_Extended = new TextView(this);
    mTextView_Extended.setText(" Root CAs extended information\n (could take several seconds!!!)");
    mRowE.addView(mTextView_Extended);

    mButton_Extended.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
    	readRootCAsExtended();      	
      }
    });

    mTableLayout.addView(mRowF);
    mButton_Export = new Button(this);
    mButton_Export.setText("Export");
    mButton_Export.setTextColor(Color.argb(255,153, 0, 0));
    mButton_Export.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
    mRowF.addView(mButton_Export);

    mTextView_Export = new TextView(this);
    mTextView_Export.setText(" Export Root CAs to SD card");
    mRowF.addView(mTextView_Export);

    mButton_Export.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        saveRootCAs();
      }
    });
    
    mTableLayout.addView(mRowG);
    mButton_Import = new Button(this);
    mButton_Import.setText("Import");
    mButton_Import.setTextColor(Color.argb(255,153, 0, 0));
    mButton_Import.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
//  mRowG.addView(mButton_Import);

    mTextView_Import = new TextView(this);
    mTextView_Import.setText(" Import CA");
//  mRowG.addView(mTextView_Import);

    mButton_Import.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
    	// Button is not enabled
        importRootCA();
      }
    });   
    
    setContentView(mTableLayout);
  }

  public boolean onKeyDown(int keyCode, KeyEvent event) {
      if (keyCode == KeyEvent.KEYCODE_BACK) {
    	  this.finish();
      }
	  if (keyCode == KeyEvent.KEYCODE_CALL) {
    	readRootCAs();
      return true;
    }
    return false;
  }

  public void readRootCAs(){
	  if((mEditText_password!=null)&&((mEditText_keystore!=null))){
		  try {	 
	    	
	   	    String ksFile = mEditText_keystore.getText().toString();
	  		char[] ksPass = mEditText_password.getText().toString().toCharArray();
	  		Integer i = 0;
	  		Enumeration calist;
	    	FileInputStream fis = new FileInputStream(ksFile);
	    	KeyStore ks = KeyStore.getInstance("BKS");
	    	ks.load(fis, ksPass);
	    	i = ks.size();
	    	calist = ks.aliases();
	    	String canames = "Results (" + i + " elements found):\n";
	    	Certificate cert;
	    	while (calist.hasMoreElements()) {
	    		String alias = (String) calist.nextElement();
	    	    cert = ks.getCertificate(alias);
	    	    Principal issuerdn = ((X509Certificate) cert).getIssuerDN();
	    	    System.out.println(alias + " - " + issuerdn);
	    	    canames += "----------------------------------\n" + issuerdn + "\n";
	    	}
	    	AlertDialog.Builder msg = new AlertDialog.Builder(this);
	    	msg.setMessage(canames);
	    	AlertDialog alert = msg.create();
	        alert.setTitle("KeyStore:");
	    	alert.show();
	      } catch (Exception e) {
	    	  AlertDialog.Builder msg = new AlertDialog.Builder(this);
	    	  msg.setMessage("Check that the password or location are correct.\nError message: " + e.getMessage());
	    	  AlertDialog alert = msg.create();
	    	  alert.setTitle("Error!");
	    	  alert.setIcon(R.drawable.icon);
	    	  alert.show();
	          e.printStackTrace();
	      }
	    }//if
  	} 
  public void readRootCAsExtended(){
	  if((mEditText_password!=null)&&((mEditText_keystore!=null))){
	      try {	    	  
	   	    String ksFile = mEditText_keystore.getText().toString();
	  		char[] ksPass = mEditText_password.getText().toString().toCharArray();
	  		Integer i = 0;
	  		Enumeration calist;
	    	FileInputStream fis = new FileInputStream(ksFile);
	    	KeyStore ks = KeyStore.getInstance("BKS");
	    	ks.load(fis, ksPass);
	    	i = ks.size();
	    	calist = ks.aliases();
	    	String canames = "Results (" + i + " elements found):\n";
	    	//Certificate cert;
	    	X509Certificate cert;
	    	
	    	while (calist.hasMoreElements()) {
	    		String alias = (String) calist.nextElement();
	    	    cert = (X509Certificate)ks.getCertificate(alias);
	    	    
	    	    // Principal issuerdn = ((X509Certificate) cert).getIssuerDN();
	    	    // System.out.println(alias + " - " + issuerdn);
	    	    //canames += "----------------------------------\n" + issuerdn + "\n";

	    	    String certinfo = cert.toString();
	    	    
	    	    //String dn = cert.getIssuerDN().toString();
	    	    //String notafeter = cert.getNotAfter().toString();
	    	    //String notbefore = cert.getNotBefore().toString();
	    	    //String serialnumber = cert.getSerialNumber().toString();
	    	    //String algsig = cert.getSigAlgName().toString();
	    	    
	    	    //String certinfo = "DN: " + dn + "\nSerial: " + serialnumber + "\nNot before:" + notbefore +"\nNot after:" + notafeter + "\nAlg:" + algsig +"\n";
	    	    System.out.println(alias + " - " + certinfo);
	    	    canames += "----------------------------------\n" + certinfo + "\n";
	    	} 
	    	
		  	AlertDialog.Builder msg = new AlertDialog.Builder(this);
	    	msg.setMessage(canames);
	    	AlertDialog alert = msg.create();
	        alert.setTitle("KeyStore:");
	    	alert.show();
	      } catch (Exception e) {
	    	  AlertDialog.Builder msg = new AlertDialog.Builder(this);
	    	  msg.setMessage("Check that the password or location are correct.\nError message: " + e.getMessage());
	    	  AlertDialog alert = msg.create();
	    	  alert.setTitle("Error!");
	    	  alert.setIcon(R.drawable.icon);
	    	  alert.show();
	          e.printStackTrace();
	      }
	    }//if
  	} 
   
  public void saveRootCAs(){
	  if((mEditText_password!=null)&&((mEditText_keystore!=null))){
	      try {	   	    
	    	String state = Environment.getExternalStorageState();
	    	if (Environment.MEDIA_MOUNTED.equals(state)) {
	    	      // We can read and write the media
	    	} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
	    	      // We can only read the media
		    	  AlertDialog.Builder msg = new AlertDialog.Builder(this);
		    	  msg.setMessage("Sorry, your SD card is mounted as ready only. We can« export the Root CAs if we can« write there");
		    	  AlertDialog alert = msg.create();
		          alert.setTitle("Error:");
		    	  alert.show();	    	      
	    	      return;
	    	} else {
	    	      // Something else is wrong. It may be one of many other states, but all we need
	    	      //  to know is we can neither read nor write
		    	  AlertDialog.Builder msg = new AlertDialog.Builder(this);
		    	  msg.setMessage("Sorry, I could not find an SD card where to place the exported Root CAs");
		    	  AlertDialog alert = msg.create();
		          alert.setTitle("Error:");
		    	  alert.show();	    	      
	    	      return;	    	
	     	}
	    	String ksFile = mEditText_keystore.getText().toString();
	  		char[] ksPass = mEditText_password.getText().toString().toCharArray();
	  		Enumeration calist;
	    	FileInputStream fis = new FileInputStream(ksFile);
	    	KeyStore ks = KeyStore.getInstance("BKS");
	    	ks.load(fis, ksPass);
	    	calist = ks.aliases();
	    	Certificate cert;
	    	// create a File object for the parent directory
    	    File rootcasirectory = new File(Environment.getExternalStorageDirectory() + "/ExportedRootCAs/");
    	    // have the object build the directory structure, if needed.
    	    rootcasirectory.mkdirs();
    	    // create a File object for the output file

	    	while (calist.hasMoreElements()) {
	    		String alias = (String) calist.nextElement();
	    	    cert = ks.getCertificate(alias);
	    	    File file = new File(rootcasirectory, alias + ".cer");	    	    
	    	    System.out.println("Save " + alias + " in " + file.getAbsolutePath());
	    	    try {
	    	        OutputStream os = new FileOutputStream(file);
	    	        os.write(cert.getEncoded());
	    	    } catch (IOException e) {
	    	        // Unable to create file, likely because external storage is
	    	        // not currently mounted.
	    	    	System.out.println("ExternalStorage: Error writing " + file + e.getMessage());
	    	    }
	    	}    
	    	AlertDialog.Builder msg = new AlertDialog.Builder(this);
	    	msg.setMessage("Root CA exported in" + rootcasirectory);
	    	AlertDialog alert = msg.create();
	        alert.setTitle("KeyStore:");
	    	alert.show();
	      } catch (Exception e) {
	    	  AlertDialog.Builder msg = new AlertDialog.Builder(this);
	    	  msg.setMessage("Check that the password or location are correct.\nError message: " + e.getMessage());
	    	  AlertDialog alert = msg.create();
	    	  alert.setTitle("Error!");
	    	  alert.setIcon(R.drawable.icon);
	    	  alert.show();
	          e.printStackTrace();
	      }
	    }//if
  	}   


  public void importRootCA(){
	  if((mEditText_password!=null)&&((mEditText_keystore!=null))){
	      try {	   	    
	    	String state = Environment.getExternalStorageState();
	    	if (Environment.MEDIA_MOUNTED.equals(state)) {
	    	      // We can read and write the media
	    	} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
	    	      // We can only read the media
			} else {
	    	      // Something else is wrong. It may be one of many other states, but all we need
	    	      //  to know is we can neither read nor write
		    	  AlertDialog.Builder msg = new AlertDialog.Builder(this);
		    	  msg.setMessage("Sorry, I could not find an SD card where to read Root CAs");
		    	  AlertDialog alert = msg.create();
		          alert.setTitle("Error:");
		    	  alert.show();	    	      
	    	      return;	    	
	     	}
	    	String ksFile = mEditText_keystore.getText().toString();
	  		char[] ksPass = mEditText_password.getText().toString().toCharArray();
	  		
    	    String cacert = Environment.getExternalStorageDirectory() + "/ExportedRootCAs/0.cer";
    	    String alias = "certin";
    	    CertificateFactory cf = CertificateFactory.getInstance("X.509");
    	    FileInputStream in1 = new FileInputStream(cacert);
    	    //java.security.cert.Certificate cac = cf.generateCertificate(in1);
    	    Certificate cac = cf.generateCertificate(in1);
    	    in1.close();
    	    
	  	    // Create an empty keystore object 
	  		KeyStore keystoretmp = KeyStore.getInstance(KeyStore.getDefaultType()); 
	  		// Load the real keystore contents 
	  		FileInputStream in = new FileInputStream(ksFile); 
	  		keystoretmp.load(in, ksPass); 
		    in.close(); 
		    // Add the certificate 
		    keystoretmp.setCertificateEntry(alias, cac); 
		    // Save the new keystore contents 
		    FileOutputStream out = new FileOutputStream(ksFile); 
		    keystoretmp.store(out, ksPass); 
		    out.close(); 	    
    	        	    
	    	AlertDialog.Builder msg = new AlertDialog.Builder(this);
	    	msg.setMessage("Root CA imported!");
	    	AlertDialog alert = msg.create();
	        alert.setTitle("KeyStore:");
	    	alert.show();
	      } catch (Exception e) {
	    	  AlertDialog.Builder msg = new AlertDialog.Builder(this);
	    	  msg.setMessage("Check that the password or location are correct.\nError message: " + e.getMessage());
	    	  AlertDialog alert = msg.create();
	    	  alert.setTitle("Error!");
	    	  alert.setIcon(R.drawable.icon);
	    	  alert.show();
	          e.printStackTrace();
	      }
	    }//if
  	}
}












