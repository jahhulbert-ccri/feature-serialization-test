package com.bennight.serializers;

import geomesa.feature.AvroSimpleFeature;
import geomesa.feature.FeatureSpecificReader;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class AvroSimpleFeatureTest extends AbstractSerializer {

    private final SimpleFeatureType sft;
    private final FeatureSpecificReader reader;

    public AvroSimpleFeatureTest(final SimpleFeatureType sft) {
        this.sft = sft;
        this.reader = FeatureSpecificReader.apply(sft);
    }

    @Override
    protected List<byte[]> serialize(List<SimpleFeature> features) {
        final List<byte[]> ret = new ArrayList<byte[]>();
        for(final SimpleFeature sf: features) {
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ((AvroSimpleFeature) sf).write(baos);
            ret.add(baos.toByteArray());
        }
        return ret;
    }

    @Override
    protected void deserialize(List<byte[]> serializedData) {
        final List<SimpleFeature> parsed = new ArrayList<SimpleFeature>();
        BinaryDecoder decoder = null;
        for(final byte[] bytes: serializedData) {
            decoder = DecoderFactory.get().binaryDecoder(new ByteArrayInputStream(bytes), decoder);
            parsed.add(reader.read(null, decoder));
        }
    }

    @Override
    public String getName() {
        return "AvroSimpleFeature";
    }
}
