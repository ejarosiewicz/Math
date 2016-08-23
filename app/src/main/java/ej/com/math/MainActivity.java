package ej.com.math;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ejarosiewicz.exceptions.format.InvalidExpressionFormatException;
import com.ejarosiewicz.exceptions.resources.ResourcesShortageException;
import com.example.emja.numericalintegrationsdraw.IntegrationGraphView;

public class MainActivity extends Activity implements View.OnClickListener {

    private EditText functionEdit;
    private EditText minEdit;
    private EditText maxEdit;
    private EditText countEdit;
    private TextView score;
    private RadioButton squareRadio;
    private RadioButton trapezeRadio;
    private Button button;
    private IntegrationGraphView integrationGraphView;
    private boolean integrationMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewSetup();
    }

    private void viewSetup() {
        functionEdit = (EditText) findViewById(R.id.functionEdit);
        minEdit = (EditText) findViewById(R.id.minEdit);
        maxEdit = (EditText) findViewById(R.id.maxEdit);
        countEdit = (EditText) findViewById(R.id.countEdit);
        score = (TextView) findViewById(R.id.score);
        button = (Button) findViewById(R.id.button);
        squareRadio = (RadioButton) findViewById(R.id.squareRadio);
        trapezeRadio = (RadioButton) findViewById(R.id.trapezeRadio);
        trapezeRadio.setChecked(true);
        squareRadio.setChecked(false);
        squareRadio.setOnClickListener(this);
        trapezeRadio.setOnClickListener(this);
        integrationGraphView = (IntegrationGraphView) findViewById(R.id.view);
        button.setOnClickListener(this);
        squareRadio.callOnClick();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.button:
                invalidateInputAndStart();
                break;
            case R.id.trapezeRadio:
                setIntegrationMode(false);
                break;
            case R.id.squareRadio:
                setIntegrationMode(true);
                break;
        }
    }

    private void invalidateInputAndStart(){
        if (!functionEdit.getText().toString().isEmpty()
                && !minEdit.getText().toString().isEmpty()
                && !maxEdit.getText().toString().isEmpty()
                && !countEdit.getText().toString().isEmpty()){
            calculateIntegrationMethod();
        }
    }

    private void calculateIntegrationMethod() {
        try {
            integrationGraphView.getMathOperator().setMin(Integer.parseInt(minEdit.getText().toString()));
            integrationGraphView.getMathOperator().setMax(Integer.parseInt(maxEdit.getText().toString()));
            integrationGraphView.getMathOperator().setCount(Integer.parseInt(countEdit.getText().toString()));
            integrationGraphView.getMathOperator().prepareFunctionCalculatorFromExpression(functionEdit.getText().toString());
            if (integrationMode){
                score.setText(Float.toString(integrationGraphView.getMathOperator().calculateSquareMethod()));
            } else {
                score.setText(Float.toString(integrationGraphView.getMathOperator().calculateTrapezeMethod()));
            }
            integrationGraphView.invalidate();
        } catch (InvalidExpressionFormatException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        } catch (ResourcesShortageException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void setIntegrationMode(boolean isSquare) {
        this.integrationMode = isSquare;
        if (isSquare){
            squareRadio.setChecked(true);
            trapezeRadio.setChecked(false);
        } else {
            squareRadio.setChecked(false);
            trapezeRadio.setChecked(true);
        }
            integrationGraphView.setIsSquare(isSquare);
    }
}
