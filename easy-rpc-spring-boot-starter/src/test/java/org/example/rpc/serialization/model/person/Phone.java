package org.example.rpc.serialization.model.person;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author Roc
 * @Date 2024/11/14 11:32
 */
@Getter
@Setter
public class Phone implements Serializable {

    private static final long serialVersionUID = 4399060521859707703L;

    private String country;

    private String area;

    private String number;

    private String extensionNumber;

    public Phone() {
    }

    public Phone(String country, String area, String number, String extensionNumber) {
        this.country = country;
        this.area = area;
        this.number = number;
        this.extensionNumber = extensionNumber;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((area == null) ? 0 : area.hashCode());
        result = prime * result + ((country == null) ? 0 : country.hashCode());
        result = prime * result + ((extensionNumber == null) ? 0 : extensionNumber.hashCode());
        result = prime * result + ((number == null) ? 0 : number.hashCode());
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
        Phone other = (Phone) obj;
        if (area == null) {
            if (other.area != null)
                return false;
        } else if (!area.equals(other.area))
            return false;
        if (country == null) {
            if (other.country != null)
                return false;
        } else if (!country.equals(other.country))
            return false;
        if (extensionNumber == null) {
            if (other.extensionNumber != null)
                return false;
        } else if (!extensionNumber.equals(other.extensionNumber))
            return false;
        if (number == null) {
            if (other.number != null)
                return false;
        } else if (!number.equals(other.number))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (country != null && country.length() > 0) {
            sb.append(country);
            sb.append("-");
        }
        if (area != null && area.length() > 0) {
            sb.append(area);
            sb.append("-");
        }
        if (number != null && number.length() > 0) {
            sb.append(number);
        }
        if (extensionNumber != null && extensionNumber.length() > 0) {
            sb.append("-");
            sb.append(extensionNumber);
        }
        return sb.toString();
    }

}
