package com.example.transportapp.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.transportapp.R;
import com.example.transportapp.models.Experience;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;

public class FormExperience extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    String uidCurrentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        uidCurrentUser=firebaseAuth.getCurrentUser().getUid();

        setContentView(R.layout.activity_form_experience);

        TextView textViewDepartureHour=findViewById(R.id.tvDepartureHour);
        textViewDepartureHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(FormExperience.this, new TimePickerDialog.OnTimeSetListener()
                {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int min) {
                        textViewDepartureHour.setText(hour+":"+min);
                    }
                },hour,minute,false);
                timePickerDialog.show();
            }
        });
        SeekBar seekBar=findViewById(R.id.seekbarCrowdness);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Toast.makeText(FormExperience.this,progress+"",Toast.LENGTH_LONG).show();


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Button buttonAddExperience=findViewById(R.id.btnAdd);
        buttonAddExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Experience experience=new Experience();
                EditText etStartingPoint=findViewById(R.id.etStartingPoint);
                String startingPoint=etStartingPoint.getText().toString();

                EditText editTextDestinationPoint=findViewById(R.id.etDestinationPoint);
                String destinationPoint=editTextDestinationPoint.getText().toString();

                Spinner spinnerTypeTransport=findViewById(R.id.typeTransport);
                String typeTransport=spinnerTypeTransport.getSelectedItem().toString();

                TextView tvDepartureHour=findViewById(R.id.tvDepartureHour);
                String departureHour=tvDepartureHour.getText().toString();

                EditText editTextDurationTripHour=findViewById(R.id.etDurationTripHour);
                EditText editTextDurationTripMinutes=findViewById(R.id.etDurationTripMinutes);
                int durationTripInSeconds=Integer.parseInt(editTextDurationTripHour.getText().toString())*3600+Integer.parseInt(editTextDurationTripMinutes.getText().toString())*60;

                SeekBar seekBarCrowdness=findViewById(R.id.seekbarCrowdness);
                int crowdness=seekBarCrowdness.getProgress();

                RatingBar ratingBarSatisfaction=findViewById(R.id.ratingSatisfaction);
                int satisfaction=ratingBarSatisfaction.getNumStars();

                HashMap<String,Object> experienceDocument=new HashMap<>();
                experienceDocument.put("Starting Point",startingPoint);
                experienceDocument.put("DestinationPoint",destinationPoint);
                experienceDocument.put("Type Transport",typeTransport);
                experienceDocument.put("Departure Hour",departureHour);
                experienceDocument.put("Duration Trip",durationTripInSeconds);
                experienceDocument.put("Crowdness",crowdness);
                experienceDocument.put("Satisfaction",satisfaction);

                firebaseFirestore.collection("users").document(uidCurrentUser)
                        .collection("Experiences").add(experienceDocument);
            }
        });
    }


}