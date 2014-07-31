package com.bennight.serializers;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.thrift.TDeserializer;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.geotools.data.DataUtilities;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeDescriptor;

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
	public List<byte[]> serialize(List<SimpleFeature> features) {
		
		List<byte[]> serializedData = new ArrayList<byte[]>();
		try {
			for (SimpleFeature f : features){
				ThriftFeature tf = new ThriftFeature();
				Map<String, String> attributes = new HashMap<String, String>();
				tf.setId("def");
				for (AttributeDescriptor ad : ShapefileReader.FeatureType.getAttributeDescriptors()){
					if (ad.getName().toString() == "the_geom"){
						tf.setGeometry(wkbwriter.write((Geometry)f.getAttribute(ad.getName())));
					} else if (ad.getName().toString() == "ID"){
						tf.setId(f.getAttribute(ad.getName()).toString());
					} else {
						attributes.put(ad.getName().toString(), f.getAttribute(ad.getName()).toString());
					}
				}
				tf.setAttributes(attributes);
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
	public void deserialize(List<byte[]> serializedData) {
		
		try {
			List<ThriftFeature> features = new ArrayList<ThriftFeature>();
			for (byte[] feature : serializedData){
				//features.add(DataUtilities.createFeature(ShapefileReader.FeatureType, line));
				ThriftFeature tf = new ThriftFeature();
				deserializer.deserialize(tf, feature);
				features.add(tf);
				wkbreader.read(tf.getGeometry()); //do something later on with this - just incur penalty now
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
		return "Thrift + WKB";
	}

}
