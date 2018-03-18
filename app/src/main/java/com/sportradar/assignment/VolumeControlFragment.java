package com.sportradar.assignment;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.sportradar.assignment.views.VolumeControlView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VolumeControlFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VolumeControlFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText volumeEditText;
    private EditText linesEditText;
    private Button setVolumeButton;
    private Button setLinesButton;
    private VolumeControlView volumeControlView;

    public VolumeControlFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VolumeControlFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VolumeControlFragment newInstance(String param1, String param2) {
        VolumeControlFragment fragment = new VolumeControlFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //this.linesEditText = getView().findViewById(R.id.edit_text_lines);
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_volume_control, container, false);
        this.linesEditText = fragmentView.findViewById(R.id.edit_text_lines);
        this.setLinesButton = fragmentView.findViewById(R.id.button_set_lines);
        this.volumeEditText = fragmentView.findViewById(R.id.edit_text_volume);
        this.setVolumeButton = fragmentView.findViewById(R.id.button_set_volume);
        this.volumeControlView = fragmentView.findViewById(R.id.view_volume_control);

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

}
