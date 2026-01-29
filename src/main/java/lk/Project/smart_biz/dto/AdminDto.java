package lk.Project.smart_biz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminDto {
    private String username;
    private String email;
    private String password;
}
