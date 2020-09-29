package com.tesis.demo.service;

import com.tesis.demo.model.dto.PointZoneDto;

import java.util.List;

public class ComparePoints {
// A Java program to check if a given point
// lies inside a given polygon
// Refer https://www.geeksforgeeks.org/check-if-two-given-line-segments-intersect/
// for explanation of functions onSegment(),
// orientation() and doIntersect()

    // Define Infinite (Using INT_MAX
    // caused overflow problems)
    static int INF = 100000;

    // Given three colinear points p, q, r,
    // the function checks if point q lies
    // on line segment 'pr'
    static boolean onSegment(PointZoneDto p, PointZoneDto q, PointZoneDto r)
    {
        if (q.getLat() <= Math.max(p.getLat(), r.getLat()) &&
                q.getLat() >= Math.min(p.getLat(), r.getLat()) &&
                q.getLng() <= Math.max(p.getLng(), r.getLng()) &&
                q.getLng() >= Math.min(p.getLng(), r.getLng()))
        {
            return true;
        }
        return false;
    }

    // To find orientation of ordered triplet (p, q, r).
    // The function returns following values
    // 0 --> p, q and r are colinear
    // 1 --> Clockwise
    // 2 --> Counterclockwise
    static int orientation(PointZoneDto p, PointZoneDto q, PointZoneDto r)
    {
        double val = (q.getLng() - p.getLng()) * (r.getLat() - q.getLat())
                - (q.getLat() - p.getLat()) * (r.getLng() - q.getLng());

        if (val == 0)
        {
            return 0; // colinear
        }
        return (val > 0) ? 1 : 2; // clock or counterclock wise
    }

    // The function that returns true if
    // line segment 'p1q1' and 'p2q2' intersect.
    static boolean doIntersect(PointZoneDto p1, PointZoneDto q1,
                               PointZoneDto p2, PointZoneDto q2)
    {
        // Find the four orientations needed for
        // general and special cases
        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        // General case
        if (o1 != o2 && o3 != o4)
        {
            return true;
        }

        // Special Cases
        // p1, q1 and p2 are colinear and
        // p2 lies on segment p1q1
        if (o1 == 0 && onSegment(p1, p2, q1))
        {
            return true;
        }

        // p1, q1 and p2 are colinear and
        // q2 lies on segment p1q1
        if (o2 == 0 && onSegment(p1, q2, q1))
        {
            return true;
        }

        // p2, q2 and p1 are colinear and
        // p1 lies on segment p2q2
        if (o3 == 0 && onSegment(p2, p1, q2))
        {
            return true;
        }

        // p2, q2 and q1 are colinear and
        // q1 lies on segment p2q2
        if (o4 == 0 && onSegment(p2, q1, q2))
        {
            return true;
        }

        // Doesn't fall in any of the above cases
        return false;
    }

    // Returns true if the point p lies
    // inside the polygon[] with n vertices
    static boolean isInside(List<PointZoneDto> polygon, PointZoneDto p)
    {
        // There must be at least 3 vertices in polygon[]
        if (polygon.size() < 3)
        {
            return false;
        }

        // Create a point for line segment from p to infinite
        PointZoneDto extreme = new PointZoneDto()
                .lat(INF)
                .lng(p.getLng());

        // Count intersections of the above line
        // with sides of polygon
        int count = 0, i = 0;
        do
        {
            int next = (i + 1) % polygon.size();

            // Check if the line segment from 'p' to
            // 'extreme' intersects with the line
            // segment from 'polygon[i]' to 'polygon[next]'
            if (doIntersect(polygon.get(i), polygon.get(next), p, extreme))
            {
                // If the point 'p' is colinear with line
                // segment 'i-next', then check if it lies
                // on segment. If it lies, return true, otherwise false
                if (orientation(polygon.get(i), p, polygon.get(next)) == 0)
                {
                    return onSegment(polygon.get(i), p,
                            polygon.get(next));
                }

                count++;
            }
            i = next;
        } while (i != 0);

        // Return true if count is odd, false otherwise
        return (count % 2 == 1); // Same as (count%2 == 1)
    }

}
