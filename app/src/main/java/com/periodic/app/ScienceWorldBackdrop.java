package com.periodic.app;
import android.graphics.*;
/** Lightweight pseudo-3D science world rendered behind the unchanged v2.2 table. */
public final class ScienceWorldBackdrop {
 private ScienceWorldBackdrop(){}
 public static void draw(Canvas c, Paint p, float phase, int w, int h){
  c.drawColor(Color.rgb(2,7,11));
  float cx=w*.5f, cy=h*.5f;
  p.setShader(new RadialGradient(cx,cy,Math.max(w,h)*.72f,new int[]{Color.argb(75,14,91,111),Color.argb(24,8,32,43),Color.TRANSPARENT},null,Shader.TileMode.CLAMP));
  c.drawRect(0,0,w,h,p);p.setShader(null);
  // Perspective grid floor.
  p.setStyle(Paint.Style.STROKE);p.setStrokeWidth(1f);
  for(int i=-9;i<=9;i++){p.setColor(Color.argb(22,92,190,210));c.drawLine(cx,cy+h*.03f,cx+i*w*.12f,h,p);} 
  for(int i=0;i<10;i++){float t=i/9f;float y=cy+h*.03f+(h-cy)*t*t;p.setColor(Color.argb(12+(int)(t*24),92,190,210));c.drawLine(0,y,w,y,p);}p.setStyle(Paint.Style.FILL);
  String[] words={"MATTER","ATOM","ENERGY","QUANTUM","MOLECULE","NUCLEUS","ELECTRON","PROTON","NEUTRON","ORBITAL","CHEMISTRY","PHYSICS"};
  for(int i=0;i<words.length;i++){
   float depth=.28f+(i%4)*.18f; float drift=(float)Math.sin(Math.toRadians(phase*.35f+i*41))*18f*depth;
   float x=((i*337)%Math.max(1,w+400))-200+drift; float y=70+((i*191)%Math.max(120,h-100));
   p.setTypeface(Typeface.create("sans",Typeface.BOLD));p.setTextAlign(Paint.Align.LEFT);p.setTextSize(26+depth*42);p.setColor(Color.argb(12+(int)(depth*32),100,197,215));
   c.save();c.rotate(-18+(i%6)*7,x,y);c.scale(.72f+depth*.55f,.72f+depth*.55f,x,y);c.drawText(words[i],x,y,p);c.restore();
  }
  // Molecules and orbital sculptures at multiple depths.
  for(int m=0;m<8;m++){float mx=(m*251+97)%Math.max(1,w),my=90+(m*137)%Math.max(1,h-150);float rr=18+(m%4)*9;float spin=phase*(.18f+(m%3)*.06f)+m*33;
   p.setStrokeWidth(2);p.setColor(Color.argb(30,100,205,225));p.setStyle(Paint.Style.STROKE);c.save();c.rotate(spin,mx,my);c.drawOval(new RectF(mx-rr*2.2f,my-rr*.7f,mx+rr*2.2f,my+rr*.7f),p);c.rotate(60,mx,my);c.drawOval(new RectF(mx-rr*2.2f,my-rr*.7f,mx+rr*2.2f,my+rr*.7f),p);c.restore();p.setStyle(Paint.Style.FILL);p.setColor(Color.argb(70,135,225,238));c.drawCircle(mx,my,5+(m%3),p);
  }
  for(int i=0;i<95;i++){float x=(i*137+phase*(.25f+(i%3)*.12f))%Math.max(1,w),y=(i*83)%Math.max(1,h);p.setColor(Color.argb(16+(i%5)*9,120,205,222));c.drawCircle(x,y,1+(i%3),p);} 
 }
}
