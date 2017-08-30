package nz.co.udenbrothers.clockwork.customWigets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.List;

import nz.co.udenbrothers.clockwork.abstractions.SpinnerFace;

public class Choser extends AppCompatSpinner implements AdapterView.OnItemSelectedListener {

    private Context context;
    private ArrayAdapter<String> dataAdapter;
    private SpinnerFace spinnerFace;

    public void init(String[] strings){
        dataAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, strings);
        setUp();
    }

    public void init(List<String> strings, int rid){
        dataAdapter = new ArrayAdapter<>(context, rid, strings);
        setUp();
    }

    public void init(List<String> strings){
        dataAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, strings);
        setUp();
    }

    public void init(String[] strings, int rid){
        dataAdapter = new ArrayAdapter<>(context, rid, strings);
        setUp();
    }

    private void setUp(){
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setAdapter(dataAdapter);
        setOnItemSelectedListener(this);
    }

    public Choser(Context context)
    {
        super(context);
        this.context = context;
    }

    public Choser(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
    }

    public Choser(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    public void selected(SpinnerFace spinnerFace){
        this.spinnerFace = spinnerFace;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
      spinnerFace.doit(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}
