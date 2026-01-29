package lk.Project.smart_biz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessDto {
    private Integer id;
    private String name;
    private String location;
    private LocalDate regDate;
    private String ownerName;
    private String ownerContact;
    private String username;
    private String password;
    private Integer status;
    private Integer adminId;

    public BusinessDto(Integer id, String name, LocalDate regDate, Integer status, String location, String ownerContact, String username) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.regDate = regDate;
        this.status = status;
        this.ownerContact = ownerContact;
        this.username = username;
    }
}
