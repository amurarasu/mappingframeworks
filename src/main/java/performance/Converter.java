package performance;

import performance.model.destination.DestinationCode;
import performance.model.destination.Order;
import performance.model.source.SourceCode;
import performance.model.source.SourceOrder;

public interface Converter {
    Order convert( SourceOrder sourceOrder );
    DestinationCode convert( SourceCode sourceCode );
}
