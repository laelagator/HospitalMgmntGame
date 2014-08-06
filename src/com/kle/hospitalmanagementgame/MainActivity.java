package com.kle.hospitalmanagementgame;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import com.kle.hospitalmanagementgame.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity{

	static Activity mActivity;

	//set initial values
	public static int numberOfPatientAs = 1;
	public static int numberOfPatientBs = 0;
	public static int numberOfDoctors = 3;
	public static int numberOfNurses = 3;
	public static int numberOfSurgeons = 3;
	public static int otherNumberOfDoctors = 3;
	public static int otherNumberOfNurses = 3;
	public static int otherNumberOfSurgeons = 3;
	public static int otherNumberOfPatientAs = 0;
	public static int otherNumberOfPatientBs = 1;
	public static int startTimeMilliseconds = 480000;
	public static int patientTimeLeftMilliseconds = 60000;
	public static int score = 0;
	public static int otherScore = 0;

	int totalMissedPatients = 0;
	int NHTotalMissedPatients = 0;

	int otherNumOfRooms = 0;

	int totalTimeLeftInMilliseconds;

	int numPatientsForHighQuintuplet = 8;
	int numPatientsForMediumQuintuplet = 5;
	int numPatientsForLowQuintuplet = 2;

	public static boolean highCooperation = true;
	public static boolean earlySlowPattern = true;
	public static boolean practiceRound = false;

	boolean interruptOn = false;

	//all the layouts in side bar
	RelativeLayout containerMain;

	//main timer
	TextView mainTimeLeft;

	//score
	TextView scoreTextView;
	TextView otherScoreTextView;

	//containerMain Views
	TextView doctorsValue1;
	TextView nursesValue1;
	TextView surgeonsValue1;
	TextView otherDoctorsValue;
	TextView otherNursesValue;
	TextView otherSurgeonsValue;
	Button assignPatientToRoomButton;
	Button assignResourceToRoomButton;
	Button requestResourceButton;

	ImageView xButton;

	//containerPatient Views
	Button patientAButton;
	Button patientBButton;

	//containerResource Views
	Button doctorsButton;
	Button nursesButton;
	Button surgeonsButton;

	//containerRequest Views
	Button doctorsButton2;
	Button nursesButton2;
	Button surgeonsButton2;

	//Room1 Views
	RelativeLayout room1Main;
	TextView vacantText1;
	Button collectButton1;
	RelativeLayout room1Layout;
	TextView patientText1;
	TextView doctorSurgeonText1;
	TextView nurseText1;
	TextView timeLeftText1;

	//Room2 Views
	RelativeLayout room2Main;
	TextView vacantText2;
	Button collectButton2;
	RelativeLayout room2Layout;
	TextView patientText2;
	TextView doctorSurgeonText2;
	TextView nurseText2;
	TextView timeLeftText2;

	//Room3 Views
	RelativeLayout room3Main;
	TextView vacantText3;
	Button collectButton3;
	RelativeLayout room3Layout;
	TextView patientText3;
	TextView doctorSurgeonText3;
	TextView nurseText3;
	TextView timeLeftText3;

	//Room4 Views
	RelativeLayout room4Main;
	TextView vacantText4;
	Button collectButton4;
	RelativeLayout room4Layout;
	TextView patientText4;
	TextView doctorSurgeonText4;
	TextView nurseText4;
	TextView timeLeftText4;

	//Room5 Views
	RelativeLayout room5Main;
	TextView vacantText5;
	Button collectButton5;
	RelativeLayout room5Layout;
	TextView patientText5;
	TextView doctorSurgeonText5;
	TextView nurseText5;
	TextView timeLeftText5;

	//Room6 Views
	RelativeLayout room6Main;
	TextView vacantText6;
	Button collectButton6;
	RelativeLayout room6Layout;
	TextView patientText6;
	TextView doctorSurgeonText6;
	TextView nurseText6;
	TextView timeLeftText6;

	Room[] roomList = new Room[6];

	static String userID;
	static String condition;
	static String trial;

	ImageView circleA1;
	ImageView circleA2;
	ImageView circleA3;
	ImageView circleA4;
	ImageView circleA5;
	ImageView circleA6;

	ImageView circleB1;
	ImageView circleB2;
	ImageView circleB3;
	ImageView circleB4;
	ImageView circleB5;
	ImageView circleB6;

	ImageView circleOtherA;
	ImageView circleOtherB;

	//Interruption Layout
	RelativeLayout interruptionLayout;
	TextView interruptionText;
	Button acceptRequest;
	Button denyRequest;

	ImageView exitButton;

	boolean start = false;

	//Disable Back Button
	@Override
	public void onBackPressed() {
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mActivity = this;

		Bundle bundle = getIntent().getExtras();
		userID = bundle.getString("user_id");
		condition = bundle.getString("condition");
		trial = bundle.getString("trial");

		File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), userID+"_"+condition+"_"+trial+".csv");
		file.delete();
		writeStringAsFile("InputType,InputParameter,TimeInput,PlayerNurse,PlayerDoctor,PlayerSurgeon,AgentNurses,AgentDoctors,AgentSurgeons,PlayerQueueA,PlayerQueueB,AgentQueueA,AgentQueueB,PlayerScore,AgentScore,GroupScore");

		containerMain = (RelativeLayout) findViewById(R.id.container_main);

		mainTimeLeft = (TextView) findViewById(R.id.timeLeftValue);

		scoreTextView = (TextView) findViewById(R.id.score_value);
		otherScoreTextView = (TextView) findViewById(R.id.nh_score_value);

		//containerMain Views
		doctorsValue1 = (TextView) findViewById(R.id.doctorsValue);
		nursesValue1 = (TextView) findViewById(R.id.nursesValue);
		surgeonsValue1 = (TextView) findViewById(R.id.surgeonValue);
		otherDoctorsValue = (TextView) findViewById(R.id.otherDoctorsValue);
		otherNursesValue = (TextView) findViewById(R.id.otherNursesValue);
		otherSurgeonsValue = (TextView) findViewById(R.id.otherSurgeonValue);
		assignPatientToRoomButton = (Button) findViewById(R.id.assign_patient_button);
		assignResourceToRoomButton = (Button) findViewById(R.id.assign_resource_button);
		requestResourceButton = (Button) findViewById(R.id.request_resource_button);

		//circles
		circleA1 = (ImageView) findViewById(R.id.patientACircle1);
		circleA2 = (ImageView) findViewById(R.id.patientACircle2);
		circleA3 = (ImageView) findViewById(R.id.patientACircle3);
		circleA4 = (ImageView) findViewById(R.id.patientACircle4);
		circleA5 = (ImageView) findViewById(R.id.patientACircle5);
		circleA6 = (ImageView) findViewById(R.id.patientACircle6);

		circleB1 = (ImageView) findViewById(R.id.patientBCircle1);
		circleB2 = (ImageView) findViewById(R.id.patientBCircle2);
		circleB3 = (ImageView) findViewById(R.id.patientBCircle3);
		circleB4 = (ImageView) findViewById(R.id.patientBCircle4);
		circleB5 = (ImageView) findViewById(R.id.patientBCircle5);
		circleB6 = (ImageView) findViewById(R.id.patientBCircle6);

		circleOtherA = (ImageView) findViewById(R.id.NHACircle);
		circleOtherB = (ImageView) findViewById(R.id.NHBCircle);

		xButton = (ImageView) findViewById(R.id.x);

		updateSideBarMain();

		assignPatientToRoomButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!interruptOn){
					hideMainBottomButtons();
					patientAButton.setVisibility(View.VISIBLE);
					patientBButton.setVisibility(View.VISIBLE);
					xButton.setVisibility(View.VISIBLE);
				}
			}
		});

		assignResourceToRoomButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!interruptOn){
					hideMainBottomButtons();
					doctorsButton.setVisibility(View.VISIBLE);
					nursesButton.setVisibility(View.VISIBLE);
					surgeonsButton.setVisibility(View.VISIBLE);
					xButton.setVisibility(View.VISIBLE);
				}
			}
		});

		requestResourceButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!interruptOn){
					hideMainBottomButtons();
					doctorsButton2.setVisibility(View.VISIBLE);
					nursesButton2.setVisibility(View.VISIBLE);
					surgeonsButton2.setVisibility(View.VISIBLE);
					xButton.setVisibility(View.VISIBLE);
				}
			}
		});

		//containerPatient Views
		patientAButton = (Button) findViewById(R.id.patient_a_button);
		patientBButton = (Button) findViewById(R.id.patient_b_button);

		patientAButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!interruptOn){
					if(numberOfPatientAs > 0){
						colorizeRoomForPatients();
						patientAButton.setBackgroundResource(R.drawable.green_button_selected_button);
						patientBButton.setEnabled(false);
						patientBButton.setTextColor(getResources().getColor(R.color.gray));
						setRoomClicksForPatientA();
					}else{
						Toast.makeText(mActivity, "There are no Type A Patients in the Waiting Room.", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});

		patientBButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!interruptOn){
					if(numberOfPatientBs > 0){
						colorizeRoomForPatients();
						patientBButton.setBackgroundResource(R.drawable.green_button_selected_button);
						patientAButton.setEnabled(false);
						patientAButton.setTextColor(getResources().getColor(R.color.gray));
						setRoomClicksForPatientB();
					}else{
						Toast.makeText(mActivity, "There are no Type B Patients in the Waiting Room.", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});

		xButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!interruptOn){
					colorizeRoomsNormal();
					resetAllButtons();
				}
			}
		});

		//containerResource Views
		doctorsButton = (Button) findViewById(R.id.doctor_button);
		nursesButton = (Button) findViewById(R.id.nurses_button);
		surgeonsButton = (Button) findViewById(R.id.surgeon_button);

		doctorsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!interruptOn){
					if(numberOfDoctors > 0){
						colorizeRoomForDoctor();
						doctorsButton.setBackgroundResource(R.drawable.green_button_selected_button);
						nursesButton.setEnabled(false);
						nursesButton.setTextColor(getResources().getColor(R.color.gray));
						surgeonsButton.setEnabled(false);
						surgeonsButton.setTextColor(getResources().getColor(R.color.gray));
						setRoomClicksForDoctors();
					}else{
						writeStringAsFile(createCSVLine("FailAssign,Doctor"));
						Toast.makeText(mActivity, "There are no available doctors. Wait for a doctor to finish with a patient or request one from the other hospital.", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});

		nursesButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!interruptOn){
					if(numberOfNurses > 0){
						colorizeRoomForNurse();
						nursesButton.setBackgroundResource(R.drawable.green_button_selected_button);
						doctorsButton.setEnabled(false);
						doctorsButton.setTextColor(getResources().getColor(R.color.gray));
						surgeonsButton.setEnabled(false);
						surgeonsButton.setTextColor(getResources().getColor(R.color.gray));
						setRoomClicksForNurse();
					}else{
						writeStringAsFile(createCSVLine("FailAssign,Nurse"));
						Toast.makeText(mActivity, "There are no available nurses. Wait for a nurse to finish with a patient or request one from the other hospital.", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});

		surgeonsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!interruptOn){
					if(numberOfSurgeons > 0){
						colorizeRoomForSurgeon();
						surgeonsButton.setBackgroundResource(R.drawable.green_button_selected_button);
						doctorsButton.setEnabled(false);
						doctorsButton.setTextColor(getResources().getColor(R.color.gray));
						nursesButton.setEnabled(false);
						nursesButton.setTextColor(getResources().getColor(R.color.gray));
						setRoomClicksForSurgeons();
					}else{
						writeStringAsFile(createCSVLine("FailAssign,Surgeon"));
						Toast.makeText(mActivity, "There are no available surgeons. Wait for a surgeon to finish with a patient or request one from the other hospital.", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});

		//containerRequest Views
		doctorsButton2 = (Button) findViewById(R.id.doctor_button2);
		nursesButton2 = (Button) findViewById(R.id.nurses_button2);
		surgeonsButton2 = (Button) findViewById(R.id.surgeon_button2);

		doctorsButton2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!interruptOn){
					writeStringAsFile(createCSVLine("PlayerRequest,Doctor"));
					if(fulfillRequestAlgorithm(numberOfDoctors, otherNumberOfDoctors)){
						numberOfDoctors++;
						otherNumberOfDoctors--;
						Toast.makeText(mActivity, "The other hospital has given you a doctor." , Toast.LENGTH_SHORT).show();
						writeStringAsFile(createCSVLine("AgentResponse,Accept"));
					}else{
						writeStringAsFile(createCSVLine("AgentResponse,Deny"));
						Toast.makeText(mActivity, "Your request has been denied by the other hospital." , Toast.LENGTH_SHORT).show();
					}
					updateSideBarMain();
					showMainBottomButtons();
				}
			}
		});

		nursesButton2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!interruptOn){
					writeStringAsFile(createCSVLine("PlayerRequest,Nurse"));
					if(fulfillRequestAlgorithm(numberOfNurses, otherNumberOfNurses)){
						numberOfNurses++;
						otherNumberOfNurses--;
						Toast.makeText(mActivity, "The other hospital has given you a nurse." , Toast.LENGTH_SHORT).show();
						writeStringAsFile(createCSVLine("AgentResponse,Accept"));
					}else{
						writeStringAsFile(createCSVLine("AgentResponse,Deny"));
						Toast.makeText(mActivity, "Your request has been denied by the other hospital." , Toast.LENGTH_SHORT).show();
					}
					updateSideBarMain();
					showMainBottomButtons();
				}
			}
		});

		surgeonsButton2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!interruptOn){
					writeStringAsFile(createCSVLine("PlayerRequest,Surgeon"));
					if(fulfillRequestAlgorithm(numberOfSurgeons, otherNumberOfSurgeons)){
						numberOfSurgeons++;
						otherNumberOfSurgeons--;
						Toast.makeText(mActivity, "The other hospital has given you a surgeon." , Toast.LENGTH_SHORT).show();
						writeStringAsFile(createCSVLine("AgentResponse,Accept"));
					}else{
						writeStringAsFile(createCSVLine("AgentResponse,Deny"));
						Toast.makeText(mActivity, "Your request has been denied by the other hospital." , Toast.LENGTH_SHORT).show();
					}
					updateSideBarMain();
					showMainBottomButtons();
				}
			}
		});

		room1Main = (RelativeLayout) findViewById(R.id.room_1);
		vacantText1 = (TextView) findViewById(R.id.vacant_1_text);
		collectButton1 = (Button) findViewById(R.id.collect_button_1);
		room1Layout = (RelativeLayout) findViewById(R.id.room_info_1);
		patientText1 = (TextView) findViewById(R.id.patient_text_1);
		doctorSurgeonText1 = (TextView) findViewById(R.id.doctor_surgeon_text_1);
		nurseText1 = (TextView) findViewById(R.id.nurse_text_1);
		timeLeftText1 = (TextView) findViewById(R.id.timeLeftValue1);

		room2Main = (RelativeLayout) findViewById(R.id.room_2);
		vacantText2 = (TextView) findViewById(R.id.vacant_2_text);
		collectButton2 = (Button) findViewById(R.id.collect_button_2);
		room2Layout = (RelativeLayout) findViewById(R.id.room_info_2);
		patientText2 = (TextView) findViewById(R.id.patient_text_2);
		doctorSurgeonText2 = (TextView) findViewById(R.id.doctor_surgeon_text_2);
		nurseText2 = (TextView) findViewById(R.id.nurse_text_2);
		timeLeftText2 = (TextView) findViewById(R.id.timeLeftValue2);

		room3Main = (RelativeLayout) findViewById(R.id.room_3);
		vacantText3 = (TextView) findViewById(R.id.vacant_3_text);
		collectButton3 = (Button) findViewById(R.id.collect_button_3);
		room3Layout = (RelativeLayout) findViewById(R.id.room_info_3);
		patientText3 = (TextView) findViewById(R.id.patient_text_3);
		doctorSurgeonText3 = (TextView) findViewById(R.id.doctor_surgeon_text_3);
		nurseText3 = (TextView) findViewById(R.id.nurse_text_3);
		timeLeftText3 = (TextView) findViewById(R.id.timeLeftValue3);

		room4Main = (RelativeLayout) findViewById(R.id.room_4);
		vacantText4 = (TextView) findViewById(R.id.vacant_4_text);
		collectButton4 = (Button) findViewById(R.id.collect_button_4);
		room4Layout = (RelativeLayout) findViewById(R.id.room_info_4);
		patientText4 = (TextView) findViewById(R.id.patient_text_4);
		doctorSurgeonText4 = (TextView) findViewById(R.id.doctor_surgeon_text_4);
		nurseText4 = (TextView) findViewById(R.id.nurse_text_4);
		timeLeftText4 = (TextView) findViewById(R.id.timeLeftValue4);

		room5Main = (RelativeLayout) findViewById(R.id.room_5);
		vacantText5 = (TextView) findViewById(R.id.vacant_5_text);
		collectButton5 = (Button) findViewById(R.id.collect_button_5);
		room5Layout = (RelativeLayout) findViewById(R.id.room_info_5);
		patientText5 = (TextView) findViewById(R.id.patient_text_5);
		doctorSurgeonText5 = (TextView) findViewById(R.id.doctor_surgeon_text_5);
		nurseText5 = (TextView) findViewById(R.id.nurse_text_5);
		timeLeftText5 = (TextView) findViewById(R.id.timeLeftValue5);

		room6Main = (RelativeLayout) findViewById(R.id.room_6);
		vacantText6 = (TextView) findViewById(R.id.vacant_6_text);
		collectButton6 = (Button) findViewById(R.id.collect_button_6);
		room6Layout = (RelativeLayout) findViewById(R.id.room_info_6);
		patientText6 = (TextView) findViewById(R.id.patient_text_6);
		doctorSurgeonText6 = (TextView) findViewById(R.id.doctor_surgeon_text_6);
		nurseText6 = (TextView) findViewById(R.id.nurse_text_6);
		timeLeftText6 = (TextView) findViewById(R.id.timeLeftValue6);

		roomList[0] = new Room();
		roomList[1] = new Room();
		roomList[2] = new Room();
		roomList[3] = new Room();
		roomList[4] = new Room();
		roomList[5] = new Room();

		interruptionLayout = (RelativeLayout) findViewById(R.id.interruption_layout);
		acceptRequest = (Button) findViewById(R.id.accept_request);
		denyRequest = (Button) findViewById(R.id.deny_request);
		interruptionText = (TextView) findViewById(R.id.interruption_text);

		exitButton = (ImageView) findViewById(R.id.exit_button);
		exitButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(mActivity).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit?")
				.setMessage("Do you wish to quit the game? Data will be deleted.")
				.setPositiveButton("Quit", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), userID+"_"+condition+"_"+trial+".csv");
						file.delete();
						endGame();
					}

				}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				})
				.show();
			}
		});

		startButton();
	}

	private void showMainBottomButtons(){
		if(!interruptOn){
			assignPatientToRoomButton.setVisibility(View.VISIBLE);
			assignResourceToRoomButton.setVisibility(View.VISIBLE);
			requestResourceButton.setVisibility(View.VISIBLE);

			patientAButton.setVisibility(View.GONE);
			patientBButton.setVisibility(View.GONE);

			doctorsButton.setVisibility(View.GONE);
			nursesButton.setVisibility(View.GONE);
			surgeonsButton.setVisibility(View.GONE);
			xButton.setVisibility(View.GONE);

			doctorsButton2.setVisibility(View.GONE);
			nursesButton2.setVisibility(View.GONE);
			surgeonsButton2.setVisibility(View.GONE);
		}
	}

	private void hideMainBottomButtons(){
		patientAButton.setVisibility(View.GONE);
		patientBButton.setVisibility(View.GONE);

		doctorsButton.setVisibility(View.GONE);
		nursesButton.setVisibility(View.GONE);
		surgeonsButton.setVisibility(View.GONE);
		xButton.setVisibility(View.GONE);

		doctorsButton2.setVisibility(View.GONE);
		nursesButton2.setVisibility(View.GONE);
		surgeonsButton2.setVisibility(View.GONE);

		assignPatientToRoomButton.setVisibility(View.GONE);
		assignResourceToRoomButton.setVisibility(View.GONE);
		requestResourceButton.setVisibility(View.GONE);
	}

	private void updateSideBarMain(){
		updatePatientsInSideBar();
		doctorsValue1.setText(numberOfDoctors+"");
		nursesValue1.setText(numberOfNurses+"");
		surgeonsValue1.setText(numberOfSurgeons+"");
		otherDoctorsValue.setText(otherNumberOfDoctors+"");
		otherNursesValue.setText(otherNumberOfNurses+"");
		otherSurgeonsValue.setText(otherNumberOfSurgeons+"");
	}

	private void updatePatientsInSideBar(){
		circleA1.setVisibility(View.INVISIBLE);
		circleA2.setVisibility(View.INVISIBLE);
		circleA3.setVisibility(View.INVISIBLE);
		circleA4.setVisibility(View.INVISIBLE);
		circleA5.setVisibility(View.INVISIBLE);
		circleA6.setVisibility(View.INVISIBLE);

		circleB1.setVisibility(View.INVISIBLE);
		circleB2.setVisibility(View.INVISIBLE);
		circleB3.setVisibility(View.INVISIBLE);
		circleB4.setVisibility(View.INVISIBLE);
		circleB5.setVisibility(View.INVISIBLE);
		circleB6.setVisibility(View.INVISIBLE);

		switch(numberOfPatientAs){
		case 1:
			circleA1.setVisibility(View.VISIBLE);
			break;
		case 2:
			circleA1.setVisibility(View.VISIBLE);
			circleA2.setVisibility(View.VISIBLE);
			break;
		case 3:
			circleA1.setVisibility(View.VISIBLE);
			circleA2.setVisibility(View.VISIBLE);
			circleA3.setVisibility(View.VISIBLE);
			break;
		case 4:
			circleA1.setVisibility(View.VISIBLE);
			circleA2.setVisibility(View.VISIBLE);
			circleA3.setVisibility(View.VISIBLE);
			circleA4.setVisibility(View.VISIBLE);
			break;
		case 5:
			circleA1.setVisibility(View.VISIBLE);
			circleA2.setVisibility(View.VISIBLE);
			circleA3.setVisibility(View.VISIBLE);
			circleA4.setVisibility(View.VISIBLE);
			circleA5.setVisibility(View.VISIBLE);
			break;
		case 6:
			circleA1.setVisibility(View.VISIBLE);
			circleA2.setVisibility(View.VISIBLE);
			circleA3.setVisibility(View.VISIBLE);
			circleA4.setVisibility(View.VISIBLE);
			circleA5.setVisibility(View.VISIBLE);
			circleA6.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}

		switch(numberOfPatientBs){
		case 1:
			circleB1.setVisibility(View.VISIBLE);
			break;
		case 2:
			circleB1.setVisibility(View.VISIBLE);
			circleB2.setVisibility(View.VISIBLE);
			break;
		case 3:
			circleB1.setVisibility(View.VISIBLE);
			circleB2.setVisibility(View.VISIBLE);
			circleB3.setVisibility(View.VISIBLE);
			break;
		case 4:
			circleB1.setVisibility(View.VISIBLE);
			circleB2.setVisibility(View.VISIBLE);
			circleB3.setVisibility(View.VISIBLE);
			circleB4.setVisibility(View.VISIBLE);
			break;
		case 5:
			circleB1.setVisibility(View.VISIBLE);
			circleB2.setVisibility(View.VISIBLE);
			circleB3.setVisibility(View.VISIBLE);
			circleB4.setVisibility(View.VISIBLE);
			circleB5.setVisibility(View.VISIBLE);
			break;
		case 6:
			circleB1.setVisibility(View.VISIBLE);
			circleB2.setVisibility(View.VISIBLE);
			circleB3.setVisibility(View.VISIBLE);
			circleB4.setVisibility(View.VISIBLE);
			circleB5.setVisibility(View.VISIBLE);
			circleB6.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}

		//set color of circles
		int total = numberOfPatientAs+numberOfPatientBs;
		if(total > 0 && total <= 2)
			setCircleColors(R.drawable.green_circle);
		else if(total > 2 && total <= 4)
			setCircleColors(R.drawable.yellow_circle);
		else if(total > 4 && total <= 6)
			setCircleColors(R.drawable.red_circle);
		//
		//old way
		//				if(otherNumberOfPatientAs == 4 || otherNumberOfPatientAs == 5)
		//					circleOtherA.setImageResource(R.drawable.yellow_square);
		//				else if(otherNumberOfPatientAs >= 6)
		//					circleOtherA.setImageResource(R.drawable.red_square);
		//				else
		//					circleOtherA.setImageResource(R.drawable.green_square);
		//		
		//				if(otherNumberOfPatientBs == 4 || otherNumberOfPatientBs == 5)
		//					circleOtherB.setImageResource(R.drawable.yellow_square);
		//				else if(otherNumberOfPatientBs >= 6)
		//					circleOtherB.setImageResource(R.drawable.red_square);
		//				else
		//					circleOtherB.setImageResource(R.drawable.green_square);

		//new way

		int totalOther = otherNumberOfPatientAs+otherNumberOfPatientBs;
		if(totalOther > 0 && totalOther <= 2){
			circleOtherA.setImageResource(R.drawable.green_square);
			circleOtherB.setImageResource(R.drawable.green_square);
		}else if(totalOther > 2 && totalOther <= 4){
			circleOtherA.setImageResource(R.drawable.yellow_square);
			circleOtherB.setImageResource(R.drawable.yellow_square);
		}else if(totalOther > 4 && totalOther <= 6){
			circleOtherA.setImageResource(R.drawable.red_square);
			circleOtherB.setImageResource(R.drawable.red_square);
		}
	}

	private void setCircleColors(int colorResourceId){
		if(circleA1.getVisibility() == View.VISIBLE)
			circleA1.setImageResource(colorResourceId);
		if(circleA2.getVisibility() == View.VISIBLE)
			circleA2.setImageResource(colorResourceId);
		if(circleA3.getVisibility() == View.VISIBLE)
			circleA3.setImageResource(colorResourceId);
		if(circleA4.getVisibility() == View.VISIBLE)
			circleA4.setImageResource(colorResourceId);
		if(circleA5.getVisibility() == View.VISIBLE)
			circleA5.setImageResource(colorResourceId);
		if(circleA6.getVisibility() == View.VISIBLE)
			circleA6.setImageResource(colorResourceId);

		if(circleB1.getVisibility() == View.VISIBLE)
			circleB1.setImageResource(colorResourceId);
		if(circleB2.getVisibility() == View.VISIBLE)
			circleB2.setImageResource(colorResourceId);
		if(circleB3.getVisibility() == View.VISIBLE)
			circleB3.setImageResource(colorResourceId);
		if(circleB4.getVisibility() == View.VISIBLE)
			circleB4.setImageResource(colorResourceId);
		if(circleB5.getVisibility() == View.VISIBLE)
			circleB5.setImageResource(colorResourceId);
		if(circleB6.getVisibility() == View.VISIBLE)
			circleB6.setImageResource(colorResourceId);
	}

	private void setRoomInfo(){

		if(roomList[0].collect){
			collectButton1.setVisibility(View.VISIBLE);
			vacantText1.setVisibility(View.GONE);
			room1Layout.setVisibility(View.GONE);
			collectButton1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					//free up resources
					roomList[0].collect = false;
					if(roomList[0].patient.equals("A"))
						numberOfDoctors++;
					else if(roomList[0].patient.equals("B"))
						numberOfSurgeons++;

					roomList[0].patient = "";
					roomList[0].numDoctor = 0;
					roomList[0].numSurgeon = 0;
					roomList[0].numNurse = 0;
					roomList[0].timeStarted = false;
					numberOfNurses++;
					timeLeftText1.setText("-:--");

					updateSideBarMain();
					setRoomInfo();
					writeStringAsFile(createCSVLine("PlayerCollect,A"));
				}
			});
		}else{
			if(roomList[0].patient.equals("")){
				collectButton1.setVisibility(View.GONE);
				vacantText1.setVisibility(View.VISIBLE);
				room1Layout.setVisibility(View.GONE);
			}else{
				collectButton1.setVisibility(View.GONE);
				vacantText1.setVisibility(View.GONE);
				room1Layout.setVisibility(View.VISIBLE);

				if(roomList[0].patient.equals("A")){
					patientText1.setText("Patient A");

					if(roomList[0].numDoctor == 1){
						doctorSurgeonText1.setText("1 Doctor");
					}else{
						doctorSurgeonText1.setText("0 Doctors");
					}
				}else{
					patientText1.setText("Patient B");

					if(roomList[0].numSurgeon == 1){
						doctorSurgeonText1.setText("1 Surgeon");
					}else{
						doctorSurgeonText1.setText("0 Surgeons");
					}
				}

				if(roomList[0].numNurse == 1){
					nurseText1.setText("1 Nurse");
				}else{
					nurseText1.setText("0 Nurses");
				}

				startTimerForRoom(0, timeLeftText1);
			}

		}

		if(roomList[1].collect){
			collectButton2.setVisibility(View.VISIBLE);
			vacantText2.setVisibility(View.GONE);
			room2Layout.setVisibility(View.GONE);
			collectButton2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					//free up resources
					roomList[1].collect = false;
					if(roomList[1].patient.equals("A"))
						numberOfDoctors++;
					else if(roomList[1].patient.equals("B"))
						numberOfSurgeons++;

					roomList[1].patient = "";
					roomList[1].numDoctor = 0;
					roomList[1].numSurgeon = 0;
					roomList[1].numNurse = 0;
					roomList[1].timeStarted = false;
					numberOfNurses++;
					timeLeftText2.setText("-:--");

					updateSideBarMain();
					setRoomInfo();
					writeStringAsFile(createCSVLine("PlayerCollect,B"));
				}
			});
		}else{
			if(roomList[1].patient.equals("")){
				collectButton2.setVisibility(View.GONE);
				vacantText2.setVisibility(View.VISIBLE);
				room2Layout.setVisibility(View.GONE);
			}else{
				collectButton2.setVisibility(View.GONE);
				vacantText2.setVisibility(View.GONE);
				room2Layout.setVisibility(View.VISIBLE);

				if(roomList[1].patient.equals("A")){
					patientText2.setText("Patient A");

					if(roomList[1].numDoctor == 1){
						doctorSurgeonText2.setText("1 Doctor");
					}else{
						doctorSurgeonText2.setText("0 Doctors");
					}
				}else{
					patientText2.setText("Patient B");

					if(roomList[1].numSurgeon == 1){
						doctorSurgeonText2.setText("1 Surgeon");
					}else{
						doctorSurgeonText2.setText("0 Surgeons");
					}
				}

				if(roomList[1].numNurse == 1){
					nurseText2.setText("1 Nurse");
				}else{
					nurseText2.setText("0 Nurses");
				}

				startTimerForRoom(1, timeLeftText2);
			}
		}

		if(roomList[2].collect){
			collectButton3.setVisibility(View.VISIBLE);
			vacantText3.setVisibility(View.GONE);
			room3Layout.setVisibility(View.GONE);
			collectButton3.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					//free up resources
					roomList[2].collect = false;
					if(roomList[2].patient.equals("A"))
						numberOfDoctors++;
					else if(roomList[2].patient.equals("B"))
						numberOfSurgeons++;

					roomList[2].patient = "";
					roomList[2].numDoctor = 0;
					roomList[2].numSurgeon = 0;
					roomList[2].numNurse = 0;
					roomList[2].timeStarted = false;
					numberOfNurses++;
					timeLeftText3.setText("-:--");

					updateSideBarMain();
					setRoomInfo();
					writeStringAsFile(createCSVLine("PlayerCollect,C"));
				}
			});
		}else{
			if(roomList[2].patient.equals("")){
				collectButton3.setVisibility(View.GONE);
				vacantText3.setVisibility(View.VISIBLE);
				room3Layout.setVisibility(View.GONE);
			}else{
				collectButton3.setVisibility(View.GONE);
				vacantText3.setVisibility(View.GONE);
				room3Layout.setVisibility(View.VISIBLE);

				if(roomList[2].patient.equals("A")){
					patientText3.setText("Patient A");

					if(roomList[2].numDoctor == 1){
						doctorSurgeonText3.setText("1 Doctor");
					}else{
						doctorSurgeonText3.setText("0 Doctors");
					}
				}else{
					patientText3.setText("Patient B");

					if(roomList[2].numSurgeon == 1){
						doctorSurgeonText3.setText("1 Surgeon");
					}else{
						doctorSurgeonText3.setText("0 Surgeons");
					}
				}

				if(roomList[2].numNurse == 1){
					nurseText3.setText("1 Nurse");
				}else{
					nurseText3.setText("0 Nurses");
				}

				startTimerForRoom(2, timeLeftText3);
			}
		}

		if(roomList[3].collect){
			collectButton4.setVisibility(View.VISIBLE);
			vacantText4.setVisibility(View.GONE);
			room4Layout.setVisibility(View.GONE);
			collectButton4.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					//free up resources
					roomList[3].collect = false;
					if(roomList[3].patient.equals("A"))
						numberOfDoctors++;
					else if(roomList[3].patient.equals("B"))
						numberOfSurgeons++;

					roomList[3].patient = "";
					roomList[3].numDoctor = 0;
					roomList[3].numSurgeon = 0;
					roomList[3].numNurse = 0;
					roomList[3].timeStarted = false;
					numberOfNurses++;
					timeLeftText4.setText("-:--");

					updateSideBarMain();
					setRoomInfo();
					writeStringAsFile(createCSVLine("PlayerCollect,D"));
				}
			});
		}else{
			if(roomList[3].patient.equals("")){
				collectButton4.setVisibility(View.GONE);
				vacantText4.setVisibility(View.VISIBLE);
				room4Layout.setVisibility(View.GONE);
			}else{
				collectButton4.setVisibility(View.GONE);
				vacantText4.setVisibility(View.GONE);
				room4Layout.setVisibility(View.VISIBLE);

				if(roomList[3].patient.equals("A")){
					patientText4.setText("Patient A");

					if(roomList[3].numDoctor == 1){
						doctorSurgeonText4.setText("1 Doctor");
					}else{
						doctorSurgeonText4.setText("0 Doctors");
					}
				}else{
					patientText4.setText("Patient B");

					if(roomList[3].numSurgeon == 1){
						doctorSurgeonText4.setText("1 Surgeon");
					}else{
						doctorSurgeonText4.setText("0 Surgeons");
					}
				}

				if(roomList[3].numNurse == 1){
					nurseText4.setText("1 Nurse");
				}else{
					nurseText4.setText("0 Nurses");
				}

				startTimerForRoom(3, timeLeftText4);
			}
		}

		if(roomList[4].collect){
			collectButton5.setVisibility(View.VISIBLE);
			vacantText5.setVisibility(View.GONE);
			room5Layout.setVisibility(View.GONE);
			collectButton5.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					//free up resources
					roomList[4].collect = false;
					if(roomList[4].patient.equals("A"))
						numberOfDoctors++;
					else if(roomList[4].patient.equals("B"))
						numberOfSurgeons++;

					roomList[4].patient = "";
					roomList[4].numDoctor = 0;
					roomList[4].numSurgeon = 0;
					roomList[4].numNurse = 0;
					roomList[4].timeStarted = false;
					numberOfNurses++;
					timeLeftText5.setText("-:--");

					updateSideBarMain();
					setRoomInfo();
					writeStringAsFile(createCSVLine("PlayerCollect,E"));
				}
			});
		}else{
			if(roomList[4].patient.equals("")){
				collectButton5.setVisibility(View.GONE);
				vacantText5.setVisibility(View.VISIBLE);
				room5Layout.setVisibility(View.GONE);
			}else{
				collectButton5.setVisibility(View.GONE);
				vacantText5.setVisibility(View.GONE);
				room5Layout.setVisibility(View.VISIBLE);

				if(roomList[4].patient.equals("A")){
					patientText5.setText("Patient A");

					if(roomList[4].numDoctor == 1){
						doctorSurgeonText5.setText("1 Doctor");
					}else{
						doctorSurgeonText5.setText("0 Doctors");
					}
				}else{
					patientText5.setText("Patient B");

					if(roomList[4].numSurgeon == 1){
						doctorSurgeonText5.setText("1 Surgeon");
					}else{
						doctorSurgeonText5.setText("0 Surgeons");
					}
				}

				if(roomList[4].numNurse == 1){
					nurseText5.setText("1 Nurse");
				}else{
					nurseText5.setText("0 Nurses");
				}

				startTimerForRoom(4, timeLeftText5);
			}
		}

		if(roomList[5].collect){
			collectButton6.setVisibility(View.VISIBLE);
			vacantText6.setVisibility(View.GONE);
			room6Layout.setVisibility(View.GONE);
			collectButton6.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					//free up resources
					roomList[5].collect = false;
					if(roomList[5].patient.equals("A"))
						numberOfDoctors++;
					else if(roomList[0].patient.equals("B"))
						numberOfSurgeons++;

					roomList[5].patient = "";
					roomList[5].numDoctor = 0;
					roomList[5].numSurgeon = 0;
					roomList[5].numNurse = 0;
					roomList[5].timeStarted = false;
					numberOfNurses++;
					timeLeftText6.setText("-:--");

					updateSideBarMain();
					setRoomInfo();
					writeStringAsFile(createCSVLine("PlayerCollect,F"));
				}
			});
		}else{
			if(roomList[5].patient.equals("")){
				collectButton6.setVisibility(View.GONE);
				vacantText6.setVisibility(View.VISIBLE);
				room6Layout.setVisibility(View.GONE);
			}else{
				collectButton6.setVisibility(View.GONE);
				vacantText6.setVisibility(View.GONE);
				room6Layout.setVisibility(View.VISIBLE);

				if(roomList[5].patient.equals("A")){
					patientText6.setText("Patient A");

					if(roomList[5].numDoctor == 1){
						doctorSurgeonText6.setText("1 Doctor");
					}else{
						doctorSurgeonText6.setText("0 Doctors");
					}
				}else{
					patientText6.setText("Patient B");

					if(roomList[5].numSurgeon == 1){
						doctorSurgeonText6.setText("1 Surgeon");
					}else{
						doctorSurgeonText6.setText("0 Surgeons");
					}
				}

				if(roomList[5].numNurse == 1){
					nurseText6.setText("1 Nurse");
				}else{
					nurseText6.setText("0 Nurses");
				}
			}

			startTimerForRoom(5, timeLeftText6);
		}
	}

	private void colorizeRoomForPatients(){
		if(roomList[0].patient.equals("")){
			room1Main.setBackgroundResource(R.drawable.background_with_border_green);
		}else{
			room1Main.setBackgroundResource(R.drawable.background_with_border_gray);
		}

		if(roomList[1].patient.equals("")){
			room2Main.setBackgroundResource(R.drawable.background_with_border_green);
		}else{
			room2Main.setBackgroundResource(R.drawable.background_with_border_gray);
		}

		if(roomList[2].patient.equals("")){
			room3Main.setBackgroundResource(R.drawable.background_with_border_green);
		}else{
			room3Main.setBackgroundResource(R.drawable.background_with_border_gray);
		}

		if(roomList[3].patient.equals("")){
			room4Main.setBackgroundResource(R.drawable.background_with_border_green);
		}else{
			room4Main.setBackgroundResource(R.drawable.background_with_border_gray);
		}

		if(roomList[4].patient.equals("")){
			room5Main.setBackgroundResource(R.drawable.background_with_border_green);
		}else{
			room5Main.setBackgroundResource(R.drawable.background_with_border_gray);
		}

		if(roomList[5].patient.equals("")){
			room6Main.setBackgroundResource(R.drawable.background_with_border_green);
		}else{
			room6Main.setBackgroundResource(R.drawable.background_with_border_gray);
		}
	}

	private void colorizeRoomForDoctor(){
		if(roomList[0].numDoctor == 0 && roomList[0].patient.equals("A")){
			room1Main.setBackgroundResource(R.drawable.background_with_border_green);
		}else{
			room1Main.setBackgroundResource(R.drawable.background_with_border_gray);
		}

		if(roomList[1].numDoctor == 0 && roomList[1].patient.equals("A")){
			room2Main.setBackgroundResource(R.drawable.background_with_border_green);
		}else{
			room2Main.setBackgroundResource(R.drawable.background_with_border_gray);
		}

		if(roomList[2].numDoctor == 0 && roomList[2].patient.equals("A")){
			room3Main.setBackgroundResource(R.drawable.background_with_border_green);
		}else{
			room3Main.setBackgroundResource(R.drawable.background_with_border_gray);
		}

		if(roomList[3].numDoctor == 0 && roomList[3].patient.equals("A")){
			room4Main.setBackgroundResource(R.drawable.background_with_border_green);
		}else{
			room4Main.setBackgroundResource(R.drawable.background_with_border_gray);
		}

		if(roomList[4].numDoctor == 0 && roomList[4].patient.equals("A")){
			room5Main.setBackgroundResource(R.drawable.background_with_border_green);
		}else{
			room5Main.setBackgroundResource(R.drawable.background_with_border_gray);
		}

		if(roomList[5].numDoctor == 0 && roomList[5].patient.equals("A")){
			room6Main.setBackgroundResource(R.drawable.background_with_border_green);
		}else{
			room6Main.setBackgroundResource(R.drawable.background_with_border_gray);
		}
	}

	private void colorizeRoomForSurgeon(){
		if(roomList[0].numSurgeon == 0 && roomList[0].patient.equals("B")){
			room1Main.setBackgroundResource(R.drawable.background_with_border_green);
		}else{
			room1Main.setBackgroundResource(R.drawable.background_with_border_gray);
		}

		if(roomList[1].numSurgeon == 0 && roomList[1].patient.equals("B")){
			room2Main.setBackgroundResource(R.drawable.background_with_border_green);
		}else{
			room2Main.setBackgroundResource(R.drawable.background_with_border_gray);
		}

		if(roomList[2].numSurgeon == 0 && roomList[2].patient.equals("B")){
			room3Main.setBackgroundResource(R.drawable.background_with_border_green);
		}else{
			room3Main.setBackgroundResource(R.drawable.background_with_border_gray);
		}

		if(roomList[3].numSurgeon == 0 && roomList[3].patient.equals("B")){
			room4Main.setBackgroundResource(R.drawable.background_with_border_green);
		}else{
			room4Main.setBackgroundResource(R.drawable.background_with_border_gray);
		}

		if(roomList[4].numSurgeon == 0 && roomList[4].patient.equals("B")){
			room5Main.setBackgroundResource(R.drawable.background_with_border_green);
		}else{
			room5Main.setBackgroundResource(R.drawable.background_with_border_gray);
		}

		if(roomList[5].numSurgeon == 0 && roomList[5].patient.equals("B")){
			room6Main.setBackgroundResource(R.drawable.background_with_border_green);
		}else{
			room6Main.setBackgroundResource(R.drawable.background_with_border_gray);
		}
	}

	private void colorizeRoomForNurse(){
		if(roomList[0].numNurse == 0 && !roomList[0].patient.equals("")){
			room1Main.setBackgroundResource(R.drawable.background_with_border_green);
		}else{
			room1Main.setBackgroundResource(R.drawable.background_with_border_gray);
		}

		if(roomList[1].numNurse == 0 && !roomList[1].patient.equals("")){
			room2Main.setBackgroundResource(R.drawable.background_with_border_green);
		}else{
			room2Main.setBackgroundResource(R.drawable.background_with_border_gray);
		}

		if(roomList[2].numNurse == 0 && !roomList[2].patient.equals("")){
			room3Main.setBackgroundResource(R.drawable.background_with_border_green);
		}else{
			room3Main.setBackgroundResource(R.drawable.background_with_border_gray);
		}

		if(roomList[3].numNurse == 0 && !roomList[3].patient.equals("")){
			room4Main.setBackgroundResource(R.drawable.background_with_border_green);
		}else{
			room4Main.setBackgroundResource(R.drawable.background_with_border_gray);
		}

		if(roomList[4].numNurse == 0 && !roomList[4].patient.equals("")){
			room5Main.setBackgroundResource(R.drawable.background_with_border_green);
		}else{
			room5Main.setBackgroundResource(R.drawable.background_with_border_gray);
		}

		if(roomList[5].numNurse == 0 && !roomList[5].patient.equals("")){
			room6Main.setBackgroundResource(R.drawable.background_with_border_green);
		}else{
			room6Main.setBackgroundResource(R.drawable.background_with_border_gray);
		}
	}

	private void colorizeRoomsNormal(){
		room1Main.setBackgroundResource(R.drawable.background_with_border);
		room2Main.setBackgroundResource(R.drawable.background_with_border);
		room3Main.setBackgroundResource(R.drawable.background_with_border);
		room4Main.setBackgroundResource(R.drawable.background_with_border);
		room5Main.setBackgroundResource(R.drawable.background_with_border);
		room6Main.setBackgroundResource(R.drawable.background_with_border);

		disableRoomClicks();
	}

	private void setRoomClicksForPatientA(){
		if(roomList[0].patient.equals(""))
			room1Main.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					colorizeRoomsNormal();
					roomList[0].patient = "A";
					numberOfPatientAs--;
					updateSideBarMain();
					resetAllButtons();
					setRoomInfo();
					writeStringAsFile(createCSVLine("PatientType,A"));
					writeStringAsFile(createCSVLine("RoomAssign,A"));
				}
			});

		if(roomList[1].patient.equals(""))
			room2Main.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					colorizeRoomsNormal();
					roomList[1].patient = "A";
					numberOfPatientAs--;
					updateSideBarMain();
					resetAllButtons();
					setRoomInfo();
					writeStringAsFile(createCSVLine("PatientType,A"));
					writeStringAsFile(createCSVLine("RoomAssign,B"));
				}
			});

		if(roomList[2].patient.equals(""))
			room3Main.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					colorizeRoomsNormal();
					roomList[2].patient = "A";
					numberOfPatientAs--;
					updateSideBarMain();
					resetAllButtons();
					setRoomInfo();
					writeStringAsFile(createCSVLine("PatientType,A"));
					writeStringAsFile(createCSVLine("RoomAssign,C"));
				}
			});

		if(roomList[3].patient.equals(""))
			room4Main.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					colorizeRoomsNormal();
					roomList[3].patient = "A";
					numberOfPatientAs--;
					updateSideBarMain();
					resetAllButtons();
					setRoomInfo();
					writeStringAsFile(createCSVLine("PatientType,A"));
					writeStringAsFile(createCSVLine("RoomAssign,D"));
				}
			});

		if(roomList[4].patient.equals(""))
			room5Main.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					colorizeRoomsNormal();
					roomList[4].patient = "A";
					numberOfPatientAs--;
					updateSideBarMain();
					resetAllButtons();
					setRoomInfo();
					writeStringAsFile(createCSVLine("PatientType,A"));
					writeStringAsFile(createCSVLine("RoomAssign,E"));
				}
			});

		if(roomList[5].patient.equals(""))
			room6Main.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					colorizeRoomsNormal();
					roomList[5].patient = "A";
					numberOfPatientAs--;
					updateSideBarMain();
					resetAllButtons();
					setRoomInfo();
					writeStringAsFile(createCSVLine("PatientType,A"));
					writeStringAsFile(createCSVLine("RoomAssign,F"));
				}
			});
	}

	private void setRoomClicksForPatientB(){
		if(roomList[0].patient.equals(""))
			room1Main.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					colorizeRoomsNormal();
					roomList[0].patient = "B";
					numberOfPatientBs--;
					updateSideBarMain();
					resetAllButtons();
					setRoomInfo();
					writeStringAsFile(createCSVLine("PatientType,B"));
					writeStringAsFile(createCSVLine("RoomAssign,A"));
				}
			});

		if(roomList[1].patient.equals(""))
			room2Main.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					colorizeRoomsNormal();
					roomList[1].patient = "B";
					numberOfPatientBs--;
					updateSideBarMain();
					resetAllButtons();
					setRoomInfo();
					writeStringAsFile(createCSVLine("PatientType,B"));
					writeStringAsFile(createCSVLine("RoomAssign,B"));
				}
			});

		if(roomList[2].patient.equals(""))
			room3Main.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					colorizeRoomsNormal();
					roomList[2].patient = "B";
					numberOfPatientBs--;
					updateSideBarMain();
					resetAllButtons();
					setRoomInfo();
					writeStringAsFile(createCSVLine("PatientType,B"));
					writeStringAsFile(createCSVLine("RoomAssign,C"));
				}
			});

		if(roomList[3].patient.equals(""))
			room4Main.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					colorizeRoomsNormal();
					roomList[3].patient = "B";
					numberOfPatientBs--;
					updateSideBarMain();
					resetAllButtons();
					setRoomInfo();
					writeStringAsFile(createCSVLine("PatientType,B"));
					writeStringAsFile(createCSVLine("RoomAssign,D"));
				}
			});

		if(roomList[4].patient.equals(""))
			room5Main.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					colorizeRoomsNormal();
					roomList[4].patient = "B";
					numberOfPatientBs--;
					updateSideBarMain();
					resetAllButtons();
					setRoomInfo();
					writeStringAsFile(createCSVLine("PatientType,B"));
					writeStringAsFile(createCSVLine("RoomAssign,E"));
				}
			});

		if(roomList[5].patient.equals(""))
			room6Main.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					colorizeRoomsNormal();
					roomList[5].patient = "B";
					numberOfPatientBs--;
					updateSideBarMain();
					resetAllButtons();
					setRoomInfo();
					writeStringAsFile(createCSVLine("PatientType,B"));
					writeStringAsFile(createCSVLine("RoomAssign,F"));
				}
			});
	}

	private void setRoomClicksForNurse(){
		if(!roomList[0].patient.equals("") && roomList[0].numNurse == 0)
			room1Main.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					colorizeRoomsNormal();
					roomList[0].numNurse = 1;
					numberOfNurses--;
					updateSideBarMain();
					resetAllButtons();
					setRoomInfo();

					if(roomList[0].numDoctor == 0){
						writeStringAsFile(createCSVLine("FirstResource,A"));
					}else{
						writeStringAsFile(createCSVLine("SecondResource,A"));
					}
				}
			});

		if(!roomList[1].patient.equals("") && roomList[1].numNurse == 0)
			room2Main.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					colorizeRoomsNormal();
					roomList[1].numNurse = 1;
					numberOfNurses--;
					updateSideBarMain();
					resetAllButtons();
					setRoomInfo();

					if(roomList[1].numDoctor == 0){
						writeStringAsFile(createCSVLine("FirstResource,B"));
					}else{
						writeStringAsFile(createCSVLine("SecondResource,B"));
					}
				}
			});

		if(!roomList[2].patient.equals("") && roomList[2].numNurse == 0)
			room3Main.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					colorizeRoomsNormal();
					roomList[2].numNurse = 1;
					numberOfNurses--;
					updateSideBarMain();
					resetAllButtons();
					setRoomInfo();

					if(roomList[2].numDoctor == 0){
						writeStringAsFile(createCSVLine("FirstResource,C"));
					}else{
						writeStringAsFile(createCSVLine("SecondResource,C"));
					}
				}
			});

		if(!roomList[3].patient.equals("") && roomList[3].numNurse == 0)
			room4Main.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					colorizeRoomsNormal();
					roomList[3].numNurse = 1;
					numberOfNurses--;
					updateSideBarMain();
					resetAllButtons();
					setRoomInfo();

					if(roomList[3].numDoctor == 0){
						writeStringAsFile(createCSVLine("FirstResource,D"));
					}else{
						writeStringAsFile(createCSVLine("SecondResource,D"));
					}
				}
			});

		if(!roomList[4].patient.equals("") && roomList[4].numNurse == 0)
			room5Main.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					colorizeRoomsNormal();
					roomList[4].numNurse = 1;
					numberOfNurses--;
					updateSideBarMain();
					resetAllButtons();
					setRoomInfo();

					if(roomList[4].numDoctor == 0){
						writeStringAsFile(createCSVLine("FirstResource,E"));
					}else{
						writeStringAsFile(createCSVLine("SecondResource,E"));
					}
				}
			});

		if(!roomList[5].patient.equals("") && roomList[5].numNurse == 0)
			room6Main.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					colorizeRoomsNormal();
					roomList[5].numNurse = 1;
					numberOfNurses--;
					updateSideBarMain();
					resetAllButtons();
					setRoomInfo();

					if(roomList[5].numDoctor == 0){
						writeStringAsFile(createCSVLine("FirstResource,F"));
					}else{
						writeStringAsFile(createCSVLine("SecondResource,F"));
					}
				}
			});
	}

	private void setRoomClicksForDoctors(){
		if(roomList[0].patient.equals("A") && roomList[0].numDoctor == 0)
			room1Main.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					colorizeRoomsNormal();
					roomList[0].numDoctor = 1;
					numberOfDoctors--;
					updateSideBarMain();
					resetAllButtons();
					setRoomInfo();

					if(roomList[0].numNurse == 0){
						writeStringAsFile(createCSVLine("FirstResource,A"));
					}else{
						writeStringAsFile(createCSVLine("SecondResource,A"));
					}
				}
			});

		if(roomList[1].patient.equals("A") && roomList[1].numDoctor == 0)
			room2Main.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					colorizeRoomsNormal();
					roomList[1].numDoctor = 1;
					numberOfDoctors--;
					updateSideBarMain();
					resetAllButtons();
					setRoomInfo();

					if(roomList[1].numNurse == 0){
						writeStringAsFile(createCSVLine("FirstResource,B"));
					}else{
						writeStringAsFile(createCSVLine("SecondResource,B"));
					}
				}
			});

		if(roomList[2].patient.equals("A") && roomList[2].numDoctor == 0)
			room3Main.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					colorizeRoomsNormal();
					roomList[2].numDoctor = 1;
					numberOfDoctors--;
					updateSideBarMain();
					resetAllButtons();
					setRoomInfo();

					if(roomList[2].numNurse == 0){
						writeStringAsFile(createCSVLine("FirstResource,C"));
					}else{
						writeStringAsFile(createCSVLine("SecondResource,C"));
					}
				}
			});

		if(roomList[3].patient.equals("A") && roomList[3].numDoctor == 0)
			room4Main.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					colorizeRoomsNormal();
					roomList[3].numDoctor = 1;
					numberOfDoctors--;
					updateSideBarMain();
					resetAllButtons();
					setRoomInfo();

					if(roomList[3].numNurse == 0){
						writeStringAsFile(createCSVLine("FirstResource,D"));
					}else{
						writeStringAsFile(createCSVLine("SecondResource,D"));
					}
				}
			});

		if(roomList[4].patient.equals("A") && roomList[4].numDoctor == 0)
			room5Main.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					colorizeRoomsNormal();
					roomList[4].numDoctor = 1;
					numberOfDoctors--;
					updateSideBarMain();
					resetAllButtons();
					setRoomInfo();

					if(roomList[4].numNurse == 0){
						writeStringAsFile(createCSVLine("FirstResource,E"));
					}else{
						writeStringAsFile(createCSVLine("SecondResource,E"));
					}
				}
			});

		if(roomList[5].patient.equals("A") && roomList[5].numDoctor == 0)
			room6Main.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					colorizeRoomsNormal();
					roomList[5].numDoctor = 1;
					numberOfDoctors--;
					updateSideBarMain();
					resetAllButtons();
					setRoomInfo();

					if(roomList[5].numNurse == 0){
						writeStringAsFile(createCSVLine("FirstResource,F"));
					}else{
						writeStringAsFile(createCSVLine("SecondResource,F"));
					}
				}
			});
	}

	private void setRoomClicksForSurgeons(){
		if(roomList[0].patient.equals("B") && roomList[0].numSurgeon == 0)
			room1Main.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					colorizeRoomsNormal();
					roomList[0].numSurgeon = 1;
					numberOfSurgeons--;
					updateSideBarMain();
					resetAllButtons();
					setRoomInfo();

					if(roomList[0].numNurse == 0){
						writeStringAsFile(createCSVLine("FirstResource,A"));
					}else{
						writeStringAsFile(createCSVLine("SecondResource,A"));
					}
				}
			});

		if(roomList[1].patient.equals("B") && roomList[1].numSurgeon == 0)
			room2Main.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					colorizeRoomsNormal();
					roomList[1].numSurgeon = 1;
					numberOfSurgeons--;
					updateSideBarMain();
					resetAllButtons();
					setRoomInfo();

					if(roomList[1].numNurse == 0){
						writeStringAsFile(createCSVLine("FirstResource,B"));
					}else{
						writeStringAsFile(createCSVLine("SecondResource,B"));
					}
				}
			});

		if(roomList[2].patient.equals("B") && roomList[2].numSurgeon == 0)
			room3Main.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					colorizeRoomsNormal();
					roomList[2].numSurgeon = 1;
					numberOfSurgeons--;
					updateSideBarMain();
					resetAllButtons();
					setRoomInfo();

					if(roomList[2].numNurse == 0){
						writeStringAsFile(createCSVLine("FirstResource,C"));
					}else{
						writeStringAsFile(createCSVLine("SecondResource,C"));
					}
				}
			});

		if(roomList[3].patient.equals("B") && roomList[3].numSurgeon == 0)
			room4Main.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					colorizeRoomsNormal();
					roomList[3].numSurgeon = 1;
					numberOfSurgeons--;
					updateSideBarMain();
					resetAllButtons();
					setRoomInfo();

					if(roomList[3].numNurse == 0){
						writeStringAsFile(createCSVLine("FirstResource,D"));
					}else{
						writeStringAsFile(createCSVLine("SecondResource,D"));
					}
				}
			});

		if(roomList[4].patient.equals("B") && roomList[4].numSurgeon == 0)
			room5Main.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					colorizeRoomsNormal();
					roomList[4].numSurgeon = 1;
					numberOfSurgeons--;
					updateSideBarMain();
					resetAllButtons();
					setRoomInfo();

					if(roomList[4].numNurse == 0){
						writeStringAsFile(createCSVLine("FirstResource,E"));
					}else{
						writeStringAsFile(createCSVLine("SecondResource,E"));
					}
				}
			});

		if(roomList[5].patient.equals("B") && roomList[5].numSurgeon == 0)
			room6Main.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					colorizeRoomsNormal();
					roomList[5].numSurgeon = 1;
					numberOfSurgeons--;
					updateSideBarMain();
					resetAllButtons();
					setRoomInfo();

					if(roomList[5].numNurse == 0){
						writeStringAsFile(createCSVLine("FirstResource,F"));
					}else{
						writeStringAsFile(createCSVLine("SecondResource,F"));
					}
				}
			});
	}

	private void resetAllButtons(){
		patientAButton.setEnabled(true);
		patientAButton.setTextColor(getResources().getColor(R.color.black));
		patientAButton.setBackgroundResource(R.drawable.selector_button);
		patientBButton.setEnabled(true);
		patientBButton.setTextColor(getResources().getColor(R.color.black));
		patientBButton.setBackgroundResource(R.drawable.selector_button);
		doctorsButton.setEnabled(true);
		doctorsButton.setTextColor(getResources().getColor(R.color.black));
		doctorsButton.setBackgroundResource(R.drawable.selector_button);
		nursesButton.setEnabled(true);
		nursesButton.setTextColor(getResources().getColor(R.color.black));
		nursesButton.setBackgroundResource(R.drawable.selector_button);
		surgeonsButton.setEnabled(true);
		surgeonsButton.setTextColor(getResources().getColor(R.color.black));
		surgeonsButton.setBackgroundResource(R.drawable.selector_button);
		showMainBottomButtons();
	}

	private void disableRoomClicks(){
		room1Main.setClickable(false);
		room2Main.setClickable(false);
		room3Main.setClickable(false);
		room4Main.setClickable(false);
		room5Main.setClickable(false);
		room6Main.setClickable(false);
	}

	private void startTimerForRoom(final int index, final TextView timeLeftForRoom){
		if(roomList[index].numNurse == 1 && !roomList[index].timeStarted){
			if(roomList[index].patient.equals("A") && roomList[index].numDoctor == 1){
				roomList[index].timeStarted = true;
				new CountDownTimer(patientTimeLeftMilliseconds, 1000) {

					public void onTick(long millisUntilFinished) {
						String secondsLeft = String.format("%02d", millisUntilFinished / 1000 % 60);
						timeLeftForRoom.setText("0:"+secondsLeft);
					}

					public void onFinish() {
						if(!mainTimeLeft.getText().toString().equals("00:00") && start){
							score++;
							flashScore(scoreTextView);
							scoreTextView.setText(score+"");
							roomList[index].collect = true;
							setRoomInfo();

							String room = "";
							switch(index){
							case 0:
								room = "A";
								break;
							case 1:
								room = "B";
								break;
							case 2:
								room = "C";
								break;
							case 3:
								room = "D";
								break;
							case 4:
								room = "E";
								break;
							case 5:
								room = "F";
								break;
							default:
								break;
							}

							writeStringAsFile(createCSVLine("TreatComplete,"+room));
						}
					}
				}.start();
			}else if(roomList[index].patient.equals("B") && roomList[index].numSurgeon == 1){
				roomList[index].timeStarted = true;
				new CountDownTimer(patientTimeLeftMilliseconds, 1000) {

					public void onTick(long millisUntilFinished) {
						String secondsLeft = String.format("%02d", millisUntilFinished / 1000 % 60);
						timeLeftForRoom.setText("0:"+secondsLeft);
					}

					public void onFinish() {
						if(!mainTimeLeft.getText().toString().equals("00:00") && start){
							score++;
							flashScore(scoreTextView);
							scoreTextView.setText(score+"");
							roomList[index].collect = true;
							setRoomInfo();

							String room = "";
							switch(index){
							case 0:
								room = "A";
								break;
							case 1:
								room = "B";
								break;
							case 2:
								room = "C";
								break;
							case 3:
								room = "D";
								break;
							case 4:
								room = "E";
								break;
							case 5:
								room = "F";
								break;
							default:
								break;
							}

							writeStringAsFile(createCSVLine("TreatComplete,"+room));
						}
					}
				}.start();
			}
		}
	}

	private void startButton(){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mActivity);

		// set title
		alertDialogBuilder.setTitle("Get Ready to Play!");

		// set dialog message
		alertDialogBuilder
		.setMessage("The goal is to save as many patients as possible. Good luck!")
		.setCancelable(false)
		.setPositiveButton("Start",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				start = true;
				writeStringAsFile(createCSVLine("GameStart,0"));
				//main countdown timer
				new CountDownTimer(startTimeMilliseconds, 1000) {

					public void onTick(long millisUntilFinished) {
						String minutesLeft = String.format("%02d", millisUntilFinished / 60000);
						String secondsLeft = String.format("%02d", millisUntilFinished / 1000 % 60);
						mainTimeLeft.setText(minutesLeft+":"+secondsLeft);
						totalTimeLeftInMilliseconds = (int) millisUntilFinished;
					}

					public void onFinish() {
						if(start){
							//Alert Dialog
							mainTimeLeft.setText("00:00");
							showEndGameDialog();

							writeStringAsFile(createCSVLine("GameOver,0"));
						}
					}
				}.start();

				newPatient();
				newPatientForNH();
				NHHelpPatient(8000);
				dialog.cancel();
			}
		});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}

	private void newPatient(){
		int milliseconds;
		int quintupletTimeLeft = startTimeMilliseconds/5;

		if(practiceRound){
			quintupletTimeLeft = 480000/5;
			milliseconds = quintupletTimeLeft/numPatientsForMediumQuintuplet;
		}else{
			if(earlySlowPattern){
				switch(whichQuintupletTimeLeft()){
				case 1:
				case 3:
				case 5:
				default:
					milliseconds = quintupletTimeLeft/numPatientsForMediumQuintuplet;
					break;
				case 2:
					milliseconds = quintupletTimeLeft/numPatientsForLowQuintuplet;
					break;
				case 4:
					milliseconds = quintupletTimeLeft/numPatientsForHighQuintuplet;
					break;

				}
			}else{
				switch(whichQuintupletTimeLeft()){
				case 1:
				case 3:
				case 5:
				default:
					milliseconds = quintupletTimeLeft/numPatientsForMediumQuintuplet;
					break;
				case 4:
					milliseconds = quintupletTimeLeft/numPatientsForLowQuintuplet;
					break;
				case 2:
					milliseconds = quintupletTimeLeft/numPatientsForHighQuintuplet;
					break;

				}
			}
		}

		new CountDownTimer(milliseconds, milliseconds) {

			public void onTick(long millisUntilFinished) {
			}

			public void onFinish() {
				if(!mainTimeLeft.getText().toString().equals("00:00") && start){
					int patient = 1 + (int)(Math.random() * ((1 - 0) + 1));
					if(patient % 2 == 0){
						if(numberOfPatientAs+numberOfPatientBs < 6){
							numberOfPatientAs++;
							writeStringAsFile(createCSVLine("PlayerPatientAdded,A"));
						}else{
							totalMissedPatients++;
							writeStringAsFile(createCSVLine("PlayerMissedPatient,A"));
						}
						updateSideBarMain();
						newPatient();
					}else if(patient % 2 == 1){
						if(numberOfPatientAs+numberOfPatientBs < 6){
							numberOfPatientBs++;
							writeStringAsFile(createCSVLine("PlayerPatientAdded,B"));
						}else{
							totalMissedPatients++;
							writeStringAsFile(createCSVLine("PlayerMissedPatient,B"));
						}
						updateSideBarMain();
						newPatient();
					}
				}
			}
		}.start();
	}

	private void newPatientForNH(){
		int milliseconds;
		int quintupletTimeLeft = startTimeMilliseconds/5;

		if(practiceRound){
			quintupletTimeLeft = 480000/5;
			milliseconds = quintupletTimeLeft/numPatientsForMediumQuintuplet;
		}else{
			if(earlySlowPattern){
				switch(whichQuintupletTimeLeft()){
				case 1:
				case 3:
				case 5:
				default:
					milliseconds = quintupletTimeLeft/numPatientsForMediumQuintuplet;
					break;
				case 4:
					milliseconds = quintupletTimeLeft/numPatientsForLowQuintuplet;
					break;
				case 2:
					milliseconds = quintupletTimeLeft/8;
					break;

				}
			}else{
				switch(whichQuintupletTimeLeft()){
				case 1:
				case 3:
				case 5:
				default:
					milliseconds = quintupletTimeLeft/numPatientsForMediumQuintuplet;
					break;
				case 2:
					milliseconds = quintupletTimeLeft/numPatientsForLowQuintuplet;
					break;
				case 4:
					milliseconds = quintupletTimeLeft/numPatientsForHighQuintuplet;
					break;

				}
			}
		}

		new CountDownTimer(milliseconds, milliseconds) {

			public void onTick(long millisUntilFinished) {
			}

			public void onFinish() {
				if(!mainTimeLeft.getText().toString().equals("00:00") && start){
					int patient = 1 + (int)(Math.random() * ((1 - 0) + 1));
					if(patient % 2 == 0){
						if(otherNumberOfPatientAs+otherNumberOfPatientBs < 6){
							otherNumberOfPatientAs++;
							writeStringAsFile(createCSVLine("AgentPatientAdded,A"));
						}else{
							NHTotalMissedPatients++;
							writeStringAsFile(createCSVLine("AgentMissedPatient,A"));
						}
						updateSideBarMain();
						newPatientForNH();
					}else if(patient % 2 == 1){
						if(otherNumberOfPatientAs+otherNumberOfPatientBs < 6){
							otherNumberOfPatientBs++;
							writeStringAsFile(createCSVLine("AgentPatientAdded,B"));
						}else{
							NHTotalMissedPatients++;
							writeStringAsFile(createCSVLine("AgentMissedPatient,B"));
						}
						updateSideBarMain();
						newPatientForNH();
					}
				}
			}
		}.start();
	}

	private int whichQuintupletTimeLeft(){
		int quintupletTimeLeft = startTimeMilliseconds/5;
		if(totalTimeLeftInMilliseconds > startTimeMilliseconds - quintupletTimeLeft) //First Quintuplet
			return 1;

		if(totalTimeLeftInMilliseconds > startTimeMilliseconds - quintupletTimeLeft*2) //Second Quintuplet
			return 2;

		if(totalTimeLeftInMilliseconds > startTimeMilliseconds - quintupletTimeLeft*3) //Third Quintuplet
			return 3;

		if(totalTimeLeftInMilliseconds > startTimeMilliseconds - quintupletTimeLeft*4) //Fourth Quintuplet
			return 4;

		if(totalTimeLeftInMilliseconds > startTimeMilliseconds - quintupletTimeLeft*5) //Fifth Quintuplet
			return 5;

		return 1;
	}

	public void writeStringAsFile(final String fileContents) {
		if(!mainTimeLeft.getText().toString().equals("00:00") && start){
			try {
				File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), userID+"_"+condition+"_"+trial+".csv");
				file.setReadable(true, false);
				FileWriter out = new FileWriter(file, true);
				out.write(fileContents+"\n");
				out.close();
			} catch (IOException e) {}
		}
	}

	public String createCSVLine(String firstHalf){
		int totalScore = score + otherScore;
		String csvLine = firstHalf + ","
				+ mainTimeLeft.getText().toString() + ","
				+ numberOfNurses + ","
				+ numberOfDoctors + ","
				+ numberOfSurgeons + ","
				+ otherNumberOfNurses + ","
				+ otherNumberOfDoctors + ","
				+ otherNumberOfSurgeons + ","
				+ numberOfPatientAs + ","
				+ numberOfPatientBs + ","
				+ otherNumberOfPatientAs + ","
				+ otherNumberOfPatientBs + ","
				+ score + ","
				+ otherScore + ","
				+ totalScore;

		return csvLine;
	}

	public boolean fulfillRequestAlgorithm(int currentResources, int otherHospitalResources){
		if(otherHospitalResources == 0){
			return false;
		}

		//old way
		//		if(highCooperation){
		//			if((numberOfPatientAs + numberOfPatientBs) > (otherNumberOfPatientAs + otherNumberOfPatientBs))
		//				return true;
		//			else if((numberOfPatientAs + numberOfPatientBs) == (otherNumberOfPatientAs + otherNumberOfPatientBs)){
		//				if(score > otherScore)
		//					return true;
		//				else
		//					return false;
		//			}else
		//				return false;
		//		}else{
		//			double total = (double) currentResources + (double) otherHospitalResources;
		//			double percentage = ((double) otherHospitalResources)/total * 100;
		//
		//			Random random = new Random();
		//			int rand = random.nextInt(100);
		//			if(rand < percentage)
		//				return true;
		//			else
		//				return false;
		//		}

		//new way 5/14/14
		/*
	  		--Cooperative--
		     0-2, 100% accept
		     3-4, 50% accept
		     5-6, 25% accept
		     --Competitive--
		     0-2, 50% accept
		     3-4, 25% accept
		     5-6, 0% accept
		 */
		int total = otherNumberOfPatientAs + otherNumberOfPatientBs;
		Random random = new Random();

		if(highCooperation){
			if(total >= 0 && total <= 2)
				return true;
			else if(total > 2 && total <= 4){
				int rand = random.nextInt(2);
				if(rand == 0)
					return true;
				else
					return false;
			}else{
				int rand = random.nextInt(4);
				if(rand == 0)
					return true;
				else
					return false;
			}
		}else{
			if(total >= 0 && total <= 2){
				int rand = random.nextInt(2);
				if(rand == 0)
					return true;
				else
					return false;
			}else if(total > 2 && total <= 4){
				int rand = random.nextInt(4);
				if(rand == 0)
					return true;
				else
					return false;
			}else{
				return false;
			}
		}
	}

	private void interruptionRequest(int resourceValue){
		//0 = doctor
		//1 = nurse
		//2 = surgeon
		if(!mainTimeLeft.getText().toString().equals("00:00") && start){
			int resource = 0;
			if(highCooperation){

				//first look at what's needed and if user has that resource
				//if user does not have resource, it will look at what else it needs and request that
				//if user still does not have resource, it will not request

				if(resourceValue == 0){
					if(numberOfDoctors > 0)//check if doctor is available to take
						resource = 0;
					else if(otherNumberOfNurses == 0 && numberOfNurses > 0){ //otherwise check if need nurse and request if they have it
						resource = 1;
					}else if(otherNumberOfSurgeons == 0 && otherNumberOfPatientBs > 0 && numberOfSurgeons > 0){//otherwise check if need surgeon and they have it
						resource = 2;
					}else{ //skip request if nothing is needed
						NHHelpPatient(8000);
						return;
					}
				}else if(resourceValue == 1){
					if(numberOfNurses > 0) //check if nurse is available to take
						resource = 1;
					else if(otherNumberOfDoctors == 0 && otherNumberOfPatientAs > 0 && numberOfDoctors > 0){
						resource = 0;
					}else if(otherNumberOfSurgeons == 0 && otherNumberOfPatientBs > 0 && numberOfSurgeons > 0){
						resource = 2;
					}else{
						NHHelpPatient(8000);
						return;
					}
				}else if(resourceValue == 2){
					if(numberOfSurgeons > 0)
						resource = 2;
					else if(otherNumberOfNurses == 0 && numberOfNurses > 0){
						resource = 1;
					}else if(otherNumberOfDoctors == 0 && otherNumberOfPatientAs > 0 && numberOfDoctors > 0){
						resource = 0;
					}else{
						NHHelpPatient(8000);
						return;
					}
				}
			}else{
				//low cooperation requests what they need
				resource = resourceValue;
			}

			interruptOn = true;
			hideMainBottomButtons();
			disableRoomClicks();
			colorizeRoomsNormal();
			interruptionLayout.setVisibility(View.VISIBLE);

			if(resource == 0){
				interruptionText.setText("The neighboring hospital requests your assistance! They would like to have a doctor.");
				writeStringAsFile(createCSVLine("AgentRequest,Doctor"));
				acceptRequest.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						interruptRequestConfirmDialog("doctor");
					}
				});

			}else if(resource == 1){
				interruptionText.setText("The neighboring hospital requests your assistance! They would like to have a nurse.");
				writeStringAsFile(createCSVLine("AgentRequest,Nurse"));
				acceptRequest.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						interruptRequestConfirmDialog("nurse");
					}
				});
			}else if(resource == 2){
				interruptionText.setText("The neighboring hospital requests your assistance! They would like to have a surgeon.");
				writeStringAsFile(createCSVLine("AgentRequest,Surgeon"));
				acceptRequest.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						interruptRequestConfirmDialog("surgeon");
					}
				});
			}

			denyRequest.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					writeStringAsFile(createCSVLine("PlayerResponse,Deny"));
					interruptionLayout.setVisibility(View.GONE);
					interruptOn = false;
					resetAllButtons();
					NHHelpPatient(8000);
				}
			});
		}
	}

	private void interruptRequestConfirmDialog(final String resource){
		new AlertDialog.Builder(this)
		.setMessage("Send "+resource+" to neighboring hospital?")
		.setCancelable(false)
		.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) { 

				if(resource.equals("doctor")){
					if(numberOfDoctors == 0){
						Toast.makeText(mActivity, "You don't have any available doctors to lend.", Toast.LENGTH_SHORT).show();
						writeStringAsFile(createCSVLine("FailResponse,Doctor"));
					}else{
						numberOfDoctors--;
						otherNumberOfDoctors++;
						Toast.makeText(mActivity, "A doctor has been sent to the other hospital.", Toast.LENGTH_SHORT).show();
						writeStringAsFile(createCSVLine("PlayerResponse,Accept"));
					}
				}else if(resource.equals("nurse")){
					if(numberOfNurses == 0){
						Toast.makeText(mActivity, "You don't have any available nurses to lend.", Toast.LENGTH_SHORT).show();
						writeStringAsFile(createCSVLine("FailResponse,Nurse"));
					}else{
						numberOfNurses--;
						otherNumberOfNurses++;
						Toast.makeText(mActivity, "A nurse has been sent to the other hospital.", Toast.LENGTH_SHORT).show();
						writeStringAsFile(createCSVLine("PlayerResponse,Accept"));
					}
				}else if(resource.equals("surgeon")){
					if(numberOfSurgeons == 0){
						Toast.makeText(mActivity, "You don't have any available surgeons to lend.", Toast.LENGTH_SHORT).show();
						writeStringAsFile(createCSVLine("FailResponse,Surgeon"));
					}else{
						numberOfSurgeons--;
						otherNumberOfSurgeons++;
						Toast.makeText(mActivity, "A surgeon has been sent to the other hospital.", Toast.LENGTH_SHORT).show();
						writeStringAsFile(createCSVLine("PlayerResponse,Accept"));
					}
				}

				interruptionLayout.setVisibility(View.GONE);
				interruptOn = false;
				resetAllButtons();
				updateSideBarMain();
				NHHelpPatient(3000);
			}
		})
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) { 
				interruptionLayout.setVisibility(View.GONE);
				interruptOn = false;
				resetAllButtons();
				NHHelpPatient(8000);
				dialog.dismiss();
			}
		})
		.show();
	}

	private void NHHelpPatient(int millisecond){
		new CountDownTimer(millisecond, 10000) {

			public void onTick(long millisUntilFinished) {
			}

			public void onFinish() {
				if(!mainTimeLeft.getText().toString().equals("00:00") && start){
					int patientChoice;

					if(otherNumberOfPatientAs > 0 && otherNumberOfPatientBs > 0 && otherNumOfRooms != 6){
						Random random = new Random();
						patientChoice = random.nextInt(1);
					}else if(otherNumberOfPatientAs > 0)
						patientChoice = 0;
					else if(otherNumberOfPatientBs > 0)
						patientChoice = 1;
					else
						patientChoice = 2;

					if(patientChoice == 0){
						//Patient A
						if(otherNumberOfDoctors > 0 && otherNumberOfNurses > 0)
						{
							otherNumberOfPatientAs--;
							otherNumberOfDoctors--;
							otherNumberOfNurses--;
							NHCollectPatient("A");
						}else{
							if(otherNumberOfSurgeons > 0 && otherNumberOfNurses > 0 && otherNumberOfPatientBs > 0)
							{
								otherNumberOfPatientBs--;
								otherNumberOfSurgeons--;
								otherNumberOfNurses--;
								NHCollectPatient("B");
							}else{
								if(otherNumberOfDoctors == 0)
									interruptionRequest(0);
								else
									interruptionRequest(1);
								patientChoice = -1;
							}
						}
					}else if (patientChoice == 1){
						//Patient B
						if(otherNumberOfSurgeons > 0 && otherNumberOfNurses > 0)
						{
							otherNumberOfPatientBs--;
							otherNumberOfSurgeons--;
							otherNumberOfNurses--;
							NHCollectPatient("B");
						}else{
							if(otherNumberOfDoctors > 0 && otherNumberOfNurses > 0 && otherNumberOfPatientAs > 0)
							{
								otherNumberOfPatientAs--;
								otherNumberOfDoctors--;
								otherNumberOfNurses--;
								NHCollectPatient("A");
							}else{
								if(otherNumberOfSurgeons == 0)
									interruptionRequest(2);
								else
									interruptionRequest(1);
								patientChoice = -1;
							}
						}
					}

					//help patient success, so do 8 second wait before next one
					if(patientChoice != -1)
						NHHelpPatient(8000);

					updateSideBarMain();
				}
			}
		}.start();
	}

	private void NHCollectPatient(final String patient){
		int timeleft = patientTimeLeftMilliseconds;

		new CountDownTimer(timeleft+2000, timeleft+2000) {

			public void onTick(long millisUntilFinished) {
			}

			public void onFinish() {
				if(!mainTimeLeft.getText().toString().equals("00:00") && start){
					if(patient.equals("A")){
						otherNumberOfDoctors++;
					}else{
						otherNumberOfSurgeons++;
					}
					otherNumberOfNurses++;

					otherScore++;
					flashScore(otherScoreTextView);
					otherNumOfRooms--;
					otherScoreTextView.setText(otherScore+"");
					updateSideBarMain();
				}
			}
		}.start();
		otherNumOfRooms++;
	}

	private void endGame(){
		start = false;

		numberOfPatientAs = Integer.parseInt(StartOptionsActivity.startAPatientET.getText().toString());
		numberOfPatientBs = Integer.parseInt(StartOptionsActivity.startBPatientET.getText().toString());
		numberOfDoctors = Integer.parseInt(StartOptionsActivity.startDoctorsET.getText().toString());
		numberOfNurses = Integer.parseInt(StartOptionsActivity.startNursesET.getText().toString());
		numberOfSurgeons = Integer.parseInt(StartOptionsActivity.startSurgeonsET.getText().toString());
		otherNumberOfDoctors = Integer.parseInt(StartOptionsActivity.startOtherDoctorsET.getText().toString());
		otherNumberOfNurses = Integer.parseInt(StartOptionsActivity.startOtherNursesET.getText().toString());
		otherNumberOfSurgeons = Integer.parseInt(StartOptionsActivity.startOtherSurgeonsET.getText().toString());
		otherNumberOfPatientAs = Integer.parseInt(StartOptionsActivity.NHStartAPatientET.getText().toString());
		otherNumberOfPatientBs = Integer.parseInt(StartOptionsActivity.NHStartBPatientET.getText().toString());
		startTimeMilliseconds = Integer.parseInt(StartOptionsActivity.totalTimeET.getText().toString())*1000;
		patientTimeLeftMilliseconds = Integer.parseInt(StartOptionsActivity.patientTimeET.getText().toString())*1000;
		score = 0;
		otherScore = 0;

		if(StartOptionsActivity.cooperationSpinner.getSelectedItemPosition() == 0)
			highCooperation = true;
		else
			highCooperation = false;

		if(StartOptionsActivity.patientFlowPatternSpinner.getSelectedItemPosition() == 0)
			earlySlowPattern = true;
		else
			earlySlowPattern = false;

		if(StartOptionsActivity.practiceSpinner.getSelectedItemPosition() == 0)
			practiceRound = false;
		else
			practiceRound = true;

		Intent intent = new Intent(mActivity, UserSurveyActivity.class);
		startActivity(intent);
		finish();
	}

	public void showEndGameDialog(){
		AlertDialog.Builder builder;
		final AlertDialog alertDialog;

		LayoutInflater inflater = (LayoutInflater)	mActivity.getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.dialog_end_score, null);

		builder = new AlertDialog.Builder(mActivity);
		builder.setView(layout);
		alertDialog = builder.create();

		TextView scoreText = (TextView) layout.findViewById(R.id.score_text);
		scoreText.setText("The round is over. You helped "+score+" patients!");

		TextView totalScore = (TextView) layout.findViewById(R.id.total_score_text);
		int totalScoreV = score + otherScore;
		totalScore.setText("The total number of patients saved is "+totalScoreV+".");

		WebView chart = (WebView) layout.findViewById(R.id.webView1);
		String url = "https://chart.googleapis.com/chart?cht=bhs&chs=300x100&chd=t:"+score+","+totalMissedPatients+"|"+otherScore+","+NHTotalMissedPatients
				+"&chxl=1:|PatientsMissed - "+(totalMissedPatients+NHTotalMissedPatients)+"|Patients Saved - "+(score+otherScore)
				+"&chdl=You|Neighbor&chds=a&chco=4D89F9,C6D9FD&chxt=x,y&";
		chart.loadUrl(url);

		Button done = (Button) layout.findViewById(R.id.done_button);
		done.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				alertDialog.dismiss();
				endGame();
			}
		});

		alertDialog.setCancelable(false);
		alertDialog.show();
	}

	private void flashScore(final TextView tv){
		tv.setBackgroundResource(R.drawable.green_background);

		new CountDownTimer(500, 500) {

			@Override
			public void onTick(long millisUntilFinished) {
			}

			@Override
			public void onFinish() {
				tv.setBackgroundResource(R.drawable.background_with_border);
			}
		}.start();
	}
}
