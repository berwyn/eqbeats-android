package org.codeweaver.eqbeats.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Berwyn Codeweaver on 23/06/13.
 */
public class SquareImage extends ImageView {
    public SquareImage(Context context) {
        super(context);
    }

    public SquareImage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        setMeasuredDimension(width, width);
    }
}
