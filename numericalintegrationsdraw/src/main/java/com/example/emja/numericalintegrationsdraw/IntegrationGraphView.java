package com.example.emja.numericalintegrationsdraw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.ejarosiewicz.MathOperator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by emja on 2016-03-15.
 */
public class IntegrationGraphView extends View {

    public static final int PADDING = 20;
    public static final int SCALE_X = 10;
    public static final int SCALE_Y = 10;
    public static final int STEP = 1;

    public static final int SIGN = 10;

    private int width;
    private int height;

    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Context context;
    private Paint coordinatePaint;
    private Paint functionPaint;
    private Paint integrationPaint;
    private MathOperator mathOperator;

    private boolean isSquare = true;

    public MathOperator getMathOperator() {
        return mathOperator;
    }

    public void setIsSquare(boolean isSquare) {
        this.isSquare = isSquare;
    }



    public IntegrationGraphView(Context context, AttributeSet attrs) {
        super(context,attrs);
        paintSetup();
        mathOperator = new MathOperator();
    }


    private void paintSetup() {
        coordinatePaint = new Paint();
        coordinatePaint.setAntiAlias(true);
        coordinatePaint.setColor(Color.BLACK);
        coordinatePaint.setStyle(Paint.Style.STROKE);
        coordinatePaint.setStrokeJoin(Paint.Join.ROUND);
        coordinatePaint.setStrokeWidth(1f);
        functionPaint = new Paint();
        functionPaint.setAntiAlias(true);
        functionPaint.setColor(Color.RED);
        functionPaint.setStyle(Paint.Style.STROKE);
        functionPaint.setStrokeJoin(Paint.Join.ROUND);
        functionPaint.setStrokeWidth(1f);
        integrationPaint = new Paint();
        integrationPaint.setAntiAlias(true);
        integrationPaint.setColor(Color.BLUE);
        integrationPaint.setStyle(Paint.Style.STROKE);
        integrationPaint.setStrokeJoin(Paint.Join.ROUND);
        integrationPaint.setStrokeWidth(1f);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isMathBuilderComplete(mathOperator)) {
            drawChart(canvas);
        }

    }

    private void drawChart(Canvas canvas) {

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        int centerWidth = width / 2;
        int centerHeight = height / 2;

        int stepX = (centerWidth - PADDING) / SCALE_X;
        int stepY = (centerHeight - PADDING) / SCALE_Y;


        drawCoordiane(canvas, width, height, centerWidth, centerHeight, SCALE_X, SCALE_Y,
                stepX, stepY);
        drawFunction(canvas, centerWidth, centerHeight, stepX, stepY);
        drawIntegration(canvas, width, height, centerWidth, centerHeight, SCALE_X, SCALE_Y,
                stepX, stepY);
    }

    private void drawIntegration(Canvas canvas, int width, int height, int centerWidth, int centerHeight, int scaleX, int scaleY, int stepX, int stepY) {
        ArrayList<Point> pointArrayList = new ArrayList<>();
        int singleStep = (int) (
                countLength(mathOperator.getMin(), mathOperator.getMax()).floatValue() / mathOperator.getCount()
        );
        for (int i = 0; i < centerWidth - PADDING; i += stepX) {
            if (i == stepX * 2) {
                Log.d("", "");
            }
            float computedXRight = pxToX(i, stepX);
            if (belongsToRange(computedXRight, singleStep)) {
                float computedYRight = mathOperator.getFunctionCalculator().calculate(computedXRight);
                float yRight = yToPx(computedYRight, stepY);
                float computedXRightFurther = pxToX(i + stepX, stepX);
                if (!isSquare || belongsToRange(computedXRightFurther,singleStep)) {
                    canvas.drawLine(centerWidth + i, centerHeight, centerWidth + i, centerHeight - yRight, integrationPaint);
                }
                pointArrayList.add(new Point(centerWidth + i, (int) (centerHeight - yRight)));
            }

            float computedXLeft = pxToX(-i, stepX);
            if (belongsToRange(computedXLeft, singleStep)) {
                float computedYLeft = mathOperator.getFunctionCalculator().calculate(computedXLeft);
                float yLeft = yToPx(computedYLeft, stepY);
                canvas.drawLine(centerWidth - i, centerHeight, centerWidth - i, centerHeight - yLeft, integrationPaint);
                pointArrayList.add(new Point(centerWidth - i, (int) (centerHeight - yLeft)));
            }
        }
        Collections.sort(pointArrayList, new Comparator<Point>() {
            @Override
            public int compare(Point lhs, Point rhs) {
                if (lhs.x == rhs.x) {
                    return 0;
                } else if (lhs.x < rhs.x) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
        if (isSquare) {
            squareIntegrationMethod(pointArrayList, canvas, centerHeight);
        } else {
            trapezeIntegrationMethod(pointArrayList, canvas);
        }
    }

    private void squareIntegrationMethod(ArrayList<Point> pointArrayList, Canvas canvas, int centerHeight) {
        Path path = new Path();
        boolean isFirst = true;
        Point previousPoint = new Point();
        int size = pointArrayList.size();
        for (int i = 0; i < size; i++) {
            Point point = pointArrayList.get(i);
            if (isFirst) {
                path.moveTo(point.x, point.y);
                previousPoint = point;
                isFirst = false;
            } else {
                path.lineTo(point.x, previousPoint.y);
                if (i == size -1) {
                    path.lineTo(point.x, centerHeight);
                } else {
                    path.lineTo(point.x, point.y);
                }
                previousPoint = point;
            }
        }
        canvas.drawPath(path, integrationPaint);
    }

    private void trapezeIntegrationMethod(ArrayList<Point> pointArrayList, Canvas canvas) {
        Path path = new Path();
        boolean isFirst = true;
        for (Point point : pointArrayList) {
            if (isFirst) {
                path.moveTo(point.x, point.y);
                isFirst = false;
            } else {
                path.lineTo(point.x, point.y);
            }
        }
        canvas.drawPath(path, integrationPaint);
    }

    private boolean belongsToRange(float computedXRight, int singleStep) {
        return (computedXRight >= mathOperator.getMin()
                && computedXRight <= mathOperator.getMax()
                && computedXRight % singleStep == 0);
    }


    private void drawFunction(Canvas canvas, int centerWidth, int centerHeight, int stepX, int stepY) {
        for (int i = 0; i < centerWidth - PADDING; i++) {
            if (i == 82) {
                Log.d("", "");
            }
            float computedXRight = pxToX(i, stepX);
            float computedYRight = mathOperator.getFunctionCalculator().calculate(computedXRight);
            float yRight = yToPx(computedYRight, stepY);
            canvas.drawPoint(i + centerWidth, centerHeight - yRight, functionPaint);

            float computedXLeft = pxToX(-i, stepX);
            float computedYLeft = mathOperator.getFunctionCalculator().calculate(computedXLeft);
            float yLeft = yToPx(computedYLeft, stepY);
            canvas.drawPoint(centerWidth - i, centerHeight - yLeft, functionPaint);
        }

    }

    private float pxToX(float i, float stepX) {
        return i / stepX;
    }

    private float yToPx(float i, float stepY) {
        return i * stepY;
    }


    private void drawCoordiane(Canvas canvas, int width, int height, int centerWidth, int centerHeight,
                               int scaleX, int scaleY, int stepX, int stepY) {
        canvas.drawLine(0, centerHeight, width, centerHeight, coordinatePaint);
        canvas.drawLine(centerWidth, 0, centerWidth, height, coordinatePaint);
        for (int i = centerWidth; i < width - PADDING; i += stepX) {
            canvas.drawLine(i, centerHeight + SIGN, i, centerHeight - SIGN, coordinatePaint);
        }
        for (int i = centerWidth; i > PADDING; i -= stepX) {
            canvas.drawLine(i, centerHeight + SIGN, i, centerHeight - SIGN, coordinatePaint);
        }
        for (int i = centerHeight; i > PADDING; i -= stepY) {
            canvas.drawLine(centerWidth - SIGN, i, centerWidth + SIGN, i, coordinatePaint);
        }
        for (int i = centerHeight; i < height - PADDING; i += stepY) {
            canvas.drawLine(centerWidth - SIGN, i, centerWidth + SIGN, i, coordinatePaint);
        }
    }



/*    private void drawFunction(Canvas canvas, int x, int chartStepWidth, int chartStepHeight,
                              int coordMaxWidth,int xStep, int yStep) {
        int computedX = (-coordMaxWidth)+(x-PADDING)*xStep;
        Float y =  mathOperator.getFunctionCalculator().calculate(computedX);
        int convertedY = (int)(SCALE - (chartStepHeight*(SCALE-y)));
        canvas.drawPoint(x,convertedY,functionPaint);

    }*/

    private Float whichIsGreater(Float min, Float max) {
        return min >= max ? min : max;
    }

    private Float countLength(Float min, Float max) {
        if (min > 0 && max > 0) {
            return max - min;
        } else if (min <= 0 && max > 0) {
            return max + Math.abs(min);
        } else {
            return Math.abs(min) - Math.abs(max);
        }
    }


    private boolean isMathBuilderComplete(MathOperator mathOperator) {
        return mathOperator.getCount() != null
                && mathOperator.getMin() != null
                && mathOperator.getMax() != null
                && mathOperator.getFunctionCalculator() != null;
    }

}
