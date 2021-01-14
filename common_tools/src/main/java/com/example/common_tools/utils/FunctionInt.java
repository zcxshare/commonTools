package com.example.common_tools.utils;

public interface FunctionInt<O> {
    /**
     * Applies this function to the given input.
     *
     * @param input the input
     * @return the function result.
     */
    O apply(int input);
}
