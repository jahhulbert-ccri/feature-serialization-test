package com.bennight.serializers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.opengis.feature.simple.SimpleFeature;

import com.bennight.ShapefileReader;

public abstract class AbstractSerializer implements SerializerInterface {

	public AbstractSerializer(){
		
	}

	protected abstract List<byte[]> Serialize(List<SimpleFeature> features);
	protected abstract void Deserialize(List<byte[]> serializedData);

	public double[] GetSerializationPerformance(List<SimpleFeature> features) throws IOException{
		
		long start = System.nanoTime();
		List<byte[]> serializedData = Serialize(features);
		long serializeTime = System.nanoTime() - start;
		
		start = System.nanoTime();
		Deserialize(serializedData);
		long deserializeTime = System.nanoTime() - start;
		
		return new double[] {serializeTime, deserializeTime};
		
		
	}
	

}
