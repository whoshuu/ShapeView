package com.whoshuu.shapeview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class ShapeView extends View {
    private ShapeType type;
    private ShapeDirection direction;
    private int color;
    private int width;
    private int height;
    private Style style;
    private float strokeWidth;
    private Paint paint;
    private RectF bounds;
    private Path triangle;

    public ShapeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupAttributes(attrs);
        setupPaint();
    }
    
    public ShapeType getType() {
        return type;
    }

    public void setType(ShapeType type) {
        this.type = type;
        if (type.equals(ShapeType.TRIANGLE)) {
            triangle = new Path();
        }
        invalidate();
        requestLayout();
    }

    public ShapeDirection getDirection() {
        return direction;
    }

    public void setDirection(ShapeDirection direction) {
        this.direction = direction;
        invalidate();
        requestLayout();
    }
    
    public int getColor() {
        return color;
    }
    
    public void setColor(int color) {
        this.color = color;
        if (paint != null) {
            paint.setColor(color);
        }
        invalidate();
        requestLayout();
    }
    
    public Style getStyle() {
        return style;
    }
    
    public void setStyle(Style style) {
        this.style = style;
        if (paint != null) {
            paint.setStyle(style);
        }
        invalidate();
        requestLayout();
    }
    
    public float getStrokeWidth() {
        return strokeWidth;
    }
    
    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        if (paint != null) {
            paint.setStrokeWidth(strokeWidth);
        }
        invalidate();
        requestLayout();
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        bounds.left = 0;
        bounds.top = 0;
        bounds.right = width;
        bounds.bottom = height;
        Log.d("shapes", "left: " + bounds.left + " top: " + bounds.top + " right: " + bounds.right + " bottom: " + bounds.bottom);
        if (!style.equals(Style.FILL)) {
            bounds.left += strokeWidth / 2;
            bounds.top += strokeWidth / 2;
            bounds.right -= strokeWidth / 2;
            bounds.bottom -= strokeWidth / 2;
            Log.d("shapes", "modified left: " + bounds.left + " top: " + bounds.top + " right: " + bounds.right + " bottom: " + bounds.bottom);
            
        }
        if (type.equals(ShapeType.RECTANGLE)) {
            canvas.drawRect(bounds, testPaint);
        } else if (type.equals(ShapeType.SQUARE)) {
            if ((bounds.right - bounds.left) > (bounds.bottom - bounds.top)) {
                canvas.drawRect((bounds.right - bounds.left + 3 * bounds.top - bounds.bottom) / 2,
                                bounds.top,
                                (bounds.right - bounds.left + bounds.top + bounds.bottom) / 2,
                                bounds.bottom,
                                paint);
            } else {
                canvas.drawRect(bounds.left,
                                (bounds.bottom - bounds.top + 3 * bounds.left - bounds.right) / 2,
                                bounds.right,
                                (bounds.bottom - bounds.top + bounds.left + bounds.right) / 2,
                                paint);
            }
        } else if (type.equals(ShapeType.CIRCLE)) {
            if ((bounds.right - bounds.left) > (bounds.bottom - bounds.top)) {
                canvas.drawCircle((bounds.left + bounds.right) / 2,
                                  (bounds.top + bounds.bottom) / 2,
                                  (bounds.bottom - bounds.top) / 2,
                                  paint);
            } else {
                canvas.drawCircle((bounds.left + bounds.right) / 2,
                                  (bounds.top + bounds.bottom) / 2,
                                  (bounds.right - bounds.left) / 2,
                                  paint);
            }
        } else if (type.equals(ShapeType.OVAL)) {
            canvas.drawOval(bounds, paint);
        } else if (type.equals(ShapeType.TRIANGLE)) {
            float   x1 = bounds.left,                       y1 = bounds.bottom,
                    x2 = bounds.right, y2 = bounds.bottom,
                    x3 = (bounds.right + bounds.left) / 2,  y3 = bounds.top;
            switch (direction) {
                case DOWN:
                    x1 = bounds.left;                       y1 = bounds.top;
                    x2 = bounds.right;                      y2 = bounds.top;
                    x3 = (bounds.right + bounds.left) / 2;  y3 = bounds.bottom;
                    break;
                case LEFT:
                    x1 = bounds.right;                      y1 = bounds.top;
                    x2 = bounds.right;                      y2 = bounds.bottom;
                    x3 = bounds.left;                       y3 = (bounds.top + bounds.bottom) / 2;
                    break;
                case RIGHT:
                    x1 = bounds.left;                       y1 = bounds.top;
                    x2 = bounds.left;                       y2 = bounds.bottom;
                    x3 = bounds.right;                      y3 = (bounds.top + bounds.bottom) / 2;
                    break;
                default: // The UP case
                    break;
            }
            triangle.reset();
            triangle.moveTo(x1, y1);
            triangle.lineTo(x2, y2);
            triangle.lineTo(x3, y3);
            triangle.lineTo(x1, y1);
            triangle.lineTo(x2, y2);
            canvas.drawPath(triangle, paint);
        }
    }
    
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w;
        height = h;
        super.onSizeChanged(w, h, oldw, oldh);
    }
    
    private void setupAttributes(AttributeSet attrs) {
        TypedArray a = getContext()
                .getTheme()
                .obtainStyledAttributes(attrs, R.styleable.ShapeView, 0, 0);
        try {
            setType(ShapeType.values()[a.getInteger(R.styleable.ShapeView_shape,
                    ShapeType.RECTANGLE.ordinal())]);
            setDirection(ShapeDirection.values()[a.getInteger(R.styleable.ShapeView_direction,
                    ShapeDirection.UP.ordinal())]);
            setColor(a.getColor(R.styleable.ShapeView_color, Color.BLACK));
            setStyle(Style.values()[a.getInteger(R.styleable.ShapeView_style,
                    Style.FILL.ordinal())]);
            setStrokeWidth(a.getDimension(R.styleable.ShapeView_stroke_width, 1f));
            bounds = new RectF(0, 0, width, height);
        } finally {
            a.recycle();
        }
    }
    
    private void setupPaint() {
        paint = new Paint();
        paint.setStyle(style);
        paint.setColor(color);
        paint.setStrokeWidth(strokeWidth);
    }
    
}
