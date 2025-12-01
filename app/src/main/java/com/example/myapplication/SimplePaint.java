package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SimplePaint extends View {

    public enum ShapeType {
        FREEHAND, RECTANGLE, CIRCLE
    }

    private static class Layer {
        Path path;
        Paint paint;

        Layer(Path path, Paint paint) {
            this.path = path;
            this.paint = new Paint(paint); // Cria uma cópia do Paint para preservar a cor/estilo
        }
    }

    private List<Layer> layers = new ArrayList<>();
    private Paint currentPaint;
    private Path currentPath;
    private ShapeType currentShapeType = ShapeType.FREEHAND;

    private float startX, startY;
    private boolean isDrawing = false;

    private void setup() {
        currentPaint = new Paint();
        currentPaint.setAntiAlias(true);
        currentPaint.setStyle(Paint.Style.STROKE);
        currentPaint.setStrokeWidth(10f);
        currentPaint.setColor(Color.BLACK);
        currentPaint.setStrokeJoin(Paint.Join.ROUND);
        currentPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    public SimplePaint(Context context) {
        super(context);
        setup();
    }

    public SimplePaint(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    public SimplePaint(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        // Desenha o histórico de camadas
        for (Layer layer : layers) {
            canvas.drawPath(layer.path, layer.paint);
        }
        // Desenha a forma que está sendo criada no momento (preview)
        if (isDrawing && currentPath != null) {
            canvas.drawPath(currentPath, currentPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isDrawing = true;
                startX = x;
                startY = y;
                currentPath = new Path();
                currentPath.moveTo(x, y);
                return true;

            case MotionEvent.ACTION_MOVE:
                if (currentShapeType == ShapeType.FREEHAND) {
                    currentPath.lineTo(x, y);
                } else if (currentShapeType == ShapeType.RECTANGLE) {
                    currentPath.reset();
                    float left = Math.min(startX, x);
                    float top = Math.min(startY, y);
                    float right = Math.max(startX, x);
                    float bottom = Math.max(startY, y);
                    currentPath.addRect(left, top, right, bottom, Path.Direction.CW);
                } else if (currentShapeType == ShapeType.CIRCLE) {
                    currentPath.reset();
                    float dx = x - startX;
                    float dy = y - startY;
                    float radius = (float) Math.sqrt(dx * dx + dy * dy);
                    currentPath.addCircle(startX, startY, radius, Path.Direction.CW);
                }
                invalidate();
                return true;

            case MotionEvent.ACTION_UP:
                isDrawing = false;
                // Garante que o último movimento seja registrado
                if (currentShapeType == ShapeType.FREEHAND) {
                    currentPath.lineTo(x, y);
                } else {
                    // Recalcula a forma final
                    currentPath.reset();
                    if (currentShapeType == ShapeType.RECTANGLE) {
                        float left = Math.min(startX, x);
                        float top = Math.min(startY, y);
                        float right = Math.max(startX, x);
                        float bottom = Math.max(startY, y);
                        currentPath.addRect(left, top, right, bottom, Path.Direction.CW);
                    } else if (currentShapeType == ShapeType.CIRCLE) {
                        float dx = x - startX;
                        float dy = y - startY;
                        float radius = (float) Math.sqrt(dx * dx + dy * dy);
                        currentPath.addCircle(startX, startY, radius, Path.Direction.CW);
                    }
                }

                // Adiciona a camada finalizada à lista
                layers.add(new Layer(currentPath, currentPaint));
                currentPath = null;
                invalidate();
                break;
        }
        return true;
    }

    public void setShapeType(ShapeType type) {
        this.currentShapeType = type;
    }

    public void setColor(int color) {
        currentPaint.setColor(color);
    }

    public void undo() {
        if (!layers.isEmpty()) {
            layers.remove(layers.size() - 1);
            invalidate();
        }
    }

    public void clear() {
        layers.clear();
        invalidate();
    }
}
