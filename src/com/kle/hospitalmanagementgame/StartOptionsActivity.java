package com.kle.hospitalmanagementgame;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

public class StartOptionsActivity extends Activity{

	Activity mActivity;
	static EditText startAPatientET;
	static EditText startBPatientET;
	static EditText startDoctorsET;
	static EditText startNursesET;
	static EditText startSurgeonsET;
	static EditText totalTimeET;
	static EditText startOtherDoctorsET;
	static EditText startOtherNursesET;
	static EditText startOtherSurgeonsET;
	static EditText patientTimeET;
	static EditText NHStartAPatientET;
	static EditText NHStartBPatientET;
	
	Button saveButton;
	ImageView settingsButton;
	
	static Spinner cooperationSpinner;
	static Spinner patientFlowPatternSpinner;
	static Spinner practiceSpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_options);
		
		InputMethodManager im = (InputMethodManager) this.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		im.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

		mActivity = this;
		
		startAPatientET = (EditText) findViewById(R.id.startPatientAET);
		startBPatientET = (EditText) findViewById(R.id.startPatientBET);
		startDoctorsET = (EditText) findViewById(R.id.startDoctorsET);
		startNursesET = (EditText) findViewById(R.id.startNursesET);
		startSurgeonsET = (EditText) findViewById(R.id.startSurgeonsET);
		totalTimeET = (EditText) findViewById(R.id.totalTimeET);
		startOtherDoctorsET = (EditText) findViewById(R.id.startOtherDoctorsET);
		startOtherNursesET = (EditText) findViewById(R.id.startOtherNursesET);
		startOtherSurgeonsET = (EditText) findViewById(R.id.startOtherSurgeonsET);
		patientTimeET = (EditText) findViewById(R.id.patientATimeET);
		NHStartAPatientET = (EditText) findViewById(R.id.NHStartPatientAET);
		NHStartBPatientET = (EditText) findViewById(R.id.NHStartPatientBET);
		
		startAPatientET.setText(HospitalPeople.patientA +"");
		startBPatientET.setText(HospitalPeople.patientB +"");
		startDoctorsET.setText(HospitalPeople.doctors+"");
		startNursesET.setText(HospitalPeople.nurses+"");
		startSurgeonsET.setText(HospitalPeople.surgeons+"");
		totalTimeET.setText(MainActivity.startTimeMilliseconds/1000+"");
		startOtherDoctorsET.setText(HospitalPeople.npcDoctors+"");
		startOtherNursesET.setText(HospitalPeople.npcNurses+"");
		startOtherSurgeonsET.setText(HospitalPeople.npcSurgeons+"");
		patientTimeET.setText(MainActivity.patientTimeLeftMilliseconds/1000+"");
		NHStartAPatientET.setText(HospitalPeople.npcPatientA+"");
		NHStartBPatientET.setText(HospitalPeople.npcPatientB+"");
		
		cooperationSpinner = (Spinner) findViewById(R.id.cooperation_spinner);
		ArrayList<String> list = new ArrayList<String>();
		list.add("High Cooperation");
		list.add("Low Cooperation");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		cooperationSpinner.setAdapter(dataAdapter);
		
		patientFlowPatternSpinner = (Spinner) findViewById(R.id.patient_flow_pattern_spinner);
		ArrayList<String> patternSpeed = new ArrayList<String>();
		patternSpeed.add("Early Slow");
		patternSpeed.add("Early Fast");
		ArrayAdapter<String> data2Adapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_spinner_item, patternSpeed);
		data2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		patientFlowPatternSpinner.setAdapter(data2Adapter);
		
		practiceSpinner = (Spinner) findViewById(R.id.practice_spinner);
		ArrayList<String> practiceList = new ArrayList<String>();
		practiceList.add("Normal Mode");
		practiceList.add("Practice Mode");
		ArrayAdapter<String> data3Adapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_spinner_item, practiceList);
		data3Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		practiceSpinner.setAdapter(data3Adapter);
		
		practiceSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int index, long arg3) {
				if(index == 0){
					MainActivity.startTimeMilliseconds = 480000;
				}else{
					MainActivity.startTimeMilliseconds = 120000;
					cooperationSpinner.setSelection(0);
				}
				
				startAPatientET.setText(HospitalPeople.patientA+"");
				startBPatientET.setText(HospitalPeople.patientB+"");
				startDoctorsET.setText(HospitalPeople.doctors+"");
				startNursesET.setText(HospitalPeople.nurses+"");
				startSurgeonsET.setText(HospitalPeople.surgeons+"");
				totalTimeET.setText(MainActivity.startTimeMilliseconds/1000+"");
				startOtherDoctorsET.setText(HospitalPeople.npcDoctors+"");
				startOtherNursesET.setText(HospitalPeople.npcNurses+"");
				startOtherSurgeonsET.setText(HospitalPeople.npcSurgeons+"");
				patientTimeET.setText(MainActivity.patientTimeLeftMilliseconds/1000+"");
				NHStartAPatientET.setText(HospitalPeople.npcPatientA+"");
				NHStartBPatientET.setText(HospitalPeople.npcPatientB+"");
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		
		saveButton = (Button) findViewById(R.id.save_button);
		
		saveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(startAPatientET.getText().toString().equals("")){
					startAPatientET.setError("Can't be blank");
					return;
				}
				
				if(startBPatientET.getText().toString().equals("")){
					startBPatientET.setError("Can't be blank");
					return;
				}

				if(startDoctorsET.getText().toString().equals("")){
					startDoctorsET.setError("Can't be blank");
					return;
				}
				
				if(startNursesET.getText().toString().equals("")){
					startNursesET.setError("Can't be blank");
					return;
				}
				
				if(startSurgeonsET.getText().toString().equals("")){
					startSurgeonsET.setError("Can't be blank");
					return;
				}
				
				if(totalTimeET.getText().toString().equals("")){
					totalTimeET.setError("Can't be blank");
					return;
				}
				
				if(startOtherDoctorsET.getText().toString().equals("")){
					startOtherDoctorsET.setError("Can't be blank");
					return;
				}
				
				if(startOtherNursesET.getText().toString().equals("")){
					startOtherNursesET.setError("Can't be blank");
					return;
				}
				
				if(startOtherSurgeonsET.getText().toString().equals("")){
					startOtherSurgeonsET.setError("Can't be blank");
					return;
				}
				
				if(patientTimeET.getText().toString().equals("")){
					patientTimeET.setError("Can't be blank");
					return;
				}
				
				if(NHStartAPatientET.getText().toString().equals("")){
					NHStartAPatientET.setError("Can't be blank");
					return;
				}
				
				if(NHStartBPatientET.getText().toString().equals("")){
					NHStartBPatientET.setError("Can't be blank");
					return;
				}
				
				HospitalPeople.patientA = Integer.parseInt(startAPatientET.getText().toString());
				HospitalPeople.patientB = Integer.parseInt(startBPatientET.getText().toString());
				HospitalPeople.doctors = Integer.parseInt(startDoctorsET.getText().toString());
				HospitalPeople.nurses = Integer.parseInt(startNursesET.getText().toString());
				HospitalPeople.surgeons = Integer.parseInt(startSurgeonsET.getText().toString());
				HospitalPeople.npcDoctors = Integer.parseInt(startOtherDoctorsET.getText().toString());
				HospitalPeople.npcNurses = Integer.parseInt(startOtherNursesET.getText().toString());
				HospitalPeople.npcDoctors = Integer.parseInt(startOtherSurgeonsET.getText().toString());
				HospitalPeople.npcPatientA = Integer.parseInt(NHStartAPatientET.getText().toString());
				HospitalPeople.npcPatientB = Integer.parseInt(NHStartBPatientET.getText().toString());
				MainActivity.startTimeMilliseconds = Integer.parseInt(totalTimeET.getText().toString())*1000;
				MainActivity.patientTimeLeftMilliseconds = Integer.parseInt(patientTimeET.getText().toString()) * 1000;
				
				if(cooperationSpinner.getSelectedItemPosition() == 0)
					CooperationLevel.highCooperation = true;
				else
					CooperationLevel.highCooperation = false;
				
				if(patientFlowPatternSpinner.getSelectedItemPosition() == 0)
					MainActivity.earlySlowPattern = true;
				else
					MainActivity.earlySlowPattern = false;
				
				if(practiceSpinner.getSelectedItemPosition() == 0)
					MainActivity.practiceRound = false;
				else
					MainActivity.practiceRound = true;
				
				Intent intent = new Intent(mActivity, UserSurveyActivity.class);
				startActivity(intent);
				mActivity.finish();
				
			}
		});
		
	}
}
