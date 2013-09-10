namespace java com.bennight.serializers

struct ThriftFeature {
 1: required binary geometry;
 2: required string id;
 3: optional  map<string,string> attributes
}