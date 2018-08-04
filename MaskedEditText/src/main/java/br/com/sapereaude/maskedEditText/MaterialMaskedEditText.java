package br.com.sapereaude.maskedEditText;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.TextInputLayout;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MaterialMaskedEditText extends TextInputLayout {

    protected TextInputLayout mInputContainer;
    protected MaskedEditText mEditText;
    protected String mMaskHint = "";
    protected CharSequence mFloatingText = "";
    protected String mMask = "";
    protected String mInactiveHint = "";
    protected boolean mKeepHint;
    protected boolean mKeepFloatingText;

    public MaterialMaskedEditText(Context context) {
        super(context);
        init(context, null, 0);
    }

    public MaterialMaskedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public MaterialMaskedEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TextInputLayout.inflate(context, R.layout.custom_masked_edit_text, this);
        mInputContainer = (TextInputLayout) findViewById(R.id.input_container);
        TypedArray ogTypedArray = context.obtainStyledAttributes(attrs, R.styleable.MaskedEditText, defStyleAttr, 0);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MaterialMaskedEditText, defStyleAttr, 0);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(context, attrs);

        mEditText = new MaskedEditText(context, attrs);
        mInputContainer.addView(mEditText);
        mEditText.setLayoutParams(params);

        mKeepHint = ogTypedArray.getBoolean(R.styleable.MaskedEditText_keep_hint, true);
        mEditText.setKeepHint(mKeepHint);
        mKeepFloatingText = typedArray.getBoolean(R.styleable.MaterialMaskedEditText_keep_floating_hint, false);

        mFloatingText = this.getHint();
        setFloatingHintText(mFloatingText.toString());
        mInactiveHint = templateMask(mFloatingText.toString());

        String maskHint = typedArray.getString(R.styleable.MaterialMaskedEditText_mask_hint);
        if (maskHint != null && !maskHint.isEmpty()) {
            mMaskHint = maskHint;
        }

        String mask = ogTypedArray.getString(R.styleable.MaskedEditText_mask);
        if (mask != null && !mask.isEmpty()) {
            mMask = mask;
        }

        setInputFocusListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    setFloatingHintEnabled(true);
                    setFloatingHintText(mFloatingText.toString());
                    if (!mMaskHint.isEmpty()) {
                        String text = mEditText.getRawText();
                        setHintText(mMaskHint);
                        setMask(mMask);
                        mEditText.setText(text);
                    }
                } else {
                    mInactiveHint = templateMask(mFloatingText.toString());
                    if (mKeepFloatingText) {
                        setFloatingHintEnabled(true);
                    } else {
                        setFloatingHintEnabled(false);
                    }
                    if (mEditText.getRawText().isEmpty() && !mKeepFloatingText) {
                        setHintText(mFloatingText.toString());
                        setMask(mInactiveHint);
                    }
                }
            }
        });




    }

    public void setOnEditorActionListener(TextView.OnEditorActionListener editorActionListener) {
        mEditText.setOnEditorActionListener(editorActionListener);
    }

    public void setTextWatcher(TextWatcher textWatcher) {
        mEditText.addTextChangedListener(textWatcher);
    }

    public void setInputFocusListener(View.OnFocusChangeListener focusListener) {
        mEditText.setOnFocusChangeListener(focusListener);
    }

    public void setFloatingHintText(String hintText) {
        mFloatingText = hintText;
        if(mInputContainer != null) mInputContainer.setHint(hintText);
    }

    public void setFloatingHintEnabled(boolean enabled) {
        mInputContainer.setHintEnabled(enabled);
    }

    public void setText(String text) {
        mEditText.setText(text);
    }

    public String getText() {
        return mEditText.getText().toString();
    }

    public void setMask(String mask) {
        mEditText.setMask(mask);
    }

    public String getMask() {
        return mEditText.getMask();
    }

    public void setHintText(String placeholder) {
        mEditText.setHint(placeholder);
    }

    public CharSequence getHintText() {
       return mEditText.getHint();
    }

    public void setKeepMaskHint(boolean keep) {
        mEditText.setKeepHint(keep);
    }

    public void setKeepFloatingText(boolean keep) {
        mKeepFloatingText = keep;
    }

    public void setMaxLength(int maxLength) {
        mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
    }

    public String getRawText() {
        return mEditText.getRawText();
    }

    public void setCharRepresentation(char charRep){
        mEditText.setCharRepresentation(charRep);
    }

    public char getCharRepresentation() {
        return mEditText.getCharRepresentation();
    }

    public void setInputType(int inputType) {
        mEditText.setInputType(inputType);
    }

    private String templateMask(String templateString) {
        return new String(new char[templateString.length()]).replace("\0", String.valueOf(mEditText.getCharRepresentation()));
    }
}