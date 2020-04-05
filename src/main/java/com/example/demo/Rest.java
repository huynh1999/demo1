package com.example.demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
        response=client.execute(new HttpGet("https://code.junookyo.xyz/api/ncov-moh/data.json"));
        String reTG= EntityUtils.toString(response.getEntity());
        JsonNode TG=mapper.readTree(reTG);
        JsonNode listnode=mapper.readTree(re);
        JsonNode vn = null;
        for(JsonNode node:listnode)
        {
            if(node.get("Country_Name").asText().equals("Vietnam")){vn=node;
            break;}
        }
        assert vn != null;
        String out="----TG----\\n" +
                "Số ca nhiễm : "+TG.get("data").get("global").get("cases").asText()+
                "\\nSố ca tử vong: "+TG.get("data").get("global").get("deaths").asText()+
                "\\nSố ca chữa khỏi: "+TG.get("data").get("global").get("recovered").asText()+
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
    @GetMapping("/ip")
    String ip(HttpServletRequest request)
    {
        return request.getRemoteAddr();
    }
}
