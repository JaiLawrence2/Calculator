package edu.jsu.mcis.cs408.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.nfc.Tag;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.beans.PropertyChangeEvent;
import java.math.BigDecimal;

import edu.jsu.mcis.cs408.calculator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements AbstractMainActivityView{
    private static final String TAG = "MainActivity";
    private static final int KEYS_HEIGHT = 4;
    private static final int KEYS_WIDTH = 5;
    private ActivityMainBinding binding;
    CalculatorClickHandler click = new CalculatorClickHandler();
    CalculatorController controller;
    class CalculatorClickHandler implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            String tag = view.getTag().toString();
            Toast toast = Toast.makeText(binding.getRoot().getContext(), tag, Toast.LENGTH_SHORT);
            toast.show();
            Log.i(tag, "button clicked is"+tag);
            // INSERT EVENT HANDLING CODE HERE
            if (tag.contains("btn")){
                Button button = (Button) view;
                String newText = button.getText().toString();
                Log.i(CalculatorModel.TAG, "State "+ CalculatorModel.state );
                controller.changeTextView(newText);
                Log.i(TAG, "button text is"+ newText);
                Log.i(CalculatorModel.TAG, "LHS "+ CalculatorModel.lhs);
            }

        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initLayout();
        controller = new CalculatorController();
        CalculatorModel model = new CalculatorModel();

        /* Register Activity View and Model with Controller */

        controller.addView(this);
        controller.addModel(model);
    }

    private void initLayout() {
        ConstraintLayout layout = binding.layout;
        int[][] horizontals = new int[KEYS_HEIGHT][KEYS_WIDTH];
        int[][] verticals = new int[KEYS_WIDTH][KEYS_HEIGHT];
        String[] btnTextArray = getResources().getStringArray(R.array.button_text);
        String[] btnTagArray = getResources().getStringArray(R.array.tags);

        ConstraintSet set = new ConstraintSet();
        TextView tv = new TextView(this);
        int tv_id = View.generateViewId();
        tv.setId(tv_id);
        tv.setTag("TextView");
        tv.setText(R.string.textplaceholder);
        tv.setGravity(Gravity.END);
        tv.setTextSize(48);
        layout.addView(tv);
        set.clone(layout);
        set.connect(tv.getId(), ConstraintSet.RIGHT, binding.guideEast.getId(), ConstraintSet.RIGHT, 0);
        set.connect(tv.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0);
        set.connect(tv.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);

        for (int row = 0; row < KEYS_HEIGHT; ++row) {
             for (int col = 0; col < KEYS_WIDTH; ++col) {
                 int i = (row * KEYS_WIDTH) + col;
                 int id = View.generateViewId();
                 Button button = new Button(this);
                 button.setId(id);
                 button.setTag(btnTagArray[i]);
                 button.setText(btnTextArray[i]);
                 button.setTextSize(28);
                 layout.addView(button);
                 horizontals[row][col] = id;
                 verticals[col][row] = id;
                 button.setOnClickListener(click);
             }
        }
        for (int row = 0; row < KEYS_HEIGHT; ++row) {
             set.createHorizontalChain(binding.guideWest.getId(), ConstraintSet.LEFT, binding.guideEast.getId(),
                     ConstraintSet.RIGHT, horizontals[row], null, ConstraintSet.CHAIN_PACKED);
        }
        for (int col = 0; col < KEYS_WIDTH; ++col) {
             set.createVerticalChain(tv.getId(), ConstraintSet.BOTTOM, binding.guideSouth.getId(),
                     ConstraintSet.BOTTOM, verticals[col], null, ConstraintSet.CHAIN_PACKED);
        }
        set.applyTo(layout);
        LayoutParams params = tv.getLayoutParams();
        params.width = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT;
        params.height = LayoutParams.WRAP_CONTENT;
        tv.setLayoutParams(params);
    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        String propertyValue = evt.getNewValue().toString();

        Log.i(TAG,"new "+propertyName+" Value from Model: "+propertyValue);

        if(propertyName.equals(CalculatorController.NEW_DIGIT)){
            String oldPropertyValue = CalculatorController.NEW_DIGIT;

            if (!oldPropertyValue.equals(propertyValue)){
                updateDisplay(propertyValue);
            }
        }
    }

    private void updateDisplay(String propertyValue) {
        TextView displayTextView = binding.layout.findViewWithTag("TextView");
        displayTextView.setText(propertyValue);
    }
}
