package me.nic_e.allthedice;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
    private AnimationDrawable diceAnimation;
    private boolean toggleBool = false;
    private String[] lastRoll = new String[6];
    //widgets
    private ImageView[] diceImgs = new ImageView[6];
    private Switch toggleDice;
    private Button rollAll;

    //listeners
    private View.OnClickListener rollListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!toggleBool)
                startAnimation(view);
            else
                switchDice(view);

        }
    };

    private View.OnClickListener rollAllListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!toggleBool) {
                for (ImageView img : diceImgs)
                    startAnimation(img);
            }
        }
    };

    private CompoundButton.OnCheckedChangeListener switchListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                toggleBool = true;
                resetDice();
            } else {
                toggleBool = false;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        diceImgs[0] = (ImageView) findViewById(R.id.dice1);
        diceImgs[1] = (ImageView) findViewById(R.id.dice2);
        diceImgs[2] = (ImageView) findViewById(R.id.dice3);
        diceImgs[3] = (ImageView) findViewById(R.id.dice4);
        diceImgs[4] = (ImageView) findViewById(R.id.dice5);
        diceImgs[5] = (ImageView) findViewById(R.id.dice6);

        for (ImageView img : diceImgs) {
          img.setOnClickListener(rollListener);
        }

        toggleDice = (Switch) findViewById(R.id.toggleSwitch);
        toggleDice.setOnCheckedChangeListener(switchListener);

        rollAll = (Button) findViewById(R.id.rollAllButton);
        rollAll.setOnClickListener(rollAllListener);
    }

   /* @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        int i = 0;
        for (String s : lastRoll) {
            outState.putString(Integer.toString(i++),s);
        }
    }*/


    public void startAnimation(View view) {
        ImageView img = (ImageView) view;
        String tag = (String) img.getTag();
        diceAnimation = new AnimationDrawable();
        diceAnimation.addFrame(getDiceDrawable(tag, rollDice(tag)), 175);
        diceAnimation.addFrame(getDiceDrawable(tag, rollDice(tag)), 200);
        diceAnimation.addFrame(getDiceDrawable(tag, rollDice(tag)), 150);
        diceAnimation.addFrame(getDiceDrawable(tag, rollDice(tag)), 150);
        diceAnimation.addFrame(getDiceDrawable(tag, rollDice(tag)), 200);
        if (img.getId() == R.id.dice1) {
            lastRoll[0] = rollDice(tag);
            diceAnimation.addFrame(getDiceDrawable(tag, lastRoll[0]), 150);
        }
        else if (img.getId() == R.id.dice2) {
            lastRoll[1] = rollDice(tag);
            diceAnimation.addFrame(getDiceDrawable(tag, lastRoll[1]), 150);
        }
        else if (img.getId() == R.id.dice3) {
            lastRoll[2] = rollDice(tag);
            diceAnimation.addFrame(getDiceDrawable(tag, lastRoll[2]), 150);
        }
        else if (img.getId() == R.id.dice4) {
            lastRoll[3] = rollDice(tag);
            diceAnimation.addFrame(getDiceDrawable(tag, lastRoll[3]), 150);
        }
        else if (img.getId() == R.id.dice5) {
            lastRoll[4] = rollDice(tag);
            diceAnimation.addFrame(getDiceDrawable(tag, lastRoll[4]), 150);
        }
        else if (img.getId() == R.id.dice6) {
            lastRoll[5] = rollDice(tag);
            diceAnimation.addFrame(getDiceDrawable(tag, lastRoll[5]), 150);
        }
        diceAnimation.setOneShot(true);
        img.setImageDrawable(diceAnimation);
        diceAnimation.start();

    }

    public Drawable getDiceDrawable(String tag, String number) {
        String name = tag+"_"+number;
        int resourceId = this.getResources().getIdentifier(name, "drawable", this.getPackageName());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { //>= API 21
            return this.getResources().getDrawable(resourceId, this.getApplicationContext().getTheme());
        }
        else {
            return this.getResources().getDrawable(resourceId);
        }
    }

    public String rollDice(String tag) {
      int roll = 0;

      switch(tag) {
        case "d4":
           roll = (int) (Math.random() * 4) + 1;
           break;
        case "d6":
           roll = (int) (Math.random() * 6) + 1;
           break;
        case "d8":
           roll = (int) (Math.random() * 8) + 1;
           break;
        case "d10":
           roll = (int) (Math.random() * 10) + 1;
           break;
        case "d12":
           roll = (int) (Math.random() * 12) + 1;
           break;
        case "d20":
           roll = (int) (Math.random() * 20) + 1;
           break;
      }

      return Integer.toString(roll);
    }

    public void switchDice(View view) {
        ImageView img = (ImageView) view;
        String current = (String) img.getTag();
        switch(current) {
            case "d4":
                img.setImageResource(R.drawable.d6_start);
                img.setTag(new String("d6"));
                break;
            case "d6":
                img.setImageResource(R.drawable.d8_start);
                img.setTag(new String("d8"));
                break;
            case "d8":
                img.setImageResource(R.drawable.d10_start);
                img.setTag(new String("d10"));
                break;
            case "d10":
                img.setImageResource(R.drawable.d12_start);
                img.setTag(new String("d12"));
                break;
            case "d12":
                img.setImageResource(R.drawable.d20_start);
                img.setTag(new String("d20"));
                break;
            case "d20":
                img.setImageResource(R.drawable.d4_start);
                img.setTag(new String("d4"));
                break;
            default:
                img.setImageResource(R.drawable.d6_start);
                img.setTag(new String("d6"));

        }

    }

    public void resetDice() {
        for (ImageView img : diceImgs) {
          img.setImageDrawable(getDiceDrawable((String) img.getTag(),"start"));
        }
    }
}