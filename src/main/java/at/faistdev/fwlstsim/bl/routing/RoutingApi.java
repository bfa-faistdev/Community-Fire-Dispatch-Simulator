package at.faistdev.fwlstsim.bl.routing;

import at.faistdev.fwlstsim.bl.game.GameProperties;
import at.faistdev.fwlstsim.bl.routing.objects.Feature;
import at.faistdev.fwlstsim.bl.routing.objects.RouteResponse;
import at.faistdev.fwlstsim.bl.routing.objects.Segment;
import at.faistdev.fwlstsim.bl.routing.objects.Step;
import at.faistdev.fwlstsim.dataaccess.entities.Location;
import at.faistdev.fwlstsim.dataaccess.entities.Waypoint;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class RoutingApi {

    protected static List<Waypoint> getRoute(Location startLocation, Location endLocation) {
        Client client = ClientBuilder.newClient();
        String requestUrl = "https://api.openrouteservice.org/v2/directions/driving-car?api_key=" + GameProperties.ROUTING_API_KEY
                + "&start=" + startLocation.getLng() + "," + startLocation.getLat()
                + "&end=" + endLocation.getLng() + "," + endLocation.getLat();

        Response response = client.target(requestUrl)
                .request(MediaType.TEXT_PLAIN_TYPE)
                .header("Accept", "application/json, application/geo+json, application/gpx+xml, img/png; charset=utf-8")
                .get();

        if (response.getStatus() != 200) {
            throw new IllegalStateException("Received status " + response.getStatus());
        }

        RouteResponse routeBody = response.readEntity(RouteResponse.class);
        List<Waypoint> waypoints = parseBody(routeBody);
        System.out.println("getRoute: " + waypoints);

        return waypoints;
    }

    private static List<Waypoint> parseBody(RouteResponse body) {
        List<Waypoint> finalWaypoints = new ArrayList<>();

        Feature feature = body.features.get(0);
        Segment segment = feature.properties.segments.get(0);
        List<Step> steps = segment.steps;

        for (Step step : steps) {
            double durationInSeconds = step.duration;
            List<Integer> wayPoints = step.way_points;
            double secondsPerWaypoint = durationInSeconds / (wayPoints.size() - 1);

            // Skip first waypoint, because its the one where we are currently at
            for (int i = 1; i < wayPoints.size(); i++) {
                Integer wayPointIdx = wayPoints.get(i);
                List<Double> coordinates = getCoordinatesOfWaypoint(feature, wayPointIdx);

                finalWaypoints.add(new Waypoint(new Location("", coordinates.get(1), coordinates.get(0)), (long) secondsPerWaypoint));
            }
        }

        return finalWaypoints;
    }

    private static List<Double> getCoordinatesOfWaypoint(Feature feature, int idx) {
        return feature.geometry.coordinates.get(idx);
    }

    // public static void main(String[] args) {
    //     Location startLoc = new Location("", 46.939554, 15.317044);
    //     Location endLoc = new Location("", 46.926147, 15.310242);
    //     RoutingApi.getRoute(startLoc, endLoc);
    // }
}
