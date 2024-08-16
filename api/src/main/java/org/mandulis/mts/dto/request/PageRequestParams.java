package org.mandulis.mts.dto.request;

import lombok.*;
import org.springframework.web.bind.annotation.ModelAttribute;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageRequestParams {

    private int page = 0;
    private int size = 10;

    @ModelAttribute
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @ModelAttribute
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
