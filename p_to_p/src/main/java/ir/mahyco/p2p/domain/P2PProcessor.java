package ir.mahyco.p2p.domain;

import ir.mahyco.p2p.repository.DBOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Slf4j
public class P2PProcessor {
    @Autowired
    DBOperator dbOperator;

    public void startDaily(int dayOffset)
    {
        String logId = "P4D_" + new Date(dayOffset).getTime() + "_OPR";
        log.info(logId + " - daily run for " + new Date(dayOffset));



    }
}
