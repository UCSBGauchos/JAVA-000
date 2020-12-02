package me.bob.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentEntity {

    private Long id;

    private String name;

}
