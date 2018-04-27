package com.example.a77011_40_05.proxiservices.Utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a77011_40_05.proxiservices.R;

public class SectionWidget {
    private Context context;
    private LinearLayout head;
    private String headTitle;
    private int headSize;
    private LinearLayout content;
    private int idIcon;
    private View container;


    public SectionWidget(Context context, String title, int headSize) {
        this.context = context;
        this.headTitle = title;
        this.headSize = headSize;
        build();
        bind();
    }

    public SectionWidget(Context context, String title) {
        this.context = context;
        this.headTitle = title;
        this.container = buildFromLayout();
        bind();
    }

    public LinearLayout getHead() {
        return head;
    }

    public void setHead(LinearLayout head) {
        this.head = head;
    }

    public LinearLayout getContent() {
        return content;
    }

    public void setContent(LinearLayout content) {
        this.content = content;
    }

    public View getContainer() {
        return container;
    }

    public void setContainer(View container) {
        this.container = container;
    }

    private void build(){
        LinearLayout.LayoutParams params;

        head = new LinearLayout(context);
        head.setOrientation(LinearLayout.HORIZONTAL);
        head.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,Functions.dpToPixels(headSize)));

        TextView txtTitle = new TextView(context);
        txtTitle.setText(headTitle);
        txtTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,5));
        txtTitle.setTextSize(20);
        txtTitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        txtTitle.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        txtTitle.setGravity(Gravity.CENTER);
        head.addView(txtTitle);

        View separator = new View(context);
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,Functions.dpToPixels(1),4);
        params.gravity = Gravity.CENTER;
        separator.setLayoutParams(params);
        separator.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        head.addView(separator);

        ImageView icon = new ImageView(context);
        icon.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,7));
        icon.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_action_collapse));
        head.addView(icon);

        content = new LinearLayout(context);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        content.setVisibility(View.GONE);

    }

    private View buildFromLayout(){
        View view = LayoutInflater.from(context).inflate(R.layout.model_section,null);

        head = view.findViewById(R.id.head);
        TextView title = (TextView) head.getChildAt(0);
        title.setText(headTitle);
        content = view.findViewById(R.id.content);
        return view;
    }

    private void bind(){

        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(content.getVisibility() == View.GONE){
                    Functions.expand(content);
                    Functions.rotate(head.getChildAt(2),0.0f,90.0f,300);
                }else{
                    Functions.collapse(content);
                    Functions.rotate(head.getChildAt(2),90.0f,0.0f,300);
                }
            }
        });
    }

    public void addTo(ViewGroup view){
        view.addView(head);
        view.addView(content);
    }
}
