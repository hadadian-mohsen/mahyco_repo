package ir.mahyco.p2p.repository.entity;

import jakarta.persistence.Column;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

public class IssuerDiff implements Serializable {
    @Id
    long diff_id   ;
    @Column
    String diff_value ;
    @Column
    Date run_date ;
    @Column
    String additional_data;
}
