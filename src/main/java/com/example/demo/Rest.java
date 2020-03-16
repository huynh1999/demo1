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
        HttpGet get=new HttpGet("http://corona-js.herokuapp.com/apidata");
        HttpClient client= HttpClients.createDefault();
        HttpResponse response=client.execute(get);
        String re= EntityUtils.toString(response.getEntity());
        ObjectMapper mapper=new ObjectMapper();
        int tong=0,chet=0,cuu=0;
        JsonNode listnode=mapper.readTree(re);
        JsonNode vn = null;
        for(JsonNode node:listnode)
        {
            tong+=node.get("Total_Cases").asInt();
            chet+=node.get("Total_Deaths").asInt();
            cuu+=node.get("Total_Recovered").asInt();
            if(node.get("Country_Name").asText().equals("Vietnam"))vn=node;
        }
        assert vn != null;
        String out="----TG----\\n" +
                "Số ca nhiễm : "+tong+
                "\\nSố ca tử vong: "+chet+
                "\\nSố ca chữa khỏi: "+cuu+
                "\\n----VN----\\n" +
                "Số ca nhiễm : "+vn.get("Total_Cases").asText()+"("+vn.get("New_Cases").asText()+")"+
                "\\nSố ca tử vong: "+vn.get("Total_Deaths").asText()+
                "\\nSố ca chữa khỏi: "+vn.get("Total_Recovered").asText();
        return "{\n" +
                "  \"messages\": [\n" +
                "    {\n" +
                "      \"attachment\": {\n" +
                "        \"type\": \"template\",\n" +
                "        \"payload\": {\n" +
                "          \"template_type\": \"button\",\n" +
                "          \"text\": \""+out+"\",\n" +
                "          \"buttons\": [\n" +
                "            {\n" +
                "              \"type\": \"show_block\",\n" +
                "              \"block_names\": [\"Update Corona\"],\n" +
                "              \"title\": \"Update Corona\"\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }
}
