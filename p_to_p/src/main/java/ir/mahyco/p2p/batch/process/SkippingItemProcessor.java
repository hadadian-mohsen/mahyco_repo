package ir.mahyco.p2p.batch.process;

import ir.mahyco.p2p.batch.object.IssuerTransaction;
import org.springframework.batch.item.ItemProcessor;

public class SkippingItemProcessor implements ItemProcessor<IssuerTransaction, IssuerTransaction> {
    @Override
    public IssuerTransaction process(IssuerTransaction item) throws Exception {
        return null;
    }
}
