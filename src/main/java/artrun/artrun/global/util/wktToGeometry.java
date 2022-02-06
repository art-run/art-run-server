package artrun.artrun.global.util;

import artrun.artrun.global.error.exception.InvalidWktException;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

public class wktToGeometry {
    public static Geometry wktToGeometry(String wellKnownText) {
        try {
            return new WKTReader().read(wellKnownText);
        } catch(ParseException e) {
            throw new InvalidWktException("invalid wellKnownText: " + wellKnownText);
        }
    }
}
