package lk.Project.SmartBiz.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDto {
    private Integer id;
    private String username;
    private String email;
    private String password;
    private String token; // dynamically generated, not stored in DB
}
