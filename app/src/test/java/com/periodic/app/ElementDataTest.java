package com.periodic.app;
import org.junit.Test;
import static org.junit.Assert.*;
public class ElementDataTest {
 @Test public void all118ElementsAreUniqueAndInternallyConsistent(){
  assertEquals(118,ElementData.ALL.size()); boolean[] seen=new boolean[119];
  for(Element e:ElementData.ALL){assertTrue(e.number>=1&&e.number<=118);assertFalse(seen[e.number]);seen[e.number]=true;int sum=0;for(int n:NuclearData.shellDistribution(e.number))sum+=n;assertEquals(e.number,sum);assertTrue(e.neutrons()>=0);assertSame(e,ElementData.byNumber(e.number));}
  assertNull(ElementData.byNumber(999));
 }
 @Test public void representativeNucleiMatchKnownExamples(){
  assertEquals(6,ElementData.byNumber(6).neutrons());
  assertEquals(8,ElementData.byNumber(8).neutrons());
  assertEquals(30,ElementData.byNumber(26).neutrons());
  assertEquals(118,ElementData.byNumber(79).neutrons());
  assertEquals(146,ElementData.byNumber(92).neutrons());
 }
}
