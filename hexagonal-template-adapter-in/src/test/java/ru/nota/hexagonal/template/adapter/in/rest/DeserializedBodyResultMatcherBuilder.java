package ru.nota.hexagonal.template.adapter.in.rest;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.function.Consumer;
import java.util.function.Function;

public class DeserializedBodyResultMatcherBuilder<T> {
    private final Function<MvcResult, T> deserializedBodyExtractor;

    public DeserializedBodyResultMatcherBuilder(Function<MvcResult, T> deserializedBodyExtractor) {
        this.deserializedBodyExtractor = deserializedBodyExtractor;
    }

    public ResultMatcher matches(Consumer<T> resultMatcher) {
        return mvcResult -> {
            T deserializedObject = deserializedBodyExtractor.apply(mvcResult);
            resultMatcher.accept(deserializedObject);
        };
    }
}
