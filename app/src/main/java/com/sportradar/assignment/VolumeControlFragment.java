package com.sportradar.assignment;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.sportradar.assignment.views.VolumeControlView;


public class VolumeControlFragment extends Fragment implements VolumeControlView.OnVolumeChangeListener {

    private EditText volumeEditText;
    private EditText linesEditText;
    private Button setVolumeButton;
    private Button setLinesButton;
    private VolumeControlView volumeControlView;

    public VolumeControlFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_volume_control, container, false);
        this.linesEditText = fragmentView.findViewById(R.id.edit_text_lines);
        this.setLinesButton = fragmentView.findViewById(R.id.button_set_lines);
        this.volumeEditText = fragmentView.findViewById(R.id.edit_text_volume);
        this.setVolumeButton = fragmentView.findViewById(R.id.button_set_volume);
        this.volumeControlView = fragmentView.findViewById(R.id.view_volume_control);
        this.volumeEditText.setText(String.valueOf(this.volumeControlView.getVolumePercent()));
        this.linesEditText.setText((String.valueOf(this.volumeControlView.getLines())));
        this.volumeControlView.setOnVolumeChangeListener(this);

        this.setLinesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int lines = Integer.parseInt(VolumeControlFragment.this.linesEditText.getText().toString());
                    VolumeControlFragment.this.volumeControlView.setLines(lines);
                } catch (Exception e) {
                    //if the text is not number, we don't do anything
                }
            }
        });


        this.setVolumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    float volumePercent = Float.parseFloat(VolumeControlFragment.this.volumeEditText.getText().toString());
                    VolumeControlFragment.this.volumeControlView.setVolumePercent(volumePercent);
                } catch (Exception e) {
                    //if the text is not number, we don't do anything
                }
            }
        });


        return fragmentView;
    }


    @Override
    public void onVolumeChangedByUser(float volumePercent) {
        this.volumeEditText.setText(String.valueOf(volumePercent));
    }

    @Override
    public void onVolumeChangedExternally(float volumePercent) {
        //nothing to do
    }
}
