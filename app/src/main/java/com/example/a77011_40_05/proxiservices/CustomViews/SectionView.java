package com.example.a77011_40_05.proxiservices.CustomViews;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a77011_40_05.proxiservices.R;

public class SectionView extends LinearLayout {

    private int _DEFAULT_HEAD_SIZE = 50; //DP

    //SETTINGS
    private String headText = "Section";
    private int headSize;
    private int headIcon;
    private boolean isCollapse;


    //ELEMENTS
    LinearLayout head, body;


    public SectionView(Context context) {
        super(context);
    }

    public SectionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SectionView,0,0);
        String headText = "TEST";
        try {
            if(a.getString(R.styleable.SectionView_headText) != null){
                headText = a.getString(R.styleable.SectionView_headText);
            }
            headSize = a.getDimensionPixelSize(R.styleable.SectionView_headSize,
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _DEFAULT_HEAD_SIZE, Resources.getSystem().getDisplayMetrics()));
            headIcon = a.getResourceId(R.styleable.SectionView_headIcon,android.R.drawable.ic_media_play);
            isCollapse = a.getBoolean(R.styleable.SectionView_isCollapse,true);

        }finally {
            a.recycle();
        }

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.model_section_view,this,true);

        head = view.findViewById(R.id.head);
        body = view.findViewById(R.id.body);

        TextView headTitle = view.findViewById(R.id.headTitle);
        headTitle.setText(headText);

        head.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,headSize));

        ImageView icon = view.findViewById(R.id.headIcon);
        icon.setLayoutParams(new LayoutParams(headSize, LayoutParams.MATCH_PARENT));
        icon.setImageDrawable(context.getDrawable(headIcon));

        if(!isCollapse){
            toggleBody();
        }

        head.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleBody();
            }
        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //Log.e("#COUNT", ""+getChildCount());
        while(getChildCount() > 1){
            //Log.e("#ELEMENT "+1,getChildAt(1).getClass().getName());
            View v = getChildAt(1);
            ViewGroup.LayoutParams params = v.getLayoutParams();
            removeViewAt(1);
            v.setLayoutParams(params);
            body.addView(v);
        }
    }

    public void toggleBody(){
        if(body.getVisibility() == View.GONE){
            expand(body);
            rotate(head.getChildAt(2),0.0f,90.0f,300);
        }else{
            collapse(body);
            rotate(head.getChildAt(2),90.0f,0.0f,300);
        }
    }

    //ANIMATION
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

    public static void rotate(View view, float from, float to, int duration){
        RotateAnimation rotateAnimation = new RotateAnimation(from, to, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setInterpolator(new DecelerateInterpolator());
        rotateAnimation.setRepeatCount(0);
        rotateAnimation.setDuration(duration);
        rotateAnimation.setFillAfter(true);
        view.startAnimation(rotateAnimation);
    }
}
