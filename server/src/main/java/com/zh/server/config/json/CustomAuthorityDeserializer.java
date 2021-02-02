package com.zh.server.config.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 自定义Authority(User实体)解析器。因为User.Authority没有构造函数，所以序列化会失败，这里重新序列化的方法
 *
 * @author ZH
 * @date 2021-02-02
 */
public class CustomAuthorityDeserializer extends JsonDeserializer {

    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
        JsonNode jsonNode=mapper.readTree(jsonParser);

        List<GrantedAuthority> grantedAuthorities=new LinkedList<>();
        Iterator<JsonNode> elements=jsonNode.elements();
        while(elements.hasNext()){
            JsonNode next=elements.next();
            JsonNode authority=next.get("authority");//authority是接口读取结果中的值
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.asText()));
        }
        return grantedAuthorities;
    }
}
