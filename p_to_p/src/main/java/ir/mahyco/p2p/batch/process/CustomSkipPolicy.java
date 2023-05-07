package ir.mahyco.p2p.batch.process;

import org.springframework.batch.core.step.skip.SkipLimitExceededException;

public class CustomSkipPolicy implements org.springframework.batch.core.step.skip.SkipPolicy {
    @Override
    public boolean shouldSkip(Throwable t, long skipCount) throws SkipLimitExceededException {
            return false;
    }
}
