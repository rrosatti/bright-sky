package com.example.rodri.brightsky.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

/**
 * Created by rodri on 5/1/2016.
 */
public class CustomAutoCompleteView extends AutoCompleteTextView {

    public CustomAutoCompleteView(Context context) {
        super(context);
    }

    public CustomAutoCompleteView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CustomAutoCompleteView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
    }

    /**
     *
     * this method will be used to disable the AutoComplete filter
     *
     * @param text
     * @param keyCode
     */
    @Override
    protected void performFiltering(CharSequence text, int keyCode) {
        String filterText = "";
        super.performFiltering(filterText, keyCode);
    }

    /**
     *
     * This method will catch the new character and append to the existing text
     *
     * @param text
     */
    @Override
    protected void replaceText(CharSequence text) {
        super.replaceText(text);
    }
}
