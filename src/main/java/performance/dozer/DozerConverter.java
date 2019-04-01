package performance.dozer;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import performance.Converter;
import performance.model.destination.DestinationCode;
import performance.model.destination.Order;
import performance.model.source.SourceCode;
import performance.model.source.SourceOrder;

public class DozerConverter implements Converter
{
  private final Mapper mapper;

  public DozerConverter()
  {
    DozerBeanMapper mapper = new DozerBeanMapper();
    mapper.addMapping( DozerConverter.class.getResourceAsStream( "/dozer-mapping.xml" ) );
    this.mapper = mapper;
  }

  @Override
  public Order convert( SourceOrder sourceOrder )
  {
    return mapper.map( sourceOrder, Order.class );
  }

  @Override
  public DestinationCode convert( SourceCode sourceCode )
  {
    return mapper.map( sourceCode, DestinationCode.class );
  }
}
