package artrun.artrun.global.util.requester;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.leonard.PolylineUtils;
import io.leonard.Position;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class OsrmRequester {
    /**
     * OSRM match로 맵매칭 해보고, 맵매칭 결과가 있으면 맵매칭된 geometry를 반환 없으면 그대로 반환.
     * @param geometry
     * @return Geometry
     */
    public Geometry requestMatch(Geometry geometry) {
        try {
            log.info("requestMatch - origin: " + geometry.toString());
            String positions = PolylineUtils.encode(Arrays.stream(geometry.getCoordinates()).map(c -> Position.fromLngLat(c.getY(), c.getX())).collect(Collectors.toList()), 5);

            String jsonResult = receiveMatchResponse(positions);

            // to get matchedPositions
            Map<String, Object> jsonMap = new ObjectMapper().readValue(jsonResult, new TypeReference<Map<String, Object>>() {});
            LinkedHashMap matchings = (LinkedHashMap) ((ArrayList) jsonMap.get("matchings")).get(0);
            String matchedGeometry = (String) matchings.get("geometry");
            List<Position> matchedPositions = PolylineUtils.decode(matchedGeometry, 5);

            ArrayList<Coordinate> coordinateArrayList = new ArrayList<>();
            for (Position position : matchedPositions) {
                coordinateArrayList.add(new Coordinate(position.getLatitude(), position.getLongitude()));
            }
            Coordinate[] coordinates = coordinateArrayList.toArray(Coordinate[]::new);

            log.info("requestMatch - matched: " + new GeometryFactory().createLineString(coordinates).toString());
            return new GeometryFactory().createLineString(coordinates);
        } catch (Exception exception) {
            log.info("requestMatch - catch exception");
            return geometry;
        }
    }

    private String receiveMatchResponse(String positions) {
        return WebClient.create().get()
                .uri(uriBuilder ->
                        uriBuilder
                                .scheme("http")
                                .host("router.project-osrm.org")
                                .pathSegment("match", "v1", "foot", "polyline({encodedPosition})")
                                .build(positions))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, response -> Mono.error(new RuntimeException("convert fail")))
                .bodyToMono(String.class).block();
    }

}
