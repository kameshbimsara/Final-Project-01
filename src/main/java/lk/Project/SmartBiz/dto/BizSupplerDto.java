package lk.Project.SmartBiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class BizSupplerDto {
    private Integer id;
    private String companyName;
    private String contactNo;
    private Integer business_id;
}
