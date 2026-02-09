package lk.Project.smart_biz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DateReqDto {
    private LocalDate date;
    private Integer businessId;
}
