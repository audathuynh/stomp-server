package org.maxsure.demo.encoding;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import org.maxsure.demo.data.BaseCommand;
import org.maxsure.demo.data.CommandType;
import org.maxsure.demo.data.PushCommand;
import org.maxsure.demo.data.SearchCommand;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 *
 * @author Dat Huynh
 * @since 1.0
 */
public class BaseCommandTypeAdapterFactory implements TypeAdapterFactory {

    private Map<String, TypeAdapter<?>> typeToDelegate = Maps.newLinkedHashMap();

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (type.getRawType() != BaseCommand.class) {
            return null;
        }
        final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
        typeToDelegate.put(BaseCommand.class.getName(), delegate);

        @SuppressWarnings("unchecked")
        TypeAdapter<T> searchCommandTypeAdapter = (TypeAdapter<T>) gson.getDelegateAdapter(
                this,
                TypeToken.get(SearchCommand.class));
        typeToDelegate.put(SearchCommand.class.getName(), searchCommandTypeAdapter);

        @SuppressWarnings("unchecked")
        TypeAdapter<T> pushCommandTypeAdapter = (TypeAdapter<T>) gson.getDelegateAdapter(
                this,
                TypeToken.get(PushCommand.class));
        typeToDelegate.put(PushCommand.class.getName(), pushCommandTypeAdapter);

        return new TypeAdapter<T>() {
            @Override
            public void write(JsonWriter out, T value) throws IOException {
                String className = value.getClass().getName();
                @SuppressWarnings("unchecked")
                TypeAdapter<T> delegate = (TypeAdapter<T>) typeToDelegate.get(className);
                delegate.write(out, value);
            }

            @Override
            public T read(JsonReader reader) throws IOException {
                JsonElement jsonElement = Streams.parse(reader);
                JsonElement commandTypeElement = jsonElement.getAsJsonObject().get("commandType");
                if (Objects.isNull(commandTypeElement)) {
                    return delegate.fromJsonTree(jsonElement);
                }
                CommandType commandType = CommandType.valueOf(
                        commandTypeElement.getAsString().toUpperCase());
                switch (commandType) {
                    case SEARCH:
                        return searchCommandTypeAdapter.fromJsonTree(jsonElement);
                    case PUSH:
                    case DELETE:
                        return pushCommandTypeAdapter.fromJsonTree(jsonElement);
                    default:
                        return null;
                }
            }
        };
    }

}
