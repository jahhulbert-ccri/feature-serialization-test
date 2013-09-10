package com.bennight.serializers;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.geotools.data.DataUtilities;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import com.bennight.ShapefileReader;

public class SimpleFeatureTest extends AbstractSerializer {


	
	@Override
	public List<byte[]> Serialize(List<SimpleFeature> features) {
		
		StringBuilder sb = new StringBuilder();
		for (SimpleFeature f : features){
			sb.append(DataUtilities.encodeFeature(f));
			sb.append("\r\n");
		}
		List<byte[]> serializedData = new ArrayList<byte[]>();
		serializedData.add(sb.toString().getBytes(StandardCharsets.UTF_8));
		return serializedData;
		
	}

	@Override
	public void Deserialize(List<byte[]> serializedData) {
		
		List<SimpleFeature> features = new ArrayList<SimpleFeature>();
		for (String line : (new String(serializedData.get(0), StandardCharsets.UTF_8).split("\r\n"))){
			features.add(DataUtilities.createFeature(ShapefileReader.FeatureType, line));
		}
		//System.out.println(features.size());
	}

	public String GetSerializerName() {
		return "GeoTools Simple Feature";
	}

}
