package com.normtronics.tuner;

import java.io.File;
import java.io.IOException;

import org.puredata.android.io.AudioParameters;
import org.puredata.android.io.PdAudio;
import org.puredata.android.utils.PdUiDispatcher;
import org.puredata.core.PdBase;
import org.puredata.core.utils.IoUtils;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
	
	private PdUiDispatcher dispatcher;
	private static final String TAG = "GuitarTuner";
	private Button eButton;
	private Button aButton;
	private Button dButton;
	private Button gButton;
	private Button bButton;
	private Button loweButton;
	
	
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		eButton = (Button) findViewById(R.id.e_button);
		eButton.setOnClickListener(this);
		aButton = (Button) findViewById(R.id.a_button);
		aButton.setOnClickListener(this);
		
		try{
			initPd();
			loadPatch();
			
		}
		catch(IOException e){
			Log.e(TAG, e.toString());
			finish();
		}
	}
	
	private void initPd() throws IOException{
		int sampleRate = AudioParameters.suggestSampleRate();
		PdAudio.initAudio(sampleRate, 0, 2, 8, true);
		dispatcher = new PdUiDispatcher();
		PdBase.setReceiver(dispatcher);
	}
	
	private void loadPatch() throws IOException{
		File dir = getFilesDir();
		IoUtils.extractZipResource(getResources().openRawResource(R.raw.tuner) , dir, true);
		File patchFile = new File(dir, "tuner.pd");
		PdBase.openPatch(patchFile.getAbsolutePath());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	protected void onResume(){
		super.onResume();
		PdAudio.startAudio(this);
	}
	
	protected void onPause(){
		super.onPause();
		PdAudio.stopAudio();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
