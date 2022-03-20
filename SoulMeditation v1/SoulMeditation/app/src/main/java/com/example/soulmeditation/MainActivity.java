package com.example.soulmeditation;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;
import java.io.InputStream;

public class MainActivity extends Activity {

    //Class variables
    private EditText frequencyCarrierInput;
    private EditText frequencyBeatInput;
    private TextView displayCarrierFrequency;
    private TextView displayBeatFrequency;
    private RadioGroup radioBeatGroup;
    private ToggleButton startStop;
    private boolean isDataChanged = true, isCarrierValid = true, isBeatValid = true;
    private BeatsEngine wave;
     Button alpha;
     Button  beta;
     Button  delta;
     Button theta;
     Button gamma;

    View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (isDataChanged && startStop.isActivated()){
                refresh();
                attemptStartWave();
            }
        }
    };
    TextWatcher textWatcherCarrier = new TextWatcher() {

        public void afterTextChanged(Editable s)
        {
            isCarrierValid = isDataChanged = false;
            if( frequencyCarrierInput.getText().toString().length() == 0 )
                frequencyCarrierInput.setError( "Carrier Frequency is required!" );
            else {
                try {
                    float carrierFrequency = Float.parseFloat(frequencyCarrierInput.getText().toString());
                    if (carrierFrequency < 20)
                        frequencyCarrierInput.setError( "Carrier Frequency must be greater than 20!" );
                    else if (carrierFrequency > 1200)
                        frequencyCarrierInput.setError( "Carrier Frequency must be less than 1200!" );
                    else {
                        isCarrierValid = true;
                        if (isBeatValid) {
                            isDataChanged = true;
                        }
                    }
                }catch (NumberFormatException ex) {
                    frequencyCarrierInput.setError( "Carrier Frequency is required!" );
                }
            }
        }

        public void beforeTextChanged(CharSequence s, int start,
                                      int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {
        }

    };
    TextWatcher textWatcherBeat = new TextWatcher() {

        public void afterTextChanged(Editable s)
        {
            isBeatValid = isDataChanged = false;
            if( frequencyBeatInput.getText().toString().length() == 0 )
                frequencyBeatInput.setError( "Beat Frequency is required!" );
            else {
                float carrierFrequency;
                try {
                    carrierFrequency= Float.parseFloat(frequencyBeatInput.getText().toString());
                    int selectedId = radioBeatGroup.getCheckedRadioButtonId();
                    RadioButton selectedButton = (RadioButton) findViewById(selectedId);
                    double minVal = getString(R.string.binaural_radio).equals(selectedButton.getText()) ?  0 : .5;
                    if (carrierFrequency < minVal)
                        frequencyBeatInput.setError(String.format("Beat Frequency must be greater than %d1!", minVal ));
                    else if (carrierFrequency > 1200)
                        frequencyBeatInput.setError( "Beat Frequency must be less than 1200!" );
                    else {
                        isBeatValid = true;
                        if (isCarrierValid)
                        {
                            isDataChanged = true;
                        }
                    }
                } catch (NumberFormatException ex)
                {
                    frequencyBeatInput.setError( "Beat Frequency is required!" );
                }
            }
        }

        public void beforeTextChanged(CharSequence s, int start,
                                      int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.main_activity);
        initializeView();
        final MediaPlayer xp = MediaPlayer.create(this,R.raw.alpha);
        Button  alpha=findViewById(R.id.alpha);
        alpha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (xp.isPlaying()) {
                        xp.stop();
                        xp.release();
                    } xp.start();
                } catch(Exception e) { e.printStackTrace(); }
            }
        });
        final MediaPlayer xp1 = MediaPlayer.create(this,R.raw.beta);
        Button  beta=findViewById(R.id.beta);
        beta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (xp1.isPlaying()) {
                        xp1.stop();
                        xp1.release();
                    } xp1.start();
                } catch(Exception e) { e.printStackTrace(); }
            }
        });
        final MediaPlayer xp2 = MediaPlayer.create(this,R.raw.delta);
        Button  delta=findViewById(R.id.delta);
        delta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (xp2.isPlaying()) {
                        xp2.stop();
                        xp2.release();
                    } xp2.start();
                } catch(Exception e) { e.printStackTrace(); }
            }
        });
        final MediaPlayer xp3 = MediaPlayer.create(this,R.raw.gamma);
        Button  gamma=findViewById(R.id.gamma);
        gamma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (xp3.isPlaying()) {
                        xp3.stop();
                        xp3.release();
                    } xp3.start();
                } catch(Exception e) { e.printStackTrace(); }
            }
        });
        final MediaPlayer xp4 = MediaPlayer.create(this,R.raw.theta);
        Button  theta=findViewById(R.id.theta);
        theta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (xp4.isPlaying()) {
                        xp4.stop();
                        xp4.release();
                    } xp4.start();
                } catch(Exception e) { e.printStackTrace(); }
            }
        });

    }

    private void initializeView() {
        frequencyCarrierInput = (EditText) findViewById(R.id.etCarrierFrequency);
        frequencyBeatInput = (EditText) findViewById(R.id.etBeatFrequency);
        radioBeatGroup = (RadioGroup) findViewById(R.id.radioBeatType);
        startStop = (ToggleButton) findViewById(R.id.btnPlay);
        displayCarrierFrequency = (TextView) findViewById(R.id.tvCarrierFrequency);
        displayBeatFrequency = (TextView) findViewById(R.id.tvBeatFrequency);

        //By default, and when someone changes anything, denote that data has been updated
        isDataChanged = true;
        frequencyCarrierInput.addTextChangedListener(textWatcherCarrier);
        frequencyCarrierInput.setOnFocusChangeListener(focusChangeListener);
        frequencyBeatInput.addTextChangedListener(textWatcherBeat);
        frequencyBeatInput.setOnFocusChangeListener(focusChangeListener);
        radioBeatGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioIsochronic)
                {
                    float beatFrequency = Float.parseFloat(frequencyBeatInput.getText().toString());
                    if (beatFrequency < .5) {
                        frequencyBeatInput.setText(".5");
                    }
                }
                isDataChanged = true;
            }
        });
        //Linkify the title using our internal version of linkify
        TextView gmt = (TextView) findViewById(R.id.gmtTitle);
        String scheme = getString(R.string.url);
        Linkify.addLinks(gmt, getString(R.string.gmt), scheme);

        //Prepare noise track
        InputStream inputStream = getResources().openRawResource(R.raw.alpha);
        InputStream hint=getResources().openRawResource(R.raw.beta);
    }
    public void clickPlay(View v) {
        togglePlay();
    }

    private void togglePlay() {
        startStop.setActivated(!startStop.isActivated());

        if (startStop.isActivated()) {
            if (isDataChanged) {
                refresh();
            }
            //wave.start();
            attemptStartWave();
        } else {
            wave.stop();
        }
    }

    private void attemptStartWave() {
        if (!wave.getIsPlaying()) {
            wave.start();
            startStop.setActivated(true);
            startStop.setChecked(true);
        } else {
            startStop.setActivated(false);
            startStop.setChecked(false);
        }
    }
    public void runCountdown(View v) {
        int countdownLength = Integer.parseInt((String)v.getTag()) * 1000 * 60;

        if (!startStop.isActivated())
        {
            startStop.setChecked(true);
            togglePlay();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    private void refresh() {
        float carrierFrequency, beatFrequency;
        int selectedId = radioBeatGroup.getCheckedRadioButtonId();
        RadioButton selectedButton = (RadioButton) findViewById(selectedId);
        carrierFrequency = Float.parseFloat(frequencyCarrierInput.getText().toString());
        beatFrequency = Float.parseFloat(frequencyBeatInput.getText().toString());

        if (wave != null) {
            wave.release();
        }

        wave = getString(R.string.binaural_radio).equals(selectedButton.getText())
                ? new Binaural(carrierFrequency, beatFrequency)
                : new Isochronic(carrierFrequency, beatFrequency);

        isDataChanged = false;

        displayCarrierFrequency.setText(
                getString(R.string.binaural_radio).equals(selectedButton.getText())
                        ? String.format("%.2f / %.2f", carrierFrequency + beatFrequency / 2, carrierFrequency - beatFrequency / 2)
                        : String.valueOf(carrierFrequency)
        );
        displayBeatFrequency.setText(String.valueOf(beatFrequency));
    }
    public  void  mp3(){

    }




}