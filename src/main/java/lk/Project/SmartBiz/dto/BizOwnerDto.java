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
    private String email;
    private String password;
    private String nicNumber;
}
