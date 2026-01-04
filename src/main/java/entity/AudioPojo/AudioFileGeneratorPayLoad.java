package entity.AudioPojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AudioFileGeneratorPayLoad {
    public String originalFilename;
    public String fileType;
    public String mimeType;
}

