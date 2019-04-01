package performance.modelmapper;

import performance.Converter;
import performance.model.destination.DestinationCode;
import performance.model.source.SourceCode;
import performance.model.source.SourceOrder;
import performance.model.destination.Order;
import org.modelmapper.ModelMapper;

public class ModelMapperConverter implements Converter
{
    private ModelMapper modelMapper;

    public ModelMapperConverter()
    {
        modelMapper = new ModelMapper();
    }

    @Override
    public Order convert( SourceOrder sourceOrder )
    {
        return modelMapper.map( sourceOrder, Order.class );
    }

    @Override
    public DestinationCode convert( SourceCode sourceCode )
    {
        return modelMapper.map( sourceCode, DestinationCode.class );
    }
}
