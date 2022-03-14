package com.kona.soogang.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Register {

    //복합키
    @EmbeddedId
    private RegisterId registerId;

    @Column(name="cancel_status")
    private String cancelStatus;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;


}
