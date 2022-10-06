package lk.sltc.calculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText questionEditText;
    EditText answerEditText;
    boolean operatorInUse;
    String previousOperator;

    List<Double> values = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionEditText = findViewById(R.id.et_question);
        answerEditText = findViewById(R.id.et_answer);
    }

    public void numberTapped(View view) {
        Button numberButton = (Button) view;
        questionEditText.append(numberButton.getText());
        operatorInUse = false;
    }

    public void clearTapped(View view) {
        questionEditText.setText("");
        answerEditText.setText("");

        values.clear();
        operatorInUse = false;
    }

    public void operatorTapped(View view) {
        if (!operatorInUse) {
            Button operatorButton = (Button) view;
            String currentOperator = operatorButton.getText().toString();

            switch (operatorButton.getId()) {
                case R.id.btn_dot:
                    questionEditText.append(operatorButton.getText());
                    break;
                case R.id.btn_equal:
                    values.add(getLastValue());
                    calculate(currentOperator);
                    questionEditText.setText("");
                    break;
                default:
                    values.add(getLastValue());
                    calculate(currentOperator);
                    questionEditText.append(operatorButton.getText());
                    break;
            }

            operatorInUse = true;
        }
    }

    private void calculate(String operator) {
        double answer = 0.0;
        if (values.size() == 1) {
            answer = values.get(0);
        } else if (values.size() == 2) {
            double firsValue = values.get(0);
            double secondValue = values.get(1);

            switch (previousOperator) {
                case "+":
                    answer = firsValue + secondValue;
                    break;
                case "-":
                    answer = firsValue - secondValue;
                    break;
                case "*":
                    answer = firsValue * secondValue;
                    break;
                case "/":
                    answer = firsValue / secondValue;
                    break;
            }

            values.clear();
            values.add(answer);
        }

        previousOperator = operator;
        answerEditText.setText(String.valueOf(answer));
    }

    private double getLastValue() {
        double lastValue = 0.0;
        String question = questionEditText.getText().toString();
        String[] valueStrings = question.split("[+-/*]");
        if (valueStrings.length > 0) {
            String lastValueString = valueStrings[valueStrings.length - 1];
            lastValue = Double.parseDouble(lastValueString);
        }

        return lastValue;
    }
}












