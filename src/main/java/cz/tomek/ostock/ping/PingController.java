package cz.tomek.ostock.ping;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ping")
@RequiredArgsConstructor
public class PingController {

    private final ObjectMapper objectMapper;

    @GetMapping
    public JsonNode ping() {
        return objectMapper
            .createObjectNode()
            .put("status", "OK v2");
    }

}
