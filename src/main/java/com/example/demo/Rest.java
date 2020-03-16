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
                "\\n Số ca tử vong: "+node.get("data").get("global").get("deaths").asText()+
                "\\n Số ca chữa khỏi: "+node.get("data").get("global").get("recovered").asText()+
                "\\n----VN----\\n" +
                "Số ca nhiễm : "+node.get("data").get("vietnam").get("cases").asText()+
                "\\n Số ca tử vong: "+node.get("data").get("vietnam").get("deaths").asText()+
                "\\n Số ca chữa khỏi: "+node.get("data").get("vietnam").get("recovered").asText();
        return "    {\n" +
                "        messages: [\n" +
                "            {text: \""+out+"\"}\n" +
                "        ]\n" +
                "    }";
    }
}
