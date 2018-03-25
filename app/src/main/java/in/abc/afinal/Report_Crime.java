package in.abc.afinal;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Report_Crime extends AppCompatActivity {
    private static final String TAG = "main" ;
    String desc,crime;
    TimePicker tp;
    Button btn_rpt,btn_loc;
    DatePicker dp;
    RadioGroup rg;
    EditText et_desc;
    RadioButton rb1,rb2,rb3,rb4;
    int id;
    int hour,min;
    int year,month,date;
    float lat,lon;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report__crime);
        auth = FirebaseAuth.getInstance();

        btn_rpt = (Button)findViewById(R.id.btn_rpt);
        tp = (TimePicker)findViewById(R.id.tp);
        dp = (DatePicker)findViewById(R.id.dp);
        rg = (RadioGroup)findViewById(R.id.rg);
        et_desc = (EditText)findViewById(R.id.et_desc);
        rb1 = (RadioButton)findViewById(R.id.rb1);
        rb2 = (RadioButton)findViewById(R.id.rb2);
        rb3 = (RadioButton)findViewById(R.id.rb3);
        rb4 = (RadioButton)findViewById(R.id.rb4);
        btn_loc = (Button)findViewById(R.id.btn_loc) ;

        hour = tp.getCurrentHour();
        min = tp.getCurrentMinute();
        year = dp.getYear();
        month = dp.getMonth();
        date = dp.getDayOfMonth();


        LocationManager locman = (LocationManager)getSystemService(LOCATION_SERVICE);
        LocationListener loclis =new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lat = (float)location.getLatitude();
                lon = (float)location.getLongitude();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };


        tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                hour = i;
                min = i1;
            }
        });
        dp.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                year = i;
                month = i1;
                date = i2;
            }
        });


        btn_rpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                desc = et_desc.getText().toString().trim();
                id = rg.getCheckedRadioButtonId();
                if(id== R.id.rb1){
                    crime = rb1.getText().toString();
                }
                if(id== R.id.rb2){
                    crime = rb2.getText().toString();
                }
                if(id== R.id.rb3){
                    crime = rb3.getText().toString();
                }
                if(id== R.id.rb4){
                    crime = rb4.getText().toString();
                }

               final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference("server/saving-data/fireblog");

               DatabaseReference usersRef = ref.child("crimes");

                 FirebaseUser use = FirebaseAuth.getInstance().getCurrentUser();
                 String UID = use.getUid();

                 DatabaseReference newRef = usersRef.push();
                usersRef.child(UID).setValue(new Crime(crime, year, month, date, hour, min, 0, 0, desc), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        Toast.makeText(getApplicationContext(), "Crime Reported Successfully", Toast.LENGTH_SHORT).show();
                    }
                });

                Intent i = new Intent(Report_Crime.this, Options.class);
                startActivity(i);
            }
        });

        btn_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
