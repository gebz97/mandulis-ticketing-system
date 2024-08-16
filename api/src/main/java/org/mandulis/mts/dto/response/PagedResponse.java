package org.mandulis.mts.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mandulis.mts.dto.PageInfo;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagedResponse<T> {
    private List<T> content;
    private PageInfo pageInfo;
}
