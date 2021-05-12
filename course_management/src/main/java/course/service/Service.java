package course.service;

public interface Service {

    void service() throws Exception;

    default void service(Exception exception) {

    }
}
