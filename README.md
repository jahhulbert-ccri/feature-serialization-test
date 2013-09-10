feature-serialization-test
==========================

Test of various geometry serialization methods

Uses shapefile from: http://www.nws.noaa.gov/geodata/catalog/county/html/county.htm

(specifically http://www.nws.noaa.gov/geodata/catalog/county/data/c_03de13.zip )

Shapefile has ~31k features in it.

#To Run
Download shape file, unzip it somewhere, and edit the main method in App.java to point to the shape file.  Then

```
mvn clean compile
mvn exec:exec
```


#Results on an i7-920
``` 
Serializer:  GeoTools Simple Feature  [Serialize: 11864.52 msec][Deserialize: 12044.95 msec]
Serializer:                  JTS WKT  [Serialize:  7403.86 msec][Deserialize:  8394.77 msec]
Serializer:                  JTS WKB  [Serialize:   291.33 msec][Deserialize:   200.09 msec]
Serializer:             Thrift + WKB  [Serialize:   334.04 msec][Deserialize:   247.26 msec]
```

#Note
GeoTools Simple Feature and Thrift + WKB tests serialize all attributes  - the JTS tests just serialize geometries
