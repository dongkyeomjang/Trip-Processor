package com.dongkyeom.trip.processor.core.utility;

import com.mapbox.geojson.Point;
import com.mapbox.geojson.utils.PolylineUtils;

import java.util.ArrayList;
import java.util.List;


public class PolylineMergeUtil {

    public static String mergePolylines(String polyline1, String polyline2) {
        List<Point> decoded1 = PolylineUtils.decode(polyline1, 6);
        List<Point> decoded2 = PolylineUtils.decode(polyline2, 6);

        if (!decoded1.isEmpty() && !decoded2.isEmpty()) {
            Point lastOfFirst = decoded1.get(decoded1.size() - 1);
            Point firstOfSecond = decoded2.get(0);

            // 중복 좌표 제거
            if (pointsEqual(lastOfFirst, firstOfSecond)) {
                decoded2.remove(0);
            }
        }

        List<Point> merged = new ArrayList<>(decoded1);
        merged.addAll(decoded2);

        return PolylineUtils.encode(merged, 6);
    }

    private static boolean pointsEqual(Point p1, Point p2) {
        double epsilon = 1e-6;
        return Math.abs(p1.latitude() - p2.latitude()) < epsilon &&
                Math.abs(p1.longitude() - p2.longitude()) < epsilon;
    }
}

