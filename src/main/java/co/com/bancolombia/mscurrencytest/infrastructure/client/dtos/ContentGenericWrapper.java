package co.com.bancolombia.mscurrencytest.infrastructure.client.dtos;

import java.util.List;


public class ContentGenericWrapper<T> {

    private final List<T> content;

    private ContentGenericWrapper() {
        content = List.of();
    }

    public ContentGenericWrapper(List<T> content) {
        this.content = content == null ? List.of() : content;
    }

    public List<T> getContent() {
        return content;
    }
}
