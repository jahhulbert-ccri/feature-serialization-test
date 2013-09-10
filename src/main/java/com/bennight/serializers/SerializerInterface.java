package com.bennight.serializers;

import java.io.IOException;
import java.util.List;

import org.opengis.feature.simple.SimpleFeature;


public interface SerializerInterface {
	
	public String GetSerializerName();
	public double[] GetSerializationPerformance(List<SimpleFeature> features) throws IOException;
	
}
