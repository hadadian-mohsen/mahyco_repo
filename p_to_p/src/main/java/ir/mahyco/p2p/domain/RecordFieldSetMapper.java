package ir.mahyco.p2p.domain;

import ir.mahyco.p2p.batch.object.IssuerTransaction;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class RecordFieldSetMapper implements FieldSetMapper<IssuerTransaction> {
    @Override
    public IssuerTransaction mapFieldSet(FieldSet fieldSet) throws BindException {
        return null;
    }
}
