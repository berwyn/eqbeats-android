package org.codeweaver.eqbeats.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import org.codeweaver.eqbeats.R;

/**
 * Created by Berwyn Codeweaver on 23/06/13.
 */
public class SquareImage extends ImageView {

	public static enum FixedSide {
		width, height
	}

	private FixedSide	fixedSide	= FixedSide.width;

	public SquareImage(Context context) {
		super(context);
	}

	public SquareImage(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}

	public SquareImage(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
        init(attrs);
	}

	int	squareDimen	= 1;

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int size = (fixedSide == FixedSide.width) ? getMeasuredWidth()
				: getMeasuredHeight();
		if (size > squareDimen) {
			squareDimen = size;
		}
		setMeasuredDimension(squareDimen, squareDimen);
	}

    private void init(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs,
                R.styleable.SquareImage);
        fixedSide = FixedSide.valueOf(array
                .getString(R.styleable.SquareImage_fixedSide));
        if (fixedSide == null) {
            fixedSide = FixedSide.width;
        }
        array.recycle();
    }
}
