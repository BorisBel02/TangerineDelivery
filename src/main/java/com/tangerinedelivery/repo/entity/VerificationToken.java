<<<<<<<< HEAD:src/main/java/com/tangerinedelivery/repo/entity/VerificationToken.java
package com.tangerinedelivery.repo.entity;
========
package com.tangerinedelivery.repos.entities;
>>>>>>>> origin/develop:src/main/java/com/tangerinedelivery/repos/entities/VerificationToken.java

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Entity
@Setter
@Getter
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(targetEntity = UserEntity.class, fetch = FetchType.EAGER)
    private UserEntity userEntity;

    private Date expiryDate;

    private Date calculateExpiryDate(int expiryTimeInMinutes){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
}
