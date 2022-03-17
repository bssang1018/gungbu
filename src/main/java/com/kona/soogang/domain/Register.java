package com.kona.soogang.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Register {

    //복합키
    @EmbeddedId
    private RegisterId registerId;

    @Column(name="cancel_status")
    private String cancelStatus;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    @Builder
    public Register(RegisterId registerId, String cancelStatus, Date timestamp){
        this.registerId = registerId;
        this.cancelStatus = cancelStatus;
        this.timestamp = timestamp;
    }

}
