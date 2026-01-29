package lk.Project.smart_biz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BusinessLoginResponseDto {
    private Integer id;
    private String token;
    private String ownerName;
}
