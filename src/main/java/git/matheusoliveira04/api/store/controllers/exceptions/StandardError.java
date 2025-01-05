package git.matheusoliveira04.api.store.controllers.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class StandardError {

    private LocalDateTime dateTime;
    private Integer status;
    private String url;
    private List<String> message;
}
