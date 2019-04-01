package performance;

import performance.dozer.DozerConverter;
import performance.jmapper.JMapperConverter;
import performance.mapstruct.MapStructConverter;
import performance.model.destination.DestinationCode;
import performance.model.destination.Order;
import performance.model.source.AccountStatus;
import performance.model.source.Address;
import performance.model.source.DeliveryData;
import performance.model.source.Discount;
import performance.model.source.OrderStatus;
import performance.model.source.PaymentType;
import performance.model.source.Product;
import performance.model.source.RefundPolicy;
import performance.model.source.Review;
import performance.model.source.Shop;
import performance.model.source.SourceCode;
import performance.model.source.SourceOrder;
import performance.model.source.User;
import performance.modelmapper.ModelMapperConverter;
import performance.orika.OrikaConverter;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Group;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.RunnerException;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

//@Fork(value = 1, warmups = 5)
@Fork
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.All)
@Measurement(iterations = 5)
@State(Scope.Group)
public class MappingFrameworksPerformance {
    private SourceOrder sourceOrder = null;
    private SourceCode sourceCode = null;
    private static final OrikaConverter ORIKA_CONVERTER = new OrikaConverter();
    private static final JMapperConverter JMAPPER_CONVERTER = new JMapperConverter();
    private static final ModelMapperConverter MODEL_MAPPER_CONVERTER = new ModelMapperConverter();
    private static final DozerConverter DOZER_CONVERTER = new DozerConverter();

    @Setup
    public void setUp() {
        User user = new User("John", "John@doe.com", AccountStatus.ACTIVE);
        RefundPolicy refundPolicy = new RefundPolicy(true, 30, Collections
          .singletonList("Refundable only if not used!"));

        Product product = new Product(BigDecimal.valueOf(10.99),
          100,
          "Sample Product",
          "Sample Product to be sold",
          true,
          refundPolicy
        );

        Discount discount = new Discount(Instant.now().toString(), Instant.now().toString(), BigDecimal.valueOf(5.99));
        Address deliveryAddress = new Address("Washington Street 5", "New York", "55045", "USA");
        DeliveryData deliveryData = new DeliveryData(deliveryAddress, true, "", 10);
        Address shopAddress = new Address("Roosvelt Street 9", "Boston", "55042", "USA");
        User reviewingUser = new User("John", "Johhny@John.com", AccountStatus.ACTIVE);
        User negativeReviewingUser = new User("Carl", "Carl@Coral.com", AccountStatus.ACTIVE);
        Review review = new Review(5, 5, 5, reviewingUser, "The best shop I've ever bought things in");

        Review negativeReview = new Review(1, 1, 1, negativeReviewingUser, "I will never buy anything again here!");

        List<Review> reviewList = new ArrayList<>();
        reviewList.add(review);
        reviewList.add(negativeReview);
        Shop shop = new Shop("Super Shop", shopAddress, "www.super-shop.com", reviewList);

        sourceOrder = new SourceOrder(OrderStatus.CONFIRMED,
          Instant.now().toString(),
          Instant.MAX.toString(),
          PaymentType.TRANSFER,
          discount,
          deliveryData,
          user,
          Collections.singletonList(product),
          shop,
          1
        );

        sourceCode = new SourceCode("This is source code!");
    }

    public static void main(String[] args) throws IOException, RunnerException {
        org.openjdk.jmh.Main.main(args);
    }

    @Benchmark
    @Group("realLifeTest")
    public Order orikaMapperRealLifeBenchmark() {
        return ORIKA_CONVERTER.convert(sourceOrder);
    }

    @Benchmark
    @Group("realLifeTest")
    public Order jmapperRealLifeBenchmark() {
        return JMAPPER_CONVERTER.convert(sourceOrder);
    }

    @Benchmark
    @Group("realLifeTest")
    public Order modelMapperRealLifeBenchmark() {
        return MODEL_MAPPER_CONVERTER.convert(sourceOrder);
    }

    @Benchmark
    @Group("realLifeTest")
    public Order dozerMapperRealLifeBenchmark() {
        return DOZER_CONVERTER.convert(sourceOrder);
    }

    @Benchmark
    @Group("realLifeTest")
    public Order mapStructRealLifeMapperBenchmark() {
        return MapStructConverter.MAPPER.convert(sourceOrder);
    }

    @Benchmark
    @Group("simpleTest")
    public DestinationCode orikaMapperSimpleBenchmark() {
        return ORIKA_CONVERTER.convert(sourceCode);
    }

    @Benchmark
    @Group("simpleTest")
    public DestinationCode jmapperSimpleBenchmark() {
        return JMAPPER_CONVERTER.convert(sourceCode);
    }

    @Benchmark
    @Group("simpleTest")
    public DestinationCode modelMapperBenchmark() {
        return MODEL_MAPPER_CONVERTER.convert(sourceCode);
    }

    @Benchmark
    @Group("simpleTest")
    public DestinationCode dozerMapperSimpleBenchmark() {
        return DOZER_CONVERTER.convert(sourceCode);
    }

    @Benchmark
    @Group("simpleTest")
    public DestinationCode mapStructMapperSimpleBenchmark() {
        return MapStructConverter.MAPPER.convert(sourceCode);
    }
}
