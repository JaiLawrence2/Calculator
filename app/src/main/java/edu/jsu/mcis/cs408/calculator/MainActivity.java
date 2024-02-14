package edu.jsu.mcis.cs408.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

import edu.jsu.mcis.cs408.calculator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final int KEYS_HEIGHT = 4;
    private static final int KEYS_WIDTH = 5;
    private ActivityMainBinding binding;

    public static final int BUTTON_GRID = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initLayout();
    }

    private void initLayout() {
        ConstraintLayout layout = binding.layout;
        int[][] horizontals = new int[KEYS_HEIGHT][KEYS_WIDTH];
        int[][] verticals = new int[KEYS_WIDTH][KEYS_HEIGHT];
        int[] buttons = new int[BUTTON_GRID];
        String[] btnTextArray = getResources().getStringArray(R.array.button_text);
        String[] btnTagArray = getResources().getStringArray(R.array.tags);

        ConstraintSet set = new ConstraintSet();
        TextView tv = new TextView(this);
        tv.setId(View.generateViewId());
        tv.setText(R.string.textplaceholder);
        tv.setTextSize(48);

        set.connect(tv.getId(), ConstraintSet.RIGHT, binding.guideEast.getId(), ConstraintSet.RIGHT, 0);
        set.connect(tv.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0);
        set.connect(tv.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
        tv.setGravity(Gravity.END);
        layout.addView(tv);

        //Button button;
        LayoutParams buttondis;
        for (int i = 0; i < buttons.length; i++) {
            int id = View.generateViewId(); // generate new ID
            Button button = new Button(this);
            button.setId(id);  // assign ID
            button.setTag(btnTagArray[i]); // assign tag (for acquiring references later)
            button.setText(btnTextArray[i]); // set text (using a string resource)
            button.setTextSize(24); // set size
            layout.addView(button); // add to layout
            buttons[i] = id; // store ID to collection

            buttondis = button.getLayoutParams();
            buttondis.height = ConstraintLayout.LayoutParams.WRAP_CONTENT;
            buttondis.width = ConstraintLayout.LayoutParams.WRAP_CONTENT;


        }
        for (int id : buttons) {
            set.connect(id, ConstraintSet.LEFT, binding.guideWest.getId(), ConstraintSet.LEFT, 0);
            set.connect(id, ConstraintSet.RIGHT, binding.guideEast.getId(), ConstraintSet.RIGHT, 0);
            set.connect(id, ConstraintSet.BOTTOM, binding.guideSouth.getId(), ConstraintSet.BOTTOM);
            set.connect(id, ConstraintSet.TOP, tv.getId(), ConstraintSet.BOTTOM);

        }
        for (int row = 0; row < KEYS_HEIGHT; ++row) {
            set.createHorizontalChain(binding.guideWest.getId(), ConstraintSet.LEFT, binding.guideEast.getId(), ConstraintSet.RIGHT, horizontals[row], null, ConstraintSet.CHAIN_PACKED);
            for (int col = 0; col < KEYS_WIDTH; ++col) {
                set.createVerticalChain(tv.getId(), ConstraintSet.BOTTOM, binding.guideSouth.getId(), ConstraintSet.BOTTOM, verticals[col], null, ConstraintSet.CHAIN_PACKED);
            }
        }
        for (int j = 0; j < BUTTON_GRID; ++j) {
            set.createVerticalChain(tv.getId(), ConstraintSet.BOTTOM,
                    binding.guideSouth.getId(), ConstraintSet.BOTTOM, buttons,
                    null, ConstraintSet.CHAIN_PACKED);
        }

        set.applyTo(layout);
        set.clone(layout);
        LayoutParams params = tv.getLayoutParams();
        params.width = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT;
        params.height = LayoutParams.WRAP_CONTENT;
        tv.setLayoutParams(params);



    }
}