package com.periodic.app;
import android.graphics.*;
public final class Ui {
 private Ui(){}
 public static float s(float w,float h){return Math.min(w/1600f,h/900f);} 
 public static void text(Paint p,float size,int color,Paint.Align align){p.setShader(null);p.setStyle(Paint.Style.FILL);p.setTypeface(Typeface.create("sans",Typeface.BOLD));p.setTextSize(size);p.setColor(color);p.setTextAlign(align);} 
 public static float wrap(Canvas c,Paint p,String text,float x,float y,float width,float lead,int maxLines){String[] ws=text.split(" ");String line="";int n=0;for(String q:ws){String t=line.isEmpty()?q:line+" "+q;if(p.measureText(t)>width&&!line.isEmpty()){c.drawText(line,x,y,p);y+=lead;n++;line=q;if(n>=maxLines)return y;}else line=t;}if(!line.isEmpty()&&n<maxLines){c.drawText(line,x,y,p);y+=lead;}return y;}
 public static void panel(Canvas c,Paint p,RectF r,String title,int accent){p.setColor(Color.argb(225,7,16,22));c.drawRoundRect(r,22,22,p);p.setStyle(Paint.Style.STROKE);p.setStrokeWidth(2);p.setColor(Color.argb(180,Color.red(accent),Color.green(accent),Color.blue(accent)));c.drawRoundRect(r,22,22,p);p.setStyle(Paint.Style.FILL);text(p,18,Color.rgb(226,210,154),Paint.Align.LEFT);c.drawText(title,r.left+24,r.top+36,p);} 
 public static void chip(Canvas c,Paint p,RectF r,String label,boolean active,int accent){p.setColor(active?Color.argb(235,20,74,91):Color.argb(210,13,29,37));c.drawRoundRect(r,18,18,p);p.setStyle(Paint.Style.STROKE);p.setStrokeWidth(active?2.6f:1.2f);p.setColor(active?accent:Color.rgb(53,91,102));c.drawRoundRect(r,18,18,p);p.setStyle(Paint.Style.FILL);text(p,14,active?Color.WHITE:Color.rgb(176,202,208),Paint.Align.CENTER);c.drawText(label,r.centerX(),r.centerY()+5,p);} 
}
