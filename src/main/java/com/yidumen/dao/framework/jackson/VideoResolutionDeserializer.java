package com.yidumen.dao.framework.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.yidumen.dao.constant.VideoResolution;
import java.io.IOException;

/**
 *
 * @author 蔡迪旻
 */
public class VideoResolutionDeserializer extends JsonDeserializer<VideoResolution> {

    @Override
    public VideoResolution deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        return VideoResolution.getByDescript(jp.getText());
    }
    
}
