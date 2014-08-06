package com.kle.hospitalmanagementgame;

import com.kle.hospitalmanagementgame.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UserSurveyActivity extends Activity{

	Activity mActivity;
	EditText userIdET;
	EditText conditionET;
	EditText trialET;
	
	Button proceedButton;
	ImageView settingsButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_survey);

		mActivity = this;
		
		userIdET = (EditText) findViewById(R.id.user_id_ET);
		conditionET = (EditText) findViewById(R.id.condition_ET);
		trialET = (EditText) findViewById(R.id.trial_ET);
		
		proceedButton = (Button) findViewById(R.id.proceed_button);
		
		proceedButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String userID = userIdET.getText().toString();
				String condition = conditionET.getText().toString();
				String trial = trialET.getText().toString();
				
				if(userID.equals("")){
					userIdET.setError("Can't be blank");
					return;
				}
				
				if(condition.equals("")){
					conditionET.setError("Can't be blank");
					return;
				}
				
				if(trial.equals("")){
					trialET.setError("Can't be blank");
					return;
				}
				
				
				Intent intent = new Intent(mActivity, MainActivity.class);
				intent.putExtra("user_id", userID);
				intent.putExtra("condition", condition);
				intent.putExtra("trial", trial);
				startActivity(intent);
			}
		});
		
		settingsButton = (ImageView) findViewById(R.id.settings_button);
		settingsButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mActivity, StartOptionsActivity.class);
				startActivity(intent);
			}
		});
		
	}
}
