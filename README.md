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
Serializer:  GeoTools Simple Feature  [Serialize: 11376.83 msec][Deserialize: 11531.63 msec]
Serializer:                  JTS WKT  [Serialize:  7369.06 msec][Deserialize:  8460.18 msec]
Serializer:                  JTS WKB  [Serialize:   252.66 msec][Deserialize:   198.91 msec]
Serializer:             Thrift + WKB  [Serialize:   296.23 msec][Deserialize:   231.27 msec]
```

#Note
GeoTools Simple Feature and Thrift + WKB tests serialize all attributes  - the JTS tests just serialize geometries
