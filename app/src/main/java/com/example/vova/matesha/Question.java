package com.example.vova.matesha;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class Question extends Fragment {
    TextView seekBarReflector, type2Right, type2Your, type3Right, type3Your;
    SeekBar type4Answer;
    int number, divided;
    Intent intent;
    ImageView task;
    DBHelper helper;
    SQLiteDatabase database;
    int type, id;
    RadioButton type1A, type1B, type1V, type1G, type1D;
    RadioGroup type2Answers1, type2Answers2, type2Answers3, type2Answers4, type1answers;
    RelativeLayout type1, type2, type3, type4, right2, right3, subType2, sub_type3;
    SharedPreferences answers;
    TextView hint;
    EditText type3Answer1, type3Answer2;
    Cursor answer;
    LinearLayout type3_2;
    View.OnClickListener type1Listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            SharedPreferences.Editor editor = answers.edit();
            switch (view.getId()) {
                case R.id.type1_a: {

                    editor.putString(number + "", "А");


                }
                break;
                case R.id.type1_b: {

                    editor.putString(number + "", "Б");

                }
                break;
                case R.id.type1_v: {

                    editor.putString(number + "", "В");

                }
                break;
                case R.id.type1_g: {

                    editor.putString(number + "", "Г");

                }
                break;
                case R.id.type1_d: {

                    editor.putString(number + "", "Д");

                }
                break;
            }
            editor.apply();
            editor.commit();
        }
    };

    RadioGroup.OnCheckedChangeListener type2listener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {

            SharedPreferences.Editor editor = answers.edit();
            switch (radioGroup.getId()) {
                case R.id.answer_type2_1: {
                    String answer = null;
                    switch (i) {
                        case R.id.A_1: {
                            answer = "А";
                        }
                        break;
                        case R.id.B_1: {
                            answer = "Б";
                        }
                        break;
                        case R.id.V_1: {
                            answer = "В";
                        }
                        break;
                        case R.id.G_1: {
                            answer = "Г";
                        }
                        break;
                        case R.id.D_1: {
                            answer = "Д";
                        }
                        break;
                    }
                    editor.putString(number + "_1", answer);
                }
                break;
                case R.id.answer_type2_2: {
                    String answer = null;
                    switch (i) {
                        case R.id.A_2: {
                            answer = "А";
                        }
                        break;
                        case R.id.B_2: {
                            answer = "Б";
                        }
                        break;
                        case R.id.V_2: {
                            answer = "В";
                        }
                        break;
                        case R.id.G_2: {
                            answer = "Г";
                        }
                        break;
                        case R.id.D_2: {
                            answer = "Д";
                        }
                        break;
                    }
                    editor.putString(number + "_2", answer);
                }
                break;
                case R.id.answer_type2_3: {
                    String answer = null;
                    switch (i) {
                        case R.id.A_3: {
                            answer = "А";
                        }
                        break;
                        case R.id.B_3: {
                            answer = "Б";
                        }
                        break;
                        case R.id.V_3: {
                            answer = "В";
                        }
                        break;
                        case R.id.G_3: {
                            answer = "Г";
                        }
                        break;
                        case R.id.D_3: {
                            answer = "Д";
                        }
                        break;
                    }
                    editor.putString(number + "_3", answer);
                }
                break;
                case R.id.answer_type2_4: {
                    String answer = null;
                    switch (i) {
                        case R.id.A_4: {
                            answer = "А";
                        }
                        break;
                        case R.id.B_4: {
                            answer = "Б";
                        }
                        break;
                        case R.id.V_4: {
                            answer = "В";
                        }
                        break;
                        case R.id.G_4: {
                            answer = "Г";
                        }
                        break;
                        case R.id.D_4: {
                            answer = "Д";
                        }
                        break;
                    }
                    editor.putString(number + "_4", answer);
                }
                break;
            }
            editor.commit();
        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.question_fragment, container, false);
        Log.e("onCreateView", "onCreateView " + number);
        type1 = v.findViewById(R.id.answers);
        type2 = v.findViewById(R.id.type2);
        type3 = v.findViewById(R.id.type3);
        type4 = v.findViewById(R.id.type4);
        type3_2 = v.findViewById(R.id.type3_2);
        hint = v.findViewById(R.id.hint1);
        right2 = v.findViewById(R.id.right2);
        right3 = v.findViewById(R.id.right3);
        type2Right = v.findViewById(R.id.type2_right_answer);
        type2Your = v.findViewById(R.id.type2_your_answer);
        type3Right = v.findViewById(R.id.type3_right_answer);
        type3Your = v.findViewById(R.id.type3_your_answer);
        subType2 = v.findViewById(R.id.your_answer);
        sub_type3 = v.findViewById(R.id.sub_type3);

        type = determineType();


        if (type == 1) {
            type1answers = v.findViewById(R.id.type1_answers);
            type1A = v.findViewById(R.id.type1_a);
            type1B = v.findViewById(R.id.type1_b);
            type1V = v.findViewById(R.id.type1_v);
            type1G = v.findViewById(R.id.type1_g);
            type1D = v.findViewById(R.id.type1_d);

            if (answers.getBoolean("unfinished test", false)) {
                setAnswers();
            }

            type1A.setOnClickListener(type1Listener);
            type1B.setOnClickListener(type1Listener);
            type1V.setOnClickListener(type1Listener);
            type1G.setOnClickListener(type1Listener);
            type1D.setOnClickListener(type1Listener);
        } else if (type == 2) {
            type2Answers1 = v.findViewById(R.id.answer_type2_1);
            type2Answers2 = v.findViewById(R.id.answer_type2_2);
            type2Answers3 = v.findViewById(R.id.answer_type2_3);
            type2Answers4 = v.findViewById(R.id.answer_type2_4);


            if (answers.getBoolean("unfinished test", false)) {
                setAnswers(v);
            }


            type2Answers1.setOnCheckedChangeListener(type2listener);
            type2Answers2.setOnCheckedChangeListener(type2listener);
            type2Answers3.setOnCheckedChangeListener(type2listener);
            type2Answers4.setOnCheckedChangeListener(type2listener);
        } else if (type == 3) {
            type3Answer1 = v.findViewById(R.id.type3_1_answer);
            type3Answer2 = v.findViewById(R.id.type3_2_answer);

            if (answers.getBoolean("unfinished test", false)) {
                setAnswers();
            }

            type3Answer1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    SharedPreferences.Editor editor = answers.edit();

                    editor.putString(number + "_1", type3Answer1.getText().toString());

                    editor.commit();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            type3Answer2.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    SharedPreferences.Editor editor = answers.edit();

                    editor.putString(number + "_2", type3Answer2.getText().toString());

                    editor.commit();
                }

                @Override
                public void afterTextChanged(Editable editable) {


                }
            });

        } else {
            seekBarReflector = v.findViewById(R.id.seek_bar_helper);
            type4Answer = v.findViewById(R.id.type4_answer);
            if (number == 33) {
                type4Answer.setProgress(3);
                type4Answer.setMax(6);
            } else {
                type4Answer.setProgress(2);
                type4Answer.setMax(4);
            }
            if (answers.getBoolean("unfinished test", false)) {
                setAnswers();
            }


            seekBarReflector.setText(seekBarReflector.getText().toString().substring(0, 10) + String.valueOf(type4Answer.getProgress()));
            type4Answer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    seekBarReflector.setText(seekBarReflector.getText().toString().substring(0, 10) + String.valueOf(seekBar.getProgress()));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    SharedPreferences.Editor editor = answers.edit();
                    editor.putInt(number + "", seekBar.getProgress());
                    editor.apply();
                }
            });
        }

        task = v.findViewById(R.id.task_img);
        setIMG();

        return v;
    }

    @Override
    public void onPause() {
        Log.e("onPause", "onPause " + number);
        super.onPause();
    }

    @Override
    public void onResume() {
//        Log.e("onResume", answers.getBoolean("finished",false)+"");
        if (answers.getBoolean("finished", false)) {
            Log.e("onResume", "onResume " + number);
            setRightAnswers();
        }
        super.onResume();

    }

    private void setAnswers(View v) {

        RadioButton radioButton = null;

        switch (answers.getString(number + "_1", "")) {
            case "A": {
                radioButton = v.findViewById(R.id.A_1);
            }
            break;
            case "B": {
                radioButton = v.findViewById(R.id.B_1);
            }
            break;
            case "V": {
                radioButton = v.findViewById(R.id.V_1);
            }
            break;
            case "G": {
                radioButton = v.findViewById(R.id.G_1);
            }
            break;
            case "D": {
                radioButton = v.findViewById(R.id.D_1);
            }
            break;
        }
        if (radioButton != null)
            radioButton.setChecked(true);

        switch (answers.getString(number + "_2", "")) {
            case "A": {
                radioButton = v.findViewById(R.id.A_2);
            }
            break;
            case "B": {
                radioButton = v.findViewById(R.id.B_2);
            }
            break;
            case "V": {
                radioButton = v.findViewById(R.id.V_2);
            }
            break;
            case "G": {
                radioButton = v.findViewById(R.id.G_2);
            }
            break;
            case "D": {
                radioButton = v.findViewById(R.id.D_2);
            }
            break;
        }
        if (radioButton != null)
            radioButton.setChecked(true);

        switch (answers.getString(number + "_3", "")) {
            case "A": {
                radioButton = v.findViewById(R.id.A_3);
            }
            break;
            case "B": {
                radioButton = v.findViewById(R.id.B_3);
            }
            break;
            case "V": {
                radioButton = v.findViewById(R.id.V_3);
            }
            break;
            case "G": {
                radioButton = v.findViewById(R.id.G_3);
            }
            break;
            case "D": {
                radioButton = v.findViewById(R.id.D_3);
            }
            break;
        }
        if (radioButton != null)
            radioButton.setChecked(true);

        switch (answers.getString(number + "_4", "")) {
            case "A": {
                radioButton = v.findViewById(R.id.A_4);
            }
            break;
            case "B": {
                radioButton = v.findViewById(R.id.B_4);
            }
            break;
            case "V": {
                radioButton = v.findViewById(R.id.V_4);
            }
            break;
            case "G": {
                radioButton = v.findViewById(R.id.G_4);
            }
            break;
            case "D": {
                radioButton = v.findViewById(R.id.D_4);
            }
            break;
        }
        if (radioButton != null)
            radioButton.setChecked(true);

    }

    private void setAnswers() {
        if (type == 1) {
            switch (answers.getString(number + "", "")) {
                case "A": {
                    type1A.setChecked(true);
                }
                return;
                case "B": {
                    type1B.setChecked(true);
                }
                return;
                case "V": {
                    type1V.setChecked(true);
                }
                return;
                case "G": {
                    type1G.setChecked(true);
                }
                return;
                case "D": {
                    type1D.setChecked(true);
                }
                return;
            }
        } else if (type == 3) {

            if (divided == 1) {
                type3Answer2.setText(answers.getString(number + "_2", ""));
            }
            type3Answer1.setText(answers.getString(number + "_1", ""));

        } else {
            int i = answers.getInt(number + "", 0);
            seekBarReflector.setText(seekBarReflector.getText().toString().substring(0, 10) + i);
            type4Answer.setProgress(i);
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        number = getArguments().getInt("number");
        helper = new DBHelper(getContext());
        database = helper.getReadableDatabase();
        intent = getActivity().getIntent();
        answers = getActivity().getSharedPreferences(ZnoHolder.ANSWERS, Context.MODE_PRIVATE);
        id = intent.getExtras().getInt("ID");
    }


    private int determineType() {
        Cursor query;
        query = database.rawQuery("SELECT  first_level, second_level, third_level FROM types WHERE _id=?", new String[]{id + ""});
        query.moveToFirst();
        int level_1, level_2, level_3;
        level_1 = query.getInt(0);
        level_2 = query.getInt(1);
        level_3 = query.getInt(2);

        if (number > level_1) {
            if (number > level_2) {
                if (number > level_3) {// 4 фрагмент
                    type4.setVisibility(View.VISIBLE);
                    answer = database.rawQuery("SELECT _id, number, question, criteria, answer FROM fourth_level WHERE _id=? AND number=?", new String[]{id + "", number + ""});

                    return 4;
                } else {// 3 фрагмент
                    answer = database.rawQuery("SELECT _id, number, question, explanation, divided FROM third_level WHERE _id=? AND number=?", new String[]{id + "", number + ""});
                    if (answer.moveToFirst()) {
                        divided = answer.getInt(4);
                        if (divided == 1) {
                            hint.setVisibility(View.VISIBLE);
                            type3_2.setVisibility(View.VISIBLE);
                        }
                    }
                    type3.setVisibility(View.VISIBLE);
                    return 3;
                }
            } else {     // 2 фрагмент

                answer = database.rawQuery("SELECT _id, number, question FROM second_level WHERE _id=? AND number=?", new String[]{id + "", number + ""});
                type2.setVisibility(View.VISIBLE);
                return 2;
            }
        } else {// 1 фрагмент
            answer = database.rawQuery("SELECT _id, number, question, explanation FROM first_level WHERE _id=? AND number=?", new String[]{id + "", number + ""});
            type1.setVisibility(View.VISIBLE);
            return 1;
        }
    }

    private void setIMG() {
        answer.moveToFirst();
        int resourceId = getResources().getIdentifier(answer.getString(2), "drawable", getContext().getPackageName());
        Uri uri = Uri.parse("android.resource://" + getContext().getPackageName() + "/" + resourceId);
        Glide.with(task.getContext())
                .load(uri)
                .into(task);
    }

    static Question newInstance(int number) {
        Question question = new Question();
        Bundle arguments = new Bundle();
        arguments.putInt("number", number);
        question.setArguments(arguments);
        return question;
    }


    private void setRightAnswers() {
        if (type == 1) {
            answer = database.rawQuery("SELECT answers FROM first_level WHERE _id=? and number = ?", new String[]{id + "", number + ""});
            answer.moveToFirst();
            switch (type1answers.getCheckedRadioButtonId()) {
                case R.id.type1_a: {
                    type1A.setTextColor(Color.RED);
                    break;
                }
                case R.id.type1_b: {
                    type1B.setTextColor(Color.RED);
                    break;
                }
                case R.id.type1_v: {
                    type1V.setTextColor(Color.RED);
                    break;
                }
                case R.id.type1_g: {
                    type1G.setTextColor(Color.RED);
                    break;
                }
                case R.id.type1_d: {
                    type1D.setTextColor(Color.RED);
                    break;
                }

            }


            switch (answer.getString(0)) {
                case "А": {
                    type1A.setChecked(true);
                    type1A.setTextColor(Color.GREEN);
                }
                break;
                case "Б": {
                    type1B.setChecked(true);
                    type1B.setTextColor(Color.GREEN);
                }
                break;
                case "В": {
                    type1V.setChecked(true);
                    type1V.setTextColor(Color.GREEN);
                }
                break;
                case "Г": {
                    type1G.setChecked(true);
                    type1G.setTextColor(Color.GREEN);
                }
                break;
                case "Д": {
                    type1D.setChecked(true);
                    type1D.setTextColor(Color.GREEN);
                }
                break;
            }
            type1A.setClickable(false);
            type1B.setClickable(false);
            type1V.setClickable(false);
            type1G.setClickable(false);
            type1D.setClickable(false);

        } else if (type == 2) {
            answer = database.rawQuery("SELECT right_answer_1, right_answer_2, right_answer_3, right_answer_4 FROM second_level WHERE _id=? and number = ?", new String[]{id + "", number + ""});
            answer.moveToFirst();
            subType2.setVisibility(View.GONE);
            right2.setVisibility(View.VISIBLE);
            type2Your.setText(getString(R.string.your) + answers.getString(number + "_1", "") + answers.getString(number + "_2", "") + answers.getString(number + "_3", "") + answers.getString(number + "_4", ""));
            type2Right.setText(getString(R.string.right) + answer.getString(0) + answer.getString(1) + answer.getString(2) + answer.getString(3));

        } else if (type == 3) {
            answer = database.rawQuery("SELECT answer1,answer2,divided FROM third_level WHERE _id=? and number = ?", new String[]{id + "", number + ""});
            answer.moveToFirst();
            sub_type3.setVisibility(View.GONE);
            right3.setVisibility(View.VISIBLE);
            if (divided == 1) {
                type3Your.setText(getString(R.string.your) + "  1)" + answers.getString(number + "_1", " ") + "   2)" + answers.getString(number + "_2", " "));
                type3Right.setText(getString(R.string.right) + "  1)" + answer.getString(0) + "   2)" + answer.getString(1));
            } else {
                type3Your.setText(getString(R.string.your) + "  " + answers.getString(number + "_1", " "));
                type3Right.setText(getString(R.string.right) + "  " + answer.getString(0));
            }
        }

    }


}
