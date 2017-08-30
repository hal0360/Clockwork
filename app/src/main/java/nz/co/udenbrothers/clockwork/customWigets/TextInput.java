package nz.co.udenbrothers.clockwork.customWigets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class TextInput extends android.support.v7.widget.AppCompatEditText {
    public TextInput(Context context)
    {
        super(context);
    }

    public TextInput(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public TextInput(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void error(String s){
        requestFocus();
        setError(s);
    }

    public void setTxt(String s){
        setText(s);
    }

    public String getTxt(){
        return getText().toString().trim();
    }
}
