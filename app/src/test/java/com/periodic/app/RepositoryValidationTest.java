package com.periodic.app;
import org.junit.Test;
import static org.junit.Assert.*;
public final class RepositoryValidationTest {
 @Test public void allElementsPassRepositoryValidation(){ElementRepository.validate();}
 @Test public void everyElementHasPositiveRepresentativeMass(){for(Element e:ElementRepository.all())assertTrue(e.representativeMassNumber()>=e.number);}
 @Test public void lookupRoundTrips(){for(Element e:ElementRepository.all())assertSame(e,ElementRepository.require(e.number));}
}
