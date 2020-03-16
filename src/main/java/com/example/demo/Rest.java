package com.example.demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class Rest {
    @GetMapping("/")
    String test() throws IOException {
        HttpGet get=new HttpGet("https://code.junookyo.xyz/api/ncov-moh/data.json?fbclid=IwAR0e6MvY5bRj5uWO8p7eeFMOHXxLXmVLmkgGvoeD8U1AJpyKHeWbW7OEnRY");
        HttpClient client= HttpClients.createDefault();
        HttpResponse response=client.execute(get);
        String re= EntityUtils.toString(response.getEntity());
        ObjectMapper mapper=new ObjectMapper();
        JsonNode node=mapper.readTree(re);
        String out="----TG----\\n" +
                "Số ca nhiễm : "+node.get("data").get("global").get("cases").asText()+
                "\\nSố ca tử vong: "+node.get("data").get("global").get("deaths").asText()+
                "\\nSố ca chữa khỏi: "+node.get("data").get("global").get("recovered").asText()+
                "\\n----VN----\\n" +
                "Số ca nhiễm : "+node.get("data").get("vietnam").get("cases").asText()+
                "\\nSố ca tử vong: "+node.get("data").get("vietnam").get("deaths").asText()+
                "\\nSố ca chữa khỏi: "+node.get("data").get("vietnam").get("recovered").asText();
        return "{\n" +
                "  \"messages\": [\n" +
                "    {\n" +
                "      \"attachment\": {\n" +
                "        \"type\": \"template\",\n" +
                "        \"payload\": {\n" +
                "          \"template_type\": \"button\",\n" +
                "          \"text\": \"Hello!\",\n" +
                "          \"buttons\": [\n" +
                "            {\n" +
                "              \"type\": \"show_block\",\n" +
                "              \"block_names\": [\"name of block\"],\n" +
                "              \"title\": \"Show Block\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"web_url\",\n" +
                "              \"url\": \"https://rockets.chatfuel.com\",\n" +
                "              \"title\": \"Visit Website\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"url\": \"https://rockets.chatfuel.com/api/welcome\",\n" +
                "              \"type\":\"json_plugin_url\",\n" +
                "              \"title\":\"Postback\"\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }
}
