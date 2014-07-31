package com.bennight.serializers;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.geotools.data.DataUtilities;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import com.bennight.ShapefileReader;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKBReader;
import com.vividsolutions.jts.io.WKBWriter;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.io.WKTWriter;
import com.vividsolutions.jts.geom.Geometry;

public class WKBTest extends AbstractSerializer {

	private WKBWriter wkbwriter = new WKBWriter();
	private WKBReader wkbreader = new WKBReader();
	
	
	@Override
	public List<byte[]> serialize(List<SimpleFeature> features) {
		
		List<byte[]> serializedData = new ArrayList<byte[]>();
		for (SimpleFeature f : features){
			serializedData.add(wkbwriter.write((Geometry)f.getDefaultGeometry()));
		}
		return serializedData;
	}

	@Override
	public void deserialize(List<byte[]> serializedData) {
		
		try {
			List<Geometry> features = new ArrayList<Geometry>();
			for (byte[] feature : serializedData){
				//features.add(DataUtilities.createFeature(ShapefileReader.FeatureType, line));
				features.add(wkbreader.read(feature));
			}
			//System.out.println(features.size());
		}
		catch (Exception ex){
			ex.printStackTrace();
			System.exit(1);
		}
	}

    @Override
	public String getName() {
		return "JTS WKB";
	}

}
