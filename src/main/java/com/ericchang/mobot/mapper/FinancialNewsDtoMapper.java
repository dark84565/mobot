package com.ericchang.mobot.mapper;

import com.ericchang.mobot.domain.FinancialNewsData;
import com.ericchang.mobot.dto.FinancialNews;
import com.ericchang.mobot.dto.FinancialNewsResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.mapstruct.*;

@Mapper(
    componentModel = "spring",
    collectionMappingStrategy = CollectionMappingStrategy.TARGET_IMMUTABLE,
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
    builder = @Builder)
public interface FinancialNewsDtoMapper {
  default List<FinancialNewsData> fromResponse(FinancialNewsResponse source) {
    return toData(source.getFinancialNewsResultSet().getFinancialNewsResult());
  }

  List<FinancialNewsData> toData(List<FinancialNews> source);

  @Mapping(target = "date", source = "date", dateFormat = "yyyy/MM/dd")
  FinancialNewsData toData(FinancialNews source);

  @Named("convertStringToLocalDate")
  default LocalDate convertStringToLocalDate(String source) {
    return LocalDate.parse(source, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
  }
}
