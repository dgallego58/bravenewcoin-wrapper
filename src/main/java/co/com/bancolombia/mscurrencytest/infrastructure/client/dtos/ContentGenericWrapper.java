package co.com.bancolombia.mscurrencytest.infrastructure.client.dtos;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ContentGenericWrapper<T> {

    List<T> content;
}
