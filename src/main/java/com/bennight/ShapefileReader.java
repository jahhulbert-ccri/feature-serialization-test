package com.bennight;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureSource;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

public class ShapefileReader {


	
	public static SimpleFeatureType FeatureType = null;
	
	public static List<SimpleFeature> ReadShapefile(File file) throws IOException{
		
		
		
		
		Map<String, Serializable> connectParameters = new HashMap<String, Serializable>();

		connectParameters.put("url", file.toURI().toURL());
		connectParameters.put("create spatial index", false);

		DataStore dataStore = DataStoreFinder.getDataStore(connectParameters);
		if (dataStore == null) {
			System.out.println("No DataStore found to handle" + file.getPath());
			System.exit(1);
		}
		
		String[] typeNames = dataStore.getTypeNames();
		String typeName = typeNames[0];

		FeatureSource<SimpleFeatureType, SimpleFeature> featureSource;
		FeatureCollection<SimpleFeatureType, SimpleFeature> collection;
		FeatureIterator<SimpleFeature> iterator;

		featureSource = dataStore.getFeatureSource(typeName);
		collection = featureSource.getFeatures();
		iterator = collection.features();
		
		List<SimpleFeature> features = new ArrayList<SimpleFeature>();
		try{
			while (iterator.hasNext()){
				SimpleFeature sf = iterator.next();
				if (ShapefileReader.FeatureType == null){
					ShapefileReader.FeatureType = sf.getFeatureType();
				}
				features.add(sf);
			}
		} finally {
			if (iterator != null){
				iterator.close();
			}
		}
		
		return features;
	}
	
}
