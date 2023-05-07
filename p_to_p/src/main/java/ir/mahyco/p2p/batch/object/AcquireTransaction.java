package ir.mahyco.p2p.batch.object;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@XmlRootElement(name = "AcquireTransaction")
@Data
public class AcquireTransaction {

    private String cardNo;
    private int trace;
    private String transactionDate;
    private double amount;

}
