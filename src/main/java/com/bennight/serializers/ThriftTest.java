package com.bennight.serializers;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TDeserializer;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TBinaryProtocol;
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

public class ThriftTest extends AbstractSerializer {

	//private ThriftFeature tf = new ThriftFeature();
	private WKBWriter wkbwriter = new WKBWriter();
	private WKBReader wkbreader = new WKBReader();
	private TSerializer serializer = new TSerializer(new TBinaryProtocol.Factory());
	private TDeserializer deserializer = new TDeserializer(new TBinaryProtocol.Factory());
	
	@Override
	public List<byte[]> Serialize(List<SimpleFeature> features) {
		
		List<byte[]> serializedData = new ArrayList<byte[]>();
		try {
			for (SimpleFeature f : features){
				ThriftFeature tf = new ThriftFeature();
				tf.setGeometry(wkbwriter.write((Geometry)f.getDefaultGeometry()));
				tf.setId("8324893shjdjkhfjf");
				serializedData.add(serializer.serialize(tf));
			}
		}
		catch (Exception ex){
			ex.printStackTrace();
			System.exit(1);
		}
		return serializedData;
	}

	@Override
	public void Deserialize(List<byte[]> serializedData) {
		
		try {
			List<Geometry> features = new ArrayList<Geometry>();
			for (byte[] feature : serializedData){
				//features.add(DataUtilities.createFeature(ShapefileReader.FeatureType, line));
				ThriftFeature tf = new ThriftFeature();
				deserializer.deserialize(tf, feature);
				features.add(wkbreader.read(tf.getGeometry()));
			}
			//System.out.println(features.size());
		}
		catch (Exception ex){
			ex.printStackTrace();
			System.exit(1);
		}
	}

	public String GetSerializerName() {
		return "Thrift + WKB";
	}

}
