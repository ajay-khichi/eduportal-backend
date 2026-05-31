package com.ignishers.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Entity
@Table(name = "elective_group")
public class ElectiveGroup extends BaseEntity{

    @NotBlank(message = "Name can't be Empty")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Name can't be Empty")
    @Column(nullable = false)
    private String type;

    @Min(value = 0, message = "Selection can't be Negative")
    @NotNull(message = "Selection can't be Null")
    @Column(nullable = false)
    private Integer minSelection;
}
