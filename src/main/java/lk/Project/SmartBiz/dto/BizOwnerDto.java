package lk.Project.SmartBiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BizOwnerDto {
    private Integer id;
    private String name;
    private String username;
    private String password;
    private Integer business_id;
    private String token; // dynamically generated, not stored
}
