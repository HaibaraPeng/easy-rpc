package org.example.rpc.serialization.model.person;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author Roc
 * @Date 2024/11/14 11:30
 */
@Getter
@Setter
public class BigPerson implements Serializable {

    private static final long serialVersionUID = 1L;

    String personId;

    String loginName;

    PersonStatus status;

    String email;

    String penName;

    PersonInfo infoProfile;

    public BigPerson() {

    }

    public BigPerson(String id) {
        this.personId = id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((infoProfile == null) ? 0 : infoProfile.hashCode());
        result = prime * result + ((loginName == null) ? 0 : loginName.hashCode());
        result = prime * result + ((penName == null) ? 0 : penName.hashCode());
        result = prime * result + ((personId == null) ? 0 : personId.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BigPerson other = (BigPerson) obj;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (infoProfile == null) {
            if (other.infoProfile != null)
                return false;
        } else if (!infoProfile.equals(other.infoProfile))
            return false;
        if (loginName == null) {
            if (other.loginName != null)
                return false;
        } else if (!loginName.equals(other.loginName))
            return false;
        if (penName == null) {
            if (other.penName != null)
                return false;
        } else if (!penName.equals(other.penName))
            return false;
        if (personId == null) {
            if (other.personId != null)
                return false;
        } else if (!personId.equals(other.personId))
            return false;
        if (status != other.status)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "BigPerson [personId=" + personId + ", loginName=" + loginName + ", status="
                + status + ", email=" + email + ", penName=" + penName + ", infoProfile="
                + infoProfile + "]";
    }
}
