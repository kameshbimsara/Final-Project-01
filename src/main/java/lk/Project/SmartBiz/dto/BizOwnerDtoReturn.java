package lk.Project.SmartBiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BizOwnerDtoReturn {
    private Integer id;
    private String name;
    private String username;
    private String token;
}
