package it.polimi.myshelfie.utils;

import java.io.Serializable;
import java.util.function.Consumer;

/**
 * Used to invoke methods from messages traversing the network.
 *
 * @param <T> the type of the input to the operation
 */
public interface SerializableConsumer<T> extends Consumer<T>, Serializable {
}
