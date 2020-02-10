package com.example.homework322;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
	private static final char POINT = '.';

	private TextView textView;
	private Button[] buttons;
	private View standardCalcPad;
	private View scientificCalcPad;

	private static final String POINT_STRING = Character.toString(POINT);
	private String inputDigits = "0";
	private boolean sign = true; // true-positive; false-negative
	private boolean inverseTrig;
	private boolean scientificMode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();

		textView.setText(inputDigits);
		View.OnClickListener padListener = this::handlePress;
		for (Button b : buttons)
			b.setOnClickListener(padListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_calc, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		if (item.getItemId() == R.id.switchScientific) {
			switchScientific();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void initViews() {
		textView = findViewById(R.id.textView);

		buttons = new Button[17];

		buttons[0] = findViewById(R.id.button0);
		buttons[1] = findViewById(R.id.button1);
		buttons[2] = findViewById(R.id.button2);
		buttons[3] = findViewById(R.id.button3);
		buttons[4] = findViewById(R.id.button4);
		buttons[5] = findViewById(R.id.button5);
		buttons[6] = findViewById(R.id.button6);
		buttons[7] = findViewById(R.id.button7);
		buttons[8] = findViewById(R.id.button8);
		buttons[9] = findViewById(R.id.button9);

		buttons[10] = findViewById(R.id.buttonDot);
		buttons[11] = findViewById(R.id.buttonClear);
		buttons[12] = findViewById(R.id.buttonSwitchSign);
		buttons[13] = findViewById(R.id.buttonSin);
		buttons[14] = findViewById(R.id.buttonCos);
		buttons[15] = findViewById(R.id.buttonTan);
		buttons[16] = findViewById(R.id.buttonTrigInv);

		standardCalcPad = findViewById(R.id.standardPad);
		scientificCalcPad = findViewById(R.id.scientificPad);
	}

	private void handlePress(View v) {
		switch (v.getId()) {
			case R.id.buttonDot:
				dotPress();
				break;
			case R.id.buttonClear:
				clear();
				break;
			case R.id.buttonSwitchSign:
				switchSign();
				break;
			case R.id.buttonTrigInv:
				invertTrigButtons();
				break;
			default:
				numPress(v.getId());
				break;
		}

		textView.setText(getInputString());
	}

	private void numPress(int id) { // proceeds number press
		for (int i = 0; i < 10; i++)
			if (id == buttons[i].getId()) {
				inputDigits += i;

				if (inputDigits.charAt(0) == '0') { // cut leading zero
					boolean isPointAfterZero = false;
					if (inputDigits.length() >= 2) // to prevent IndexOutOfBoundsException
						isPointAfterZero = inputDigits.charAt(1) == POINT;

					if (!isPointAfterZero) // in case it's i.e. "0.1", don't cut
						inputDigits = inputDigits.substring(1);
				}

				return;
			}
	}

	private void dotPress() { // proceeds dot press
		if (inputDigits.contains(POINT_STRING))
			return;

		inputDigits += POINT;
	}

	private void clear() {
		inputDigits = "0";
		sign = true;
	}

	private void switchSign() {
		sign = !sign;
	}

	private String getInputString() {
		String sign = this.sign ? "" : "-";
		return sign + inputDigits;
	}

	private void invertTrigButtons() {
		inverseTrig = !inverseTrig;
		String tmp = inverseTrig ? getString(R.string.inv_trig) : "%s";
		buttons[13].setText(String.format(tmp, getString(R.string.sin_func)));
		buttons[14].setText(String.format(tmp, getString(R.string.cos_func)));
		buttons[15].setText(String.format(tmp, getString(R.string.tan_func)));
	}

	private void switchScientific() {
		scientificMode = !scientificMode;
		standardCalcPad.setVisibility(scientificMode ? View.INVISIBLE : View.VISIBLE);
		scientificCalcPad.setVisibility(scientificMode ? View.VISIBLE : View.INVISIBLE);
	}
}
