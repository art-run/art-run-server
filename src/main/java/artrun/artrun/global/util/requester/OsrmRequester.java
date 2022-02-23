package artrun.artrun.global.util.requester;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.leonard.PolylineUtils;
import io.leonard.Position;
import lombok.SneakyThrows;
import org.locationtech.jts.geom.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class OsrmRequester {

    // TODO refactor it
    @SneakyThrows
    public Geometry match(Geometry geometries) {

        List<Position> positions = Arrays.stream(geometries.getCoordinates()).map(c -> Position.fromLngLat(c.getX(), c.getY())).collect(Collectors.toList());
        String encodedPositions = PolylineUtils.encode(positions, 5);
        System.out.println("encodedPositions = " + encodedPositions);
        String jsonResult = WebClient.create().get()
                .uri(uriBuilder ->
                        uriBuilder
                                .scheme("http")
                                .host("router.project-osrm.org")
                                .pathSegment("match","v1", "foot", "polyline({encodedPosition})")
                                .build(encodedPositions))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> jsonMap = objectMapper.readValue(jsonResult, new TypeReference<Map<String, Object>>() {});
        if (jsonMap.get("code").equals("Ok")) {
            //noinspection unchecked
            ArrayList matchings = (ArrayList) jsonMap.get("matchings");
            LinkedHashMap newMatchings = (LinkedHashMap) matchings.get(0);
            String matchedGeometry = (String) newMatchings.get("geometry");
            List<Position> matchedPositions = PolylineUtils.decode(matchedGeometry, 5);
            ArrayList<Coordinate> coordinateArrayList = new ArrayList<>();

            for(Position position: matchedPositions) {
                coordinateArrayList.add(new Coordinate(position.getLongitude(), position.getLatitude()));
            }
            Coordinate[] coordinates = coordinateArrayList.toArray(Coordinate[]::new);
            return new GeometryFactory().createLineString(coordinates);
        } else {
            return geometries;
        }
    }

}
