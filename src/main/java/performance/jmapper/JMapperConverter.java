package performance.jmapper;

import performance.Converter;
import performance.model.destination.DestinationCode;
import performance.model.source.SourceCode;
import performance.model.source.SourceOrder;
import performance.model.destination.Order;
import com.googlecode.jmapper.JMapper;
import com.googlecode.jmapper.api.JMapperAPI;

public class JMapperConverter implements Converter {

    private JMapper realLifeMapper;
    private JMapper simpleMapper;

    public JMapperConverter() {
        JMapperAPI api = new JMapperAPI().add(JMapperAPI.mappedClass(Order.class));
        realLifeMapper = new JMapper(Order.class, SourceOrder.class, api);
        JMapperAPI simpleApi = new JMapperAPI().add(JMapperAPI.mappedClass(DestinationCode.class));
        simpleMapper = new JMapper(DestinationCode.class, SourceCode.class, simpleApi);
    }

    @Override
    public Order convert(SourceOrder sourceOrder) {
        return (Order) realLifeMapper.getDestination(sourceOrder);
    }

    @Override
    public DestinationCode convert(SourceCode sourceCode) {
        return (DestinationCode) simpleMapper.getDestination(sourceCode);
    }
}
