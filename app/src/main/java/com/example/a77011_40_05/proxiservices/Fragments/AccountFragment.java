package com.example.a77011_40_05.proxiservices.Fragments;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.a77011_40_05.proxiservices.Activities.HomeActivity;
import com.example.a77011_40_05.proxiservices.R;
import com.example.a77011_40_05.proxiservices.Utils.SectionWidget;
import com.example.a77011_40_05.proxiservices.Utils.Session;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

import static com.example.a77011_40_05.proxiservices.Utils.Constants._FRAG_ACCOUNT_GALLERY;
import static com.example.a77011_40_05.proxiservices.Utils.Constants._FRAG_ACCOUNT_PROFILE_PICS;


public class AccountFragment extends Fragment {

    Context context;

    SectionWidget section1;
    SectionWidget section2;
    SectionWidget section3;
    public AccountFragment() {
        // Required empty public constructor
    }



    public static AccountFragment newInstance() {
        AccountFragment fragment = new AccountFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        LinearLayout lltSections = view.findViewById(R.id.lltSections);
        Button btnProfilPics = view.findViewById(R.id.btnProfilPics);
        Button btnGallery = view.findViewById(R.id.btnGallery);
        ImageView imgProfil = view.findViewById(R.id.imgProfil);
        Picasso.with(context).load(Session.getMyUser().getPath())
                .transform(new CropCircleTransformation()).into(imgProfil);

        btnProfilPics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity home = (HomeActivity) getActivity();
                home.changeFragment(_FRAG_ACCOUNT_PROFILE_PICS,null);
            }
        });

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity home = (HomeActivity) getActivity();
                home.changeFragment(_FRAG_ACCOUNT_GALLERY,null);
            }
        });


        section1 = new SectionWidget(context,"Un text un peu long pour tester");
        lltSections.addView(section1.getContainer());

        section2 = new SectionWidget(context,"court");
        lltSections.addView(section2.getContainer());

        section3 = new SectionWidget(context,"Section 3");
        lltSections.addView(section3.getContainer());
        return view;
    }

    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            context = activity.getBaseContext();
        }
    }

    public static void expand(final View v) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void rotate(ImageView img,float from, float to){
        RotateAnimation rotateAnimation = new RotateAnimation(from, to, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setInterpolator(new DecelerateInterpolator());
        rotateAnimation.setRepeatCount(0);
        rotateAnimation.setDuration(300);
        rotateAnimation.setFillAfter(true);
        img.startAnimation(rotateAnimation);
    }

}
