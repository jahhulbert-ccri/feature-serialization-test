package com.bennight;

import com.bennight.serializers.AvroSimpleFeatureTest;
import com.bennight.serializers.SerializerInterface;
import com.bennight.serializers.ThriftTest;
import geomesa.feature.AvroSimpleFeatureFactory;
import org.opengis.feature.simple.SimpleFeature;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	String shapefile = "/home/ahulbert/Desktop/c_04jn14c/c_04jn14.shp";

        AvroSimpleFeatureFactory.init();

        List<SimpleFeature> features = null;
		try {
			features = ShapefileReader.ReadShapefile(new File(shapefile));
		} catch (IOException e1) {
			e1.printStackTrace();
			System.exit(1);
		}
    	//Warm up JIT
        SerializerInterface[] interfaces = new SerializerInterface[] {
               // new SimpleFeatureTest(),
                //new WKTTest(),
                //new WKBTest(),
                new ThriftTest(),
                new AvroSimpleFeatureTest(features.get(0).getFeatureType())
        };

        //Warm up JIT
        for (SerializerInterface sft : interfaces)
        {
	        try {
				sft.getPerformance(features);
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        
        for (SerializerInterface sft : interfaces)
        {
	        try {
	        	System.gc();
				double[] vals = sft.getPerformance(features);
				System.out.println(String.format("Serializer: %s  [serialize: %s msec][deserialize: %s msec]",
						pad(sft.getName(),"                        "),
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
