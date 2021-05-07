package idv.tfp10102.wayne.ch16_1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import idv.tfp10102.wayne.R;

public class Ch16_1_MyView extends View {
    int offset;
    Paint paint;

    public Ch16_1_MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        offset = 300;
        paint = new Paint();
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(getResources().getColor(R.color.purple_200));
        canvas.drawRect(offset, 0, offset + 100,  100, paint);

        paint.setColor(Color.CYAN);
        canvas.drawCircle(offset + 200, 100, 100, paint);
    }
}
