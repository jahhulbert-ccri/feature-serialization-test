package com.bennight.serializers;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.io.WKTWriter;
import org.opengis.feature.simple.SimpleFeature;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class WKTTest extends AbstractSerializer {

	private WKTWriter wktwriter = new WKTWriter();
	private WKTReader wktreader = new WKTReader();
	
	
	@Override
	public List<byte[]> serialize(List<SimpleFeature> features) {
		
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
	public void deserialize(List<byte[]> serializedData) {
		
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

    @Override
	public String getName() {
		return "JTS WKT";
	}

}
