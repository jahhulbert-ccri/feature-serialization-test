feature-serialization-test
==========================

Test of various geometry serialization methods

Uses shapefile from: http://www.nws.noaa.gov/geodata/catalog/county/html/county.htm

(specifically http://www.nws.noaa.gov/geodata/catalog/county/data/c_03de13.zip )

#To Run
Download shape file, unzip it somewhere, and edit the main method in App.java to point to the shape file.  Then

```
mvn clean compile
mvn exec:exec
```


#Results on an i7-920
``` 
Serializer:  GeoTools Simple Feature  [Deserialize: 11376.83 msec][Serialize: 11531.63 msec]
Serializer:                  JTS WKT  [Deserialize:  7240.80 msec][Serialize:  8215.21 msec]
Serializer:                  JTS WKB  [Deserialize:   256.66 msec][Serialize:   217.36 msec]
Serializer:             Thrift + WKB  [Deserialize:   291.97 msec][Serialize:   242.02 msec]
```

#ToDo
Add in tests for serializing attributes with geometry
