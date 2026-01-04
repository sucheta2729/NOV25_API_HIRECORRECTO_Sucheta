package entity.VideoPojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoFileGeneratorPayLoad {
        public String originalFilename;
        public String fileType;
        public String mimeType;
}
