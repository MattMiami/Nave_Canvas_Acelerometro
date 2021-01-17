package com.example.nave_espacial_canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.View;

public class VistaJuego extends View {

    //Pantalla y ajustado
    Drawable ship;
    Drawable background;
    int shipSides = 60;

    //Variable ejes acelerómetro
    float x = 0, y = 0, z = 0;
    String X , Y, Z;

    //área porteria
    float xStart, yStart, xEnd, yEnd;

    public VistaJuego (Context context){super(context);}

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint lapiz = new Paint();
        Paint linea_gol = new Paint();
        Paint ejes = new Paint();
        Paint restart = new Paint();

        //Linea salida
        xStart = canvas.getWidth()/2-(shipSides +20);
        yStart = canvas.getHeight();
        xEnd = canvas.getWidth()/2+(shipSides +20);
        yEnd = canvas.getHeight();

        //Límite pantalla
        lapiz.setStrokeWidth(35);
        lapiz.setColor(getResources().getColor(R.color.purple_200));
        lapiz.setStyle(Paint.Style.STROKE);

        //Línea salida
        linea_gol.setStrokeWidth(35);
        linea_gol.setColor(getResources().getColor(R.color.design_default_color_background));
        linea_gol.setTextAlign(Paint.Align.CENTER);
        linea_gol.setTextSize(150);

        //Valores acelerómetro
        ejes.setColor(getResources().getColor(R.color.white));
        ejes.setTextSize(24);

        /*Mensaje para reiniciar
        restart.setColor(getResources().getColor(R.color.white));
        restart.setTextSize(54);*/

        //Limite pantalla
        //canvas.drawRect(0,0, canvas.getWidth(),canvas.getHeight(),lapiz);
        background = getResources().getDrawable(R.drawable.space);
        background.setBounds(0,0, canvas.getWidth(),canvas.getHeight());
        background.draw(canvas);

        //Area salída
        canvas.drawLine(xStart, yStart, xEnd, yEnd, linea_gol);

        //Ejes acelerómetro
        canvas.drawText(X, canvas.getWidth()/2, 40,ejes);
        canvas.drawText(Y, canvas.getWidth()/2, 80,ejes);
        canvas.drawText(Z, canvas.getWidth()/2, 120,ejes);

        //Nave
        ship = getResources().getDrawable(R.drawable.invasores);
        ship.setBounds((int) x - shipSides, (int)y- shipSides, (int)x + shipSides,(int)y + shipSides);
        ship.draw(canvas);
    }

}
