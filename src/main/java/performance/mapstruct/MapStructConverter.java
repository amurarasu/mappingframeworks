package performance.mapstruct;

import performance.Converter;
import performance.model.destination.DestinationCode;
import performance.model.source.SourceCode;
import performance.model.source.SourceOrder;
import performance.model.destination.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MapStructConverter extends Converter {

    MapStructConverter MAPPER = Mappers.getMapper(MapStructConverter.class);

    @Mapping(source = "status", target = "orderStatus")
    @Override
    Order convert( SourceOrder sourceOrder );

    @Override
    DestinationCode convert( SourceCode sourceCode );
}
