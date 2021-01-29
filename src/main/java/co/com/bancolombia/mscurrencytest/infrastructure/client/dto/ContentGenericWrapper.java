package co.com.bancolombia.mscurrencytest.infrastructure.client.dto;

import java.util.ArrayList;
import java.util.List;


public class ContentGenericWrapper<T> {

    private final List<T> content;

    public ContentGenericWrapper() {
        this.content = new ArrayList<>();
    }

    public ContentGenericWrapper(List<T> content) {
        this.content = content == null ? new ArrayList<>() : content;
    }

    public List<T> getContent() {
        return content;
    }
}
