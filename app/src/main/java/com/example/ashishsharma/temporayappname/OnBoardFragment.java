package com.example.ashishsharma.temporayappname;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class OnBoardFragment extends Fragment {

    private int page_number = 0;
    private View rootView;

    public static OnBoardFragment newInstance(int position) {
        OnBoardFragment fragment = new OnBoardFragment();
        Bundle args = new Bundle();
        args.putInt("page_num", position);
        fragment.setArguments(args);
        return fragment;
    }
    public OnBoardFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page_number = getArguments().getInt("page_num", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_on_board, container, false);
        populateView();
        return rootView;
    }


    private void populateView() {

        switch (page_number) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                handleLoginScreen();
                break;
        }
    }
    private void handleLoginScreen() {
//        fbLoginButton = (LoginButton)rootView.findViewById(R.id.fb_login_button);
//        fbLoginButton.setVisibility(View.VISIBLE);
    }













}
