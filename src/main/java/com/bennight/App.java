package com.bennight;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.opengis.feature.simple.SimpleFeature;

import com.bennight.serializers.SerializerInterface;
import com.bennight.serializers.SimpleFeatureTest;
import com.bennight.serializers.ThriftTest;
import com.bennight.serializers.WKBTest;
import com.bennight.serializers.WKTTest;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	String shapefile = "d:/tempshape/c_03de13.shp";
    	
    	SerializerInterface[] interfaces = new SerializerInterface[] {new SimpleFeatureTest(), new WKTTest(), new WKBTest(),new ThriftTest()};
    	
    	List<SimpleFeature> features = null;
		try {
			features = ShapefileReader.ReadShapefile(new File(shapefile));
		} catch (IOException e1) {
			e1.printStackTrace();
			System.exit(1);
		}
    	//Warm up JIT
        for (SerializerInterface sft : interfaces)
        {
	        try {
				double[] vals = sft.GetSerializationPerformance(features);
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        
        for (SerializerInterface sft : interfaces)
        {
	        try {
	        	System.gc();
				double[] vals = sft.GetSerializationPerformance(features);
				System.out.println(String.format("Serializer: %s  [Deserialize: %s msec][Serialize: %s msec]", 
						pad(sft.GetSerializerName(),"                        "), 
						pad(String.format("%.2f", vals[0] / 1000000f),"        "),  
						pad(String.format("%.2f", vals[1] / 1000000f),"        ")));
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }
    
    public static String pad(String string, String pad) {
    	  return (pad + string).substring(string.length());
    	}
}
