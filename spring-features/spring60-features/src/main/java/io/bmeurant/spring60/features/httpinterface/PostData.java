package io.bmeurant.spring60.features.httpinterface;

/**
 * A simple record representing data to be sent/received via HTTP.
 */
public record PostData(Integer id, String title, String body, Integer userId) {
}
