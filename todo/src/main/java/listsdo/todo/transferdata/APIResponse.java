package listsdo.todo.transferdata;

public class APIResponse {
    String message;
    boolean status;

    public APIResponse(String message, boolean status) {
        this.message = message;
        this.status = status;
    }

    public APIResponse() {}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
