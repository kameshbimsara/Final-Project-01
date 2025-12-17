package lk.Project.SmartBiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BizOwnerLoginResponse {
    private String token;
    private Integer ownerId;
    private String ownerName;
}
