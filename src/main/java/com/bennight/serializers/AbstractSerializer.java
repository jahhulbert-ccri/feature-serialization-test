package com.bennight.serializers;

import org.opengis.feature.simple.SimpleFeature;

import java.io.IOException;
import java.util.List;

public abstract class AbstractSerializer implements SerializerInterface {

    public static final int NUM_ITERATIONS = 25;

	protected abstract List<byte[]> serialize(List<SimpleFeature> features);

	protected abstract void deserialize(List<byte[]> serializedData);

    @Override
	public double[] getPerformance(List<SimpleFeature> features) throws IOException{
		
		long start = System.nanoTime();
        List<byte[]> serializedData = null;
        for(int i=0; i < NUM_ITERATIONS; i++) {
            serializedData = serialize(features);
        }
		long serializeTime = System.nanoTime() - start;
		
		start = System.nanoTime();
        for(int i=0; i < NUM_ITERATIONS; i ++) {
            deserialize(serializedData);
        }
		long deserializeTime = System.nanoTime() - start;
		
		return new double[] {serializeTime, deserializeTime};
	}

}
