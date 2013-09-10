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
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.io.WKTWriter;
import com.vividsolutions.jts.geom.Geometry;

public class WKTTest extends AbstractSerializer {

	private WKTWriter wktwriter = new WKTWriter();
	private WKTReader wktreader = new WKTReader();
	
	
	@Override
	public List<byte[]> Serialize(List<SimpleFeature> features) {
		
		StringBuilder sb = new StringBuilder();
		for (SimpleFeature f : features){
			sb.append(wktwriter.write((Geometry)f.getDefaultGeometry()));
			sb.append("\r\n");
		}
		List<byte[]> serializedData = new ArrayList<byte[]>();
		serializedData.add(sb.toString().getBytes(StandardCharsets.UTF_8));
		return serializedData;
		
	}

	@Override
	public void Deserialize(List<byte[]> serializedData) {
		
		try {
			List<Geometry> features = new ArrayList<Geometry>();
			for (String line : (new String(serializedData.get(0), StandardCharsets.UTF_8).split("\r\n"))){
				//features.add(DataUtilities.createFeature(ShapefileReader.FeatureType, line));
				features.add(wktreader.read(line));
			}
			//System.out.println(features.size());
		}
		catch (Exception ex){
			ex.printStackTrace();
			System.exit(1);
		}
	}

	public String GetSerializerName() {
		return "JTS WKT";
	}

}
