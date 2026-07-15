package com.periodic.app;

import android.content.Context;
import android.graphics.*;
import android.view.View;

public class OxygenSplashView extends View {
    private final Paint p = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    private float phase = 0f;

    public OxygenSplashView(Context c) {
        super(c);
        p.setTypeface(Typeface.create("sans", Typeface.BOLD));
    }

    @Override protected void onDraw(Canvas c) {
        super.onDraw(c);
        float w=getWidth(), h=getHeight(), cx=w/2f, cy=h/2f-24f;
        c.drawColor(Color.rgb(1,5,14));
        drawSpace(c,w,h);
        float r=Math.min(w,h)*.155f; float camera=1f+.018f*(float)Math.sin(Math.toRadians(phase*.55f)); c.save(); c.scale(camera,camera,cx,cy);

        final float[] tilts={-28f,31f,89f};
        final int[] colors={Color.rgb(193,78,103),Color.rgb(91,151,224),Color.rgb(218,188,90)};
        final float[] speeds={1.0f,.72f,.53f};
        final int[] counts={3,3,2};

        p.setStyle(Paint.Style.STROKE); p.setStrokeWidth(Math.max(2f,r*.018f));
        for(int k=0;k<3;k++) {
            p.setColor(colors[k]); p.setAlpha(220);
            c.save(); c.rotate(tilts[k],cx,cy);
            c.drawOval(cx-r*1.66f,cy-r*.62f,cx+r*1.66f,cy+r*.62f,p);
            c.restore();
        }

        // Back half of electrons, calculated from the exact same ellipses as the visible rings.
        for(int k=0;k<3;k++) for(int i=0;i<counts[k];i++) {
            double a=Math.toRadians(phase*speeds[k]+i*360f/counts[k]);
            if(Math.sin(a)<0) drawElectron(c,cx,cy,r,tilts[k],a,colors[k],false);
        }

        drawNucleus(c,cx,cy,r);

        // Front half.
        for(int k=0;k<3;k++) for(int i=0;i<counts[k];i++) {
            double a=Math.toRadians(phase*speeds[k]+i*360f/counts[k]);
            if(Math.sin(a)>=0) drawElectron(c,cx,cy,r,tilts[k],a,colors[k],true);
        }

        c.restore();
        float fade=Math.min(1f,phase/100f);
        p.setAlpha((int)(255*fade)); p.setTextAlign(Paint.Align.CENTER);
        p.setColor(Color.WHITE); p.setTextSize(Math.min(w,h)*.073f);
        c.drawText("PERIODIC",cx,cy+r*2.18f,p);
        p.setColor(Color.rgb(218,190,99)); p.setTextSize(Math.min(w,h)*.026f);
        c.drawText("Explore matter from atoms to the universe",cx,cy+r*2.58f,p);
        p.setAlpha(255);
        phase=(phase+2.1f)%360f;
        postInvalidateOnAnimation();
    }

    private void drawSpace(Canvas c,float w,float h){
        p.setStyle(Paint.Style.FILL);
        for(int i=0;i<125;i++){
            float x=(i*79%997)/997f*w, y=(i*137%991)/991f*h;
            float twinkle=.55f+.45f*(float)Math.sin(Math.toRadians(phase*1.4f+i*31));
            p.setColor(Color.argb((int)(55+145*twinkle),175,205,255));
            c.drawCircle(x,y,.7f+(i%3)*.65f,p);
        }
        p.setShader(new RadialGradient(w*.24f,h*.35f,w*.4f,new int[]{Color.argb(48,45,74,145),Color.argb(12,30,35,75),Color.TRANSPARENT},null,Shader.TileMode.CLAMP));
        c.drawRect(0,0,w,h,p); p.setShader(null);
    }

    private void drawNucleus(Canvas c,float cx,float cy,float r){
        p.setStyle(Paint.Style.FILL);
        p.setShader(new RadialGradient(cx-r*.12f,cy-r*.15f,r*.68f,new int[]{Color.argb(170,92,119,255),Color.argb(50,35,55,120),Color.TRANSPARENT},null,Shader.TileMode.CLAMP));
        c.drawCircle(cx,cy,r*.72f,p); p.setShader(null);
        for(int i=0;i<16;i++){
            double a=i*2.399963; float rr=(float)Math.sqrt(i/16f)*r*.39f;
            float x=cx+(float)Math.cos(a)*rr, y=cy+(float)Math.sin(a)*rr;
            int base=i%2==0?Color.rgb(190,65,89):Color.rgb(93,112,164);
            p.setShader(new RadialGradient(x-r*.035f,y-r*.04f,r*.12f,Visuals.lighten(base,.42f),base,Shader.TileMode.CLAMP));
            c.drawCircle(x,y,r*.1f,p); p.setShader(null);
        }
    }

    private void drawElectron(Canvas c,float cx,float cy,float r,float tilt,double angle,int color,boolean front){
        float ex=(float)Math.cos(angle)*r*1.66f, ey=(float)Math.sin(angle)*r*.62f;
        double t=Math.toRadians(tilt); float x=cx+ex*(float)Math.cos(t)-ey*(float)Math.sin(t); float y=cy+ex*(float)Math.sin(t)+ey*(float)Math.cos(t);
        float er=r*.046f;
        p.setStyle(Paint.Style.FILL);
        // short trail follows the same ellipse rather than drifting away from it
        for(int j=4;j>=1;j--){double ta=angle-j*.055;float tx=(float)Math.cos(ta)*r*1.66f,ty=(float)Math.sin(ta)*r*.62f;float qx=cx+tx*(float)Math.cos(t)-ty*(float)Math.sin(t),qy=cy+tx*(float)Math.sin(t)+ty*(float)Math.cos(t);p.setColor(Color.argb(front?10+j*8:5+j*4,Color.red(color),Color.green(color),Color.blue(color)));c.drawCircle(qx,qy,er*(.28f+j*.08f),p);}
        p.setShader(new RadialGradient(x,y,er*3.6f,new int[]{Color.argb(front?185:90,145,205,255),Color.argb(20,90,150,255),Color.TRANSPARENT},null,Shader.TileMode.CLAMP));
        c.drawCircle(x,y,er*3.6f,p); p.setShader(null);
        p.setColor(front?Color.WHITE:Color.rgb(175,190,215)); c.drawCircle(x,y,er,p);
    }
}
