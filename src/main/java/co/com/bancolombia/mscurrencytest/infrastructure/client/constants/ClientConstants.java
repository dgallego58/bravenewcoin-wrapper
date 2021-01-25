package co.com.bancolombia.mscurrencytest.infrastructure.client.constants;


public final class ClientConstants {

    //needed to get the token
    public static final String AUDIENCE = "https://api.bravenewcoin.com";
    public static final String CLIENT_ID = "oCdQoZoI96ERE9HY3sQ7JmbACfBf55RY";
    public static final String GRANT_TYPE = "client_credentials";


    //these are needed for header content
    public static final String RAPID_API_KEY_HEADER_NAME = "x-rapidapi-key";
    public static final String RAPID_API_KEY_HEADER_VALUE = "df0972c81bmsh3907660d951cd1cp13ff0cjsn59891829d50b";

    public static final String RAPID_API_HOST_HEADER_NAME = "x-rapidapi-host";
    public static final String RAPID_API_HOST_HEADER_VALUE = "bravenewcoin.p.rapidapi.com";


    private ClientConstants() {
        //Object constant pool
    }
}
