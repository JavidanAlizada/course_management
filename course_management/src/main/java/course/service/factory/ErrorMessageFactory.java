package course.service.factory;

import course.constant.ExceptionMessage;
import course.helper.MessageReader;

public class ErrorMessageFactory {

    public static Exception message(ExceptionMessage messageType) {
        switch (messageType) {
            case ACCOUNT_VIEW_ROLE_RESTRICTION:
                return new Exception(MessageReader.readMessage("account.admin"));
            case COURSE_ENROLL_ROLE_RESTRICTION:
                return new Exception(MessageReader.readMessage("course.attender.enroll"));
            case COURSE_EDIT_ROLE_RESTRICTION:
                return new Exception(MessageReader.readMessage("course.attender.edit"));
            case COURSE_VIEW_ROLE_RESTRICTION:
                return new Exception(MessageReader.readMessage("course.attender.view"));
            case COURSE_OWNER_ERROR:
                return new Exception(MessageReader.readMessage("course.tutor.own"));
            case ROLE_ERROR_REASON:
                return new Exception(MessageReader.readMessage("role.error.reason"));
            case COURSE_NOT_EXIST:
                return new Exception(MessageReader.readMessage("course.exists.error"));
            case COURSE_EDIT_RESTRICTION:
                return new Exception(MessageReader.readMessage("course.owner.edit"));
            case SERVICE_NOT_FOUND_ERROR:
                return new Exception(MessageReader.readMessage("service.error"));
            case WRONG_CREDENTIALS:
                return new Exception(MessageReader.readMessage("login.credential"));
            case ROLE_NOT_FOUND:
                return new Exception(MessageReader.readMessage("role.error"));
            case REGISTER_ERROR:
                return new Exception(MessageReader.readMessage("user.register"));
            case UNIQUE_NAME:
                return new Exception(MessageReader.readMessage("name.unique"));
            default:
                return new Exception();
        }
    }
}
