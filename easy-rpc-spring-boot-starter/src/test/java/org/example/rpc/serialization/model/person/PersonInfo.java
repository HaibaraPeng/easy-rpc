package org.example.rpc.serialization.model.person;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @Author Roc
 * @Date 2024/11/14 11:32
 */
@Getter
@Setter
public class PersonInfo implements Serializable {

    private static final long serialVersionUID = 7443011149612231882L;

    List<Phone> phones;

    Phone fax;

    FullAddress fullAddress;

    String mobileNo;

    String name;

    boolean male;

    boolean female;

    String department;

    String jobTitle;

    String homepageUrl;

    public List<Phone> getPhones() {
        return phones;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((department == null) ? 0 : department.hashCode());
        result = prime * result + ((fax == null) ? 0 : fax.hashCode());
        result = prime * result + (female ? 1231 : 1237);
        result = prime * result + ((fullAddress == null) ? 0 : fullAddress.hashCode());
        result = prime * result + ((homepageUrl == null) ? 0 : homepageUrl.hashCode());
        result = prime * result + ((jobTitle == null) ? 0 : jobTitle.hashCode());
        result = prime * result + (male ? 1231 : 1237);
        result = prime * result + ((mobileNo == null) ? 0 : mobileNo.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((phones == null) ? 0 : phones.hashCode());
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
        PersonInfo other = (PersonInfo) obj;
        if (department == null) {
            if (other.department != null)
                return false;
        } else if (!department.equals(other.department))
            return false;
        if (fax == null) {
            if (other.fax != null)
                return false;
        } else if (!fax.equals(other.fax))
            return false;
        if (female != other.female)
            return false;
        if (fullAddress == null) {
            if (other.fullAddress != null)
                return false;
        } else if (!fullAddress.equals(other.fullAddress))
            return false;
        if (homepageUrl == null) {
            if (other.homepageUrl != null)
                return false;
        } else if (!homepageUrl.equals(other.homepageUrl))
            return false;
        if (jobTitle == null) {
            if (other.jobTitle != null)
                return false;
        } else if (!jobTitle.equals(other.jobTitle))
            return false;
        if (male != other.male)
            return false;
        if (mobileNo == null) {
            if (other.mobileNo != null)
                return false;
        } else if (!mobileNo.equals(other.mobileNo))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (phones == null) {
            if (other.phones != null)
                return false;
        } else if (!phones.equals(other.phones))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "PersonInfo [phones=" + phones + ", fax=" + fax + ", fullAddress=" + fullAddress
                + ", mobileNo=" + mobileNo + ", name=" + name + ", male=" + male + ", female="
                + female + ", department=" + department + ", jobTitle=" + jobTitle
                + ", homepageUrl=" + homepageUrl + "]";
    }
}
