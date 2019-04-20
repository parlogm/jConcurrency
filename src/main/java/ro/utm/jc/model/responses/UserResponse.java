package ro.utm.jc.model.responses;

import ro.utm.jc.model.entities.Users;

import java.util.Objects;

public class UserResponse extends OperationResponse {
    private Users data = new Users();

    public Users getData() {
        return data;
    }

    public void setData(Users data) {
        this.data = data;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        UserResponse that = (UserResponse) o;
        return Objects.equals(data, that.data);
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), data);
    }
}
