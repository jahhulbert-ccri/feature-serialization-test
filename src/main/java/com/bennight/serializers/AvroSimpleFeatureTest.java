package com.bennight.serializers;

import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.EncoderFactory;
import org.locationtech.geomesa.feature.AvroSimpleFeatureWriter;
import org.locationtech.geomesa.feature.FeatureSpecificReader;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AvroSimpleFeatureTest extends AbstractSerializer {

    private final SimpleFeatureType sft;
    private final FeatureSpecificReader reader;
    private final AvroSimpleFeatureWriter writer;

    public AvroSimpleFeatureTest(final SimpleFeatureType sft) {
        this.sft = sft;
        this.reader = FeatureSpecificReader.apply(sft);
        this.writer = new AvroSimpleFeatureWriter(sft);
    }

    @Override
    protected List<byte[]> serialize(List<SimpleFeature> features) throws IOException {
        final List<byte[]> ret = new ArrayList<byte[]>();
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BinaryEncoder encoder = null;
        for(final SimpleFeature sf: features) {
            baos.reset();
            encoder = EncoderFactory.get().directBinaryEncoder(baos, encoder);
            writer.write(sf, encoder);
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
