package com.bennight.serializers;

import org.opengis.feature.simple.SimpleFeature;

import java.io.IOException;
import java.util.List;


public interface SerializerInterface {
	
	public String getName();
	public double[] getPerformance(List<SimpleFeature> features) throws IOException;
	
}
